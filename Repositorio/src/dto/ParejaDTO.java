package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParejaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8367212573800908537L;
	
	private JugadorDTO jugador1;
	private JugadorDTO jugador2;
	private List<CartaDTO> cartasJug1;
	private List<CartaDTO> cartasJug2;
	
	public ParejaDTO(JugadorDTO jugador1, JugadorDTO jugador2) {
		super();
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
	}
	
	public JugadorDTO getJugadorDTO1() {
		return jugador1;
	}
	public void setJugadorDTO1(JugadorDTO jugador1) {
		this.jugador1 = jugador1;
	}
	public JugadorDTO getJugadorDTO2() {
		return jugador2;
	}
	public void setJugadorDTO2(JugadorDTO jugador2) {
		this.jugador2 = jugador2;
	}

	public List<CartaDTO> getCartasJug1() {
		return cartasJug1;
	}

	public void setCartasJug1(List<CartaDTO> cartasJug1) {
		this.cartasJug1 = cartasJug1;
	}

	public List<CartaDTO> getCartasJug2() {
		return cartasJug2;
	}

	public void setCartasJug2(List<CartaDTO> cartasJug2) {
		this.cartasJug2 = cartasJug2;
	}
	
	public void agregarCartaJug1(CartaDTO c){
		if(this.cartasJug1 == null){
			this.cartasJug1 = new ArrayList<CartaDTO>();
		}
		this.cartasJug1.add(c);
	}
	
	public void agregarCartaJug2(CartaDTO c){
		if(this.cartasJug2 == null){
			this.cartasJug2 = new ArrayList<CartaDTO>();
		}
		this.cartasJug2.add(c);
	}
}
