package fr.univartois.ili.fsnet.mobile.services.client.rest;

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

	public int size() {
		return announcements.size();
	}

	public  RestAnnouncement get(int i) {
		return announcements.get(i);
	}

}
