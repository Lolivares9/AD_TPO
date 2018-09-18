package entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MANOS")
public class ManoEntity {

	@Id
	@Column (name="ID_MANO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idMano;
	
	@OneToMany
	@JoinColumn(name="ID_CHICO")
	private List<ChicoEntity> chico;
	
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

	public List<ChicoEntity> getChico() {
		return chico;
	}

	public void setChico(List<ChicoEntity> chico) {
		this.chico = chico;
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
	
}
