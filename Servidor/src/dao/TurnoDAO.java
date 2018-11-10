package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.BazaEntity;
import entities.CartaEntity;
import entities.JugadorEntity;
import entities.TurnoEntity;
import excepciones.TurnoException;
import hbt.HibernateUtil;
import negocio.Turno;

public class TurnoDAO {
	private static TurnoDAO instancia;
	
	public static TurnoDAO getInstancia() {
		if(instancia == null){
			instancia = new TurnoDAO();
		}
		return instancia;
	}

	public boolean guardar(Turno turno) {
		TurnoEntity tEntity = toEntity(turno);
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		s.saveOrUpdate(tEntity);
		s.getTransaction().commit();
		s.close();

		return true;
	}
	
	public TurnoEntity toEntity(Turno turno) {
		TurnoEntity te = new TurnoEntity();
		CartaEntity carta = null;
		if(turno.getCarta() != null){
			carta = CartaDAO.getInstancia().toEntity(turno.getCarta());
		}
		te.setCarta(carta);
		te.setEnvite(turno.getEnvite());
		te.setIdTurno(turno.getIdTurno());
		JugadorEntity jugador = JugadorDAO.getInstancia().toEntity(turno.getJugador());
		te.setJugador(jugador);
		return te;
	}

	@SuppressWarnings("unchecked")
	public List<Turno> buscarTurnosPorBaza(Integer idBaza) throws TurnoException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		List<TurnoEntity> manosET = null;
		try {
			manosET = (List<TurnoEntity>) s
					.createQuery("from TurnoEntity te where te.idBaza = ?")
					.setInteger(0, idBaza).list();
			s.getTransaction().commit();
			
			
			if (manosET == null) {
				throw new TurnoException("No se encontraron turnos para la baza indicada");
			}else {
				return manosET.stream().map(this::toNegocio).collect(Collectors.toList());		
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new TurnoException("Error al buscar los turnos para la baza "+idBaza);
		}
	}
	
	public Turno toNegocio(TurnoEntity te) {
		Turno t = null;
		if(te != null){
			t = new Turno(te.getIdTurno(),JugadorDAO.getInstancia().toNegocio(te.getJugador()),
					te.getEnvite(), CartaDAO.getInstancia().toNegocio(te.getCarta()));
		}
		return t;
	}

	public List<Turno> toNegocioAll(List<TurnoEntity> turnos) {
		List<Turno> turnosNegocio = new ArrayList<Turno>();
		if(turnos != null){
			for(int i = 0;i<turnos.size();i++){
				turnosNegocio.add(toNegocio(turnos.get(i)));
			}
		}
		return turnosNegocio;
	}
}
