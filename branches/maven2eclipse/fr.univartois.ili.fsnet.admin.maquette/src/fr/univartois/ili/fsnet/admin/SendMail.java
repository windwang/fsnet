package fr.univartois.ili.fsnet.admin;

import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {
	
	private static final String TIMEOUT = "1000";
	private Properties props;
	private Authenticator auth;
	private String addressHost;
	private String pwdAddressHost;

	public SendMail(){}
	
	public SendMail(String serverSmtp,String addressHost, String pwdAddressHost, String port) {
		// Setting the host address and smtp address
		props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", serverSmtp);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.timeout", TIMEOUT);
		this.addressHost = addressHost;
		this.pwdAddressHost = pwdAddressHost;
		java.security.Security
				.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		auth = new AuthenticationSMTP();

	}
	
	public String getAddressHost(){
		return addressHost;
	}
	
	public String getPwdAddressHost(){
		return pwdAddressHost;
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
			return new PasswordAuthentication(addressHost, pwdAddressHost);
		}
	}

}
