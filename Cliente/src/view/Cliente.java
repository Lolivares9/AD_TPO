package view;

import delegado.BusinessDelegate;
import dto.JugadorDTO;
import enums.Categoria;
import excepciones.ComunicationException;

public class Cliente {

	public static void main(String[] args) {
		
		//COMENZAMOS CON LOS TEST DE RMI Y HIBERNATE
		altaJugador();

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

}
