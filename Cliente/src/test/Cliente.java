package test;

import java.util.ArrayList;
import java.util.List;

import delegado.BusinessDelegate;
import dto.JugadorDTO;
import enums.Categoria;
import excepciones.ComunicationException;

public class Cliente {

	public static void main(String[] args) {
		
		//COMENZAMOS CON LOS TEST DE RMI Y HIBERNATE
		//cargarCartas();
		//crearGrupo(); TODO 
		//llenarGrupo(); TODO Grupo-Jugador Entity
		//altaJugador();
		//iniciarSesion();
		
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
		System.out.println("Inició bien: "+inicioBien);
	}

	private static void cargarCartas() {
		// TODO Auto-generated method stub
		try {
			BusinessDelegate.getInstancia().cargarCartas();
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}

	private static void altaJugador() {
		Categoria categ = Categoria.Novato;
		JugadorDTO jugador = new JugadorDTO("Matias","Chulo","boccardo2013@gmail.com",categ,0,0,0,true,false,"segu2022");
		try {
			BusinessDelegate.getInstancia().AltaJugador(jugador);
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}
	
	private static void crearGrupo(){
		//TODO METER AL JUGADOR QUE CREA COMO INTEGRANTE???
		Categoria categ = Categoria.Novato;
		JugadorDTO jugador = new JugadorDTO("Matias","Chulo","boccardo2013@gmail.com",categ,0,0,0,true,false,"segu2022");
		try {
			BusinessDelegate.getInstancia().crearGrupo("Grupo1", jugador); //TODO Pasar jugador que lo crea (Admin)
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}

	private static void llenarGrupo(){
		Categoria categ = Categoria.Novato;
		JugadorDTO jugador = new JugadorDTO("Matias","Chulo","boccardo2013@gmail.com",categ,0,0,0,true,false,"segu2022");
		List<JugadorDTO> jugadores = new ArrayList<JugadorDTO>();
		jugadores.add(jugador);
		JugadorDTO jugador3 = new JugadorDTO("Daniel","Chulo","boccardo2013@gmail.com",categ,0,0,0,true,false,"segu2022");
		jugadores.add(jugador3);
		try {
			BusinessDelegate.getInstancia().llenarGrupo("Grupo1", jugadores); //TODO Pasar jugador que lo crea (Admin)
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}
}
