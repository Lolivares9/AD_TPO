package controlador;

import dao.GrupoDAO;
import dao.JugadorDAO;
import dto.GrupoDTO;
import dto.JugadorDTO;
import entities.GrupoEntity;
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
		Jugador jug = null;
		if(!JugadorDAO.getInstancia().existeJugador(jugador.getApodo())){
			jug = DTOMapper.getInstancia().jugadorDTOtoNegocio(jugador);
		}
			 JugadorDAO.getInstancia().guardarJugador(jug);
	}

	public boolean crearGrupo(String nombreGrupo, JugadorDTO jugadorAdmin) throws GrupoException{
		boolean valido = GrupoDAO.getInstancia().nombreGrupoValido(nombreGrupo);
		if(valido){
			Grupo g = new Grupo();
			g.setNombre(nombreGrupo);
			Jugador jug = DTOMapper.getInstancia().jugadorDTOtoNegocio(jugadorAdmin);
			g.setJugadorAdmin(jug);
			GrupoDAO.getInstancia().guardar(g);
			return true;
		}else{
			return false;
		}
	}

	public boolean iniciarSesion(JugadorDTO jug) throws JugadorException{
		Jugador jugador = DTOMapper.getInstancia().dtoJugadorToNegocio(jug);
		if(jugador.getApodo().equals(jug.getApodo()) && jugador.getPassword().equals(jug.getPassword())){
			return true;
		}
		return false;
	}
}
