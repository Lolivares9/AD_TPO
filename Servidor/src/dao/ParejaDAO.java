package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.JugadorEntity;
import entities.ParejaEntity;
import excepciones.ParejaException;
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
	public List<ParejaEntity> buscarParejasPorJugador(Integer idJugador, Session s) throws ParejaException{
		List<ParejaEntity> p;
		try {
			p = (List<ParejaEntity>) s
					.createQuery("from ParejaEntity pe where pe.jugador1 = ? OR pe.jugador2 = ?")
					.setInteger(0, idJugador).setInteger(1, idJugador).list();
			
			s.getTransaction().commit();
			
			
			if (p == null) {
				return Collections.<ParejaEntity>emptyList();
			}else {
				return p;		
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ParejaException("Error al buscar las parejas del jugador");
		}
	}
	
	public List<Pareja> buscarParejasLibres(Pareja parej) throws ParejaException{
		List<Integer> idParejasDisponibles = getIdParejas(parej.getIdPareja());
		List<ParejaEntity> parejas = new ArrayList<ParejaEntity>();
		List<Pareja> parejasNuevas = new ArrayList<Pareja>();
		ParejaEntity p;
		int i = 0;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		try {
			while(i<idParejasDisponibles.size()){
				p = (ParejaEntity) s
						.createQuery("from ParejaEntity pe where pe.idPareja = ?")
						.setInteger(0, idParejasDisponibles.get(i)).uniqueResult();
				s.getTransaction().commit();
				i++;
				if(p != null){
					parejas.add(p);
				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ParejaException("Error al buscar las parejas del jugador");
		}
		if(parejas != null){
			for(int x = 0;x < parejas.size();x++){
				parejasNuevas.add(toNegocio(parejas.get(x)));
				parejas.remove(x);
			}
		}
		return parejasNuevas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getIdParejas(int idPareja){
		List<Integer> ids = new ArrayList<Integer>();
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		ids = (List<Integer>) s.createSQLQuery("SELECT P.ID_PAREJA FROM PAREJAS P WHERE (P.ID_JUGADOR1 IN (SELECT J.ID_JUGADOR FROM JUGADORES J WHERE J.JUGANDO = 'false' AND J.CONECTADO = 'true') AND P.ID_JUGADOR2 IN (SELECT J.ID_JUGADOR FROM JUGADORES J WHERE J.JUGANDO = 'false' AND J.CONECTADO = 'true')) AND P.ID_PAREJA <> ?").setInteger(0, idPareja).list();
		s.getTransaction().commit();

		return ids;
	}
	
	public Pareja toNegocio(ParejaEntity pe){
		return new Pareja(pe.getIdPareja(),JugadorDAO.getInstancia().toNegocio(pe.getJugador1()), 
				JugadorDAO.getInstancia().toNegocio(pe.getJugador2()));
	}

	public ParejaEntity toEntity(Pareja parejaGanadora) {
		ParejaEntity pe = new ParejaEntity();
		
		pe.setIdPareja(parejaGanadora.getIdPareja());
		
		JugadorEntity jugador1 = JugadorDAO.getInstancia().toEntity(parejaGanadora.getJugador1());
		pe.setJugador1(jugador1);
		
		JugadorEntity jugador2 = JugadorDAO.getInstancia().toEntity(parejaGanadora.getJugador2());
		pe.setJugador2(jugador2);
		
		return pe;
	}
}
