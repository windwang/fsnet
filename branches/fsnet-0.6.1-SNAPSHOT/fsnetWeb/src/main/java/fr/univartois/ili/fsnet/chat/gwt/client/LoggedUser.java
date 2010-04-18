package fr.univartois.ili.fsnet.chat.gwt.client;

public class LoggedUser {

	private int id;

	private String name;

	private UserInfosPanel infoPanel;

	private UserPicture userMiniature;
	private UserPicture userAvatar;

	public LoggedUser(int userId, String name) {
		this.id = userId;
		this.name = name;
		infoPanel = new UserInfosPanel(name, userId);
		userMiniature = new UserPicture(userId, name, true);
		userAvatar = new UserPicture(userId, name, false);
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

	public UserPicture getUserMiniature() {
		return userMiniature;
	}

	public UserPicture getUserAvatar() {
		return userAvatar;
	}

	public UserInfosPanel getInfoPanel() {
		return infoPanel;
	}

}
