package fr.univartois.ili.fsnet.commons.mail;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * @author FSNet
 *
 */
public class Mail {

	private final Message message;

	/**
	 * @param message
	 */
	Mail(Message message) {
		this.message = message;

	}

	/**
	 * @param recipient
	 */
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

	/**
	 * @param obj
	 */
	public void setContent(Object obj) {
		try {
			message.setContent(obj,"text/html; charset=UTF-8");
		} catch (MessagingException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}
	}

	/**
	 * @param subject
	 */
	public void setSubject(String subject) {
		try {
			message.setSubject(subject);
		} catch (MessagingException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}
	}
	
	/**
	 * @return a Message
	 */
	Message getMessage() {
		return message;
	}

}
