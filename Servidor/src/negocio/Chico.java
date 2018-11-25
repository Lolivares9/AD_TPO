package negocio;

import java.util.ArrayList;
import java.util.List;

import dao.ChicoDAO;
import dto.ChicoDTO;
import excepciones.GrupoException;
/**
 * Formo parte de un Partido y contengo una coleccion de 3 manos como maximo
 * Un chico es finalizado cuando se llegan a los 30 tantos
 * (15 malos y 15 buenos)
 */
public class Chico {
	private Integer idChico;
	private int numero;
	private boolean finalizado;
	private Pareja parejaGanadora;
	private int puntajePareja1;
	private int puntajePareja2;
	private List<Mano> manos;
	
	public Chico(int numero, boolean finalizado, Pareja parejaGanadora, int puntajePareja1, int puntajePareja2) {
		super();
		this.numero = numero;
		this.manos = new ArrayList<Mano>();
		this.finalizado = finalizado;
		this.parejaGanadora = parejaGanadora;
		this.puntajePareja1 = puntajePareja1;
		this.puntajePareja2 = puntajePareja2;
	}
	
	public Chico(Integer idChico, int numero, List<Mano> manos, boolean finalizado, Pareja parejaGanadora,
			int puntajePareja1, int puntajePareja2) {
		super();
		this.idChico = idChico;
		this.numero = numero;
		this.manos = manos;
		this.finalizado = finalizado;
		this.parejaGanadora = parejaGanadora;
		this.puntajePareja1 = puntajePareja1;
		this.puntajePareja2 = puntajePareja2;
	}
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public List<Mano> getManos() {
		return manos;
	}

	public void setManos(List<Mano> manos) {
		this.manos = manos;
	}

	public Pareja getParejaGanadora() {
		return parejaGanadora;
	}
	public void setParejaGanadora(Pareja parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}
	
	public boolean guardar(){
		return ChicoDAO.getInstancia().guardar(this);
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
	
	public Integer getIdChico() {
		return idChico;
	}

	public void setIdChico(Integer idChico) {
		this.idChico = idChico;
	}

	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	public ChicoDTO toDTO() throws GrupoException {
		if(parejaGanadora != null) {
			return new ChicoDTO(numero, finalizado, parejaGanadora.toDTO(), puntajePareja1, puntajePareja2);
		}else {
			return new ChicoDTO(numero, finalizado, null, puntajePareja1, puntajePareja2);
		}
	}

	public void crearNuevaMano() {
		if (manos == null) {
			manos = new ArrayList<Mano> ();
		}
		
		Mano m = new Mano(manos.size() + 1 , null, 0, 0, new ArrayList <Baza>());
		m.crearNuevaBaza();
		manos.add(m);
	}

	public void cargarMovimientoMano(Mano manoActual, Partido p) {
		
		Pareja pareja1 = p.getParejas().get(0);
		Pareja pareja2 = p.getParejas().get(1);
		
		if (manoActual.getParejaGanadora() != null) {
			setearNuevoOrdenMano(manoActual,p);
			this.setPuntajePareja1(puntajePareja1 + manoActual.getPuntajePareja1());
			this.setPuntajePareja2(puntajePareja2 + manoActual.getPuntajePareja2());
			
			if (puntajePareja1 >= 10) {
				this.setPuntajePareja1(10);
				this.setParejaGanadora(pareja1);
				this.setFinalizado(true);
			}
			else if (puntajePareja2 >= 10) {
				this.setPuntajePareja2(10);
				this.setParejaGanadora(pareja2);
				this.setFinalizado(true);
			}
		}
	}
	
	/*COMO EMPIEZA UNA NUEVA MANO, TENGO QUE REORDENAR A LOS JUGADORES. ES DECIR, EL QUE VA A EMPEZAR AHORA, VA A SER EL JUGADOR
	 * QUE HABIA TIRADO LA SEGUNDA CARTA EN LA BAZA 1*/
	private void setearNuevoOrdenMano(Mano manoActual,Partido p) {
		Baza primeraBaza = manoActual.getBazas().get(0);
		int idJug = primeraBaza.getTurnos().get(0).getJugador().getId();
		Pareja pareja1 = p.getParejas().get(0);
		Pareja pareja2 = p.getParejas().get(1);
		if(pareja1.getJugador1().getId() == idJug){
			pareja2.getJugador1().setNumeroTurnoPartido(1);
			pareja1.getJugador2().setNumeroTurnoPartido(2);
			pareja2.getJugador2().setNumeroTurnoPartido(3);
			pareja1.getJugador1().setNumeroTurnoPartido(4);
		}
		else if(pareja2.getJugador1().getId() == idJug){
			pareja1.getJugador2().setNumeroTurnoPartido(1);
			pareja2.getJugador2().setNumeroTurnoPartido(2);
			pareja1.getJugador1().setNumeroTurnoPartido(3);
			pareja1.getJugador2().setNumeroTurnoPartido(4);
		}
		else if(pareja1.getJugador2().getId() == idJug){
			pareja2.getJugador2().setNumeroTurnoPartido(1);
			pareja1.getJugador1().setNumeroTurnoPartido(2);
			pareja2.getJugador1().setNumeroTurnoPartido(3);
			pareja1.getJugador2().setNumeroTurnoPartido(4);
		}
		else if(pareja2.getJugador2().getId() == idJug){
			pareja1.getJugador1().setNumeroTurnoPartido(1);
			pareja2.getJugador1().setNumeroTurnoPartido(2);
			pareja1.getJugador2().setNumeroTurnoPartido(3);
			pareja2.getJugador2().setNumeroTurnoPartido(4);
		}
	}
}
