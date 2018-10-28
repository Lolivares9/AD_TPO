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

		
		analizarEnvitesMano(manoActual);
		return null;
	}

	private void analizarEnvitesMano(Mano manoActual) {
		Baza bazaActual = manoActual.getBazas().get(0);
		Turno turnoActual = bazaActual.getTurnos().get(0);
		Envite enviteActual = turnoActual.getEnvite();
		
		if(enviteActual.equals(Envite.Nada)){
			
		}
		if(enviteActual.equals(Envite.Mazo)){
			
		}
		if(enviteActual.equals(Envite.Envido)){
			
		}
		if(enviteActual.equals(Envite.Truco)){
			
		}
		
	}
	
	
}
