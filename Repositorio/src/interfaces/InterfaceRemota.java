package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceRemota extends Remote {
	
	public boolean AltaJugador() throws RemoteException;
	
	public boolean crearGrupo() throws RemoteException;

	public boolean iniciarSesion() throws RemoteException;
	
	public boolean iniciarPartida() throws RemoteException;
	
	public boolean iniciarPartidaCerrada() throws RemoteException;
	
	public boolean buscarJugDisponibles() throws RemoteException;
	
	public boolean repartirCartas() throws RemoteException;
	
	public boolean nuevaMano() throws RemoteException;

	public boolean registrarPuntaje() throws RemoteException;
	
	public boolean eliminarJugador() throws RemoteException;
	
	public boolean modificarJugador() throws RemoteException;
}
