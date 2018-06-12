package views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import controllers.Controller;
import controllers.Events;
import models.User;

public class JPanelNorth extends JPanel{

	private static final long serialVersionUID = 1L;
	private JComboBox<User> jcbUsers; 

	public JPanelNorth(Controller controller) {
		setBackground(Color.WHITE);
		setLayout(new FlowLayout());
		jcbUsers = new JComboBox<>();
		add(jcbUsers);
		JButton jbtnAccept = new JButton("Search");
		jbtnAccept.setActionCommand(Events.SEARCH.toString());
		jbtnAccept.addActionListener(controller);
		add(jbtnAccept);
	}
	
	public void setUsers(ArrayList<User> users) {
		jcbUsers.removeAllItems();
		for (User user : users) {
			jcbUsers.addItem(user);
		}
	}
	
	public User getUserSelected() {
		return (User) jcbUsers.getSelectedItem();
	}
}