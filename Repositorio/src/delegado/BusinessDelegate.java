package delegado;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import dto.JugadorDTO;
import excepciones.ComunicationException;
import excepciones.JugadorException;
import interfaces.InterfaceRemota;

public class BusinessDelegate {

	private InterfaceRemota ir;
	private static BusinessDelegate instancia;

	private BusinessDelegate() throws ComunicationException {
		try {
			ir = (InterfaceRemota) Naming.lookup("//localhost/Remoto");
		} catch (MalformedURLException e1) {
			throw new ComunicationException("La ubicacion del seridor es incorrecta");
		} catch (RemoteException e1) {
			throw new ComunicationException("Se produjo un error en la comunicación");
		} catch (NotBoundException e1) {
			throw new ComunicationException("No encontre a nadie que me responda");
		}
	}

	public static BusinessDelegate getInstancia() throws ComunicationException {
		if (instancia == null)
			instancia = new BusinessDelegate();
		return instancia;
	}
	

	public boolean AltaJugador(JugadorDTO jugador) throws ComunicationException {
		try {
			ir.AltaJugador(jugador);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (JugadorException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean crearGrupo() throws ComunicationException {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean iniciarSesion() throws ComunicationException {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean iniciarPartida() throws ComunicationException {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean iniciarPartidaCerrada() throws ComunicationException {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean buscarJugDisponibles() throws ComunicationException {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean repartirCartas() throws ComunicationException {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean nuevaMano() throws ComunicationException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean registrarPuntaje() throws ComunicationException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean eliminarJugador() throws ComunicationException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean modificarJugador() throws ComunicationException {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	/*
	public int suma(int a, int b) throws ComunicacionException{
		try {
			return ir.suma(a, b);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");	
		}
	}*/	
}
