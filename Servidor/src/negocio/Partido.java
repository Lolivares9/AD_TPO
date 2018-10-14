package negocio;

import java.util.List;

import enums.EstadoPartido;
import enums.TipoModalidad;

/**
 * Soy la jugada de 2 o 3 chicos
 * El partido se gana cuando una de las dos parejas consigue dos chicos. Si hay
 * empate en los dos primeros chicos, el tercero es el definitivo 
 */
public class Partido {
	private Chico chico;
	private TipoModalidad modalidad;
	private List<Pareja> parejas;
	private Pareja parejaGanadora;
	private EstadoPartido estado;
	
	public Partido(Chico chico, TipoModalidad modalidad, List<Pareja> parejas, Pareja parejaGanadora,
			EstadoPartido estado) {
		super();
		this.chico = chico;
		this.modalidad = modalidad;
		this.parejas = parejas;
		this.parejaGanadora = parejaGanadora;
		this.estado = estado;
	}
	public Chico getChico() {
		return chico;
	}
	public void setChico(Chico chico) {
		this.chico = chico;
	}
	public TipoModalidad getModalidad() {
		return modalidad;
	}
	public void setModalidad(TipoModalidad modalidad) {
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
	public EstadoPartido getEstado() {
		return estado;
	}
	public void setEstado(EstadoPartido estado) {
		this.estado = estado;
	}
	

}
