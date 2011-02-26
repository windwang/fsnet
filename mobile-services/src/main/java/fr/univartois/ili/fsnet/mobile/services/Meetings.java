package fr.univartois.ili.fsnet.mobile.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.mobile.services.model.AuthInfo;
import fr.univartois.ili.fsnet.mobile.services.model.RestAnnouncement;
import fr.univartois.ili.fsnet.mobile.services.model.RestMeeting;

@Resource
@Path("/meetings")
public class Meetings {

	private EntityManagerFactory factory;
	private EntityManager em;

	public Meetings() {
		factory = Persistence.createEntityManagerFactory("fsnetjpa");
		em = factory.createEntityManager();
	}

	/**
	 * Return the unread personal messages
	 * 
	 * @param login
	 * @param password
	 * @return
	 */
	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public GenericEntity<List<RestMeeting>> getNewEvents(AuthInfo authInfo) {
		Logger.getAnonymousLogger().info(authInfo.toString());
		int decalage = authInfo.getDelay();
		Date d = new Date();
		Date e = new Date(d.getTime()- decalage*60000);
		List<RestMeeting> meetings = new ArrayList<RestMeeting>();
		SocialEntityFacade sef = new SocialEntityFacade(em);
		if (sef.isMember(authInfo.getLogin(), authInfo.getPassword())) {
			TypedQuery<Meeting> meetingQuery = em
					.createQuery(
							"SELECT m FROM Meeting m where m.creationDate>=?1",
							Meeting.class);
			meetingQuery.setParameter(1, e);
			for (Meeting meeting : meetingQuery.getResultList()) {
				
				RestMeeting restMeeting = new RestMeeting();
				
				String from = meeting.getCreator().getName() + " " + meeting.getCreator().getFirstName();
				restMeeting.setFrom(from);
				restMeeting.setMeetingId(meeting.getId());
				restMeeting.setTitle(meeting.getTitle());
				
				meetings.add(restMeeting);
			}
		}
		return new GenericEntity<List<RestMeeting>>(meetings){};
	}
}
