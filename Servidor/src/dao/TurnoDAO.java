package dao;

import negocio.Turno;

public class TurnoDAO {
	private static TurnoDAO instancia;
	
	public static TurnoDAO getInstancia() {
		if(instancia == null){
			instancia = new TurnoDAO();
		}
		return instancia;
	}

	public boolean guardar(Turno turno) {
		// TODO Auto-generated method stub
		return false;
	}
}
