package dto;

import java.io.Serializable;

public class ManoDTO implements Serializable {

	private ChicoDTO chico;
	private int numeroMano;
	private ParejaDTO parejaGanadora;
	
	public ManoDTO(ChicoDTO chico, int numeroMano, ParejaDTO parejaGanadora) {
		super();
		this.chico = chico;
		this.numeroMano = numeroMano;
		this.parejaGanadora = parejaGanadora;
	}
	
	public ChicoDTO getChico() {
		return chico;
	}
	public void setChico(ChicoDTO chico) {
		this.chico = chico;
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
	
}
