package test;

import delegado.BusinessDelegate;
import dto.JugadorDTO;
import enums.Categoria;
import excepciones.ComunicationException;

public class Cliente {

	public static void main(String[] args) {
		
		//COMENZAMOS CON LOS TEST DE RMI Y HIBERNATE
		cargarCartas();
		crearGrupo();
		altaJugador();

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
		Categoria categ = Categoria.Novato;
		JugadorDTO jugador = new JugadorDTO("Matias","Chulo","boccardo2013@gmail.com",categ,0,0,0,true,false,"segu2022");
		try {
			BusinessDelegate.getInstancia().crearGrupo("Grupo", jugador); //TODO Pasar jugador que lo crea (Admin)
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}

}
