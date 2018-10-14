package dto;
import java.io.Serializable;

public class ChicoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1843892719080160092L;
	
	private int numero;
	private boolean finaizado;
	private ParejaDTO parejaGanadora;
	private int puntajePareja1;
	private int puntajePareja2;
	
	public ChicoDTO(int numero, boolean finaizado, ParejaDTO parejaGanadora, int puntajePareja1, int puntajePareja2) {
		super();
		this.numero = numero;
		this.finaizado = finaizado;
		this.parejaGanadora = parejaGanadora;
		this.puntajePareja1 = puntajePareja1;
		this.puntajePareja2 = puntajePareja2;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public boolean isFinaizado() {
		return finaizado;
	}
	public void setFinaizado(boolean finaizado) {
		this.finaizado = finaizado;
	}
	public ParejaDTO getParejaGanadora() {
		return parejaGanadora;
	}
	public void setParejaGanadora(ParejaDTO parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
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
