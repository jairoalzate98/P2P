package comunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

public class ThreadSocket extends Thread{


	private Socket connection;
	private DataInputStream input;
	private DataOutputStream output;
	private boolean stop;
	private String name;
	private String[] archives; 

	public ThreadSocket(Socket socket) throws IOException {
		this.connection = socket;
		name = "";
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
		start();
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
			}
		}
	}

	private void saveArchives(String request) {
		archives = request.split(",");
		Server.LOGGER.log(Level.INFO, "Archivos recibidos de " + name);
	}

	private void manageRequest(String request)throws IOException {
		this.name = request;
		Server.LOGGER.log(Level.INFO, "Conexion con  -> " + request + " con la direccion ip -> " + connection.getInetAddress().getHostAddress());
	}

	@Override
	public String toString() {
		return "Conectado con " + name + " con direccion ip " + connection.getInetAddress().getHostAddress();
	}
}