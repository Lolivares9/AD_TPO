package interfaces;

import negocio.Jugador;

public interface IJugadorDAO {
	
	public boolean guardarJugador(Jugador p);
	public boolean existeJugador(String apodo);
}
