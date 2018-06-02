package comunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import persistence.FileManager;

public class ThreadSocket extends Thread{


	private Socket connection;
	private DataInputStream input;
	private DataOutputStream output;
	private boolean stop;
	private String name;
	private String[] archives;
	private Server server;

	public ThreadSocket(Socket socket, Server server) throws IOException {
		this.connection = socket;
		this.server = server;
		name = "";
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
		start();
	}

	public boolean isStop() {
		return stop;
	}

	public String getNameUser() {
		return name;
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				String request = input.readUTF();
				if (request != null) {
					if (name.equals("")) {
						manageRequest(request);
					}else{
						saveArchives(request);
					}
				}
			} catch (IOException e) {
				Server.LOGGER.log(Level.INFO, "Usuario " + name + " con direccion ip = " + connection.getInetAddress().getHostAddress() + " desconectado");
				stop = true;
				try {
					server.sendUser();
					try {
						server.sendArchivesToUser();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} catch (ParserConfigurationException | TransformerFactoryConfigurationError
						| TransformerException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public String[] getArchives() {
		return archives;
	}

	private void saveArchives(String request)  {
		archives = request.split(",");
		Server.LOGGER.log(Level.INFO, "Archivos recibidos de " + name);
		try {
			server.sendUser();
			try {
				server.sendArchivesToUser();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException | TransformerFactoryConfigurationError | TransformerException e) {
			e.printStackTrace();
		}
	}

	private void manageRequest(String request)throws IOException {
		this.name = request;
		Server.LOGGER.log(Level.INFO, "Conexion con  -> " + request + " con la direccion ip -> " + connection.getInetAddress().getHostAddress());
	}

	public void sendArchives() throws IOException {
		output.writeUTF(Request.SEND_CLIENTS.toString());
		if (input.readUTF().equals(Request.PETITION_ACCEPT.toString())) {
			waitForNewConnectionArchive();
		}
	}

	private void waitForNewConnectionArchive() throws IOException {
		File file = FileManager.NEW_FILE;
		output.writeUTF(String.valueOf(file.length()));
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[4096];
		while (fis.read(buffer) > 0) {
			output.write(buffer);
		}
		fis.close();
	}

	@Override
	public String toString() {
		return "Conectado con " + name + " con direccion ip " + connection.getInetAddress().getHostAddress();
	}
}