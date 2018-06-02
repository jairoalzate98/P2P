package models;

import java.util.ArrayList;

public class User {
	
	private String name;
	private ArrayList<String> archives;
	
	public User(String name, ArrayList<String> archives) {
		this.name = name;
		this.archives = archives;
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getArchives() {
		return archives;
	}

	@Override
	public String toString() {
		return "User [name=" + name + "]";
	}
}