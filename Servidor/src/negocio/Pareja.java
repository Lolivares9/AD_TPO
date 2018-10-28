package negocio;

import dao.ParejaDAO;
import dto.ParejaDTO;

public class Pareja {
	private Integer idPareja;
	private Jugador jugador1;
	private Jugador jugador2;
	
	public Pareja(Jugador jugador1, Jugador jugador2) {
		super();
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
	}
	
	public Pareja(Integer idPareja, Jugador negocio, Jugador negocio2) {
		this.idPareja = idPareja;
		this.jugador1 = negocio;
		this.jugador2 = negocio2;
	}

	public Jugador getJugador1() {
		return jugador1;
	}
	public void setJugador1(Jugador jugador1) {
		this.jugador1 = jugador1;
	}
	public Jugador getJugador2() {
		return jugador2;
	}
	public void setJugador2(Jugador jugador2) {
		this.jugador2 = jugador2;
	}	
	public Integer getIdPareja() {
		return idPareja;
	}

	public void setIdPareja(Integer idPareja) {
		this.idPareja = idPareja;
	}
	
	public boolean guardar(){
		return ParejaDAO.getInstancia().guardar(this);
	}
	
	public ParejaDTO toDTO() {
		return new ParejaDTO(jugador1.toDTO(), jugador2.toDTO());
	}
	
}
