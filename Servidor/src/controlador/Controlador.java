package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dao.BazaDAO;
import dao.CartaDAO;
import dao.ChicoDAO;
import dao.GrupoDAO;
import dao.JugadorDAO;
import dao.ManoDAO;
import dao.ParejaDAO;
import dao.PartidoDAO;
import dao.TurnoDAO;
import dto.BazaDTO;
import dto.ChicoDTO;
import dto.JugadorDTO;
import dto.ManoDTO;
import dto.PartidoDTO;
import dto.TurnoDTO;
import enums.Categoria;
import enums.Envite;
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

	public JugadorDTO altaJugador(JugadorDTO jugador) throws JugadorException, GrupoException {
		boolean datosValidos = JugadorDAO.getInstancia().validarDatos(jugador.getApodo(), jugador.getMail());
		if(datosValidos){
			Jugador jug = DTOMapper.getInstancia().jugadorDTOtoNegocio(jugador);
			JugadorDAO.getInstancia().guardar(jug);
			return jug.toDTO();
		}else{
			return null;
		}
	}
	
	public JugadorDTO iniciarSesion(JugadorDTO jug) throws JugadorException, GrupoException{
		
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
			jugadores.add(jug);
			Grupo g = new Grupo(nombreGrupo, jug, jugadores);
			g.guardar();
			return true;
		}else{
			return false;
		}
	}
	
	public boolean ingresarNuevosMiembros(String nombreGrupo, List<JugadorDTO> jugadores) throws GrupoException, JugadorException{
		List<Jugador> jugadoresNegocio = new ArrayList<Jugador>();
		Grupo g = GrupoDAO.getInstancia().buscarGrupo(nombreGrupo);
		if(g != null){
			for(int i = 0;i<jugadores.size();i++){
				jugadoresNegocio.add(JugadorDAO.getInstancia().buscarPorApodo(jugadores.get(i).getApodo()));
				g.añadirJugador(jugadoresNegocio.get(i));
			}
			return g.guardar();
		}
		
		return false;
	}

	public PartidoDTO iniciarPartidaLibreIndividual(String categ, String apodo) throws PartidoException, CartaException, JugadorException, GrupoException {
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
			mazo = new Mazo();
			mazo.repartiCartas(p);
			
			PartidoDTO pd = p.toDTO();
			
			
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
		List<Partido> partidos = PartidoDAO.getInstancia().buscarPartidosPorJugador(jugador.getId(), mod, fechaInicial, fechaFin, null);
		return partidos.stream().map(t -> {
			try {
				return t.toDTOListar();
			} catch (GrupoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());

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
		return c.stream().map(t -> {
			try {
				return t.toDTO();
			} catch (GrupoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
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
	 * @throws GrupoException 
	 */
	public Map<ManoDTO,Map<BazaDTO,List<TurnoDTO>>> obtenerDetalleDeChico(ChicoDTO chicoDTO) throws ManoException, BazaException, TurnoException, GrupoException {
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
					System.out.println(t.getJugador().getApodo()+  (!t.getEnviteTantos().equals(Envite.Nada) ? " cantó " + t.getEnviteTantos() + " y " : " ") + (!t.getEnviteJuego().equals(Envite.Nada) ? " cantó "  + t.getEnviteJuego() + " y ": " ") + " jugó un "+ t.getCarta().toString());
				}
				detalleMano.put(b.toDTO(), turnos.stream().map(t -> {
					try {
						return t.toDTO();
					} catch (GrupoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}).collect(Collectors.toList()));
			}
			
			detallePartida.put(m.toDTO(), detalleMano);
		}
		
		return detallePartida;
	}

	public PartidoDTO buscarPartidaLobby(String apodo, String modalidad) throws PartidoException, ParejaException, JugadorException, GrupoException{
		Jugador jug = JugadorDAO.getInstancia().buscarPorApodo(apodo);
		List<Partido> partidos = PartidoDAO.getInstancia().buscarPartidosPorJugador(jug.getId(), TipoModalidad.valueOf(modalidad), EstadoPartido.En_Proceso.name());
		if(partidos!= null && !partidos.isEmpty()) {
			return partidos.get(0).toDTO();
		}
		return null;
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

	public void repartiCartas(PartidoDTO pd) throws CartaException, PartidoException {
		Partido p = PartidoDAO.getInstancia().buscarPartidoPorID(pd.getIdPartido());
		mazo.repartiCartas(p);
	}

	public void actualizarPartido(int idPartido,TurnoDTO turnoDTO) throws PartidoException, GrupoException, JugadorException {
		Partido p = PartidoDAO.getInstancia().buscarPartidoPorID(idPartido);
		Jugador jugadorTurno = DTOMapper.getInstancia().jugadorDTOtoNegocio(turnoDTO.getJugador());
		int indiceMano = 0;
		int indiceBaza = 0;
		if(p.getChico().get(p.getNumeroChicoActual()-1).getManos().size() > 0){
			indiceMano = p.getChico().get(p.getNumeroChicoActual()-1).getManos().size()-1;
		}
		if(p.getChico().get(p.getNumeroChicoActual()-1).getManos().get(indiceMano).getBazas().size() > 0){
			indiceBaza = p.getChico().get(p.getNumeroChicoActual()-1).getManos().get(indiceMano).getBazas().size()-1; 
		}
		Baza baza = p.getChico().get(p.getNumeroChicoActual()-1).getManos().get(indiceMano).getBazas().get(indiceBaza);
		
		Turno turno = null;
		for (Turno t : baza.getTurnos()){
			if (t.getJugador().getId().equals(jugadorTurno.getId())) {
				turno = t;
				break;
			}
		}
		
		if (turno == null) {
			turno = DTOMapper.getInstancia().FrontEndToNegocio(turnoDTO);
			turno.setJugador(JugadorDAO.getInstancia().buscarPorApodo(turnoDTO.getJugadorDTO().getApodo()));
			if (turno.getCarta() != null )
				turno.setCarta(CartaDAO.getInstancia().obtenerCartaPorID(turnoDTO.getCartaDTO().getIdCarta()));
			baza.agregarTurno(turno);	
		}
		else if (turno.getCarta() == null && turnoDTO.getCartaDTO() != null) {
			turno.setCarta(CartaDAO.getInstancia().obtenerCartaPorID(turnoDTO.getCartaDTO().getIdCarta()));
		}
		turno.setearEnviteActual (turnoDTO.getEnviteActual());
		
		p.actualizar();
		if (turnoDTO.getEnviteActual().toString().contains("Envido")) {
			p.nuevaJugadaTantos(turno);
		}
		else {
			p.nuevaJugadaJuego(turno);
		}
	}
	


	public TurnoDTO buscarSiguienteTurno(Integer idBaza, Integer numTurnos) throws TurnoException, GrupoException {
		List<Turno> turnos = TurnoDAO.getInstancia().buscarTurnosPorBaza(idBaza);
		if(turnos != null && turnos.size()>numTurnos) {
			return turnos.get(numTurnos).toDTO();
		}
		return null;
	}
}
