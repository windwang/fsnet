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
	 * 
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
	 * @param meetingId
	 * @return
	 */
	public Meeting getMeeting(int meetingId) {
		return em.find(Meeting.class, meetingId);
	}

	/**
	 * Delete the Meeting with the id of the meeting meetingId
	 * 
	 * @param meetingId
	 */
	public void deleteMeeting(int meetingId) {
		Meeting meet = getMeeting(meetingId);
		if (meet != null) {
			em.remove(meet);
			em.flush();
		}
	}

	/**
	 * Search the meeting with the param searchStr
	 * 
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
}
