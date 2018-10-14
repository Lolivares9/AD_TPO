package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CHICOS")
public class ChicoEntity {

	@Id
	@Column (name="ID_CHICO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idChico;
	
	@ManyToOne
	@JoinColumn(name="ID_PARTIDO")
	private PartidoEntity idPartido;
	
	@Column(name="NUMERO_CHICO")
	private int numeroChico;
	
	@Column(name="FINALIZADO")
	private boolean finalizado;

	@OneToOne
	@JoinColumn(name="ID_PAREJA_GANADORA")
	private ParejaEntity parejaGanadora;
	
	@Column(name="PUNTAJE_PAREJA1")
	private int puntajePareja1;

	@Column(name="PUNTAJE_PAREJA2")
	private int puntajePareja2;
	
	public ChicoEntity() {
		super();
	}

	public Integer getIdChico() {
		return idChico;
	}

	public void setIdChico(Integer idChico) {
		this.idChico = idChico;
	}

	public int getNumeroChico() {
		return numeroChico;
	}

	public void setNumeroChico(int numeroChico) {
		this.numeroChico = numeroChico;
	}

	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	public ParejaEntity getParejaGanadora() {
		return parejaGanadora;
	}

	public void setParejaGanadora(ParejaEntity parejaGanadora) {
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

	public PartidoEntity getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(PartidoEntity idPartido) {
		this.idPartido = idPartido;
	}
}
