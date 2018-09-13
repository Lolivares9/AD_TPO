package negocio;

import java.util.List;

public class Partido {
	private Chico chico;
	private Modalidad modalidad;
	private List<Pareja> parejas;
	private Pareja parejaGanadora;
	
	public Partido(Chico chico, Modalidad modalidad, List<Pareja> parejas, Pareja parejaGanadora) {
		super();
		this.chico = chico;
		this.modalidad = modalidad;
		this.parejas = parejas;
		this.parejaGanadora = parejaGanadora;
	}
	
	public Chico getChico() {
		return chico;
	}
	public void setChico(Chico chico) {
		this.chico = chico;
	}
	public Modalidad getModalidad() {
		return modalidad;
	}
	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}
	public List<Pareja> getParejas() {
		return parejas;
	}
	public void setParejas(List<Pareja> parejas) {
		this.parejas = parejas;
	}
	public Pareja getParejaGanadora() {
		return parejaGanadora;
	}
	public void setParejaGanadora(Pareja parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}
}
