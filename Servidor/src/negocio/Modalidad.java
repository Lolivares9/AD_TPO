package negocio;

import dao.CartaDAO;
import dao.ModalidadDAO;

public class Modalidad {
	private String descripcion;
	private boolean individual;
	
	public Modalidad(String descripcion, boolean individual) {
		super();
		this.descripcion = descripcion;
		this.individual = individual;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean isIndividual() {
		return individual;
	}
	public void setIndividual(boolean individual) {
		this.individual = individual;
	}
	
	public boolean guardar(){
		return ModalidadDAO.getInstancia().guardar(this);
	}
}
