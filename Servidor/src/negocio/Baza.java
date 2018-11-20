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
	public void calcularGanadorBaza(Pareja pareja1, Pareja pareja2){
			Turno turno1 = this.getTurnos().get(0);
			Turno turno2 = this.getTurnos().get(1);
			Turno turno3 = this.getTurnos().get(2);
			Turno turno4 = this.getTurnos().get(3);
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
				this.setGanadores(pareja1);
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
				this.setGanadores(pareja2);
			}
			else{
				this.setGanadores(null);
			}
			
			//una vez que se calculo la pareja ganadora evaluo los puntajes
			/*if(bazaActual.getGanadores() != null){
				if (bazaActual.getGanadores().equals(pareja1)) {
					bazaActual.setPuntajePareja1(bazaActual.getPuntajePareja1() + 1 );
				}
				else {
					bazaActual.setPuntajePareja2(bazaActual.getPuntajePareja2() + 1 );
				}
			}*/
		}

	public boolean analizarEnviteTantos(Partido partidoNegocio, Turno turnoEnvite) {
		//*******COMENZAMOS CON EL ENVIDO**********
		
				if(turnoEnvite.getEnviteTantos().equals(Envite.Envido)){
					return true;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.Envido_Querido)){
					setearPuntajeEnvido(partidoNegocio,2,false);
					//PAREJA GANADORA +2 PUNTOS
					return true;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.Envido_RealEnvido_Querido)){
					setearPuntajeEnvido(partidoNegocio,5,false);
					partidoNegocio.actualizar();
					//PAREJA GANADORA +5 PUNTOS
					return true;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.Envido_RealEnvido_FaltaEnvido_Querido)){
					setearPuntajeEnvido(partidoNegocio,30,true);
					return true;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.Envido_FaltaEnvido_Querido)){
					setearPuntajeEnvido(partidoNegocio,0,true);
					//PAREJA GANADORA GANA CHICO
					return false;
				}

				else if(turnoEnvite.getEnviteTantos().equals(Envite.Envido_NoQuerido)){
					setearPuntajeEnvidoNOquerido(partidoNegocio,turnoEnvite,1);
					//PAREJA GANADORA +1 PUNTOS
					return true;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.Envido_RealEnvido_NoQuerido)){
					setearPuntajeEnvidoNOquerido(partidoNegocio,turnoEnvite,3);
					//PAREJA GANADORA +3 PUNTOS
					return true;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.Envido_FaltaEnvido_NoQuerido)){
					setearPuntajeEnvidoNOquerido(partidoNegocio,turnoEnvite,5);
					//PAREJA GANADORA +5 PUNTOS
					return true;
				}
				
				
				
				//*******COMENZAMOS CON EL ENVIDO ENVIDO**********
				
				if(turnoEnvite.getEnviteTantos().equals(Envite.Envido_Envido)){
					return true;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.EnvidoEnvido_Querido)){
					setearPuntajeEnvido(partidoNegocio,4,false);
					//PAREJA GANADORA +4 PUNTOS
					return false;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.EnvidoEnvido_RealEnvido_Querido)){
					setearPuntajeEnvido(partidoNegocio,7,false);
					//PAREJA GANADORA +7 PUNTOS
					return false;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.EnvidoEnvido_FaltaEnvido_Querido)){
					setearPuntajeEnvido(partidoNegocio,0,true);
					//PAREJA GANADORA GANA CHICO
					return false;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.EnvidoEnvido_RealEnvido_FaltaEnvido_Querido)){
					setearPuntajeEnvido(partidoNegocio,0,true);				
					//PAREJA GANADORA GANA CHICO
					return false;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.EnvidoEnvido_NoQuerido)){
					setearPuntajeEnvidoNOquerido(partidoNegocio,turnoEnvite,2);
					//PAREJA GANADORA +2 PUNTOS
					return false;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.EnvidoEnvido_RealEnvido_NoQuerido)){
					setearPuntajeEnvidoNOquerido(partidoNegocio,turnoEnvite,4);
					//PAREJA GANADORA +4 PUNTOS
					return false;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.EnvidoEnvido_FaltaEnvido_NoQuerido)){
					setearPuntajeEnvidoNOquerido(partidoNegocio,turnoEnvite,4);
					//PAREJA GANADORA +4 PUNTOS
					return false;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.EnvidoEnvido_RealEnvido_FaltaEnvido_NoQuerido)){
					setearPuntajeEnvidoNOquerido(partidoNegocio,turnoEnvite,7);
					//PAREJA GANADORA +7 PUNTOS
					return false;
				}

				
				
				//*******COMENZAMOS CON EL REAL ENVIDO**********
				
				if(turnoEnvite.getEnviteTantos().equals(Envite.Real_Envido)){
					return true;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.RealEnvido_Querido)){
					setearPuntajeEnvido(partidoNegocio,3,false);
					//PAREJA GANADORA +3 PUNTOS
					return false;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.RealEnvido_FaltaEnvido_Querido)){
					setearPuntajeEnvido(partidoNegocio,30,true);
					//PAREJA GANADORA GANA PARTIDO
					return false;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.RealEnvido_NoQuerido)){
					setearPuntajeEnvidoNOquerido(partidoNegocio,turnoEnvite,1);
					//PAREJA GANADORA +1 PUNTOS
					return false;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.RealEnvido_FaltaEnvido_NoQuerido)){
					setearPuntajeEnvidoNOquerido(partidoNegocio,turnoEnvite,3);
					//PAREJA GANADORA +3 PUNTOS
					return false;
				}

				
				
				//*******COMENZAMOS CON EL FALTA ENVIDO**********
				
				if(turnoEnvite.getEnviteTantos().equals(Envite.Falta_Envido)){
					return true;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.FaltaEnvido_Querido)){
					setearPuntajeEnvido(partidoNegocio,3,true);
					//PAREJA GANADORA GANA CHICO
					return false;
				}
				else if(turnoEnvite.getEnviteTantos().equals(Envite.FaltaEnvido_NoQuerido)){
					setearPuntajeEnvidoNOquerido(partidoNegocio,turnoEnvite,2);
					//PAREJA GANADORA +2 PUNTOS
					return false;
				}
				return true;
	}
	
	private void setearPuntajeEnvido(Partido p, int puntaje, boolean finaliza_chico) {
		if(finaliza_chico){
			if(p.getParejas().get(0).equals(Pareja.calcularTantoParejas(p.getParejas().get(0), p.getParejas().get(1)))){
				this.setPuntajePareja1(30);
				this.setGanadores(p.getParejas().get(0));
				this.setBazaTerminada(true);
			}
			else{
				this.setPuntajePareja2(30);
				this.setGanadores(p.getParejas().get(1));
				this.setBazaTerminada(true);
			}
		}else{
			if(p.getParejas().get(0).equals(Pareja.calcularTantoParejas(p.getParejas().get(0),p.getParejas().get(1)))){
				this.setPuntajePareja1(puntajePareja1 + puntaje);
			}else {
				this.setPuntajePareja2(puntajePareja2 + puntaje);
			}
			verificarSiFinalizaBaza(p);
		}
	}
	
	private void setearPuntajeEnvidoNOquerido(Partido p, Turno turnoEnvite, int puntaje) {
		if(p.getParejas().get(0).getJugador1().equals(turnoEnvite.getJugador()) || p.getParejas().get(0).getJugador2().equals(turnoEnvite.getJugador())){
			this.setPuntajePareja1(this.getPuntajePareja1() + puntaje);
		}
		else{
			this.setPuntajePareja2(puntajePareja1  + puntaje);
			
		}
		verificarSiFinalizaBaza(p);
	}

	private void verificarSiFinalizaBaza(Partido p) {
		if (this.getPuntajePareja1() == 30) {
			this.setGanadores(p.getParejas().get(0));
			this.setBazaTerminada(true);
		}
		else if (this.getPuntajePareja2() == 30) {
			this.setGanadores(p.getParejas().get(1));
			this.setBazaTerminada(true);
		}
	}

	public boolean analizarEnviteJuego(Partido partidoNegocio, Turno turnoEnvite) {
		Pareja pareja1 = partidoNegocio.getParejas().get(0);
		Pareja pareja2 = partidoNegocio.getParejas().get(1);
		//*******COMENZAMOS CON EL TRUCO**********
		
				if(turnoEnvite.getEnviteJuego().equals(Envite.Truco)){
					return true;
				}
				else if(turnoEnvite.getEnviteJuego().equals(Envite.Truco_Querido)){
					if(setearPuntajeTruco(partidoNegocio,2)){
						return true;
					}
					//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
					//PAREJA GANADORA +2 PUNTOS
				}
				else if(turnoEnvite.getEnviteJuego().equals(Envite.Truco_QuieroRetruco_Querido)){
					if(setearPuntajeTruco(partidoNegocio,3)){
						return true;
					}
					//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
					//PAREJA GANADORA +3 PUNTOS
				}
				else if(turnoEnvite.getEnviteJuego().equals(Envite.Truco_QuieroRetruco_QuieroValeCuatro_Querido)){
					if(setearPuntajeTruco(partidoNegocio,4)){
						return true;
					}
					//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
					//PAREJA GANADORA +4 PUNTOS
				}
				else if(turnoEnvite.getEnviteJuego().equals(Envite.Truco_NoQuerido)){
					if(pareja1.getJugador1().equals(turnoEnvite.getJugador()) || pareja1.getJugador2().equals(turnoEnvite.getJugador())){
						setearPuntajeTrucoNoQuerido(partidoNegocio, 1, pareja1);
					}
					else{
						setearPuntajeTrucoNoQuerido(partidoNegocio,1, pareja2);
					}
					//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
					//PAREJA GANADORA +1 PUNTOS
				}
				else if(turnoEnvite.getEnviteJuego().equals(Envite.Truco_QuieroRetruco_NoQuerido)){
					if(pareja1.getJugador1().equals(turnoEnvite.getJugador()) || pareja1.getJugador2().equals(turnoEnvite.getJugador())){
						setearPuntajeTrucoNoQuerido(partidoNegocio, 2, pareja1);
					}
					else{
						setearPuntajeTrucoNoQuerido(partidoNegocio,2, pareja2);
					}
					//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
					//PAREJA GANADORA +2 PUNTOS
				}
				else if(turnoEnvite.getEnviteJuego().equals(Envite.Truco_QuieroRetruco_QuieroValeCuatro_NoQuerido)){
					if(pareja1.getJugador1().equals(turnoEnvite.getJugador()) || pareja1.getJugador2().equals(turnoEnvite.getJugador())){
						setearPuntajeTrucoNoQuerido(partidoNegocio,3, pareja1);
					}
					else{
						setearPuntajeTrucoNoQuerido(partidoNegocio,3, pareja2);
					}
					//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
					//PAREJA GANADORA +3 PUNTOS
				}
				
				//*******MAZO**********
				
				else if(turnoEnvite.getEnviteJuego().equals(Envite.Mazo)){
					if(this.getNumero() == 0){
						setearPuntajeEnvido(partidoNegocio,0,true);
					}else{
						if(pareja1.getJugador1().equals(turnoEnvite.getJugador()) || pareja1.getJugador2().equals(turnoEnvite.getJugador())){
							this.setPuntajePareja1(this.getPuntajePareja1() + 1);
						}
						else{
							this.setPuntajePareja2(this.getPuntajePareja1() + 1);
						}
					}
					//ACA SI ES LA PRIMER BAZA, LA PAREJA QUE (NO) SE VA AL MAZO VA A GANAR 2 PUNTOS, SINO, GANA SOLAMENTE 1
				}
				
				//*******NADA**********

				else if(turnoEnvite.getEnviteJuego().equals(Envite.Nada)){
					if(this.getTurnos().size() == 4){
						Turno turno1 = this.getTurnos().get(0);
						Turno turno2 = this.getTurnos().get(1);
						Turno turno3 = this.getTurnos().get(2);
						Turno turno4 = this.getTurnos().get(3);
						Carta cartaJug1 = turno1.getCarta();
						Carta cartaJug2 = turno2.getCarta();
						Carta cartaJug3 = turno3.getCarta();
						Carta cartaJug4 = turno4.getCarta();
						if (cartaJug1 != null && cartaJug2 != null && cartaJug3 != null && cartaJug4 != null ) {
							calcularGanadorBaza(partidoNegocio.getParejas().get(0), partidoNegocio.getParejas().get(1));
						}
					}
					
					if(this.getGanadores().getIdPareja() != null){
						if (this.getGanadores().equals(pareja1)) {
							
							this.setPuntajePareja1(this.getPuntajePareja1() + 1 );
						}
						else {
							this.setPuntajePareja2(this.getPuntajePareja2() + 1 );
						}
						this.setBazaTerminada(true);
					}
				}
				//SI ES EL ULTIMO TURNO, ES DECIR EL JUGADOR NUMERO 4, ME FIJO A VER QUE PAREJA GANA ESA BAZA, BUSCANDO LA CARTA MAS ALTA
				//LA PAREJA QUE GANE MAS DE 1 BAZA, GANA LA MANO
				return true;
	}
	
	/**Si este metodo devuelve false, es porque a alguno de los jugadores le falta jugar una carta, para que se pueda evaluar los ganadores*/
	private boolean setearPuntajeTruco(Partido p, int puntaje){
		boolean faltaJugarCarta = false;
		boolean finaliza_mano = false;
		for(int i = 0;i<this.getTurnos().size();i++){
			if(this.getTurnos().get(i).getCarta() == null){
				faltaJugarCarta = true;
				break;
			}
		}
		if(faltaJugarCarta == false){
			this.calcularGanadorBaza(p.getParejas().get(0), p.getParejas().get(1));
			if(this.getGanadores() != null){
				this.setBazaTerminada(true);
			}
		}
		return faltaJugarCarta;
	}
	

	private void setearPuntajeTrucoNoQuerido(Partido p, int puntaje, Pareja parejaGanadora){
		Pareja pareja1 = p.getParejas().get(0);
		Pareja pareja2 = p.getParejas().get(1);
		if(pareja1.equals(pareja1)){
			this.setBazaTerminada(true);
			this.setGanadores(pareja1);
			this.setPuntajePareja1(this.getPuntajePareja1() + puntaje);
		}
		else{
			this.setBazaTerminada(true);
			this.setGanadores(pareja2);
			this.setPuntajePareja2(this.getPuntajePareja2() + puntaje);
		}
		p.actualizar();
	}
}
