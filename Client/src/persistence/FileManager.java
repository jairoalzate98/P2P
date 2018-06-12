package persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import models.User;

public class FileManager {
	
	private static final String ARCHIVE = "Archive";
	public static final String NAME = "Name";
	public static final String ENTITY_NAME = "User";

	public ArrayList<User> read() throws IOException, SAXException, ParserConfigurationException{
		ArrayList<User> userList = new ArrayList<>();
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("users.xml"));
		Element root = document.getDocumentElement();;
		NodeList list = root.getElementsByTagName(ENTITY_NAME);
		ArrayList<String> archives = new ArrayList<>();
		for (int i = 0; i < list.getLength(); i++) {
			Element user = (Element) list.item(i);
			String name = user.getElementsByTagName(NAME).item(0).getTextContent();
			NodeList a = user.getElementsByTagName(ARCHIVE);
			for (int j = 0; j < a.getLength(); j++) {
				archives.add(a.item(i).getTextContent());
			}
			userList.add(new User(name, archives));
		}
		return userList;
	}
}