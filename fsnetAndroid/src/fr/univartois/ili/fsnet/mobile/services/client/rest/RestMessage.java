package fr.univartois.ili.fsnet.mobile.services.client.rest;


/**
 * 
 * @author william vermersch
 *
 */

public class RestMessage {

	private String subject;
	private String from;
	private int messageId;

	public RestMessage() {
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public int getMessageId() {
		return messageId;
	}

	public String getSubject() {
		return subject;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final RestMessage other = (RestMessage) obj;
		if ((this.subject == null) ? (other.subject != null) : !this.subject
				.equals(other.subject)) {
			return false;
		}
		if ((this.from == null) ? (other.from != null) : !this.from
				.equals(other.from)) {
			return false;
		}
		if (this.messageId != other.messageId) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 19 * hash + (this.subject != null ? this.subject.hashCode() : 0);
		hash = 19 * hash + (this.from != null ? this.from.hashCode() : 0);
		hash = 19 * hash + this.messageId;
		return hash;
	}
}
