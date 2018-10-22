package controlador;

import java.rmi.RemoteException;
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
			g.agregarJugador(jug);
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
		if(jugador != null && jugador.getApodo().equals(jug.getApodo()) && jugador.getPassword().equals(jug.getPassword())){
			return true;
		}
		return false;
	}
	
	//OK, FALTARIA SETEARLE A LOS JUGADORES QUE JUGANDO = TRUE
	public Partido iniciarPartidaLibreIndividual(Categoria categ,Jugador jug) throws PartidoException {
		List <Jugador> jugDisp = new ArrayList<Jugador>();
		List <Pareja> parejas = new ArrayList<Pareja>();
		jugDisp.add(jug);
		boolean completo = false;
		boolean esParejo = false;
		completo =completarJugadores(categ,jugDisp);
		if(jugDisp.size() ==  4  && completo == true){
			esParejo = verificarIgualdadParejas(jugDisp);
			if(esParejo){
				parejas = distribuirParejas(jugDisp);
				Partido p =  new Partido(null, TipoModalidad.Libre_individual, parejas, null, null, EstadoPartido.En_Proceso);
				p.guardar();
				for (Jugador j : jugDisp) {
					j.setJugando(true);
					j.setPartidosJugados(j.getPartidosJugados() + 1);
					j.guardar();
				}
				return p;
			}	
		}
		return null;
	}
	
	private List<Pareja> distribuirParejas(List <Jugador> jugDisp) {
		Categoria inicial;
		List <Pareja> parejasArmadas = new ArrayList<Pareja>();
		inicial = jugDisp.get(0).getCategoria();
		Pareja uno = new Pareja(null,null);
		Pareja dos = new Pareja(null,null);
		for(int i = 0;i<4;i++){
			if(jugDisp.get(0).getCategoria().equals(inicial) && (uno.getJugador1() == null || dos.getJugador1() == null)){
				if(uno.getJugador1() == null){
					uno.setJugador1(jugDisp.get(0));
					jugDisp.remove(0);
				}
				else{
					
					dos.setJugador1(jugDisp.get(0));
					jugDisp.remove(0);
				}
			}
			else{
				if(uno.getJugador2() == null){
					uno.setJugador2(jugDisp.get(0));
					jugDisp.remove(0);
				}
				else{
					dos.setJugador2(jugDisp.get(0));
					jugDisp.remove(0);
				}
			}
		}
		
		parejasArmadas.add(uno);
		parejasArmadas.add(dos);
		
		return parejasArmadas;
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
		
		if(novatos != 4 && masters != 4 && expertos != 4 && calificados != 4){
			if(novatos == 1 || masters == 1 || expertos == 1 || calificados == 1){
				return false;
			}
		}
		
		return true;
	}
	
	private boolean completarJugadores(Categoria categ,List<Jugador> jugDisp) {
		
		/*
		 *1- Si el jugador que arma la partida es novato - Revisar que exista al menos 1 jugador de la misma categoria del jugador que esta creando la partida.
		 *2- Si existe al menos uno, busca si existen 2 mas de la misma categoria. 
		 *2.1 Sino busco 2 jugadores mas de la misma categoria 
		 *sino buso superiores
		 *sino busco de la categoria inferior  
		 		PD: en ambos casos se deben equiparar los equipos.
		 * */
		
		
		List<Jugador> jugadores = JugadorDAO.getInstancia().getAllJugadores();
		//SACO EL JUGADOR DE LA LISTA
		for(int i = 0;i<jugadores.size();i++){
			if(jugadores.get(i).getApodo().equals(jugDisp.get(0).getApodo())){
				jugadores.remove(jugadores.get(i));
				break;
			}
		}
		
		if (jugadores.size() > 2) {
			List <Jugador> novatos = new ArrayList<Jugador>();
			List <Jugador> masters = new ArrayList<Jugador>();
			List <Jugador> expertos = new ArrayList<Jugador>();
			List <Jugador> calificados = new ArrayList<Jugador>();
			
			for(int i = 0;i<jugadores.size();i++){
				if(jugadores.get(i).getCategoria().equals(Categoria.Novato)){
					novatos.add(jugadores.get(i));
				}
				else if(jugadores.get(i).getCategoria().equals(Categoria.Master)){
					masters.add(jugadores.get(i));
				}
				else if(jugadores.get(i).getCategoria().equals(Categoria.Experto)){
					expertos.add(jugadores.get(i));
				}
				else if(jugadores.get(i).getCategoria().equals(Categoria.Calificado)){
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
		}
		else {
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

	/**
	 * Entiendo que aca la pareja ya va a estar persistida (la que quiere jugar), por eso necesito su id
	 * */
	//OK, FALTARIA SETEARLE A LOS JUGADORES QUE JUGANDO = TRUE
	public Partido iniciarPartidaLibre(Pareja parej) throws ParejaException {
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
					part =  new Partido(null, TipoModalidad.Libre, parejasFinales, null, null, EstadoPartido.En_Proceso);
					return part;
				}
			}
		}
		
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
	 * @throws ParejaException 
	 * @throws PartidoException 
	 */
	public List<PartidoDTO> buscarPartidosJugados(JugadorDTO jugador, TipoModalidad mod, Date fechaInicial, Date fechaFin) throws ParejaException, PartidoException{
		List<PartidoDTO> partidosDTO = new ArrayList<PartidoDTO>();
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
	/*  
	 * 
	 * Para qué esta el puntaje en la tabla de parejas?, las parejas se eliminan despues de que juegan, pero nosotros las podriamos reusar
	 * si se repite una pareja, pero hay que sacarle la columna de puntaje para eso.
	 */
}
