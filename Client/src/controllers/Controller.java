package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import comunication.Client;
import persistence.FileManager;
import views.MainWindow;

public class Controller implements ActionListener{

	public static final String ENTER_THE_PORT = "Ingrese el puerto";
	public static final String ENTER_THE_IP = "Ingrese la ip";
	private Client client;
	private MainWindow mainWindow;
	private FileManager fileManager;
	
	public Controller() {
		String ip = JOptionPane.showInputDialog(null, ENTER_THE_IP);
		int port = Integer.parseInt(JOptionPane.showInputDialog(null, ENTER_THE_PORT));
		String name = JOptionPane.showInputDialog(null, "Ingrese su nombre de usuario");
		String path;
		fileManager = new FileManager();
		mainWindow = new MainWindow(this);
		try {
			path = mainWindow.setVisibleFileChooser();
			client = new Client(ip, port, name, path);
			sendArchives();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainWindow.setVisible(true);
	}

	private void sendArchives() {
		try {
			fileManager.writeFile(client.getArchives());
			client.sendArchives();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (Events.valueOf(e.getActionCommand())) {
			
		}
	}
}