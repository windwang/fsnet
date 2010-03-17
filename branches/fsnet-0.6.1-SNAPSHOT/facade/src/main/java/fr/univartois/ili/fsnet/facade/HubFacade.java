package fr.univartois.ili.fsnet.facade;

import java.util.List;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.SocialEntity;

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
     *
     * @param community
     *            the related Community
     * @param creator
     *            the Hub creator
     * @param name
     *            Hub name
     * @return the new Hub
     */
    public final Hub createHub(Community community, SocialEntity creator,
            String name) {
        //TODO actually no gestion for commmunity
        if (creator == null || name == null) {
            throw new IllegalArgumentException();
        }
        Hub hub = new Hub(community, creator, name);
        community.getHubs().add(hub);
        em.persist(hub);
        return hub;
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
     *
     * @param pattern
     * @return a list of pattern matching hub
     */
    public final List<Hub> searchHub(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException();
        }
        List<Hub> hubs = em.createQuery(
                "SELECT hub FROM Hub hub WHERE hub.title LIKE :hubName ",
                Hub.class).setParameter("hubName", "%" + pattern + "%").getResultList();
        return hubs;
    }
	
	/**
	 * 
	 * @param name
	 * @param community
	 * @return the Hub identified by name and community
	 */
	public final Hub getHubByName(String name, Community community) {
		if (name == null || community == null) {
			throw new IllegalArgumentException();
		}
		Hub hub = em.createQuery(
				"SELECT hub FROM Hub hub WHERE hub.title LIKE :hubName AND hub.community = :com",
				Hub.class).setParameter("hubName", name ).setParameter("com", community ).getSingleResult();
		return hub;
	}

    /**
     * Search a Hub in a Community
     *
     * @param pattern
     * @param community
     * @return a list of pattern matching hub
     */
    public final List<Hub> searchHub(String pattern, Community community) {
        if (pattern == null || community == null) {
            throw new IllegalArgumentException();
        }
        List<Hub> hubs = em.createQuery(
                "SELECT hub FROM Hub hub WHERE hub.title LIKE :hubName AND hub.community = :com",
                Hub.class).setParameter("hubName", "%" + pattern + "%").setParameter("com", community).getResultList();
        return hubs;
    }
}
