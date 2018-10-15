package dto;

import java.io.Serializable;

public class BazaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6002073309038469873L;
	
	private ManoDTO mano;
	private int numeroBaza;
	private ParejaDTO ganadores;
	private int puntajePareja1;
	private int puntajePareja2;
	
	public BazaDTO(ManoDTO mano, int numeroBaza, ParejaDTO ganadores, int puntajePareja1, int puntajePareja2) {
		super();
		this.mano = mano;
		this.numeroBaza = numeroBaza;
		this.ganadores = ganadores;
		this.puntajePareja1 = puntajePareja1;
		this.puntajePareja2 = puntajePareja2;
	}
	
	public BazaDTO(int numeroBaza, ParejaDTO ganadores, int puntajePareja1, int puntajePareja2) {
		super();
		this.numeroBaza = numeroBaza;
		this.ganadores = ganadores;
		this.puntajePareja1 = puntajePareja1;
		this.puntajePareja2 = puntajePareja2;
	}
	
	public ManoDTO getMano() {
		return mano;
	}
	public void setMano(ManoDTO mano) {
		this.mano = mano;
	}
	public int getNumero() {
		return numeroBaza;
	}
	public void setNumero(int numero) {
		this.numeroBaza = numero;
	}
	public ParejaDTO getGanadores() {
		return ganadores;
	}
	public void setGanadores(ParejaDTO ganadores) {
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
}
