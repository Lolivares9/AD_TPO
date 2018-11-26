package negocio;

import java.util.ArrayList;
import java.util.List;

import dao.BazaDAO;
import dto.BazaDTO;
import enums.Envite;

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
	
	public BazaDTO toDTO(){
		if(idBaza == null) {
			return new BazaDTO(numeroBaza, ganadores.toDTO(), puntajePareja1, puntajePareja2);
		}else {
			return new BazaDTO(idBaza, numeroBaza, ganadores.toDTO(), puntajePareja1, puntajePareja2); 
		}
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
	public void calcularGanadorBaza(Pareja pareja1, Pareja pareja2,Mano manoActual){
			List<Baza> bazas = manoActual.getBazas();
			Turno turno1 = this.getTurnos().get(0);
			Turno turno2 = this.getTurnos().get(1);
			Turno turno3 = this.getTurnos().get(2);
			Turno turno4 = this.getTurnos().get(3);
			Carta cartaJug1Pareja1 = turno1.getCarta();
			Carta cartaJug1Pareja2 = turno2.getCarta();
			Carta cartaJug2Pareja1 = turno3.getCarta();
			Carta cartaJug2Pareja2 = turno4.getCarta();
			
			if((cartaJug1Pareja1.getValorJuego() > cartaJug1Pareja2.getValorJuego() && cartaJug1Pareja1.getValorJuego() > cartaJug2Pareja2.getValorJuego()) || (cartaJug2Pareja1.getValorJuego() > cartaJug1Pareja2.getValorJuego() && cartaJug2Pareja1.getValorJuego() > cartaJug2Pareja2.getValorJuego())){
				if(bazas.size() == 2){
					//ESTO QUIERE DECIR QUE SI HICIERON PARDA EN LA ANTERIOR Y EN ESTA GANA LA PAREJA 1, GANA LA MANO
					if(bazas.get(0).getGanadores().getIdPareja() == null){
						this.setGanadores(pareja1);
						this.setBazaTerminada(true);
						return;
					}
				}
				if(cartaJug1Pareja1.getValorJuego() > cartaJug2Pareja1.getValorJuego()){
					pareja1.getJugador1().setNumeroTurnoPartido(1);
					pareja2.getJugador1().setNumeroTurnoPartido(2);
					pareja1.getJugador2().setNumeroTurnoPartido(3);
					pareja2.getJugador2().setNumeroTurnoPartido(4);
				}
				else if(cartaJug2Pareja1.getValorJuego() > cartaJug1Pareja1.getValorJuego()){
					pareja1.getJugador2().setNumeroTurnoPartido(1);
					pareja2.getJugador2().setNumeroTurnoPartido(2);
					pareja1.getJugador1().setNumeroTurnoPartido(3);
					pareja2.getJugador1().setNumeroTurnoPartido(4);
				}
				/*ESTE CASO ES CUANDO GANA LA PAREJA, PERO ADEMAS HAY PARDA ENTRE ELLOS, EN ESTE CASO
				  EL QUE VA A TENER EL PRIMER TURNO, ES EL DE LA DERECHA DE LA MANO
				*/
				else if(cartaJug1Pareja1.getValorJuego() == cartaJug2Pareja1.getValorJuego()){
				//SI NINGUNO ERA MANO
					if(pareja1.getJugador1().getNumeroTurnoPartido() != 1 && pareja1.getJugador2().getNumeroTurnoPartido() != 1){
						//SI NINGUNO ES MANO,SIGNIFICA QUE LA MANO ESTA EN LA PAREJA 2, TENGO QUE AGARRAR EL QUE ESTA A LA DERECHA DE LA MANO
						if(pareja2.getJugador1().getNumJugador() == 1){
							pareja1.getJugador2().setNumeroTurnoPartido(1);
							pareja2.getJugador2().setNumeroTurnoPartido(2);
							pareja1.getJugador1().setNumeroTurnoPartido(3);
							pareja2.getJugador1().setNumeroTurnoPartido(4);
						}
						else{
							pareja1.getJugador1().setNumeroTurnoPartido(1);
							pareja2.getJugador1().setNumeroTurnoPartido(2);
							pareja1.getJugador2().setNumeroTurnoPartido(3);
							pareja2.getJugador2().setNumeroTurnoPartido(4);
						}
					}
					//SI ALGUNO DE LOS 2 ES MANO
					else if(pareja1.getJugador1().getNumeroTurnoPartido() == 1 || pareja1.getJugador2().getNumeroTurnoPartido() == 1){
						if(pareja1.getJugador1().getNumeroTurnoPartido() == 1){
							pareja2.getJugador1().setNumeroTurnoPartido(1);
							pareja1.getJugador2().setNumeroTurnoPartido(2);
							pareja2.getJugador2().setNumeroTurnoPartido(3);
							pareja1.getJugador1().setNumeroTurnoPartido(4);
						}
						else{
							pareja2.getJugador2().setNumeroTurnoPartido(1);
							pareja1.getJugador1().setNumeroTurnoPartido(2);
							pareja2.getJugador1().setNumeroTurnoPartido(3);
							pareja1.getJugador2().setNumeroTurnoPartido(4);
						}
					}
				}
				bazaTerminada = true;
				this.setGanadores(pareja1);
			}
			else if((cartaJug1Pareja2.getValorJuego() > cartaJug1Pareja1.getValorJuego() && cartaJug1Pareja2.getValorJuego() > cartaJug2Pareja1.getValorJuego()) || (cartaJug2Pareja2.getValorJuego() > cartaJug1Pareja1.getValorJuego() && cartaJug2Pareja2.getValorJuego() > cartaJug2Pareja1.getValorJuego())){
				if(bazas.size() == 2){
					//ESTO QUIERE DECIR QUE SI HICIERON PARDA EN LA ANTERIOR Y EN ESTA GANA LA PAREJA 1, GANA LA MANO
					if(bazas.get(0).getGanadores().getIdPareja() == null){
						this.setGanadores(pareja2);
						this.setBazaTerminada(true);
						return;
					}
				}
				if(cartaJug1Pareja2.getValorJuego() > cartaJug2Pareja2.getValorJuego()){
					pareja2.getJugador1().setNumeroTurnoPartido(1);
					pareja1.getJugador2().setNumeroTurnoPartido(2);
					pareja2.getJugador2().setNumeroTurnoPartido(3);
					pareja1.getJugador1().setNumeroTurnoPartido(4);
				}
				else if(cartaJug2Pareja2.getValorJuego() > cartaJug1Pareja2.getValorJuego()){
					pareja2.getJugador2().setNumeroTurnoPartido(1);
					pareja1.getJugador1().setNumeroTurnoPartido(2);
					pareja2.getJugador1().setNumeroTurnoPartido(3);
					pareja1.getJugador2().setNumeroTurnoPartido(4);
				}
				/*ESTE CASO ES CUANDO GANA LA PAREJA, PERO ADEMAS HAY PARDA ENTRE ELLOS, EN ESTE CASO
				  EL QUE VA A TENER EL PRIMER TURNO, ES EL DE LA DERECHA DE LA MANO
				*/
				else if(cartaJug1Pareja2.getValorJuego() == cartaJug2Pareja2.getValorJuego()){
					//SI NINGUNO ES MANO,SIGNIFICA QUE LA MANO ESTA EN LA PAREJA 1, TENGO QUE AGARRAR EL QUE ESTA A LA DERECHA DE LA MANO
					if(pareja2.getJugador1().getNumeroTurnoPartido() != 1 && pareja2.getJugador2().getNumeroTurnoPartido() != 1){
						if(pareja1.getJugador1().getNumJugador() == 1){
							pareja2.getJugador1().setNumeroTurnoPartido(1);
							pareja1.getJugador2().setNumeroTurnoPartido(2);
							pareja2.getJugador2().setNumeroTurnoPartido(3);
							pareja1.getJugador1().setNumeroTurnoPartido(4);
						}
						else{
							pareja2.getJugador2().setNumeroTurnoPartido(1);
							pareja1.getJugador1().setNumeroTurnoPartido(2);
							pareja2.getJugador1().setNumeroTurnoPartido(3);
							pareja1.getJugador2().setNumeroTurnoPartido(4);
						}
					}
					//SI ALGUNO DE LOS 2 ES MANO
					else if(pareja2.getJugador1().getNumeroTurnoPartido() == 1 || pareja2.getJugador2().getNumeroTurnoPartido() == 1){
						if(pareja2.getJugador1().getNumeroTurnoPartido() == 1){
							pareja1.getJugador2().setNumeroTurnoPartido(1);
							pareja2.getJugador2().setNumeroTurnoPartido(2);
							pareja1.getJugador1().setNumeroTurnoPartido(3);
							pareja2.getJugador1().setNumeroTurnoPartido(4);
						}
						else{
							pareja1.getJugador1().setNumeroTurnoPartido(1);
							pareja2.getJugador1().setNumeroTurnoPartido(2);
							pareja1.getJugador2().setNumeroTurnoPartido(3);
							pareja2.getJugador2().setNumeroTurnoPartido(4);
						}
					}
				}
				bazaTerminada = true;
				this.setGanadores(pareja2);
			}
			/*
			 * CASOS DE EMPATES/PARDA:
			 * 1) BAZA 1 EN PARDA = EL QUE GANA LA BAZA 2 GANA LA MANO
			 * 2) BAZA 2 EN PARDA Y BAZA 1 CON GANADOR = GANA LA MANO LA PAREJA QUE HAYA GANADO LA BAZA 1
			 * 3) BAZA 3 EN PARDA Y BAZA 1 Y 2 CON DIFERENTES GANADORES = GANA LA MANO EL QUE HAYA GANADO LA BAZA 1
			 * 4) BAZA 1 Y BAZA 2 EN PARDA AMBAS SIN GANADORES = GANA LA MANO EL QUE GANE LA BAZA 3
			 * 5) BAZA 1 Y BAZA 2 Y BAZA 3 EN PARDA = GANA LA MANO LA PAREJA QUE ES MANO, ES DECIR LA PAREJA DONDE
				  EL JUG TIENE NUMERO DE TURNO = 1
			 * */
			else{
				if((cartaJug1Pareja1.getValorJuego() <= cartaJug1Pareja2.getValorJuego() || cartaJug1Pareja1.getValorJuego() <= cartaJug2Pareja2.getValorJuego()) && (cartaJug2Pareja1.getValorJuego() <= cartaJug1Pareja2.getValorJuego() || cartaJug2Pareja1.getValorJuego() <= cartaJug2Pareja2.getValorJuego())){
					if(this.numeroBaza == 1){
						//ACA NO HAY GANADOR, Y LOS TURNOS SIGUEN IGUALES COMO AL PRINCIPIO
						this.setBazaTerminada(true);
						this.setGanadores(null);
						return;
					}
					else if(this.numeroBaza == 2){
						//PARDA CASO 1
						if(bazas.get(0).getGanadores() != null){
							if((cartaJug1Pareja1.getValorJuego() >= cartaJug1Pareja2.getValorJuego() && cartaJug1Pareja1.getValorJuego() >= cartaJug2Pareja2.getValorJuego()) || (cartaJug2Pareja1.getValorJuego() >= cartaJug1Pareja2.getValorJuego() && cartaJug2Pareja1.getValorJuego() >= cartaJug2Pareja2.getValorJuego())){
								if(bazas.get(0).getGanadores().getIdPareja().equals(pareja1.getIdPareja())){
									this.setGanadores(pareja1);
									manoActual.setParejaGanadora(pareja1);
								}
								else if(bazas.get(0).getGanadores().getIdPareja().equals(pareja2.getIdPareja())){
									this.setGanadores(pareja2);
									manoActual.setParejaGanadora(pareja2);
								}
							}
							else if((cartaJug1Pareja2.getValorJuego() >= cartaJug1Pareja1.getValorJuego() && cartaJug1Pareja2.getValorJuego() >= cartaJug2Pareja1.getValorJuego()) || (cartaJug2Pareja2.getValorJuego() >= cartaJug1Pareja1.getValorJuego() && cartaJug2Pareja2.getValorJuego() >= cartaJug2Pareja1.getValorJuego())){
								if(bazas.get(0).getGanadores().getIdPareja().equals(pareja2.getIdPareja())){
									this.setGanadores(pareja2);
									manoActual.setParejaGanadora(pareja2);
								}
								else if(bazas.get(0).getGanadores().getIdPareja().equals(pareja1.getIdPareja())){
									this.setGanadores(pareja1);
									manoActual.setParejaGanadora(pareja1);
								}
							}
						}
						//PARDA CASO 2
						else if(bazas.get(0).getGanadores() == null){
							if((cartaJug1Pareja1.getValorJuego() > cartaJug1Pareja2.getValorJuego() && cartaJug1Pareja1.getValorJuego() > cartaJug2Pareja2.getValorJuego()) || (cartaJug2Pareja1.getValorJuego() > cartaJug1Pareja2.getValorJuego() && cartaJug2Pareja1.getValorJuego() > cartaJug2Pareja2.getValorJuego())){
								this.setGanadores(pareja1);
								manoActual.setParejaGanadora(pareja1);
							}
							else if((cartaJug1Pareja2.getValorJuego() > cartaJug1Pareja1.getValorJuego() && cartaJug1Pareja2.getValorJuego() > cartaJug2Pareja1.getValorJuego()) || (cartaJug2Pareja2.getValorJuego() > cartaJug1Pareja1.getValorJuego() && cartaJug2Pareja2.getValorJuego() > cartaJug2Pareja1.getValorJuego())){
								this.setGanadores(pareja2);
								manoActual.setParejaGanadora(pareja2);
							}
						}
						if(this.numeroBaza == 3){
							//PARDA CASO 3
							if(bazas.get(0).getGanadores().getIdPareja() != bazas.get(1).getGanadores().getIdPareja()){
								if(bazas.get(0).getGanadores().getIdPareja() == pareja1.getIdPareja()){
									this.setGanadores(pareja1);
									manoActual.setParejaGanadora(pareja1);
								}
								else{
									this.setGanadores(pareja2);
									manoActual.setParejaGanadora(pareja2);
								}
							}
							//PARDA CASO 4
							else if(bazas.get(0).getGanadores().getIdPareja() == null && bazas.get(1).getGanadores().getIdPareja() == null){
								if((cartaJug1Pareja1.getValorJuego() > cartaJug1Pareja2.getValorJuego() && cartaJug1Pareja1.getValorJuego() > cartaJug2Pareja2.getValorJuego()) || (cartaJug2Pareja1.getValorJuego() > cartaJug1Pareja2.getValorJuego() && cartaJug2Pareja1.getValorJuego() > cartaJug2Pareja2.getValorJuego())){
									this.setGanadores(pareja1);
									manoActual.setParejaGanadora(pareja1);
								}
								else if((cartaJug1Pareja2.getValorJuego() > cartaJug1Pareja1.getValorJuego() && cartaJug1Pareja2.getValorJuego() > cartaJug2Pareja1.getValorJuego()) || (cartaJug2Pareja2.getValorJuego() > cartaJug1Pareja1.getValorJuego() && cartaJug2Pareja2.getValorJuego() > cartaJug2Pareja1.getValorJuego())){
									this.setGanadores(pareja2);
									manoActual.setParejaGanadora(pareja2);
								}
							}
							//PARDA CASO 5
							else if(bazas.get(0).getGanadores().getIdPareja() == null && bazas.get(1).getGanadores().getIdPareja() == null && this.ganadores == null){
								if(pareja1.getJugador1().getNumeroTurnoPartido() == 1 || pareja1.getJugador2().getNumeroTurnoPartido() == 1){
									this.setGanadores(pareja1);
									manoActual.setParejaGanadora(pareja1);
								}
								else{
									this.setGanadores(pareja2);
									manoActual.setParejaGanadora(pareja2);
								}
							}
						}
					}
				}
				this.setBazaTerminada(true);
				this.setGanadores(null);
			}
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
		}
	}
	
	private void setearPuntajeEnvidoNOquerido(Partido p, Turno turnoEnvite, int puntaje) {
		if(p.getParejas().get(0).getJugador1().equals(turnoEnvite.getJugador()) || p.getParejas().get(0).getJugador2().equals(turnoEnvite.getJugador())){
			this.setPuntajePareja1(this.getPuntajePareja1() + puntaje);
		}
		else{
			this.setPuntajePareja2(puntajePareja1  + puntaje);
		}
	}



	public boolean analizarEnviteJuego(Partido partidoNegocio, Turno turnoEnvite,Chico chicoActual, Mano manoActual) {
		Pareja pareja1 = partidoNegocio.getParejas().get(0);
		Pareja pareja2 = partidoNegocio.getParejas().get(1);
		//*******COMENZAMOS CON EL TRUCO**********
		
				if(turnoEnvite.getEnviteJuego().equals(Envite.Truco)){
					return true;
				}
				else if(turnoEnvite.getEnviteJuego().equals(Envite.Truco_Querido)){
					if(setearPuntajeTruco(partidoNegocio,2,chicoActual,manoActual)){
						return true;
					}
					return false;
					//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
					//PAREJA GANADORA +2 PUNTOS
				}
				else if(turnoEnvite.getEnviteJuego().equals(Envite.Truco_QuieroRetruco_Querido)){
					if(setearPuntajeTruco(partidoNegocio,3,chicoActual,manoActual)){
						return true;
					}
					return false;
					//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
					//PAREJA GANADORA +3 PUNTOS
				}
				else if(turnoEnvite.getEnviteJuego().equals(Envite.Truco_QuieroRetruco_QuieroValeCuatro_Querido)){
					if(setearPuntajeTruco(partidoNegocio,4,chicoActual,manoActual)){
						return true;
					}
					return false;
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
					if(this.getNumero() == 1){
						if(pareja1.getJugador1().equals(turnoEnvite.getJugador()) || pareja1.getJugador2().equals(turnoEnvite.getJugador())){
							this.setPuntajePareja2(this.getPuntajePareja2() + 1);
						}
						else{
							this.setPuntajePareja1(this.getPuntajePareja1() + 1);
						}
					}else{
						if(pareja1.getJugador1().equals(turnoEnvite.getJugador()) || pareja1.getJugador2().equals(turnoEnvite.getJugador())){
							this.setPuntajePareja2(this.getPuntajePareja2() + 1);
						}
						else{
							this.setPuntajePareja1(this.getPuntajePareja1() + 1);
						}
					}
					//ACA SI ES LA PRIMER BAZA, LA PAREJA QUE (NO) SE VA AL MAZO VA A GANAR 2 PUNTOS, SINO, GANA SOLAMENTE 1
				}
				
				//*******NADA**********

				else if(turnoEnvite.getEnviteJuego().equals(Envite.Nada)){
//					if(this.getTurnos().size() == 4){
//						Turno turno1 = this.getTurnos().get(0);
//						Turno turno2 = this.getTurnos().get(1);
//						Turno turno3 = this.getTurnos().get(2);
//						Turno turno4 = this.getTurnos().get(3);
//						Carta cartaJug1 = turno1.getCarta();
//						Carta cartaJug2 = turno2.getCarta();
//						Carta cartaJug3 = turno3.getCarta();
//						Carta cartaJug4 = turno4.getCarta();
//						if (cartaJug1 != null && cartaJug2 != null && cartaJug3 != null && cartaJug4 != null ) {
//							calcularGanadorBaza(partidoNegocio.getParejas().get(0), partidoNegocio.getParejas().get(1),manoActual);
//						}
//					}
//					
//					if(this.getGanadores() != null){
//						if(this.getGanadores().getIdPareja() != null){
//							this.setBazaTerminada(true);
//						}
//					}
					setearPuntajeTruco(partidoNegocio,1,chicoActual,manoActual);
				}
				//SI ES EL ULTIMO TURNO, ES DECIR EL JUGADOR NUMERO 4, ME FIJO A VER QUE PAREJA GANA ESA BAZA, BUSCANDO LA CARTA MAS ALTA
				//LA PAREJA QUE GANE MAS DE 1 BAZA, GANA LA MANO
				return true;
	}
	
	/**Si este metodo devuelve false, es porque a alguno de los jugadores le falta jugar una carta, para que se pueda evaluar los ganadores*/
	private boolean setearPuntajeTruco(Partido p, int puntaje,Chico chicoA,Mano manoA){
		boolean faltaJugarCarta = false;
		int indiceChicoActual = chicoA.getNumero()-1;
		int indiceManoActual = manoA.getNumeroMano()-1;
		if (this.getTurnos().size() < 4) {
			faltaJugarCarta = true;
		}
		else {
			for(int i = 0;i<this.getTurnos().size();i++){
				if(this.getTurnos().get(i).getCarta() == null){
					faltaJugarCarta = true;
					break;
				}
			}
		}
		if(faltaJugarCarta == false){
			this.calcularGanadorBaza(p.getParejas().get(0), p.getParejas().get(1),manoA);
			Pareja pareja1 = p.getParejas().get(0);
			Pareja pareja2 = p.getParejas().get(1);
			Mano manoActual = p.getChico().get(indiceChicoActual).getManos().get(indiceManoActual);
			
			if (manoActual.getBazas().size() == 2) {
				//GANO BAZA 1 Y BAZA 2 PAREJA 1
				if(this.getGanadores() != null  && this.getGanadores().getIdPareja().equals(pareja1.getIdPareja())  && this.numeroBaza == 2 && (manoActual.getBazas().get(0).getGanadores() != null && manoActual.getBazas().get(0).getGanadores().getIdPareja().equals(pareja1.getIdPareja()))){
					puntajePareja1 = puntajePareja1 + puntaje;
				}
				//GANO BAZA 1 Y BAZA 2 PAREJA 2
				else if(this.getGanadores() != null  && this.getGanadores().getIdPareja().equals(pareja2.getIdPareja())  && this.numeroBaza == 2 && (manoActual.getBazas().get(0).getGanadores() != null && manoActual.getBazas().get(0).getGanadores().getIdPareja().equals(pareja2.getIdPareja()))){
					puntajePareja2 = puntajePareja2 + puntaje;
				}
				//SI CORRESPONDE A LA BAZA PARDA, VERIFICA SI YA SE JUGO OTRA BAZA Y ESTA SOLO ESTA DESEMPATANDO
				else if (ganadores == null) {
					for (Baza b: manoActual.getBazas()) {
						if (b.getGanadores() != null && b.getGanadores().getIdPareja() != null) {
							if (b.getGanadores().getIdPareja().equals(pareja1.getIdPareja())) {
								puntajePareja1 = puntajePareja1 + puntaje;
							}
							else if (b.getGanadores().getIdPareja().equals(pareja2.getIdPareja())) {
								puntajePareja2 = puntajePareja2 + puntaje;
							}
							break;
						}
					}
				}
			}
			else if (manoActual.getBazas().size() == 3) {
				//GANO BAZA 2 Y BAZA 3 PAREJA 1
				if(this.getGanadores() != null  && this.getGanadores().getIdPareja().equals(pareja1.getIdPareja()) && this.numeroBaza == 3 && (manoActual.getBazas().get(1).getGanadores() != null && manoActual.getBazas().get(1).getGanadores().getIdPareja().equals(pareja1.getIdPareja()))){
					puntajePareja1 = puntajePareja1 + puntaje;
				}
				//GANO BAZA 2 Y BAZA 3 PAREJA 2
				else if(this.getGanadores() != null && this.getGanadores().getIdPareja().equals(pareja2.getIdPareja())  &&  this.numeroBaza == 3 && (manoActual.getBazas().get(1).getGanadores() != null && manoActual.getBazas().get(1).getGanadores().getIdPareja().equals(pareja2.getIdPareja()))){
					puntajePareja2 = puntajePareja2 + puntaje;
				}
				
				
				//GANO BAZA 1 Y BAZA 3 PAREJA 1
				else if(this.getGanadores() != null &&  this.getGanadores().getIdPareja().equals(pareja1.getIdPareja()) && this.numeroBaza == 3 && (manoActual.getBazas().get(0).getGanadores() != null && manoActual.getBazas().get(0).getGanadores().getIdPareja().equals(pareja1.getIdPareja()))){
					puntajePareja1 = puntajePareja1 + puntaje;
				}
				//GANO BAZA 1 Y BAZA 3 PAREJA 2
				else if(this.getGanadores() != null && this.getGanadores().getIdPareja().equals(pareja2.getIdPareja())  &&  this.numeroBaza == 3 && (manoActual.getBazas().get(0).getGanadores() != null && manoActual.getBazas().get(0).getGanadores().getIdPareja().equals(pareja2.getIdPareja()))){
					puntajePareja2 = puntajePareja2 + puntaje;
				}
				//SI CORRESPONDE A LA BAZA PARDA, VERIFICA SI YA SE JUGO OTRA BAZA Y ESTA SOLO ESTA DESEMPATANDO
				else if (ganadores == null) {
					for (Baza b: manoActual.getBazas()) {
						if (b.getGanadores() != null && b.getGanadores().getIdPareja() != null) {
							if (b.getGanadores().getIdPareja().equals(pareja1.getIdPareja())) {
								puntajePareja1 = puntajePareja1 + puntaje;
							}
							else if (b.getGanadores().getIdPareja().equals(pareja2.getIdPareja())) {
								puntajePareja2 = puntajePareja2 + puntaje;
							}
							break;
						}
					}
				}
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
