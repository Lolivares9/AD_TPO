package dao;

import dto.JugadorDTO;
import entities.JugadorEntity;
import interfaces.IJugadorDAO;
import negocio.JugadorBO;

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

	public boolean guardarJugador(JugadorBO p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public JugadorBO toNegocio(JugadorEntity jugador){
		return new JugadorBO(jugador.getApodo(), jugador.getMail(), jugador.getPassword());
	}

	public JugadorDTO toDTO(JugadorEntity jugador){
		return new JugadorDTO(jugador.getApodo(), jugador.getMail(), jugador.getPassword());
	}
}
