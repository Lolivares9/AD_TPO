package negocio;

import dao.TurnoDAO;
import dto.TurnoDTO;
import enums.Envite;
import util.DTOMapper;

public class Turno {
	private Integer idTurno;
	private Jugador jugador;
	private Envite envite;
	private Carta carta;
	
	public Turno(Jugador jugador, Envite envite, Carta carta) {
		super();
		this.jugador = jugador;
		this.envite = envite;
		this.carta = carta;
	}
	
	public Turno(Integer idTurno,Jugador jugador, Envite envite, Carta carta) {
		super();
		this.idTurno = idTurno;
		this.jugador = jugador;
		this.envite = envite;
		this.carta = carta;
	}

	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	public Envite getEnvite() {
		return envite;
	}
	public void setEnvite(Envite envite) {
		this.envite = envite;
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
	
	public TurnoDTO toDTO() {
		return new TurnoDTO(idTurno, jugador.toDTO(), envite, carta.toDTO());
	}
	
	public Turno toNegocio(TurnoDTO turno){
		return new Turno(turno.getIdTurno(),DTOMapper.getInstancia().jugadorDTOtoNegocio(turno.getJugadorDTO()),turno.getEnvite(),DTOMapper.getInstancia().cartaDTOtoNegocio(turno.getCartaDTO()));
	}
}
