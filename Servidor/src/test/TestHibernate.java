package test;

import controlador.Controlador;
import dao.JugadorDAO;
import dao.ParejaDAO;
import dto.PartidoDTO;
import enums.Categoria;
import excepciones.CartaException;
import excepciones.JugadorException;
import excepciones.ParejaException;
import excepciones.PartidoException;
import negocio.Jugador;
import negocio.Pareja;
import negocio.Partido;
import util.DTOMapper;

public class TestHibernate {
/*
 * Query Util
 * 	DELETE FROM PAREJAS
	DBCC CHECKIDENT ('APD.dbo.PAREJAS',RESEED, 0)
	*/
	
	public static void main(String[] args) throws JugadorException, ParejaException, PartidoException, CartaException {
		Partido partido = null;
		//guardarParejas();
		//guardarPartido();
		//iniciarPartidaLibre();
		partido = iniciarPartidaLibreIndividual();
		
	}

	private static Partido iniciarPartidaLibreIndividual() throws PartidoException, CartaException, JugadorException {
		Jugador jug = JugadorDAO.getInstancia().buscarPorApodo("Mati");
		Partido part = null;
		Categoria categ = jug.getCategoria();
		PartidoDTO partido = Controlador.getInstancia().iniciarPartidaLibreIndividual(categ, jug);
		part = DTOMapper.getInstancia().partidoDTOtoNegocio(partido);
		return part;
	}

	private static void iniciarPartidaLibre() throws ParejaException, JugadorException {
		Jugador jug = JugadorDAO.getInstancia().buscarPorApodo("Mati");
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
