package fr.univartois.ili.fsnet.admin;

import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {

	private static final String HOST_SMTP = "smtp.gmail.com";

	private static final String USER = "mojacojava@gmail.com";

	private static final String PWD = "testjava";
	private Properties props;
	private Authenticator auth;

	public SendMail() {
		// Setting the host address and smtp address
		props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", HOST_SMTP);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		java.security.Security
				.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		auth = new AuthenticationSMTP();

	}

	private Message createMessage(List<String> liste, String sujet,
			String message) throws MessagingException {
		Session session = Session.getDefaultInstance(props, auth);

		session.setDebug(false);

		// creation message
		Message msg = new MimeMessage(session);

		InternetAddress[] destination = new InternetAddress[liste.size()];
		for (int i = 0; i < liste.size(); i++) {
			destination[i] = new InternetAddress(liste.get(i));
		}
		msg.setRecipients(Message.RecipientType.TO, destination);
		msg.setSubject(sujet);
		msg.setContent(message, "text/plain");
		return msg;
	}

	public void sendMessageWithAttachements(List<String> liste, String sujet, String message,
			String file) throws MessagingException {

		Message msg = createMessage(liste, sujet, message);
		msg.setFileName(file);
		Transport.send(msg);
	}

	// **********************************************************************************

	public void sendMessage(List<String> liste, String sujet, String message)
			throws MessagingException {

		Message msg = createMessage(liste, sujet, message);
		Transport.send(msg);
	}

	// ***********************************************************************************

	/**
	 * a function that allows the authentication to the mail server
	 */
	private class AuthenticationSMTP extends javax.mail.Authenticator {

		public PasswordAuthentication getPasswordAuthentication() {
			String username = USER;
			String password = PWD;
			return new PasswordAuthentication(username, password);
		}
	}

}
