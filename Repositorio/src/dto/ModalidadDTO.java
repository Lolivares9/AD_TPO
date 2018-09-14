package dto;

import java.io.Serializable;

import enums.TipoModalidad;

public class ModalidadDTO implements Serializable {

	private TipoModalidad modalidad;
	private boolean individual;
	
	public ModalidadDTO(TipoModalidad descripcion, boolean individual) {
		super();
		this.modalidad = descripcion;
		this.individual = individual;
	}
	
	public TipoModalidad getDescripcion() {
		return modalidad;
	}
	public void setDescripcion(TipoModalidad descripcion) {
		this.modalidad = descripcion;
	}
	public boolean isIndividual() {
		return individual;
	}
	public void setIndividual(boolean individual) {
		this.individual = individual;
	}
	
	

}
