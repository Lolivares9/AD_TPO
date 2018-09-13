package dto;

import java.util.List;

public class PartidoDTO {

	private ChicoDTO chico;
	private ModalidadDTO modalidad;
	private List<ParejaDTO> parejas;
	private ParejaDTO parejaGanadora;
	
	public PartidoDTO(ChicoDTO chico, ModalidadDTO modalidad, List<ParejaDTO> parejas, ParejaDTO parejaGanadora) {
		super();
		this.chico = chico;
		this.modalidad = modalidad;
		this.parejas = parejas;
		this.parejaGanadora = parejaGanadora;
	}
	
	public ChicoDTO getChicoDTO() {
		return chico;
	}
	public void setChicoDTO(ChicoDTO chico) {
		this.chico = chico;
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
	
	
	

}
