package fr.univartois.ili.fsnet.admin.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Mail {

	private final Message message;

	Mail(Message message) {
		this.message = message;

	}

	public void addRecipient(String recipient) {
		Logger.getAnonymousLogger().info("Add recipient :" + recipient);
		try {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					recipient));
		} catch (AddressException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		} catch (MessagingException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}
	}

	public void setContent(Object obj) {
		try {
			message.setContent(obj, "text/html");
		} catch (MessagingException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}
	}

	public void setSubject(String subject) {
		try {
			message.setSubject(subject);
		} catch (MessagingException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}
	}
	
	Message getMessage() {
		return message;
	}

}
