package test;

import java.util.ArrayList;
import java.util.List;

import delegado.BusinessDelegate;
import dto.JugadorDTO;
import enums.Categoria;
import excepciones.ComunicationException;
import excepciones.JugadorException;

public class Cliente {

	public static void main(String[] args) {
		
		//COMENZAMOS CON LOS TEST DE RMI Y HIBERNATE
		//cargarCartas();
		//crearGrupo();
		llenarGrupo();
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
		JugadorDTO jugador = new JugadorDTO("Facundo","faculth","mail@gmail.com",categ,0,0,0,true,false,"123");
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
			BusinessDelegate.getInstancia().crearGrupo("Grupo5", jugador); //TODO Pasar jugador que lo crea (Admin)
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
