package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PARTIDOS")
public class PartidoEntity {

	@Id
	@Column (name="ID_PARTIDO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPartido;
	
	@OneToMany
	@JoinColumn(name="ID_CHICO")
	private ChicoEntity chico;
	
	@OneToOne
	@JoinColumn(name="ID_MODALIDAD")
	private ModalidadEntity modalidad;
	
	@ManyToMany
	@JoinColumn(name="ID_PAREJA_GANADORA")
	private ParejaEntity parejaGanadora;

	public int getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(int idPartido) {
		this.idPartido = idPartido;
	}

	public ChicoEntity getChico() {
		return chico;
	}

	public void setChico(ChicoEntity chico) {
		this.chico = chico;
	}

	public ModalidadEntity getModalidad() {
		return modalidad;
	}

	public void setModalidad(ModalidadEntity modalidad) {
		this.modalidad = modalidad;
	}

	public ParejaEntity getParejaGanadora() {
		return parejaGanadora;
	}

	public void setParejaGanadora(ParejaEntity parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}


}
