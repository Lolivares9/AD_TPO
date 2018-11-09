package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.CascadeType;

import enums.Envite;

@Entity
@Table(name = "TURNOS")
public class TurnoEntity {

	@Id
	@Column (name="ID_TURNO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTurno;
	
	@ManyToOne
	@JoinColumn(name = "ID_BAZA")
	private BazaEntity idBaza;
	
	@ManyToOne
	@JoinColumn(name="ID_JUGADOR")
	private JugadorEntity jugador;
	
	@Column(name="ENVITE")
	@Enumerated(EnumType.STRING)
	private Envite envite;
	
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CARTA")
	private CartaEntity carta;
	
	public TurnoEntity() {
		super();
	}

	public Integer getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(Integer idTurno) {
		this.idTurno = idTurno;
	}

	public BazaEntity getBaza() {
		return idBaza;
	}

	public void setBaza(BazaEntity baza) {
		this.idBaza = baza;
	}

	public JugadorEntity getJugador() {
		return jugador;
	}

	public void setJugador(JugadorEntity jugador) {
		this.jugador = jugador;
	}

	public Envite getEnvite() {
		return envite;
	}

	public void setEnvite(Envite envite) {
		this.envite = envite;
	}

	public CartaEntity getCarta() {
		return carta;
	}

	public void setCarta(CartaEntity carta) {
		this.carta = carta;
	}
}
