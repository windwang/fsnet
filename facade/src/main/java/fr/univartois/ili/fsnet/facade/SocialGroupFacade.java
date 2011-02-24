package fr.univartois.ili.fsnet.facade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.SocialElement;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;

/**
 * Facade SocialGroup
 * 
 * @author J FLAMEN
 * @author SAID Mohamed <simo.said09 at gmail.com>
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
	 * 
	 * @param masterGroup
	 *            the master of the group
	 * @param creator
	 *            the member who create group
	 * @param name
	 *            description
	 * @param socialElements
	 *            SocialElement list (group or member)
	 * @return the created SocialGroup
	 */
	// public final SocialGroup createSocialGroup(SocialEntity masterGroup,
	// SocialEntity creator,
	public final SocialGroup createSocialGroup(SocialEntity masterGroup,
			String name, String description, List<SocialElement> socialElements) {
		SocialGroup sg = new SocialGroup(masterGroup, name, description);
		sg.setSocialElements(socialElements);
		em.persist(sg);
		return sg;
	}

	/**
	 * search Social Group by id
	 * 
	 * @param id
	 *            the id of the Social Group
	 * @return the SocialGroup we search
	 */
	public final SocialGroup getSocialGroup(int socialGroupId) {
		return em.find(SocialGroup.class, socialGroupId);
	}

	/**
	 * add SocialElement in SocialGroup socialElements
	 * 
	 * @param SocialElement
	 *            the socialElement to add
	 * @param socialGroup
	 *            the SocialGroup
	 */
	public final void addSocialElement(SocialElement socialElement,
			SocialGroup socialGroup) {
		if (socialElement == null || socialGroup == null) {
			throw new IllegalArgumentException();
		}
		if (!socialGroup.getSocialElements().contains(socialElement)) {
			socialGroup.getSocialElements().add(socialElement);
		}
	}

	/**
	 * remove socialElement from a SocialGroup socialElements
	 * 
	 * @param socialElement
	 *            the socialElement to remove
	 * @param socialGroup
	 *            the SocialGroup
	 */
	public final void removeSocialElement(SocialElement socialElement,
			SocialGroup socialGroup) {
		if (socialElement == null || socialGroup == null) {
			throw new IllegalArgumentException();
		}
		socialGroup.getSocialElements().remove(socialElement);
	}

	/**
	 * Find a SocialGroup by its name
	 * 
	 * @param name
	 *            the name to search for
	 * @return the fetched social Group
	 */
	public final SocialGroup findByName(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		TypedQuery<SocialGroup> query = em.createQuery(
				"SELECT sg FROM SocialGroup sg WHERE sg.name = :name",
				SocialGroup.class);
		query.setParameter("name", name);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * search a Social Group having Name Or description like searchText
	 * 
	 * @param searchText
	 *            the search text
	 * @return the Set of Social Groups matching with the search text
	 */
	public final Set<SocialGroup> searchGroup(String inputText) {
		if (inputText == null) {
			throw new IllegalArgumentException();
		}
		TypedQuery<SocialGroup> query = null;
		Set<SocialGroup> results = new HashSet<SocialGroup>();

		for (String searchText : inputText.split(" ")) {
			query = em.createQuery(
					"SELECT gs FROM SocialGroup gs WHERE gs.name LIKE :searchText"
							+ " OR gs.description LIKE :searchText",
					SocialGroup.class);
			query.setParameter("searchText", "%" + searchText + "%");
			results.addAll(query.getResultList());
		}

		return results;
	}

	/**
	 * Get all social group
	 * 
	 * @return the Set of all Social Groups
	 */
	public final List<SocialGroup> getAllSocialEntity() {
		TypedQuery<SocialGroup> query = em.createQuery(
				"SELECT gs FROM SocialGroup gs", SocialGroup.class);
		return query.getResultList();
	}

	public List<SocialGroup> getAcceptedSocialGroup(SocialGroup socialGroup) {
		if (socialGroup == null) {
			throw new IllegalArgumentException();
		}
		List<SocialGroup> groups = new ArrayList<SocialGroup>();
		List<SocialElement> socialElements = socialGroup.getSocialElements();
		for (SocialElement socialElement : socialElements) {
			if (socialElement instanceof SocialGroup)
				groups.add((SocialGroup) socialElement);
		}
		return groups;
	}

	public List<SocialEntity> getAcceptedSocialEntity(SocialGroup socialGroup) {
		if (socialGroup == null) {
			throw new IllegalArgumentException();
		}
		List<SocialEntity> members = new ArrayList<SocialEntity>();
		List<SocialElement> socialElements = socialGroup.getSocialElements();
		for (SocialElement socialElement : socialElements) {

			if (socialElement instanceof SocialEntity) {

				members.add((SocialEntity) socialElement);

			}
		}
		return members;
	}

	public List<SocialGroup> AllParent(SocialGroup socialGroup) {

		if (socialGroup == null) {
			throw new IllegalArgumentException();
		}
		List<SocialGroup> groups = new ArrayList<SocialGroup>();
		return getAllParent(socialGroup, groups);
	}

	private List<SocialGroup> getAllParent(SocialGroup socialGroup,
			List<SocialGroup> groups) {

		if (socialGroup.getGroup() == null) {
			return groups;
		} else {
			groups.add(socialGroup.getGroup());
			return getAllParent(socialGroup.getGroup(), groups);
		}

	}

	public final void switchState(int socialGroupId) {
		SocialGroup sg = getSocialGroup(socialGroupId);
		sg.setEnabled(!sg.isEnabled());
		em.merge(sg);
		em.flush();
	}
}