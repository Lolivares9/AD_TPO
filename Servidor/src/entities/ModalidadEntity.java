package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MODALIDAD")
public class ModalidadEntity {

	@Id
	@Column (name="ID_MODALIDAD")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idModalidad;
	
	@Column(name="DESCRIPCION")
	private int descripcion;
	
	@Column(name="INDIVIDUAL")
	private boolean individual;
	
	public ModalidadEntity() {
		super();
	}

	public Integer getIdModalidad() {
		return idModalidad;
	}

	public void setIdModalidad(Integer idModalidad) {
		this.idModalidad = idModalidad;
	}

	public int getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(int descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isIndividual() {
		return individual;
	}

	public void setIndividual(boolean individual) {
		this.individual = individual;
	}
	
}

