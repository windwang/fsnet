package fr.univartois.ili.fsnet.facade.forum.iliforum;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.Interest;
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
     * delete a Social Entity
     * @param socialEntityId the id of the Social Entity to delete
     */
    public final void deleteSocialEntity(int socialEntityId) {
        SocialEntity se = getSocialEntity(socialEntityId);
        em.remove(se);
        em.flush();
    }

    /**
     * search Social Entitys having Name Or FirstName Or Email like searchText
     * @param searchText the search text
     * @param socialEntityId the id of the Social Entity who search for others Social Entity
     * @return a map of list of search results (Contacts, Requested, Asked and Others)
     */
    public final HashMap<SearchResult, List<SocialEntity>> searchSocialEntity(String searchText, int socialEntityId) {
    //TODO NPE
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

    /**
     * search a Social Entity having Name Or FirstName Or Email like searchText
     * @param searchText the search text
     * @return the list of Social Entitys matching with the search text
     */
    public final List<SocialEntity> searchSocialEntity(String searchText) {
        //TODO NPE
        TypedQuery<SocialEntity> query = null;
        List<SocialEntity> results = null;

        query = em.createQuery(
                "SELECT es FROM SocialEntity es WHERE es.name LIKE :searchText"
                + " OR es.firstName LIKE :searchText OR es.email LIKE :searchText", SocialEntity.class);
        query.setParameter("searchText", "%" + searchText + "%");
        results = query.getResultList();

        return results;
    }

    /**
     * add interest in user interests
     * @param interestId id of the interest to add
     * @param userId id of the user
     */
    public final void addInterest(int interestId, int userId) {
        InterestFacade interestFacade = new InterestFacade(em);
        Interest interest = interestFacade.getInterest(interestId);
        SocialEntity user = getSocialEntity(userId);
        if (!user.getInterests().contains(interest)) {
            user.getInterests().add(interest);
        }
    }

    /**
     * remove interest from user interests
     * @param interestId id of the interest to remove
     * @param userId id of the user
     */
    public final void removeInterest(int interestId, int userId) {
        InterestFacade interestFacade = new InterestFacade(em);
        Interest interest = interestFacade.getInterest(interestId);
        SocialEntity user = getSocialEntity(userId);
        user.getInterests().remove(interest);
    }
}
