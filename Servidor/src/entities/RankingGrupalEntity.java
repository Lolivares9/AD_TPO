package entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="RANKING_GRUPAL")
public class RankingGrupalEntity {

	@EmbeddedId
	private RankingGrupalPK id;
	
	@Column(name="PARTIDOS_JUGADOS")
	private int partidosJugados;
	
	@Column(name="PARTIDOS_GANADOS")
	private int partidosGanados;
	
	@Column(name="PUNTAJE")
	private int puntaje;
	
	public RankingGrupalEntity() {
		super();
	}

	public RankingGrupalPK getId() {
		return id;
	}

	public void setId(RankingGrupalPK id) {
		this.id = id;
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

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	
}
