package controlador;

import dto.PartidoDTO;
import enums.Envite;
import negocio.Baza;
import negocio.Mano;
import negocio.Partido;
import negocio.Turno;
import util.DTOMapper;

public class ControladorJuego {
	private static ControladorJuego instancia;
	
	private ControladorJuego(){
	}
	
	public ControladorJuego getInstancia(){
		if (instancia == null){
			instancia = new ControladorJuego();
		}
		return instancia;
	}
	
	/**
	 * Aca deberia agarrar siempre la primera mano (return manos(0) del primer Chico porque es en la que se esta jugando en ese momento
	 * 
	 * @param partido
	 * @return
	 */
	public PartidoDTO nuevaJugada(PartidoDTO partido){
		Partido partidoBO = DTOMapper.getInstancia().partidoDTOtoNegocio(partido);
		Mano manoActual = partidoBO.getChico().get(0).getManos().get(0);
		boolean siguienteTurno = false;
		
		siguienteTurno = analizarEnvitesMano(manoActual);
		return null;
	}

	private boolean analizarEnvitesMano(Mano manoActual) {
		Baza bazaActual = manoActual.getBazas().get(0);
		Turno turnoEnvite = null;
		if(bazaActual.isEnvitePendiente()){
			turnoEnvite = bazaActual.getTurnos().get(bazaActual.getNumJugEnvitePendiente());

			
			
			//*******COMENZAMOS CON EL ENVIDO**********
			
			if(turnoEnvite.getEnvite().equals(Envite.Envido)){
				return true;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Envido_Querido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES, Y LE SUMO LOS PUNTOS A LA PAREJA
				//PAREJA GANADORA +2 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Envido_RealEnvido_Querido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES, Y LE SUMO LOS PUNTOS A LA PAREJA
				//PAREJA GANADORA +5 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Envido_FaltaEnvido_Querido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES
				//PAREJA GANADORA GANA PARTIDO
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Envido_NoQuerido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES, Y LE SUMO LOS PUNTOS A LA PAREJA
				//PAREJA GANADORA +1 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Envido_RealEnvido_NoQuerido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES, Y LE SUMO LOS PUNTOS A LA PAREJA
				//PAREJA GANADORA +3 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.Envido_FaltaEnvido_NoQuerido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES, Y LE SUMO LOS PUNTOS A LA PAREJA
				//PAREJA GANADORA +5 PUNTOS
				return false;
			}
			
			
			
			//*******COMENZAMOS CON EL ENVIDO ENVIDO**********
			
			if(turnoEnvite.getEnvite().equals(Envite.Envido_Envido)){
				return true;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_Querido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES, Y LE SUMO LOS PUNTOS A LA PAREJA
				//PAREJA GANADORA +4 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_RealEnvido_Querido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES, Y LE SUMO LOS PUNTOS A LA PAREJA
				//PAREJA GANADORA +7 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_FaltaEnvido_Querido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES
				//PAREJA GANADORA GANA PARTIDO
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_RealEnvido_FaltaEnvido_Querido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES
				//PAREJA GANADORA GANA PARTIDO
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_NoQuerido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES, Y LE SUMO LOS PUNTOS A LA PAREJA
				//PAREJA GANADORA +2 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_RealEnvido_NoQuerido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES, Y LE SUMO LOS PUNTOS A LA PAREJA
				//PAREJA GANADORA +4 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_FaltaEnvido_NoQuerido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES, Y LE SUMO LOS PUNTOS A LA PAREJA
				//PAREJA GANADORA +4 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.EnvidoEnvido_RealEnvido_FaltaEnvido_NoQuerido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES, Y LE SUMO LOS PUNTOS A LA PAREJA
				//PAREJA GANADORA +7 PUNTOS
				return false;
			}

			
			
			//*******COMENZAMOS CON EL REAL ENVIDO**********
			
			if(turnoEnvite.getEnvite().equals(Envite.Real_Envido)){
				return true;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.RealEnvido_Querido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES, Y LE SUMO LOS PUNTOS A LA PAREJA
				//PAREJA GANADORA +3 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.RealEnvido_FaltaEnvido_Querido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES
				//PAREJA GANADORA GANA PARTIDO
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.RealEnvido_NoQuerido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES, Y LE SUMO LOS PUNTOS A LA PAREJA
				//PAREJA GANADORA +1 PUNTOS
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.RealEnvido_FaltaEnvido_NoQuerido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES, Y LE SUMO LOS PUNTOS A LA PAREJA
				//PAREJA GANADORA +3 PUNTOS
				return false;
			}

			
			
			//*******COMENZAMOS CON EL FALTA ENVIDO**********
			
			if(turnoEnvite.getEnvite().equals(Envite.Falta_Envido)){
				return true;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.FaltaEnvido_Querido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES
				//PAREJA GANADORA GANA PARTIDO
				return false;
			}
			else if(turnoEnvite.getEnvite().equals(Envite.FaltaEnvido_NoQuerido)){
				//CALCULO INDIVIDUALMENTE EL TANTO DE LOS JUGADORES, Y LE SUMO LOS PUNTOS A LA PAREJA
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
		}
		else{
			return true;
		}
		return false;
	}
	
	
}
