package negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import dao.PartidoDAO;
import dto.ChicoDTO;
import dto.ModalidadDTO;
import dto.ParejaDTO;
import dto.PartidoDTO;
import enums.EstadoPartido;
import enums.TipoModalidad;
import excepciones.GrupoException;
import excepciones.PartidoException;

/**
 * Soy la jugada de 2 o 3 chicos
 * El partido se gana cuando una de las dos parejas consigue dos chicos. Si hay
 * empate en los dos primeros chicos, el tercero es el definitivo 
 */
public class Partido {
	private Integer idPartido;
	private List<Chico> chicos;
	private int numeroChicoActual;
	private TipoModalidad modalidad;
	private List<Pareja> parejas;
	private Pareja parejaGanadora;
	private Date fecha;
	private EstadoPartido estado;
	
	public Partido(TipoModalidad modalidad, List<Pareja> parejas, Pareja parejaGanadora, Date fecha,EstadoPartido estado) {
		super();
		this.chicos = crearChicos();
		this.numeroChicoActual = 1;
		this.modalidad = modalidad;
		this.parejas = parejas;
		this.parejaGanadora = parejaGanadora;
		this.fecha = fecha;
		this.estado = estado;
	}
	
	/**
	 * Constructor usado para traer datos de la base, las parejas se insertaran con el set
	 * @param chicos
	 * @param modalidad
	 * @param parejaGanadora
	 */
	public Partido(TipoModalidad modalidad, Pareja parejaGanadora, Date fecha) {
		super();
		this.modalidad = modalidad;
		this.parejaGanadora = parejaGanadora;
		this.fecha = fecha;
	}
	
	public List<Chico> getChico() {
		return chicos;
	}
	public void setChico(List<Chico> chico) {
		this.chicos = chico;
	}
	public TipoModalidad getModalidad() {
		return modalidad;
	}
	public void setModalidad(TipoModalidad modalidad) {
		this.modalidad = modalidad;
	}
	public List<Pareja> getParejas() {
		return parejas;
	}
	public void setParejas(List<Pareja> parejas) {
		this.parejas = parejas;
	}
	public Pareja getParejaGanadora() {
		return parejaGanadora;
	}
	public void setParejaGanadora(Pareja parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}
	
	public EstadoPartido getEstado() {
		return estado;
	}
	
	public void setEstado(EstadoPartido estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(Integer idPartido) {
		this.idPartido = idPartido;
	}
	
	public Integer guardar(){
		idPartido = PartidoDAO.getInstancia().guardar(this);
		return idPartido;
	}
	
	public boolean actualizar(){
		return PartidoDAO.getInstancia().actualizar(this);
	}
	
	public PartidoDTO toDTOListar() throws GrupoException {
		List<ParejaDTO> parejasDTO = parejas.stream().map(t -> {
			try {
				return t.toDTO();
			} catch (GrupoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
		if(parejaGanadora != null) {
			return new PartidoDTO(new ModalidadDTO(modalidad, true), parejasDTO, parejaGanadora.toDTO());
		}else {
			return new PartidoDTO(new ModalidadDTO(modalidad, true), parejasDTO, null);
		}
		
	}
	
	public PartidoDTO toDTO() throws GrupoException{
		ModalidadDTO mod = null;
		List<ParejaDTO> parejasDTO = new ArrayList<ParejaDTO>();
		List<ChicoDTO> chicosDTO = new ArrayList<ChicoDTO>();
		if(modalidad.equals(TipoModalidad.Libre_individual)){
			mod = new ModalidadDTO(modalidad,true);
		}
		else{
			mod = new ModalidadDTO(modalidad,false);
		}
		if(parejas != null){
			for(Pareja p : parejas){
				parejasDTO.add(p.toDTO());
			}
		}
		if(chicos != null){
			for(Chico c : chicos){
				chicosDTO.add(c.toDTO());
			}
		}
		if(parejaGanadora != null){
			return new PartidoDTO(this.idPartido, chicosDTO,mod,parejasDTO,parejaGanadora.toDTO());
		}
		
		return new PartidoDTO(this.idPartido, chicosDTO,mod,parejasDTO,null);
	}

	public void save() {
		PartidoDAO.getInstancia().guardar(this);
	}
	
	private List<Chico> crearChicos() {
		List <Chico> chicos = new ArrayList<Chico>();
		List <Mano> manos = new ArrayList<Mano>();
		List <Baza> bazas = new ArrayList<Baza>();
		Baza b = new Baza(1,null,0,0,null);
		bazas.add(b);
		Mano m = new Mano(1,null,0,0,bazas);
		manos.add(m);
		Chico chico1 = new Chico(1, false, null, 0, 0);
		chico1.setManos(manos);
		Chico chico2 = new Chico(2, false, null, 0, 0);
		Chico chico3 = new Chico(3, false, null, 0, 0);
		
		chicos.add(chico1);
		chicos.add(chico2);
		chicos.add(chico3);
		
		return chicos;
	}
	
	public int getNumeroChicoActual() {
		return numeroChicoActual;
	}

	public void setNumeroChicoActual(int chicoActual) {
		this.numeroChicoActual = chicoActual;
	}

	public void crearNuevoChico() {
		Chico c = chicos.get(this.numeroChicoActual);
		numeroChicoActual = numeroChicoActual + 1;
		c.crearNuevaMano();
	}

	public boolean nuevaJugadaTantos(Turno turno) throws PartidoException {
		boolean siguienteTurno = Mano.analizarEnviteTantos(this.idPartido, turno);
		return siguienteTurno;
	}

	public boolean nuevaJugadaJuego(Turno turno) throws PartidoException {
		boolean siguienteTurno = Mano.analizarEnviteJuego(this.idPartido, turno);
		return siguienteTurno;
	}

	public void cargarMovimientoChico(Chico chicoActual) {
		if (chicoActual.isFinalizado()) {
			if (isFinalizaPartido(chicoActual)) {
				parejaGanadora = chicoActual.getParejaGanadora();
			}else {
				crearNuevoChico();
			}
		}
	}
	private boolean isFinalizaPartido(Chico chicoActual) {
		Pareja p1 = this.getParejas().get(0);
		Pareja p2 = this.getParejas().get(1);
		
		boolean finalizaPartido = false;
		if(getNumeroChicoActual()== 1){
			finalizaPartido = false;
		}
		else if(getNumeroChicoActual()== 2){
			if((this.getChico().get(0).getParejaGanadora().equals(p1) && chicoActual.getParejaGanadora().equals(p1)) || this.getChico().get(0).getParejaGanadora().equals(p2) && chicoActual.getParejaGanadora().equals(p2)){
				finalizaPartido = true;
			}
		}
		else if(getNumeroChicoActual()== 3){
			if( (this.getChico().get(0).getParejaGanadora().equals(p1) && chicoActual.getParejaGanadora().equals(p1))|| (this.getChico().get(1).getParejaGanadora().equals(p1) && chicoActual.getParejaGanadora().equals(p1)) ){
				finalizaPartido = true;
			}
			else if((this.getChico().get(0).getParejaGanadora().equals(p2) && chicoActual.getParejaGanadora().equals(p2))|| (this.getChico().get(1).getParejaGanadora().equals(p2) && chicoActual.getParejaGanadora().equals(p2)) ){
				finalizaPartido = true;
			}
		}
		return finalizaPartido;
	}

}
