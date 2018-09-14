package remoto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import controlador.Controlador;
import dto.JugadorDTO;
import excepciones.JugadorException;
import interfaces.InterfaceRemota;
import negocio.Jugador;
import util.DTOMapper;

public class ObjetoRemoto extends UnicastRemoteObject implements InterfaceRemota {

	private static final long serialVersionUID = 7432516509801597745L;

	public ObjetoRemoto() throws RemoteException {}

	@Override
	public boolean AltaJugador(JugadorDTO jugador) throws RemoteException, JugadorException {
		Controlador.getInstancia().altaJugador(jugador);
		return false;
	}

	@Override
	public boolean crearGrupo() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarSesion() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarPartida() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
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
