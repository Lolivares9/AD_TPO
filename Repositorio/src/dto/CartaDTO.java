package dto;

import java.io.Serializable;

import enums.PaloCarta;

public class CartaDTO implements Serializable {

	private int numero;
	private PaloCarta palo;
	private int valorJuego;
	
	public CartaDTO(int numero, PaloCarta palo, int valorJuego) {
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

}
