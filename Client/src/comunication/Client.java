package comunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import controllers.Controller;

public class Client extends Thread {

	private Socket connection;
	private DataInputStream input;
	private DataOutputStream output;
	private boolean stop;
	public final static Logger LOGGER = Logger.getGlobal();
	private String name;
	private ArrayList<String> archives;
	private String path;
	private Controller controller;

	public Client(String ip, int port, String name, String path, Controller controller) throws IOException {
		this.path = path;
		this.controller = controller;
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
			String response = null;
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
		if (response.equals(Request.SEND_CLIENTS.toString())) {
			int size = Integer.parseInt(input.readUTF());
			readArchive(size);
//			controller.readUsers();
		}
	}

	private void readArchive(double size) throws IOException {
		FileOutputStream fos = new FileOutputStream("users.xml");
		byte[] buffer = new byte[4096];
		int filesize = (int)size;
		int read = 0;
		int remaining = filesize;
		while((read = input.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
			remaining -= read;
			fos.write(buffer, 0, read);
		}
		fos.close();
	}

	public void getArchivesUser() throws IOException{
		File file = new File(path);
		File[] files = file.listFiles();
		for (File archive : files) {
			if (!(archive.isDirectory())) {
				archives.add(archive.getAbsolutePath());
			}
		}
		sendArchives();
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
		String send = "";
		for (String string : archives) {
			send += string + ",";
		}
		output.writeUTF(send);
	}
}