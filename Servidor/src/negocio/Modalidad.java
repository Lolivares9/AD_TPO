package negocio;
import enums.TipoModalidad;

public class Modalidad {
	private TipoModalidad modalidad;
	private boolean individual;
	
	public Modalidad(TipoModalidad modalidad, boolean individual) {
		super();
		this.modalidad = modalidad;
		this.individual = individual;
	}
	
	public TipoModalidad getModalidad() {
		return modalidad;
	}
	public void setModalidad(TipoModalidad modalidad) {
		this.modalidad = modalidad;
	}
	public boolean isIndividual() {
		return individual;
	}
	public void setIndividual(boolean individual) {
		this.individual = individual;
	}

}
