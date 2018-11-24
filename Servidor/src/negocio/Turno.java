package negocio;

import dao.TurnoDAO;
import dto.TurnoDTO;
import enums.Envite;
import excepciones.GrupoException;
import util.DTOMapper;

public class Turno {
	private Integer idTurno;
	private Jugador jugador;
	private Envite enviteTantos;
	private Envite enviteJuego;
	private Carta carta;
	
	public Turno(Jugador jugador, Envite enviteTantos,Envite enviteJuego, Carta carta) {
		super();
		this.jugador = jugador;
		this.enviteTantos = enviteTantos;
		this.enviteJuego = enviteJuego;
		this.carta = carta;
	}
	
	public Turno(Integer idTurno,Jugador jugador, Envite enviteTantos,Envite enviteJuego, Carta carta) {
		super();
		this.idTurno = idTurno;
		this.jugador = jugador;
		this.enviteTantos = enviteTantos;
		this.enviteJuego = enviteJuego;
		this.carta = carta;
	}

	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public Envite getEnviteTantos() {
		return enviteTantos;
	}

	public void setEnviteTantos(Envite enviteTantos) {
		this.enviteTantos = enviteTantos;
	}

	public Envite getEnviteJuego() {
		return enviteJuego;
	}

	public void setEnviteJuego(Envite enviteJuego) {
		this.enviteJuego = enviteJuego;
	}

	public Carta getCarta() {
		return carta;
	}
	public void setCarta(Carta carta) {
		this.carta = carta;
	}
	
	public Integer getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(Integer idTurno) {
		this.idTurno = idTurno;
	}

	public boolean guardar(){
		return TurnoDAO.getInstancia().guardar(this);
	}
	
	public TurnoDTO toDTO() throws GrupoException {
		if(carta != null) {
			return new TurnoDTO(idTurno, jugador.toDTO(), enviteTantos, enviteJuego, carta.toDTO());
		}else {
			return new TurnoDTO(idTurno, jugador.toDTO(), enviteTantos, enviteJuego, null);
		}
	}
	
	public Turno toNegocio(TurnoDTO turno) throws GrupoException{
		return new Turno(turno.getIdTurno(),DTOMapper.getInstancia().jugadorDTOtoNegocio(turno.getJugadorDTO()),turno.getEnviteTantos(), turno.getEnviteJuego(),DTOMapper.getInstancia().cartaDTOtoNegocio(turno.getCartaDTO()));
	}

	public void setearEnviteActual(Envite enviteActual) {
		if (enviteActual.toString().contains("Envido")) {
			this.setEnviteTantos(enviteActual);
		}
		else {
			this.setEnviteJuego(enviteActual);
		}
	}
}
