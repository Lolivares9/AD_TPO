package test;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import delegado.BusinessDelegate;
import dto.BazaDTO;
import dto.CartaDTO;
import dto.ChicoDTO;
import dto.JugadorDTO;
import dto.ManoDTO;
import dto.PartidoDTO;
import dto.TurnoDTO;
import enums.Categoria;
import enums.Envite;
import enums.PaloCarta;
import excepciones.ComunicationException;
import excepciones.GrupoException;
import excepciones.JugadorException;
import excepciones.PartidoException;

public class Cliente {

	public static void main(String[] args) throws ComunicationException, RemoteException, JugadorException, GrupoException {
		
		//COMENZAMOS CON LOS TEST DE RMI Y HIBERNATE
		altaJugador();  // OK 
		iniciarSesion(); // OK 
		//crearGrupo();
		//ingresarNuevosMiembros(); // OK
		//buscarTodosPartidosJugados();  //OK
		buscarPartidaLibreIndividual(); // OK
	
		/*Crear partido libre individual*/
//		JugadorDTO jugador = new JugadorDTO("Matias","chulo","boccardo2013@gmail.com","123456",null);
//		jugador.setCategoria(Categoria.Novato);
//		PartidoDTO part;
//		part = BusinessDelegate.getInstancia().iniciarPartidaLibreIndividual(jugador.getCategoria().name(),jugador.getApodo());
//		iniciarPartidaLibreIndividual(part);
	
		//repartirCartas();
		//buscarTodosPartidosJugadosConFiltro();
		//buscarChicosPorPartido();
		//buscarDetalleChico();
		
	}
	private static void iniciarPartidaLibreIndividual(PartidoDTO part) throws GrupoException {
		if (part != null) {
			System.out.println("Se inicia partido Id: "+part.getIdPartido()+ " Modalidad: "+part.getModalidadDTO().getDescripcion());	
			try {
				ronda1(part);
			} catch (ComunicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//ronda11(part);
			//ronda2(part);
			//ronda3(part);
		}
		else {
			System.out.println("No se puede inicializar el partido, no hay suficientes jugadores en linea.");
		}
	}


	private static void ronda3(PartidoDTO part) throws ComunicationException, GrupoException {
		JugadorDTO pareja1Jug1 = part.getParejaDTOs().get(0).getJugadorDTO1();
		JugadorDTO pareja2Jug1 = part.getParejaDTOs().get(1).getJugadorDTO1();
		JugadorDTO pareja1Jug2 = part.getParejaDTOs().get(0).getJugadorDTO2();
		JugadorDTO pareja2Jug2 = part.getParejaDTOs().get(1).getJugadorDTO2();
		List <TurnoDTO> turnos = new ArrayList<TurnoDTO>();
		TurnoDTO turno1 = new TurnoDTO(null,pareja1Jug1,Envite.Nada,Envite.Nada,part.getParejaDTOs().get(0).getCartasJug1().get(3));
		TurnoDTO turno2 = new TurnoDTO(null,pareja2Jug1,Envite.Nada,Envite.Nada,part.getParejaDTOs().get(1).getCartasJug1().get(3));
		TurnoDTO turno3 = new TurnoDTO(null,pareja1Jug2,Envite.Nada,Envite.Nada,part.getParejaDTOs().get(0).getCartasJug2().get(3));
		TurnoDTO turno4 = new TurnoDTO(null,pareja2Jug2,Envite.Nada,Envite.Nada,part.getParejaDTOs().get(1).getCartasJug2().get(3));
		turnos.add(turno1);
		turnos.add(turno2);
		turnos.add(turno3);
		turnos.add(turno4);
		BusinessDelegate.getInstancia().nuevaJugada(part.getIdPartido(),turno1);
		
	}

	private static void ronda2(PartidoDTO part) throws ComunicationException, GrupoException {
		JugadorDTO pareja1Jug1 = part.getParejaDTOs().get(0).getJugadorDTO1();
		JugadorDTO pareja2Jug1 = part.getParejaDTOs().get(1).getJugadorDTO1();
		JugadorDTO pareja1Jug2 = part.getParejaDTOs().get(0).getJugadorDTO2();
		JugadorDTO pareja2Jug2 = part.getParejaDTOs().get(1).getJugadorDTO2();
		List <TurnoDTO> turnos = new ArrayList<TurnoDTO>();
		TurnoDTO turno1 = new TurnoDTO(null,pareja1Jug1,Envite.Nada,Envite.Nada,part.getParejaDTOs().get(0).getCartasJug1().get(1));
		TurnoDTO turno2 = new TurnoDTO(null,pareja2Jug1,Envite.Nada,Envite.Truco,null);
		TurnoDTO turno3 = new TurnoDTO(null,pareja1Jug2,Envite.Nada,Envite.Truco_Querido,null);
		TurnoDTO turno4 = new TurnoDTO(null,pareja2Jug2,Envite.Nada,Envite.Nada,null);
		turnos.add(turno1);
		turnos.add(turno2);
		turnos.add(turno3);
		turnos.add(turno4);
		BusinessDelegate.getInstancia().nuevaJugada(part.getIdPartido(),turno1);
		
	}
	
	private static void ronda11(PartidoDTO part) throws ComunicationException, GrupoException {
		JugadorDTO pareja1Jug1 = part.getParejaDTOs().get(0).getJugadorDTO1();
		JugadorDTO pareja2Jug1 = part.getParejaDTOs().get(1).getJugadorDTO1();
		JugadorDTO pareja1Jug2 = part.getParejaDTOs().get(0).getJugadorDTO2();
		JugadorDTO pareja2Jug2 = part.getParejaDTOs().get(1).getJugadorDTO2();
		List <TurnoDTO> turnos = new ArrayList<TurnoDTO>();
		TurnoDTO turno1 = new TurnoDTO(1,pareja1Jug1,Envite.Nada,Envite.Nada,part.getParejaDTOs().get(0).getCartasJug1().get(0));
		TurnoDTO turno2 = new TurnoDTO(2,pareja2Jug1,Envite.Nada,Envite.Nada,part.getParejaDTOs().get(1).getCartasJug1().get(0));
		TurnoDTO turno3 = new TurnoDTO(3,pareja1Jug2,Envite.Envido,Envite.Nada,part.getParejaDTOs().get(0).getCartasJug2().get(0));
		TurnoDTO turno4 = new TurnoDTO(4,pareja2Jug2,Envite.Envido_Querido,Envite.Nada,part.getParejaDTOs().get(1).getCartasJug2().get(0));
		turnos.add(turno1);
		turnos.add(turno2);
		turnos.add(turno3);
		turnos.add(turno4);
		BusinessDelegate.getInstancia().nuevaJugada(part.getIdPartido(),turno1);
	}

	private static void ronda1(PartidoDTO part) throws ComunicationException, GrupoException {
		JugadorDTO pareja1Jug1 = part.getParejaDTOs().get(0).getJugadorDTO1();
		JugadorDTO pareja2Jug1 = part.getParejaDTOs().get(1).getJugadorDTO1();
		JugadorDTO pareja1Jug2 = part.getParejaDTOs().get(0).getJugadorDTO2();
		JugadorDTO pareja2Jug2 = part.getParejaDTOs().get(1).getJugadorDTO2();
		List <TurnoDTO> turnos = new ArrayList<TurnoDTO>();
		TurnoDTO turno1 = new TurnoDTO(null,pareja1Jug1,Envite.Nada,part.getParejaDTOs().get(0).getCartasJug1().get(0),0);
		TurnoDTO turno2 = new TurnoDTO(null,pareja2Jug1,Envite.Nada,part.getParejaDTOs().get(1).getCartasJug1().get(0),0);
		TurnoDTO turno3 = new TurnoDTO(null,pareja1Jug2,Envite.Envido,null,0);
		TurnoDTO turno4 = new TurnoDTO(null,pareja2Jug2,Envite.Envido_Querido,null,0);
		TurnoDTO turno33 = new TurnoDTO(null,pareja1Jug2,Envite.Nada,part.getParejaDTOs().get(0).getCartasJug2().get(0),0);
		TurnoDTO turno44 = new TurnoDTO(null,pareja2Jug2,Envite.Nada,part.getParejaDTOs().get(1).getCartasJug2().get(0),0);
		turnos.add(turno1);
		turnos.add(turno2);
		turnos.add(turno3);
		turnos.add(turno4);
		BusinessDelegate.getInstancia().nuevaJugada(part.getIdPartido(),turno1);
		BusinessDelegate.getInstancia().nuevaJugada(part.getIdPartido(),turno2);
		BusinessDelegate.getInstancia().nuevaJugada(part.getIdPartido(),turno3);
		BusinessDelegate.getInstancia().nuevaJugada(part.getIdPartido(),turno4);
		
		BusinessDelegate.getInstancia().nuevaJugada(part.getIdPartido(),turno33);
		BusinessDelegate.getInstancia().nuevaJugada(part.getIdPartido(),turno44);
	}

	private static void buscarPartidaLibreIndividual() {
		//MATI SE ANOTA PARA BUSCAR PARTIDA LIBRE INDIVIDUAL
		JugadorDTO jugador = new JugadorDTO("Matias","chulo","boccardo2013@gmail.com","123456",null);
		jugador.setCategoria(Categoria.Novato);
		try {
			BusinessDelegate.getInstancia().buscarPartidaLibreIndividual(jugador);
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//FACU SE ANOTA PARA BUSCAR PARTIDA LIBRE INDIVIDUAL
		jugador = new JugadorDTO("Facundo","faculth","asd@gmail.com","123",null);
		jugador.setCategoria(Categoria.Novato);
		try {
			BusinessDelegate.getInstancia().buscarPartidaLibreIndividual(jugador);
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//LAUTI SE ANOTA PARA BUSCAR PARTIDA LIBRE INDIVIDUAL
		jugador = new JugadorDTO("Lautaro","lolivares","lautiolivares@gmail.com","123",null);
		jugador.setCategoria(Categoria.Novato);
		try {
			BusinessDelegate.getInstancia().buscarPartidaLibreIndividual(jugador);
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ROBERTO SE ANOTA PARA BUSCAR PARTIDA LIBRE INDIVIDUAL
		jugador = new JugadorDTO("Roberto","rober","rober@gmail.com","123",null);
		jugador.setCategoria(Categoria.Novato);
		try {
			BusinessDelegate.getInstancia().buscarPartidaLibreIndividual(jugador);
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static void buscarTodosPartidosJugados() {
		JugadorDTO jugador = new JugadorDTO("Facundo","faculth","asd@gmail.com","123",null);
		jugador.setId(2);
		try {
			List<PartidoDTO> partidos = BusinessDelegate.getInstancia().buscarPartidosJugados(jugador, null, null, null);
			System.out.println("El jugador tiene "+ partidos.size() + " partido/s registrado/s.");
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}
	
	private static void buscarTodosPartidosJugadosConFiltro() {
		JugadorDTO jugador = new JugadorDTO("Facundo","faculth","asd@gmail.com","123",null);
		jugador.setId(1);
		try {
			//Prueba Filtrando Categorias
			//List<PartidoDTO> partidos = BusinessDelegate.getInstancia().buscarPartidosJugados(jugador, TipoModalidad.Cerrado, null, null);
			
			//Prueba filtrando fechas
			String str = "07/10/2018";
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date dateIni = format.parse(str);
			str = "10/10/2018";
			Date dateFin = format.parse(str);
			List<PartidoDTO> partidos = BusinessDelegate.getInstancia().buscarPartidosJugados(jugador, null, dateIni, dateFin);
			System.out.println("El jugador tiene "+ partidos.size() + " partido/s registrado/s.");
		} catch (ComunicationException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	private static void buscarChicosPorPartido() {
		PartidoDTO p = new PartidoDTO(null, null, null);
		p.setIdPartido(1);
		try {
			List<ChicoDTO> chicos = BusinessDelegate.getInstancia().buscarChicosPorPartido(p);
			System.out.println("El partido tuvo "+chicos.size()+" chicos.");
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}
	
	private static void buscarDetalleChico() throws GrupoException {
		ChicoDTO chico = new ChicoDTO(1, false, null, 0, 0);
		chico.setIdChico(1);
		try {
			Map<ManoDTO,Map<BazaDTO,List<TurnoDTO>>> det = BusinessDelegate.getInstancia().obtenerDetalleDeChico(chico);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
		
	}

	private static void repartirCartas() throws PartidoException {
		try {
			BusinessDelegate.getInstancia().repartirCartas(null);
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private static void iniciarSesion() throws GrupoException{
		JugadorDTO jugLog = null;
		JugadorDTO jug = new JugadorDTO();
		//INICIA SESION MATI
		jug.setApodo("chulo");
		jug.setPassword("123");
		try {
			jugLog = BusinessDelegate.getInstancia().iniciarSesion(jug);
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jug.getApodo() + " Inició sesión.\n");
		
		//INICIA SESION FACU
		jug.setApodo("faculth");
		jug.setPassword("123");
		try {
			jugLog = BusinessDelegate.getInstancia().iniciarSesion(jug);
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jug.getApodo() + " Inició sesión.\n");
		
		//INICIA SESION LAUTI
		jug.setApodo("lolivares");
		jug.setPassword("123");
		try {
			jugLog = BusinessDelegate.getInstancia().iniciarSesion(jug);
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jug.getApodo() + " Inició sesión.\n");
		
		//INICIA SESION ROBERTO
		jug.setApodo("rober");
		jug.setPassword("123");
		try {
			jugLog = BusinessDelegate.getInstancia().iniciarSesion(jug);
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jug.getApodo() + " Inició sesión.\n");
	}

	private static void altaJugador() throws GrupoException {
		JugadorDTO jugador = new JugadorDTO("Matias","chulo","matias@gmail.com","123",null);
		try {
			BusinessDelegate.getInstancia().altaJugador(jugador);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
		JugadorDTO jugador2 = new JugadorDTO("Facundo","faculth","asd@gmail.com","123",null);
		try {
			BusinessDelegate.getInstancia().altaJugador(jugador2);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
		JugadorDTO jugador3 = new JugadorDTO("Lautaro","lolivares","lautiolivares@gmail.com","123",null);
		try {
			BusinessDelegate.getInstancia().altaJugador(jugador3);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
		JugadorDTO jugador4 = new JugadorDTO("Roberto","rober","rober@gmail.com","123",null);
		try {
			BusinessDelegate.getInstancia().altaJugador(jugador4);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}
	
	private static void crearGrupo(){
		JugadorDTO jugador = new JugadorDTO("Matias","Chulo","boccardo2013@gmail.com","123456",null);
		try {
			BusinessDelegate.getInstancia().crearGrupo("Distribuidas", jugador);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}

	private static void ingresarNuevosMiembros() throws RemoteException, JugadorException, GrupoException, ComunicationException{
		List<JugadorDTO> jugadores = new ArrayList<JugadorDTO>();
		JugadorDTO jugador = BusinessDelegate.getInstancia().buscarJugadorDTO("Lautaro","Feto","a@a.a","123456");
		jugadores.add(jugador);
		//JugadorDTO jugador2 = 
		
		//jugadores.add(jugador);
		//jugadores.add(jugador2);
		try {
			BusinessDelegate.getInstancia().ingresarNuevosMiembros("Distribuidas", jugadores);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}
}
