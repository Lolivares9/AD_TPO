package controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dao.CartaDAO;
import dao.GrupoDAO;
import dao.JugadorDAO;
import dto.JugadorDTO;
import enums.Categoria;
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

	public boolean iniciarPartidaLibreIndividual(Categoria categ){
		/*
		  
		 LO ESTOY HACIENDO YO (MATI) 
		  
		 
		List<Jugador> jugDisp = new ArrayList<Jugador>();
		boolean completo = false;
		
		completo = completarJugadores(categ,jugDisp);
		//SI EN EL PRIMER CASO YA ME DEVOLVIO 0 SIGNIFICA QUE NO HAY NADIE DISPONIBLE
		if(jugDisp.size() == 0 && completo == false){
			return false;
		}
		else{
			//ACA HABRIA QUE IR BAJANDO DE CATEGORIA, YA QUE EN EL METODO VAMOS SUBIENDO DE CATEGORIA
		}
		
		return false;
		*/
		return false;
	}
	
	private boolean completarJugadores(Categoria categ,List<Jugador> jugDisp) {
		List<Jugador> jugadores = JugadorDAO.getInstancia().obtenerJugadoresPorCateg(categ);
		int i = 0;
		if(jugadores.size() <= 3 || jugadores == null){
			
			
			if((jugadores == null || jugadores.size() <= 3) && categ.equals("Novato")){
				if(jugadores.size() >= 1){
					//VOY A�ADIENDO LOS JUGADORES
					while(i < 3 && !jugadores.isEmpty() && jugDisp.size() < 4){
						jugDisp.add(jugadores.get(i));
						jugadores.remove(i);
						i++;
					}
					//YA COMPLETE, ENTONCES NO SIGO
					if(jugDisp.size() == 4){
						return true;
					}
					i = 0;
				}
				jugadores = JugadorDAO.getInstancia().obtenerJugadoresPorCateg(Categoria.Master);
			}
			
			
			if((jugadores == null || jugadores.size() <= 3) && categ.equals("Master")){
					if(jugadores.size() >= 1){
						while(i < 3 && !jugadores.isEmpty() && jugDisp.size() < 4){
							jugDisp.add(jugadores.get(i));
							jugadores.remove(i);
							i++;
						}
						if(jugDisp.size() == 4){
							return true;
						}
						i = 0;
					}
				jugadores = JugadorDAO.getInstancia().obtenerJugadoresPorCateg(Categoria.Experto);
			}
				
				
			if((jugadores == null || jugadores.size() <= 3) && categ.equals("Experto")){
					if(jugadores.size() >= 1){
						while(i < 3 && !jugadores.isEmpty() && jugDisp.size() < 4){
							jugDisp.add(jugadores.get(i));
							jugadores.remove(i);
							i++;
						}
						if(jugDisp.size() == 4){
							return true;
						}
						i = 0;
					}
				jugadores = JugadorDAO.getInstancia().obtenerJugadoresPorCateg(Categoria.Calificado);
			}
			
			
			if((jugadores == null || jugadores.size() <= 3) && categ.equals("Calificado")){
				if(jugadores.size() >= 1){
					while(i < 3 && !jugadores.isEmpty() && jugDisp.size() < 4){
						jugDisp.add(jugadores.get(i));
						jugadores.remove(i);
						i++;
					}
					if(jugDisp.size() == 4){
						return true;
					}
					i = 0;
				}
			}
			/*SI DESPUES DE TODOS ESTOS CASOS NO LLEGUE A POR LO MENOS UN JUGADOR, DEVUELVO FALSE. PORQUE SI NO ENCONTRE AL MENOS 1, 
			NO TENDRIA SENTIDO BUSCAR EN CATEGORIAS MAS BAJAS*/
			if(jugDisp.size() == 1){
				return false;
			}
		}
		else if(jugadores.size() >= 3){
			while(i < 3){
				jugDisp.add(jugadores.get(i));
				i++;
				}
		}
		return true;
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
		//TODO Para qu� se usa la clase Mazo? Por qu� no se piden 12 cartas cada vez que se reparte?
		List<Carta> mazo = CartaDAO.getInstancia().obtenerCartas();
		List<Carta> cartasRepartir = new ArrayList<Carta>();
		int pos = 0;
		while(cartasRepartir.size() < 12) {
			pos = random.nextInt(mazo.size());
			cartasRepartir.add(mazo.remove(pos));			
		}
		// TODO Auto-generated method stub
		return false;
	}

}
