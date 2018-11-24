package dto;

import java.io.Serializable;

import enums.Envite;

public class TurnoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1422007714923225296L;
	private Integer idTurno;
	private Integer numTurno;
	private Integer idBaza;
	private JugadorDTO jugador;
	private Envite enviteTantos;
	private Envite enviteJuego;
	private Envite enviteActual;
	private CartaDTO carta;

	public TurnoDTO(Integer idTurno, JugadorDTO jugador, Envite enviteActual,  CartaDTO carta) {
		super();
		this.idTurno = idTurno;
		this.jugador = jugador;
		this.enviteActual = enviteActual;
		this.carta = carta;
	}
	public TurnoDTO(Integer idBaza, Integer numTurno, JugadorDTO jugador, Envite enviteActual,  CartaDTO carta) {
		super();
		this.setIdBaza(idBaza);
		this.setNumTurno(numTurno);
		this.jugador = jugador;
		this.enviteActual = enviteActual;
		this.carta = carta;
	} 
	public TurnoDTO(Integer idTurno, JugadorDTO jugador, Envite enviteTantos, Envite enviteJuego, CartaDTO carta) {
		super();
		this.idTurno = idTurno;
		this.jugador = jugador;
		this.enviteTantos = enviteTantos;
		this.enviteJuego = enviteJuego;
		this.carta = carta;
	}
	
	public TurnoDTO(Integer idBaza, Integer numTurno, JugadorDTO jugador,Envite enviteTantos, Envite enviteJuego, CartaDTO carta) {
		super();
		this.setIdBaza(idBaza);
		this.setNumTurno(numTurno);
		this.jugador = jugador;
		this.enviteTantos = enviteTantos;
		this.enviteJuego = enviteJuego;
		this.carta = carta;
	}
	
	public JugadorDTO getJugadorDTO() {
		return jugador;
	}
	public void setJugadorDTO(JugadorDTO jugador) {
		this.jugador = jugador;
	}

	public JugadorDTO getJugador() {
		return jugador;
	}

	public void setJugador(JugadorDTO jugador) {
		this.jugador = jugador;
	}

	public Envite getEnviteTantos() {
		return enviteTantos;
	}

	public void setEnviteTantos(Envite enviteTantos) {
		this.enviteTantos = enviteTantos;
	}

	public Envite getEnviteJuego() {
		return enviteJuego;
	}

	public void setEnviteJuego(Envite enviteJuego) {
		this.enviteJuego = enviteJuego;
	}

	public Envite getEnviteActual() {
		return enviteActual;
	}

	public void setEnviteActual(Envite enviteActual) {
		this.enviteActual = enviteActual;
	}

	public CartaDTO getCarta() {
		return carta;
	}

	public void setCarta(CartaDTO carta) {
		this.carta = carta;
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

	public Integer getNumTurno() {
		return numTurno;
	}

	public void setNumTurno(Integer numTurno) {
		this.numTurno = numTurno;
	}

	public Integer getIdBaza() {
		return idBaza;
	}

	public void setIdBaza(Integer idBaza) {
		this.idBaza = idBaza;
	}
}
