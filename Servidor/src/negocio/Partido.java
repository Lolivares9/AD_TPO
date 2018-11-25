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
import enums.Categoria;
import enums.EstadoPartido;
import enums.TipoModalidad;
import excepciones.CartaException;
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
	private Mazo mazo;
	
	public Partido(TipoModalidad modalidad, List<Pareja> parejas, Pareja parejaGanadora, Date fecha,EstadoPartido estado) {
		super();
		this.chicos = new ArrayList<Chico>();
		this.numeroChicoActual = 0;
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
	
	
	
	public List<Chico> getChicos() {
		return chicos;
	}

	public void setChicos(List<Chico> chicos) {
		this.chicos = chicos;
	}

	public Mazo getMazo() {
		return mazo;
	}

	public void setMazo(Mazo mazo) {
		this.mazo = mazo;
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
	
//	private List<Chico> crearChicos() {
//		List <Chico> chicos = new ArrayList<Chico>();
//		List <Mano> manos = new ArrayList<Mano>();
//		List <Baza> bazas = new ArrayList<Baza>();
//		Baza b = new Baza(1,null,0,0,null);
//		bazas.add(b);
//		Mano m = new Mano(1,null,0,0,bazas);
//		manos.add(m);
//		Chico chico1 = new Chico(1, false, null, 0, 0);
//		chico1.setManos(manos);
//		Chico chico2 = new Chico(2, false, null, 0, 0);
//		Chico chico3 = new Chico(3, false, null, 0, 0);
//		
//		chicos.add(chico1);
//		chicos.add(chico2);
//		chicos.add(chico3);
//		
//		return chicos;
//	}
	
	public int getNumeroChicoActual() {
		return numeroChicoActual;
	}

	public void setNumeroChicoActual(int chicoActual) {
		this.numeroChicoActual = chicoActual;
	}

	public void crearNuevoChico() {
		numeroChicoActual = numeroChicoActual + 1;
		Chico c = new Chico(numeroChicoActual, false, null, 0, 0);
		c.crearNuevaMano();
		chicos.add(c);
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
				estado = EstadoPartido.Finalizado;
				actualizarDatosJugadores();
				setearPuntajesPorPartido();
			}
		}
	}
	
	private void actualizarDatosJugadores() {
		//Sumamos partidos jugados a los jugadores
		Jugador j= null;
		for (Pareja p: parejas) {
			//jugador1
			j = p.getJugador1();
			j.sumarPartidoJugado(1);
			
			if(this.getParejaGanadora().getIdPareja().equals( p.getIdPareja())) {
				j.sumarPartidoGanado(1);
			}
			j.actualizarEstadoJugador(false, false);
			
			//jugador2
			j = p.getJugador2();
			j.sumarPartidoJugado(1);
			
			if(this.getParejaGanadora().getIdPareja().equals( p.getIdPareja())) {
				j.sumarPartidoGanado(1);
			}
			j.actualizarEstadoJugador(false, false);
		}
		
	}

	private void setearPuntajesPorPartido() {
		int puntaje = 0;
		Jugador jug1Pareja1 = parejas.get(0).getJugador1();
		Jugador jug1Pareja2 = parejas.get(1).getJugador1();
		Jugador jug2Pareja1 = parejas.get(0).getJugador2();
		Jugador jug2Pareja2 = parejas.get(1).getJugador2();
		float promedioJug1Pareja1 = jug1Pareja1.getPuntaje()/jug1Pareja1.getPartidosJugados();
		float promedioJug2Pareja1 = jug1Pareja2.getPuntaje()/jug1Pareja2.getPartidosJugados();;
		float promedioJug1Pareja2 = jug2Pareja1.getPuntaje()/jug2Pareja1.getPartidosJugados();;
		float promedioJug2Pareja2 = jug2Pareja2.getPuntaje()/jug2Pareja2.getPartidosJugados();;
		if(this.modalidad.equals(TipoModalidad.Libre) || this.modalidad.equals(TipoModalidad.Libre_individual)){
			puntaje = 10;
			setearPuntajePorParametros(jug1Pareja1,promedioJug1Pareja1,puntaje);
			setearPuntajePorParametros(jug1Pareja2,promedioJug2Pareja1,puntaje);
			setearPuntajePorParametros(jug2Pareja1,promedioJug1Pareja2,puntaje);
			setearPuntajePorParametros(jug2Pareja2,promedioJug2Pareja2,puntaje);
		}
		else{
			puntaje = 5;
			setearPuntajePorParametros(jug1Pareja1,promedioJug1Pareja1,puntaje);
			setearPuntajePorParametros(jug1Pareja2,promedioJug2Pareja1,puntaje);
			setearPuntajePorParametros(jug2Pareja1,promedioJug1Pareja2,puntaje);
			setearPuntajePorParametros(jug2Pareja2,promedioJug2Pareja2,puntaje);
		}
	}

	private void setearPuntajePorParametros(Jugador jugador,float promedio,int puntaje) {
		if(jugador.getPartidosJugados() > 100  && jugador.getPartidosJugados() < 500 && jugador.getPuntaje() > 500 && jugador.getPuntaje() < 3000 && promedio >= 5 && promedio < 6){
			jugador.setCategoria(Categoria.Calificado);
			jugador.setPuntaje(jugador.getPuntaje() + puntaje);
		}
		else if(jugador.getPartidosJugados() > 500  && jugador.getPartidosJugados() < 1000 && jugador.getPuntaje() > 3000 && jugador.getPuntaje() < 8000 && promedio >= 6 && promedio < 8){
			jugador.setCategoria(Categoria.Experto);
			jugador.setPuntaje(jugador.getPuntaje() + puntaje);
		}
		else if(jugador.getPartidosJugados() > 1000 && jugador.getPuntaje() > 8000 && promedio >= 8){
			jugador.setCategoria(Categoria.Master);
			jugador.setPuntaje(jugador.getPuntaje() + puntaje);
		}
	}

	private boolean isFinalizaPartido(Chico chicoActual) {
		Pareja p1 = this.getParejas().get(0);
		Pareja p2 = this.getParejas().get(1);
		int idp1 = p1.getIdPareja() ;
		int idp2 = p2.getIdPareja() ;;
		boolean finalizaPartido = false;
		if(numeroChicoActual== 1){
			finalizaPartido = false;
		}
		else if(numeroChicoActual== 2){
			if((this.getChico().get(0).getParejaGanadora().getIdPareja().equals(idp1) && chicoActual.getParejaGanadora().getIdPareja().equals(idp1)) || this.getChico().get(0).getParejaGanadora().getIdPareja().equals(idp2) && chicoActual.getParejaGanadora().equals(idp2)){
				finalizaPartido = true;
			}
		}
		else if(numeroChicoActual== 3){
			if( (this.getChico().get(0).getParejaGanadora().getIdPareja().equals(idp1) && chicoActual.getParejaGanadora().getIdPareja().equals(idp1))|| (this.getChico().get(1).getParejaGanadora().getIdPareja().equals(idp1) && chicoActual.getParejaGanadora().getIdPareja().equals(idp1)) ){
				finalizaPartido = true;
			}
			else if((this.getChico().get(0).getParejaGanadora().getIdPareja().equals(idp2) && chicoActual.getParejaGanadora().getIdPareja().equals(idp2))|| (this.getChico().get(1).getParejaGanadora().getIdPareja().equals(idp2) && chicoActual.getParejaGanadora().getIdPareja().equals(idp2)) ){
				finalizaPartido = true;
			}
		}
		return finalizaPartido;
	}

	public void repartirCartas() throws CartaException {
		this.mazo = new Mazo();
		this.mazo.repartiCartas(this);
	}

}
