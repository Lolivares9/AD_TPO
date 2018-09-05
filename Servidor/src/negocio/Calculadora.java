package negocio;

public class Calculadora {

	private static Calculadora instancia;
	
	private Calculadora(){}
	
	public static Calculadora getInstancia(){
		if(instancia == null)
			instancia = new Calculadora();
		return instancia;
	}
	
	public int sumar(int a, int b){
		return a + b;
	}
	
	public int restar(int a, int b){
		return a - b;
	}
}
