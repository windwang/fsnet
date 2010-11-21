package fr.univartois.ili.fsnet.mobile.services.model;

/**
 * @author m
 *
 */
public class AuthInfo {

	private String login;
	
	private String password;
	
	public AuthInfo() {}
	
	public AuthInfo(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AuthInfo [login=" + login + ", password=" + password + "]";
	}
	
}
