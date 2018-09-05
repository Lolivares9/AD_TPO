package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceRemota extends Remote {

	public int suma(int a, int b) throws RemoteException;
	
	public boolean registrarJugador() throws RemoteException;
	
	
}
