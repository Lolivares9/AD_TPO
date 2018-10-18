package delegado;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import dto.CartaDTO;
import dto.JugadorDTO;
import dto.ParejaDTO;
import dto.PartidoDTO;
import excepciones.CartaException;
import excepciones.ComunicationException;
import excepciones.GrupoException;
import excepciones.JugadorException;
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
			throw new ComunicationException("Se produjo un error en la comunicaci�n");
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
			ir.AltaJugador(jugador);
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

	public PartidoDTO iniciarPartidaLibreIndividual(JugadorDTO jugador) throws ComunicationException {
		PartidoDTO partido = null;
		try {
			partido = ir.iniciarPartidaLibreIndividual(jugador);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return partido;
	}

	public PartidoDTO iniciarPartidaLibre(ParejaDTO pareja) throws ComunicationException {
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
	
	/*
	public int suma(int a, int b) throws ComunicacionException{
		try {
			return ir.suma(a, b);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");	
		}
	}*/	
}
