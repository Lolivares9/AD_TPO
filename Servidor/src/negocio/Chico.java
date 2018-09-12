package negocio;

import java.util.Date;

public class Chico {
	private int numero;
	private Date fecha;
	private boolean finaizado;
	private Pareja parejaGanadora;
	
	public Chico(int numero, Date fecha, boolean finaizado, Pareja parejaGanadora) {
		super();
		this.numero = numero;
		this.fecha = fecha;
		this.finaizado = finaizado;
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
		return finaizado;
	}
	public void setFinaizado(boolean finaizado) {
		this.finaizado = finaizado;
	}
	public Pareja getParejaGanadora() {
		return parejaGanadora;
	}
	public void setParejaGanadora(Pareja parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}
	
}
