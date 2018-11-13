package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.BazaEntity;
import entities.ChicoEntity;
import entities.ManoEntity;
import entities.ParejaEntity;
import excepciones.ManoException;
import hbt.HibernateUtil;
import negocio.Baza;
import negocio.Chico;
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
		ManoEntity mEntity = toEntity(mano);
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		s.saveOrUpdate(mEntity);
		s.getTransaction().commit();
		s.close();

		return true;
	}

//	public ManoEntity toEntity(Mano mano) {
//		ManoEntity me = new ManoEntity();
//		
//		me.setIdMano(mano.getIdMano());
//		me.setNumeroMano(mano.getNumeroMano());
//		
//		ParejaEntity parejaGanadora = ParejaDAO.getInstancia().toEntity(mano.getParejaGanadora());
//		me.setParejaGanadora(parejaGanadora);
//		
//		return me;
//	}

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
			throw new ManoException("Error al obtener las manos para el chico "+idChico);
		}
	}
	
	public Mano toNegocio(ManoEntity me) {
		Mano mano = null;
		List<Baza> bazas = null;
		if(me.getBazas() != null){
			bazas = BazaDAO.getInstancia().toNegocioAll(me.getBazas());
		}
		if(me.getParejaGanadora() != null){
			mano = new Mano(me.getNumeroMano(),ParejaDAO.getInstancia().toNegocio(me.getParejaGanadora()),me.getPuntajePareja1(), me.getPuntajePareja2(), bazas);
		}
		else{
			mano = new Mano(me.getNumeroMano(),null,me.getPuntajePareja1(), me.getPuntajePareja2(), bazas);
		}
		mano.setIdMano(me.getIdMano());
		return mano;
	}
	
	public List<Mano> manosToNegocio(List<ManoEntity> manos){
		List<Mano> manosNegocio = null;
		if(manos.size() > 0){
			manosNegocio = new ArrayList<Mano>();
			for(int i = 0;i<manos.size();i++){
				manosNegocio.add(toNegocio(manos.get(i)));
			}
		}
		return manosNegocio;
	}

	public ManoEntity toEntity(Mano mano) {
		ManoEntity me = new ManoEntity();
		me.setIdMano(mano.getIdMano());
		me.setNumeroMano(mano.getNumeroMano());
		if(mano.getParejaGanadora() != null){
			me.setParejaGanadora(ParejaDAO.getInstancia().toEntity(mano.getParejaGanadora()));
		}
		List<BazaEntity> bazasentities = new ArrayList<BazaEntity>();
		for(Baza b : mano.getBazas()){
			BazaEntity be = BazaDAO.getInstancia().toEntity(b);
			be.setMano(me);
			bazasentities.add(be);
		}
		if(bazasentities.size() > 0){
			me.setBazas(bazasentities);
		}
		return me;
	}
}
