package negocio;

import java.util.List;

public class GrupoBO {

	private String nombre;
	private List<JugadorBO> jugadores;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<JugadorBO> getJugadores() {
		return jugadores;
	}
	public void setJugadores(List<JugadorBO> jugadores) {
		this.jugadores = jugadores;
	}
	
	
}
