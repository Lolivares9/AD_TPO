package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import enums.PaloCarta;

@Entity
@Table(name="CARTAS")
public class CartaEntity {

	@Id
	@Column (name="ID_CARTA")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCarta;
	
	@Column(name="PALO")
	@Enumerated(EnumType.STRING)
	private PaloCarta palo;
	
	@Column(name="NUMERO")
	//TODO usar enum para numeros de cartas??
	private int numero;
	
	@Column(name="VALOR_JUEGO")
	private int valorJuego;

	public int getIdCarta() {
		return idCarta;
	}

	public void setIdCarta(int idCarta) {
		this.idCarta = idCarta;
	}

	public PaloCarta getPalo() {
		return palo;
	}

	public void setPalo(PaloCarta palo) {
		this.palo = palo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getValorJuego() {
		return valorJuego;
	}

	public void setValorJuego(int valorJuego) {
		this.valorJuego = valorJuego;
	}
	
}
