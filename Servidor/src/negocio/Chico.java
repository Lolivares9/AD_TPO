package negocio;

import java.util.Date;
import dao.ChicoDAO;

/**
 * Soy una colección de manos
 * Un chico es finalizado cuando se llegan a los 30 tantos
 * (15 malos y 15 buenos)
 */
public class Chico {
	private int numero;
	private Date fecha;
	private boolean finalizado;
	private Pareja parejaGanadora;
	
	public Chico(int numero, Date fecha, boolean finaizado, Pareja parejaGanadora) {
		super();
		this.numero = numero;
		this.fecha = fecha;
		this.finalizado = finaizado;
		this.parejaGanadora = parejaGanadora;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public boolean isFinaizado() {
		return finalizado;
	}
	public void setFinaizado(boolean finaizado) {
		this.finalizado = finaizado;
	}
	public Pareja getParejaGanadora() {
		return parejaGanadora;
	}
	public void setParejaGanadora(Pareja parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}
	
	public boolean guardar(){
		return ChicoDAO.getInstancia().guardar(this);
	}
}
