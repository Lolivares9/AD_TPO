package controlador;

import dao.JugadorDAO;
import dto.JugadorDTO;
import excepciones.JugadorException;
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

	
}
