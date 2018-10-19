package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.ParejaEntity;
import entities.PartidoEntity;
import enums.TipoModalidad;
import excepciones.ParejaException;
import excepciones.PartidoException;
import hbt.HibernateUtil;
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
	
	private List<Partido> procesarDatos(ParejaEntity pe, TipoModalidad mod, Date fechaInicial, Date fechaFin) {
		List<Partido> partidos = new ArrayList<Partido>();
		Partido p;
		List<PartidoEntity> partidosE = new ArrayList<PartidoEntity>();
		if(mod != null) {
			if(mod.equals(TipoModalidad.Cerrado)) {
				partidosE = pe.getPartidosCerrado();
			}else if(mod.equals(TipoModalidad.Libre)){
				partidosE = pe.getPartidosLibre();
			}else {
				partidosE = pe.getPartidosLibreIndiv();
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
	
	public Partido toNegocio(PartidoEntity pe) {
		Partido p = null;
		p = new Partido(pe.getModalidad(), ParejaDAO.getInstancia().toNegocio(pe.getParejaGanadora()), pe.getFecha());
		return p;
	}

	public boolean guardar(Partido partido) {
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
		
		ParejaEntity parejaGanadora = ParejaDAO.getInstancia().toEntity(partido.getParejaGanadora());
		pe.setParejaGanadora((parejaGanadora));
		
		return pe;
	}
}
