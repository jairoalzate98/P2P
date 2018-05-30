package controller;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import comunication.Server;
import views.MainWindow;

public class Controller {
	
	private Server server;
	private Timer timer;
	private MainWindow mainWindow;

	public Controller() {
		int port = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el puerto"));
		try {
			server = new Server(port);
		} catch (NumberFormatException | HeadlessException | IOException e) {
			e.printStackTrace();
		}
		mainWindow = new MainWindow();
		timer = new Timer(10, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.setModel(server.getConnections());
			}
		});
		timer.start();
	}
}