package util;

import dto.ChicoDTO;
import dto.JugadorDTO;
import dto.PartidoDTO;
import negocio.Chico;
import negocio.Jugador;
import negocio.Partido;

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
					jugDTO.getPartidosJugados(),jugDTO.getPartidosGanados(),jugDTO.isConectado(),jugDTO.isJugando(),jugDTO.isBuscandoLibreIndividual(),jugDTO.getPassword());
			jugador.setId(jugDTO.getId());
		return jugador;
	}
	
	public Partido partidoDTOtoNegocio(PartidoDTO partDTO){
		Partido partido = null;
		return partido;
	}
	
	public Chico chicoDTOtoNegocio(ChicoDTO chicoDTO){
		Chico chicoBO = null;
		return chicoBO;
	}
}
