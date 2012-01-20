package fr.univartois.ili.fsnet.mobile.services.client.rest;

import java.util.List;

public class RestMeetingList {

	private List<RestMeeting> meetings;

	public RestMeetingList() {

	}

	public RestMeetingList(List<RestMeeting> meetings) {
		this.meetings = meetings;
	}

	public void setMeetings(List<RestMeeting> meetings) {
		this.meetings = meetings;
	}

	public List<RestMeeting> getMeetings() {
		return meetings;
	}
	public RestMeeting get(int location)
	{
		return meetings.get(location);
	}
	public int size()
	{
		return meetings.size();
	}
}
