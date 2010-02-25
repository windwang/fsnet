package fr.univartois.ili.fsnet.facade.forum.iliforum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.security.UnauthorizedOperationException;

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
	 * @return the list of interests having name like interestName
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public final HashMap<String, List<Interaction>> getLastInteractions(SocialEntity user){
		HashMap<String, List<Interaction>> resultMap = new HashMap<String, List<Interaction>>();
		List<Interaction> result = em.createQuery(
				"SELECT interaction FROM Interaction interaction WHERE interaction.lastModified > :lastConnection",
				Interaction.class).setParameter("lastConnection", user.getLastConnection()).getResultList();
		for (Interaction interaction : result) {
			if (! resultMap.containsKey(interaction.getClass().getSimpleName())) {
				List<Interaction> list = new ArrayList<Interaction>();
				list.add(interaction);
				resultMap.put(interaction.getClass().getSimpleName(), list);
			} else {
				resultMap.get(interaction.getClass().getSimpleName()).add(interaction);
			}
		}
		return resultMap;
	}
}
