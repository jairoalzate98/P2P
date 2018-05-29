package controllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import comunication.Client;

public class Controller {

	public static final String ENTER_THE_PORT = "Ingrese el puerto";
	public static final String ENTER_THE_IP = "Ingrese la ip";
	private Client client;
	
	public Controller() {
		String ip = JOptionPane.showInputDialog(null, ENTER_THE_IP);
		int port = Integer.parseInt(JOptionPane.showInputDialog(null, ENTER_THE_PORT));
		String name = JOptionPane.showInputDialog(null, "Ingrese su nombre de usuario");
		try {
			client = new Client(ip, port, name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}