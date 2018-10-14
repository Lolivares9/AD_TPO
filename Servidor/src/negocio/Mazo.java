package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import dao.CartaDAO;
import excepciones.CartaException;

public class Mazo {
	private List<Carta> mazo;

	public Mazo() throws CartaException {
		super();
		this.mazo = CartaDAO.getInstancia().obtenerCartas();
	}
	
	public List<Carta> repartiCartas() throws CartaException {
		Random rand = new Random();
		//Mezclo el mazo primero
		Collections.shuffle(mazo, rand);
		List<Carta> cartasRepartir = new ArrayList<Carta>();
		//Reparto las primeras 12 cartas
		for (int i = 0; cartasRepartir.size() < 12; i++) {
			cartasRepartir.add(mazo.remove(i));
		}
		return cartasRepartir;
	}
}
