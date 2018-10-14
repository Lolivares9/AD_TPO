package remoto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import controlador.Controlador;
import dto.CartaDTO;
import dto.JugadorDTO;
import excepciones.CartaException;
import excepciones.GrupoException;
import excepciones.JugadorException;
import interfaces.InterfaceRemota;

public class ObjetoRemoto extends UnicastRemoteObject implements InterfaceRemota {

	private static final long serialVersionUID = 7432516509801597745L;

	public ObjetoRemoto() throws RemoteException {}

	@Override
	public boolean AltaJugador(JugadorDTO jugador) throws RemoteException, JugadorException {
		Controlador.getInstancia().altaJugador(jugador);
		return false;
	}

	@Override
	public boolean crearGrupo(String nombreGrupo, JugadorDTO jugadorAdmin) throws RemoteException, GrupoException, JugadorException {
		return Controlador.getInstancia().crearGrupo(nombreGrupo, jugadorAdmin);
	}
	
	@Override
	public boolean llenarGrupo(String nombreGrupo, List<JugadorDTO> jugadores) throws RemoteException, GrupoException {
		return Controlador.getInstancia().llenarGrupo(nombreGrupo, jugadores);
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

	public boolean iniciarPartidaLibreIndividual(JugadorDTO jug) throws RemoteException {
		//TODO MATI return Controlador.getInstancia().iniciarPartidaLibreIndividual();
		return false;
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
	public List<CartaDTO> repartirCartas() throws RemoteException {
		try {
			return Controlador.getInstancia().repartiCartas();
		} catch (CartaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
