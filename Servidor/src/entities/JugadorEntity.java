package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import enums.Categoria;

@Entity
@Table(name = "JUGADORES")
public class JugadorEntity {
	
	@Id
	@Column (name="ID_JUGADOR")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idJugador;
	
	@Column(name="NOMBRE")
	private String nombre;
	
	@Column(name="APODO")
	private String apodo;
	
	@Column(name="MAIL")
	private String mail;
	
	//TODO PONER EN LA TABLA
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="CATEGORIA")
	@Enumerated(EnumType.STRING)
	private Categoria categoria;
	
	@Column(name="PUNTAJE")
	private int puntaje;
	
	@Column(name="PARTIDOS_JUGADOS")
	private int partidosJugados;
	
	@Column(name="PARTIDOS_GANADOS")
	private int partidosGanados;
	
	@Column(name="CONECTADO")
	private boolean conectado;
	
	@Column(name="JUGANDO")
	private boolean jugando;
	
	//TODO �?�?�? cambiar ultimos dos a:
	//@Column(name="ESTADO")
	//enum con estados posibles (jugando, conectado...)
	
	public JugadorEntity() {
		super();
	}
	
	public JugadorEntity(String nombre, String apodo, String mail, String password,
			Categoria categoria, int puntaje, int partidosJugados, int partidosGanados, boolean conectado,
			boolean jugando) {
		super();
		this.nombre = nombre;
		this.apodo = apodo;
		this.mail = mail;
		this.password = password;
		this.categoria = categoria;
		this.puntaje = puntaje;
		this.partidosJugados = partidosJugados;
		this.partidosGanados = partidosGanados;
		this.conectado = conectado;
		this.jugando = jugando;
	}

	public Integer getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(Integer idJugador) {
		this.idJugador = idJugador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public int getPartidosJugados() {
		return partidosJugados;
	}

	public void setPartidosJugados(int partidosJugados) {
		this.partidosJugados = partidosJugados;
	}

	public int getPartidosGanados() {
		return partidosGanados;
	}

	public void setPartidosGanados(int partidosGanados) {
		this.partidosGanados = partidosGanados;
	}

	public boolean isConectado() {
		return conectado;
	}

	public void setConectado(boolean conectado) {
		this.conectado = conectado;
	}

	public boolean isJugando() {
		return jugando;
	}

	public void setJugando(boolean jugando) {
		this.jugando = jugando;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idJugador;
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
		JugadorEntity other = (JugadorEntity) obj;
		if (idJugador != other.idJugador)
			return false;
		return true;
	}
	
}
