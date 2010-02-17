package fr.univartois.ili.fsnet.facade.forum.iliforum;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import java.util.Set;

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
     * Delete the Meeting
     *
     * @param meeting
     */
    public final void deleteMeeting(Meeting meeting) {
        if (meeting == null) {
            throw new IllegalArgumentException();
        }
        Set<SocialEntity> followingEntitys = meeting.getFollowingEntitys();
        for(SocialEntity se : followingEntitys){
            se.getFavoriteInteractions().remove(meeting);
        }
        InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(em);
        interactionRoleFacade.unsubscribeAll(meeting);
        em.remove(meeting);
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
                + "OR e.content LIKE :searchString ", Meeting.class);
        query.setParameter("searchString", "%" + searchStr + "%");
        results = query.getResultList();
        return results;
    }
}
