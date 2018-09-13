package dao;

import negocio.Grupo;

public class GrupoDAO {
	private static GrupoDAO instancia;
	
	public static GrupoDAO getInstancia() {
		if(instancia == null){
			instancia = new GrupoDAO();
		}
		return instancia;
	}

	public boolean guardar(Grupo grupo) {
		// TODO Auto-generated method stub
		return false;
	}
}
