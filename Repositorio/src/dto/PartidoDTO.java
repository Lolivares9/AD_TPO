package dto;

import java.io.Serializable;
import java.util.List;

public class PartidoDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1807299335449993623L;
	
	private Integer idPartido;
	private List<ChicoDTO> chicos;
	private ModalidadDTO modalidad;
	private List<ParejaDTO> parejas;
	private ParejaDTO parejaGanadora;
	private Integer idBazaInicial;
	
	public PartidoDTO() {
		super();
	}
	
	public PartidoDTO(Integer idPartido, List <ChicoDTO> chico, ModalidadDTO modalidad, List<ParejaDTO> parejas, ParejaDTO parejaGanadora) {
		super();
		this.idPartido = idPartido;
		this.chicos = chico;
		this.modalidad = modalidad;
		this.parejas = parejas;
		this.parejaGanadora = parejaGanadora;
	}
	
	/**
	 * Constructor para listar los partidos jugados
	 * 
	 * @param modalidad
	 * @param parejas
	 * @param parejaGanadora
	 */
	public PartidoDTO(ModalidadDTO modalidad, List<ParejaDTO> parejas, ParejaDTO parejaGanadora) {
		super();
		this.modalidad = modalidad;
		this.parejas = parejas;
		this.parejaGanadora = parejaGanadora;
	}
	
	public List<ChicoDTO> getChicoDTO() {
		return chicos;
	}
	public void setChicoDTO(List<ChicoDTO> chicos) {
		this.chicos = chicos;
	}
	public ModalidadDTO getModalidadDTO() {
		return modalidad;
	}
	public void setModalidadDTO(ModalidadDTO modalidad) {
		this.modalidad = modalidad;
	}
	public List<ParejaDTO> getParejaDTOs() {
		return parejas;
	}
	public void setParejaDTOs(List<ParejaDTO> parejas) {
		this.parejas = parejas;
	}
	public ParejaDTO getParejaDTOGanadora() {
		return parejaGanadora;
	}
	public void setParejaDTOGanadora(ParejaDTO parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}

	public Integer getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(Integer idPartido) {
		this.idPartido = idPartido;
	}

	public Integer getIdBazaInicial() {
		return idBazaInicial;
	}

	public void setIdBazaInicial(Integer idBazaInicial) {
		this.idBazaInicial = idBazaInicial;
	}
}
