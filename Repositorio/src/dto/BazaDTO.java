package dto;

import java.io.Serializable;
import java.util.List;

public class BazaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6002073309038469873L;
	
	private Integer idBaza;
	private int numeroBaza;
	private ParejaDTO ganadores;
	private int puntajePareja1;
	private int puntajePareja2;
	private List<TurnoDTO> turnos;

	public BazaDTO(int numeroBaza, ParejaDTO ganadores, int puntajePareja1, int puntajePareja2) {
		super();
		this.numeroBaza = numeroBaza;
		this.ganadores = ganadores;
		this.puntajePareja1 = puntajePareja1;
		this.puntajePareja2 = puntajePareja2;
	}
	
	public BazaDTO(Integer idBaza, int numeroBaza, ParejaDTO ganadores, int puntajePareja1, int puntajePareja2) {
		super();
		this.idBaza = idBaza;
		this.numeroBaza = numeroBaza;
		this.ganadores = ganadores;
		this.puntajePareja1 = puntajePareja1;
		this.puntajePareja2 = puntajePareja2;
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

	public List<TurnoDTO> getTurnos() {
		return turnos;
	}

	public void setTurnos(List<TurnoDTO> turnos) {
		this.turnos = turnos;
	}

	public Integer getIdBaza() {
		return idBaza;
	}

	public void setIdBaza(Integer idBaza) {
		this.idBaza = idBaza;
	}
}
