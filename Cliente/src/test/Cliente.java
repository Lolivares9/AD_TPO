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
import dto.CartaDTO;
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
		//crearGrupo();
		//llenarGrupo();
		//altaJugador();
		//iniciarSesion();
		//repartirCartas();
		//buscarTodosPartidosJugados();
		//buscarTodosPartidosJugadosConFiltro();
		buscarChicosPorPartido();
		//buscarDetalleChico();
		
	}


	private static void buscarTodosPartidosJugados() {
		JugadorDTO jugador = new JugadorDTO("Facundo","faculth","asd@gmail.com","123");
		jugador.setId(1);
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
			List<CartaDTO> cartas = BusinessDelegate.getInstancia().repartirCartas();
			cartas.forEach(c -> System.out.println(c.getNumero() + " de " + c.getPalo()));
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private static void iniciarSesion(){
		boolean inicioBien = false;
		JugadorDTO jug = new JugadorDTO();
		jug.setApodo("Chulo");
		jug.setPassword("segu2022");
		try {
			inicioBien = BusinessDelegate.getInstancia().iniciarSesion(jug);
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Inici� bien: "+inicioBien);
	}

	private static void altaJugador() {
		JugadorDTO jugador = new JugadorDTO("Facundo","faculth","asd@gmail.com","123");
		try {
			BusinessDelegate.getInstancia().AltaJugador(jugador);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}
	
	private static void crearGrupo(){
		//TODO METER AL JUGADOR QUE CREA COMO INTEGRANTE???
		JugadorDTO jugador = new JugadorDTO();
		jugador.setApodo("Chulo");
		try {
			BusinessDelegate.getInstancia().crearGrupo("Grupo5", jugador);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}

	private static void llenarGrupo(){
		Categoria categ = Categoria.Novato;
		JugadorDTO jugador = new JugadorDTO("Matias","Chulo","boccardo2013@gmail.com",categ,0,0,0,true,false,"segu2022");
		jugador.setId(1);
		List<JugadorDTO> jugadores = new ArrayList<JugadorDTO>();
		jugadores.add(jugador);
		JugadorDTO jugador3 = new JugadorDTO("Facundo","faculth","mail@gmail.com",categ,0,0,0,true,false,"123");
		jugador3.setId(2);
		jugadores.add(jugador3);
		try {
			BusinessDelegate.getInstancia().llenarGrupo("Grupo5", jugadores); //TODO Pasar jugador que lo crea (Admin)
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}
}
