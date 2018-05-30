package comunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;

public class ThreadSocket extends Thread{


	private Socket connection;
	private DataInputStream input;
	private DataOutputStream output;
	private boolean stop;
	private String name;
	private ArrayList<String> archives; 

	public ThreadSocket(Socket socket) throws IOException {
		this.connection = socket;
		archives = new ArrayList<>();
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
					manageRequest(request);
				}
			} catch (IOException e) {
				Server.LOGGER.log(Level.INFO, "Usuario " + name + " con direccion ip = " + connection.getInetAddress().getHostAddress() + " desconectado");
				stop = true;
			}
		}
	}

	private void manageRequest(String request) throws NumberFormatException, IOException {
		this.name = request;
		Server.LOGGER.log(Level.INFO, "Conexion con  -> " + request + " con la direccion ip -> " + connection.getInetAddress().getHostAddress());
		int size = Integer.parseInt(input.readUTF() + 1);
		FileOutputStream fos = new FileOutputStream("words.txt");
		byte[] buffer = new byte[4096];
		int read = 0;
		int remaining = size;
		while((read = input.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
			remaining -= read;
			fos.write(buffer, 0, read);
		}
		fos.close();
		Server.LOGGER.log(Level.INFO, "Se recibieron los archivos");
	}

	@Override
	public String toString() {
		return "Conectado con " + name + " con direccion ip " + connection.getInetAddress().getHostAddress();
	}
}