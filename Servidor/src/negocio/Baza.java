package negocio;

import java.util.ArrayList;
import java.util.List;

import dao.BazaDAO;
import dto.BazaDTO;
import enums.Envite;
import excepciones.GrupoException;

/**
 * Soy la jugada de 1 carta de los 4 jugadores
 */
public class Baza {
	
	private Integer idBaza;
	private int numeroBaza;
	private Envite enviteActual;
	private int numJugEnvitePendiente;
	private boolean bazaTerminada;
	private Pareja ganadores;
	private int puntajePareja1;
	private int puntajePareja2;
	private List<Turno> turnos;
	
	public Baza(Integer idBaza, int numeroBaza, Pareja ganadores, int puntajePareja1, int puntajePareja2,List<Turno> turnos) {
		super();
		this.idBaza = idBaza;
		this.numeroBaza = numeroBaza;
		this.ganadores = ganadores;
		this.puntajePareja1 = puntajePareja1;
		this.puntajePareja2 = puntajePareja2;
		this.turnos = turnos;
	}
	
	public Baza(Integer idBaza, int numeroBaza, Pareja ganadores, int puntajePareja1, int puntajePareja2) {
		super();
		this.idBaza = idBaza;
		this.numeroBaza = numeroBaza;
		this.ganadores = ganadores;
		this.puntajePareja1 = puntajePareja1;
		this.puntajePareja2 = puntajePareja2;
	}
	
	public Baza() {
	}

	public Baza(int numeroBaza, Pareja ganadores, int puntajePareja1, int puntajePareja2, List<Turno> turnos) {
		super();
		this.numeroBaza = numeroBaza;
		this.ganadores = ganadores;
		this.puntajePareja1 = puntajePareja1;
		this.puntajePareja2 = puntajePareja2;
		this.turnos = turnos;
	}

	public int getNumero() {
		return numeroBaza;
	}
	public void setNumero(int numero) {
		this.numeroBaza = numero;
	}
	public Pareja getGanadores() {
		return ganadores;
	}
	public void setGanadores(Pareja ganadores) {
		this.ganadores = ganadores;
	}
	public int getPuntajePareja1() {
		return puntajePareja1;
	}
	public void setPuntajePareja1(int puntajePareja1) {
		this.puntajePareja1 = puntajePareja1;
	}
	public int getPuntajePareja2() {
		return puntajePareja2;
	}
	public void setPuntajePareja2(int puntajePareja2) {
		this.puntajePareja2 = puntajePareja2;
	}

	public boolean guardar(){
		return BazaDAO.getInstancia().guardar(this);
	}
	
	public BazaDTO toDTO() throws GrupoException {
		return new BazaDTO(numeroBaza, ganadores.toDTO(), puntajePareja1, puntajePareja2);
	}
	public Integer getIdBaza() {
		return idBaza;
	}
	public void setIdBaza(Integer idBaza) {
		this.idBaza = idBaza;
	}

	public List<Turno> getTurnos() {
		return turnos;
	}

	public void setTurnos(List<Turno> turnos) {
		this.turnos = turnos;
	}
	
	public void agregarTurno(Turno turno){
		if(turnos == null){
			turnos = new ArrayList<Turno>();
		}
		turnos.add(turno);
	}

	public int getNumJugEnvitePendiente() {
		return numJugEnvitePendiente;
	}

	public void setNumJugEnvitePendiente(int numJugEnvitePendiente) {
		this.numJugEnvitePendiente = numJugEnvitePendiente;
	}

	public boolean isBazaTerminada() {
		return bazaTerminada;
	}

	public void setBazaTerminada(boolean bazaTerminada) {
		this.bazaTerminada = bazaTerminada;
	}

	public Envite getEnviteActual() {
		return enviteActual;
	}

	public void setEnviteActual(Envite enviteActual) {
		this.enviteActual = enviteActual;
	}
	
	/**Aca vamos a evaluar la carta mas alta de la Baza, se seteará la pareja ganadora de la baza*/
	public void cartaMasAltaBaza(Baza bazaActual, Pareja pareja1, Pareja pareja2){
			Turno turno1 = bazaActual.getTurnos().get(0);
			Turno turno2 = bazaActual.getTurnos().get(1);
			Turno turno3 = bazaActual.getTurnos().get(2);
			Turno turno4 = bazaActual.getTurnos().get(3);
			Carta cartaJug1 = turno1.getCarta();
			Carta cartaJug2 = turno2.getCarta();
			Carta cartaJug3 = turno3.getCarta();
			Carta cartaJug4 = turno4.getCarta();
			
			if((cartaJug1.getValorJuego() > cartaJug2.getValorJuego() && cartaJug1.getValorJuego() > cartaJug4.getValorJuego()) || (cartaJug3.getValorJuego() > cartaJug2.getValorJuego() && cartaJug3.getValorJuego() > cartaJug4.getValorJuego())){
				if(cartaJug1.getValorJuego() > cartaJug3.getValorJuego()){
					pareja1.getJugador1().setNumeroTurnoPartido(1);
					pareja2.getJugador1().setNumeroTurnoPartido(2);
					pareja1.getJugador2().setNumeroTurnoPartido(3);
					pareja2.getJugador2().setNumeroTurnoPartido(4);
				}
				else if(cartaJug3.getValorJuego() > cartaJug1.getValorJuego()){
					pareja1.getJugador2().setNumeroTurnoPartido(1);
					pareja2.getJugador2().setNumeroTurnoPartido(2);
					pareja1.getJugador1().setNumeroTurnoPartido(3);
					pareja2.getJugador1().setNumeroTurnoPartido(4);
				}
				bazaActual.setGanadores(pareja1);
			}
			else if((cartaJug2.getValorJuego() > cartaJug1.getValorJuego() && cartaJug2.getValorJuego() > cartaJug3.getValorJuego()) || (cartaJug4.getValorJuego() > cartaJug1.getValorJuego() && cartaJug4.getValorJuego() > cartaJug3.getValorJuego())){
				if(cartaJug2.getValorJuego() > cartaJug4.getValorJuego()){
					pareja2.getJugador1().setNumeroTurnoPartido(1);
					pareja1.getJugador2().setNumeroTurnoPartido(2);
					pareja2.getJugador2().setNumeroTurnoPartido(3);
					pareja1.getJugador1().setNumeroTurnoPartido(4);
				}
				else if(cartaJug4.getValorJuego() > cartaJug2.getValorJuego()){
					pareja2.getJugador2().setNumeroTurnoPartido(1);
					pareja1.getJugador1().setNumeroTurnoPartido(2);
					pareja2.getJugador1().setNumeroTurnoPartido(3);
					pareja1.getJugador2().setNumeroTurnoPartido(4);
				}
				bazaActual.setGanadores(pareja2);
			}
			else{
				bazaActual.setGanadores(null);
			}
		}
}
