package interfaces;

import negocio.JugadorBO;

public interface IJugadorDAO {
	
	public boolean guardarJugador(JugadorBO p);
	public boolean existeJugador(String apodo);
}
