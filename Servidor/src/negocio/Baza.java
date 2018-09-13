package negocio;

import dao.BazaDAO;
import dao.JugadorDAO;

public class Baza {
	private Mano mano;
	private int numero;
	private Pareja ganadores;
	private int puntajePareja1;
	private int puntajePareja2;
	
	public Baza(Mano mano, int numero, Pareja ganadores, int puntajePareja1, int puntajePareja2) {
		super();
		this.mano = mano;
		this.numero = numero;
		this.ganadores = ganadores;
		this.puntajePareja1 = puntajePareja1;
		this.puntajePareja2 = puntajePareja2;
	}
	public Mano getMano() {
		return mano;
	}
	public void setMano(Mano mano) {
		this.mano = mano;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public Pareja getGanadores() {
		return ganadores;
	}
	public void setGanadores(Pareja ganadores) {
		this.ganadores = ganadores;
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

	public boolean guardar(){
		return BazaDAO.getInstancia().guardar(this);
	}
}
