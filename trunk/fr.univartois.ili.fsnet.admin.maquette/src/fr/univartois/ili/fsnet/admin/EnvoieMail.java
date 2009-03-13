package fr.univartois.ili.fsnet.admin;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EnvoieMail {

	private static final String HOST_SMTP = "smtp.gmail.com";

	private static final String USER = "mojacojava@gmail.com";

	private static final String PWD = "testjava";
	private Properties props;
	private Authenticator auth;

	public EnvoieMail() {
		// paramettrage du host et adresse smtp
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

	public void postMail(String liste[], String sujet, String message,
			String file) throws MessagingException {

		Session session = Session.getDefaultInstance(props, auth);

		session.setDebug(false);

		// creation de message
		Message msg = new MimeMessage(session);

		InternetAddress[] addressTo = new InternetAddress[liste.length];
		for (int i = 0; i < liste.length; i++) {
			addressTo[i] = new InternetAddress(liste[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		msg.setSubject(sujet);
		msg.setContent(message, "text/plain");
		msg.setFileName(file);
		Transport.send(msg);
	}

	// **********************************************************************************

	public void envoyerMessage(String liste[], String sujet, String message)
			throws MessagingException {

		Session session = Session.getDefaultInstance(props, auth);

		session.setDebug(false);

		// creation de message
		Message msg = new MimeMessage(session);

		InternetAddress[] destination = new InternetAddress[liste.length];
		for (int i = 0; i < liste.length; i++) {
			destination[i] = new InternetAddress(liste[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, destination);
		msg.setSubject(sujet);
		msg.setContent(message, "text/plain");

		Transport.send(msg);
	}

	// ***********************************************************************************

	/**
	 * une fonction qui permet l'authentifcation au pr�s du serveur mail
	 */
	private class AuthenticationSMTP extends javax.mail.Authenticator {

		public PasswordAuthentication getPasswordAuthentication() {
			String username = USER;
			String password = PWD;
			return new PasswordAuthentication(username, password);
		}
	}

}
