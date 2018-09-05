package remoto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import controlador.Controlador;
import interfaces.InterfaceRemota;

public class ObjetoRemoto extends UnicastRemoteObject implements InterfaceRemota {

	private static final long serialVersionUID = 7432516509801597745L;

	public ObjetoRemoto() throws RemoteException {}

	@Override
	public int suma(int a, int b) throws RemoteException {
		return new Controlador().sumar(a, b);
	}

	@Override
	public boolean registrarJugador() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
