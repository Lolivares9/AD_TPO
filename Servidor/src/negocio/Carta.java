package negocio;

import dao.BazaDAO;
import dao.CartaDAO;
import enums.PaloCarta;

public class Carta {
	private int numero;
	private PaloCarta palo;
	private int valorJuego;
	
	public Carta(int numero, PaloCarta palo, int valorJuego) {
		super();
		this.numero = numero;
		this.palo = palo;
		this.valorJuego = valorJuego;
	}
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public PaloCarta getPalo() {
		return palo;
	}
	public void setPalo(PaloCarta palo) {
		this.palo = palo;
	}
	public int getValorJuego() {
		return valorJuego;
	}
	public void setValorJuego(int valorJuego) {
		this.valorJuego = valorJuego;
	}
	
	
	public boolean guardar(){
		return CartaDAO.getInstancia().guardar(this);
	}
}
