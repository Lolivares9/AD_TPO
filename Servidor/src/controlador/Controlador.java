package controlador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import dao.GrupoDAO;
import dao.JugadorDAO;
import dao.ParejaDAO;
import dto.CartaDTO;
import dto.JugadorDTO;
import entities.ParejaEntity;
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
	private static Mazo mazo;
	
	private Controlador() {
	}

	public static Controlador getInstancia(){
		if (instancia == null){
			instancia = new Controlador();
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
		resultadoPartido();
		Jugador jugador = JugadorDAO.getInstancia().buscarPorApodo(jug.getApodo());
		if(jugador.getApodo().equals(jug.getApodo()) && jugador.getPassword().equals(jug.getPassword())){
			return true;
		}
		return false;
	}

	public boolean iniciarPartidaLibreIndividual(Categoria categ,Jugador jug){
		
		 
		List<Jugador> jugDisp = new ArrayList<Jugador>();
		jugDisp.add(jug);
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
		
	}
	
	@SuppressWarnings({ "unused" })
	private boolean completarJugadores(Categoria categ,List<Jugador> jugDisp) {
		List<Jugador> jugadores = JugadorDAO.getInstancia().getAllJugadores();
		
		HashSet<Jugador> novatos = new HashSet<>();
		HashSet<Jugador> masters = new HashSet<>();
		HashSet<Jugador> expertos = new HashSet<>();
		HashSet<Jugador> calificados = new HashSet<>();
		
		for(int i = 0;i<jugadores.size();i++){
			if(jugadores.get(i).getCategoria().equals("Novato")){
				novatos.add(jugadores.get(i));
			}
			if(jugadores.get(i).getCategoria().equals("Master")){
				novatos.add(jugadores.get(i));
			}
			if(jugadores.get(i).getCategoria().equals("Experto")){
				novatos.add(jugadores.get(i));
			}
			if(jugadores.get(i).getCategoria().equals("Calificado")){
				novatos.add(jugadores.get(i));
			}
		}
		
		if(novatos.size() >= 3 && categ.equals("Novatos")){
			Iterator<Jugador> i = novatos.iterator(); 
			while(!novatos.isEmpty()){
				jugDisp.add((Jugador) novatos.iterator());
				novatos.remove(i);
				i.next();
			}
		}
		
		
		return false;
	}

	public boolean iniciarPartidaLibre() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<CartaDTO> repartiCartas() throws CartaException {
		mazo = new Mazo();
		return (mazo.repartiCartas().stream().map(Carta::toDTO).collect(Collectors.toList()));
	}
	
	public void resultadoPartido() {
		//FACU
		//Estoy probando con datos insertados a mano traer todos los resultados de un partido
		//ENCONTRE MUCHOS ERRORES --> LEER COMENTARIO ABAJO
		List<ParejaEntity> idParejas = ParejaDAO.getInstancia().buscarParejasPorJugador();
		System.out.println(idParejas.get(0).getPartidos());
	}

	/*
	 * Modifiqué la tabla de partido_pareja, para poder hacer la relación con hibernate. Subo el script corregido  
	 *  
	 * La tabla partidos tiene una columna id_chico que no tiene sentido, se estarian repitiendo los datos por cada chico dentro de la partida,
	 * propongo poner un id_partido en la tabla id_chico
	 * 
	 * ManoEntity tenia una lista de chicos, y en realidad una mano esta compuesta por chicos
	 * 
	 * Para que esta el puntaje en la tabla de parejas?, las parejas se eliminan despues de que juegan, pero nosotros las podriamos reusar
	 * si se repite una pareja, pero hay que sacarle la columna de puntaje para eso.
	 * 
	 * Como vamos a marcar un empate en la tabla bazas (tiene una columna ganadores_baza), le ponemos 0?
	 * 
	 * Como vamos a representar en la tabla de turnos cuando se cante por ejemplo Truco-Re truco- Quiero? 
	 * (todo sucede en un mismo turno) repetimos el id y despues lo unimos??
	 * 
	 * 
	 */
	
	
}
