package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.ParejaEntity;
import entities.PartidoEntity;
import hbt.HibernateUtil;
import negocio.Pareja;

public class ParejaDAO {
	private static ParejaDAO instancia;
	
	public static ParejaDAO getInstancia() {
		if(instancia == null){
			instancia = new ParejaDAO();
		}
		return instancia;
	}

	public boolean guardar(Pareja pareja) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<ParejaEntity> buscarParejasPorJugador(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		Integer idJugador = 1;
		List<ParejaEntity> p;
		try {
			p = (List<ParejaEntity>) s
					.createQuery("from ParejaEntity pe where pe.jugador1 = ? OR pe.jugador2 = ?")
					.setInteger(0, idJugador).setInteger(1, idJugador).list();
			s.getTransaction().commit();
			
			
			if (p == null) {
				//throw new GrupoException("El jugador no tuvo parejas");
			}else {
				List<PartidoEntity> partidos = p.get(0).getPartidos();
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
}
