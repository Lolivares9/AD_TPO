package dto;
import java.io.Serializable;
import java.util.List;

public class ChicoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1843892719080160092L;
	
	private Integer idChico;
	private int numero;
	private boolean finalizado;
	private ParejaDTO parejaGanadora;
	private int puntajePareja1;
	private int puntajePareja2;
	private List<ManoDTO> manos;
	
	public ChicoDTO(int numero, boolean finaizado, ParejaDTO parejaGanadora, int puntajePareja1, int puntajePareja2) {
		super();
		this.numero = numero;
		this.finalizado = finaizado;
		this.parejaGanadora = parejaGanadora;
		this.puntajePareja1 = puntajePareja1;
		this.puntajePareja2 = puntajePareja2;
	}
	
	public ChicoDTO() {
		super();
	}
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public boolean isFinaizado() {
		return finalizado;
	}
	public void setFinaizado(boolean finaizado) {
		this.finalizado = finaizado;
	}
	public ParejaDTO getParejaGanadora() {
		return parejaGanadora;
	}
	public void setParejaGanadora(ParejaDTO parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
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

	public List<ManoDTO> getManos() {
		return manos;
	}

	public void setManos(List<ManoDTO> manos) {
		this.manos = manos;
	}
}
