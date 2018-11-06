package remoto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;
import java.util.Map;

import controlador.Controlador;
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
import interfaces.InterfaceRemota;
import negocio.Pareja;
import negocio.Partido;

public class ObjetoRemoto extends UnicastRemoteObject implements InterfaceRemota {

	private static final long serialVersionUID = 7432516509801597745L;

	public ObjetoRemoto() throws RemoteException {}

	
	public JugadorDTO altaJugador(JugadorDTO jugador) throws RemoteException, JugadorException {
		JugadorDTO jugLog = null;
		try {
			jugLog=Controlador.getInstancia().altaJugador(jugador);
		} catch (JugadorException e) {
			e.printStackTrace();
		}
		return jugLog;
	}

	//OK
	public boolean crearGrupo(String nombreGrupo, JugadorDTO jugadorAdmin) throws RemoteException, GrupoException, JugadorException {
		return Controlador.getInstancia().crearGrupo(nombreGrupo, jugadorAdmin);
	}
	
	//OK
	public boolean llenarGrupo(String nombreGrupo, List<JugadorDTO> jugadores) throws RemoteException, GrupoException {
		return Controlador.getInstancia().llenarGrupo(nombreGrupo, jugadores);
	}

	//OK
	public JugadorDTO iniciarSesion(JugadorDTO jugador) throws RemoteException {
		JugadorDTO jugLog = null;
		try {
			jugLog = Controlador.getInstancia().iniciarSesion(jugador);
		} catch (JugadorException e) {
			e.printStackTrace();
		}
		return jugLog;
	}
	
	@Override
	public void buscarPartidaLibreIndividual(JugadorDTO jugador) throws RemoteException, JugadorException {
		Controlador.getInstancia().buscarPartidaLibreIndividual(jugador);
	}
	
	
	public PartidoDTO buscarPartidaLobby(String apodoJugador, String modalidad) throws RemoteException, PartidoException, ParejaException, JugadorException {
		return Controlador.getInstancia().buscarPartidaLobby(apodoJugador, modalidad);
	}
	//OK
	public PartidoDTO iniciarPartidaLibreIndividual(String categ, String apodo) throws RemoteException, PartidoException, CartaException, JugadorException {
		
		return Controlador.getInstancia().iniciarPartidaLibreIndividual(categ,apodo);
	}
	
	//FALTARIA TESTEAR
	public PartidoDTO iniciarPartidaLibre(ParejaDTO pareja) throws RemoteException, ParejaException, CartaException {
		Pareja parej = null;
		Partido partidoNuevo = Controlador.getInstancia().iniciarPartidaLibre(parej);
		PartidoDTO part = partidoNuevo.toDTO();
		return part;
	}

	//SIN HACER, ESTE NO HACE CONTROL DE CATEGORIAS
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
	public void repartirCartas(PartidoDTO pd) throws RemoteException {
		try {
			Controlador.getInstancia().repartiCartas(pd);
		} catch (CartaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	@Override
	public List<PartidoDTO> buscarPartidosJugados(JugadorDTO jugador, TipoModalidad modalidad, Date fechaIni, Date fechaFin) throws ParejaException, PartidoException {
		return Controlador.getInstancia().buscarPartidosJugados(jugador, modalidad, fechaIni, fechaFin);
	}
	
	@Override
	public List<ChicoDTO> buscarChicosPorPartido(PartidoDTO partido) throws ChicoException{
		return Controlador.getInstancia().buscarChicosPorPartido(partido);
	}
	
	@Override
	public Map<ManoDTO,Map<BazaDTO,List<TurnoDTO>>> obtenerDetalleDeChico(ChicoDTO chico) throws ManoException, BazaException, TurnoException{
		return Controlador.getInstancia().obtenerDetalleDeChico(chico);
	}


	@Override
	public PartidoDTO buscarPartidaLobby(String apodoJugador) throws RemoteException, PartidoException {
		// TODO Auto-generated method stub
		return null;
	}



	
}
