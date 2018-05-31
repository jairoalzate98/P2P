package persistence;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import comunication.ThreadSocket;

public class FileManager {
	
	public static final String YES = "yes";
	public static final String SPACE = "4";
	public static final String IDENT = "{http://xml.apache.org/xslt}indent-amount";
	public static final File NEW_FILE = new File("file.xml");

	/*public ArrayList<Game> read() throws Exception{
		ArrayList<Game> gameList = new ArrayList<>();
		File file = FILE;
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
		Element root = document.getDocumentElement();
		NodeList list = root.getElementsByTagName(TITLE_ENTITY);
		for (int i = 0; i < list.getLength(); i++) {
			Element game = (Element) list.item(i);
			String id = game.getAttribute(ID);
			String name = game.getElementsByTagName(NAME).item(0).getTextContent();
			String rating = getRating(game);
			String temp = game.getElementsByTagName(RELEASEDATE).item(0).getTextContent();
			String date = "";
			if (!temp.isEmpty() && !rating.isEmpty()) {
				date = Manager.getDate(temp);
				gameList.add(Manager.createGame(id, name, rating, date));	
			}	
		}
		return gameList;
	}
*/
	public void writeReport(ArrayList<ThreadSocket> sockets) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException{
		System.out.println(sockets.size());
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element games = document.createElement("Users");
		for (ThreadSocket socket : sockets) {
			if (!socket.isStop()) {
				Element information = createElement(document, socket);
				games.appendChild(information);
			}
		}
		document.appendChild(games);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, YES);
		transformer.setOutputProperty(IDENT, SPACE);
		transformer.transform(new DOMSource(document),new StreamResult(NEW_FILE));
	}
//	
	public Element createElement(Document document, ThreadSocket socket){
		Element element = document.createElement("User");
		Element name = document.createElement("Name");
		name.setTextContent(socket.getNameUser());
		element.appendChild(name);
		String[] archives = socket.getArchives();
		for (String string : archives) {
			Element archive = document.createElement("Archive");
			archive.setTextContent(string);
			element.appendChild(archive);
		}
		return element;
	}
}