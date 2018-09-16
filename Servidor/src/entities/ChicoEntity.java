package entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CHICOS")
public class ChicoEntity {

	@Id
	@Column (name="ID_CHICO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idChico;
	
	@Column(name="NUMERO_CHICO")
	private int numeroChico;
	
	@Column(name="FECHA")
	//TODO poner tipo Date
	private Date fecha;
	
	@Column(name="FINALIZADO")
	private boolean finalizado;

	@OneToOne
	@JoinColumn(name="ID_PAREJA_GANADORA")
	private ParejaEntity parejaGanadora;

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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
}
