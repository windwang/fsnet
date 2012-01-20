package fr.univartois.ili.fsnet.facade;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.commons.security.Encryption;
import fr.univartois.ili.fsnet.entities.SocialElement;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;


/**
* Facade SocialGroup 
* @author J FLAMEN 
*/

public class SocialGroupFacade {

	private final EntityManager em;

	/**
	 *
	 * @param em
	 */
	public SocialGroupFacade(EntityManager em) {
		this.em = em;
	}

	/**
	 * create a new Social Group 
	 * @param masterGroup the master of the group
	 * @param creator the member who create group
	 * @param description description 
	 * @param socialElements SocialElement list (group or member)
	 * @return the created SocialGroup
	 */
	public final SocialGroup createSocialGroup(SocialEntity masterGroup, SocialEntity creator,
			String description, List<SocialElement> socialElements) {
		SocialGroup sg = new SocialGroup(masterGroup, creator, description);
		sg.setSocialElements(socialElements);
		em.persist(sg);
		return sg;
	}

	/**
	 * search Social Group by id
	 * @param id the id of the Social Group
	 * @return the SocialGroup we search
	 */
	public final SocialGroup getSocialGroup(int socialGroupId) {
		return em.find(SocialGroup.class, socialGroupId);
	}
    

	/**
	 * add SocialElement in SocialGroup socialElements
	 * @param SocialElement the socialElement to add
	 * @param socialGroup the SocialGroup
	 */
	public final void addSocialElement(SocialElement socialElement, SocialGroup socialGroup) {
		if (socialElement == null || socialGroup == null) {
			throw new IllegalArgumentException();
		}
		if (!socialGroup.getSocialElements().contains(socialElement)) {
			socialGroup.getSocialElements().add(socialElement);
		}
	}

	/**
	 * remove socialElement from a SocialGroup socialElements
	 * @param socialElement the socialElement to remove
	 * @param socialGroup the SocialGroup
	 */
	public final void removeSocialElement(SocialElement socialElement, SocialGroup socialGroup) {
		if (socialElement == null || socialGroup == null) {
			throw new IllegalArgumentException();
		}
		socialGroup.getSocialElements().remove(socialElement);
	}
	
    /**
     * Search group 
     * 
     * @param designaton
     * @return a list of SocialGroup
     */
    public final List<SocialGroup> searchGroup(String pattern) {

    	TypedQuery<SocialGroup> query = em.createQuery("SELECT group FROM SocialGroup group WHERE group.description LIKE :description",SocialGroup.class);
        if(pattern == null)
        	query.setParameter("description", "%");
        else
        	query.setParameter("description", "%" + pattern + "%");
        	
        List<SocialGroup> groups = query.getResultList();
        return groups;
    }
	
	public final void switchState(int socialGroupId) {
		SocialGroup sg = getSocialGroup(socialGroupId);
		sg.setEnabled(!sg.isEnabled());
		em.merge(sg);
		em.flush();
	}

    
    
    
}