package negocio;

import java.util.List;

import dao.CartaDAO;
import dao.GrupoDAO;

public class Grupo {
	private String nombre;
	private Jugador jugadorAdmin;
	private List<Jugador> jugadores;
	
	
	public Grupo(String nombre, Jugador jugadorAdmin, List<Jugador> jugadores) {
		super();
		this.nombre = nombre;
		this.jugadorAdmin = jugadorAdmin;
		this.jugadores = jugadores;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Jugador getJugadorAdmin() {
		return jugadorAdmin;
	}
	public void setJugadorAdmin(Jugador jugadorAdmin) {
		this.jugadorAdmin = jugadorAdmin;
	}
	public List<Jugador> getJugadores() {
		return jugadores;
	}
	public void setJugadores(List<Jugador> jugadores) {
		this.jugadores = jugadores;
	}
	
	public boolean guardar(){
		return GrupoDAO.getInstancia().guardar(this);
	}
}
