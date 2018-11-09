package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

	public boolean guardar(Jugador p) {
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
		Jugador j = null;
		if(jugador.getNumeroJugadorPartido() != -1){
			j = new Jugador(jugador.getNombre(),jugador.getApodo(),jugador.getMail(),jugador.getCategoria(),jugador.getPuntaje(),
					jugador.getPartidosJugados(),jugador.getPartidosGanados(),jugador.isConectado(),jugador.isJugando(),jugador.isBuscandoLibreIndividual(),jugador.getPassword(),jugador.getNumeroJugadorPartido());
			 j.setId(jugador.getIdJugador());
		}
		else{
			 j = new Jugador(jugador.getNombre(),jugador.getApodo(),jugador.getMail(),jugador.getCategoria(),jugador.getPuntaje(),
						jugador.getPartidosJugados(),jugador.getPartidosGanados(),jugador.isConectado(),jugador.isJugando(),jugador.isBuscandoLibreIndividual(),jugador.getPassword());
			j.setId(jugador.getIdJugador());
		}
		return j;
	}
	
	public JugadorEntity toEntity(Jugador jugador){
		JugadorEntity je = new JugadorEntity();
		
		je.setApodo(jugador.getApodo());
		je.setNombre(jugador.getNombre());
		je.setMail(jugador.getMail());
		je.setCategoria(jugador.getCategoria());
		je.setPuntaje(jugador.getPuntaje());
		je.setPartidosJugados(jugador.getPartidosJugados());
		je.setPartidosGanados(jugador.getPartidosGanados());
		je.setConectado(jugador.isConectado());
		je.setJugando(jugador.isJugando());
		je.setBuscandoLibreIndividual(jugador.isBuscandoLibreIndividual());
		je.setPassword(jugador.getPassword());
		je.setIdJugador(jugador.getId());
		if(jugador.getNumJugador() != -1){
			je.setNumeroJugadorPartido(jugador.getNumJugador());
		}
		return je;
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
				return resultado;
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return resultado;
	}
	
	public List <Jugador> getAllJugadores(){
		List<Jugador> jugadores = new ArrayList<Jugador>();
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		@SuppressWarnings("unchecked")
		//FALTARIA AGREGAR UN TOP
		List<JugadorEntity> jugadoresRecup = (List<JugadorEntity>) s.createQuery("from JugadorEntity je where je.conectado = true and je.jugando = false and je.buscandoLibreIndividual = true").list();
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
