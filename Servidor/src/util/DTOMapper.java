package util;

import dao.CartaDAO;
import dto.CartaDTO;
import dto.ChicoDTO;
import dto.JugadorDTO;
import dto.PartidoDTO;
import dto.TurnoDTO;
import negocio.Carta;
import negocio.Chico;
import negocio.Jugador;
import negocio.Partido;
import negocio.Turno;

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
	
	public Carta cartaDTOtoNegocio(CartaDTO cartaDTO) {
		Carta c = null;
		if(cartaDTO != null){
			c = CartaDAO.getInstancia().obtenerCartaPorID(cartaDTO.getIdCarta()); 
		}
		return c;
	}

	public Turno turnoDTOtoNegocio(TurnoDTO turno) {
		return new Turno(turno.getIdTurno(),DTOMapper.getInstancia().jugadorDTOtoNegocio(turno.getJugadorDTO()),turno.getEnvite(),cartaDTOtoNegocio(turno.getCartaDTO()));
	}
}
