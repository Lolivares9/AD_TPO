package test;

import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import servidor.Server;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			new Server();
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, "No pude arrancar el servidor");
		}
	}

}
