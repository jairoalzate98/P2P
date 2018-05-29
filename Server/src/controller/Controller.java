package controller;

import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JOptionPane;

import comunication.Server;

public class Controller {
	
	private Server server;

	public Controller() {
		int port = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el puerto"));
		try {
			server = new Server(port);
		} catch (NumberFormatException | HeadlessException | IOException e) {
			e.printStackTrace();
		}
	}
}