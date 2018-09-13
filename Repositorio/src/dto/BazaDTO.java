package dto;

public class BazaDTO {

	private ManoDTO mano;
	private int numero;
	private ParejaDTO ganadores;
	private int puntajePareja1;
	private int puntajePareja2;
	
	public BazaDTO(ManoDTO mano, int numero, ParejaDTO ganadores, int puntajePareja1, int puntajePareja2) {
		super();
		this.mano = mano;
		this.numero = numero;
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
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
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
