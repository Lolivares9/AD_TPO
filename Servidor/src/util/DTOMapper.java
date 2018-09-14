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
		Jugador jug = Jugador.getJugador (jugDTO.getMail());
		return jug;
	}
}
