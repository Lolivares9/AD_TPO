package dao;

import negocio.Mano;

public class ManoDAO {
	private static ManoDAO instancia;
	
	public static ManoDAO getInstancia() {
		if(instancia == null){
			instancia = new ManoDAO();
		}
		return instancia;
	}

	public boolean guardar(Mano mano) {
		// TODO Auto-generated method stub
		return false;
	}
}
