package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.CartaEntity;
import excepciones.CartaException;
import hbt.HibernateUtil;
import negocio.Carta;
import negocio.Pareja;

public class CartaDAO {
	private static CartaDAO instancia;
	private static List<Carta> cartas;
	
	public static CartaDAO getInstancia() {
		if(instancia == null){
			instancia = new CartaDAO();
			cartas = new ArrayList<Carta>();
		}
		return instancia;
	}

	@SuppressWarnings("unchecked")
	public List<Carta> obtenerCartas() throws CartaException{
		if(cartas.isEmpty()){
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session s = sf.openSession();
			s.beginTransaction();
			List<CartaEntity> c;
			try {
				c = (List<CartaEntity>) s.createCriteria(CartaEntity.class).list();
				s.getTransaction().commit();
			
				if (c == null) {
					throw new CartaException("No se pudo recuperar las cartas.");
				}else {
					cartas = c.stream().map(this::toNegocio).collect(Collectors.toList());
					Collections.shuffle(cartas);
				}
				
			} catch (HibernateException e) {
				e.printStackTrace();
				throw new CartaException("Error al obtener las cartas.");
			}finally{
				s.close();
			}
		}
		return new ArrayList<Carta>(cartas);
	}
	
	public List<Carta> obtenerCartaPorJugador(String carta1,String carta2,String carta3) throws CartaException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		CartaEntity c1;
		CartaEntity c2;
		CartaEntity c3;
		List<Carta> cartasJugador = new ArrayList<Carta>();
		try {
			c1 = (CartaEntity) s.createQuery("FROM CartaEntity WHERE idCarta = ?").setString(0, carta1).uniqueResult();
			c2 = (CartaEntity) s.createQuery("FROM CartaEntity WHERE idCarta = ?").setString(0, carta2).uniqueResult();
			c3 = (CartaEntity) s.createQuery("FROM CartaEntity WHERE idCarta = ?").setString(0, carta3).uniqueResult();
			s.getTransaction().commit();
			if(c1 != null && c2 != null && c3 != null){
					cartasJugador.add(toNegocio(c1));
					cartasJugador.add(toNegocio(c2));
					cartasJugador.add(toNegocio(c3));
			}
		}catch (HibernateException e) {
			e.printStackTrace();
			throw new CartaException("Error al obtener las cartas.");
		}finally{
			s.close();
		}
		return cartasJugador;
	}
	
	public CartaEntity toEntity(Carta carta) {
		CartaEntity ce = new CartaEntity();
		ce.setIdCarta(carta.getIdCarta());
		ce.setNumero(carta.getNumero());
		ce.setPalo(carta.getPalo());
		ce.setValorEnvido(carta.getValorEnvido());
		ce.setValorJuego(carta.getValorJuego());
		
		return ce;
	}
	
	public Carta toNegocio(CartaEntity carta) {
		Carta c = null;
		if(carta != null){
			c = new Carta(carta.getNumero(), carta.getPalo(), carta.getValorJuego(),carta.getValorEnvido()); 
			c.setIdCarta(carta.getIdCarta());
		}
		return c;
	}
	
	public void saveCartasParejas(Pareja p) throws CartaException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		String cartasJug1 = "";
		String cartasJug2 = "";
		String c1 = String.valueOf(p.getCartasJugador1().get(0).getIdCarta());
		String c2 = String.valueOf(p.getCartasJugador1().get(1).getIdCarta());
		String c3 = String.valueOf(p.getCartasJugador1().get(2).getIdCarta());
		cartasJug1 = c1+','+c2+','+c3;
		c1 = String.valueOf(p.getCartasJugador2().get(0).getIdCarta());
		c2 = String.valueOf(p.getCartasJugador2().get(1).getIdCarta());
		c3 = String.valueOf(p.getCartasJugador2().get(2).getIdCarta());
		cartasJug2 = c1+','+c2+','+c3;
		s.beginTransaction();
		List<Integer> existe = new ArrayList<Integer>();
		try {
			existe = s.createSQLQuery("SELECT ID_PAREJA FROM PAREJAS_CARTAS WHERE ID_PAREJA = ?").setString(0, String.valueOf(p.getIdPareja())).list();
			if(existe.size() == 0){
				s.createSQLQuery("INSERT INTO PAREJAS_CARTAS VALUES ("+String.valueOf(p.getIdPareja())+",'"+cartasJug1+"','"+cartasJug2+"')").executeUpdate();
			}else{
				s.createSQLQuery("UPDATE PAREJAS_CARTAS SET CARTAS_JUGADOR1 = ('"+ cartasJug1 +"'), CARTAS_JUGADOR2 = ('"+ cartasJug2 +"') WHERE ID_PAREJA = ?").setString(0, String.valueOf(p.getIdPareja())).executeUpdate();
			}
			s.getTransaction().commit();
		}catch (HibernateException e) {
			e.printStackTrace();
			throw new CartaException("Error al guardar las cartas de los jugadores.");
		}finally{
			s.close();
		}
	}
	
	public Carta obtenerCartaPorID(int idCarta){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		CartaEntity centity;
		Carta cartaNegocio = null;
		try {
			centity = (CartaEntity) s
					.createQuery("from CartaEntity where idCarta = ?")
					.setInteger(0, idCarta).uniqueResult();
			
			s.getTransaction().commit();
			s.close();
			
			if (centity != null) {
				cartaNegocio = this.toNegocio(centity);
			} else {
				return cartaNegocio;
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return cartaNegocio;
	}
}
