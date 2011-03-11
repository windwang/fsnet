package fr.univartois.ili.fsnet.mobile.services.client.data;
/**
 * 
 * @author alexandre thery
 *
 */
public class InfoSettings {

	private String login;
	private String password;
	private int minutes;
	private String serverUrl;
	public void setLogin(String login) {
		this.login = login;
	}
	public String getLogin() {
		return login;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public String getServerUrl() {
		return serverUrl;
	}
	public String toString()
	{
		return login+"\n"+password+"\n"+serverUrl+"\n"+minutes;
	}
	
}
