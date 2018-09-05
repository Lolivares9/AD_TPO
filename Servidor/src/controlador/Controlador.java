package controlador;

import negocio.Calculadora;

public class Controlador {

	public int sumar (int a, int b){
		return Calculadora.getInstancia().sumar(a, b);
	}
	
	public int restar(int a, int b){
		return Calculadora.getInstancia().restar(a, b);
	}
	
}
