package views;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class MainWindow extends JFrame{

	public static final String EXCEPTION_FILE_CHOOSER_TEXT = "Please select a directory";
	public static final String TITLE_TEXT = "Archives";
	private static final long serialVersionUID = 1L;
	public static final int HEIGHT_FRAME = 500;
	public static final int WIDTH_FRAME = 400;

	public MainWindow(ActionListener actionListener) {
		setTitle(TITLE_TEXT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(WIDTH_FRAME, HEIGHT_FRAME));
	}
	
	public String setVisibleFileChooser() throws Exception{
		String path= "";
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = jFileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = jFileChooser.getSelectedFile();
			path = file.getPath();
		}else if(result == JFileChooser.CANCEL_OPTION){
			new Exception(EXCEPTION_FILE_CHOOSER_TEXT);
		}
		return path;
	}
}