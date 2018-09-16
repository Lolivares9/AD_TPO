package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MANOS")
public class ManoEntity {

	@Id
	@Column (name="ID_MANO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idMano;
	
	@OneToMany
	@JoinColumn(name="ID_CHICO")
	private ChicoEntity chico;
	
	@Column(name="NUMERO_MANO")
	private int numeroMano;
	
	@Column(name="ID_PAREJA_GANADORA")
	private ParejaEntity parejaGanadora;

	public int getIdMano() {
		return idMano;
	}

	public void setIdMano(int idMano) {
		this.idMano = idMano;
	}

	public ChicoEntity getChico() {
		return chico;
	}

	public void setChico(ChicoEntity chico) {
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
