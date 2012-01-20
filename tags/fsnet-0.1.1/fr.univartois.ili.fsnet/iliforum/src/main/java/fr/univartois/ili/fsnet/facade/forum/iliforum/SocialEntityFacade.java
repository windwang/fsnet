package fr.univartois.ili.fsnet.facade.forum.iliforum;

import fr.univartois.ili.fsnet.commons.security.Encryption;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.Interaction;
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
     * delete a Social Entity
     * @param socialEntityId the id of the Social Entity to delete
     */
    public final void deleteSocialEntity(SocialEntity socialEntity) {
        if (socialEntity == null) {
            throw new IllegalArgumentException();
        }
        for (SocialEntity contact : socialEntity.getContacts()) {
            contact.getContacts().remove(socialEntity);
        }
        for (SocialEntity contact : socialEntity.getRefused()) {
            contact.getRefused().remove(socialEntity);
        }
        for (SocialEntity contact : socialEntity.getRequested()) {
            contact.getRequested().remove(socialEntity);
        }
        for (SocialEntity contact : socialEntity.getAsked()) {
            contact.getAsked().remove(socialEntity);
        }
        em.remove(socialEntity);
        em.flush();
    }

    /**
     * search Social Entitys having Name Or FirstName Or Email like searchText
     * @param searchText the search text
     * @param socialEntity the Social Entity who search for others Social Entity
     * @return a map of list of search results (Contacts, Requested, Asked and Others)
     */
    public final HashMap<SearchResult, List<SocialEntity>> searchSocialEntity(String searchText, SocialEntity socialEntity) {
        if (searchText == null || socialEntity == null) {
            throw new IllegalArgumentException();
        }
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
        query.setParameter("id", socialEntity.getId());
        resultOthers = query.getResultList();

        queryContacts = em.createQuery(
                "SELECT e FROM SocialEntity e JOIN e.contacts c WHERE c.id = :id AND (e.name LIKE :searchText"
                + " OR e.firstName LIKE :searchText OR e.email LIKE :searchText)", SocialEntity.class);
        queryContacts.setParameter("searchText", "%" + searchText + "%");
        queryContacts.setParameter("id", socialEntity.getId());
        resultContacts = queryContacts.getResultList();

        queryRequested = em.createQuery(
                "SELECT e FROM SocialEntity e JOIN e.asked r WHERE r.id = :id AND (e.name LIKE :searchText"
                + " OR e.firstName LIKE :searchText OR e.email LIKE :searchText)", SocialEntity.class);
        queryRequested.setParameter("searchText", "%" + searchText + "%");
        queryRequested.setParameter("id", socialEntity.getId());
        resultRequested = queryRequested.getResultList();

        queryAsked = em.createQuery(
                "SELECT e FROM SocialEntity e JOIN e.requested r WHERE r.id = :id AND (e.name LIKE :searchText"
                + " OR e.firstName LIKE :searchText OR e.email LIKE :searchText)", SocialEntity.class);
        queryAsked.setParameter("searchText", "%" + searchText + "%");
        queryAsked.setParameter("id", socialEntity.getId());
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
        if (searchText == null) {
            throw new IllegalArgumentException();
        }
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
}
