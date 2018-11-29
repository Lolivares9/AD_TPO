package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.GrupoEntity;
import entities.JugadorEntity;
import entities.RankingGrupalEntity;
import entities.RankingGrupalPK;
import excepciones.GrupoException;
import excepciones.JugadorException;
import hbt.HibernateUtil;
import negocio.Grupo;
import negocio.Jugador;
import negocio.Partido;

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
		JugadorEntity j = null;
		if(grupo.getJugadores() != null && grupo.getJugadores().size() > 0) {
			jugNeg = new ArrayList<JugadorEntity>();
			for(int i = 0;i<grupo.getJugadores().size();i++){
				j = JugadorDAO.getInstancia().toEntity(grupo.getJugadores().get(i));
				jugNeg.add(j);
			}
		}
		
		List<GrupoEntity> jeGrupos = new ArrayList<GrupoEntity> ();
		jeGrupos.add(ge);
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

	public boolean ingresarNuevosMiembros(String nombreGrupo, List<String> apodoNuevosMiembros) throws JugadorException {
		GrupoEntity g;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		List<Jugador> miembrosNuevos = new ArrayList<Jugador>();
		JugadorEntity jugadorNuevo;
		s.beginTransaction();
		try{
			g = (GrupoEntity) s.createQuery("from GrupoEntity ge where ge.nombre = ?").setString(0, nombreGrupo).uniqueResult();
			if(g != null){
				for(int i = 0;i<apodoNuevosMiembros.size();i++){
					miembrosNuevos.add(JugadorDAO.getInstancia().buscarPorApodo(apodoNuevosMiembros.get(i)));
					jugadorNuevo = JugadorDAO.getInstancia().toEntity(miembrosNuevos.get(i));
					g.getJugadores().add(jugadorNuevo);
				}
			}
			s.saveOrUpdate(g);
			s.getTransaction().commit();
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			s.close();
		}
		return false;
	}

	public void setarPuntajeGrupal(Partido partido) {
		int idGrupo = 0;
		RankingGrupalPK id = new RankingGrupalPK();
		RankingGrupalPK id2 = new RankingGrupalPK();
		RankingGrupalPK id3 = new RankingGrupalPK();
		RankingGrupalPK id4 = new RankingGrupalPK();
		RankingGrupalEntity rkEntity1 = new RankingGrupalEntity();
		RankingGrupalEntity rkEntity2 = new RankingGrupalEntity();
		RankingGrupalEntity rkEntity3 = new RankingGrupalEntity();
		RankingGrupalEntity rkEntity4 = new RankingGrupalEntity();
		GrupoEntity g;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		JugadorEntity jugador11;
		JugadorEntity jugador21;
		JugadorEntity jugador12;
		JugadorEntity jugador22;
		s.beginTransaction();
		try{
			idGrupo = (Integer) s.createSQLQuery("SELECT ID_GRUPO FROM GRUPOS WHERE ID_GRUPO IN (SELECT ID_PARTIDO FROM PARTIDOS WHERE ID_PARTIDO = ?)").setInteger(0, partido.getIdPartido()).uniqueResult();
			//BUSCO EL GRUPO
			g = (GrupoEntity) s.createQuery("from GrupoEntity ge where ge.idGrupo = ?").setInteger(0, idGrupo).uniqueResult();
			jugador11 = (JugadorEntity) s.createQuery("from JugadorEntity je where je.idJugador = ?").setInteger(0, partido.getParejas().get(0).getJugador1().getId()).uniqueResult();
			id.setGrupo(g);
			id.setJugador(jugador11);
			jugador21 = (JugadorEntity) s.createQuery("from JugadorEntity je where je.idJugador = ?").setInteger(0, partido.getParejas().get(1).getJugador1().getId()).uniqueResult();
			id2.setGrupo(g);
			id2.setJugador(jugador21);
			jugador12 = (JugadorEntity) s.createQuery("from JugadorEntity je where je.idJugador = ?").setInteger(0, partido.getParejas().get(0).getJugador2().getId()).uniqueResult();
			id3.setGrupo(g);
			id3.setJugador(jugador12);
			jugador22 = (JugadorEntity) s.createQuery("from JugadorEntity je where je.idJugador = ?").setInteger(0, partido.getParejas().get(1).getJugador2().getId()).uniqueResult();
			id4.setGrupo(g);
			id4.setJugador(jugador22);
			//SIGNIFICA QUE GANO LA PAREJA 1
			if(partido.getParejaGanadora().getIdPareja().equals(partido.getParejas().get(0).getIdPareja())){
				if(!exiteEnRaking(g.getIdGrupo(),jugador11.getIdJugador()) && !exiteEnRaking(g.getIdGrupo(),jugador12.getIdJugador())){
					rkEntity1.setId(id);
					rkEntity1.setPartidosGanados(1);
					rkEntity1.setPartidosJugados(1);
					rkEntity1.setPuntaje(5);
					rkEntity2.setId(id2);
					rkEntity2.setPartidosGanados(1);
					rkEntity2.setPartidosJugados(1);
					rkEntity2.setPuntaje(5);
					s.saveOrUpdate(rkEntity1);
					s.saveOrUpdate(rkEntity2);
					return;
				}
				else{
					rkEntity1.setId(id);
					rkEntity1.setPartidosGanados(rkEntity1.getPartidosGanados() + 1);
					rkEntity1.setPartidosJugados(rkEntity1.getPartidosJugados() + 1);
					rkEntity1.setPuntaje(rkEntity1.getPuntaje() + 5);
					rkEntity2.setId(id2);
					rkEntity2.setPartidosGanados(1);
					rkEntity2.setPartidosJugados(1);
					rkEntity2.setPuntaje(5);
					s.saveOrUpdate(rkEntity1);
					s.saveOrUpdate(rkEntity2);
					return;
				}
			}
			else{
				if(!exiteEnRaking(g.getIdGrupo(),jugador21.getIdJugador()) && !exiteEnRaking(g.getIdGrupo(),jugador22.getIdJugador())){
					rkEntity3.setId(id3);
					rkEntity3.setPartidosGanados(1);
					rkEntity3.setPartidosJugados(1);
					rkEntity3.setPuntaje(5);
					rkEntity4.setId(id4);
					rkEntity4.setPartidosGanados(1);
					rkEntity4.setPartidosJugados(1);
					rkEntity4.setPuntaje(5);
					s.saveOrUpdate(rkEntity3);
					s.saveOrUpdate(rkEntity4);
					return;
				}
				else{
					rkEntity3.setId(id3);
					rkEntity3.setPartidosGanados(rkEntity1.getPartidosGanados() + 1);
					rkEntity3.setPartidosJugados(rkEntity1.getPartidosJugados() + 1);
					rkEntity3.setPuntaje(rkEntity1.getPuntaje() + 5);
					rkEntity4.setId(id4);
					rkEntity4.setPartidosGanados(1);
					rkEntity4.setPartidosJugados(1);
					rkEntity4.setPuntaje(5);
					s.saveOrUpdate(rkEntity3);
					s.saveOrUpdate(rkEntity4);
					return;
				}
			}
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			s.close();
		}
	}

	@SuppressWarnings("unchecked")
	private boolean exiteEnRaking(Integer idGrupo, Integer idJugador) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		List<Integer> idGrupos = new ArrayList<Integer>();
		s.beginTransaction();
		try{
			idGrupos = (List<Integer>) s.createSQLQuery("SELECT ID_GRUPO FROM RANKING_GRUPAL WHERE ID_GRUPO = ? AND ID_JUGADOR = ?)").setInteger(0, idGrupo).setInteger(1, idJugador).list();
			if(idGrupos != null && idGrupos.size() >= 1){
				s.getTransaction().commit();
				s.close();
				return true;
			}
		}catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> buscarJugadoresGrupo(String nombreGrupo){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		List<String> apodos = new ArrayList<String>();
		s.beginTransaction();
		try{
			apodos = (List<String>) s.createSQLQuery("SELECT APODO FROM JUGADORES WHERE ID_JUGADOR IN (SELECT jugadores_ID_JUGADOR FROM GRUPOS_JUGADORES WHERE grupos_ID_GRUPO = (SELECT ID_GRUPO FROM GRUPOS WHERE NOMBRE = ?))").setString(0, nombreGrupo).list();
			if(apodos != null){
				s.getTransaction().commit();
			}
		}catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			s.close();
		}
		return apodos;
	}
}
