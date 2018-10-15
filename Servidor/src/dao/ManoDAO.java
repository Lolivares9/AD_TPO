package dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.ManoEntity;
import excepciones.ManoException;
import hbt.HibernateUtil;
import negocio.Mano;

public class ManoDAO {
	private static ManoDAO instancia;
	
	public static ManoDAO getInstancia() {
		if(instancia == null){
			instancia = new ManoDAO();
		}
		return instancia;
	}

	public boolean guardar(Mano mano) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<Mano> buscarManosPorChico(Integer idChico) throws ManoException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		List<ManoEntity> manosET = null;
		try {
			manosET = (List<ManoEntity>) s
					.createQuery("from ManoEntity me where me.idChico = ?")
					.setInteger(0, idChico).list();
			s.getTransaction().commit();
			
			
			if (manosET == null) {
				throw new ManoException("No se encontraron manos para el chico indicado");
			}else {
				return manosET.stream().map(this::toNegocio).collect(Collectors.toList());		
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Mano toNegocio(ManoEntity me) {
		return new Mano(me.getIdMano(), me.getNumeroMano(),
				ParejaDAO.getInstancia().toNegocio(me.getParejaGanadora()));
	}
}
