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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.mobile.services.model.RestAnnouncement;

@Resource
@Path("/announcements")
public class Announcements {

	private EntityManagerFactory factory;
	private EntityManager em;

	public Announcements() {
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
	@GET
	@Path("/new")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericEntity<List<RestAnnouncement>> getNewAnnouncements( //
			@QueryParam("login") String login, // 
			@QueryParam("pwd") String password, //
			@PathParam("delay") Integer delay) {
		Logger.getAnonymousLogger().info(login);
		int decalage = 10;
		Date d = new Date();
		Date e = new Date(d.getTime()- decalage*60000);
		List<RestAnnouncement> announcements = new ArrayList<RestAnnouncement>();
		SocialEntityFacade sef = new SocialEntityFacade(em);
		if (sef.isMember(login, password)) {
			TypedQuery<Announcement> announceQuery = em
					.createQuery(
							"SELECT m FROM Announcement m where type(m) in (Announcement) and m.creationDate>=?1",
							Announcement.class);
			announceQuery.setParameter(1, e);
			for (Announcement announce : announceQuery.getResultList()) {
				
				RestAnnouncement restAnnouncement = new RestAnnouncement();
				
				String from = announce.getCreator().getName() + " " + announce.getCreator().getFirstName();
				restAnnouncement.setFrom(from);
				restAnnouncement.setMeetingId(announce.getId());
				restAnnouncement.setTitle(announce.getTitle());
				
				announcements.add(restAnnouncement);
			}
		}
		return new GenericEntity<List<RestAnnouncement>>(announcements){};
	}
}