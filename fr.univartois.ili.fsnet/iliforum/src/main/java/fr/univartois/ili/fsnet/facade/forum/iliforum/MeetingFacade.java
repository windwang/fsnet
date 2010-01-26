package fr.univartois.ili.fsnet.facade.forum.iliforum;

import java.util.Date;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class MeetingFacade {

    private final EntityManager em;

    MeetingFacade(EntityManager em) {
        this.em = em;
    }

    public Meeting createMeeting(SocialEntity member, String eventName,
            String eventDescription, Date endDate, Boolean isPrivate,
            Date startDate, String address, String city) {

        Meeting event = new Meeting(member, eventName, eventDescription,
                endDate, isPrivate, startDate, new Address(address, city));

        member.getInteractions().add(event);
        em.persist(event);
        return event;
    }

    public Meeting deleteMeeting() {
        return null;

    }
}
