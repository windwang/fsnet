package fr.univartois.ili.fsnet.facade;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.commons.security.Encryption;
import fr.univartois.ili.fsnet.commons.utils.DateUtils;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 *
 * @author BEN ABDESSALEM Mehdi <mehdi.benabdessalem at gmail.com>
 */
public class SocialEntityFacade {

	public static enum SearchResult {

		Others,
		Contacts,
		Requested,
		Asked
	};
	private final EntityManager em;

	/**
	 *
	 * @param em
	 */
	public SocialEntityFacade(EntityManager em) {
		this.em = em;
	}

	/**
	 * create a new Social Entity
	 * @param name the name of the new Social Entity
	 * @param firstName the firsname of the new Social Entity
	 * @param email the email of the new Social Entity
	 * @return the created SocialEntity
	 */
	public final SocialEntity createSocialEntity(String name, String firstName, String email) {
		SocialEntity es = new SocialEntity(name, firstName, email);
		em.persist(es);
		return es;
	}

	/**
	 * search Social Entity by id
	 * @param id the id of the Social Entity
	 * @return the SocialEntity we search
	 */
	public final SocialEntity getSocialEntity(int socialEntityId) {
		return em.find(SocialEntity.class, socialEntityId);
	}

	/**
	 * Find a SocialEntity by its email
	 * @param email the email to search for
	 * @return the fetched social entity
	 */
	public final SocialEntity findByEmail(String email) {
		if (email == null) {
			throw new IllegalArgumentException();
		}
		TypedQuery<SocialEntity> query = em.createQuery(
				"SELECT es FROM SocialEntity es WHERE es.email = :email", SocialEntity.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
		}
		return null;
	}

	
	/**
	 * search Social Entitys having Name Or FirstName Or Email like inputText
	 * @param searchText the search text
	 * @param socialEntity the Social Entity who search for others Social Entity
	 * @return a map of Set of search results (Contacts, Requested, Asked and Others)
	 */
	public final HashMap<SearchResult, Set<SocialEntity>> searchSocialEntity(String inputText, SocialEntity socialEntity) {
		if (inputText == null || socialEntity == null) {
			throw new IllegalArgumentException();
		}
		TypedQuery<SocialEntity> query = null;
		TypedQuery<SocialEntity> queryContacts = null;
		TypedQuery<SocialEntity> queryRequested = null;
		TypedQuery<SocialEntity> queryAsked = null;

		Set<SocialEntity> resultOthers = new HashSet<SocialEntity>();
		Set<SocialEntity> resultContacts = new HashSet<SocialEntity>();
		Set<SocialEntity> resultRequested = new HashSet<SocialEntity>();
		Set<SocialEntity> resultAsked = new HashSet<SocialEntity>();

		HashMap<SearchResult, Set<SocialEntity>> results = new HashMap<SearchResult, Set<SocialEntity>>();

		for(String searchText : inputText.split(" ")){

			query = em.createQuery(
					"SELECT es FROM SocialEntity es WHERE (es.name LIKE :searchText"
					+ " OR es.firstName LIKE :searchText OR es.email LIKE :searchText) AND es.id <> :id AND es.isEnabled = true", SocialEntity.class);
			query.setParameter("searchText", "%" + searchText + "%");
			query.setParameter("id", socialEntity.getId());
			resultOthers.addAll(query.getResultList());

			queryContacts = em.createQuery(
					"SELECT e FROM SocialEntity e JOIN e.contacts c WHERE c.id = :id AND (e.name LIKE :searchText"
					+ " OR e.firstName LIKE :searchText OR e.email LIKE :searchText) AND e.isEnabled = true", SocialEntity.class);
			queryContacts.setParameter("searchText", "%" + searchText + "%");
			queryContacts.setParameter("id", socialEntity.getId());
			resultContacts.addAll(queryContacts.getResultList());

			queryRequested = em.createQuery(
					"SELECT e FROM SocialEntity e JOIN e.asked r WHERE r.id = :id AND (e.name LIKE :searchText"
					+ " OR e.firstName LIKE :searchText OR e.email LIKE :searchText) AND e.isEnabled = true", SocialEntity.class);
			queryRequested.setParameter("searchText", "%" + searchText + "%");
			queryRequested.setParameter("id", socialEntity.getId());
			resultRequested.addAll(queryRequested.getResultList());

			queryAsked = em.createQuery(
					"SELECT e FROM SocialEntity e JOIN e.requested r WHERE r.id = :id AND (e.name LIKE :searchText"
					+ " OR e.firstName LIKE :searchText OR e.email LIKE :searchText) AND e.isEnabled = true", SocialEntity.class);
			queryAsked.setParameter("searchText", "%" + searchText + "%");
			queryAsked.setParameter("id", socialEntity.getId());
			resultAsked.addAll(queryAsked.getResultList());

		}
		
		resultOthers.removeAll(resultContacts);
		resultOthers.removeAll(resultAsked);
		resultOthers.removeAll(resultRequested);

		results.put(SearchResult.Others, resultOthers);
		results.put(SearchResult.Contacts, resultContacts);
		results.put(SearchResult.Requested, resultRequested);
		results.put(SearchResult.Asked, resultAsked);

		return results;
	}

