package negocio;

public class Turno {
	private Baza baza;
	private Jugador jugador;
	private String envite;
	private Carta carta;
	
	public Turno(Baza baza, Jugador jugador, String envite, Carta carta) {
		super();
		this.baza = baza;
		this.jugador = jugador;
		this.envite = envite;
		this.carta = carta;
	}
	
	public Baza getBaza() {
		return baza;
	}
	public void setBaza(Baza baza) {
		this.baza = baza;
	}
	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	public String getEnvite() {
		return envite;
	}
	public void setEnvite(String envite) {
		this.envite = envite;
	}
	public Carta getCarta() {
		return carta;
	}
	public void setCarta(Carta carta) {
		this.carta = carta;
	}
	
	
	
}
