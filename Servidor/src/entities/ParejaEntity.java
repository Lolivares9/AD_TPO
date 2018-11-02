package entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "PAREJAS")
public class ParejaEntity {

	@Id
	@Column (name="ID_PAREJA")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPareja;
	
	@ManyToOne
	@JoinColumn(name="ID_JUGADOR1")
	private JugadorEntity jugador1;
	
	@ManyToOne
	@JoinColumn(name="ID_JUGADOR2")
	private JugadorEntity jugador2;
	
	@OneToMany(cascade = CascadeType.ALL)
	@Where(clause = "MODALIDAD = 'Libre'")
	private List<PartidoEntity> partidosLibre = new ArrayList<PartidoEntity>();
	
	@OneToMany(mappedBy="parejas")
	@Where(clause = "MODALIDAD = 'Libre_individual'")
	private List<PartidoEntity> partidosLibreIndiv = new ArrayList<PartidoEntity>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@Where(clause = "MODALIDAD = 'Cerrado'")
	private List<PartidoEntity> partidosCerrado = new ArrayList<PartidoEntity>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "PARTIDOS_PAREJAS", joinColumns = @JoinColumn(name="_ID_PAREJA"),inverseJoinColumns=@JoinColumn(name="_ID_PARTIDO"))
	private List<PartidoEntity> partidos = new ArrayList<PartidoEntity>();

	public ParejaEntity() {
		super();
	}

	public Integer getIdPareja() {
		return idPareja;
	}

	public void setIdPareja(Integer idPareja) {
		this.idPareja = idPareja;
	}

	public JugadorEntity getJugador1() {
		return jugador1;
	}

	public void setJugador1(JugadorEntity jugador1) {
		this.jugador1 = jugador1;
	}

	public JugadorEntity getJugador2() {
		return jugador2;
	}

	public void setJugador2(JugadorEntity jugador2) {
		this.jugador2 = jugador2;
	}

	public List<PartidoEntity> getPartidosLibre() {
		return partidosLibre;
	}

	public void setPartidosLibre(List<PartidoEntity> partidosLibre) {
		this.partidosLibre = partidosLibre;
	}

	public List<PartidoEntity> getPartidosLibreIndiv() {
		return partidosLibreIndiv;
	}

	public void setPartidosLibreIndiv(List<PartidoEntity> partidosLibreIndiv) {
		this.partidosLibreIndiv = partidosLibreIndiv;
	}

	public List<PartidoEntity> getPartidosCerrado() {
		return partidosCerrado;
	}

	public void setPartidosCerrado(List<PartidoEntity> partidosCerrado) {
		this.partidosCerrado = partidosCerrado;
	}

	public List<PartidoEntity> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<PartidoEntity> partidos) {
		this.partidos = partidos;
	}
}
