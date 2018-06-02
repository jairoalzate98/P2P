package views;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

import comunication.ThreadSocket;

public class MainWindow extends JFrame {
	
	public static final int HEIGHT_FRAME = 500;
	public static final int WIDTH_FRAME = 400;
	public static final String TITLE_TEXT = "Server";
	private static final long serialVersionUID = 1L;
	private JList<ThreadSocket> sockets;
	private DefaultListModel<ThreadSocket> modelSockets;

	public MainWindow() {
		setTitle(TITLE_TEXT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(WIDTH_FRAME, HEIGHT_FRAME));
		setLocationRelativeTo(null);
		modelSockets = new DefaultListModel<>();
		sockets = new JList<>(modelSockets);
		add(sockets, BorderLayout.CENTER);
	}
	
	public void setModel(ArrayList<ThreadSocket> sockets){
		modelSockets.clear();
		for (ThreadSocket threadSocket : sockets) {
			modelSockets.addElement(threadSocket);
		}
	}
}