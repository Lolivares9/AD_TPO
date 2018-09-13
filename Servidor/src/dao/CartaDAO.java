package dao;

import negocio.Carta;

public class CartaDAO {
	private static CartaDAO instancia;
	
	public static CartaDAO getInstancia() {
		if(instancia == null){
			instancia = new CartaDAO();
		}
		return instancia;
	}

	public boolean guardar(Carta carta) {
		// TODO Auto-generated method stub
		return false;
	}
}
