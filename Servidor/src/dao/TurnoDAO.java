package dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.TurnoEntity;
import excepciones.TurnoException;
import hbt.HibernateUtil;
import negocio.Turno;

public class TurnoDAO {
	private static TurnoDAO instancia;
	
	public static TurnoDAO getInstancia() {
		if(instancia == null){
			instancia = new TurnoDAO();
		}
		return instancia;
	}

	public boolean guardar(Turno turno) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<Turno> buscarTurnosPorBaza(Integer idBaza) throws TurnoException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		List<TurnoEntity> manosET = null;
		try {
			manosET = (List<TurnoEntity>) s
					.createQuery("from TurnoEntity te where te.idBaza = ?")
					.setInteger(0, idBaza).list();
			s.getTransaction().commit();
			
			
			if (manosET == null) {
				throw new TurnoException("No se encontraron turnos para la baza indicada");
			}else {
				return manosET.stream().map(this::toNegocio).collect(Collectors.toList());		
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Turno toNegocio(TurnoEntity te) {
		return new Turno(JugadorDAO.getInstancia().toNegocio(te.getJugador()),
				te.getEnvite(), CartaDAO.getInstancia().toNegocio(te.getCarta()));
	}
}
