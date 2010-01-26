package fr.univartois.ili.fsnet.admin.utils;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class FSNetMailer {

	private static final FSNetMailer instance;

	public static final FSNetMailer getInstance() {
		return instance;
	}

	private Properties properties;

	static {
		instance = new FSNetMailer();
	}

	private FSNetMailer() {
		properties = FSNetConfiguration.getInstance().getFSNetConfiguration();
	}

	public boolean isReadyToSendMail() {
		return FSNetConfiguration.getInstance().isMailConfigured();
	}

	public void sendMail(Mail mail) {
		FSNetConfiguration conf = FSNetConfiguration.getInstance();
		Session session = Session.getDefaultInstance(properties, null);
		String smtpHost = FSNetConfiguration.getInstance().getSMTPHost();
		String user = conf.getUsername();
		String password = (String) conf.getPassword();
		Transport transport;
		try {
			transport = session.getTransport("smtp");
			transport.connect(smtpHost, user, password);
			Message message = mail.getMessage();
			message.setFrom(new InternetAddress(conf.getFrom()));
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (NoSuchProviderException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		} catch (MessagingException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}
	}

	public Mail createMail() {
		Session session = Session.getDefaultInstance(properties, null);
		MimeMessage message = new MimeMessage(session);
		Mail mail = new Mail(message);
		return mail;
	}

}
