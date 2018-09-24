package negocio;

import java.util.List;

public class Mazo {
	private List<Carta> cartasDisponibles;

	public Mazo(List<Carta> cartasDisponibles) {
		super();
		this.cartasDisponibles = cartasDisponibles;
	}

	public List<Carta> getCartasDisponibles() {
		return cartasDisponibles;
	}

	public void setCartasDisponibles(List<Carta> cartasDisponibles) {
		this.cartasDisponibles = cartasDisponibles;
	}	
	
}
