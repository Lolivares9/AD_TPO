package negocio;

import java.util.ArrayList;
import java.util.List;

import dao.ParejaDAO;
import dto.ParejaDTO;
import enums.Categoria;

public class Pareja {
	private Integer idPareja;
	private Jugador jugador1;
	private Jugador jugador2;
	private List<Carta> cartasJugador1;
	private List<Carta> cartasJugador2;
	
	public Pareja(Jugador jugador1, Jugador jugador2) {
		super();
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
	}
	
	public Pareja(Integer idPareja, Jugador negocio, Jugador negocio2) {
		this.idPareja = idPareja;
		this.jugador1 = negocio;
		this.jugador2 = negocio2;
	}

	public Pareja(){
		
	};
	
	public Jugador getJugador1() {
		return jugador1;
	}
	public void setJugador1(Jugador jugador1) {
		this.jugador1 = jugador1;
	}
	public Jugador getJugador2() {
		return jugador2;
	}
	public void setJugador2(Jugador jugador2) {
		this.jugador2 = jugador2;
	}	
	public Integer getIdPareja() {
		return idPareja;
	}

	public void setIdPareja(Integer idPareja) {
		this.idPareja = idPareja;
	}
	
	public boolean guardar(){
		return ParejaDAO.getInstancia().guardar(this);
	}
	
	public ParejaDTO toDTO() {
		return new ParejaDTO(this.idPareja,jugador1.toDTO(), jugador2.toDTO());
	}

	public static List<Pareja> distribuirParejas(List<Jugador> jugDisp) {

		Categoria inicial;
		List <Pareja> parejasArmadas = new ArrayList<Pareja>();
		inicial = jugDisp.get(0).getCategoria();
		Pareja uno = new Pareja(null,null);
		Pareja dos = new Pareja(null,null);
		for(int i = 0;i<4;i++){
			if(jugDisp.get(0).getCategoria().equals(inicial) && (uno.getJugador1() == null || dos.getJugador1() == null)){
				if(uno.getJugador1() == null){
					uno.setJugador1(jugDisp.get(0));
					uno.getJugador1().setNumJugador(1);
					jugDisp.remove(0);
				}
				else{
					dos.setJugador1(jugDisp.get(0));
					dos.getJugador1().setNumJugador(2);
					jugDisp.remove(0);
				}
			}
			else{
				if(uno.getJugador2() == null){
					uno.setJugador2(jugDisp.get(0));
					uno.getJugador2().setNumJugador(3);
					jugDisp.remove(0);
				}
				else{
					dos.setJugador2(jugDisp.get(0));
					dos.getJugador2().setNumJugador(4);
					jugDisp.remove(0);
				}
			}
		}
		
		parejasArmadas.add(uno);
		parejasArmadas.add(dos);
		
		return parejasArmadas;
	}

	public static void actualizarEstadoParejas(List<Pareja> parejas) {
		for (Pareja pj : parejas) {
			//actualizo el jugador1
			Jugador j = pj.getJugador1();
			j.actualizarEstadoJugador();
			//actualizo el jugador 2
			j = pj.getJugador2();
			j.actualizarEstadoJugador();
		}
	}

	public List<Carta> getCartasJugador1() {
		return cartasJugador1;
	}

	public void setCartasJugador1(List<Carta> cartasJugador1) {
		this.cartasJugador1 = cartasJugador1;
	}

	public List<Carta> getCartasJugador2() {
		return cartasJugador2;
	}

	public void setCartasJugador2(List<Carta> cartasJugador2) {
		this.cartasJugador2 = cartasJugador2;
	}
	
	public static Pareja calcularTantoParejas(Pareja pareja1, Pareja pareja2){
		Pareja parejaGanadora = null;
		
		List<Carta> cartasJug1Pareja1 = pareja1.getCartasJugador1();
		List<Carta> cartasJug2Pareja1 = pareja1.getCartasJugador2();
		List<Carta> cartasJug1Pareja2 = pareja2.getCartasJugador1();
		List<Carta> cartasJug2Pareja2 = pareja2.getCartasJugador2();
		int tantoJug1Pareja1 = Jugador.calcularTantoJugadores(cartasJug1Pareja1);
		int tantoJug2Pareja1 = Jugador.calcularTantoJugadores(cartasJug2Pareja1);
		int tantoJug1Pareja2 = Jugador.calcularTantoJugadores(cartasJug1Pareja2);
		int tantoJug2Pareja2 = Jugador.calcularTantoJugadores(cartasJug2Pareja2);
		
		if(((tantoJug1Pareja1 > tantoJug1Pareja2) && (tantoJug1Pareja1 > tantoJug2Pareja2)) || ((tantoJug2Pareja1 > tantoJug1Pareja2) && (tantoJug2Pareja1 > tantoJug2Pareja2))){
			parejaGanadora =pareja1;
		}
		else if(((tantoJug1Pareja2 > tantoJug1Pareja1) && (tantoJug1Pareja2 > tantoJug2Pareja1)) || ((tantoJug2Pareja2 > tantoJug1Pareja1) && (tantoJug2Pareja2 > tantoJug2Pareja1))){
			parejaGanadora = pareja2;
		}
		//ESTE CASO ES CUANDO EMPATAN EN EL ENVIDO,GANA EL QUE ES MANO
		else if(((tantoJug1Pareja1 == tantoJug1Pareja2) || (tantoJug1Pareja1 == tantoJug2Pareja2)) || ((tantoJug2Pareja1 == tantoJug1Pareja2) || (tantoJug2Pareja1 == tantoJug2Pareja2))){
			if(pareja1.getJugador1().getNumJugador() == 1 || pareja1.getJugador2().getNumJugador() == 1){
				return parejaGanadora = pareja1;
			}
			else{
				return parejaGanadora = pareja2;
			}
		}
		else{
			parejaGanadora = pareja2;
		}
		
		return parejaGanadora;
		
	}

	public static Pareja calcularParejaGanadoraTruco(Pareja pareja1, Pareja pareja2){
		Pareja parejaGanadora = null;
		
		
		return parejaGanadora;
	}
}
