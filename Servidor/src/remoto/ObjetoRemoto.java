package remoto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;
import java.util.Map;

import controlador.Controlador;
import dto.BazaDTO;
import dto.CartaDTO;
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
import negocio.Jugador;
import negocio.Pareja;
import negocio.Partido;
import util.DTOMapper;

public class ObjetoRemoto extends UnicastRemoteObject implements InterfaceRemota {

	private static final long serialVersionUID = 7432516509801597745L;

	public ObjetoRemoto() throws RemoteException {}

	@Override
	public boolean altaJugador(JugadorDTO jugador) throws RemoteException, JugadorException {
		Controlador.getInstancia().altaJugador(jugador);
		return false;
	}

	//OK
	public boolean crearGrupo(String nombreGrupo, JugadorDTO jugadorAdmin) throws RemoteException, GrupoException, JugadorException {
		return Controlador.getInstancia().crearGrupo(nombreGrupo, jugadorAdmin);
	}
	
	//FALTA TESTEAR FACU
	public boolean llenarGrupo(String nombreGrupo, List<JugadorDTO> jugadores) throws RemoteException, GrupoException {
		return Controlador.getInstancia().llenarGrupo(nombreGrupo, jugadores);
	}

	//OK
	public boolean iniciarSesion(JugadorDTO jugador) throws RemoteException {
		boolean inicioBien = false;
		try {
			inicioBien = Controlador.getInstancia().iniciarSesion(jugador);
		} catch (JugadorException e) {
			e.printStackTrace();
		}
		return inicioBien;
	}
	
	//FALTA TESTEAR Y HACER UNOS AJUSTES, ESTAMOS DE ACUERDO QUE ESTO DEVUELVE UN PARTIDO CON LAS PAREJAS YA PARA JUGAR (EN CASO DE QUE SE CUMPLA TODO CLARO)?
	public PartidoDTO iniciarPartidaLibreIndividual(JugadorDTO jug) throws RemoteException {
		Jugador jugador = DTOMapper.getInstancia().jugadorDTOtoNegocio(jug);
		PartidoDTO part = null;//ME FALTA HACER EL METODO EN EL DTOMAPPER QUE CONVIERTA UN PARTIDO A DTO PARA MANDARLO AL CLIENTEWEB
		Partido partidoNuevo = Controlador.getInstancia().iniciarPartidaLibreIndividual(jugador.getCategoria(),jugador);
		return part;
	}
	
	//SIN HACER, ESTAMOS DE ACUERDO QUE ESTO DEVUELVE UN PARTIDO CON LAS PAREJAS YA PARA JUGAR (EN CASO DE QUE ENCUENTRE OTRA PAREJA LIBRE)?
	//RECORDAR QUE ESTE TAMBIEN HACE CONTROL DE CATEGORIAS
	public PartidoDTO iniciarPartidaLibre(ParejaDTO pareja) throws RemoteException {
		Pareja parej = null;
		PartidoDTO part = null;//ME FALTA HACER EL METODO EN EL DTOMAPPER QUE CONVIERTA UN PARTIDO A DTO PARA MANDARLO AL CLIENTEWEB
		Partido partidoNuevo = Controlador.getInstancia().iniciarPartidaLibre(parej);
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
	public List<CartaDTO> repartirCartas() throws RemoteException {
		try {
			return Controlador.getInstancia().repartiCartas();
		} catch (CartaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
	
}
