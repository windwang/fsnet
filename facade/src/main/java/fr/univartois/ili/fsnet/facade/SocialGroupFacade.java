package fr.univartois.ili.fsnet.facade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialElement;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;

/**
 * Facade SocialGroup
 * 
 * @author J FLAMEN
 * @author SAID Mohamed <simo.said09 at gmail.com>
 * @author stephane gronowski
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
	public final List<SocialGroup> getAllSocialGroups() {
		TypedQuery<SocialGroup> query = em.createQuery(
				"SELECT gs FROM SocialGroup gs", SocialGroup.class);
		return query.getResultList();
	}

	public List<SocialGroup> getAcceptedSocialGroups(SocialGroup socialGroup) {
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

	public List<SocialEntity> getAcceptedSocialEntities(SocialGroup socialGroup) {
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

	public String TreeParentName(SocialEntity member) {

		if (member == null) {
			throw new IllegalArgumentException();
		}
		String tree = "";
		if (member.getGroup() != null) {
			List<SocialGroup> socialGroups = getAllAntecedentSocialGroups(member
					.getGroup());

			for (SocialGroup socialGroup2 : socialGroups) {
				tree = (socialGroup2.getName() + ">") + tree;
			}
			tree = tree + member.getGroup().getName();
		} else
			tree = ">";
		return tree;
	}

	public List<SocialGroup> getAllAntecedentSocialGroups(
			SocialGroup socialGroup) {

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

	public List<SocialGroup> getAllChildGroups(SocialGroup socialGroup) {

		if (socialGroup == null) {
			throw new IllegalArgumentException();
		}

		return allGroupChild(socialGroup);
	}

	private List<SocialGroup> allGroupChild(SocialGroup socialGroup) {
		List<SocialGroup> groups = new ArrayList<SocialGroup>();
		groups.add(socialGroup);
		for (SocialElement socialElement : socialGroup.getSocialElements()) {
			if (socialElement instanceof SocialGroup) {
				groups.addAll(allGroupChild((SocialGroup) socialElement));
			}
		}

		return groups;

	}

	public final void switchState(int socialGroupId) {
		SocialGroup sg = getSocialGroup(socialGroupId);
		List<SocialElement> socialElements = getAllChildSocialElements(sg);

		if (sg.getIsEnabled()) {
			for (SocialElement socialElement : socialElements) {
				socialElement.setIsEnabled(false);
				em.merge(socialElement);
			}

		} else {
			for (SocialElement socialElement : socialElements) {
				socialElement.setIsEnabled(true);
				em.merge(socialElement);
			}
		}

		em.flush();
	}

	private List<SocialElement> getAllChildSocialElements(SocialGroup sg) {
		List<SocialEntity> entities = getAllChildMembers(sg);
		List<SocialGroup> groups = getAllChildGroups(sg);
		List<SocialElement> socialElements = new ArrayList<SocialElement>();
		socialElements.addAll(groups);
		socialElements.addAll(entities);
		return socialElements;
	}

	public List<SocialEntity> getAllMasterGroupes() {
		TypedQuery<SocialEntity> query = null;
		query = em.createQuery("SELECT g.masterGroup FROM SocialGroup g ",
				SocialEntity.class);

		return query.getResultList();
	}

	/**
	 * Return the parents {@link Right} of a {@link SocialGroup}
	 * 
	 * @param group
	 *            the {@link SocialGroup}
	 * @return the parents {@link Right} of a {@link SocialGroup}
	 */

	public Set<Right> getParentsRights(SocialGroup group) {
		Set<Right> rights = new HashSet<Right>();
		SocialGroup parent = group.getGroup();
		while (parent != null) {
			rights.addAll(parent.getRights());
			parent = parent.getGroup();
		}

		return rights;
	}

	public boolean isMasterGroup(SocialEntity member) {
		boolean resultat = false;
		TypedQuery<Long> query = em
				.createQuery(
						"SELECT count(sg) FROM SocialGroup sg WHERE sg.masterGroup.id=:id",
						Long.class);
		query.setParameter("id", member.getId());
		Long count = query.getSingleResult();
		if (count > 0)
			resultat = true;
		return resultat;
	}

	public List<SocialEntity> getAllChildMembers(SocialGroup socialGroupUser) {
		if (socialGroupUser == null) {
			throw new IllegalArgumentException();
		}
		List<SocialEntity> listSocialEntity = new ArrayList<SocialEntity>();
		if (socialGroupUser != null) {

			List<SocialGroup> listSocialGroup = getAllChildGroups(socialGroupUser);

			for (SocialGroup socialGroup : listSocialGroup) {
				List<SocialElement> socialElements = socialGroup
						.getSocialElements();
				for (SocialElement socialElement : socialElements) {
					if (socialElement instanceof SocialEntity) {
						listSocialEntity.add((SocialEntity) socialElement);
					}
				}
			}
		}

		return listSocialEntity;
	}


	public List<SocialEntity> getMembersFromGroup(SocialGroup socialGroup) {
		List<SocialEntity> members = new ArrayList<SocialEntity>();

		if (socialGroup != null) {
			List<SocialElement> socialElements = socialGroup
					.getSocialElements();
			for (SocialElement socialElement : socialElements) {
				if (socialElement instanceof SocialEntity) {
					members.add((SocialEntity) socialElement);
				}
			}
		}
		return members;
	}

	public List<SocialEntity> getMastersFromGroupAndChildGroups(
			SocialGroup socialGroup) {
		List<SocialEntity> masters = new ArrayList<SocialEntity>();
		if (socialGroup != null) {
			masters.add(socialGroup.getMasterGroup());
			for (SocialGroup element : getAllChildGroups(socialGroup)) {
				masters.add(element.getMasterGroup());
			}
		}
		return masters;
	}

	public List<SocialEntity> getPersonsWithWhoMemberCanInteract(
			SocialEntity socialEntity) {
		List<SocialEntity> persons = new ArrayList<SocialEntity>();

		if (socialEntity.getGroup() != null) {
			persons = getAllChildMembers(socialEntity.getGroup());
		}
		return persons;
	}

	public List<Integer> getIdOfThePersonsWithWhoMemberCanInteract(
			SocialEntity socialEntity) {
		List<Integer> personsIds = new ArrayList<Integer>();

		if (socialEntity.getGroup() != null) {
			List<SocialEntity> persons = getPersonsWithWhoMemberCanInteract(socialEntity);
			for (SocialEntity se : persons) {
				personsIds.add(se.getId());
			}
		}
		return personsIds;
	}



	/**
	 * Check if the {@link SocialEntity} have the specified {@link Right}
	 * @param member the {@link SocialEntity}
	 * @param right the specified {@link Right}
	 * @return if the {@link SocialEntity} have the specified {@link Right}
	 */
	public boolean isAuthorized(SocialEntity member, Right right){
		if(member == null || right == null)
			return false;
		
		SocialGroup socialGroup = member.getGroup();
		//no group, no rights
		if(socialGroup == null)
			return false;
		//super admin
		if(socialGroup.getGroup() == null && socialGroup.getMasterGroup().equals(member))
			return true;
		//regular rights
		return socialGroup.isAuthorized(right);
			
	}
	
}
