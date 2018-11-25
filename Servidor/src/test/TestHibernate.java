package test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import controlador.Controlador;
import dao.JugadorDAO;
import dao.ParejaDAO;
import dao.PartidoDAO;
import dto.PartidoDTO;
import dto.TurnoDTO;
import enums.Categoria;
import enums.Envite;
import excepciones.CartaException;
import excepciones.GrupoException;
import excepciones.JugadorException;
import excepciones.ParejaException;
import excepciones.PartidoException;
import negocio.Baza;
import negocio.Carta;
import negocio.Chico;
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
		iniciarPartido();
	}		
	

	private static void iniciarPartido() throws PartidoException, CartaException, JugadorException, GrupoException {
		PartidoDTO pdto = null;
		pdto = Controlador.getInstancia().iniciarPartidaLibreIndividual(Categoria.Novato.toString(),"Chulo");
		//jugar Partido
		//pdto = new PartidoDTO (1,null,null,null,null);
		jugarPartido(pdto);
	}


	private static void jugarPartido(PartidoDTO pdto) throws PartidoException, GrupoException, JugadorException {
		Partido p = null;
		p = PartidoDAO.getInstancia().buscarPartidoPorID(pdto.getIdPartido());
		
		Jugador pareja1Jug1 = p.getParejas().get(0).getJugador1();
		Jugador pareja2Jug1 = p.getParejas().get(1).getJugador1();
		Jugador pareja1Jug2 = p.getParejas().get(0).getJugador2();
		Jugador pareja2Jug2 = p.getParejas().get(1).getJugador2();
		
		List <Carta> cartasP1J1 = p.getParejas().get(0).getCartasJugador1();
		List <Carta> cartasP1J2 = p.getParejas().get(0).getCartasJugador2();
		List <Carta> cartasP2J1 = p.getParejas().get(1).getCartasJugador1();
		List <Carta> cartasP2J2 = p.getParejas().get(1).getCartasJugador2();
								
		
		while (p.getParejaGanadora() == null) {
			Chico chicoActual = p.getChico().get(p.getNumeroChicoActual()-1);
			
			int indiceManoActual = chicoActual.getManos().size()-1;
			Mano manoActual = chicoActual.getManos().get(indiceManoActual);
			int indiceBazaActual = manoActual.getBazas().size()-1;
			Baza bazaActual = manoActual.getBazas().get(indiceBazaActual);
			int nroTurno= manoActual.getBazas().size()-1;
			
			while (chicoActual.getParejaGanadora() == null){
				TurnoDTO tj1 = new TurnoDTO(null, pareja1Jug1.toDTO(), Envite.Nada, cartasP1J1.get(nroTurno).toDTO(),1);
				Controlador.getInstancia().actualizarPartido(p.getIdPartido(), tj1);
				
				TurnoDTO tj2 = new TurnoDTO(null, pareja2Jug1.toDTO(), Envite.Nada, cartasP1J2.get(nroTurno).toDTO(),2);
				Controlador.getInstancia().actualizarPartido(p.getIdPartido(), tj2);
				
				TurnoDTO tj3 = new TurnoDTO(null, pareja1Jug2.toDTO(), Envite.Nada, cartasP2J1.get(nroTurno).toDTO(),3);
				Controlador.getInstancia().actualizarPartido(p.getIdPartido(), tj3);
				
				TurnoDTO tj4 = new TurnoDTO(null, pareja2Jug2.toDTO(), Envite.Nada, cartasP2J2.get(nroTurno).toDTO(),4);
				Controlador.getInstancia().actualizarPartido(p.getIdPartido(), tj4);
				
				p = PartidoDAO.getInstancia().buscarPartidoPorID(pdto.getIdPartido());
				
				chicoActual = p.getChico().get(p.getNumeroChicoActual()-1);
				
				if (p.getChico().get(0).isFinalizado()) {
					chicoActual = p.getChico().get(p.getNumeroChicoActual()-1);
				}
				
				indiceManoActual = chicoActual.getManos().size()-1;
				manoActual = chicoActual.getManos().get(indiceManoActual);
				
				indiceBazaActual = manoActual.getBazas().size()-1;
				bazaActual = manoActual.getBazas().get(indiceBazaActual);
				
				cartasP1J1 = p.getParejas().get(0).getCartasJugador1();
				cartasP1J2 = p.getParejas().get(0).getCartasJugador2();
				cartasP2J1 = p.getParejas().get(1).getCartasJugador1();
				cartasP2J2 = p.getParejas().get(1).getCartasJugador2();
				
				nroTurno= manoActual.getBazas().size()-1;
				if (nroTurno == -1)
					nroTurno = 0;
			}
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
		Mano mano = p.getChico().get(0).getManos().get(1);
		Baza baza = mano.getBazas().get(0);
		
		Turno turno1 = new Turno(pareja1Jug1,Envite.Nada, Envite.Nada,pareja1.getCartasJugador1().get(0),1);
		Turno turno2 = new Turno(pareja2Jug1,Envite.Nada,Envite.Nada,pareja2.getCartasJugador1().get(0),2);
		Turno turno3 = new Turno(pareja1Jug2,Envite.Nada,Envite.Nada,pareja1.getCartasJugador2().get(0),3);
		Turno turno4 = new Turno(pareja2Jug2,Envite.Nada,Envite.Nada,pareja2.getCartasJugador2().get(0),4);
		
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
		Mano mano = p.getChico().get(0).getManos().get(1);
		Baza baza = mano.getBazas().get(1);
		
		Turno turno1 = new Turno(pareja1Jug1,Envite.Nada, Envite.Nada,pareja1.getCartasJugador1().get(0),1);
		Turno turno2 = new Turno(pareja2Jug1,Envite.Nada,Envite.Nada,pareja2.getCartasJugador1().get(0),2);
		Turno turno3 = new Turno(pareja1Jug2,Envite.Nada,Envite.Nada,pareja1.getCartasJugador2().get(0),3);
		Turno turno4 = new Turno(pareja2Jug2,Envite.Nada,Envite.Nada,pareja2.getCartasJugador2().get(0),4);
		
		turnos.add(turno1);
		baza.setTurnos(turnos);
		p.actualizar();
		p.nuevaJugadaJuego(turno1);
		
		p = PartidoDAO.getInstancia().buscarPartidoPorID(1);
		p.getChico().get(0).getManos().get(1).getBazas().get(1).getTurnos().add(turno2);
		p.actualizar();
		p.nuevaJugadaJuego(turno2);
		
		p = PartidoDAO.getInstancia().buscarPartidoPorID(1);
		p.getChico().get(0).getManos().get(1).getBazas().get(1).getTurnos().add(turno3);
		p.actualizar();
		p.nuevaJugadaJuego(turno3);
		
		p = PartidoDAO.getInstancia().buscarPartidoPorID(1);
		p.getChico().get(0).getManos().get(1).getBazas().get(1).getTurnos().add(turno4);
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
		Mano mano = p.getChico().get(0).getManos().get(0);
		Baza baza = mano.getBazas().get(0);
		
		Turno turno1 = new Turno(pareja1Jug1,Envite.Nada, Envite.Nada,pareja1.getCartasJugador1().get(0),1);
		Turno turno2 = new Turno(pareja2Jug1,Envite.Nada,Envite.Nada,pareja2.getCartasJugador1().get(0),2);
		Turno turno3 = new Turno(pareja1Jug2,Envite.Nada,Envite.Nada,pareja1.getCartasJugador2().get(0),3);
		Turno turno4 = new Turno(pareja2Jug2,Envite.Nada,Envite.Nada,pareja2.getCartasJugador2().get(0),4);
		
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
		Mano mano = p.getChico().get(0).getManos().get(0);
		Baza baza = mano.getBazas().get(1);
		
		Turno turno1 = new Turno(pareja1Jug1,Envite.Nada, Envite.Nada,pareja1.getCartasJugador1().get(0),1);
		Turno turno2 = new Turno(pareja2Jug1,Envite.Nada,Envite.Nada,pareja2.getCartasJugador1().get(0),2);
		Turno turno3 = new Turno(pareja1Jug2,Envite.Nada,Envite.Nada,pareja1.getCartasJugador2().get(0),3);
		Turno turno4 = new Turno(pareja2Jug2,Envite.Nada,Envite.Nada,pareja2.getCartasJugador2().get(0),4);
		
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
		Mano mano = p.getChico().get(0).getManos().get(0);
		Baza baza = mano.getBazas().get(2);
		
		Turno turno1 = new Turno(pareja1Jug1,Envite.Nada, Envite.Nada,pareja1.getCartasJugador1().get(0),1);
		Turno turno2 = new Turno(pareja2Jug1,Envite.Nada,Envite.Nada,pareja2.getCartasJugador1().get(0),2);
		Turno turno3 = new Turno(pareja1Jug2,Envite.Nada,Envite.Nada,pareja1.getCartasJugador2().get(0),3);
		Turno turno4 = new Turno(pareja2Jug2,Envite.Nada,Envite.Nada,pareja2.getCartasJugador2().get(0),4);
		
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
