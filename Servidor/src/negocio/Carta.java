package negocio;

import enums.PaloCarta;

public class Carta {
	private Integer idCarta;
	private int numero;
	private PaloCarta palo;
	private int valorJuego;
	
	public Carta(Integer idCarta, int numero, PaloCarta palo, int valorJuego) {
		super();
		this.idCarta = idCarta;
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
	
	public Integer getIdCarta() {
		return idCarta;
	}

	public void setIdCarta(Integer idCarta) {
		this.idCarta = idCarta;
	}

}
