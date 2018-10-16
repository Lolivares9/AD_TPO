package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.ChicoEntity;
import entities.JugadorEntity;
import entities.ParejaEntity;
import hbt.HibernateUtil;
import negocio.Pareja;
import negocio.Partido;

public class ParejaDAO {
	private static ParejaDAO instancia;
	
	public static ParejaDAO getInstancia() {
		if(instancia == null){
			instancia = new ParejaDAO();
		}
		return instancia;
	}

	public boolean guardar(Pareja p) {
		ParejaEntity pEntity = toEntity(p);
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		s.saveOrUpdate(pEntity);
		s.getTransaction().commit();
		s.close();

		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<ParejaEntity> buscarParejasPorJugador(Integer idJugador){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		List<ParejaEntity> p;
		try {
			p = (List<ParejaEntity>) s
					.createQuery("from ParejaEntity pe where pe.jugador1 = ? OR pe.jugador2 = ?")
					.setInteger(0, idJugador).setInteger(1, idJugador).list();
			s.getTransaction().commit();
			
			
			if (p == null) {
				//throw new GrupoException("El jugador no tuvo parejas");
			}else {
				return p;		
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}finally {
			s.close();
		}
		return null;
	}
	
	public List<Partido> obtenerPartidosPorPareja(Integer idJugador){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		List<ParejaEntity> parejas = buscarParejasPorJugador(idJugador);
		try {
			parejas.forEach(p -> p.getPartidos());			
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}finally {
			s.close();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<ParejaEntity> buscarParejasPorJugador(Integer idJugador, Session s){
		List<ParejaEntity> p;
		try {
			p = (List<ParejaEntity>) s
					.createQuery("from ParejaEntity pe where pe.jugador1 = ? OR pe.jugador2 = ?")
					.setInteger(0, idJugador).setInteger(1, idJugador).list();
			s.getTransaction().commit();
			
			
			if (p == null) {
				//throw new GrupoException("El jugador no tuvo parejas");
			}else {
				return p;		
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public Pareja toNegocio(ParejaEntity pe){
		return new Pareja(JugadorDAO.getInstancia().toNegocio(pe.getJugador1()), 
				JugadorDAO.getInstancia().toNegocio(pe.getJugador2()), pe.getPuntaje());
	}

	public ParejaEntity toEntity(Pareja parejaGanadora) {
		ParejaEntity pe = new ParejaEntity();
		
		pe.setIdPareja(parejaGanadora.getIdPareja());
		pe.setPuntaje(parejaGanadora.getPuntaje());
		
		JugadorEntity jugador1 = JugadorDAO.getInstancia().toEntity(parejaGanadora.getJugador1());
		pe.setJugador1(jugador1);
		
		JugadorEntity jugador2 = JugadorDAO.getInstancia().toEntity(parejaGanadora.getJugador2());
		pe.setJugador2(jugador2);
		
		return pe;
	}
}
