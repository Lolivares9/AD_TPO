package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import dto.BazaDTO;
import dto.ChicoDTO;
import dto.GrupoDTO;
import dto.JugadorDTO;
import dto.ManoDTO;
import dto.ParejaDTO;
import dto.PartidoDTO;
import dto.TurnoDTO;
import enums.Envite;
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
	
	public boolean ingresarNuevosMiembros(String nombreGrupo, List<JugadorDTO> jugadores) throws RemoteException, GrupoException, JugadorException;

	public JugadorDTO iniciarSesion(JugadorDTO jugador) throws RemoteException, GrupoException;
	
	public List<PartidoDTO> buscarPartidosJugados(JugadorDTO jugador, TipoModalidad modalidad, Date fechaIni, Date fechaFin) throws RemoteException, ParejaException, PartidoException;
	
	public List<ChicoDTO> buscarChicosPorPartido(PartidoDTO partido) throws RemoteException, ChicoException;
	
	public Map<ManoDTO, Map<BazaDTO, List<TurnoDTO>>> obtenerDetalleDeChico(ChicoDTO chico) throws RemoteException, ManoException, BazaException, TurnoException, GrupoException;
	
	public void buscarPartidaLibreIndividual(JugadorDTO jugador)throws RemoteException, JugadorException;
	
	public PartidoDTO buscarPartidaLobby(String apodoJugador, String modalidad) throws RemoteException, PartidoException, ParejaException, JugadorException;
	
	public PartidoDTO iniciarPartidaLibreIndividual(String categoria, String apodo) throws RemoteException, PartidoException, CartaException, JugadorException;
	
	public PartidoDTO iniciarPartidaLibre(ParejaDTO pareja) throws RemoteException, ParejaException, CartaException, GrupoException;
	
	public boolean iniciarPartidaCerrada() throws RemoteException;
	
	public boolean buscarJugDisponibles() throws RemoteException;
	
	public void repartirCartas(PartidoDTO pd) throws RemoteException, CartaException, PartidoException;
	
	public boolean nuevaMano() throws RemoteException;

	public boolean registrarPuntaje() throws RemoteException;
	
	public boolean eliminarJugador() throws RemoteException;
	
	public boolean modificarJugador() throws RemoteException;

	public void nuevaJugada(Integer idPartido, TurnoDTO turno) throws PartidoException,RemoteException, JugadorException;

	public JugadorDTO buscarJugadorDTO(String nombre) throws RemoteException,JugadorException;

	public List<TurnoDTO> buscarTurnos(Integer idBaza)throws RemoteException, TurnoException;

	public Map<String, Object> buscarActualizacion(int idPartido, int numBazas, int numManos, int numChico) throws RemoteException, PartidoException;
	
	public TurnoDTO getRespuestaEnvite(Integer idBaza, Envite enviteActual) throws RemoteException, TurnoException;	
	
	public BazaDTO buscarBaza(Integer idBaza) throws RemoteException, BazaException;

	public GrupoDTO buscarGrupo(String nombre) throws RemoteException, GrupoException;

	public PartidoDTO iniciarPartidaCerrada(List<ParejaDTO> parejas) throws RemoteException, PartidoException, CartaException, JugadorException;

	public List<GrupoDTO> traerGruposJugador(int idJugador) throws RemoteException;

	public List<String> traerJugadoresGrupo(String nombreGrupo) throws RemoteException;

}
