package fr.univartois.ili.fsnet.facade;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class InteractionFacade {

	private final EntityManager em;

	public InteractionFacade(EntityManager em) {
		this.em = em;

	}

	/**
	 * Add an interest to given interaction
	 * @param interaction the interaction to add the interest in
	 * @param interest the interest to add to the interaction
	 */
	public final void addInterest(Interaction interaction, Interest interest) {
		if (interaction == null || interest == null) {
			throw new IllegalArgumentException();
		}
		final Set<Interest> interests = interaction.getInterests();
		if (!interests.contains(interest)) {
			interests.add(interest);
		}
	}

	/**
	 * Add a list of interests to given interaction
	 * @param interaction the interaction to add the interest in
	 * @param interests the list of interests to add to the interaction
	 */
	public final void addInterests(Interaction interaction, List<Interest> interests) {
		if (interaction == null || interests == null) {
			throw new IllegalArgumentException();
		}
		final Set<Interest> interactionInterests = interaction.getInterests();
		interactionInterests.addAll(interests);
	}

	/**
	 * Remove an interest from a given interaction
	 * @param interaction the interaction to remove the interest from
	 * @param interests the interest to remove from the interaction
	 */
	public final void removeInterest(Interaction interaction, Interest interest) {
		if (interaction == null || interest == null) {
			throw new IllegalArgumentException();
		}
		interaction.getInterests().remove(interest);
	}


	/**
	 * delete an interaction, control if the deleter is the owner
	 * @param entity the entity who call the delete
	 * @param interaction the interaction who is deleted
	 */
	public final void deleteInteraction(SocialEntity entity,Interaction interaction){
		if(interaction == null || entity == null) {
			throw new IllegalArgumentException();
		}
		if(!interaction.getCreator().equals(entity)){
			throw new UnauthorizedOperationException("unauthorizedOperation");
		}else{
			deleteInteraction(interaction);
		}
	}

	/**
	 * delete an interaction without control of the creator
	 * @param interaction
	 */
	public final void deleteInteraction(Interaction interaction){
		if(interaction == null) {
			throw new IllegalArgumentException();
		}
		em.remove(interaction);
	}
	
	/**
	 * 
	 * @param user the user connected
	 * @return an HashMap with interaction as Key and his value contains his class and the associated action-parameter in an array
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public final HashMap<Interaction, ArrayList<String>> getLastInteractions(){
		List<Interaction> result = em.createQuery(
				"SELECT interaction FROM Interaction interaction ORDER BY interaction.lastModified DESC",
				Interaction.class).setMaxResults(10).getResultList();
		HashMap<Interaction, ArrayList<String>> resultMap = new HashMap<Interaction, ArrayList<String>>();
		
		for (Interaction interaction : result) {
			ArrayList<String> array = new ArrayList<String>();
			String clazz = interaction.getClass().getSimpleName(); 
			array.add(clazz);
			if ("Announcement".equals(clazz)) {
				array.add("/DisplayAnnounce");
				array.add("idAnnounce");
			} else if ("Meeting".equals(clazz)) {
				array.add("/DisplayEvent");
				array.add("eventId");
			} else if ("Topic".equals(clazz)) {
				array.add("/Topic");
				array.add("topicId");
			} else if ("Hub".equals(clazz)) {
				array.add("/DisplayHub");
				array.add("hubId");
			} else if ("Community".equals(clazz)) {
				array.add("/DisplayCommunity");
				array.add("communityId");
			}
			resultMap.put(interaction, array);
		}
		
		return resultMap;
	}
	
	/**
	 * @author geoffrey boulay
	 * 
	 * get one user's interactions
	 * @param user person you wish to recover interaction
	 * @return
	 */
	public final List<Interaction> getIntetactionsByUser(SocialEntity user){
		return em.createQuery(
				"SELECT interaction " +
				"FROM Interaction interaction " +
				"WHERE interaction.creator.id = :userId " +
				"ORDER BY interaction.lastModified DESC"
				, Interaction.class).setParameter("userId", Integer.valueOf(user.getId())).getResultList() ;
	}
	
}
