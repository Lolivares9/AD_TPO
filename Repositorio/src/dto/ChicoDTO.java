package dto;
import java.util.Date;

public class ChicoDTO {

	private int numero;
	private Date fecha;
	private boolean finaizado;
	private ParejaDTO parejaGanadora;
	
	public ChicoDTO(int numero, Date fecha, boolean finaizado, ParejaDTO parejaGanadora) {
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
	public ParejaDTO getParejaGanadora() {
		return parejaGanadora;
	}
	public void setParejaGanadora(ParejaDTO parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}
	

}
