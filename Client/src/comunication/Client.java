package comunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import persistence.FileManager;

public class Client extends Thread {

	private Socket connection;
	private DataInputStream input;
	private DataOutputStream output;
	private boolean stop;
	public final static Logger LOGGER = Logger.getGlobal();
	private String name;
	private ArrayList<String> archives;

	public Client(String ip, int port, String name, String path) throws IOException {
		archives = new ArrayList<>();
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
				LOGGER.log(Level.INFO, "Servidor desconectado");
				stop = true;
			}
		}
	}

	private void manageResponse(String response) throws IOException {
		LOGGER.log(Level.INFO, "Mensaje Recibido" + response);
	}

	public void getArchives(String path){
		File file = new File(path);
		File[] files = file.listFiles();
		for (File archive : files) {
			archives.add(archive.getAbsolutePath());
		}
	}

	public String getNameUser() {
		return name;
	}

	public ArrayList<String> getArchives() {
		return archives;
	}

	public void requestMessage(String message) throws IOException{
		output.writeUTF(message);
	}

	public void sendArchives() throws IOException {
		File file = FileManager.FILE;
		output.writeUTF(String.valueOf(file.length()));
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[4096];
		while (fis.read(buffer) > 0) {
			output.write(buffer);
		}
		fis.close();
	}
}