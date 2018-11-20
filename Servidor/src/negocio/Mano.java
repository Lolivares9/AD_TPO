package negocio;

import java.util.ArrayList;
import java.util.List;

import dao.ManoDAO;
import dao.PartidoDAO;
import dto.BazaDTO;
import dto.ManoDTO;
import enums.Envite;
import excepciones.GrupoException;
import excepciones.PartidoException;

/**
 * Soy la jugada de 2 o 3 bazas 
 * (los 4 jugadores jugaron sus 3 cartas)
 */
public class Mano {
	private Integer idMano;
	private int numeroMano;
	private Pareja parejaGanadora;
	private int puntajePareja1;
	private int puntajePareja2;
	private List<Baza> bazas;
	
	public Mano(int numeroMano, Pareja parejaGanadora, int puntajePareja1, int puntajePareja2,List<Baza> bazas) {
		super();
		this.numeroMano = numeroMano;
		this.parejaGanadora = parejaGanadora;
		this.puntajePareja1 = puntajePareja1;
		this.puntajePareja2 = puntajePareja2;
		this.bazas = bazas;
	}

	public Mano() {
	}
	
	public int getPuntajePareja1() {
		return puntajePareja1;
	}

	public void setPuntajePareja1(int puntajePareja1) {
		this.puntajePareja1 = puntajePareja1;
	}

	public int getPuntajePareja2() {
		return puntajePareja2;
	}

	public void setPuntajePareja2(int puntajePareja2) {
		this.puntajePareja2 = puntajePareja2;
	}

	public int getNumeroMano() {
		return numeroMano;
	}
	public void setNumeroMano(int numeroMano) {
		this.numeroMano = numeroMano;
	}
	public Pareja getParejaGanadora() {
		return parejaGanadora;
	}
	public void setParejaGanadora(Pareja parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}
	
	public boolean guardar(Chico chico){
		return ManoDAO.getInstancia().guardar(this);
	}

	public Integer getIdMano() {
		return idMano;
	}

	public void setIdMano(Integer idMano) {
		this.idMano = idMano;
	}
	
	/**
	 * Constructor para crear un DTO sin todas sus variables completas
	 * @return
	 * @throws GrupoException 
	 */
	public ManoDTO toDTO() throws GrupoException {
		List <BazaDTO> bazasDTO = new ArrayList <BazaDTO> ();
		for (Baza b: bazas) {
			bazasDTO.add(b.toDTO());
		}
		return new ManoDTO(idMano, numeroMano, parejaGanadora.toDTO(), puntajePareja1,puntajePareja2, bazasDTO);
	}

	public List<Baza> getBazas() {
		return bazas;
	}

	public void setBazas(List<Baza> bazas) {
		this.bazas = bazas;
	}
	
	public void guardar(){
		ManoDAO.getInstancia().guardar(this);
	}
	
	private static void actualizacionJuegoPartido(Partido partidoNegocio, Chico chicoActual, Mano manoActual, Baza bazaActual) {
		if (bazaActual.isBazaTerminada()) {
			if (manoActual.getParejaGanadora() != null) {
				if (chicoActual.isFinalizado()) {
					partidoNegocio.crearNuevoChico();
				}
				else {
					chicoActual.crearNuevaMano();
				}
			}
			else {
				manoActual.crearNuevaBaza();					
			}
		}
		partidoNegocio.actualizar();
	}

	public static boolean analizarEnviteTantos(int idPartido,Turno turnoActual ) throws PartidoException {
		int indiceChico = 0;
		int indiceMano = 0;
		int indiceBaza = 0;
		Partido partidoNegocio = PartidoDAO.getInstancia().buscarPartidoPorID(idPartido);
		if(partidoNegocio.getNumeroChicoActual() > 0){
			indiceChico = partidoNegocio.getNumeroChicoActual()-1;
		}
		if(partidoNegocio.getChico().size() > 0){
			if(partidoNegocio.getChico().get(indiceChico).getManos().size() > 0){
				indiceMano =  partidoNegocio.getChico().get(indiceChico).getManos().size()-1;//agarro la mano actual
				Mano manoActual = partidoNegocio.getChico().get(indiceChico).getManos().get(indiceMano);
				if(manoActual.getBazas().size() > 0){
					indiceBaza = manoActual.getBazas().size() - 1;//agarro la baza/ronda actual
				}
			}
		}
		Chico chicoActual = partidoNegocio.getChico().get(indiceChico);
		Mano manoActual = chicoActual.getManos().get(indiceMano);
		Baza bazaActual = manoActual.getBazas().get(indiceBaza);
				
		Turno turnoEnvite = null;
		for (Turno t: bazaActual.getTurnos()) {
			if (t.getJugador().getId().equals(turnoActual.getJugador().getId())) {
				turnoEnvite = t;
				break;
			}
		}
		
		bazaActual.analizarEnviteTantos(partidoNegocio,turnoEnvite);
		
		manoActual.cargarMovimientoBaza(bazaActual,partidoNegocio);
		chicoActual.cargarMovimientoMano(manoActual,partidoNegocio);
		partidoNegocio.cargarMovimientoChico(chicoActual);
		
		actualizacionJuegoPartido(partidoNegocio, chicoActual, manoActual, bazaActual);
		
		return true;
	}
	
