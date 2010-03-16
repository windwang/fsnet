package fr.univartois.ili.fsnet.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 * 
 * @author BEN ABDESSALEM Mehdi <mehdi.benabdessalem at gmail.com>
 */
public class InterestFacade {

	private final EntityManager em;

	public InterestFacade(EntityManager em) {
		this.em = em;
	}

	/**
	 * create a new interest
	 * 
	 * @param interestName
	 *            the name of the interest
	 * @return the interest created
	 */
	public final Interest createInterest(String interestName) {
		if (interestName == null)
			throw new IllegalArgumentException();
		Interest interest = new Interest(interestName.toLowerCase());
		em.persist(interest);
		return interest;
	}

	/**
	 * create a new interest with parent interest
	 * 
	 * @param interestName
	 *            the name of the interest
	 * @param parentInterestId
	 *            his parent id
	 * @return the interest created
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public final Interest createInterest(String interestName,
			int parentInterestId) {
		Interest parentInterest = getInterest(parentInterestId);
		if (interestName == null || parentInterest == null)
			throw new IllegalArgumentException();
		Interest interest = new Interest(interestName.toLowerCase(),
				parentInterest);
		em.persist(interest);
		return interest;
	}

	/**
	 * 
	 * @param interestId
	 *            the id of the interest we search
	 * @return the interest we search
	 */
	public final Interest getInterest(int interestId) {
		return em.find(Interest.class, interestId);
	}

	/**
	 * modify an interest name
	 * 
	 * @param interestName
	 *            the new interest name
	 * @param interest
	 *            the interest to modify
	 */
	public final void modifyInterest(String interestName, Interest interest) {
		if (interestName == null || interest == null)
			throw new IllegalArgumentException();
		interest.setName(interestName);
	}

	/**
	 * modify an interest name
	 * 
	 * @param interestName
	 *            the new interest name
	 * @param interest
	 *            the interest to modify
	 * @param parentInterestId
	 *            his parent id
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public final void modifyInterest(String interestName, Interest interest,
			int parentInterestId) {
		Interest parentInterest = getInterest(parentInterestId);
		if (interestName == null || interest == null || parentInterest == null)
			throw new IllegalArgumentException();
		interest.setName(interestName);
		interest.setParentInterest(parentInterest);
	}

	/**
	 * delete an interest
	 * 
	 * @param the
	 *            interest to delete
	 */
	public final void deleteInterest(Interest interest) {
		if (interest == null)
			throw new IllegalArgumentException();
		em.remove(interest);
		em.flush();
	}

	/**
	 * 
	 * @param interestName
	 *            the name of the interest we search
	 * @return the list of interests having name like interestName
	 */
	public final List<Interest> searchInterest(String interestName) {
		if (interestName == null)
			throw new IllegalArgumentException();
		List<Interest> result = em.createQuery(
				"SELECT interest FROM Interest interest "
						+ "WHERE interest.name LIKE :interestName ",
				Interest.class).setParameter("interestName",
				'%' + interestName + '%').getResultList();
		return result;
	}

	/**
	 * 
	 * @param interestName
	 *            the name of the interest we search
	 * @param begin
	 *            point of beginning for the research
	 * @param number
	 *            how many by result
	 * @return the list of interests having name like interestName
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public final List<Interest> advancedSearchInterest(String interestName,
			int begin, int number) {
		List<Interest> result = em.createQuery(
				"SELECT interest FROM Interest interest "
						+ "WHERE interest.name LIKE :interestName ",
				Interest.class).setParameter("interestName",
				'%' + interestName + '%').setFirstResult(begin).setMaxResults(
				number).getResultList();
		return result;
	}

	/**
	 * 
	 * @return the list of all interests
	 */
	public final List<Interest> getInterests() {
		List<Interest> listAllInterests = em
				.createQuery(
						"SELECT interest FROM Interest interest ORDER BY interest.name ASC",
						Interest.class).getResultList();
		return listAllInterests;
	}

	/**
	 * 
	 * @param interestId
	 * @return the list of interests having name like interestName
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public final HashMap<String, List<Interaction>> getInteractions(
			int interestId) {
		HashMap<String, List<Interaction>> resultMap = new HashMap<String, List<Interaction>>();
		List<Interaction> result = em
				.createQuery(
						"SELECT interaction "
								+ "FROM Interaction interaction, IN(interaction.interests) interest "
								+ "WHERE interest.id = :interestId",
						Interaction.class).setParameter("interestId",
						interestId).getResultList();
		for (Interaction interaction : result) {
			if (!resultMap.containsKey(interaction.getClass().getSimpleName())) {
				List<Interaction> list = new ArrayList<Interaction>();
				list.add(interaction);
				resultMap.put(interaction.getClass().getSimpleName(), list);
			} else {
				resultMap.get(interaction.getClass().getSimpleName()).add(
						interaction);
			}
		}
		return resultMap;
	}

	/**
	 * 
	 * @param socialEntity
	 * @return the list of all interests of social entity's contacts that the
	 *         social entity does not own
	 */
	public final List<Interest> getOtherInterests(SocialEntity socialEntity){
		List whole = em.createQuery(
				"SELECT interest, COUNT(contact) AS nbContacts " +
				"FROM SocialEntity soc LEFT JOIN FETCH soc.interests, IN(soc.contacts) contact, " +
				"IN(contact.interests) interest " +
				"WHERE soc = :socialEntity AND interest NOT MEMBER OF soc.interests " +
				"GROUP BY interest ORDER BY nbContacts DESC")
				.setParameter("socialEntity", socialEntity).getResultList();
		List<Interest> listAllInterests = new ArrayList<Interest>(whole.size());
		for (Object pair : whole) {
			listAllInterests.add((Interest)((Object[])pair)[0]);
		}
		return listAllInterests;
	}

	/**
	 * 
	 * @param entity
	 * @return the list of interests that entity don't have
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public final List<Interest> getNonAssociatedInterests(SocialEntity entity) {
		TypedQuery<Interest> query = em
				.createQuery(
						"SELECT DISTINCT interest "
								+ "FROM Interest interest, SocialEntity entity "
								+ "WHERE entity = :entity AND entity NOT MEMBER OF interest.entities "
								+ "ORDER BY interest.name", Interest.class);
		query.setParameter("entity", entity);
		return query.getResultList();
	}

}
