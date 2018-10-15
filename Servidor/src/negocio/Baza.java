package negocio;

import dao.BazaDAO;
import dto.BazaDTO;

/**
 * Soy la jugada de 1 carta de los 4 jugadores
 */
public class Baza {
	
	private Integer idBaza;
	private Mano mano;
	private int numeroBaza;
	private Pareja ganadores;
	private int puntajePareja1;
	private int puntajePareja2;
	
	public Baza(Mano mano, int numero, Pareja ganadores, int puntajePareja1, int puntajePareja2) {
		super();
		this.mano = mano;
		this.numeroBaza = numero;
		this.ganadores = ganadores;
		this.puntajePareja1 = puntajePareja1;
		this.puntajePareja2 = puntajePareja2;
	}
	
	public Baza(Integer idBaza, int numeroBaza, Pareja ganadores, int puntajePareja1, int puntajePareja2) {
		super();
		this.idBaza = idBaza;
		this.numeroBaza = numeroBaza;
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
		return numeroBaza;
	}
	public void setNumero(int numero) {
		this.numeroBaza = numero;
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
	
	public BazaDTO toDTO() {
		return new BazaDTO(numeroBaza, ganadores.toDTO(), puntajePareja1, puntajePareja2);
	}
	public Integer getIdBaza() {
		return idBaza;
	}
	public void setIdBaza(Integer idBaza) {
		this.idBaza = idBaza;
	}
}
