package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.Timer;

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
	private Timer timer;
	
	public Controller() {
		fileManager = new FileManager();
		mainWindow = new MainWindow(this);
		String ip = JOptionPane.showInputDialog(mainWindow, ENTER_THE_IP);
		int port = Integer.parseInt(JOptionPane.showInputDialog(mainWindow, ENTER_THE_PORT));
		String name = JOptionPane.showInputDialog(mainWindow, "Ingrese su nombre de usuario");
		String path;
		try {
			path = mainWindow.setVisibleFileChooser();
			client = new Client(ip, port, name, path);
			sendArchives();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainWindow.setVisible(true);
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				readUsers();
			}
		});
		timer.start();
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
			mainWindow.setUsers(users);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (Events.valueOf(e.getActionCommand())) {
		case SEARCH:
			search();
			break;
		case DOWNLOAD:
			download();
			break;
		}
	}

	private void download() {
		String archive = mainWindow.getArchiveToDownload();
		User u = mainWindow.getUserSelected();
		try {
			client.sendArchiveToDownload(archive, u.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void search() {
		ArrayList<String> archives = new ArrayList<>();
		ArrayList<String> files = mainWindow.getUserSelected().getArchives();
		for (String string : files) {
			int lastIndex = string.lastIndexOf("\\");
			String name = string.substring(lastIndex + 1, string.length());
			archives.add(name);
			mainWindow.setModel(archives);
		}
	}
}