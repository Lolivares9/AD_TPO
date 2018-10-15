package negocio;

import dao.TurnoDAO;
import dto.TurnoDTO;
import enums.Envite;

public class Turno {
	private Baza baza;
	private Jugador jugador;
	private Envite envite;
	private Carta carta;
	
	public Turno(Baza baza, Jugador jugador, Envite envite, Carta carta) {
		super();
		this.baza = baza;
		this.jugador = jugador;
		this.envite = envite;
		this.carta = carta;
	}
	
	public Turno(Jugador jugador, Envite envite, Carta carta) {
		super();
		this.jugador = jugador;
		this.envite = envite;
		this.carta = carta;
	}

	public Baza getBaza() {
		return baza;
	}
	public void setBaza(Baza baza) {
		this.baza = baza;
	}
	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	public Envite getEnvite() {
		return envite;
	}
	public void setEnvite(Envite envite) {
		this.envite = envite;
	}
	public Carta getCarta() {
		return carta;
	}
	public void setCarta(Carta carta) {
		this.carta = carta;
	}
	
	public boolean guardar(){
		return TurnoDAO.getInstancia().guardar(this);
	}
	
	public TurnoDTO toDTO() {
		return new TurnoDTO(jugador.toDTO(), envite, carta.toDTO());
	}
}
