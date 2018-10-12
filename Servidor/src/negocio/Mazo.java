package negocio;

import java.util.List;
import java.util.Map;

public class Mazo {
	private List<Carta> cartasDisponibles;
	private Map<Integer,List<Carta>> cartasRepartir;

	public Mazo() {
		super();
	}

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

	public Map<Integer,List<Carta>> getCartasRepartir() {
		return cartasRepartir;
	}

	public void setCartasRepartir(Map<Integer,List<Carta>> cartasRepartir) {
		this.cartasRepartir = cartasRepartir;
	}	
	
}
