package negocio;

import dao.JugadorDAO;
import dto.JugadorDTO;
import enums.Categoria;
import excepciones.JugadorException;

public class Jugador {
	//El id de jugador lo puse en esta clase porque no se puede guardar si no está acá,
	//preguntar al profesor si están bien los id que pusimos en la tabla
	//cuando el id deberia ser el apodo
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
	private String password;
	private Integer numJugador;

	public Jugador(String nombre, String apodo, String mail, Categoria categoria, int puntaje, int partidosJugados,
			int partidosGanados, boolean conectado, boolean jugando, String password) {
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
		this.password = password;
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

	public boolean guardar(){
		return JugadorDAO.getInstancia().guardar(this);
	}
	
	public static Jugador getJugador(String mail) throws JugadorException{
		Jugador j = JugadorDAO.getInstancia().buscarPorMail(mail);
		return j;
	}
	
	public JugadorDTO toDTO() {
		return new JugadorDTO(id, nombre, apodo, mail, categoria, puntaje, partidosJugados, partidosGanados, conectado, jugando, password);
	}
	
}
