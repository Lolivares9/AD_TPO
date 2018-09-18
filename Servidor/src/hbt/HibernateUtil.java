package hbt;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import entities.BazaEntity;
import entities.CartaEntity;
import entities.ChicoEntity;
import entities.GrupoEntity;
import entities.JugadorEntity;
import entities.ManoEntity;
import entities.ModalidadEntity;
import entities.ParejaEntity;
import entities.PartidoEntity;
import entities.RankingGrupalEntity;
import entities.RankingGrupalPK;
import entities.TurnoEntity;


public class HibernateUtil {

	    private static final SessionFactory sessionFactory;
	   
	    static
	    {
	        try
	        {
	        	AnnotationConfiguration config = new AnnotationConfiguration();
	         	config.addAnnotatedClass(BazaEntity.class);
	         	config.addAnnotatedClass(CartaEntity.class);
	         	config.addAnnotatedClass(ChicoEntity.class);
	         	config.addAnnotatedClass(GrupoEntity.class);
	         	config.addAnnotatedClass(JugadorEntity.class);
	         	config.addAnnotatedClass(ManoEntity.class);
	         	config.addAnnotatedClass(ModalidadEntity.class);
	         	config.addAnnotatedClass(ParejaEntity.class);
	         	config.addAnnotatedClass(PartidoEntity.class);
	         	config.addAnnotatedClass(RankingGrupalEntity.class);
	         	config.addAnnotatedClass(RankingGrupalPK.class);
	         	config.addAnnotatedClass(TurnoEntity.class);
	             sessionFactory = config.buildSessionFactory();
	        }
	        catch (Throwable ex)
	        {
	        	//	quizas aca podemos hacer otro tipo de control por una excepcion especial.
	            System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    }
	 
	    public static SessionFactory getSessionFactory()
	    {
	        return sessionFactory;
	    }
	}
	
	

