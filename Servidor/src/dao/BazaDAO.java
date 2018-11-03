package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.BazaEntity;
import entities.ParejaEntity;
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

	public boolean guardar(Baza b) {
		BazaEntity bEntity = toEntity(b);
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		s.saveOrUpdate(bEntity);
		s.getTransaction().commit();
		s.close();

		return true;
	}
	
	public BazaEntity toEntity(Baza baza) {
		BazaEntity be = new BazaEntity();
		
		be.setIdBaza(baza.getIdBaza());
		be.setNumeroBaza(baza.getNumero());
		be.setPuntajePareja1(baza.getPuntajePareja1());
		be.setPuntajePareja2(baza.getPuntajePareja2());
		
		ParejaEntity parejaGanadora = ParejaDAO.getInstancia().toEntity(baza.getGanadores());
		be.setGanadoresBaza((parejaGanadora));
		
		return be;
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
			throw new BazaException("Error al buscar las bazas para la mano "+ idMano);
		}
	}
	
	public Baza toNegocio(BazaEntity be) {
		Baza baza = new Baza();
		if(be != null){
			return new Baza(be.getIdBaza(), be.getNumeroBaza(), 
					ParejaDAO.getInstancia().toNegocio(be.getGanadoresBaza()),
					be.getPuntajePareja1(), be.getPuntajePareja2(),TurnoDAO.getInstancia().toNegocioAll(be.getTurnos()));
		}
		return baza;
	}

	public List<Baza> toNegocioAll(List<BazaEntity> bazas) {
		List<Baza> bazasNegocio = new ArrayList<Baza>();
		if(bazas != null){
			for(int i = 0;i<bazas.size();i++){
				bazasNegocio.add(toNegocio(bazas.get(i)));
			}
		}
		return bazasNegocio;
	}
}
