package negocio;

import dao.JugadorDAO;

public class JugadorBO {
	private String apodo;
	private String mail;
	private String password;
	
	public JugadorBO(String apodo, String mail, String password) {
		super();
		this.apodo = apodo;
		this.mail = mail;
		this.password = password;
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
	
	public boolean guardar(){
		return JugadorDAO.getInstancia().guardarJugador(this);
	}
	
}
