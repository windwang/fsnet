package fr.univartois.ili.fsnet.facade.forum.iliforum;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class HubFacade {

    private final EntityManager em;

    public HubFacade(EntityManager em) {
        this.em = em;

    }

    /**
     * Create and persist a new Hub
     * @param community the related Community
     * @param creator the Hub creator
     * @param name Hub name
     * @return the new Hub
     */
    public final Hub createHub(Community community, SocialEntity creator, String name) {
        Hub hub = new Hub(community, creator, name);
        em.persist(hub);
        return hub;
    }

    /**
     * Delete a Hub identified by id
     * @param id
     */
    public final void delete(int id) {
        Hub hub = em.find(Hub.class, id);
        if (hub != null) {
            em.remove(hub);
        }
    }

    /**
     *
     * @param id
     * @return the Hub identified by id
     */
    public final Hub getHub(int id) {
        return em.find(Hub.class, id);
    }

    /**
     * Search a Hub
     * @param pattern
     * @return a list of pattern matching hub
     */
    public final List<Hub> searchHub(String pattern) {
        List<Hub> hubs = em.createQuery(
                "SELECT hub FROM Hub hub WHERE hub.title LIKE :hubName ").
                setParameter("hubName", "%" + pattern + "%").
                getResultList();
        return hubs;
    }

    /**
     * Search a Hub in a Community
     * @param pattern
     * @param community
     * @return a list of pattern matching hub
     */
    public final List<Hub> searchHub(String pattern, Community community) {
        List<Hub> hubs = em.createQuery(
                "SELECT hub FROM Hub hub WHERE hub.title LIKE :hubName AND hub.community = :com").
                setParameter("hubName", "%" + pattern + "%").
                setParameter("com", community).
                getResultList();
        return hubs;
    }
}
