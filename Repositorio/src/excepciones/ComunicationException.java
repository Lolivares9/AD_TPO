package excepciones;

public class ComunicationException extends Exception {

	private static final long serialVersionUID = 3525972443403681595L;

	public ComunicationException(String mensaje){
		super(mensaje);
	}
}
