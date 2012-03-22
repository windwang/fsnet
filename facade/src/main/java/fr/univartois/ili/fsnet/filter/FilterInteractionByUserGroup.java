package fr.univartois.ili.fsnet.filter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.InteractionGroups;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;

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

	public final <T extends Interaction> List<T> filterInteraction(
			SocialEntity se, List<T> listInteraction) {

		List<T> listFilterInteraction = new ArrayList<T>();

		/* list of user */
		for (T interaction : listInteraction) {
			T interact = filterAnInteraction(se, interaction);
			if (interact != null) {
				listFilterInteraction.add(interact);
			}
		}

		return listFilterInteraction;
	}

	public final <T extends Interaction> T filterAnInteraction(SocialEntity se,
			T interaction) {
		
		if (interaction.getCreator().equals(se)) {
			return interaction;
		}
		/* load group user */
		SocialGroup socialGroupUser = se.getGroup();

		/* list of user */
		if (socialGroupUser != null) {
			for (InteractionGroups ig : interaction.getInteractionGroups()) {
				if (socialGroupUser.getId() == ig.getGroup().getId()) {
					return interaction;
				}
			}
		}

		return null;
	}

}