	/**
	 * search a Social Entity having Name Or FirstName Or Email like searchText
	 * @param searchText the search text
	 * @return the Set of Social Entitys matching with the search text
	 */
	public final Set<SocialEntity> searchSocialEntity(String inputText) {
		if (inputText == null) {
			throw new IllegalArgumentException();
		}
		TypedQuery<SocialEntity> query = null;
		Set<SocialEntity> results = new HashSet<SocialEntity>();
		
		for(String searchText : inputText.split(" ")){
			query = em.createQuery(
					"SELECT es FROM SocialEntity es WHERE es.name LIKE :searchText"
					+ " OR es.firstName LIKE :searchText OR es.email LIKE :searchText", SocialEntity.class);
			query.setParameter("searchText", "%" + searchText + "%");
			results.addAll(query.getResultList());
		}

		return results;
	}
	
	/**
	 * Get all social Entity
	 * @return the Set of all Social Entities
	 */
	public final List<SocialEntity> getAllSocialEntity() {
		TypedQuery<SocialEntity> query = em.createQuery("SELECT es FROM SocialEntity es", SocialEntity.class);
		return query.getResultList();
	}
	
	/**
	 * Get the user corresponding to the email
	 * @param email
	 * @return the user or null
	 */
	public final SocialEntity getSocialEntityByEmail(String email){
		TypedQuery<SocialEntity> query = em.createQuery("SELECT es FROM SocialEntity es WHERE es.email LIKE :mail", SocialEntity.class);
		query.setParameter("mail", email);
		return query.getSingleResult();
	}
    
//    /**
//     * Return the social Entities
//     * @param socialEntity the Social Entity who is on line
//     * @return the list of Social Entities 
//     */
//    public final List<SocialEntity> getSocialEntities(SocialEntity socialEntity) {
//        if (socialEntity == null) {
//            throw new IllegalArgumentException();
//        }
//        TypedQuery<SocialEntity> query = null;
//        List<SocialEntity> results = null;
//
//        query = em.createQuery(
//                "SELECT es FROM SocialEntity es WHERE es.id <> :id", SocialEntity.class);
//        query.setParameter("id", socialEntity.getId() );
//        results = query.getResultList();
//
//        return results;
//    }

	/**
	 * add interest in SocialEntity interests
	 * @param interest the interest to add
	 * @param socialEntity the SocialEntity
	 */
	public final void addInterest(Interest interest, SocialEntity socialEntity) {
		if (interest == null || socialEntity == null) {
			throw new IllegalArgumentException();
		}
		if (!socialEntity.getInterests().contains(interest)) {
			socialEntity.getInterests().add(interest);
			interest.getEntities().add(socialEntity);
		}
	}

	/**
	 * remove interest from a SocialEntity interests
	 * @param interest the interest to remove
	 * @param socialEntity the SocialEntity
	 */
	public final void removeInterest(Interest interest, SocialEntity socialEntity) {
		if (interest == null || socialEntity == null) {
			throw new IllegalArgumentException();
		}
		interest.getEntities().remove(socialEntity);
		socialEntity.getInterests().remove(interest);
	}

	/**
	 * add a favorite interaction
	 * @param socialEntity the SocialEntity who want to add a favorite interaction
	 * @param interaction the favorite interaction to add
	 */
	public final void addFavoriteInteraction(SocialEntity socialEntity, Interaction interaction) {
		if (socialEntity == null || interaction == null) {
			throw new IllegalArgumentException();
		}
		socialEntity.getFavoriteInteractions().add(interaction);
		interaction.getFollowingEntitys().add(socialEntity);
	}

	/**
	 * remove a favorite interaction
	 * @param socialEntity the SocialEntity who want to remove a favorite interaction
	 * @param interaction the favorite interaction to remove
	 */
	public final void removeFavoriteInteraction(SocialEntity socialEntity, Interaction interaction) {
		if (socialEntity == null || interaction == null) {
			throw new IllegalArgumentException();
		}
		socialEntity.getFavoriteInteractions().remove(interaction);
		interaction.getFollowingEntitys().remove(socialEntity);
	}

	/**
	 * Test if the email and password correspond to a member
	 * @param email
	 * @param password
	 * @return true if the member exists, false otherwise
	 */
	public final boolean isMember(String email, String password) {
		SocialEntity entity = this.findByEmail(email);
		if (entity != null) {
			return Encryption.testPassword(password, entity.getPassword());
		}
		return false;
	}
	
	public final void switchState(int socialEntityId) {
		SocialEntity se = getSocialEntity(socialEntityId);
		se.setIsEnabled(!se.getIsEnabled());
		em.merge(se);
		em.flush();
	}
	
	public final List<SocialEntity> getAllOrphanMembers() {
		TypedQuery<SocialEntity> query = em.createQuery(
				"SELECT se FROM SocialEntity se WHERE se.group IS null", SocialEntity.class);
		return query.getResultList();
	}
	
	/**
	 * Get social entity having event tomorow
	 * @return HasMap key: Meeting value: set of social entity
	 */
	
	public final HashMap<Meeting,Set<SocialEntity>> getSocialEntityHavingEventTomorow(){
		List<Meeting> listMeeting;
		HashMap<Meeting,Set<SocialEntity>> listSocialEntity = null; 
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR),
				calendar.get(Calendar.MINUTE), -1);
		Date today = calendar.getTime();
		
		String todayFormatted = "";
		todayFormatted = DateUtils.renderDBDate(today);
		
		listMeeting = em
				.createQuery(
						"SELECT m FROM Meeting m where m.startDate = \""+
								todayFormatted+"\"",
						Meeting.class).getResultList();
		
		InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(em);
		
		for(Meeting m : listMeeting){
			if(listSocialEntity==null){
				listSocialEntity = new HashMap<Meeting,Set<SocialEntity>>();
			}
			listSocialEntity.put(m,interactionRoleFacade.getSubscribers(m));
		}
		
		return listSocialEntity;
	}
	
}
