package negocio;

import dao.ChicoDAO;
import dto.ChicoDTO;

/**
 * Soy una colección de manos
 * Un chico es finalizado cuando se llegan a los 30 tantos
 * (15 malos y 15 buenos)
 */
public class Chico {
	private int numero;
	private boolean finalizado;
	private Pareja parejaGanadora;
	private int puntajePareja1;
	private int puntajePareja2;
	
	public Chico(int numero, boolean finaizado, Pareja parejaGanadora, int puntajePareja1, int puntajePareja2) {
		super();
		this.numero = numero;
		this.finalizado = finaizado;
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
		return finalizado;
	}
	public void setFinaizado(boolean finaizado) {
		this.finalizado = finaizado;
	}
	public Pareja getParejaGanadora() {
		return parejaGanadora;
	}
	public void setParejaGanadora(Pareja parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}
	
	public boolean guardar(){
		return ChicoDAO.getInstancia().guardar(this);
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
	
	public ChicoDTO toDTO() {
		return new ChicoDTO(numero, finalizado, parejaGanadora.toDTO(), puntajePareja1, puntajePareja2);
	}
}
