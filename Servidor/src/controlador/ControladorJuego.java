package controlador;

import dto.PartidoDTO;
import negocio.Chico;
import negocio.Mano;
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
		Chico chicoActual = DTOMapper.getInstancia().chicoDTOtoNegocio(partido.getChicoDTO().get(0));
		
		
		return null;
	}
	
	
}
