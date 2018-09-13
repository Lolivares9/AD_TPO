package dao;

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
}
