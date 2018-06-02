package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import comunication.Client;
import models.User;
import persistence.FileManager;
import views.MainWindow;

public class Controller implements ActionListener{

	public static final String ENTER_THE_PORT = "Ingrese el puerto";
	public static final String ENTER_THE_IP = "Ingrese la ip";
	private Client client;
	private MainWindow mainWindow;
	private FileManager fileManager;
	
	public Controller() {
		fileManager = new FileManager();
		mainWindow = new MainWindow(this);
		String ip = JOptionPane.showInputDialog(mainWindow, ENTER_THE_IP);
		int port = Integer.parseInt(JOptionPane.showInputDialog(mainWindow, ENTER_THE_PORT));
		String name = JOptionPane.showInputDialog(mainWindow, "Ingrese su nombre de usuario");
		String path;
		try {
			path = mainWindow.setVisibleFileChooser();
			client = new Client(ip, port, name, path, this);
			sendArchives();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainWindow.setVisible(true);
	}

	private void sendArchives() {
		try {
			client.getArchivesUser();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readUsers() {
		try {
			ArrayList<User> users = fileManager.read();
			for (User user : users) {
				System.out.println(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (Events.valueOf(e.getActionCommand())) {
			
		}
	}
}