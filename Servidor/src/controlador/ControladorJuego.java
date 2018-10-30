package controlador;

import java.util.List;

import dto.ParejaDTO;
import dto.PartidoDTO;
import enums.Envite;
import negocio.Baza;
import negocio.Carta;
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
		
		
		boolean siguienteTurno = false;
		
		siguienteTurno = analizarEnvitesMano(partido);
		return null;
	}

	private boolean analizarEnvitesMano(PartidoDTO partido) {
		Partido partidoBO = DTOMapper.getInstancia().partidoDTOtoNegocio(partido);
		Mano manoActual = partidoBO.getChico().get(0).getManos().get(0);
		
		
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
	
	
	private ParejaDTO calcularTantoParejas(ParejaDTO pareja1, ParejaDTO pareja2){
		ParejaDTO parejaGanadora = null;
		List<Carta> cartasJug1Pareja1 = Carta.cartasToNegocio(pareja1.getCartasJug1());
		List<Carta> cartasJug2Pareja1 = Carta.cartasToNegocio(pareja1.getCartasJug2());
		List<Carta> cartasJug1Pareja2 = Carta.cartasToNegocio(pareja2.getCartasJug1());
		List<Carta> cartasJug2Pareja2 = Carta.cartasToNegocio(pareja2.getCartasJug2());
		int tantoJug1Pareja1 = calcularTantoJugadores(cartasJug1Pareja1);
		int tantoJug2Pareja1 = calcularTantoJugadores(cartasJug1Pareja2);
		int tantoJug1Pareja2 = calcularTantoJugadores(cartasJug2Pareja1);
		int tantoJug2Pareja2 = calcularTantoJugadores(cartasJug2Pareja2);
		
		if(((tantoJug1Pareja1 > tantoJug1Pareja2) && (tantoJug1Pareja1 > tantoJug2Pareja2)) || ((tantoJug2Pareja1 > tantoJug1Pareja2) && (tantoJug2Pareja1 > tantoJug2Pareja2))){
			parejaGanadora = pareja1;
		}
		//ESTE CASO ES CUANDO EMPATAN EN EL ENVIDO
		else if(((tantoJug1Pareja1 == tantoJug1Pareja2) || (tantoJug1Pareja1 == tantoJug2Pareja2)) || ((tantoJug2Pareja1 == tantoJug1Pareja2) || (tantoJug2Pareja1 == tantoJug2Pareja2))){
			if(pareja1.getJugadorDTO1().getNumJugador() == 1 || pareja1.getJugadorDTO2().getNumJugador() == 1){
				return parejaGanadora = pareja1;
			}
			else{
				return parejaGanadora = pareja2;
			}
		}
		
		return parejaGanadora;
		
	}
	
	private int calcularTantoJugadores(List<Carta> cartas){
		Carta carta1 = cartas.get(0);
		Carta carta2 = cartas.get(1);
		Carta carta3 = cartas.get(2);
		int envido = 0;
		int sumaCarta1Carta2 = 0;
		int sumaCarta1Carta3 = 0;
		int sumaCarta2Carta3 = 0;
		
		//3 CARTAS DE IGUAL PALO
		if(carta1.getPalo().equals(carta2.getPalo()) && carta1.getPalo().equals(carta3.getPalo())){
			if((carta1.getValorEnvido() == 20 && carta2.getValorEnvido() == 20) || (carta3.getValorEnvido() == 20 && carta2.getValorEnvido() == 20) || (carta1.getValorEnvido() == 20 || carta3.getValorEnvido() == 20)){
				return envido = 20;
			}
			sumaCarta1Carta2 = carta1.getValorEnvido() + carta2.getValorEnvido();
			sumaCarta1Carta3 = carta1.getValorEnvido() + carta3.getValorEnvido();
			sumaCarta2Carta3 = carta2.getValorEnvido() + carta3.getValorEnvido();
			if((sumaCarta1Carta2 > sumaCarta1Carta3) && (sumaCarta1Carta2 > sumaCarta2Carta3)){
				envido = sumaCarta1Carta2;
				return envido;
			}
			else if((sumaCarta1Carta3 > sumaCarta1Carta2) && (sumaCarta1Carta3 > sumaCarta2Carta3)){
				envido = sumaCarta1Carta3;
				return envido;
			}
			else{
				envido = sumaCarta2Carta3;
				return envido;
			}
		}
		//NINGUNA CARTA TIENE IGUAL PALO
		else if(!carta1.getPalo().equals(carta2.getPalo()) && !carta1.getPalo().equals(carta3.getPalo())){
			if((carta1.getValorEnvido() >= carta2.getValorEnvido()) && (carta1.getValorEnvido() >= carta3.getValorEnvido())){
				envido = carta1.getValorEnvido();
				if(carta1.getValorEnvido() == 10 || carta1.getValorEnvido() == 11 || carta1.getValorEnvido() == 12){
					return envido = 20;
				}
				else{
					return envido;
				}
			}
			else if((carta2.getValorEnvido() >= carta1.getValorEnvido()) && (carta2.getValorEnvido() >= carta3.getValorEnvido())){
				envido = carta2.getValorEnvido();
				if(carta2.getValorEnvido() == 10 || carta2.getValorEnvido() == 11 || carta2.getValorEnvido() == 12){
					return envido = 20;
				}
				else{
					return envido;
				}
			}
			else if((carta3.getValorEnvido() >= carta2.getValorEnvido()) && (carta3.getValorEnvido() >= carta1.getValorEnvido())){
				envido = carta3.getValorEnvido();
				if(carta3.getValorEnvido() == 10 || carta3.getValorEnvido() == 11 || carta3.getValorEnvido() == 12){
					return envido = 20;
				}
				else{
					return envido;
				}
			}
		}
		else if(carta1.getPalo().equals(carta2.getPalo())){
			sumaCarta1Carta2 = carta1.getValorEnvido() + carta2.getValorEnvido();
			envido = sumaCarta1Carta2;
			return envido;
		}
		else if(carta1.getPalo().equals(carta3.getPalo())){
			sumaCarta1Carta3 = carta1.getValorEnvido() + carta3.getValorEnvido();
			envido = sumaCarta1Carta3;
			return envido;
		}
		else if(carta2.getPalo().equals(carta3.getPalo())){
			sumaCarta2Carta3 = carta2.getValorEnvido() + carta3.getValorEnvido();
			envido = sumaCarta2Carta3;
			return envido;
		}
		
		return envido;
	}
	
	
	
	
	
	
	
}











