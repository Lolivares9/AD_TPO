package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="GRUPOS")
public class GrupoEntity {

	@Id
	@Column (name="ID_GRUPO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idGrupo;
	
	@Column(name="NOMBRE")
	private String nombre;
	
	@OneToOne
	@JoinColumn(name="ID_JUGADOR_ADM")
	private JugadorEntity jugadorAdmin;

	public int getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(int idGrupo) {
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
