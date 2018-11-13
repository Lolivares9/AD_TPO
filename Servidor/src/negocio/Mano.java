package negocio;

import java.util.ArrayList;
import java.util.List;

import dao.ManoDAO;
import dao.PartidoDAO;
import dto.BazaDTO;
import dto.ManoDTO;
import dto.PartidoDTO;
import enums.Envite;
import excepciones.PartidoException;
import util.DTOMapper;

/**
 * Soy la jugada de 2 o 3 bazas 
 * (los 4 jugadores jugaron sus 3 cartas)
 */
public class Mano {
	private Integer idMano;
	private int numeroMano;
	private Pareja parejaGanadora;
	private int puntajePareja1;
	private int puntajePareja2;
	private List<Baza> bazas;
	
	public Mano(int numeroMano, Pareja parejaGanadora, int puntajePareja1, int puntajePareja2,List<Baza> bazas) {
		super();
		this.numeroMano = numeroMano;
		this.parejaGanadora = parejaGanadora;
		this.puntajePareja1 = puntajePareja1;
		this.puntajePareja2 = puntajePareja2;
		this.bazas = bazas;
	}

	public Mano() {
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

	public int getNumeroMano() {
		return numeroMano;
	}
	public void setNumeroMano(int numeroMano) {
		this.numeroMano = numeroMano;
	}
	public Pareja getParejaGanadora() {
		return parejaGanadora;
	}
	public void setParejaGanadora(Pareja parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}
	
	public boolean guardar(Chico chico){
		return ManoDAO.getInstancia().guardar(this);
	}

	public Integer getIdMano() {
		return idMano;
	}

	public void setIdMano(Integer idMano) {
		this.idMano = idMano;
	}
	
	/**
	 * Constructor para crear un DTO sin todas sus variables completas
	 * @return
	 */
	public ManoDTO toDTO() {
		List <BazaDTO> bazasDTO = new ArrayList <BazaDTO> ();
		for (Baza b: bazas) {
			bazasDTO.add(b.toDTO());
		}
		return new ManoDTO(idMano, numeroMano, parejaGanadora.toDTO(), puntajePareja1,puntajePareja2, bazasDTO);
	}

	public List<Baza> getBazas() {
		return bazas;
	}

	public void setBazas(List<Baza> bazas) {
		this.bazas = bazas;
	}
	
	public void guardar(){
		ManoDAO.getInstancia().guardar(this);
	}

	public static boolean analizarEnvitesMano(int id) throws PartidoException {
		int indiceChico = 0;
		int indiceMano = 0;
		int indiceBaza = 0;
		int indiceTurno = 0;
		Partido partidoNegocio = PartidoDAO.getInstancia().buscarPartidoPorID(id);
		Pareja pareja1 = partidoNegocio.getParejas().get(0);
		Pareja pareja2 = partidoNegocio.getParejas().get(1);
		if(partidoNegocio.getNumeroChicoActual() > 0){
			indiceChico = partidoNegocio.getNumeroChicoActual()-1;
		}
		if(partidoNegocio.getChico().size() > 0){
			if(partidoNegocio.getChico().get(indiceChico).getManos().size() > 0){
				indiceMano =  partidoNegocio.getChico().get(indiceChico).getManos().size()-1;//agarro la mano actual
				Mano manoActual = partidoNegocio.getChico().get(indiceChico).getManos().get(indiceMano);
				if(manoActual.getBazas().size() > 0){
					indiceBaza = manoActual.getBazas().size() - 1;//agarro la baza/ronda actual
				}
			}
		}
		Chico chicoActual = partidoNegocio.getChico().get(indiceChico);
		Mano manoActual = chicoActual.getManos().get(indiceMano);
		Baza bazaActual = manoActual.getBazas().get(indiceBaza);
		if(bazaActual.getTurnos().size() > 0){
			indiceTurno = bazaActual.getTurnos().size()-1;
		}
		
		Turno turnoEnvite = null;
		if(bazaActual.getEnviteActual() == null){
			turnoEnvite = bazaActual.getTurnos().get(indiceTurno);

			//*******COMENZAMOS CON EL ENVIDO**********
			
			if(turnoEnvite.getEnvite().equals(Envite.Envido)){
				return true;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Envido_Querido)){
				setearPuntajeEnvido(partidoNegocio,manoActual,bazaActual,chicoActual,2,false);
				//PAREJA GANADORA +2 PUNTOS
				return true;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Envido_RealEnvido_Querido)){
				setearPuntajeEnvido(partidoNegocio,manoActual,bazaActual,chicoActual,5,false);
				partidoNegocio.actualizar();
				//PAREJA GANADORA +5 PUNTOS
				return true;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Envido_RealEnvido_FaltaEnvido_Querido)){
				//Baza nuevaBaza = new Baza();
				//nuevaBaza.setNumero(1);
				//Mano nuevaMano = new Mano();
				//List <Baza>	bazasNuevas = new ArrayList<Baza>();
				//bazasNuevas.add(nuevaBaza);
				//nuevaMano.setBazas(bazasNuevas);
				//chicoActual.getManos().add(nuevaMano);
				setearPuntajeEnvido(partidoNegocio,manoActual,bazaActual,chicoActual,0,true);
				actualizacionJuegoPartido(partidoNegocio, chicoActual, manoActual, bazaActual);
				return true;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Envido_FaltaEnvido_Querido)){
				setearPuntajeEnvido(partidoNegocio,manoActual,bazaActual,chicoActual,0,true);
				//PAREJA GANADORA GANA CHICO
				return false;
			}

			else if(turnoEnvite.getEnvite().equals(Envite.Envido_NoQuerido)){
				setearPuntajeEnvidoNOquerido(partidoNegocio,bazaActual,chicoActual,1);
				//PAREJA GANADORA +1 PUNTOS
				return true;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Envido_RealEnvido_NoQuerido)){
				setearPuntajeEnvidoNOquerido(partidoNegocio,bazaActual,chicoActual,3);
				//PAREJA GANADORA +3 PUNTOS
				return true;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Envido_FaltaEnvido_NoQuerido)){
				setearPuntajeEnvidoNOquerido(partidoNegocio,bazaActual,chicoActual,5);
				//PAREJA GANADORA +5 PUNTOS
				return true;
			}
			
			
			
			//*******COMENZAMOS CON EL ENVIDO ENVIDO**********
			
			if(turnoEnvite.getEnvite().equals(Envite.Envido_Envido)){
				return true;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_Querido)){
				setearPuntajeEnvido(partidoNegocio,manoActual,bazaActual,chicoActual,4,false);
				//PAREJA GANADORA +4 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_RealEnvido_Querido)){
				setearPuntajeEnvido(partidoNegocio,manoActual,bazaActual,chicoActual,7,false);
				//PAREJA GANADORA +7 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_FaltaEnvido_Querido)){
				setearPuntajeEnvido(partidoNegocio,manoActual,bazaActual,chicoActual,0,true);
				//PAREJA GANADORA GANA CHICO
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_RealEnvido_FaltaEnvido_Querido)){
				Baza nuevaBaza = new Baza();
				nuevaBaza.setNumero(1);
				Mano nuevaMano = new Mano();
				List <Baza>	bazasNuevas = new ArrayList<Baza>();
				bazasNuevas.add(nuevaBaza);
				nuevaMano.setBazas(bazasNuevas);
				chicoActual.getManos().add(nuevaMano);
				//FALTA MODIFICAR QUE EL FALTA ENVIDO, TE DA LO QUE LE FALTA AL OTRO PARA GANAR
				setearPuntajeEnvido(partidoNegocio,manoActual,bazaActual,chicoActual,0,true);
			
				//PAREJA GANADORA GANA CHICO
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_NoQuerido)){
				setearPuntajeEnvidoNOquerido(partidoNegocio,bazaActual,chicoActual,2);
				//PAREJA GANADORA +2 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_RealEnvido_NoQuerido)){
				setearPuntajeEnvidoNOquerido(partidoNegocio,bazaActual,chicoActual,4);
				//PAREJA GANADORA +4 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_FaltaEnvido_NoQuerido)){
				setearPuntajeEnvidoNOquerido(partidoNegocio,bazaActual,chicoActual,4);
				//PAREJA GANADORA +4 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_RealEnvido_FaltaEnvido_NoQuerido)){
				setearPuntajeEnvidoNOquerido(partidoNegocio,bazaActual,chicoActual,7);
				//PAREJA GANADORA +7 PUNTOS
				return false;
			}

			
			
			//*******COMENZAMOS CON EL REAL ENVIDO**********
			
			if(turnoEnvite.getEnvite().equals(Envite.Real_Envido)){
				return true;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.RealEnvido_Querido)){
				setearPuntajeEnvido(partidoNegocio,manoActual,bazaActual,chicoActual,3,false);
				//PAREJA GANADORA +3 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.RealEnvido_FaltaEnvido_Querido)){
				setearPuntajeEnvido(partidoNegocio,manoActual,bazaActual,chicoActual,0,true);
				//PAREJA GANADORA GANA PARTIDO
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.RealEnvido_NoQuerido)){
				setearPuntajeEnvidoNOquerido(partidoNegocio,bazaActual,chicoActual,1);
				//PAREJA GANADORA +1 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.RealEnvido_FaltaEnvido_NoQuerido)){
				setearPuntajeEnvidoNOquerido(partidoNegocio,bazaActual,chicoActual,3);
				//PAREJA GANADORA +3 PUNTOS
				return false;
			}

			
			
			//*******COMENZAMOS CON EL FALTA ENVIDO**********
			
			if(turnoEnvite.getEnvite().equals(Envite.Falta_Envido)){
				return true;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.FaltaEnvido_Querido)){
				setearPuntajeEnvido(partidoNegocio,manoActual,bazaActual,chicoActual,3,true);
				//PAREJA GANADORA GANA CHICO
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.FaltaEnvido_NoQuerido)){
				setearPuntajeEnvidoNOquerido(partidoNegocio,bazaActual,chicoActual,2);
				//PAREJA GANADORA +2 PUNTOS
				return false;
			}
			
			
			//*******COMENZAMOS CON EL TRUCO**********
			
			if(turnoEnvite.getEnvite().equals(Envite.Truco)){
				return true;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Truco_Querido)){
				if(setearPuntajeTruco(partidoNegocio,bazaActual,chicoActual,manoActual,2,true)){
					return true;
				}
				//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
				//PAREJA GANADORA +2 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Truco_QuieroRetruco_Querido)){
				if(setearPuntajeTruco(partidoNegocio,bazaActual,chicoActual,manoActual,3,true)){
					return true;
				}
				//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
				//PAREJA GANADORA +3 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Truco_QuieroRetruco_QuieroValeCuatro_Querido)){
				if(setearPuntajeTruco(partidoNegocio,bazaActual,chicoActual,manoActual,4,true)){
					return true;
				}
				//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
				//PAREJA GANADORA +4 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Truco_NoQuerido)){
				if(pareja1.getJugador1().equals(turnoEnvite.getJugador()) || pareja1.getJugador1().equals(turnoEnvite.getJugador())){
					setearPuntajeTrucoNoQuerido(partidoNegocio,bazaActual,chicoActual,1,pareja1);
				}
				else{
					setearPuntajeTrucoNoQuerido(partidoNegocio,bazaActual,chicoActual,1,pareja2);
				}
				//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
				//PAREJA GANADORA +1 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Truco_QuieroRetruco_NoQuerido)){
				if(pareja1.getJugador1().equals(turnoEnvite.getJugador()) || pareja1.getJugador1().equals(turnoEnvite.getJugador())){
					setearPuntajeTrucoNoQuerido(partidoNegocio,bazaActual,chicoActual,2,pareja1);
				}
				else{
					setearPuntajeTrucoNoQuerido(partidoNegocio,bazaActual,chicoActual,2,pareja2);
				}
				//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
				//PAREJA GANADORA +2 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Truco_QuieroRetruco_QuieroValeCuatro_NoQuerido)){
				if(pareja1.getJugador1().equals(turnoEnvite.getJugador()) || pareja1.getJugador1().equals(turnoEnvite.getJugador())){
					setearPuntajeTrucoNoQuerido(partidoNegocio,bazaActual,chicoActual,3,pareja1);
				}
				else{
					setearPuntajeTrucoNoQuerido(partidoNegocio,bazaActual,chicoActual,3,pareja2);
				}
				//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
				//PAREJA GANADORA +3 PUNTOS
				return false;
			}
			
			//*******MAZO**********
			
			if(turnoEnvite.getEnvite().equals(Envite.Mazo)){
				turnoEnvite = bazaActual.getTurnos().get(indiceTurno-1);
				if(bazaActual.getNumero() == 0){
					setearPuntajeEnvido(partidoNegocio,manoActual,bazaActual,chicoActual,0,true);
				}else{
					if(pareja1.getJugador1().equals(turnoEnvite.getJugador()) || pareja1.getJugador2().equals(turnoEnvite.getJugador())){
						bazaActual.setPuntajePareja1(bazaActual.getPuntajePareja1() + 1);
						chicoActual.setPuntajePareja1(chicoActual.getPuntajePareja1() + 1);
					}
					else{
						bazaActual.setPuntajePareja2(bazaActual.getPuntajePareja1() + 1);
						chicoActual.setPuntajePareja2(chicoActual.getPuntajePareja2() + 1);
					}
					partidoNegocio.actualizar();
				}
				return false;
				//ACA SI ES LA PRIMER BAZA, LA PAREJA QUE (NO) SE VA AL MAZO VA A GANAR 2 PUNTOS, SINO, GANA SOLAMENTE 1
			}
			
			//*******NADA**********

			if(turnoEnvite.getEnvite().equals(Envite.Nada) &&  (pareja1.getJugador1().getNumJugador() == 4 || pareja1.getJugador2().getNumJugador() == 4 || pareja2.getJugador1().getNumJugador() == 4 || pareja2.getJugador2().getNumJugador() == 4)){
				bazaActual.cartaMasAltaBaza(bazaActual, partidoNegocio.getParejas().get(0), partidoNegocio.getParejas().get(1));
				if(bazaActual.getGanadores() != null){
					Baza nuevaBaza = new Baza();
					if(manoActual.getBazas().size() == 1){
						bazaActual.setBazaTerminada(true);
						nuevaBaza.setNumero(2);
						nuevaBaza.setPuntajePareja1(bazaActual.getPuntajePareja1());
						nuevaBaza.setPuntajePareja2(bazaActual.getPuntajePareja2());
						manoActual.getBazas().add(nuevaBaza);
						partidoNegocio.actualizar();
					}
					else if(manoActual.getBazas().size() == 2){
						bazaActual.setBazaTerminada(true);
						if(manoActual.getBazas().get(0).getGanadores().equals(pareja1) && manoActual.getBazas().get(1).getGanadores().equals(pareja1)){
							manoActual.setParejaGanadora(pareja1);
							chicoActual.setPuntajePareja1(chicoActual.getPuntajePareja1()+1);
							nuevaBaza.setNumero(1);
							Mano nuevaMano = new Mano();
							List <Baza>	bazasNuevas = new ArrayList<Baza>();
							bazasNuevas.add(nuevaBaza);
							nuevaMano.setBazas(bazasNuevas);
							chicoActual.getManos().add(nuevaMano);
						}
						else if(manoActual.getBazas().get(0).getGanadores().equals(pareja2) && manoActual.getBazas().get(1).getGanadores().equals(pareja2)){
							manoActual.setParejaGanadora(pareja2);
							chicoActual.setPuntajePareja2(chicoActual.getPuntajePareja2()+1);
							nuevaBaza.setNumero(1);
							Mano nuevaMano = new Mano();
							List <Baza>	bazasNuevas = new ArrayList<Baza>();
							bazasNuevas.add(nuevaBaza);
							nuevaMano.setBazas(bazasNuevas);
							chicoActual.getManos().add(nuevaMano);
						}
						else{
							nuevaBaza.setNumero(3);
							nuevaBaza.setPuntajePareja1(bazaActual.getPuntajePareja1());
							nuevaBaza.setPuntajePareja2(bazaActual.getPuntajePareja2());
							manoActual.getBazas().add(nuevaBaza);
						}
						partidoNegocio.actualizar();
					}
					else{
						bazaActual.setBazaTerminada(true);
						if((manoActual.getBazas().get(0).getGanadores().equals(pareja1) && manoActual.getBazas().get(2).getGanadores().equals(pareja1)) || 
								(manoActual.getBazas().get(1).getGanadores().equals(pareja1) && manoActual.getBazas().get(2).getGanadores().equals(pareja1))){
							manoActual.setParejaGanadora(pareja1);
							chicoActual.setPuntajePareja1(chicoActual.getPuntajePareja1()+1);
						}
						else{
							manoActual.setParejaGanadora(pareja2);
							chicoActual.setPuntajePareja2(chicoActual.getPuntajePareja2()+1);
						}
						nuevaBaza.setNumero(1);
						Mano nuevaMano = new Mano();
						List <Baza>	bazasNuevas = new ArrayList<Baza>();
						bazasNuevas.add(nuevaBaza);
						nuevaMano.setBazas(bazasNuevas);
						chicoActual.getManos().add(nuevaMano);
						partidoNegocio.actualizar();
					}
					
				}
				//SI ES EL ULTIMO TURNO, ES DECIR EL JUGADOR NUMERO 4, ME FIJO A VER QUE PAREJA GANA ESA BAZA, BUSCANDO LA CARTA MAS ALTA
				//LA PAREJA QUE GANE MAS DE 1 BAZA, GANA LA MANO
			}
			else{
				return true;
			}
		}
		else{
			return true;
		}
		return false;
	}

	private static void actualizacionJuegoPartido(Partido partidoNegocio, Chico chicoActual, Mano manoActual, Baza bazaActual) {
		if (chicoActual.isFinalizado()) {
			partidoNegocio.crearNuevoChico();
		}
		else {
			if (manoActual.getParejaGanadora() != null) {
				chicoActual.crearNuevaMano();
			}
			else {
				manoActual.crearNuevaBaza();					
			}
		}
		partidoNegocio.actualizar();
	}

	public void crearNuevaBaza() {
		int indiceUltimaBaza = 0;
		if (bazas.size() > 0) {
			indiceUltimaBaza = bazas.size() - 1;
			puntajePareja1 = puntajePareja1 + bazas.get(indiceUltimaBaza).getPuntajePareja1();
			puntajePareja2 = puntajePareja2 + bazas.get(indiceUltimaBaza).getPuntajePareja2();
			
			//esto debe ir al jugar la baza, no al inicialziar una nueva.
		}	
		Baza bazaNueva = new Baza(bazas.size() + 1, null, 0, 0, null);
		bazas.add(bazaNueva);
	}

	/**Si este metodo devuelve false, es porque a alguno de los jugadores le falta jugar una carta, para que se pueda evaluar los ganadores*/
	private static boolean setearPuntajeTruco(Partido p, Baza bazaActual, Chico chicoActual, Mano manoActual, int puntaje,boolean finaliza_mano){
		boolean faltaJugarCarta = false;
		
		for(int i = 0;i<bazaActual.getTurnos().size();i++){
			if(bazaActual.getTurnos().get(i).getCarta() == null){
				faltaJugarCarta = true;
				break;
			}
		}
		if(faltaJugarCarta == false){
			bazaActual.cartaMasAltaBaza(bazaActual, p.getParejas().get(0), p.getParejas().get(1));
			if(bazaActual.getGanadores() != null){
				bazaActual.setBazaTerminada(true);
				if(finaliza_mano){
					manoActual.setParejaGanadora(bazaActual.getGanadores());
					if(manoActual.getParejaGanadora().equals(p.getParejas().get(0))){
						bazaActual.setGanadores(p.getParejas().get(0));
						chicoActual.setPuntajePareja1(chicoActual.getPuntajePareja1() + puntaje);
						bazaActual.setPuntajePareja1(bazaActual.getPuntajePareja1() + puntaje);
					}
					else{
						bazaActual.setGanadores(p.getParejas().get(1));
						chicoActual.setPuntajePareja1(chicoActual.getPuntajePareja2() + puntaje);
						bazaActual.setPuntajePareja1(bazaActual.getPuntajePareja2() + puntaje);
					}
					Baza nuevaBaza = new Baza();
					nuevaBaza.setNumero(1);
					Mano nuevaMano = new Mano();
					List <Baza>	bazasNuevas = new ArrayList<Baza>();
					bazasNuevas.add(nuevaBaza);
					nuevaMano.setBazas(bazasNuevas);
					chicoActual.getManos().add(nuevaMano);
					p.actualizar();
				}
			}
		}
		return true;
	}
	
	private static void setearPuntajeTrucoNoQuerido(Partido p, Baza bazaActual, Chico chicoActual, int puntaje, Pareja parejaGanadora){
		Pareja pareja1 = p.getParejas().get(0);
		if(pareja1.equals(pareja1)){
			bazaActual.setBazaTerminada(true);
			bazaActual.setGanadores(p.getParejas().get(0));
			bazaActual.setPuntajePareja1(bazaActual.getPuntajePareja1() + puntaje);
			chicoActual.setPuntajePareja1(chicoActual.getPuntajePareja1() + puntaje);
		}
		else{
			bazaActual.setBazaTerminada(true);
			bazaActual.setGanadores(p.getParejas().get(1));
			bazaActual.setPuntajePareja2(bazaActual.getPuntajePareja2() + puntaje);
			chicoActual.setPuntajePareja2(chicoActual.getPuntajePareja2() + puntaje);
		}
		p.actualizar();
	}
	
	private static void setearPuntajeEnvidoNOquerido(Partido p, Baza bazaActual, Chico chicoActual, int puntaje) {
		int indiceTurno=0;
		indiceTurno = bazaActual.getTurnos().size()-1;
		Turno turnoEnvite = null;
		turnoEnvite = bazaActual.getTurnos().get(indiceTurno-1);
		if(p.getParejas().get(0).getJugador1().equals(turnoEnvite.getJugador()) || p.getParejas().get(0).getJugador2().equals(turnoEnvite.getJugador())){
			bazaActual.setPuntajePareja1(bazaActual.getPuntajePareja1() + puntaje);
			chicoActual.setPuntajePareja1(chicoActual.getPuntajePareja1() + puntaje);
		}
		else{
			bazaActual.setPuntajePareja2(bazaActual.getPuntajePareja1() + puntaje);
			chicoActual.setPuntajePareja2(chicoActual.getPuntajePareja1() + puntaje);
		}
		p.actualizar();
		
	}

	private static void setearPuntajeEnvido(Partido p, Mano manoActual, Baza bazaActual, Chico chicoActual, int puntaje,boolean finaliza_chico) {
		if(finaliza_chico){
			if(p.getParejas().get(0).equals(Pareja.calcularTantoParejas(p.getParejas().get(0), p.getParejas().get(1)))){
				bazaActual.setPuntajePareja1(30);
				bazaActual.setGanadores(p.getParejas().get(0));
				manoActual.setParejaGanadora(p.getParejas().get(0));
				chicoActual.setParejaGanadora(p.getParejas().get(0));
				chicoActual.setPuntajePareja1(30);
				chicoActual.setFinalizado(true);
			}
			else{
				bazaActual.setPuntajePareja2(30);
				bazaActual.setGanadores(p.getParejas().get(1));
				manoActual.setParejaGanadora(p.getParejas().get(1));
				chicoActual.setParejaGanadora(p.getParejas().get(1));
				chicoActual.setPuntajePareja2(30);
				chicoActual.setFinalizado(true);
				if (p.getNumeroChicoActual() < 3) {
					p.setNumeroChicoActual(p.getNumeroChicoActual() + 1);
				}
			}
		}else{
			if(p.getParejas().get(0).equals(Pareja.calcularTantoParejas(p.getParejas().get(0),p.getParejas().get(1)))){
				bazaActual.setPuntajePareja1(bazaActual.getPuntajePareja1() + puntaje);
				chicoActual.setPuntajePareja1(chicoActual.getPuntajePareja2() + puntaje);
			}else {
				bazaActual.setPuntajePareja2(bazaActual.getPuntajePareja2() + puntaje);
				chicoActual.setPuntajePareja2(chicoActual.getPuntajePareja1() + puntaje);
			}
		}
		p.actualizar();
	}

}
