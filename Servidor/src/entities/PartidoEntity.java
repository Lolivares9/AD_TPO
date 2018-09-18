package entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	private List<ChicoEntity> chico;
	
	@OneToOne
	@JoinColumn(name="ID_MODALIDAD")
	private ModalidadEntity modalidad;
	
	@ManyToOne
	@JoinColumn(name="ID_PAREJA_GANADORA")
	private ParejaEntity parejaGanadora;

	public PartidoEntity() {
		super();
	}
	
	public Integer getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(Integer idPartido) {
		this.idPartido = idPartido;
	}

	public List<ChicoEntity> getChico() {
		return chico;
	}

	public void setChico(List<ChicoEntity> chico) {
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
