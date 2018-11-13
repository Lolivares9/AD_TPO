package dto;

import java.io.Serializable;

import enums.Envite;

public class TurnoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1422007714923225296L;
	private Integer idTurno;
	private JugadorDTO jugador;
	private Envite envite;
	private CartaDTO carta;

	public TurnoDTO(Integer idTurno, JugadorDTO jugador, Envite envite, CartaDTO carta) {
		super();
		this.idTurno = idTurno;
		this.jugador = jugador;
		this.envite = envite;
		this.carta = carta;
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

	public Integer getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(Integer idTurno) {
		this.idTurno = idTurno;
	}
}
