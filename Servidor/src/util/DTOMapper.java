package util;

import java.util.ArrayList;
import java.util.List;

import dao.CartaDAO;
import dao.GrupoDAO;
import dto.CartaDTO;
import dto.ChicoDTO;
import dto.GrupoDTO;
import dto.JugadorDTO;
import dto.PartidoDTO;
import dto.TurnoDTO;
import excepciones.GrupoException;
import negocio.Carta;
import negocio.Chico;
import negocio.Grupo;
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
	
	public Jugador jugadorDTOtoNegocio(JugadorDTO jugDTO) throws GrupoException {
		/*
		List<Grupo> gruposNegocio = new ArrayList<Grupo>();
		Grupo g = null;
		for(int i = 0;i<jugDTO.getGrupos().size();i++){
			g = GrupoDAO.getInstancia().buscarGrupo(jugDTO.getGrupos().get(i).getNombre());
			gruposNegocio.add(g);
		}*/
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

	public Turno turnoDTOtoNegocio(TurnoDTO turno) throws GrupoException {
		return new Turno(turno.getIdTurno(),DTOMapper.getInstancia().jugadorDTOtoNegocio(turno.getJugadorDTO()),turno.getEnviteTantos(), turno.getEnviteJuego(),cartaDTOtoNegocio(turno.getCartaDTO()));
	}
	
	public Turno FrontEndToNegocio(TurnoDTO turno) throws GrupoException {
		return new Turno(turno.getIdTurno(),null,turno.getEnviteTantos(), turno.getEnviteJuego(),DTOMapper.getInstancia().cartaDTOtoNegocio(turno.getCartaDTO()));
	}

	public List<GrupoDTO> gruposToDTO(List<Grupo> grupos) throws GrupoException {
		List<GrupoDTO> gruposDTO = new ArrayList<GrupoDTO>();
		if(grupos != null){
			for(int i =0;i<grupos.size();i++){
				gruposDTO.add(GrupoDAO.getInstancia().buscarGrupo(grupos.get(i).getNombre()).toDTO());
			}
		}
		return gruposDTO;
	}
}
