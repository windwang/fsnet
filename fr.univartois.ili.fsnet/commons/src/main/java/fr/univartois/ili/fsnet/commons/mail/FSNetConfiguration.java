package fr.univartois.ili.fsnet.commons.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FSNetConfiguration {

	private static final String MAIL_FROM = "fsnet.mail.from";
	private static final String ENABLE_TLS = "mail.smtp.starttls.enable";
	private static final String SMTP_HOST = "mail.smtp.host";
	private static final String SMTP_USER = "mail.smtp.user";
	private static final String SMTP_PASSWORD = "mail.smtp.password";
	private static final String FSNET_WEB_ADDRESS = "fsnet.web.address";
	private static final String ENABLE_AUTHENTICATION = "mail.smtp.auth";
	private static final String SMTP_PORT = "mail.smtp.port";
	private static final String SSL_KEY = "mail.smtp.socketFactory.class";
	private static final String SSL_ENABLED_VALUE = "javax.net.ssl.SSLSocketFactory";
	private static final String MAIl_CONFIGURATION_DONE = "fsnet.mail.isConfigured";

	private static final FSNetConfiguration instance;

	public static final FSNetConfiguration getInstance() {
		return instance;
	}

	private static String CONFIGURATION_FILE_PATH;

	private Properties properties = new Properties();

	static {
		CONFIGURATION_FILE_PATH = System.getProperty("user.home");
		CONFIGURATION_FILE_PATH += "/.FsnetConfiguration";
		instance = new FSNetConfiguration();
	}

	private FSNetConfiguration() {
		loadConfiguration();
	}

	public Properties getFSNetConfiguration() {
		return properties;
	}

	public void save() {
		File f = new File(CONFIGURATION_FILE_PATH);
		try {
			f.createNewFile();
			properties.storeToXML(new FileOutputStream(f), null);
		} catch (FileNotFoundException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		} catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}
	}

	private final void loadConfiguration() {
		File conf = new File(CONFIGURATION_FILE_PATH);
		// if no exists we create the file
		if (!conf.exists()) {
			try {
				properties.storeToXML(new FileOutputStream(conf), null);
			} catch (IOException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
			}
		} else {
			try {
				properties.loadFromXML(new FileInputStream(conf));
			} catch (FileNotFoundException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
			} catch (IOException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
			}
		}
	}

	public void reset() {
		properties.clear();
		save();
	}

	public void enableTLS() {
		properties.setProperty(ENABLE_TLS, "true");
	}

	public void disableTLS() {
		properties.setProperty(ENABLE_TLS, "false");
	}

	public boolean isTLSEnabled() {
		return "true".equals(properties.getProperty(ENABLE_TLS));
	}

	public void enableSSL() {
		properties.setProperty(SSL_KEY, SSL_ENABLED_VALUE);
	}

	public void disableSSL() {
		properties.remove(SSL_KEY);
	}

	public boolean isSSLEnabled() {
		return SSL_ENABLED_VALUE.equals(properties.getProperty(SSL_KEY));
	}

	public void enableAuthentication(String username, String password) {
		properties.setProperty(ENABLE_AUTHENTICATION, "true");
		properties.setProperty(SMTP_USER, username);
		properties.setProperty(SMTP_PASSWORD, password);
	}

	public void disableAuthentication() {
		properties.setProperty(ENABLE_AUTHENTICATION, "false");
		properties.remove(SMTP_USER);
		properties.remove(SMTP_PASSWORD);
	}

	public boolean isAuthenticationEnabled() {
		return "true".equals(properties.getProperty(ENABLE_AUTHENTICATION));
	}

	public String getUsername() {
		return properties.getProperty(SMTP_USER);
	}

	public String getPassword() {
		return properties.getProperty(SMTP_PASSWORD);
	}

	public String getSMTPHost() {
		return properties.getProperty(SMTP_HOST);
	}

	public void setSMTPHost(String SMTPHost) {
		properties.setProperty(SMTP_HOST, SMTPHost);
	}

	public String getSMTPPort() {
		return properties.getProperty(SMTP_PORT);
	}

	public void setSMTPPort(int SMTPPort) {
		properties.setProperty(SMTP_PORT, Integer.toString(SMTPPort));
	}

	public String getFrom() {
		return properties.getProperty(MAIL_FROM);
	}

	public void setFrom(String mailAddress) {
		properties.setProperty(MAIL_FROM, mailAddress);
	}

	public String getFSNetWebAddress() {
		return properties.getProperty(FSNET_WEB_ADDRESS);
	}

	public void setFSNetWebAddress(String FSNetWebAddress) {
		properties.setProperty(FSNET_WEB_ADDRESS, FSNetWebAddress);
	}

	public void setMailConfigured(boolean isConfigured) {
		if (isConfigured) {
			properties.setProperty(MAIl_CONFIGURATION_DONE, "true");
		} else {
			properties.setProperty(MAIl_CONFIGURATION_DONE, "false");
		}
	}

	public boolean isMailConfigured() {
		return "true".equals(properties.getProperty(MAIl_CONFIGURATION_DONE));
	}

}
