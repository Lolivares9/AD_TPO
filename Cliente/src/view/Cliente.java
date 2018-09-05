package view;

import javax.swing.JOptionPane;

import delegado.BusinessDelegate;
import excepciones.ComunicacionException;

public class Cliente {

	public static void main(String[] args) {

		try {
			System.out.println(new BusinessDelegate().suma(10, 20));
		} catch (ComunicacionException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}


	}

}
