package fr.univartois.ili.fsnet.chat.gwt.client;

public class LoggedUser {
	
	private int id;
	
	private String name;
	
	private UserMiniature userMiniature;
	
	public LoggedUser(int id, String name) {
		this.id = id;
		this.name = name;
		userMiniature = new UserMiniature(id, name);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserMiniature getUserMiniature() {
		return userMiniature;
	}
	
}
