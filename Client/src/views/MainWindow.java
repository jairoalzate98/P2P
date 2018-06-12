package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;

import controllers.Controller;
import controllers.Events;
import models.User;

public class MainWindow extends JFrame{

	public static final String EXCEPTION_FILE_CHOOSER_TEXT = "Please select a directory";
	public static final String TITLE_TEXT = "Archives";
	private static final long serialVersionUID = 1L;
	public static final int HEIGHT_FRAME = 500;
	public static final int WIDTH_FRAME = 400;
	private JPanelNorth jPanelNorth;
	private JList<String> archiveList;
	private DefaultListModel<String> archiveModel;

	public MainWindow(Controller actionListener) {
		setTitle(TITLE_TEXT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(WIDTH_FRAME, HEIGHT_FRAME));
		setLocationRelativeTo(null);
		jPanelNorth = new JPanelNorth(actionListener);
		add(jPanelNorth, BorderLayout.NORTH);
		archiveModel = new DefaultListModel<>();
		archiveList = new JList<>(archiveModel);
		add(archiveList, BorderLayout.CENTER);
		JButton jbtnAccept = new JButton("Download");
		jbtnAccept.setActionCommand(Events.DOWNLOAD.toString());
		jbtnAccept.addActionListener(actionListener);
		add(jbtnAccept, BorderLayout.SOUTH);
	}
	
	public void setUsers(ArrayList<User> users) {
		jPanelNorth.setUsers(users);
	}
	
	public void setModel(ArrayList<String> archives) {
		archiveModel.clear();
		for (String string : archives) {
			archiveModel.addElement(string);
		}
	}
	
	public String getArchiveToDownload() {
		return archiveList.getSelectedValue();
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
	
	public User getUserSelected() {
		return jPanelNorth.getUserSelected();
	}
}