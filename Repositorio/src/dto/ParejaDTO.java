package dto;

import java.io.Serializable;

public class ParejaDTO implements Serializable {

	private JugadorDTO jugador1;
	private JugadorDTO jugador2;
	private int puntaje;
	
	
	public ParejaDTO(JugadorDTO jugador1, JugadorDTO jugador2, int puntaje) {
		super();
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
		this.puntaje = puntaje;
	}
	
	public JugadorDTO getJugadorDTO1() {
		return jugador1;
	}
	public void setJugadorDTO1(JugadorDTO jugador1) {
		this.jugador1 = jugador1;
	}
	public JugadorDTO getJugadorDTO2() {
		return jugador2;
	}
	public void setJugadorDTO2(JugadorDTO jugador2) {
		this.jugador2 = jugador2;
	}
	public int getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	
	

}
