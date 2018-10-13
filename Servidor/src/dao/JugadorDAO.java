package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import dto.JugadorDTO;
import entities.JugadorEntity;
import enums.Categoria;
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
		JugadorEntity jEntity = toEntity(p);
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		s.saveOrUpdate(jEntity);
		s.getTransaction().commit();
		s.close();

		return true;
	}
	
	public Jugador toNegocio(JugadorEntity jugador){
		Jugador j = new Jugador(jugador.getNombre(),jugador.getApodo(),jugador.getMail(),jugador.getCategoria(),jugador.getPuntaje(),
				jugador.getPartidosJugados(),jugador.getPartidosGanados(),jugador.isConectado(),jugador.isJugando(),jugador.getPassword());
		j.setId(jugador.getIdJugador());
		return j;
	}

	public JugadorDTO toDTO(JugadorEntity jugador){
		//return new JugadorDTO(jugador.getApodo(), jugador.getMail(), jugador.getPassword());
		return null;
	}
	
	public JugadorEntity toEntity(Jugador jugador){
		JugadorEntity entity = new JugadorEntity(jugador.getNombre(), jugador.getApodo(), jugador.getMail(),
				jugador.getPassword(), jugador.getCategoria(), jugador.getPuntaje(), jugador.getPartidosJugados(), jugador.getPartidosGanados(),
				true, true);
		entity.setIdJugador(jugador.getId());
		return entity;
	}

	public Jugador buscarPorMail(String mail) throws JugadorException {

		Jugador resultado = null;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		JugadorEntity aux;
		try {
			aux = (JugadorEntity) s
					.createQuery("from JugadorEntity je where je.mail = ?")
					.setString(0, mail).uniqueResult();
			
			s.getTransaction().commit();
			s.close();
			
			if (aux != null) {
				resultado = this.toNegocio(aux);
			} else {
				//No puedo tirar una excepcion acá porque lo estoy usando para validar
				//throw new JugadorException("No se encontro el jugador con el mail: '" + mail + "'.");
				return resultado;
			}

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultado;
	}
	
	public Jugador buscarPorApodo(String apodo) throws JugadorException {

		Jugador resultado = null;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		JugadorEntity aux;
		try {
			aux = (JugadorEntity) s
					.createQuery("from JugadorEntity je where je.apodo = ?")
					.setString(0, apodo).uniqueResult();
			
			s.getTransaction().commit();
			s.close();
			
			if (aux != null) {
				resultado = this.toNegocio(aux);
			} else {
				//throw new JugadorException("No se encontro el jugador con el apodo: '" + apodo + "'.");
				return resultado;
			}

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultado;
	}
	
	public List <Jugador> obtenerJugadoresPorCateg(Categoria categ){
		List<Jugador> jugadores = new ArrayList<Jugador>();
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		@SuppressWarnings("unchecked")
		List<JugadorEntity> jugadoresRecup = (List<JugadorEntity>) s.createQuery("from JugadorEntity je where je.categoria = ? and je.conectado = true and je.jugando = false").list();
		for(JugadorEntity jug : jugadoresRecup){
			jugadores.add(this.toNegocio(jug));
		}
		s.getTransaction().commit();
		s.close();
		return jugadores;
	}
	
	public boolean validarDatos(String apodo, String mail) throws JugadorException {
		if(buscarPorMail(mail) == null && buscarPorApodo(apodo) == null) {
			return true;
		}
		return false;
	}
	
}
