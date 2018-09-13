package dao;

import negocio.Modalidad;

public class ModalidadDAO {
	private static ModalidadDAO instancia;
	
	public static ModalidadDAO getInstancia() {
		if(instancia == null){
			instancia = new ModalidadDAO();
		}
		return instancia;
	}

	public boolean guardar(Modalidad modalidad) {
		// TODO Auto-generated method stub
		return false;
	}
}
