package entities;

import java.util.List;

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
@Table(name = "MANOS")
public class ManoEntity {

	@Id
	@Column (name="ID_MANO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idMano;
	
	@ManyToOne
	@JoinColumn(name="ID_CHICO")
	private ChicoEntity idChico;
	
	@Column(name="NUMERO_MANO")
	private int numeroMano;
	
	@OneToOne
	@JoinColumn(name="ID_PAREJA_GANADORA")
	private ParejaEntity parejaGanadora;
	
	
	
	public ManoEntity() {
		super();
	}

	public Integer getIdMano() {
		return idMano;
	}

	public void setIdMano(Integer idMano) {
		this.idMano = idMano;
	}

	public int getNumeroMano() {
		return numeroMano;
	}

	public void setNumeroMano(int numeroMano) {
		this.numeroMano = numeroMano;
	}

	public ParejaEntity getParejaGanadora() {
		return parejaGanadora;
	}

	public void setParejaGanadora(ParejaEntity parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}

	public ChicoEntity getIdChico() {
		return idChico;
	}

	public void setIdChico(ChicoEntity idChico) {
		this.idChico = idChico;
	}


	
}
