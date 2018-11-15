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
}
