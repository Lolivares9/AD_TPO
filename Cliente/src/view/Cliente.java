package view;

import javax.swing.JOptionPane;

import delegado.BusinessDelegate;
import excepciones.ComunicationException;

public class Cliente {

	public static void main(String[] args) {

		try {
			//System.out.println(new BusinessDelegate().suma(10, 20));
			BusinessDelegate.getInstancia().AltaJugador();
		} catch (ComunicationException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}


	}

}
