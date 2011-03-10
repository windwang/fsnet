package fr.univartois.ili.fsnet.filter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.SocialElement;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;


/*
 * author : J FLAMEN
 * Filter Interaction by belong user group
 */
public class FilterInteractionByUserGroup {
	
	private final EntityManager em;

	/**
	 * Contructor
	 * 
	 * @param em
	 */
	public FilterInteractionByUserGroup(EntityManager em) {
		this.em = em;
	}
	
	
	
	public final <T>List  filterInteraction(SocialEntity se, List<? extends  Interaction> listInteraction) {
		/* load group user */
		SocialGroup socialGroupUser = se.getGroup();
		/* list of user */
		List<SocialEntity> listSocialEntity = new ArrayList<SocialEntity>();
		if(socialGroupUser != null) {
			/* load group of group */
			SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
			List<SocialGroup> listSocialGroup = socialGroupFacade.AllGroupChild(socialGroupUser);
			/* add socialGroup of user */
			listSocialGroup.add(socialGroupUser);
			for (SocialGroup socialGroup : listSocialGroup) {
				List<SocialElement> socialElements = socialGroup.getSocialElements();
				for (SocialElement socialElement : socialElements) {
					if (socialElement instanceof SocialEntity) {
						listSocialEntity.add((SocialEntity) socialElement);
					}
				}
			}
		}
		/* user is not attached on group, alone */
		listSocialEntity.add(se);
		/* filter interaction */
		for (Interaction interaction : listInteraction) {
			if(!listSocialEntity.contains(interaction.getCreator()))
				listInteraction.remove(interaction);
		}
		return listInteraction;
		
	}
	

}
