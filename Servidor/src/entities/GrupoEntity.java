package entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="GRUPOS")
public class GrupoEntity {

	@Id
	@Column (name="ID_GRUPO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idGrupo;
	
	@Column(name="NOMBRE")
	private String nombre;
	
	@OneToOne
	@JoinColumn(name="ID_JUGADOR_ADM")
	private JugadorEntity jugadorAdmin;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "GRUPOS_JUGADORES", joinColumns = @JoinColumn(name="grupos_ID_GRUPO"),inverseJoinColumns=@JoinColumn(name="jugadores_ID_JUGADOR"))
	
	private List<JugadorEntity> jugadores = new ArrayList<JugadorEntity>();
	

	public List<JugadorEntity> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<JugadorEntity> jugadores) {
		this.jugadores = jugadores;
	}

	public GrupoEntity() {
		super();
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public JugadorEntity getJugadorAdmin() {
		return jugadorAdmin;
	}

	public void setJugadorAdmin(JugadorEntity jugadorAdmin) {
		this.jugadorAdmin = jugadorAdmin;
	}
	
	public void agregarJugador(JugadorEntity jugador) {
		this.jugadores.add(jugador);
	}
	
	public void eliminarJugador(JugadorEntity jugador) {
		this.jugadores.remove(jugador);
	}
	
	public void añadirJugador(JugadorEntity jug){
		this.jugadores.add(jug);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idGrupo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrupoEntity other = (GrupoEntity) obj;
		if (idGrupo != other.idGrupo)
			return false;
		return true;
	}
	
}
