package comunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Thread {

	private Socket connection;
	private DataInputStream input;
	private DataOutputStream output;
	private boolean stop;
	public final static Logger LOGGER = Logger.getGlobal();
	private String name;

	public Client(String ip, int port, String name) throws IOException {
		this.name = name;
		this.connection = new Socket(ip, port);
		LOGGER.log(Level.INFO, "Conexion iniciada en el puerto -> " + port + " y en la ip -> " + ip);
		input = new DataInputStream(connection.getInputStream());
		output = new DataOutputStream(connection.getOutputStream());
		output.writeUTF(name);
		start();
	}

	@Override
	public void run() {
		while (!stop) {
			String response;
			try {
				response = input.readUTF();
				if (response != null) {
					manageResponse(response);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void manageResponse(String response) throws IOException {
		LOGGER.log(Level.INFO, "Mensaje Recibido" + response);
	}

	public void requestMessage(String message) throws IOException{
		output.writeUTF(message);
	}
}
