package fr.univartois.ili.fsnet.mobile.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.mobile.services.model.RestMeeting;

@Resource
@Path("/meetings")
public class Meetings {

	private EntityManager em;

	public Meetings() {
		em = PersistenceProvider.createEntityManager();
	}

	/**
	 * Return the unread personal messages
	 * 
	 * @param login
	 * @param password
	 * @return
	 */
	@GET
	@Path("/new")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericEntity<List<RestMeeting>> getNewEvents( //
			@QueryParam("login") String login, // 
			@QueryParam("pwd") String password, //
			@PathParam("delay") Integer delay) {
		Logger.getAnonymousLogger().info(login);
		int decalage = 10;
		Date d = new Date();
		Date e = new Date(d.getTime()- decalage*60000);
		List<RestMeeting> meetings = new ArrayList<RestMeeting>();
		SocialEntityFacade sef = new SocialEntityFacade(em);
		if (sef.isMember(login, password)) {
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
