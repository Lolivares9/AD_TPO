package remoto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import controlador.Controlador;
import dto.JugadorDTO;
import excepciones.GrupoException;
import excepciones.JugadorException;
import interfaces.InterfaceRemota;
import negocio.Jugador;

public class ObjetoRemoto extends UnicastRemoteObject implements InterfaceRemota {

	private static final long serialVersionUID = 7432516509801597745L;

	public ObjetoRemoto() throws RemoteException {}

	@Override
	public boolean AltaJugador(JugadorDTO jugador) throws RemoteException, JugadorException {
		Controlador.getInstancia().altaJugador(jugador);
		return false;
	}

	@Override
	public boolean crearGrupo(String nombreGrupo, JugadorDTO jugadorAdmin) throws RemoteException, GrupoException {
		return Controlador.getInstancia().crearGrupo(nombreGrupo, jugadorAdmin);
	}

	@Override
	public boolean iniciarSesion(JugadorDTO jugador) throws RemoteException {
		boolean inicioBien = false;
		try {
			inicioBien = Controlador.getInstancia().iniciarSesion(jugador);
		} catch (JugadorException e) {
			e.printStackTrace();
		}
		return inicioBien;
	}

	@Override
	public boolean iniciarPartidaLibreIndividual() throws RemoteException {
		return Controlador.getInstancia().iniciarPartidaLibreIndividual();
	}
	
	@Override
	public boolean iniciarPartidaLibre() throws RemoteException {
		return Controlador.getInstancia().iniciarPartidaLibre();
	}

	@Override
	public boolean iniciarPartidaCerrada() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean buscarJugDisponibles() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean repartirCartas() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean nuevaMano() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registrarPuntaje() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminarJugador() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificarJugador() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	
}
