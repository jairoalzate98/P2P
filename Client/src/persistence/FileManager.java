package persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {

	public static final File FILE = new File("archives.txt");

	public void writeFile(ArrayList<String> archives) throws IOException{
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE));
		for (String archive : archives) {
			bufferedWriter.write(archive);
			bufferedWriter.newLine();
		}
		bufferedWriter.close();
	}
}