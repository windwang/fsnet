package fr.univartois.ili.fsnet.mobile.services.model;



public class RestAnnouncement {

	private String title;
	private String from;
	private int meetingId;

	public RestAnnouncement() {
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + meetingId;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		RestAnnouncement other = (RestAnnouncement) obj;
		if (from == null) {
			if (other.from != null){
				return false;
			}
		} else if (!from.equals(other.from)){
			return false;
		}
		if (meetingId != other.meetingId){
			return false;
		}
		if (title == null) {
			if (other.title != null){
				return false;
			}
		} else if (!title.equals(other.title)){
			return false;
		}
		return true;
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
