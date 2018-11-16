package dto;

import java.io.Serializable;

import enums.PaloCarta;

public class CartaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4114646579760712497L;
	
	private int IdCarta;
	private int numero;
	private PaloCarta palo;
	private int valorJuego;
	
	public CartaDTO() {
		super();
	}
	
	public CartaDTO(int numero, PaloCarta palo, int valorJuego) {
		super();
		this.numero = numero;
		this.palo = palo;
		this.valorJuego = valorJuego;
	}
	
	public CartaDTO(Integer idCarta, int numero, PaloCarta palo, int valorJuego) {
		super();
		this.IdCarta = idCarta;
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

	public int getIdCarta() {
		return IdCarta;
	}

	public void setIdCarta(int idCarta) {
		IdCarta = idCarta;
	}

}
