package comunication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import persistence.FileManager;

public class Server extends Thread{
	
	private ServerSocket server;	
	private int port;
	private boolean stop;
	public final static Logger LOGGER = Logger.getGlobal();
	private ArrayList<ThreadSocket> connections;
	private FileManager fileManager;
	
	public Server(int port) throws IOException {
		fileManager = new FileManager();
		connections = new ArrayList<>();
		this.port = port;
		server = new ServerSocket(port);
		start();
		LOGGER.log(Level.INFO, "Servidor inicado en puerto: " + this.port);
	}

	public int getPort() {
		return port;
	}
	
	@Override
	public void run() {
		while (!stop) {
			Socket connection;
			try {
				connection = server.accept();
				connections.add(new ThreadSocket(connection, this));
				LOGGER.log(Level.INFO, "Conexion aceptada: " + connection.getInetAddress().getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<ThreadSocket> getConnections() {
		return connections;
	}
	
	public void sendUser() throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
		fileManager.writeReport(connections);
	}
	
	public void sendArchivesToUser() throws IOException {
		for (ThreadSocket threadSocket : connections) {
			threadSocket.sendArchives();
		}
	}
}