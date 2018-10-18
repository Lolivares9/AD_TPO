package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import dto.CartaDTO;
import dto.JugadorDTO;
import dto.ParejaDTO;
import dto.PartidoDTO;
import excepciones.CartaException;
import excepciones.GrupoException;
import excepciones.JugadorException;

public interface InterfaceRemota extends Remote {
	
	public boolean AltaJugador(JugadorDTO jugador) throws RemoteException,JugadorException;
	
	public boolean crearGrupo(String nombreGrupo, JugadorDTO jugador) throws RemoteException, GrupoException, JugadorException;
	
	public boolean llenarGrupo(String nombreGrupo, List<JugadorDTO> jugadores) throws RemoteException, GrupoException;

	public boolean iniciarSesion(JugadorDTO jugador) throws RemoteException;
	
	public PartidoDTO iniciarPartidaLibreIndividual(JugadorDTO jugador) throws RemoteException;
	
	public PartidoDTO iniciarPartidaLibre(ParejaDTO pareja) throws RemoteException;
	
	public boolean iniciarPartidaCerrada() throws RemoteException;
	
	public boolean buscarJugDisponibles() throws RemoteException;
	
	public List<CartaDTO> repartirCartas() throws RemoteException, CartaException;
	
	public boolean nuevaMano() throws RemoteException;

	public boolean registrarPuntaje() throws RemoteException;
	
	public boolean eliminarJugador() throws RemoteException;
	
	public boolean modificarJugador() throws RemoteException;
}
