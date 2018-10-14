package entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	
	@Column(name="PUNTAJE")
	private int puntaje;
	
	@ManyToMany(mappedBy="parejas")
	private List<PartidoEntity> partidos = new ArrayList<PartidoEntity>();
	
	public List<PartidoEntity> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<PartidoEntity> partidos) {
		this.partidos = partidos;
	}

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

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}	
}
