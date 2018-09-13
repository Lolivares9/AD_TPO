package dto;

import java.util.List;

public class GrupoDTO {

	private String nombre;
	private JugadorDTO jugadorAdmin;
	private List<JugadorDTO> jugadores;
	
	
	public GrupoDTO(String nombre, JugadorDTO jugadorAdmin, List<JugadorDTO> jugadores) {
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
	public JugadorDTO getJugadorAdmin() {
		return jugadorAdmin;
	}
	public void setJugadorAdmin(JugadorDTO jugadorAdmin) {
		this.jugadorAdmin = jugadorAdmin;
	}
	public List<JugadorDTO> getJugadores() {
		return jugadores;
	}
	public void setJugadores(List<JugadorDTO> jugadores) {
		this.jugadores = jugadores;
	}

}
