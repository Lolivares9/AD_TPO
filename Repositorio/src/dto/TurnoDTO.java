package dto;

public class TurnoDTO {

	private BazaDTO baza;
	private JugadorDTO jugador;
	private String envite;
	private CartaDTO carta;
	
	public TurnoDTO(BazaDTO baza, JugadorDTO jugador, String envite, CartaDTO carta) {
		super();
		this.baza = baza;
		this.jugador = jugador;
		this.envite = envite;
		this.carta = carta;
	}
	
	public BazaDTO getBazaDTO() {
		return baza;
	}
	public void setBazaDTO(BazaDTO baza) {
		this.baza = baza;
	}
	public JugadorDTO getJugadorDTO() {
		return jugador;
	}
	public void setJugadorDTO(JugadorDTO jugador) {
		this.jugador = jugador;
	}
	public String getEnvite() {
		return envite;
	}
	public void setEnvite(String envite) {
		this.envite = envite;
	}
	public CartaDTO getCartaDTO() {
		return carta;
	}
	public void setCartaDTO(CartaDTO carta) {
		this.carta = carta;
	}
	
	
	

}
