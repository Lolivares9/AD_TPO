package negocio;

import java.util.ArrayList;
import java.util.List;

import dto.CartaDTO;
import enums.PaloCarta;

public class Carta {
	private Integer idCarta;
	private int numero;
	private PaloCarta palo;
	private int valorJuego;
	private int valorEnvido;
	
	public Carta(int numeroCarta, PaloCarta palo, int valorJuego) {
		super();
		this.numero = numeroCarta;
		this.palo = palo;
		this.valorJuego = valorJuego;
	}
	
	public Carta(int numeroCarta, PaloCarta palo, int valorJuego,int valorTanto) {
		super();
		this.numero = numeroCarta;
		this.palo = palo;
		this.valorJuego = valorJuego;
		this.valorEnvido = valorTanto;
	}
	
	public Carta() {
	}

	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public PaloCarta getPalo() {
		return palo;
	}
	public void setPalo(PaloCarta palo) {
		this.palo = palo;
	}
	public int getValorJuego() {
		return valorJuego;
	}
	public void setValorJuego(int valorJuego) {
		this.valorJuego = valorJuego;
	}
	
	public Integer getIdCarta() {
		return idCarta;
	}

	public void setIdCarta(Integer idCarta) {
		this.idCarta = idCarta;
	}
	
	public CartaDTO toDTO() {
		return new CartaDTO(this.numero, this.palo, this.valorJuego);
	}

	public String toString() {
		return numero +" de "+ palo;
	}
	
	public int getValorEnvido() {
		return valorEnvido;
	}

	public void setValorEnvido(int valorTanto) {
		this.valorEnvido = valorTanto;
	}
	
	public static Carta toNegocio(CartaDTO carta){
		return new Carta(carta.getNumero(),carta.getPalo(),carta.getValorJuego());
	}
	
	public static List<Carta> cartasToNegocio(List<CartaDTO> cartas){
		List<Carta> cartasBO = new ArrayList<Carta>();
		for(int i = 0;i<cartas.size();i++){
			cartasBO.add(toNegocio(cartas.get(i)));
		}
		return cartasBO;
	}

	public static Carta cartaMasAlta(List<Turno> turnos){
		Turno turno1 = turnos.get(0);
		Turno turno2 = turnos.get(1);
		Turno turno3 = turnos.get(2);
		Turno turno4 = turnos.get(3);
		
		//ME FALTA TERMINAR, EN EL CASO DE QUE SEA PARDA Y TODO ESO
		
		if((turno1.getCarta().getValorJuego() > turno3.getCarta().getValorJuego() || turno1.getCarta().getValorJuego() > turno4.getCarta().getValorJuego()) ||
				(turno2.getCarta().getValorJuego() > turno3.getCarta().getValorJuego() || turno2.getCarta().getValorJuego() > turno4.getCarta().getValorJuego())){
			if(turno1.getCarta().getValorJuego() > turno2.getCarta().getValorJuego()){
				return turno1.getCarta();
			}
			else{
				return turno2.getCarta();
			}
		}
		else{
			if(turno3.getCarta().getValorJuego() > turno4.getCarta().getValorJuego()){
				return turno3.getCarta();
			}
			else{
				return turno4.getCarta();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
