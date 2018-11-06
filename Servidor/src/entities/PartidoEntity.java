package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import enums.EstadoPartido;
import enums.TipoModalidad;

@Entity
@Table(name = "PARTIDOS")
public class PartidoEntity {

	@Id
	@Column (name="ID_PARTIDO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPartido;
	
	@Column(name="MODALIDAD")
	@Enumerated(EnumType.STRING)
	private TipoModalidad modalidad;
	
	@ManyToOne
	@JoinColumn(name="ID_PAREJA_GANADORA")
	private ParejaEntity parejaGanadora;
	
	@Column(name="FECHA")
	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Column(name="ESTADO")
	@Enumerated(EnumType.STRING)
	private EstadoPartido estado;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "PARTIDOS_PAREJAS", joinColumns = @JoinColumn(name="partidos_ID_PARTIDO"),inverseJoinColumns=@JoinColumn(name="parejas_ID_PAREJA"))
	List<ParejaEntity> parejas = new ArrayList<ParejaEntity>();
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="ID_PARTIDO")
	private List<ChicoEntity> chicos;
	
	@Column(name="CHICO_ACTUAL")
	private int numeroChicoActual;

	public int getNumeroChicoActual() {
		return numeroChicoActual;
	}

	public void setNumeroChicoActual(int numeroChicoActual) {
		this.numeroChicoActual = numeroChicoActual;
	}

	public List<ChicoEntity> getChicos() {
		return chicos;
	}

	public void setChicos(List<ChicoEntity> chicos) {
		this.chicos = chicos;
	}

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

	public TipoModalidad getModalidad() {
		return modalidad;
	}

	public void setModalidad(TipoModalidad modalidad) {
		this.modalidad = modalidad;
	}

	public EstadoPartido getEstado() {
		return estado;
	}

	public void setEstado(EstadoPartido estado) {
		this.estado = estado;
	}

	public ParejaEntity getParejaGanadora() {
		return parejaGanadora;
	}

	public void setParejaGanadora(ParejaEntity parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}
}
