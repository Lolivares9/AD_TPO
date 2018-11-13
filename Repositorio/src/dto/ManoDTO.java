package dto;

import java.io.Serializable;
import java.util.List;

public class ManoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3562346828808906265L;

	private Integer idMano;
	private int numeroMano;
	private ParejaDTO parejaGanadora;
	private int puntajePareja1;
	private int puntajePareja2;
	private List<BazaDTO> bazas;
	


	public ManoDTO(Integer idMano, int numeroMano, ParejaDTO parejaGanadora, int puntajePareja1, int puntajePareja2,
			List<BazaDTO> bazas) {
		super();
		this.idMano = idMano;
		this.numeroMano = numeroMano;
		this.parejaGanadora = parejaGanadora;
		this.puntajePareja1 = puntajePareja1;
		this.puntajePareja2 = puntajePareja2;
		this.bazas = bazas;
	}
	public Integer getIdMano() {
		return idMano;
	}
	public void setIdMano(Integer idMano) {
		this.idMano = idMano;
	}
	public int getPuntajePareja1() {
		return puntajePareja1;
	}
	public void setPuntajePareja1(int puntajePareja1) {
		this.puntajePareja1 = puntajePareja1;
	}
	public int getPuntajePareja2() {
		return puntajePareja2;
	}
	public void setPuntajePareja2(int puntajePareja2) {
		this.puntajePareja2 = puntajePareja2;
	}
	public int getNumeroMano() {
		return numeroMano;
	}
	public void setNumeroMano(int numeroMano) {
		this.numeroMano = numeroMano;
	}
	public ParejaDTO getParejaGanadora() {
		return parejaGanadora;
	}
	public void setParejaGanadora(ParejaDTO parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}

	public List<BazaDTO> getBazas() {
		return bazas;
	}

	public void setBazas(List<BazaDTO> bazas) {
		this.bazas = bazas;
	}
	
}
