package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.GrupoEntity;
import entities.JugadorEntity;
import entities.RankingGrupalEntity;
import entities.RankingGrupalPK;
import hbt.HibernateUtil;
import negocio.RankingGrupal;

public class RankingGrupalDAO {
	private static RankingGrupalDAO instancia;
	
	public static RankingGrupalDAO getInstancia() {
		if(instancia == null){
			instancia = new RankingGrupalDAO();
		}
		return instancia;
	}

	public boolean guardar(RankingGrupal rankingGrupal) {
		RankingGrupalEntity rgEntity = toEntity(rankingGrupal);
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		s.saveOrUpdate(rgEntity);
		s.getTransaction().commit();
		s.close();

		return true;
	}

	private RankingGrupalEntity toEntity(RankingGrupal rankingGrupal) {
		RankingGrupalPK rgePK = new RankingGrupalPK();   
		RankingGrupalEntity rge = new RankingGrupalEntity();
		
		GrupoEntity grupo = GrupoDAO.getInstancia().toEntity(rankingGrupal.getGrupo());
		rgePK.setGrupo(grupo);
		
		JugadorEntity jugador = JugadorDAO.getInstancia().toEntity(rankingGrupal.getJugador());
		rgePK.setJugador(jugador);
		
		rge.setId(rgePK);
		rge.setPartidosJugados(rankingGrupal.getPartidosJugados());
		rge.setPartidosGanados(rankingGrupal.getPartidosGanados());
		rge.setPuntaje(rankingGrupal.getPuntaje());
		
		return rge;
	}
}
