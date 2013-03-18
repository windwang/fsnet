package fr.univartois.ili.fsnet.facade;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 * @author mickael watrelot - micgamers@gmail.com
 */
public class AnnouncementFacade {

	private final EntityManager em;

	/**
	 * Contructor
	 * 
	 * @param em
	 */
	public AnnouncementFacade(EntityManager em) {
		this.em = em;
	}

	/**
	 * Create a New Announcement
	 * 
	 * @param member
	 * @param annName
	 * @param annDescription
	 * @param endDate
	 * @param isPrivate
	 * @return the new Announcement
	 */
	public final Announcement createAnnouncement(SocialEntity member,
			String annName, String annDescription, Date endDate,
			Boolean isPrivate) {
		Announcement announce = new Announcement(member, annName,
				annDescription, endDate, isPrivate);
		
		em.persist(announce);

		return announce;
	}

	public final Announcement getAnnouncement(int idAnnounce) {
		return em.find(Announcement.class, idAnnounce);
	}

	/**
	 * Modify the Announcement
	 * 
	 * @param idAnnounce
	 * @param annName
	 * @param annDescription
	 * @param endDate
	 * @return the Announcement modify
	 */
	public final Announcement modifyAnnouncement(int idAnnounce,
			String annName, String annDescription, Date endDate) {
		if (annName == null || annDescription == null || endDate == null) {
			throw new IllegalArgumentException();
		}
		Announcement announce = getAnnouncement(idAnnounce);
		if (announce == null) {
			throw new IllegalArgumentException();
		}

		announce.setTitle(annName);
		announce.setContent(annDescription);
		announce.setEndDate(endDate);
		em.merge(announce);
		return announce;
	}

	/**
	 * Search the Announcement with the param textSearchAnnounce
	 * 
	 * @param textSearchAnnounce
	 * @return a list of Announcement
	 */
	public final List<Announcement> searchAnnouncement(String textSearchAnnounce) {
		if (textSearchAnnounce == "") {
			throw new IllegalArgumentException();
		}
		List<Announcement> listAnnounces;
		listAnnounces = em
				.createQuery(
						"SELECT a FROM Announcement a WHERE  TYPE(a) = Announcement AND "
								+ "(a.title LIKE :textSearchAnnounce OR a.content LIKE :textSearchAnnounce) ORDER BY a.endDate DESC",
						Announcement.class)
				.setParameter("textSearchAnnounce",
						"%" + textSearchAnnounce + "%").getResultList();
		return listAnnounces;

	}

	/**
	 * Get the new announcements from the last user's connection
	 * 
	 * @param user
	 * @return a list of Announcement
	 */
	public final List<Announcement> getLastAnnouncementForTheLastUserConnexion(
			SocialEntity user) {
		if (user == null) {
			throw new IllegalArgumentException();
		}
		List<Announcement> listAnnounces;
		listAnnounces = em
				.createQuery(
						"SELECT a FROM Announcement a WHERE  TYPE(a) = Announcement AND "
								+ "(a.creationDate >= :lastConnection) ORDER BY a.endDate DESC",
						Announcement.class)
				.setParameter("lastConnection", user.getLastConnection())
				.getResultList();

		return listAnnounces;
	}

	/**
	 * 
	 * @param user
	 * @return a list of Announcement
	 */
	public final List<Announcement> getUserAnnouncements(SocialEntity user) {
		if (user == null) {
			throw new IllegalArgumentException();
		}
		List<Announcement> listAnnounces;
		listAnnounces = em
				.createQuery(
						"SELECT a FROM Announcement a WHERE TYPE(a) = Announcement AND a.creator = :member ORDER BY a.endDate DESC",
						Announcement.class).setParameter("member", user)
				.getResultList();
		return listAnnounces;
	}
}
