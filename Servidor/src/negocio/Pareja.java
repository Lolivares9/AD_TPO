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
		return new ParejaDTO(jugador1.toDTO(), jugador2.toDTO());
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
					jugDisp.remove(0);
				}
				else{
					
					dos.setJugador1(jugDisp.get(0));
					jugDisp.remove(0);
				}
			}
			else{
				if(uno.getJugador2() == null){
					uno.setJugador2(jugDisp.get(0));
					jugDisp.remove(0);
				}
				else{
					dos.setJugador2(jugDisp.get(0));
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
	
}
