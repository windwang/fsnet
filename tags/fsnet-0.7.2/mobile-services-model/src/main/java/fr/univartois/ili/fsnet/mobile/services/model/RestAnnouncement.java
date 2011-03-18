package fr.univartois.ili.fsnet.mobile.services.model;



public class RestAnnouncement {

	private String title;
	private String from;
	private int meetingId;

	public RestAnnouncement() {
	}

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
