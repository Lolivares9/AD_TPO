package util;

import dto.JugadorDTO;
import excepciones.JugadorException;
import negocio.Jugador;

public class DTOMapper {

	private static DTOMapper instancia;

	public static DTOMapper getInstancia() {
		if (instancia == null)
			instancia = new DTOMapper();
		return instancia;
	}

	private DTOMapper() {
	}

	/*public Articulo dtoArticuloToNegocio(ArticuloDTO artDTO) throws ArticuloException {

		Articulo art = Articulo.getArticulo(artDTO.getCodigoBarra());
		return art;
	}*/
	
	public Jugador dtoJugadorToNegocio (JugadorDTO jugDTO) throws JugadorException {
		Jugador jug = Jugador.getJugador(jugDTO.getMail());
		return jug;
	}

	public Jugador jugadorDTOtoNegocio(JugadorDTO jugDTO) {
			Jugador jugador = new Jugador(jugDTO.getNombre(),jugDTO.getApodo(),jugDTO.getMail(),jugDTO.getCategoria(),jugDTO.getPuntaje(),
					jugDTO.getPartidosJugados(),jugDTO.getPartidosGanados(),jugDTO.isConectado(),jugDTO.isJugando(),jugDTO.getPassword());
		return jugador;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}