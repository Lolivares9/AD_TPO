package negocio;

import java.util.ArrayList;
import java.util.List;

import dao.GrupoDAO;
import dto.GrupoDTO;
import dto.JugadorDTO;

public class Grupo {
	private Integer idGrupo;
	private String nombre;
	private Jugador jugadorAdmin;
	private List<Jugador> jugadores;
	
	public Grupo() {
		super();
	}

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
	
	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}
	
	public boolean guardar(){
		return GrupoDAO.getInstancia().guardar(this);
	}

	public void agregarJugador(Jugador jugador) {
		if(this.jugadores == null) {
			this.jugadores = new ArrayList<Jugador>();
		}
		this.jugadores.add(jugador);
	}
	
	public void eliminarJugador(Jugador jugador) {
		this.jugadores.remove(jugador);
	}
	
	public GrupoDTO toDTO(){
		List<JugadorDTO> jugadoresDTO = new ArrayList<JugadorDTO>();
		GrupoDTO grupoDTO = null;
		JugadorDTO adminDTO = jugadorAdmin.toDTO();
		for(int i = 0;i<jugadores.size();i++){
			jugadoresDTO.add(jugadores.get(i).toDTO());
		}
		grupoDTO =  new GrupoDTO(this.nombre,adminDTO,jugadoresDTO);
		grupoDTO.setIdGrupo(this.getIdGrupo());
		
		return grupoDTO;
	}
	
	public void añadirJugador(Jugador j){
		this.jugadores.add(j);
	}
}
