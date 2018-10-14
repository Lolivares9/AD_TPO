package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PARTIDOS")
public class PartidoEntity {

	@Id
	@Column (name="ID_PARTIDO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPartido;
	
	@OneToOne
	@JoinColumn(name="ID_MODALIDAD")
	private ModalidadEntity modalidad;
	
	@ManyToOne
	@JoinColumn(name="ID_PAREJA_GANADORA")
	private ParejaEntity parejaGanadora;
	
	@Column(name="FECHA")
	@Temporal(TemporalType.DATE)
	private Date fecha;

	@ManyToMany (cascade=CascadeType.ALL)
	List<ParejaEntity> parejas = new ArrayList<ParejaEntity>();

	public List<ParejaEntity> getParejas() {
		return parejas;
	}

	public void setParejas(List<ParejaEntity> parejas) {
		this.parejas = parejas;
	}
	
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public PartidoEntity() {
		super();
	}
	
	public Integer getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(Integer idPartido) {
		this.idPartido = idPartido;
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
