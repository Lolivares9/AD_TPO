package test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import controlador.Controlador;
import dao.JugadorDAO;
import dao.ParejaDAO;
import dao.PartidoDAO;
import dto.PartidoDTO;
import enums.Categoria;
import enums.Envite;
import excepciones.CartaException;
import excepciones.JugadorException;
import excepciones.ParejaException;
import excepciones.PartidoException;
import negocio.Baza;
import negocio.Jugador;
import negocio.Mano;
import negocio.Pareja;
import negocio.Partido;
import negocio.Turno;

public class TestHibernate {
/*
 * Query Util
 * 	DELETE FROM PAREJAS
	DBCC CHECKIDENT ('APD.dbo.PAREJAS',RESEED, 0)
	*/
	
	public static void main(String[] args) throws JugadorException, ParejaException, PartidoException, CartaException, ParseException {
		Partido partido = null;
		PartidoDTO pdto = null;
		//guardarParejas();
		//guardarPartido();
		//iniciarPartidaLibre();
		//pdto = Controlador.getInstancia().iniciarPartidaLibreIndividual(Categoria.Novato.toString(),"Chulo");
		jugada(pdto);
		//partido = PartidoDAO.getInstancia().buscarPartidoPorID(1);
		//System.out.println(partido.getParejas().get(0).getIdPareja());
		//Partido.nuevaJugada(1);
		
	}

private static void jugada(PartidoDTO pdto) throws PartidoException {
	Partido p = null;
	p = PartidoDAO.getInstancia().buscarPartidoPorID(1);
	Pareja pareja1 = p.getParejas().get(0);
	Pareja pareja2 = p.getParejas().get(1);
	Jugador pareja1Jug1 = p.getParejas().get(0).getJugador1();
	Jugador pareja2Jug1 = p.getParejas().get(1).getJugador1();
	Jugador pareja1Jug2 = p.getParejas().get(0).getJugador2();
	Jugador pareja2Jug2 = p.getParejas().get(1).getJugador2();
	List <Turno> turnos = new ArrayList<Turno>();
	List <Baza> bazas = null;
	List <Mano> manos = null;
	Mano mano = p.getChico().get(0).getManos().get(0);
	Baza baza = p.getChico().get(0).getManos().get(0).getBazas().get(0);
	Turno turno1 = new Turno(pareja1Jug1,Envite.Nada,pareja1.getCartasJugador1().get(0));
	Turno turno2 = new Turno(pareja2Jug1,Envite.Nada,pareja2.getCartasJugador1().get(0));
	Turno turno3 = new Turno(pareja1Jug2,Envite.Envido,null);
	Turno turno4 = new Turno(pareja2Jug2,Envite.Envido_Querido,null);
	turnos.add(turno1);
	turnos.add(turno2);
	turnos.add(turno3);
	turnos.add(turno4);
	baza.setTurnos(turnos);
	bazas = p.getChico().get(0).getManos().get(0).getBazas();
	bazas.set(0, baza);
	mano.setBazas(bazas);
	manos = p.getChico().get(0).getManos();
	manos.set(0, mano);
	p.getChico().get(0).setManos(manos);
	p.actualizar();
	//p.nuevaJugada(1);
	
	
	
	System.out.println(p.getParejas().get(0).getCartasJugador1().get(0).getValorJuego());
	
	
	
}

//	private static Partido iniciarPartidaLibreIndividual() throws PartidoException, CartaException, JugadorException {
//		Jugador jug = JugadorDAO.getInstancia().buscarPorApodo("Mati");
//		JugadorDTO jdto = jug.toDTO();
//		Partido part = null;
//		Categoria categ = jug.getCategoria();
//		PartidoDTO partido = Controlador.getInstancia().iniciarPartidaLibreIndividual(categ, jdto);
//		part = DTOMapper.getInstancia().partidoDTOtoNegocio(partido);
//		return part;
//	}

	private static void iniciarPartidaLibre() throws ParejaException, JugadorException, CartaException {
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
