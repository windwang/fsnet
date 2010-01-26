package fr.univartois.ili.fsnet.facade.forum.iliforum;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 *
 * @author BEN ABDESSALEM Mehdi <mehdi.benabdessalem at gmail.com>
 */
public class SocialEntityFacade {

	public static enum SearchResult{ 
		Others,
		Contacts, 
		Requested,
		Asked 
	};

	private final EntityManager em;

	public SocialEntityFacade(EntityManager em) {
		this.em = em;
	}

	/**
	 * 
	 * @param name
	 * @param firstName
	 * @param email
	 * @return the created SocialEntity
	 */
	public SocialEntity createSocialEntity(String name, String firstName, String email) {
		SocialEntity es = new SocialEntity(name, firstName, email);
		em.persist(es);
		return es;
	}
	
	/**
	 * 
	 * @param id
	 * @return the SocialEntity
	 */
	public SocialEntity getSocialEntity(int id){
		return em.find(SocialEntity.class, id);
	}

	/**
	 * 
	 * @param searchText
	 * @param socialEntityId the connected SocialEntity id
	 * @return a map of list of search results
	 */
	public HashMap<SearchResult, List<SocialEntity>> searchSocialEntity(String searchText, int socialEntityId){

		TypedQuery<SocialEntity> query = null;
		TypedQuery<SocialEntity> queryContacts = null;
		TypedQuery<SocialEntity> queryRequested = null;
		TypedQuery<SocialEntity> queryAsked = null;

		List<SocialEntity> resultOthers = null;
		List<SocialEntity> resultContacts = null;
		List<SocialEntity> resultRequested = null;
		List<SocialEntity> resultAsked = null;

		HashMap<SearchResult, List<SocialEntity>> results = new HashMap<SearchResult, List<SocialEntity>>();

		query = em.createQuery(
				"SELECT es FROM SocialEntity es WHERE (es.name LIKE :searchText"
				+ " OR es.firstName LIKE :searchText OR es.email LIKE :searchText) AND es.id <> :id", SocialEntity.class);
		query.setParameter("searchText", "%" + searchText + "%");
		query.setParameter("id", socialEntityId);
		resultOthers = query.getResultList();

		queryContacts = em.createQuery(
				"SELECT e FROM SocialEntity e JOIN e.contacts c WHERE c.id = :id AND (e.name LIKE :searchText"
				+ " OR e.firstName LIKE :searchText OR e.email LIKE :searchText)", SocialEntity.class);
		queryContacts.setParameter("searchText", "%" + searchText + "%");
		queryContacts.setParameter("id", socialEntityId);
		resultContacts = queryContacts.getResultList();

		queryRequested = em.createQuery(
				"SELECT e FROM SocialEntity e JOIN e.asked r WHERE r.id = :id AND (e.name LIKE :searchText"
				+ " OR e.firstName LIKE :searchText OR e.email LIKE :searchText)", SocialEntity.class);
		queryRequested.setParameter("searchText", "%" + searchText + "%");
		queryRequested.setParameter("id", socialEntityId);
		resultRequested = queryRequested.getResultList();

		queryAsked = em.createQuery(
				"SELECT e FROM SocialEntity e JOIN e.requested r WHERE r.id = :id AND (e.name LIKE :searchText"
				+ " OR e.firstName LIKE :searchText OR e.email LIKE :searchText)", SocialEntity.class);
		queryAsked.setParameter("searchText", "%" + searchText + "%");
		queryAsked.setParameter("id", socialEntityId);
		resultAsked = queryAsked.getResultList();

		resultOthers.removeAll(resultContacts);
		resultOthers.removeAll(resultAsked);
		resultOthers.removeAll(resultRequested);

		results.put(SearchResult.Others, resultOthers);
		results.put(SearchResult.Contacts, resultContacts);
		results.put(SearchResult.Requested, resultRequested);
		results.put(SearchResult.Asked, resultAsked);

		return results;
	}
}
