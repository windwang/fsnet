package fr.univartois.ili.fsnet.facade.forum.iliforum;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class CommunityFacade {

    private final EntityManager em;

    public CommunityFacade(EntityManager em) {
        this.em = em;
    }

    /**
     * Create and persist a new community
     * @param creator the creator of the community
     * @param name the name of the new community
     * @return the new persisted community
     */
    public final Community createCommunity(SocialEntity creator, String name) {
        Community community = new Community(creator, name);
        em.persist(community);
        return community;
    }

    /**
     * Delete the community with the given id
     * @param id
     */
    public void deleteCommunity(int id){
        Community community = em.find(Community.class, id);
        em.remove(community);
        em.flush();
    }

    /**
     *
     * @param id the id of the communty
     * @return the community with the given id
     */
    public Community getCommunity(int id){
        return em.find(Community.class, id);
    }

    /**
     * Search a community responding to the given pattern
     * @param pattern
     * @return
     */
    public List<Community> searchCommunity(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException();
        }
        TypedQuery<Community> query = em.createQuery("SELECT community FROM Community community WHERE community.title LIKE :pattern", Community.class);
        query.setParameter("pattern", "%" + pattern + "%");
        return query.getResultList();
    }

}
