package delegado;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import dto.BazaDTO;
import dto.CartaDTO;
import dto.ChicoDTO;
import dto.JugadorDTO;
import dto.ManoDTO;
import dto.PartidoDTO;
import dto.TurnoDTO;
import enums.TipoModalidad;
import excepciones.BazaException;
import dto.ParejaDTO;
import excepciones.CartaException;
import excepciones.ChicoException;
import excepciones.ComunicationException;
import excepciones.GrupoException;
import excepciones.JugadorException;
import excepciones.ManoException;
import excepciones.ParejaException;
import excepciones.PartidoException;
import excepciones.TurnoException;
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
			ir.altaJugador(jugador);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (JugadorException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean iniciarSesion(JugadorDTO jugador) throws ComunicationException {
		boolean inicioBien = false;
		try {
			inicioBien = ir.iniciarSesion(jugador);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return inicioBien;
	}
	
	public boolean crearGrupo(String nombreGrupo, JugadorDTO jugadorAdmin) throws ComunicationException{
		try {
			ir.crearGrupo(nombreGrupo, jugadorAdmin);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (GrupoException e) {
			e.printStackTrace();
		} catch (JugadorException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean llenarGrupo(String nombreGrupo, List<JugadorDTO> jugadores) throws ComunicationException {
		try {
			ir.llenarGrupo(nombreGrupo, jugadores);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (GrupoException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void buscarPartidaLibreIndividual(JugadorDTO jugador) {
		try {
			ir.buscarPartidaLibreIndividual(jugador);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (JugadorException e) {
			e.printStackTrace();
		}
	}
	public PartidoDTO iniciarPartidaLibreIndividual(JugadorDTO jugador) throws ComunicationException, PartidoException {
		PartidoDTO partido = null;
		try {
			partido = ir.iniciarPartidaLibreIndividual(jugador);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		catch (PartidoException e) {
			e.printStackTrace();
		}
		return partido;
	}

	public PartidoDTO iniciarPartidaLibre(ParejaDTO pareja) throws ComunicationException, ParejaException {
		PartidoDTO partido = null;
		try {
			partido = ir.iniciarPartidaLibre(pareja);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return partido;
	}
	public boolean iniciarPartidaCerrada() throws ComunicationException {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean buscarJugDisponibles() throws ComunicationException {
		// TODO Auto-generated method stub
		return false;
	}


	public List<CartaDTO> repartirCartas() throws ComunicationException {
		try {
			return ir.repartirCartas();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (CartaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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

	public List<PartidoDTO> buscarPartidosJugados(JugadorDTO jugador, TipoModalidad modalidad, Date fechaIni, Date fechaFin) throws ComunicationException {
		try {
			return ir.buscarPartidosJugados(jugador, modalidad, fechaIni, fechaFin);
		} catch (ParejaException | PartidoException | RemoteException e) {
			e.printStackTrace();
		}
		return Collections.<PartidoDTO>emptyList();
	}
	
	public List<ChicoDTO> buscarChicosPorPartido(PartidoDTO partido){
		try {
			return ir.buscarChicosPorPartido(partido);
		} catch (ChicoException | RemoteException e) {
			e.printStackTrace();
		}
		return Collections.<ChicoDTO>emptyList();
	}
	
	public Map<ManoDTO,Map<BazaDTO,List<TurnoDTO>>> obtenerDetalleDeChico(ChicoDTO chico){
		try {
			return ir.obtenerDetalleDeChico(chico);
		} catch (ManoException | BazaException | TurnoException | RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}


}
