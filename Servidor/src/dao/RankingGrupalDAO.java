package dao;

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
		// TODO Auto-generated method stub
		return false;
	}
}
