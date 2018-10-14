package util;

import dto.JugadorDTO;
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
	
	public Jugador jugadorDTOtoNegocio(JugadorDTO jugDTO) {
			Jugador jugador = new Jugador(jugDTO.getNombre(),jugDTO.getApodo(),jugDTO.getMail(),jugDTO.getCategoria(),jugDTO.getPuntaje(),
					jugDTO.getPartidosJugados(),jugDTO.getPartidosGanados(),jugDTO.isConectado(),jugDTO.isJugando(),jugDTO.getPassword());
			jugador.setId(jugDTO.getId());
		return jugador;
	}
}
