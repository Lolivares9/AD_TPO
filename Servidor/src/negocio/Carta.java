package negocio;

import dao.BazaDAO;
import dao.CartaDAO;

public class Carta {
	private int numero;
	private String palo;
	private int valorJuego;
	
	public Carta(int numero, String palo, int valorJuego) {
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
	public String getPalo() {
		return palo;
	}
	public void setPalo(String palo) {
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
