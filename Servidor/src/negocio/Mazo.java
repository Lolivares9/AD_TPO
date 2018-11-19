package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dao.CartaDAO;
import dto.CartaDTO;
import dto.ParejaDTO;
import dto.PartidoDTO;
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
	public void repartiCartas(PartidoDTO pd) throws CartaException {
		List<ParejaDTO> parejas = pd.getParejaDTOs();
		Pareja parejaNegocio1 = new Pareja(parejas.get(0).getIdPareja(),null,null);
		List<Carta> cartasJugador1Pareja1 = new ArrayList<Carta>();
		List<Carta> cartasJugador2Pareja1 = new ArrayList<Carta>();
		List<Carta> cartasJugador1Pareja2 = new ArrayList<Carta>();
		List<Carta> cartasJugador2Pareja2 = new ArrayList<Carta>();
		Carta c = new Carta();
		Pareja parejaNegocio2 = new Pareja(parejas.get(1).getIdPareja(),null,null);
		List<CartaDTO> cartas =  repartiCartas().stream().map(Carta::toDTO).collect(Collectors.toList());
		for(int i = 1; i <= 3; i++){
			c = new Carta();
			c.setIdCarta(cartas.get(0).getIdCarta());
			cartasJugador1Pareja1.add(c);
			parejas.get(0).agregarCartaJug1(cartas.remove(0));
			
			c = new Carta();
			c.setIdCarta(cartas.get(0).getIdCarta());
			cartasJugador2Pareja1.add(c);
			parejas.get(0).agregarCartaJug2(cartas.remove(0));
			
			c = new Carta();
			c.setIdCarta(cartas.get(0).getIdCarta());
			cartasJugador1Pareja2.add(c);
			parejas.get(1).agregarCartaJug1(cartas.remove(0));
			
			c = new Carta();
			c.setIdCarta(cartas.get(0).getIdCarta());
			cartasJugador2Pareja2.add(c);
			parejas.get(1).agregarCartaJug2(cartas.remove(0));
			
		}
		parejaNegocio1.setCartasJugador1(cartasJugador1Pareja1);
		parejaNegocio1.setCartasJugador2(cartasJugador2Pareja1);
		parejaNegocio2.setCartasJugador1(cartasJugador1Pareja2);
		parejaNegocio2.setCartasJugador2(cartasJugador2Pareja2);
		CartaDAO.getInstancia().saveCartasParejas(parejaNegocio1);
		CartaDAO.getInstancia().saveCartasParejas(parejaNegocio2);
	}
	
	
}


