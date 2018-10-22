package dto;

import java.io.Serializable;
import java.util.List;

public class ManoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3562346828808906265L;
	
	private int numeroMano;
	private ParejaDTO parejaGanadora;
	private List<BazaDTO> bazas;
	
	public ManoDTO(int numeroMano, ParejaDTO parejaGanadora) {
		super();
		this.numeroMano = numeroMano;
		this.parejaGanadora = parejaGanadora;
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
