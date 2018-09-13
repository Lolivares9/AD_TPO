package dto;

public class RankingGrupalDTO {

	private GrupoDTO grupo;
	private JugadorDTO jugador;
	private int partidosJugadorDTO;
	private int partidosGanados;
	private int puntaje;
	
	public RankingGrupalDTO(GrupoDTO grupo, JugadorDTO jugador, int partidosJugadorDTO, int partidosGanados, int puntaje) {
		super();
		this.grupo = grupo;
		this.jugador = jugador;
		this.partidosJugadorDTO = partidosJugadorDTO;
		this.partidosGanados = partidosGanados;
		this.puntaje = puntaje;
	}
	public GrupoDTO getGrupoDTO() {
		return grupo;
	}
	public void setGrupoDTO(GrupoDTO grupo) {
		this.grupo = grupo;
	}
	public JugadorDTO getJugadorDTO() {
		return jugador;
	}
	public void setJugadorDTO(JugadorDTO jugador) {
		this.jugador = jugador;
	}
	public int getPartidosJugadorDTO() {
		return partidosJugadorDTO;
	}
	public void setPartidosJugadorDTO(int partidosJugadorDTO) {
		this.partidosJugadorDTO = partidosJugadorDTO;
	}
	public int getPartidosGanados() {
		return partidosGanados;
	}
	public void setPartidosGanados(int partidosGanados) {
		this.partidosGanados = partidosGanados;
	}
	public int getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	

}
