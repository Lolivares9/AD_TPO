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
import excepciones.GrupoException;
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
	
	public static void main(String[] args) throws JugadorException, ParejaException, PartidoException, CartaException, ParseException, GrupoException {
		PartidoDTO pdto = null;
		pdto = Controlador.getInstancia().iniciarPartidaLibreIndividual(Categoria.Novato.toString(),"Chulo");
		Partido p = null;
		p = PartidoDAO.getInstancia().buscarPartidoPorID(1);
		//MANO 1
		baza1(p);
		p = PartidoDAO.getInstancia().buscarPartidoPorID(1);
		baza2(p);
		p = PartidoDAO.getInstancia().buscarPartidoPorID(1);
		if(p.getChico().get(0).getManos().size() < 1){
			baza3(p);
		}
		//MANO 2
		else{
			pdto.setIdPartido(1);
			Controlador.getInstancia().repartiCartas(pdto);
			p = PartidoDAO.getInstancia().buscarPartidoPorID(1);
			baza11(p);
			p = PartidoDAO.getInstancia().buscarPartidoPorID(1);
			baza22(p);
		}
		
	}		
	

	private static void baza11(Partido p) throws PartidoException {
		Pareja pareja1 = p.getParejas().get(0);
		Pareja pareja2 = p.getParejas().get(1);
		Jugador pareja1Jug1 = p.getParejas().get(0).getJugador1();
		Jugador pareja2Jug1 = p.getParejas().get(1).getJugador1();
		Jugador pareja1Jug2 = p.getParejas().get(0).getJugador2();
		Jugador pareja2Jug2 = p.getParejas().get(1).getJugador2();
		List <Turno> turnos = new ArrayList<Turno>();
		List <Baza> bazas = null;
		List <Mano> manos = null;
		Mano mano = p.getChico().get(0).getManos().get(1);
		Baza baza = mano.getBazas().get(0);
		
		Turno turno1 = new Turno(pareja1Jug1,Envite.Nada, Envite.Nada,pareja1.getCartasJugador1().get(0));
		Turno turno2 = new Turno(pareja2Jug1,Envite.Nada,Envite.Nada,pareja2.getCartasJugador1().get(0));
		Turno turno3 = new Turno(pareja1Jug2,Envite.Nada,Envite.Nada,pareja1.getCartasJugador2().get(0));
		Turno turno4 = new Turno(pareja2Jug2,Envite.Nada,Envite.Nada,pareja2.getCartasJugador2().get(0));
		
		turnos.add(turno1);
		baza.setTurnos(turnos);
		p.actualizar();
		p.nuevaJugadaJuego(turno1);
		
		turnos.get(0).setIdTurno(9);
		baza.getTurnos().add(turno2);
		p.actualizar();
		p.nuevaJugadaJuego(turno2);
		
		p = PartidoDAO.getInstancia().buscarPartidoPorID(1);
		p.getChico().get(0).getManos().get(1).getBazas().get(0).getTurnos().add(turno3);
		p.actualizar();
		p.nuevaJugadaJuego(turno3);

		p = PartidoDAO.getInstancia().buscarPartidoPorID(1);
		p.getChico().get(0).getManos().get(1).getBazas().get(0).getTurnos().add(turno4);
		p.actualizar();
		System.out.println(turno1.getJugador().getApodo() + " Jugo la carta: " + turno1.getCarta().getNumero() + " " + turno1.getCarta().getPalo());
		System.out.println(turno2.getJugador().getApodo() + " Jugo la carta: " + turno2.getCarta().getNumero() + " " + turno2.getCarta().getPalo());
		System.out.println(turno3.getJugador().getApodo() + " Jugo la carta: " + turno3.getCarta().getNumero() + " " + turno3.getCarta().getPalo());
		System.out.println(turno4.getJugador().getApodo() + " Jugo la carta: " + turno4.getCarta().getNumero() + " " + turno4.getCarta().getPalo());
		p.nuevaJugadaJuego(turno4);
		
	}
	
	private static void baza22(Partido p) throws PartidoException {
		Pareja pareja1 = p.getParejas().get(0);
		Pareja pareja2 = p.getParejas().get(1);
		Jugador pareja1Jug1 = p.getParejas().get(0).getJugador1();
		Jugador pareja2Jug1 = p.getParejas().get(1).getJugador1();
		Jugador pareja1Jug2 = p.getParejas().get(0).getJugador2();
		Jugador pareja2Jug2 = p.getParejas().get(1).getJugador2();
		List <Turno> turnos = new ArrayList<Turno>();
		List <Baza> bazas = null;
		List <Mano> manos = null;
		Mano mano = p.getChico().get(0).getManos().get(1);
		Baza baza = mano.getBazas().get(1);
		
		Turno turno1 = new Turno(pareja1Jug1,Envite.Nada, Envite.Nada,pareja1.getCartasJugador1().get(1));
		Turno turno2 = new Turno(pareja2Jug1,Envite.Nada,Envite.Nada,pareja2.getCartasJugador1().get(1));
		Turno turno3 = new Turno(pareja1Jug2,Envite.Nada,Envite.Nada,pareja1.getCartasJugador2().get(1));
		Turno turno4 = new Turno(pareja2Jug2,Envite.Nada,Envite.Nada,pareja2.getCartasJugador2().get(1));
		
		turnos.add(turno1);
		baza.setTurnos(turnos);
		p.actualizar();
		p.nuevaJugadaJuego(turno1);
		
		turnos.get(0).setIdTurno(13);
		baza.getTurnos().add(turno2);
		p.actualizar();
		p.nuevaJugadaJuego(turno2);
		
		turnos.get(1).setIdTurno(15);
		baza.getTurnos().add(turno3);
		p.actualizar();
		p.nuevaJugadaJuego(turno3);
		
		turnos.get(2).setIdTurno(16);
		baza.getTurnos().add(turno4);
		p.actualizar();
		System.out.println(turno1.getJugador().getApodo() + " Jugo la carta: " + turno1.getCarta().getNumero() + " " + turno1.getCarta().getPalo());
		System.out.println(turno2.getJugador().getApodo() + " Jugo la carta: " + turno2.getCarta().getNumero() + " " + turno2.getCarta().getPalo());
		System.out.println(turno3.getJugador().getApodo() + " Jugo la carta: " + turno3.getCarta().getNumero() + " " + turno3.getCarta().getPalo());
		System.out.println(turno4.getJugador().getApodo() + " Jugo la carta: " + turno4.getCarta().getNumero() + " " + turno4.getCarta().getPalo());
		p.nuevaJugadaJuego(turno4);
		
	}



	private static void baza1(Partido p) throws PartidoException {
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
		Baza baza = mano.getBazas().get(0);
		
		Turno turno1 = new Turno(pareja1Jug1,Envite.Nada, Envite.Nada,pareja1.getCartasJugador1().get(0));
		Turno turno2 = new Turno(pareja2Jug1,Envite.Nada,Envite.Nada,pareja2.getCartasJugador1().get(0));
		Turno turno3 = new Turno(pareja1Jug2,Envite.Nada,Envite.Nada,pareja1.getCartasJugador2().get(0));
		Turno turno4 = new Turno(pareja2Jug2,Envite.Nada,Envite.Nada,pareja2.getCartasJugador2().get(0));
		
		turnos.add(turno1);
		baza.setTurnos(turnos);
		p.actualizar();
		p.nuevaJugadaJuego(turno1);
		
		turnos.get(0).setIdTurno(1);
		baza.getTurnos().add(turno2);
		p.actualizar();
		p.nuevaJugadaJuego(turno2);
		
		p = PartidoDAO.getInstancia().buscarPartidoPorID(1);
		p.getChico().get(0).getManos().get(0).getBazas().get(0).getTurnos().add(turno3);
		p.actualizar();
		p.nuevaJugadaJuego(turno3);

		p = PartidoDAO.getInstancia().buscarPartidoPorID(1);
		p.getChico().get(0).getManos().get(0).getBazas().get(0).getTurnos().add(turno4);
		p.actualizar();
		System.out.println(turno1.getJugador().getApodo() + " Jugo la carta: " + turno1.getCarta().getNumero() + " " + turno1.getCarta().getPalo());
		System.out.println(turno2.getJugador().getApodo() + " Jugo la carta: " + turno2.getCarta().getNumero() + " " + turno2.getCarta().getPalo());
		System.out.println(turno3.getJugador().getApodo() + " Jugo la carta: " + turno3.getCarta().getNumero() + " " + turno3.getCarta().getPalo());
		System.out.println(turno4.getJugador().getApodo() + " Jugo la carta: " + turno4.getCarta().getNumero() + " " + turno4.getCarta().getPalo());
		p.nuevaJugadaJuego(turno4);
		
	}
	
	private static void baza2(Partido p) throws PartidoException {
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
		Baza baza = mano.getBazas().get(1);
		
		Turno turno1 = new Turno(pareja1Jug1,Envite.Nada, Envite.Nada,pareja1.getCartasJugador1().get(1));
		Turno turno2 = new Turno(pareja2Jug1,Envite.Nada,Envite.Nada,pareja2.getCartasJugador1().get(1));
		Turno turno3 = new Turno(pareja1Jug2,Envite.Nada,Envite.Nada,pareja1.getCartasJugador2().get(1));
		Turno turno4 = new Turno(pareja2Jug2,Envite.Nada,Envite.Nada,pareja2.getCartasJugador2().get(1));
		
		turnos.add(turno1);
		baza.setTurnos(turnos);
		p.actualizar();
		p.nuevaJugadaJuego(turno1);
		
		turnos.get(0).setIdTurno(5);
		baza.getTurnos().add(turno2);
		p.actualizar();
		p.nuevaJugadaJuego(turno2);
		
		turnos.get(1).setIdTurno(6);
		baza.getTurnos().add(turno3);
		p.actualizar();
		p.nuevaJugadaJuego(turno3);
		
		turnos.get(2).setIdTurno(7);
		baza.getTurnos().add(turno4);
		p.actualizar();
		System.out.println(turno1.getJugador().getApodo() + " Jugo la carta: " + turno1.getCarta().getNumero() + " " + turno1.getCarta().getPalo());
		System.out.println(turno2.getJugador().getApodo() + " Jugo la carta: " + turno2.getCarta().getNumero() + " " + turno2.getCarta().getPalo());
		System.out.println(turno3.getJugador().getApodo() + " Jugo la carta: " + turno3.getCarta().getNumero() + " " + turno3.getCarta().getPalo());
		System.out.println(turno4.getJugador().getApodo() + " Jugo la carta: " + turno4.getCarta().getNumero() + " " + turno4.getCarta().getPalo());
		p.nuevaJugadaJuego(turno4);
		
		
	}
	

	private static void baza3(Partido p) throws PartidoException {
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
		Baza baza = mano.getBazas().get(2);
		
		Turno turno1 = new Turno(pareja1Jug1,Envite.Nada, Envite.Nada,pareja1.getCartasJugador1().get(2));
		Turno turno2 = new Turno(pareja2Jug1,Envite.Nada,Envite.Nada,pareja2.getCartasJugador1().get(2));
		Turno turno3 = new Turno(pareja1Jug2,Envite.Nada,Envite.Nada,pareja1.getCartasJugador2().get(2));
		Turno turno4 = new Turno(pareja2Jug2,Envite.Nada,Envite.Nada,pareja2.getCartasJugador2().get(2));
		
		turnos.add(turno1);
		baza.setTurnos(turnos);
		p.actualizar();
		p.nuevaJugadaJuego(turno1);
		
		turnos.get(0).setIdTurno(9);
		baza.getTurnos().add(turno2);
		p.actualizar();
		p.nuevaJugadaJuego(turno2);
		
		turnos.get(1).setIdTurno(10);
		baza.getTurnos().add(turno3);
		p.actualizar();
		p.nuevaJugadaJuego(turno3);
		
		turnos.get(2).setIdTurno(11);
		baza.getTurnos().add(turno4);
		p.actualizar();
		System.out.println(turno1.getJugador().getApodo() + " Jugo la carta: " + turno1.getCarta().getNumero() + " " + turno1.getCarta().getPalo());
		System.out.println(turno2.getJugador().getApodo() + " Jugo la carta: " + turno2.getCarta().getNumero() + " " + turno2.getCarta().getPalo());
		System.out.println(turno3.getJugador().getApodo() + " Jugo la carta: " + turno3.getCarta().getNumero() + " " + turno3.getCarta().getPalo());
		System.out.println(turno4.getJugador().getApodo() + " Jugo la carta: " + turno4.getCarta().getNumero() + " " + turno4.getCarta().getPalo());
		p.nuevaJugadaJuego(turno4);
	}

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
