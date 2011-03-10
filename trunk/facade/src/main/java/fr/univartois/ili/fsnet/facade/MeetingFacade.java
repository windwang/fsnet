package fr.univartois.ili.fsnet.facade;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 * @author mickael watrelot - micgamers@gmail.com
 */
public class MeetingFacade {

    private final EntityManager em;

    /**
     * Constructor
     *
     * @param em
     */
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
    public final Meeting createMeeting(SocialEntity member, String eventName,
            String eventDescription, Date endDate, Boolean isPrivate,
            Date startDate, String address, String city) {
        if (member == null || eventName == null || eventDescription == null
                || endDate == null || isPrivate == null || startDate == null
                || address == null || city == null) {
            throw new IllegalArgumentException();
        }
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
    public final Meeting getMeeting(int meetingId) {
        return em.find(Meeting.class, meetingId);
    }


    /**
     * Search the meeting with the param searchStr
     *
     * @param searchStr
     * @return
     */
    public final List<Meeting> searchMeeting(String searchStr) {
        if (searchStr == null) {
            throw new IllegalArgumentException();
        }
        List<Meeting> results;
        final TypedQuery<Meeting> query;
        query = em.createQuery("SELECT e FROM Meeting e "
                + "WHERE e.title LIKE :searchString "
                + "OR e.content LIKE :searchString " 
                + "ORDER BY e.startDate DESC ", Meeting.class);
        query.setParameter("searchString", "%" + searchStr + "%");
        results = query.getResultList();
        return results;
    }
    
    /**
	 * Get the new meetings for the last user's connection
	 * @param user
	 * @return a list of Meeting
	 */
	public final List<Meeting> getLastMeetingForTheLastUserConnexion(SocialEntity user) {
		if (user== null) {
			throw new IllegalArgumentException();
		}
		System.out.println("test");
		List<Meeting> listMeeting;
		listMeeting = em
		.createQuery(
				"SELECT m FROM Meeting m WHERE  TYPE(m) IN(Meeting) AND "
				+ "(m.creationDate >= :lastConnection) ORDER BY m.startDate DESC",
				Meeting.class).setParameter("lastConnection",
						user.getLastConnection()).getResultList();
		
		return listMeeting;
	}
    
}
