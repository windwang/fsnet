package fr.univartois.ili.fsnet.facade.forum.iliforum;

import java.util.List;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.Interest;

/**
 *
 * @author BEN ABDESSALEM Mehdi <mehdi.benabdessalem at gmail.com>
 */
public class InterestFacade {

	private final EntityManager em;

	public InterestFacade(EntityManager em){
		this.em = em;
	}

	/**
	 * create a new interest
	 * @param interestName the name of the interest
	 * @return the interest created
	 */
	public Interest createInterest(String interestName){
		Interest interest = new Interest(interestName);
		em.persist(interest);
		return interest;
	}

	/**
	 * 
	 * @param interestId the id of the interest we search
	 * @return the interest we search
	 */
	public Interest getInterest(int interestId){
		return em.find(Interest.class, interestId);
	}

	/**
	 * modify an interest name
	 * @param interestName the new interest name
	 * @param interestId the id of the interest to modify
	 */
	public void modifyInterest(String interestName, int interestId){
            //TODO NPE
		Interest interest = getInterest(interestId);
		interest.setName(interestName);	
	}

	/**
	 * delete an interest 
	 * @param interestId the id of the interest to delete
	 */
	public void deleteInterest(int interestId){
		Interest interest = getInterest(interestId);
		em.remove(interest);
		em.flush();
	}

	/**
	 * 
	 * @param interestName the name of the interest we search
	 * @return the list of interests having name like interestName
	 */
	public List<Interest> searchInterest(String interestName){
            //TODO NPE
		List<Interest> result = em.createQuery(
				"SELECT interest FROM Interest interest "
				+ "WHERE interest.name LIKE :interestName ",
				Interest.class).setParameter("interestName",
						'%' + interestName + '%').getResultList();
		return result;
	}

	/**
	 * 
	 * @return the list of all interests
	 */
	public List<Interest> getInterests(){
		List<Interest> listAllInterests = em.createQuery(
				"SELECT interest FROM Interest interest", Interest.class).getResultList();
		return listAllInterests;
	}
	
	

}
