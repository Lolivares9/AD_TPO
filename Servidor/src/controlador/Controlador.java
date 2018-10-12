package controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dao.CartaDAO;
import dao.GrupoDAO;
import dao.JugadorDAO;
import dto.JugadorDTO;
import excepciones.CartaException;
import excepciones.GrupoException;
import excepciones.JugadorException;
import negocio.Carta;
import negocio.Grupo;
import negocio.Jugador;
import negocio.Mazo;
import util.DTOMapper;

public class Controlador {
	private static Controlador instancia;
	private static Random random;
	
	private Controlador() {
	}

	public static Controlador getInstancia() {
		if (instancia == null){
			instancia = new Controlador();
			random = new Random();
		}
		return instancia;
	}

	public void altaJugador(JugadorDTO jugador) throws JugadorException {
		//Valido apodo y mail, ambos deben estar libres
		boolean datosValidos = JugadorDAO.getInstancia().validarDatos(jugador.getApodo(), jugador.getMail());
		if(datosValidos){
			Jugador jug = DTOMapper.getInstancia().jugadorDTOtoNegocio(jugador);
			JugadorDAO.getInstancia().guardarJugador(jug);
		}else{
			throw new JugadorException("Apodo y/o mail ya registrado/s.");
		}
	}

	public boolean crearGrupo(String nombreGrupo, JugadorDTO jugadorAdmin) throws GrupoException, JugadorException {
		boolean valido = GrupoDAO.getInstancia().nombreGrupoValido(nombreGrupo);
		if(valido){
			Grupo g = new Grupo();
			g.setNombre(nombreGrupo);
			Jugador jug = JugadorDAO.getInstancia().buscarPorApodo(jugadorAdmin.getApodo());
			g.setJugadorAdmin(jug);
			GrupoDAO.getInstancia().guardar(g);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean llenarGrupo(String nombreGrupo, List<JugadorDTO> jugadores) throws GrupoException{
		Grupo g = GrupoDAO.getInstancia().buscarGrupo(nombreGrupo);
		if(g != null){
			List<Jugador> jugNeg = jugadores.stream().map(j -> DTOMapper.getInstancia().jugadorDTOtoNegocio(j)).collect(Collectors.toList());
			g.setJugadores(jugNeg);
			return g.guardar();
		}else{
			return false;
		}
	}

	public boolean iniciarSesion(JugadorDTO jug) throws JugadorException{
		Jugador jugador = JugadorDAO.getInstancia().buscarPorApodo(jug.getApodo());
		if(jugador.getApodo().equals(jug.getApodo()) && jugador.getPassword().equals(jug.getPassword())){
			return true;
		}
		return false;
	}

	public boolean iniciarPartidaLibreIndividual() {
		// TODO Auto-generated method stub
		return false;
	}
	

	public boolean iniciarPartidaLibre() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void cargarCartas() throws CartaException{
		Mazo m = new Mazo();
		m.setCartasDisponibles(CartaDAO.getInstancia().obtenerCartas());
	}

	public boolean repartiCartas() throws CartaException {
		//TODO Para qué se usa la clase Mazo? Por qué no se piden 12 cartas cada vez que se reparte?
		List<Carta> mazo = CartaDAO.getInstancia().obtenerCartas();
		List<Carta> cartasRepartir = new ArrayList<Carta>();
		Random rand = new Random();
		int pos = 0;
		while(cartasRepartir.size() < 12) {
			pos = rand.nextInt(mazo.size());
			cartasRepartir.add(mazo.remove(pos));			
		}
		// TODO Auto-generated method stub
		return false;
	}

}
