package negocio;

import dao.ParejaDAO;
import dto.ParejaDTO;

public class Pareja {
	private Jugador jugador1;
	private Jugador jugador2;
	private int puntaje;
	
	
	public Pareja(Jugador jugador1, Jugador jugador2, int puntaje) {
		super();
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
		this.puntaje = puntaje;
	}
	
	public Jugador getJugador1() {
		return jugador1;
	}
	public void setJugador1(Jugador jugador1) {
		this.jugador1 = jugador1;
	}
	public Jugador getJugador2() {
		return jugador2;
	}
	public void setJugador2(Jugador jugador2) {
		this.jugador2 = jugador2;
	}
	public int getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	
	public boolean guardar(){
		return ParejaDAO.getInstancia().guardar(this);
	}
	
	public ParejaDTO toDTO() {
		return new ParejaDTO(jugador1.toDTO(), jugador2.toDTO(), puntaje);
	}
	
}
