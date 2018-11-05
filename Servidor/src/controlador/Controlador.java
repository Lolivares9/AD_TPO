package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dao.BazaDAO;
import dao.ChicoDAO;
import dao.GrupoDAO;
import dao.JugadorDAO;
import dao.ManoDAO;
import dao.ParejaDAO;
import dao.PartidoDAO;
import dao.TurnoDAO;
import dto.BazaDTO;
import dto.CartaDTO;
import dto.ChicoDTO;
import dto.JugadorDTO;
import dto.ManoDTO;
import dto.ParejaDTO;
import dto.PartidoDTO;
import dto.TurnoDTO;
import enums.Categoria;
import enums.EstadoPartido;
import enums.TipoModalidad;
import excepciones.BazaException;
import excepciones.CartaException;
import excepciones.ChicoException;
import excepciones.GrupoException;
import excepciones.JugadorException;
import excepciones.ManoException;
import excepciones.ParejaException;
import excepciones.PartidoException;
import excepciones.TurnoException;
import negocio.Baza;
import negocio.Carta;
import negocio.Chico;
import negocio.Grupo;
import negocio.Jugador;
import negocio.Mano;
import negocio.Mazo;
import negocio.Pareja;
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

	public JugadorDTO altaJugador(JugadorDTO jugador) throws JugadorException {
		boolean datosValidos = JugadorDAO.getInstancia().validarDatos(jugador.getApodo(), jugador.getMail());
		if(datosValidos){
			Jugador jug = DTOMapper.getInstancia().jugadorDTOtoNegocio(jugador);
			JugadorDAO.getInstancia().guardar(jug);
			return jug.toDTO();
		}else{
			return null;
		}
	}
	
	public JugadorDTO iniciarSesion(JugadorDTO jug) throws JugadorException{
		
		Jugador jugador = JugadorDAO.getInstancia().buscarPorApodo(jug.getApodo());
		boolean flag = jugador.iniciarSesion(jug.getApodo(), jug.getPassword());
		if (flag) {
			return jugador.toDTO();
		}
		else {
			return null;
		}
	}
	
	public boolean crearGrupo(String nombreGrupo, JugadorDTO jugadorAdmin) throws GrupoException, JugadorException {
		boolean valido = GrupoDAO.getInstancia().nombreGrupoValido(nombreGrupo);
		if(valido){
			Jugador jug = JugadorDAO.getInstancia().buscarPorApodo(jugadorAdmin.getApodo());
			List <Jugador> jugadores = new ArrayList<Jugador>();
			Grupo g = new Grupo(nombreGrupo, jug, jugadores);
			g.guardar();
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
		//Esto va directamente en el grupo?
	}

	public PartidoDTO iniciarPartidaLibreIndividual(String categ, String apodo) throws PartidoException, CartaException, JugadorException {
		Jugador jug = JugadorDAO.getInstancia().buscarPorApodo(apodo);
		List <Jugador> jugDisp = new ArrayList<Jugador>();
		List <Pareja> parejas = new ArrayList<Pareja>();
		
		jugDisp.add(jug);
		jugDisp = Jugador.completarJugadores(Categoria.valueOf(categ), jug.getApodo());
		
		if(jugDisp != null && jugDisp.size() ==  4 ){
			parejas = Pareja.distribuirParejas(jugDisp);
			
			Partido p =  new Partido(TipoModalidad.Libre_individual, parejas, null, new Date(), EstadoPartido.En_Proceso);
			p.guardar();
			Pareja.actualizarEstadoParejas(parejas);
		
			PartidoDTO pd = p.toDTO();
			repartiCartas(pd);
			return pd;
		}
		return null;
	}
	


	/**
	 * Entiendo que aca la pareja ya va a estar persistida (la que quiere jugar), por eso necesito su id
	 * @throws CartaException 
	 * */
	//OK, FALTARIA SETEARLE A LOS JUGADORES QUE JUGANDO = TRUE
	public Partido iniciarPartidaLibre(Pareja parej) throws ParejaException, CartaException {
		Partido part = null;
		List<Pareja> parejasDisponibles = ParejaDAO.getInstancia().buscarParejasLibres(parej);
		List<Pareja> parejasFinales = new ArrayList<Pareja>();
		parejasFinales.add(parej);
		Categoria categoriaBuscada = null;
		
		if(parejasDisponibles != null){
			if(parej.getJugador1().getCategoria().equals(Categoria.Calificado) || parej.getJugador2().getCategoria().equals(Categoria.Calificado)){
				categoriaBuscada = Categoria.Calificado;
			}
			if((parej.getJugador1().getCategoria().equals(Categoria.Experto) || parej.getJugador2().getCategoria().equals(Categoria.Experto)) && categoriaBuscada == null){
				categoriaBuscada = Categoria.Experto;
			}
			if((parej.getJugador1().getCategoria().equals(Categoria.Master) || parej.getJugador2().getCategoria().equals(Categoria.Master)) && categoriaBuscada == null){
				categoriaBuscada = Categoria.Master;
			}
			if((parej.getJugador1().getCategoria().equals(Categoria.Novato) || parej.getJugador2().getCategoria().equals(Categoria.Novato)) && categoriaBuscada == null){
				categoriaBuscada = Categoria.Novato;
			}
			for(int i = 0;i<parejasDisponibles.size();i++){
				if(parejasDisponibles.get(i).getJugador1().getCategoria().equals(categoriaBuscada) || parejasDisponibles.get(i).getJugador2().getCategoria().equals(categoriaBuscada)){
					parejasFinales.add(parejasDisponibles.get(i));
					part =  new Partido(TipoModalidad.Libre, parejasFinales, null, null, EstadoPartido.En_Proceso);
					return part;
				}
			}
		}
		
		return part;
	}

	public void repartiCartas(PartidoDTO pd) throws CartaException {
		mazo = new Mazo();
		List<ParejaDTO> parejas = pd.getParejaDTOs();
		List<CartaDTO> cartas =  mazo.repartiCartas().stream().map(Carta::toDTO).collect(Collectors.toList());
		//TODO meter esta logica en mazo
		for(int i = 1; i <= 3; i++){
			parejas.get(0).agregarCartaJug1(cartas.remove(0));
			parejas.get(0).agregarCartaJug2(cartas.remove(0));
			parejas.get(1).agregarCartaJug1(cartas.remove(0));
			parejas.get(1).agregarCartaJug2(cartas.remove(0));
		}
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
	 * @throws ParejaException 
	 * @throws PartidoException 
	 */
	public List<PartidoDTO> buscarPartidosJugados(JugadorDTO jugador, TipoModalidad mod, Date fechaInicial, Date fechaFin) throws ParejaException, PartidoException{
		List<Partido> partidos = PartidoDAO.getInstancia().buscarPartidosPorJugador(jugador.getId(), mod, fechaInicial, fechaFin);
		return partidos.stream().map(Partido::toDTOListar).collect(Collectors.toList());

	}
	
	/**
	 * Del partido seleccionado, 
	 * podrá ver quienes participaron y cuál fue el resultado y los puntajes obtenidos en cada una de las partidas que conformaron el juego.
	 * 
	 * @param partidoDTO  (recibe el partido que seleccionó del listado)
	 * @throws ChicoException 
	 */
	public List<ChicoDTO> buscarChicosPorPartido(PartidoDTO partidoDTO) throws ChicoException {
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

	
	public PartidoDTO buscarPartidaEnLobby(){
		
	}
	
	public void buscarPartidaLibreIndividual(JugadorDTO jugador) {
		Jugador j;
		try {
			j = JugadorDAO.getInstancia().buscarPorApodo(jugador.getApodo());
			j.setBuscandoLibreIndividual(true);
			j.guardar();
		} catch (JugadorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Pareja evaluarBaza () {
		return null;
	}
}
