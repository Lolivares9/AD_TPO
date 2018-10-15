package negocio;

import dao.ManoDAO;
import dto.ManoDTO;

/**
 * Soy la jugada de 2 o 3 bazas 
 * (los 4 jugadores jugaron sus 3 cartas)
 */
public class Mano {
	private Integer idMano;
	private Chico chico;
	private int numeroMano;
	private Pareja parejaGanadora;
	
	public Mano(int numeroMano, Pareja parejaGanadora) {
		super();
		this.numeroMano = numeroMano;
		this.parejaGanadora = parejaGanadora;
	}
	
	public Mano(Integer idMano, int numeroMano, Pareja parejaGanadora) {
		super();
		this.idMano= idMano;
		this.numeroMano = numeroMano;
		this.parejaGanadora = parejaGanadora;
	}
	
	public int getNumeroMano() {
		return numeroMano;
	}
	public void setNumeroMano(int numeroMano) {
		this.numeroMano = numeroMano;
	}
	public Pareja getParejaGanadora() {
		return parejaGanadora;
	}
	public void setParejaGanadora(Pareja parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}
	
	public boolean guardar(){
		return ManoDAO.getInstancia().guardar(this);
	}

	public Integer getIdMano() {
		return idMano;
	}

	public void setIdMano(Integer idMano) {
		this.idMano = idMano;
	}

	public Chico getChico() {
		return chico;
	}

	public void setChico(Chico chico) {
		this.chico = chico;
	}
	
	/**
	 * Constructor para crear un DTO sin todas sus variables completas
	 * @return
	 */
	public ManoDTO toDTO() {
		return new ManoDTO(numeroMano, parejaGanadora.toDTO());
	}
}
