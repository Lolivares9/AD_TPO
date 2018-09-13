package dao;

import negocio.Baza;

public class BazaDAO {
	private static BazaDAO instancia;
	
	public static BazaDAO getInstancia() {
		if(instancia == null){
			instancia = new BazaDAO();
		}
		return instancia;
	}

	public boolean guardar(Baza baza) {
		// TODO Auto-generated method stub
		return false;
	}
}
