package fr.univartois.ili.fsnet.entities;

import javax.persistence.Entity;

@Entity
public class Configuration {
	
	private boolean authenticationEnabled;
	
	private boolean SSLEnabled;
		
	private boolean TLSEnabled;

	private String SMTPUsername;
	
	private String SMTPPassword;
	
	private String SMTPHost;
	
	private int SMTPPort;
	
	private String MailFrom;
	
	private String URL;

	public boolean isAuthenticationEnabled() {
		return authenticationEnabled;
	}

	public void setAuthenticationEnabled(boolean authenticationEnabled) {
		this.authenticationEnabled = authenticationEnabled;
	}

	public boolean isSSLEnabled() {
		return SSLEnabled;
	}

	public void setSSLEnabled(boolean sSLEnabled) {
		SSLEnabled = sSLEnabled;
	}

	public boolean isTLSEnabled() {
		return TLSEnabled;
	}

	public void setTLSEnabled(boolean tLSEnabled) {
		TLSEnabled = tLSEnabled;
	}

	public String getSMTPUsername() {
		return SMTPUsername;
	}

	public void setSMTPUsername(String sMTPUsername) {
		SMTPUsername = sMTPUsername;
	}

	public String getSMTPPassword() {
		return SMTPPassword;
	}

	public void setSMTPPassword(String sMTPPassword) {
		SMTPPassword = sMTPPassword;
	}

	public String getSMTPHost() {
		return SMTPHost;
	}

	public void setSMTPHost(String sMTPHost) {
		SMTPHost = sMTPHost;
	}

	public int getSMTPPort() {
		return SMTPPort;
	}

	public void setSMTPPort(int sMTPPort) {
		SMTPPort = sMTPPort;
	}

	public String getMailFrom() {
		return MailFrom;
	}

	public void setMailFrom(String mailFrom) {
		MailFrom = mailFrom;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}
	
	
	
}
