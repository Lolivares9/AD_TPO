package dao;

import negocio.Chico;

public class ChicoDAO {
	private static ChicoDAO instancia;
	
	public static ChicoDAO getInstancia() {
		if(instancia == null){
			instancia = new ChicoDAO();
		}
		return instancia;
	}

	public boolean guardar(Chico chico) {
		// TODO Auto-generated method stub
		return false;
	}
}
