package negocio;

import dao.CartaDAO;
import dao.RankingGrupalDAO;

public class RankingGrupal {
	private Grupo grupo;
	private Jugador jugador;
	private int partidosJugador;
	private int partidosGanados;
	private int puntaje;
	
	public RankingGrupal(Grupo grupo, Jugador jugador, int partidosJugador, int partidosGanados, int puntaje) {
		super();
		this.grupo = grupo;
		this.jugador = jugador;
		this.partidosJugador = partidosJugador;
		this.partidosGanados = partidosGanados;
		this.puntaje = puntaje;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	public int getPartidosJugador() {
		return partidosJugador;
	}
	public void setPartidosJugador(int partidosJugador) {
		this.partidosJugador = partidosJugador;
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
	
	public boolean guardar(){
		return RankingGrupalDAO.getInstancia().guardar(this);
	}
}
