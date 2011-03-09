package fr.univartois.ili.fsnet.mobile.services.model;

import java.util.List;

public class RestAnnouncementList {

	private List<RestAnnouncement> announcements;
	
	public RestAnnouncementList(){
	}
	
	public RestAnnouncementList(List<RestAnnouncement> announcements){
		this.announcements = announcements;
	}

	public void setAnnouncements(List<RestAnnouncement> announcements) {
		this.announcements = announcements;
	}

	public List<RestAnnouncement> getAnnouncements() {
		return announcements;
	}

}
