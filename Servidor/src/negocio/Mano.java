package negocio;

import dao.ManoDAO;

/**
 * Soy la jugada de 2 o 3 bazas 
 * (los 4 jugadores jugaron sus 3 cartas)
 */
public class Mano {
	private Chico chico;
	private int numeroMano;
	private Pareja parejaGanadora;
	
	public Mano(Chico chico, int numeroMano, Pareja parejaGanadora) {
		super();
		this.chico = chico;
		this.numeroMano = numeroMano;
		this.parejaGanadora = parejaGanadora;
	}
	
	public Chico getChico() {
		return chico;
	}
	public void setChico(Chico chico) {
		this.chico = chico;
	}
	public int getNumeroMano() {
		return numeroMano;
	}
	public void setNumeroMano(int numeroMano) {
		this.numeroMano = numeroMano;
	}
	public Pareja getParejaGanadora() {
		return parejaGanadora;
	}
	public void setParejaGanadora(Pareja parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}
	
	public boolean guardar(){
		return ManoDAO.getInstancia().guardar(this);
	}
}
