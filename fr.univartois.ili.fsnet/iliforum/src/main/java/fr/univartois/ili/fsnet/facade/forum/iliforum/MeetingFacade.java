package fr.univartois.ili.fsnet.facade.forum.iliforum;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class MeetingFacade {
/**
 * @author mickael watrelot - micgamers@gmail.com
 */
	private final EntityManager em;

	public MeetingFacade(EntityManager em) {
		this.em = em;
	}

	/**
	 * Create a New Meeting
	 * @param member
	 * @param eventName
	 * @param eventDescription
	 * @param endDate
	 * @param isPrivate
	 * @param startDate
	 * @param address
	 * @param city
	 * @return the new meeting
	 */
	public Meeting createMeeting(SocialEntity member, String eventName,
			String eventDescription, Date endDate, Boolean isPrivate,
			Date startDate, String address, String city) {

		Meeting event = new Meeting(member, eventName, eventDescription,
				endDate, isPrivate, startDate, new Address(address, city));

		member.getInteractions().add(event);
		em.persist(event);
		return event;
	}
	
	
	/**
	 * 
	 * @param id
	 * @return 
	 */
	public Meeting getMeeting(int id){
		return em.find(Meeting.class, id);
	}

	/**
	 * Delete the Meeting with the id of the meeting meetingId
	 * @param meetingId
	 */
	public void deleteMeeting(String meetingId) {
		String eventId = meetingId;
		TypedQuery<Meeting> query = em.createQuery(
				"Select e from Meeting e where e.id = :eventId", Meeting.class);
		query.setParameter("eventId", Integer.parseInt(eventId));
		Meeting event = query.getSingleResult();
		em.remove(event);
		em.flush();
	}

	/**
	 * Search the meeting with the param searchStr
	 * @param searchStr
	 * @return
	 */
	public List<Meeting> searchMeeting(String searchStr) {
		String searchString = searchStr;

		List<Meeting> results;
		final TypedQuery<Meeting> query;
		query = em.createQuery("SELECT e FROM Meeting e "
				+ "WHERE e.title LIKE :searchString "
				+ "OR e.content LIKE :searchString ", Meeting.class);
		query.setParameter("searchString", "%" + searchString + "%");
		results = query.getResultList();
		return results;
	}

	/**
	 * Display the meeting with the param displayMeet
	 * @param displayMeet
	 * @return
	 */
	public Meeting displayMeeting(String displayMeet) {
		String eventId = displayMeet;
		TypedQuery<Meeting> query = em.createQuery(
				"Select e from Meeting e where e.id = :eventId", Meeting.class);
		query.setParameter("eventId", Integer.parseInt(eventId));
		Meeting event = query.getSingleResult();
		return event;
	}
}
