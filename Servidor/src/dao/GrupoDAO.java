package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.GrupoEntity;
import entities.JugadorEntity;
import excepciones.GrupoException;
import hbt.HibernateUtil;
import negocio.Grupo;

public class GrupoDAO {
	private static GrupoDAO instancia;
	
	public static GrupoDAO getInstancia() {
		if(instancia == null){
			instancia = new GrupoDAO();
		}
		return instancia;
	}
	
	public Grupo buscarGrupo(String nombreGrupo) throws GrupoException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		Grupo g;
		try {
			g = (Grupo) s
					.createQuery("from GrupoEntity ge where ge.nombre = ?")
					.setString(0, nombreGrupo).uniqueResult();
			s.getTransaction().commit();
			s.close();
			
			if (g != null) {
				throw new GrupoException("No existe el grupo.");
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
		return g;
	}
	
	public boolean nombreGrupoValido(String nombreGrupo) throws GrupoException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		Integer aux;
		try {
			aux = (Integer) s
					.createQuery("select idGrupo from GrupoEntity ge where ge.nombre = ?")
					.setString(0, nombreGrupo).uniqueResult();
			s.getTransaction().commit();
			s.close();
			
			if (aux != null) {
				throw new GrupoException("Ya existe un grupo con ese nombre.");
			} else {
				return true;
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
	}

	public boolean guardar(Grupo grupo) {
		GrupoEntity jEntity = toEntity(grupo);
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		s.saveOrUpdate(jEntity);
		s.getTransaction().commit();
		s.close();

		return true;
	}
	
	private GrupoEntity toEntity(Grupo grupo){
		GrupoEntity entity = new GrupoEntity();
		entity.setNombre(grupo.getNombre());
		JugadorEntity admin = JugadorDAO.getInstancia().toEntity(grupo.getJugadorAdmin());
		entity.setJugadorAdmin(admin);
		return entity;
	}
}
