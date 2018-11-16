package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import dto.BazaDTO;
import dto.ChicoDTO;
import dto.JugadorDTO;
import dto.ManoDTO;
import dto.ParejaDTO;
import dto.PartidoDTO;
import dto.TurnoDTO;
import enums.TipoModalidad;
import excepciones.BazaException;
import excepciones.CartaException;
import excepciones.ChicoException;
import excepciones.GrupoException;
import excepciones.JugadorException;
import excepciones.ManoException;
import excepciones.ParejaException;
import excepciones.PartidoException;
import excepciones.TurnoException;

public interface InterfaceRemota extends Remote {
	
	public JugadorDTO altaJugador(JugadorDTO jugador) throws RemoteException,JugadorException, GrupoException;
	
	public boolean crearGrupo(String nombreGrupo, JugadorDTO jugador) throws RemoteException, GrupoException, JugadorException;
	
	public boolean llenarGrupo(String nombreGrupo, List<JugadorDTO> jugadores) throws RemoteException, GrupoException;

	public JugadorDTO iniciarSesion(JugadorDTO jugador) throws RemoteException, GrupoException;
	
	public List<PartidoDTO> buscarPartidosJugados(JugadorDTO jugador, TipoModalidad modalidad, Date fechaIni, Date fechaFin) throws RemoteException, ParejaException, PartidoException;
	
	public List<ChicoDTO> buscarChicosPorPartido(PartidoDTO partido) throws RemoteException, ChicoException;
	
	public Map<ManoDTO, Map<BazaDTO, List<TurnoDTO>>> obtenerDetalleDeChico(ChicoDTO chico) throws RemoteException, ManoException, BazaException, TurnoException, GrupoException;
	
	public void buscarPartidaLibreIndividual(JugadorDTO jugador)throws RemoteException, JugadorException;
	
	public PartidoDTO buscarPartidaLobby(String apodoJugador, String modalidad) throws RemoteException, PartidoException, ParejaException, JugadorException, GrupoException;
	
	public PartidoDTO iniciarPartidaLibreIndividual(String categoria, String apodo) throws RemoteException, PartidoException, CartaException, JugadorException, GrupoException;
	
	public PartidoDTO iniciarPartidaLibre(ParejaDTO pareja) throws RemoteException, ParejaException, CartaException, GrupoException;
	
	public boolean iniciarPartidaCerrada() throws RemoteException;
	
	public boolean buscarJugDisponibles() throws RemoteException;
	
	public void repartirCartas(PartidoDTO pd) throws RemoteException, CartaException;
	
	public boolean nuevaMano() throws RemoteException;

	public boolean registrarPuntaje() throws RemoteException;
	
	public boolean eliminarJugador() throws RemoteException;
	
	public boolean modificarJugador() throws RemoteException;

	public void nuevaJugada(Integer idPartido, List<TurnoDTO> turnos) throws PartidoException,RemoteException, GrupoException, JugadorException;

	public JugadorDTO buscarJugadorDTO(String nombre) throws RemoteException,JugadorException, GrupoException;

	public TurnoDTO buscarNovedades(Integer idPartido)throws RemoteException, TurnoException, GrupoException;

}
