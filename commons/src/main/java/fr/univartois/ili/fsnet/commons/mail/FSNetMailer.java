package fr.univartois.ili.fsnet.commons.mail;

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

/**
 * @author FSNet
 * 
 */
public class FSNetMailer {

	private static final FSNetMailer instance;

	/**
	 * @return a instance of FSNetMailer
	 */
	public static final FSNetMailer getInstance() {
		return instance;
	}

	static {
		instance = new FSNetMailer();
	}

	/**
	 * @param mail
	 */
	public void sendMail(Mail mail) {
		FSNetConfiguration conf = FSNetConfiguration.getInstance();
		Properties properties = conf.getFSNetConfiguration();
		Session session = Session.getDefaultInstance(properties, null);
		String smtpHost = properties
				.getProperty(FSNetConfiguration.SMTP_HOST_KEY);
		String user = properties.getProperty(FSNetConfiguration.SMTP_USER_KEY);
		String password = properties
				.getProperty(FSNetConfiguration.SMTP_PASSWORD_KEY);
		String from = properties.getProperty(FSNetConfiguration.MAIL_FROM_KEY);
		Transport transport;
		try {
			transport = session.getTransport("smtp");
			transport.connect(smtpHost, user, password);
			Message message = mail.getMessage();
			message.setFrom(new InternetAddress(from));
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (NoSuchProviderException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		} catch (MessagingException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}
	}

	/**
	 * @return Mail
	 */
	public Mail createMail() {
		FSNetConfiguration conf = FSNetConfiguration.getInstance();
		Properties properties = conf.getFSNetConfiguration();
		Session session = Session.getDefaultInstance(properties, null);
		MimeMessage message = new MimeMessage(session);
		return new Mail(message);
	}

}
