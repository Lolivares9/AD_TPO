package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import entities.ChicoEntity;
import entities.ManoEntity;
import entities.ParejaEntity;
import excepciones.ChicoException;
import hbt.HibernateUtil;
import negocio.Chico;
import negocio.Mano;


public class ChicoDAO {
	private static ChicoDAO instancia;
	
	public static ChicoDAO getInstancia() {
		if(instancia == null){
			instancia = new ChicoDAO();
		}
		return instancia;
	}

	public boolean guardar(Chico chico) {
		ChicoEntity cEntity = toEntity(chico);
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		s.saveOrUpdate(cEntity);
		s.getTransaction().commit();
		s.close();

		return true;
	}
	
	public ChicoEntity toEntity(Chico chico) {
		ChicoEntity ce = new ChicoEntity();
		
		ce.setIdChico(chico.getIdChico());
		ce.setNumeroChico(chico.getNumero());
		ce.setFinalizado(chico.isFinalizado());
		ce.setPuntajePareja1(chico.getPuntajePareja1());
		ce.setPuntajePareja2(chico.getPuntajePareja2());
		
		ParejaEntity parejaGanadora = null;
		if (chico.getParejaGanadora() != null) {
			parejaGanadora = ParejaDAO.getInstancia().toEntity(chico.getParejaGanadora());
			ce.setParejaGanadora(parejaGanadora);
		}
		if(chico.getManos() != null){
			List<ManoEntity> manosentities = new ArrayList<ManoEntity>();
			for(Mano man : chico.getManos()){
				ManoEntity m = ManoDAO.getInstancia().toEntity(man);
				m.setNumeroMano(m.getNumeroMano());
				m.setIdChico(ce);
				manosentities.add(m);
			}
			ce.setManos(manosentities);
		}
		return ce;
	}

	@SuppressWarnings("unchecked")
	public List<Chico> buscarChicosPorPartido(Integer idPartido) throws ChicoException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		List<ChicoEntity> chicosET = null;
		try {
			chicosET = (List<ChicoEntity>) s
					.createQuery("from ChicoEntity ce where ce.idPartido = ?")
					.setInteger(0, idPartido).list();
			s.getTransaction().commit();
			
			
			if (chicosET == null) {
				throw new ChicoException("No se encontraron chicos para ese partido");
			}else {
				return chicosET.stream().map(this::toNegocio).collect(Collectors.toList());		
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Chico toNegocio(ChicoEntity ce) {
		if(ce.getParejaGanadora() != null){
			return new Chico(ce.getNumeroChico(), ce.isFinalizado(), ParejaDAO.getInstancia().toNegocio(ce.getParejaGanadora()), ce.getPuntajePareja1(), ce.getPuntajePareja2());
		}
		else{
			return new Chico(ce.getIdChico(),ce.getNumeroChico(),ManoDAO.getInstancia().manosToNegocio(ce.getManos()),ce.isFinalizado(),null,ce.getPuntajePareja1(),ce.getPuntajePareja2());
		}
	}
}
