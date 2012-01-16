package fr.univartois.ili.fsnet.facade;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;
import fr.univartois.ili.fsnet.filter.FilterInteractionByUserGroup;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class InteractionFacade {

	private final EntityManager em;
	
	private FilterInteractionByUserGroup filterGroup;

	public InteractionFacade(EntityManager em) {
		this.em = em;
		filterGroup=new FilterInteractionByUserGroup(em);

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
		final List<Interest> interests = interaction.getInterests();
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
		final List<Interest> interactionInterests = interaction.getInterests();
		interactionInterests.addAll(interests);
	}

	/**
	 * Remove an interest from a given interaction
	 * @param interaction the interaction to remove the interest from
	 * @param interests the interest to remove from the interaction
	 */
	public final void removeInterest(SocialEntity member, Interaction interaction, Interest interest) {
		if (interaction == null || interest == null) {
			throw new IllegalArgumentException();
		}
		if (member.equals(interaction.getCreator())) {
			interaction.getInterests().remove(interest);	
		}
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
			throw new UnauthorizedOperationException("exception.message");
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
	public final List<Triple> getLastInteractions(SocialEntity user){

		TypedQuery<Interaction> query = em.createQuery(
				"SELECT inter FROM Interaction inter, SocialEntity se, IN(se.contacts) c " +
				"WHERE inter.creator= c AND se = :user ORDER BY inter.lastModified DESC",Interaction.class);
		query.setParameter("user", user);
		query.setMaxResults(10);
		
		List<Interaction> result = query.getResultList();
		List<Triple> triples = new ArrayList<Triple>();
		
		String path = null;
		String id = null;
		for (Interaction interaction : result) {
			String clazz = interaction.getSimpleClassName();
			if ("Announcement".equals(clazz)) {
				path = "/DisplayAnnounce";
				id ="idAnnounce";
			} else if ("Meeting".equals(clazz)) {
				path = "/DisplayEvent";
				id = "eventId";
			} else if ("Topic".equals(clazz)) {
				path = "/Topic";
				id = "topicId";
			} else if ("Hub".equals(clazz)) {
				path = "/DisplayHub";
				id = "hubId";
			} else if ("Community".equals(clazz)) {
				path = "/DisplayCommunity";
				id = "communityId";
			}else if ("Consultation".equals(clazz)){
				path = "/DisplayAConsultation";
				id = "id";
			}
			triples.add(new Triple(interaction,path,id));
		}

		return triples;
	}
	public static class Triple {
		private Interaction inter;
		private String path;
		String id;
		
		public Triple(Interaction inter, String path, String id) {
			this.inter = inter;
			this.path = path;
			this.id = id;
		}
		
		public Interaction getInteraction() {
			return inter;
		}
		
		public String getPath() {
			return path;
		}
		
		public String getId() {
			return id;
		}
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

	public Interaction getInteraction(int id) {
		return em.find(Interaction.class, id);
	}
	
	public final List<Interaction> getUnreadInteractionsForSocialEntityWithoutFilter(SocialEntity se){
		if (se== null) {
			throw new IllegalArgumentException();
		}
		
		List<Interaction> list;
		list = em
		.createQuery(
				"SELECT i FROM Interaction i, SocialEntity se WHERE "
				+ "se.id = :userId AND i NOT MEMBER OF se.interactionsRead " + " ORDER BY i.creationDate DESC",
				Interaction.class).setParameter("userId",
						se.getId()).getResultList();
		
		
		return list;
	}
	
	public final List<Interaction> getUnreadInteractionsForSocialEntity(SocialEntity se){
		
		return filterGroup.filterInteraction(se, getUnreadInteractionsForSocialEntityWithoutFilter(se));
	}
	
	public final List<Integer> getUnreadInteractionsIdForSocialEntity(SocialEntity se){
		if (se== null) {
			throw new IllegalArgumentException();
		}
		
		List<Integer> list= new ArrayList<Integer>();
		List<Interaction> interactionsList = getUnreadInteractionsForSocialEntity(se);
		
		for(Interaction interaction : interactionsList){
			list.add(interaction.getId());
		}
		
		return list;
	}
	
	

}
