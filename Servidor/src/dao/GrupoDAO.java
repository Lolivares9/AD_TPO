package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.GrupoEntity;
import entities.JugadorEntity;
import excepciones.GrupoException;
import excepciones.PartidoException;
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
	
	public GrupoEntity toEntity(Grupo grupo){
		GrupoEntity ge = new GrupoEntity();
		
		ge.setNombre(grupo.getNombre());
		ge.setIdGrupo(grupo.getIdGrupo());
		
		JugadorEntity admin = JugadorDAO.getInstancia().toEntity(grupo.getJugadorAdmin());
		ge.setJugadorAdmin(admin);
		
		List<JugadorEntity> jugNeg = null;
		if(grupo.getJugadores() != null && grupo.getJugadores().size() > 0) {
			jugNeg = grupo.getJugadores().stream().map(j -> JugadorDAO.getInstancia().toEntity(j)).collect(Collectors.toList());
		}
		
		List<GrupoEntity> jeGrupos = new ArrayList<GrupoEntity> ();
		jeGrupos.add(ge);
		if(jugNeg != null && jugNeg.size() >= 1){
			jugNeg.get(0).setGrupos(jeGrupos);
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

	@SuppressWarnings("unchecked")
	public List<Grupo> buscarGruposPorJugador(Integer id) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		List<Integer> idGrupos = new ArrayList<Integer>();
		List<GrupoEntity> gruposEntity = new ArrayList<GrupoEntity>();
		List<Grupo> gruposNegocio = new ArrayList<Grupo>();
		s.beginTransaction();
		try{
			idGrupos = (List<Integer>) s.createSQLQuery("SELECT ID_GRUPO FROM GRUPOS WHERE ID_GRUPO IN (SELECT grupos_ID_GRUPO FROM GRUPOS_JUGADORES WHERE jugadores_ID_JUGADOR = ?)").setInteger(0, id).list();
			if(idGrupos != null){
				for(int i = 0;i<idGrupos.size();i++){
					gruposEntity.add((GrupoEntity) s.createQuery("FROM GrupoEntity WHERE idGrupo = ?").setInteger(0, idGrupos.get(i)).uniqueResult());
					gruposNegocio.add(GrupoDAO.getInstancia().toNegocio(gruposEntity.get(i)));
				}
				s.getTransaction().commit();
			}
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			s.close();
		}
		return gruposNegocio;
	}
}
