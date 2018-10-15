package dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.BazaEntity;
import excepciones.BazaException;
import hbt.HibernateUtil;
import negocio.Baza;

public class BazaDAO {
	private static BazaDAO instancia;
	
	public static BazaDAO getInstancia() {
		if(instancia == null){
			instancia = new BazaDAO();
		}
		return instancia;
	}

	public boolean guardar(Baza baza) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<Baza> buscarBazasPorMano(Integer idMano) throws BazaException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		List<BazaEntity> bazasET = null;
		try {
			bazasET = (List<BazaEntity>) s
					.createQuery("from BazaEntity be where be.idMano = ?")
					.setInteger(0, idMano).list();
			s.getTransaction().commit();
			
			
			if (bazasET == null) {
				throw new BazaException("No se encontraron bazas para la mano indicada");
			}else {
				return bazasET.stream().map(this::toNegocio).collect(Collectors.toList());		
			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Baza toNegocio(BazaEntity be) {
		return new Baza(be.getIdBaza(), be.getNumeroBaza(), 
				ParejaDAO.getInstancia().toNegocio(be.getGanadoresBaza()),
				be.getPuntajePareja1(), be.getPuntajePareja2());
	}
}
