package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.ChicoEntity;
import entities.ParejaEntity;
import entities.PartidoEntity;
import enums.TipoModalidad;
import excepciones.ParejaException;
import excepciones.PartidoException;
import hbt.HibernateUtil;
import negocio.Chico;
import negocio.Pareja;
import negocio.Partido;

public class PartidoDAO {
	private static PartidoDAO instancia;
	
	public static PartidoDAO getInstancia() {
		if(instancia == null){
			instancia = new PartidoDAO();
		}
		return instancia;
	}
	
	public List<Partido> buscarPartidosPorJugador(Integer idJugador, TipoModalidad mod, Date fechaInicial, Date fechaFin) throws ParejaException, PartidoException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		List<ParejaEntity> parejas = ParejaDAO.getInstancia().buscarParejasPorJugador(idJugador, s);
		List<Partido> partidos = new ArrayList<Partido>();
		try {
			for (ParejaEntity parejaEntity : parejas) {
				partidos.addAll(procesarDatos(parejaEntity, mod, fechaInicial, fechaFin));
			}			
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new PartidoException("Error al buscar los partidos del jugador");
		}finally {
			s.close();
		}
		return partidos;
	}
	
	private List<Partido> procesarDatos(ParejaEntity pe, TipoModalidad mod, Date fechaInicial, Date fechaFin) throws PartidoException {
		List<Partido> partidos = new ArrayList<Partido>();
		Partido p;
		List<PartidoEntity> partidosE = new ArrayList<PartidoEntity>();
		if(mod != null) {
			if(mod.equals(TipoModalidad.Cerrado)) {
				partidosE = getPartidosPorModalidad(pe,TipoModalidad.Cerrado);
			}else if(mod.equals(TipoModalidad.Libre)){
				partidosE = getPartidosPorModalidad(pe,TipoModalidad.Libre);
			}else {
				partidosE = getPartidosPorModalidad(pe,TipoModalidad.Libre_individual);
			}
		}else {
			partidosE = pe.getPartidos();
		}
		if(fechaInicial != null) {
			partidosE.removeIf(partido -> (!partido.getFecha().after(fechaInicial) || !partido.getFecha().before(fechaFin))
										&& (!partido.getFecha().equals(fechaInicial) && !partido.getFecha().equals(fechaFin)));

		}
		for (PartidoEntity partido : partidosE) {
			p = toNegocio(partido);
			p.setParejas(partido.getParejas().stream().map(ParejaDAO.getInstancia()::toNegocio).collect(Collectors.toList()));
			partidos.add(p);
		}
		return partidos;
	}
	

	@SuppressWarnings("unchecked")
	private List<PartidoEntity> getPartidosPorModalidad(ParejaEntity pe,TipoModalidad tipo) throws PartidoException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		List<PartidoEntity> partidoe = new ArrayList<PartidoEntity>();
		List<Integer> idPartidos = new ArrayList<Integer>();
		try {
				idPartidos = (List<Integer>) s.createSQLQuery("SELECT ID_PARTIDO FROM PARTIDOS WHERE ID_PARTIDO IN (SELECT partidos_ID_PARTIDO FROM PARTIDOS_PAREJAS WHERE parejas_ID_PAREJA = ?) AND MODALIDAD = ?").setInteger(0, pe.getIdPareja()).setString(1, tipo.toString()).list();
				for(int i = 0;i<idPartidos.size();i++){
					partidoe.add((PartidoEntity) s.createQuery("FROM PartidoEntity WHERE idPartido = ?").setInteger(0, idPartidos.get(i)).uniqueResult());
				}
				s.getTransaction().commit();
				s.close();
			} catch (HibernateException e) {
				e.printStackTrace();
				throw new PartidoException("Error al buscar los partidos del jugador");
			}
		return partidoe;
	}

	public Partido toNegocio(PartidoEntity pe) {
		Partido p = null;
		List<Chico> chicos = new ArrayList<Chico>();
		List<Pareja> parejas = new ArrayList<Pareja>();
		if(pe.getParejaGanadora() != null) {
			p = new Partido(pe.getModalidad(), ParejaDAO.getInstancia().toNegocio(pe.getParejaGanadora()), pe.getFecha());
		}else {
			p = new Partido(pe.getModalidad(), null, pe.getFecha());
		}
		for(int i = 0;i<pe.getChicos().size();i++){
			chicos.add(ChicoDAO.getInstancia().toNegocio(pe.getChicos().get(i)));
		}
		p.setChico(chicos);
		for(int i = 0;i<pe.getParejas().size();i++){
			parejas.add(ParejaDAO.getInstancia().toNegocio(pe.getParejas().get(i)));
		}
		p.setParejas(parejas);
		p.setIdPartido(pe.getIdPartido());
		return p;
	}

	public Integer guardar(Partido partido) {
		PartidoEntity pEntity = toEntity(partido);
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		s.save(pEntity);
		s.getTransaction().commit();
		s.close();

		return pEntity.getIdPartido();
	}
	
	public boolean actualizar(Partido partido) {
		PartidoEntity pEntity = toEntity(partido);
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		s.saveOrUpdate(pEntity);
		s.getTransaction().commit();
		s.close();

		return true;
	}

	private PartidoEntity toEntity(Partido partido) {
		PartidoEntity pe = new PartidoEntity();
		
		pe.setIdPartido(partido.getIdPartido());
		pe.setModalidad(partido.getModalidad());
		pe.setFecha(partido.getFecha());
		pe.setEstado(partido.getEstado());
		
		if (partido.getParejaGanadora() != null) {
			ParejaEntity parejaGanadora = ParejaDAO.getInstancia().toEntity(partido.getParejaGanadora());
			pe.setParejaGanadora((parejaGanadora));
		}
		
		List<ParejaEntity> parejas = new ArrayList<ParejaEntity>();
		if(partido.getParejas() != null) {
			for (Pareja p: partido.getParejas()) {
				ParejaEntity pEntity = ParejaDAO.getInstancia().toEntity(p);
				pEntity.getPartidos().add(pe);
				parejas.add(pEntity);
			}
		}
		pe.setParejas(parejas);
		if (partido.getParejaGanadora() == null) {
//			pe.setParejaGanadora(parejas.get(0));
		}
		pe.setFecha(partido.getFecha());
		
		List<ChicoEntity> chicos = new ArrayList<ChicoEntity>();
		if(partido.getChico() != null) {
			for (Chico c: partido.getChico()) {
				ChicoEntity cEntity = ChicoDAO.getInstancia().toEntity(c);
				cEntity.setIdPartido(pe);
				chicos.add(cEntity);
			}
		}
		pe.setChicos(chicos);
		return pe;
	}

	public Partido buscarPartidoPorID(int id) throws PartidoException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		Partido p = null;
		PartidoEntity partidoe;
		try {
				partidoe = (PartidoEntity) s.createQuery("from PartidoEntity pe where pe.idPartido = ?").setInteger(0, id).uniqueResult();
				if(partidoe != null){
					p = toNegocio(partidoe);
				}
				else{
					return p;
				}
				s.getTransaction().commit();
			} catch (HibernateException e) {
				e.printStackTrace();
				throw new PartidoException("Error al buscar los partidos del jugador");
			}finally {
				s.close();
			}
		return p;
	}
}