	private void cargarMovimientoBaza(Baza bazaActual, Partido p) {
		if (bazaActual.getGanadores().getIdPareja() != null) {
			
			this.setPuntajePareja1(puntajePareja1 + bazaActual.getPuntajePareja1());
			this.setPuntajePareja2(puntajePareja2 + bazaActual.getPuntajePareja2());	
			
			boolean finalizaMano = isFinalizaMano(p.getParejas().get(0),p.getParejas().get(1),this.getBazas(),bazaActual);
			if(finalizaMano){
				this.setParejaGanadora(bazaActual.getGanadores());
			}
		}
		
	}

	public static boolean analizarEnviteJuego(int idPartido, Turno turnoActual) throws PartidoException {
		int indiceChico = 0;
		int indiceMano = 0;
		int indiceBaza = 0;
		int indiceTurno = 0;
		Partido partidoNegocio = PartidoDAO.getInstancia().buscarPartidoPorID(idPartido);
		Pareja pareja1 = partidoNegocio.getParejas().get(0);
		Pareja pareja2 = partidoNegocio.getParejas().get(1);
		if(partidoNegocio.getNumeroChicoActual() > 0){
			indiceChico = partidoNegocio.getNumeroChicoActual()-1;
		}
		if(partidoNegocio.getChico().size() > 0){
			if(partidoNegocio.getChico().get(indiceChico).getManos().size() > 0){
				indiceMano =  partidoNegocio.getChico().get(indiceChico).getManos().size()-1;//agarro la mano actual
				Mano manoActual = partidoNegocio.getChico().get(indiceChico).getManos().get(indiceMano);
				if(manoActual.getBazas().size() > 0){
					indiceBaza = manoActual.getBazas().size() - 1;//agarro la baza/ronda actual
				}
			}
		}
		Chico chicoActual = partidoNegocio.getChico().get(indiceChico);
		Mano manoActual = chicoActual.getManos().get(indiceMano);
		Baza bazaActual = manoActual.getBazas().get(indiceBaza);
		
		if(bazaActual.getTurnos().size() > 0){
			indiceTurno = bazaActual.getTurnos().size()-1;
		}
		
		Turno turnoEnvite = null;
		for (Turno t: bazaActual.getTurnos()) {
			if (t.getJugador().getId().equals(turnoActual.getJugador().getId())) {
				turnoEnvite = t;
				break;
			}
		}
		bazaActual.analizarEnviteJuego(partidoNegocio,turnoEnvite);
		
		manoActual.cargarMovimientoBaza(bazaActual,partidoNegocio);
		chicoActual.cargarMovimientoMano(manoActual,partidoNegocio);
		partidoNegocio.cargarMovimientoChico(chicoActual);
		
		actualizacionJuegoPartido(partidoNegocio, chicoActual, manoActual, bazaActual);		
		return false;
	}
	
	public void crearNuevaBaza() {
		int indiceUltimaBaza = 0;
		Baza bazaNueva = new Baza(bazas.size() + 1, null, 0, 0, null);
		bazas.add(bazaNueva);
	}

	private boolean isFinalizaMano(Pareja p1,Pareja p2,List<Baza> bazas,Baza bazaActual) {
		boolean finaliza_mano = false;
		if(bazaActual.getNumero() == 1){
			finaliza_mano = false;
		}
		else if(bazaActual.getNumero() == 2){
			if((bazas.get(0).getGanadores().equals(p1) && bazaActual.getGanadores().equals(p1)) || (bazas.get(0).getGanadores().equals(p2) && bazaActual.getGanadores().equals(p2))){
				finaliza_mano = true;
			}
		}
		else if(bazaActual.getNumero() == 3){
			if((bazaActual.getGanadores().equals(p1) && bazas.get(1).getGanadores().equals(p1)) || (bazaActual.getGanadores().equals(p2) && bazas.get(1).getGanadores().equals(p2))){
				finaliza_mano = true;
			}
			else if((bazaActual.getGanadores().equals(p1) && bazas.get(0).getGanadores().equals(p1)) || (bazaActual.getGanadores().equals(p2) && bazas.get(0).getGanadores().equals(p2))){
				finaliza_mano = true;
			}
		}
		return finaliza_mano;
	}

	




}
