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
import enums.EstadoPartido;
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

	public void altaJugador(JugadorDTO jugador) throws JugadorException {
		boolean datosValidos = JugadorDAO.getInstancia().validarDatos(jugador.getApodo(), jugador.getMail());
		if(datosValidos){
			Jugador jug = DTOMapper.getInstancia().jugadorDTOtoNegocio(jugador);
			JugadorDAO.getInstancia().guardar(jug);
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
	/**
	 * Este metodo recibe la categoria en la que quiere jugar el jugador (tendria que definir si va a ser asi, o si se toma la categoria
	 * que tiene el jugador) y el jugador. Digamos, si el jugador puede elegir o no en que categoria desea jugar.
	 * 
	 * Internamente primero se fija si hay 3 (ya que 1 es el que quiere jugar) jugadores disponibles, trayendo todos los que esten conectados
	 * y NO esten jugando (deberia agregarle un top 100 o algo asi), y separando por categorias (primero se fija si hay de igual o mayor categoria
	 *  y sino, se fija en las menores categorias), despues de eso, se fija si es posible armar parejas balancedas, en caso de que, devuelve NULL.
	 * Sino, devuelve un partido con las parejas ya distribuidas.
	 * 
	 * Recordar: NO se podrá iniciar un partido si no hay 2 jugadores de la menor categoria. EJ: 3 calificados y uno novato, no se puede balancear
	 * */
	public Partido iniciarPartidaLibreIndividual(Categoria categ,Jugador jug){
		List<Jugador> jugDisp = new ArrayList<Jugador>();
		List <Pareja> parejas = new ArrayList<Pareja>();
		jugDisp.add(jug);
		boolean completo = false;
		boolean esParejo = false;
		Pareja uno = null;
		Pareja dos = null;
		completo = completarJugadores(categ,jugDisp);
		if(jugDisp.size() < 4 && completo == false){
			return null;
		}
		
		esParejo = verificarIgualdadParejas(jugDisp);
		if(esParejo){
			distribuirParejas(uno,dos,jugDisp);
			parejas.add(uno);
			parejas.add(dos);
		}
		
		return new Partido(null, TipoModalidad.Libre_individual, parejas, null, null, EstadoPartido.En_Proceso);
		
	}
	
	private void distribuirParejas(Pareja uno, Pareja dos,List <Jugador> jugDisp) {
		Categoria inicial;
		inicial = jugDisp.get(0).getCategoria();
		uno.setJugador1(jugDisp.get(0));
		jugDisp.remove(0);
		for(int i = 0;i<jugDisp.size();i++){
			if(jugDisp.get(0).getCategoria().equals(inicial)){
				uno.setJugador1(jugDisp.get(0));
				jugDisp.remove(0);
			}
			else{
				dos.setJugador2(jugDisp.get(0));
				jugDisp.remove(0);
			}
		}
	}

	private boolean verificarIgualdadParejas(List<Jugador> jugDisp) {
		int novatos = 0,masters = 0,expertos = 0,calificados = 0;
		for(int i = 0;i<jugDisp.size();i++){
			if(jugDisp.get(i).getCategoria().equals(Categoria.Novato)){
				novatos++;
			}
			if(jugDisp.get(i).getCategoria().equals(Categoria.Master)){
				masters++;
			}
			if(jugDisp.get(i).getCategoria().equals(Categoria.Experto)){
				expertos++;
			}
			if(jugDisp.get(i).getCategoria().equals(Categoria.Calificado)){
				calificados++;
			}
		}
		
		if(novatos > 2 && (expertos > 0 || masters > 0 || calificados > 0)){
			return false;
		}
		if(masters > 2 && (expertos > 0 || novatos > 0 || calificados > 0)){
			return false;
		}
		if(expertos > 2 && (novatos > 0 || masters > 0 || calificados > 0)){
			return false;
		}
		if(calificados > 2 && (expertos > 0 || masters > 0 || novatos > 0)){
			return false;
		}
		
		return true;
	}
	
	private boolean completarJugadores(Categoria categ,List<Jugador> jugDisp) {
		List<Jugador> jugadores = JugadorDAO.getInstancia().getAllJugadores();
		//SACO EL JUGADOR DE LA LISTA
		for(int i = 0;i<jugadores.size();i++){
			if(jugadores.get(i).getApodo().equals(jugDisp.get(0).getApodo())){
				jugadores.remove(jugadores.get(i));
			}
		}
		
		List <Jugador> novatos = new ArrayList<Jugador>();
		List <Jugador> masters = new ArrayList<Jugador>();
		List <Jugador> expertos = new ArrayList<Jugador>();
		List <Jugador> calificados = new ArrayList<Jugador>();
		
		for(int i = 0;i<jugadores.size();i++){
			if(jugadores.get(i).getCategoria().equals(Categoria.Novato)){
				novatos.add(jugadores.get(i));
			}
			if(jugadores.get(i).getCategoria().equals(Categoria.Master)){
				masters.add(jugadores.get(i));
			}
			if(jugadores.get(i).getCategoria().equals(Categoria.Experto)){
				expertos.add(jugadores.get(i));
			}
			if(jugadores.get(i).getCategoria().equals(Categoria.Calificado)){
				calificados.add(jugadores.get(i));
			}
		}
		
		if(novatos.size() >= 3 && categ.equals(Categoria.Novato) && jugDisp.size() < 4){
			while(!novatos.isEmpty()){
				jugDisp.add(novatos.get(0));
				novatos.remove(0);
			}
			return true;
		}
		if(masters.size() >= 3 && categ.equals(Categoria.Master) && jugDisp.size() < 4){
			while(!masters.isEmpty()){
				jugDisp.add(masters.get(0));
				masters.remove(0);
			}
			return true;
		}
		if(expertos.size() >= 3 && categ.equals(Categoria.Experto) && jugDisp.size() < 4){
			while(!expertos.isEmpty()){
				jugDisp.add(expertos.get(0));
				expertos.remove(0);
			}
			return true;
		}
		if(calificados.size() >= 3 && categ.equals(Categoria.Calificado) && jugDisp.size() < 4){
			while(!calificados.isEmpty()){
				jugDisp.add(calificados.get(0));
				calificados.remove(0);
			}
			return true;
		}
		
		//SI NO COMPLETAMOS CON UNA MISMA CATEGORIA, TENEMOS QUE COMPLETAR CON LAS MAYORES O MENORES CATEGORIAS
		
		if(jugDisp.size() < 4){
			completarMayorCategoria(categ,jugDisp,novatos,masters,expertos,calificados);
		}
		if(jugDisp.size() < 4){
			completarMenorCategoria(categ,jugDisp,novatos,masters,expertos,calificados);
		}
		
		if(jugDisp.size() < 4){
			return false;
		}
		
		return true;
	}
	
	private void completarMayorCategoria(Categoria categ,List<Jugador> jugDisp,List <Jugador> novatos,List <Jugador> masters
			,List <Jugador> expertos, List <Jugador> calificados){
		int x = 0;
		if(categ.equals(Categoria.Novato)){
			if(novatos.size() >= 1){ 
				jugDisp.add(novatos.get(0));
				novatos.remove(0);
			}
			if(masters.size() >= 2){
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(masters.get(0));
					masters.remove(0);
					x++;
				}
			}
			else if(masters.size() >= 1){
				jugDisp.add(masters.get(0));
				masters.remove(0);
			}
			if(expertos.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(expertos.get(0));
					expertos.remove(0);
					x++;
				}
			}
			else if(expertos.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(expertos.get(0));
				expertos.remove(0);
			}
			if(calificados.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(calificados.get(0));
					calificados.remove(0);
					x++;
				}
			}
			else if(calificados.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(calificados.get(0));
				calificados.remove(0);
			}
		}
		if(categ.equals(Categoria.Master)){
			if(masters.size() >= 1){
				jugDisp.add(masters.get(0));
				masters.remove(0);
			}
			if(expertos.size() >= 2){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(expertos.get(0));
					expertos.remove(0);
					x++;
				}
			}
			else if(expertos.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(expertos.get(0));
				expertos.remove(0);
			}
			if(calificados.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(calificados.get(0));
					calificados.remove(0);
					x++;
				}
			}
			else if(calificados.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(calificados.get(0));
				calificados.remove(0);
			}
		}
		if(categ.equals(Categoria.Experto)){
			if(expertos.size() >= 1){
				jugDisp.add(expertos.get(0));
				expertos.remove(0);
			}
			if(calificados.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(calificados.get(0));
					calificados.remove(0);
					x++;
				}
			}
			else if(calificados.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(calificados.get(0));
				calificados.remove(0);
			}
		}
		if(categ.equals(Categoria.Calificado)){
			if(calificados.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(calificados.get(0));
					calificados.remove(0);
					x++;
				}
			}
			else if(calificados.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(calificados.get(0));
				calificados.remove(0);
			}
		}
	}
	
	private void completarMenorCategoria(Categoria categ,List<Jugador> jugDisp,List <Jugador> novatos,List <Jugador> masters
			,List <Jugador> expertos, List <Jugador> calificados){
		int x = 0;
		if(categ.equals(Categoria.Calificado)){
			if(calificados.size() >= 1){ 
				jugDisp.add(calificados.get(0));
				calificados.remove(0);
			}
			if(expertos.size() >= 2){
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(expertos.get(0));
					expertos.remove(0);
					x++;
				}
			}
			else if(expertos.size() >= 1){
				jugDisp.add(expertos.get(0));
				expertos.remove(0);
			}
			if(masters.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(masters.get(0));
					masters.remove(0);
					x++;
				}
			}
			else if(masters.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(masters.get(0));
				masters.remove(0);
			}
			if(novatos.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(novatos.get(0));
					novatos.remove(0);
					x++;
				}
			}
			else if(novatos.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(novatos.get(0));
				novatos.remove(0);
			}
		}
		if(categ.equals(Categoria.Experto)){
			if(expertos.size() >= 1){
				jugDisp.add(expertos.get(0));
				expertos.remove(0);
			}
			if(masters.size() >= 2){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(masters.get(0));
					masters.remove(0);
					x++;
				}
			}
			else if(masters.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(masters.get(0));
				masters.remove(0);
			}
			if(novatos.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(novatos.get(0));
					novatos.remove(0);
					x++;
				}
			}
			else if(novatos.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(novatos.get(0));
				novatos.remove(0);
			}
		}
		if(categ.equals(Categoria.Master)){
			if(masters.size() >= 1){
				jugDisp.add(masters.get(0));
				masters.remove(0);
			}
			if(novatos.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(novatos.get(0));
					novatos.remove(0);
					x++;
				}
			}
			else if(novatos.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(novatos.get(0));
				novatos.remove(0);
			}
		}
		if(categ.equals(Categoria.Novato)){
			if(novatos.size() >= 2 && novatos.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(novatos.get(0));
					novatos.remove(0);
					x++;
				}
			}
			else if(novatos.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(novatos.get(0));
				novatos.remove(0);
			}
		}
	}

	public Partido iniciarPartidaLibre(Pareja parej) {
		Partido part = null;
		
		return part;
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
