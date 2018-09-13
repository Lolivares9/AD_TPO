package dao;

import dto.JugadorDTO;
import entities.JugadorEntity;
import interfaces.IJugadorDAO;
import negocio.Jugador;

public class JugadorDAO implements IJugadorDAO{

	private static JugadorDAO instancia;
	
	public static JugadorDAO getInstancia() {
		if(instancia == null){
			instancia = new JugadorDAO();
		}
		return instancia;
	}
	
	public boolean existeJugador(String apodo) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean guardarJugador(Jugador p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Jugador toNegocio(JugadorEntity jugador){
		//return new Jugador(jugador.getApodo(), jugador.getMail(), jugador.getPassword(), null, 0, 0, 0, false, false, null);
		return null;
	}

	public JugadorDTO toDTO(JugadorEntity jugador){
		//return new JugadorDTO(jugador.getApodo(), jugador.getMail(), jugador.getPassword());
		return null;
	}
}
