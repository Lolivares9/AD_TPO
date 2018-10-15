package dto;

import java.io.Serializable;

import enums.Envite;

public class TurnoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1422007714923225296L;
	
	private BazaDTO baza;
	private JugadorDTO jugador;
	private Envite envite;
	private CartaDTO carta;
	
	public TurnoDTO(BazaDTO baza, JugadorDTO jugador, Envite envite, CartaDTO carta) {
		super();
		this.baza = baza;
		this.jugador = jugador;
		this.envite = envite;
		this.carta = carta;
	}
	
	public TurnoDTO(JugadorDTO jugador, Envite envite, CartaDTO carta) {
		super();
		this.jugador = jugador;
		this.envite = envite;
		this.carta = carta;
	}

	public BazaDTO getBazaDTO() {
		return baza;
	}
	public void setBazaDTO(BazaDTO baza) {
		this.baza = baza;
	}
	public JugadorDTO getJugadorDTO() {
		return jugador;
	}
	public void setJugadorDTO(JugadorDTO jugador) {
		this.jugador = jugador;
	}
	public Envite getEnvite() {
		return envite;
	}
	public void setEnvite(Envite envite) {
		this.envite = envite;
	}
	public CartaDTO getCartaDTO() {
		return carta;
	}
	public void setCartaDTO(CartaDTO carta) {
		this.carta = carta;
	}
	
	
	

}
