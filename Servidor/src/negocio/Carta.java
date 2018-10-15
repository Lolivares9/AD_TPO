package negocio;

import dto.CartaDTO;
import enums.NumeroCarta;
import enums.PaloCarta;

public class Carta {
	private Integer idCarta;
	private NumeroCarta numero;
	private PaloCarta palo;
	private int valorJuego;
	
	public Carta(Integer idCarta, NumeroCarta numeroCarta, PaloCarta palo, int valorJuego) {
		super();
		this.idCarta = idCarta;
		this.numero = numeroCarta;
		this.palo = palo;
		this.valorJuego = valorJuego;
	}
	
	public NumeroCarta getNumero() {
		return numero;
	}
	public void setNumero(NumeroCarta numero) {
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
	
	public CartaDTO toDTO() {
		return new CartaDTO(this.numero, this.palo, this.valorJuego);
	}

	@Override
	public String toString() {
		return numero +" de "+palo;
	}
}
