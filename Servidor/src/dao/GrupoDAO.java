package dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.GrupoEntity;
import entities.JugadorEntity;
import excepciones.GrupoException;
import hbt.HibernateUtil;
import negocio.Grupo;
import negocio.Jugador;

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
		GrupoEntity g;
		Grupo grupo = null;
		try {
			g = (GrupoEntity) s
					.createQuery("from GrupoEntity ge where ge.nombre = ?")
					.setString(0, nombreGrupo).uniqueResult();
			s.getTransaction().commit();
			
			
			if (g == null) {
				throw new GrupoException("No existe el grupo.");
			}else {
				grupo = toNegocio(g);
				s.close();
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
		return grupo;
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
		GrupoEntity gEntity = toEntity(grupo);
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		s.saveOrUpdate(gEntity);
		s.getTransaction().commit();
		s.close();

		return true;
	}
	
	private GrupoEntity toEntity(Grupo grupo){
		GrupoEntity ge = new GrupoEntity();
		
		ge.setNombre(grupo.getNombre());
		ge.setIdGrupo(grupo.getIdGrupo());
		
		JugadorEntity admin = JugadorDAO.getInstancia().toEntity(grupo.getJugadorAdmin());
		ge.setJugadorAdmin(admin);
		
		List<JugadorEntity> jugNeg = null;
		if(grupo.getJugadores() != null) {
			jugNeg = grupo.getJugadores().stream().map(j -> JugadorDAO.getInstancia().toEntity(j)).collect(Collectors.toList());
		}
		ge.setJugadores(jugNeg);
		
		return ge;
	}
	
	public Grupo toNegocio(GrupoEntity grupo){
		Grupo g = new Grupo();
		Jugador jAdmin = JugadorDAO.getInstancia().toNegocio(grupo.getJugadorAdmin());
		g.setJugadorAdmin(jAdmin);
		g.setNombre(grupo.getNombre());
		g.setIdGrupo(grupo.getIdGrupo());
		List<Jugador> jugNeg = grupo.getJugadores().stream().map(j -> JugadorDAO.getInstancia().toNegocio(j)).collect(Collectors.toList());
		g.setJugadores(jugNeg);
		return g;
	}
}
