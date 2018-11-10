package test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import delegado.BusinessDelegate;
import dto.BazaDTO;
import dto.ChicoDTO;
import dto.JugadorDTO;
import dto.ManoDTO;
import dto.PartidoDTO;
import dto.TurnoDTO;
import enums.Categoria;
import excepciones.ComunicationException;

public class Cliente {

	public static void main(String[] args) {
		
		//COMENZAMOS CON LOS TEST DE RMI Y HIBERNATE
		altaJugador();  // OK 
		iniciarSesion(); // OK 
		//crearGrupo(); // OK 
		//llenarGrupo(); // OK
		//buscarTodosPartidosJugados();  //OK
		buscarPartidaLibreIndividual(); // OK
		//iniciarPartidaLibreIndividual(); //TEST OK LAUTI
		
		//repartirCartas();
		//buscarTodosPartidosJugadosConFiltro();
		//buscarChicosPorPartido();
		//buscarDetalleChico();
		
	}


	private static void buscarPartidaLibreIndividual() {
		//MATI SE ANOTA PARA BUSCAR PARTIDA LIBRE INDIVIDUAL
		JugadorDTO jugador = new JugadorDTO("Matias","chulo","boccardo2013@gmail.com","123456");
		jugador.setCategoria(Categoria.Novato);
		try {
			BusinessDelegate.getInstancia().buscarPartidaLibreIndividual(jugador);
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//FACU SE ANOTA PARA BUSCAR PARTIDA LIBRE INDIVIDUAL
		jugador = new JugadorDTO("Facundo","faculth","asd@gmail.com","123");
		jugador.setCategoria(Categoria.Novato);
		try {
			BusinessDelegate.getInstancia().buscarPartidaLibreIndividual(jugador);
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//LAUTI SE ANOTA PARA BUSCAR PARTIDA LIBRE INDIVIDUAL
		jugador = new JugadorDTO("Lautaro","lolivares","lautiolivares@gmail.com","123");
		jugador.setCategoria(Categoria.Novato);
		try {
			BusinessDelegate.getInstancia().buscarPartidaLibreIndividual(jugador);
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ROBERTO SE ANOTA PARA BUSCAR PARTIDA LIBRE INDIVIDUAL
		jugador = new JugadorDTO("Roberto","rober","rober@gmail.com","123");
		jugador.setCategoria(Categoria.Novato);
		try {
			BusinessDelegate.getInstancia().buscarPartidaLibreIndividual(jugador);
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static void iniciarPartidaLibreIndividual() {
		JugadorDTO jugador = new JugadorDTO("Matias","chulo","boccardo2013@gmail.com","123456");
		jugador.setCategoria(Categoria.Novato);
		try {
			PartidoDTO part;
			part = BusinessDelegate.getInstancia().iniciarPartidaLibreIndividual(jugador.getCategoria().name(),jugador.getApodo());
			if (part != null) {
				System.out.println("Se inicia partido Id: "+part.getIdPartido()+ " Modalidad: "+part.getModalidadDTO().getDescripcion());	
			}
			else {
				System.out.println("No se puede inicializar el partido, no hay suficientes jugadores en linea.");
			}
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}


	private static void buscarTodosPartidosJugados() {
		JugadorDTO jugador = new JugadorDTO("Facundo","faculth","asd@gmail.com","123");
		jugador.setId(2);
		try {
			List<PartidoDTO> partidos = BusinessDelegate.getInstancia().buscarPartidosJugados(jugador, null, null, null);
			System.out.println("El jugador tiene "+ partidos.size() + " partido/s registrado/s.");
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}
	
	private static void buscarTodosPartidosJugadosConFiltro() {
		JugadorDTO jugador = new JugadorDTO("Facundo","faculth","asd@gmail.com","123");
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
	
	private static void buscarDetalleChico() {
		ChicoDTO chico = new ChicoDTO(1, false, null, 0, 0);
		chico.setIdChico(1);
		try {
			Map<ManoDTO,Map<BazaDTO,List<TurnoDTO>>> det = BusinessDelegate.getInstancia().obtenerDetalleDeChico(chico);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
		
	}

	private static void repartirCartas() {
		try {
			BusinessDelegate.getInstancia().repartirCartas(null);
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private static void iniciarSesion(){
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

	private static void altaJugador() {
		JugadorDTO jugador = new JugadorDTO("Matias","chulo","matias@gmail.com","123");
		try {
			BusinessDelegate.getInstancia().altaJugador(jugador);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
		JugadorDTO jugador2 = new JugadorDTO("Facundo","faculth","asd@gmail.com","123");
		try {
			BusinessDelegate.getInstancia().altaJugador(jugador2);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
		JugadorDTO jugador3 = new JugadorDTO("Lautaro","lolivares","lautiolivares@gmail.com","123");
		try {
			BusinessDelegate.getInstancia().altaJugador(jugador3);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
		JugadorDTO jugador4 = new JugadorDTO("Roberto","rober","rober@gmail.com","123");
		try {
			BusinessDelegate.getInstancia().altaJugador(jugador4);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}
	
	private static void crearGrupo(){
		JugadorDTO jugador = new JugadorDTO("Matias","chulo","matias@gmail.com","123");
		try {
			BusinessDelegate.getInstancia().crearGrupo("Grupo6", jugador);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}

	private static void llenarGrupo(){
		Categoria categ = Categoria.Novato;
		JugadorDTO jugador = new JugadorDTO("Matias","Chulo","boccardo2013@gmail.com",categ,0,0,0,true,false,false,"segu2022");
		jugador.setId(1);
		List<JugadorDTO> jugadores = new ArrayList<JugadorDTO>();
		jugadores.add(jugador);
		JugadorDTO jugador2 = new JugadorDTO("Facundo","faculth","asd@gmail.com",categ,0,0,0,true,false,false,"123");
		jugador2.setId(2);
		jugadores.add(jugador2);
		try {
			BusinessDelegate.getInstancia().llenarGrupo("Grupo6", jugadores);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}
}
