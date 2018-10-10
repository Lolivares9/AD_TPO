package controlador;

import java.util.List;
import java.util.stream.Collectors;

import dao.GrupoDAO;
import dao.JugadorDAO;
import dto.JugadorDTO;
import excepciones.GrupoException;
import excepciones.JugadorException;
import negocio.Grupo;
import negocio.Jugador;
import util.DTOMapper;

public class Controlador {
	private static Controlador instancia;
	
	private Controlador() {
	}

	public static Controlador getInstancia() {
		if (instancia == null)
			instancia = new Controlador();
		return instancia;
	}

	public void altaJugador(JugadorDTO jugador) throws JugadorException {
		Jugador jug = JugadorDAO.getInstancia().buscarPorApodo(jugador.getApodo());
		if(jug == null){
			jug = DTOMapper.getInstancia().jugadorDTOtoNegocio(jugador);
			JugadorDAO.getInstancia().guardarJugador(jug);
		}			 
	}

	public boolean crearGrupo(String nombreGrupo, JugadorDTO jugadorAdmin) throws GrupoException, JugadorException {
		boolean valido = GrupoDAO.getInstancia().nombreGrupoValido(nombreGrupo);
		if(valido){
			Grupo g = new Grupo();
			g.setNombre(nombreGrupo);
			Jugador jug = JugadorDAO.getInstancia().buscarPorApodo(jugadorAdmin.getApodo());
			g.setJugadorAdmin(jug);
			GrupoDAO.getInstancia().guardar(g);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean llenarGrupo(String nombreGrupo, List<JugadorDTO> jugadores) throws GrupoException{
		Grupo g = GrupoDAO.getInstancia().buscarGrupo(nombreGrupo);
		if(g != null){
			List<Jugador> jugNeg = jugadores.stream().map(j -> DTOMapper.getInstancia().jugadorDTOtoNegocio(j)).collect(Collectors.toList());
			g.setJugadores(jugNeg);
			return g.guardar();
		}else{
			return false;
		}
	}

	public boolean iniciarSesion(JugadorDTO jug) throws JugadorException{
		Jugador jugador = JugadorDAO.getInstancia().buscarPorApodo(jug.getApodo());
		if(jugador.getApodo().equals(jug.getApodo()) && jugador.getPassword().equals(jug.getPassword())){
			return true;
		}
		return false;
	}

	public boolean iniciarPartidaLibreIndividual() {
		// TODO Auto-generated method stub
		return false;
	}
	

	public boolean iniciarPartidaLibre() {
		// TODO Auto-generated method stub
		return false;
	}

}
