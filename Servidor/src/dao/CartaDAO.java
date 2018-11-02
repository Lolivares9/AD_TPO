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
	
	public CartaEntity toEntity(Carta carta) {
		//TODO
		return null;
	}
	
	public Carta toNegocio(CartaEntity carta) {
		Carta c = new Carta();
		if(carta != null){
			c = new Carta(carta.getNumero(), carta.getPalo(), carta.getValorJuego()); 
			c.setIdCarta(carta.getIdCarta());
		}
		return c;
	}
	
}
