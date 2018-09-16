package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TURNOS")
public class TurnoEntity {

	@Id
	@Column (name="ID_TURNO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTurno;
	
	@ManyToOne
	@JoinColumn(name="ID_BAZA")
	private BazaEntity baza;
	
	@Column(name="ID_JUGADOR")
	private JugadorEntity jugador;
	
	//TODO hacer enum para envites?
	@Column(name="ENVITE")
	private String envite;
	
	@OneToOne
	@JoinColumn(name="ID_CARTA")
	private CartaEntity carta;

	public int getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}

	public BazaEntity getBaza() {
		return baza;
	}

	public void setBaza(BazaEntity baza) {
		this.baza = baza;
	}

	public JugadorEntity getJugador() {
		return jugador;
	}

	public void setJugador(JugadorEntity jugador) {
		this.jugador = jugador;
	}

	public String getEnvite() {
		return envite;
	}

	public void setEnvite(String envite) {
		this.envite = envite;
	}

	public CartaEntity getCarta() {
		return carta;
	}

	public void setCarta(CartaEntity carta) {
		this.carta = carta;
	}
}
