package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BAZAS")
public class BazaEntity {

	@Id
	@Column (name="ID_BAZA")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idBaza;
	
	@ManyToOne
	@JoinColumn(name="ID_MANO")
	private ManoEntity mano;
	
	@Column(name="NUMERO_BAZA")
	private int numeroBaza;
	
	@ManyToOne
	@JoinColumn(name="GANADORES_BAZA")
	private ParejaEntity ganadoresBaza;
	
	@Column(name="PUNTAJE_PAREJA1")
	private int puntajePareja1;
	
	@Column(name="PUNTAJE_PAREJA2")
	private int puntajePareja2;

	public BazaEntity() {
		super();
	}
	
	public Integer getIdBaza() {
		return idBaza;
	}

	public void setIdBaza(Integer idBaza) {
		this.idBaza = idBaza;
	}

	public ManoEntity getMano() {
		return mano;
	}

	public void setMano(ManoEntity mano) {
		this.mano = mano;
	}

	public int getNumeroBaza() {
		return numeroBaza;
	}

	public void setNumeroBaza(int numeroBaza) {
		this.numeroBaza = numeroBaza;
	}

	public ParejaEntity getGanadoresBaza() {
		return ganadoresBaza;
	}

	public void setGanadoresBaza(ParejaEntity ganadoresBaza) {
		this.ganadoresBaza = ganadoresBaza;
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
	
	
}
