package test;

import java.util.ArrayList;
import java.util.List;

import controlador.Controlador;
import dao.JugadorDAO;
import dao.ParejaDAO;
import excepciones.JugadorException;
import excepciones.ParejaException;
import negocio.Jugador;
import negocio.Pareja;
import negocio.Partido;

public class TestHibernate {
/*
 * Query Util
 * 	DELETE FROM PAREJAS
	DBCC CHECKIDENT ('APD.dbo.PAREJAS',RESEED, 0)
	*/
	
	public static void main(String[] args) throws JugadorException, ParejaException {
		//guardarParejas();
		//guardarPartido();
		//iniciarPartidaLibre();
	}

	private static void iniciarPartidaLibre() throws ParejaException, JugadorException {
		Jugador jug = JugadorDAO.getInstancia().buscarPorApodo("Chulo");
		Jugador jug2 = JugadorDAO.getInstancia().buscarPorApodo("Kent");
		Pareja uno = new Pareja(jug,jug2);
		uno.setIdPareja(1);
		Partido p = Controlador.getInstancia().iniciarPartidaLibre(uno);
		
	}

	private static void guardarPartido() throws JugadorException {
		Jugador jug = JugadorDAO.getInstancia().buscarPorApodo("chulo");
		Jugador jug2 = JugadorDAO.getInstancia().buscarPorApodo("Kent");
		
	}

	private static void guardarParejas() throws JugadorException {
		Jugador jug = JugadorDAO.getInstancia().buscarPorApodo("Feto");
		Jugador jug2 = JugadorDAO.getInstancia().buscarPorApodo("ATR");
		Pareja uno = new Pareja(jug,jug2);
		ParejaDAO.getInstancia().guardar(uno);
	}

}