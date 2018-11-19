package dto;

import java.io.Serializable;
import java.util.List;

import enums.Categoria;

public class JugadorDTO implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3557868331428383755L;
	
	private Integer id;
	private String nombre;
	private String apodo;
	private String mail;
	private Categoria categoria;
	private int puntaje;
	private int partidosJugados;
	private int partidosGanados;
	private boolean conectado;
	private boolean jugando;
	private boolean buscandoLibreIndividual;
	private String password;
	private Integer numJugador;
	private List<GrupoDTO> grupos;
	
	public JugadorDTO() {
		super();
	}
	
	public JugadorDTO(String nombre, String apodo, String mail, Categoria categoria, int puntaje, int partidosJugados,
			int partidosGanados, boolean conectado, boolean jugando, boolean buscandoLibreIndividual,String password, List<GrupoDTO> grupos) {
		super();
		this.nombre = nombre;
		this.apodo = apodo;
		this.mail = mail;
		this.categoria = categoria;
		this.puntaje = puntaje;
		this.partidosJugados = partidosJugados;
		this.partidosGanados = partidosGanados;
		this.conectado = conectado;
		this.jugando = jugando;
		this.buscandoLibreIndividual = buscandoLibreIndividual;
		this.password = password;
		this.grupos = grupos;
	}

	/**
	 * Constructor para DTO completo
	 * 
	 * @param id
	 * @param nombre
	 * @param apodo
	 * @param mail
	 * @param categoria
	 * @param puntaje
	 * @param partidosJugados
	 * @param partidosGanados
	 * @param conectado
	 * @param jugando
	 * @param password
	 */
	public JugadorDTO(Integer id, String nombre, String apodo, String mail, Categoria categoria, int puntaje, int partidosJugados,
			int partidosGanados, boolean conectado, boolean jugando, String password,List<GrupoDTO> grupos , Integer numJugador) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apodo = apodo;
		this.mail = mail;
		this.categoria = categoria;
		this.puntaje = puntaje;
		this.partidosJugados = partidosJugados;
		this.partidosGanados = partidosGanados;
		this.conectado = conectado;
		this.jugando = jugando;
		this.password = password;
		this.grupos = grupos;
		this.numJugador = numJugador;
	}
	
	/**
	 * Constructor con valores por defecto para cuando se da de alta un jugador
	 * @param nombre
	 * @param apodo
	 * @param mail
	 * @param password
	 */
	public JugadorDTO(String nombre, String apodo, String mail, String password,List<GrupoDTO> grupos) {
		super();
		this.nombre = nombre;
		this.apodo = apodo;
		this.mail = mail;
		this.categoria = Categoria.Novato;
		this.puntaje = 0;
		this.partidosJugados = 0;
		this.partidosGanados = 0;
		this.conectado = false;
		this.jugando = false;
		this.password = password;
		this.grupos = grupos;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumJugador() {
		return numJugador;
	}

	public void setNumJugador(Integer numJugador) {
		this.numJugador = numJugador;
	}

	public boolean isBuscandoLibreIndividual() {
		return buscandoLibreIndividual;
	}

	public void setBuscandoLibreIndividual(boolean buscandoLibreIndividual) {
		this.buscandoLibreIndividual = buscandoLibreIndividual;
	}

	public List<GrupoDTO> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<GrupoDTO> grupos) {
		this.grupos = grupos;
	}	
	
}
