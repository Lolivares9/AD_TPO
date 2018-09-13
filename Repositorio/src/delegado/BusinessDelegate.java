package delegado;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import excepciones.ComunicacionException;
import interfaces.InterfaceRemota;

public class BusinessDelegate {

	private InterfaceRemota ir;
	
	public BusinessDelegate() throws ComunicacionException{
		try {
			ir = (InterfaceRemota) Naming.lookup("//127.0.0.1/sumador");
		} catch (MalformedURLException e) {
			throw new ComunicacionException("La direccion especificada no es correcta");
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		} catch (NotBoundException e) {
			throw new ComunicacionException("El servidor no esta disponible");		
		}
	}
	

	public boolean AltaJugador() throws ComunicacionException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean crearGrupo() throws ComunicacionException {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean iniciarSesion() throws ComunicacionException {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean iniciarPartida() throws ComunicacionException {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean iniciarPartidaCerrada() throws ComunicacionException {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean buscarJugDisponibles() throws ComunicacionException {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean repartirCartas() throws ComunicacionException {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean nuevaMano() throws ComunicacionException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean registrarPuntaje() throws ComunicacionException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean eliminarJugador() throws ComunicacionException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean modificarJugador() throws ComunicacionException {
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
