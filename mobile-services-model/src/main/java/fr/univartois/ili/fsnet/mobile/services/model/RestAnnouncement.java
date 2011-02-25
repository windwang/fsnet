package fr.univartois.ili.fsnet.mobile.services.model;

import fr.univartois.ili.fsnet.entities.Announcement;


public class RestAnnouncement {

	private String title;
	private String from;
	private int meetingId;

	public RestAnnouncement() {
	}

	public RestAnnouncement(Announcement announce) {
		this.setMeetingId(announce.getId());
		this.from = announce.getCreator().getName()+" "+announce.getCreator().getFirstName();
		this.setTitle(announce.getTitle());
	}

	

	/**@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final RestPrivateMessage other = (RestPrivateMessage) obj;
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
	}**/

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 19 * hash + (this.title != null ? this.title.hashCode() : 0);
		hash = 19 * hash + (this.from != null ? this.from.hashCode() : 0);
		hash = 19 * hash + this.meetingId;
		return hash;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}

	public int getMeetingId() {
		return meetingId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
}
