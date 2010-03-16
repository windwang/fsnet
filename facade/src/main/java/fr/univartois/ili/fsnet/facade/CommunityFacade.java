package fr.univartois.ili.fsnet.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.SocialEntity;

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
     *
     * @param id the id of the community
     * @return the community with the given id
     */
    public final Community getCommunity(int id){
        return em.find(Community.class, id);
    }
    
    /**
    *
    * @param name the name of the community
    * @return the community with the given name
    */
   public final Community getCommunityByName(String name){
	   if (name == null) {
           throw new IllegalArgumentException();
       }
	  Community community = em.createQuery(
				"SELECT community FROM Community community WHERE community.title LIKE :communityName",
				Community.class).setParameter("communityName", name ).getSingleResult();
       return community;
   }

    /**
     * Search a community responding to the given pattern
     * @param pattern
     * @return
     */
    public final List<Community> searchCommunity(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException();
        }
        TypedQuery<Community> query = em.createQuery("SELECT community FROM Community community LEFT JOIN FETCH community.interests WHERE community.title LIKE :pattern", Community.class);
        query.setParameter("pattern", "%" + pattern + "%");
        return query.getResultList();
    }

}
