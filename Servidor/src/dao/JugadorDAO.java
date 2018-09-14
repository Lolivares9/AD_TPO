package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import dto.JugadorDTO;
import entities.JugadorEntity;
import excepciones.JugadorException;
import hbt.HibernateUtil;
import negocio.Jugador;

public class JugadorDAO {

	private static JugadorDAO instancia;
	
	public static JugadorDAO getInstancia() {
		if(instancia == null){
			instancia = new JugadorDAO();
		}
		return instancia;
	}
	
	public boolean existeJugador(String apodo) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean guardarJugador(Jugador p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Jugador toNegocio(JugadorEntity jugador){
		//return new Jugador(jugador.getApodo(), jugador.getMail(), jugador.getPassword(), null, 0, 0, 0, false, false, null);
		return null;
	}

	public JugadorDTO toDTO(JugadorEntity jugador){
		//return new JugadorDTO(jugador.getApodo(), jugador.getMail(), jugador.getPassword());
		return null;
	}

	public Jugador findByMail(String mail) throws JugadorException {

		Jugador resultado = null;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		JugadorEntity aux;
		try {
			aux = (JugadorEntity) s
					.createQuery("select ae from JugadorEntity je where je.mail = ?")
					.setString(0, mail).uniqueResult();
			
			s.getTransaction().commit();
			s.close();
			
			if (aux != null) {
				resultado = this.toNegocio(aux);
			} else {
				throw new JugadorException("No se encontro el jugador con el mail: '" + mail + "'.");
			}

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultado;
	
	}
}
