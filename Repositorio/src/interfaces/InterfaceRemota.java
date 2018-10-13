package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import dto.JugadorDTO;
import excepciones.CartaException;
import excepciones.GrupoException;
import excepciones.JugadorException;

public interface InterfaceRemota extends Remote {
	
	public boolean AltaJugador(JugadorDTO jugador) throws RemoteException,JugadorException;
	
	public boolean crearGrupo(String nombreGrupo, JugadorDTO jugador) throws RemoteException, GrupoException, JugadorException;
	
	public boolean llenarGrupo(String nombreGrupo, List<JugadorDTO> jugadores) throws RemoteException, GrupoException;

	public boolean iniciarSesion(JugadorDTO jugador) throws RemoteException;
	
	public boolean iniciarPartidaLibreIndividual() throws RemoteException;
	
	public boolean iniciarPartidaLibre() throws RemoteException;
	
	public boolean iniciarPartidaCerrada() throws RemoteException;
	
	public boolean buscarJugDisponibles() throws RemoteException;
	
	public boolean repartirCartas() throws RemoteException, CartaException;
	
	public boolean nuevaMano() throws RemoteException;

	public boolean registrarPuntaje() throws RemoteException;
	
	public boolean eliminarJugador() throws RemoteException;
	
	public boolean modificarJugador() throws RemoteException;

	public void cargarCartas() throws RemoteException, CartaException;
}
