package negocio;

import java.util.List;

import dao.ManoDAO;
import dao.PartidoDAO;
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
	private List<Baza> bazas;
	
	public Mano(int numeroMano, Pareja parejaGanadora,List<Baza> bazas) {
		super();
		this.numeroMano = numeroMano;
		this.parejaGanadora = parejaGanadora;
		this.bazas = bazas;
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
		return new ManoDTO(numeroMano, parejaGanadora.toDTO());
	}

	public List<Baza> getBazas() {
		return bazas;
	}

	public void setBazas(List<Baza> bazas) {
		this.bazas = bazas;
	}
	
	
	public static boolean analizarEnvitesMano(int id) throws PartidoException {
		int indiceChico = 0;
		int indiceMano = 0;
		int indiceBaza = 0;
		int indiceTurno = 0;
		Partido partidoNegocio = PartidoDAO.getInstancia().buscarPartidoPorID(id);
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
				setearPuntajeEnvido(partidoNegocio,bazaActual,chicoActual,2,false);
				//PAREJA GANADORA +2 PUNTOS
				return true;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Envido_RealEnvido_Querido)){
				setearPuntajeEnvido(partidoNegocio,bazaActual,chicoActual,5,false);
				partidoNegocio.actualizar();
				//PAREJA GANADORA +5 PUNTOS
				return true;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Envido_FaltaEnvido_Querido)){
				setearPuntajeEnvido(partidoNegocio,bazaActual,chicoActual,0,true);
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
				setearPuntajeEnvido(partidoNegocio,bazaActual,chicoActual,4,false);
				//PAREJA GANADORA +4 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_RealEnvido_Querido)){
				setearPuntajeEnvido(partidoNegocio,bazaActual,chicoActual,7,false);
				//PAREJA GANADORA +7 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_FaltaEnvido_Querido)){
				setearPuntajeEnvido(partidoNegocio,bazaActual,chicoActual,0,true);
				//PAREJA GANADORA GANA CHICO
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_RealEnvido_FaltaEnvido_Querido)){
				setearPuntajeEnvido(partidoNegocio,bazaActual,chicoActual,0,true);
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
				setearPuntajeEnvido(partidoNegocio,bazaActual,chicoActual,3,false);
				//PAREJA GANADORA +3 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.RealEnvido_FaltaEnvido_Querido)){
				setearPuntajeEnvido(partidoNegocio,bazaActual,chicoActual,0,true);
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
				setearPuntajeEnvido(partidoNegocio,bazaActual,chicoActual,3,true);
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
				//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
				//PAREJA GANADORA +2 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Truco_QuieroRetruco_Querido)){
				//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
				//PAREJA GANADORA +3 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Truco_QuieroRetruco_QuieroValeCuatro_Querido)){
				//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
				//PAREJA GANADORA +4 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Truco_NoQuerido)){
				//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
				//PAREJA GANADORA +1 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Truco_QuieroRetruco_NoQuerido)){
				//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
				//PAREJA GANADORA +2 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Truco_QuieroRetruco_QuieroValeCuatro_NoQuerido)){
				//TOMO LAS CARTAS DE LA BAZA ACTUAL (RECORDAR QUE TIENE QUE HABER 4 CARTAS SOBRE EL TABLERO) Y ME FIJO QUE PAREJA GANA, Y LA QUE GANA, GANA LA MANO
				//PAREJA GANADORA +3 PUNTOS
				return false;
			}
			
			//*******MAZO**********
			
			if(turnoEnvite.getEnvite().equals(Envite.Mazo)){
				turnoEnvite = bazaActual.getTurnos().get(indiceTurno-1);
				if(bazaActual.getNumero() == 0){
					setearPuntajeEnvido(partidoNegocio,bazaActual,chicoActual,0,true);
				}else{
					if(partidoNegocio.getParejas().get(0).getJugador1().equals(turnoEnvite.getJugador()) || partidoNegocio.getParejas().get(0).getJugador2().equals(turnoEnvite.getJugador())){
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
		}
		else{
			return true;
		}
		return false;
	}

	private static void setearPuntajeTruco(Partido p, Baza bazaActual, Chico chicoActual, int puntaje,boolean finaliza_chico){
		int indiceTurno=0;
		indiceTurno = bazaActual.getTurnos().size()-1;
		Turno turnoEnvite = null;
		turnoEnvite = bazaActual.getTurnos().get(indiceTurno-1);
		
	}
	private static void setearPuntajeTrucoNoQuerido(Partido p, Baza bazaActual, Chico chicoActual, int puntaje,boolean finaliza_chico){
		int indiceTurno=0;
		indiceTurno = bazaActual.getTurnos().size()-1;
		Turno turnoEnvite = null;
		turnoEnvite = bazaActual.getTurnos().get(indiceTurno-1);
		
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

	private static void setearPuntajeEnvido(Partido p, Baza bazaActual, Chico chicoActual, int puntaje,boolean finaliza_chico) {
		if(finaliza_chico){
			if(p.getParejas().get(0).equals(Pareja.calcularTantoParejas(p.getParejas().get(0), p.getParejas().get(1)))){
				chicoActual.setParejaGanadora(p.getParejas().get(0));
				chicoActual.setFinalizado(true);
			}
			else{
				chicoActual.setParejaGanadora(p.getParejas().get(1));
				chicoActual.setFinalizado(true);
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
