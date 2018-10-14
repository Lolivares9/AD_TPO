package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.GrupoEntity;
import excepciones.GrupoException;
import hbt.HibernateUtil;
import negocio.Grupo;
import negocio.Partido;

public class PartidoDAO {
	private static PartidoDAO instancia;
	
	public static PartidoDAO getInstancia() {
		if(instancia == null){
			instancia = new PartidoDAO();
		}
		return instancia;
	}
	
//	public List<Partido> buscarPartidosPorJugador(){
//		SessionFactory sf = HibernateUtil.getSessionFactory();
//		Session s = sf.openSession();
//		s.beginTransaction();
//		PartidoEntity g;
//		Grupo grupo = null;
//		try {
//			g = (GrupoEntity) s
//					.createQuery("from GrupoEntity ge where ge.nombre = ?")
//					.setString(0, nombreGrupo).uniqueResult();
//			s.getTransaction().commit();
//			
//			
//			if (g == null) {
//				throw new GrupoException("No existe el grupo.");
//			}else {
//				grupo = toNegocio(g);
//				s.close();
//			}
//			
//		} catch (HibernateException e) {
//			e.printStackTrace();
//			return null;
//		}
//		return grupo;
//	}
}
