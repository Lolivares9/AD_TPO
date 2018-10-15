package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dao.BazaDAO;
import dao.ChicoDAO;
import dao.GrupoDAO;
import dao.JugadorDAO;
import dao.ManoDAO;
import dao.PartidoDAO;
import dao.TurnoDAO;
import dto.BazaDTO;
import dto.CartaDTO;
import dto.ChicoDTO;
import dto.JugadorDTO;
import dto.ManoDTO;
import dto.PartidoDTO;
import dto.TurnoDTO;
import enums.Categoria;
import enums.TipoModalidad;
import excepciones.BazaException;
import excepciones.CartaException;
import excepciones.ChicoException;
import excepciones.GrupoException;
import excepciones.JugadorException;
import excepciones.ManoException;
import excepciones.TurnoException;
import negocio.Baza;
import negocio.Carta;
import negocio.Chico;
import negocio.Grupo;
import negocio.Jugador;
import negocio.Mano;
import negocio.Mazo;
import negocio.Partido;
import negocio.Turno;
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

	/**
	 * Traer lista de partidos jugados, se puede filtar por modalidad y/o fecha
	 * En el DTO no van a estar cargados todas las variables, porque luego puede
	 * ir al detalle de un partido seleccionado (esto reduce la carga de proceso)
	 * 
	 * @param jugador
	 * @param mod
	 * @param fecha
	 * @return List<PartidoDTO>
	 * Los DTO tendrán la modalidad y la pareja ganadora
	 */
	public List<PartidoDTO> listarPartidos(JugadorDTO jugador, TipoModalidad mod, Date fecha){
		List<PartidoDTO> partidosDTO = new ArrayList<PartidoDTO>();
		if(mod == null && fecha == null) {
			List<Partido> partidos = PartidoDAO.getInstancia().buscarPartidosPorJugador(jugador.getId());
			return partidos.stream().map(Partido::toDTOListar).collect(Collectors.toList());
		}else {
			//TODO traer partidos filtrando, validar si eligió modalidad y/o fecha
		}
		return partidosDTO;
	}
	
	/**
	 * Del partido seleccionado, 
	 * podrá ver quienes participaron y cuál fue el resultado y los puntajes obtenidos en cada una de las partidas que conformaron el juego.
	 * 
	 * @param partidoDTO  (recibe el partido que seleccionó del listado)
	 * @throws ChicoException 
	 */
	public List<ChicoDTO> listarChicosPorPartido(PartidoDTO partidoDTO) throws ChicoException {
		List<Chico> c = ChicoDAO.getInstancia().buscarChicosPorPartido(partidoDTO.getIdPartido());
		return c.stream().map(Chico::toDTO).collect(Collectors.toList());
	}
	
	/**
	 * Si lo desea podrá seleccionar una de las partidas y ver su desarrollo completo mano por mano, 
	 * donde figuraran las cartas jugadas por cada jugador, los envites realizados y su resultado
	 * 
	 * @param chicoDTO
	 * @return
	 * @throws ManoException 
	 * @throws BazaException 
	 * @throws TurnoException 
	 */
	public Map<ManoDTO,Map<BazaDTO,List<TurnoDTO>>> obtenerDetalleDeChico(ChicoDTO chicoDTO) throws ManoException, BazaException, TurnoException {
		//TODO buscar las manos -> las bazas  -> los turnos  
		Map<ManoDTO,Map<BazaDTO,List<TurnoDTO>>> detallePartida = new HashMap<ManoDTO, Map<BazaDTO, List<TurnoDTO>>>();
		List<Mano> manos = ManoDAO.getInstancia().buscarManosPorChico(chicoDTO.getIdChico());
		for(Mano m : manos) {
			System.out.println("\nMano Nº "+m.getNumeroMano() + 
					" Ganada por " +m.getParejaGanadora().getJugador1().getApodo() + " y "+ m.getParejaGanadora().getJugador2().getApodo());
			
			Map<BazaDTO,List<TurnoDTO>> detalleMano = new HashMap<BazaDTO, List<TurnoDTO>>();
			
			List<Baza> bazas = BazaDAO.getInstancia().buscarBazasPorMano(m.getIdMano());
			for(Baza b : bazas) {
				System.out.println("\nBaza Nª "+b.getNumero()+ " ganada por "+b.getGanadores().getJugador1().getApodo()+" y "+b.getGanadores().getJugador2().getApodo());
				System.out.println("Puntos: "+ b.getPuntajePareja1() + " y " +b.getPuntajePareja2());
				List<Turno> turnos = TurnoDAO.getInstancia().buscarTurnosPorBaza(b.getIdBaza());
				
				for(Turno t : turnos) {
					System.out.println(t.getJugador().getApodo()+ " cantó "+t.getEnvite()+ " y jugó un "+ t.getCarta().toString());
				}
				detalleMano.put(b.toDTO(), turnos.stream().map(Turno::toDTO).collect(Collectors.toList()));
			}
			
			detallePartida.put(m.toDTO(), detalleMano);
		}
		
		return detallePartida;
	}

	/*
	 * Modifiqué la tabla de partido_pareja, para poder hacer la relación con hibernate. Subo el script corregido  
	 *  
	 * Hice varias modificaciones en la base para traer los datos que requeria por pasos sin tener que hacer consultas grandes, actualicé el script
	 * 
	 * ManoEntity tenia una lista de chicos, y en realidad un chico esta compuesto por manos
	 * 
	 * Para qué esta el puntaje en la tabla de parejas?, las parejas se eliminan despues de que juegan, pero nosotros las podriamos reusar
	 * si se repite una pareja, pero hay que sacarle la columna de puntaje para eso.
	 * 
	 * Como vamos a marcar un empate en la tabla bazas (tiene una columna ganadores_baza), le ponemos 0?
	 * 
	 * Como vamos a representar en la tabla de turnos cuando se cante por ejemplo Truco-Re truco- Quiero? 
	 * (todo sucede en un mismo turno) repetimos el id y despues lo unimos??
	 * 
	 * Al enum de modalidad se le podria agregar si es individual o no??
	 */
	
	
}
