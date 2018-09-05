package excepciones;

public class ComunicacionException extends Exception {

	private static final long serialVersionUID = 3525972443403681595L;

	public ComunicacionException(String mensaje){
		super(mensaje);
	}
}
