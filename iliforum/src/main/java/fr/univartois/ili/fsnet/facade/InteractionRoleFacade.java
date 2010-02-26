package fr.univartois.ili.fsnet.facade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.InteractionRole;
import fr.univartois.ili.fsnet.entities.InteractionRolePK;
import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 *
 * @author Mehdi
 */
public class InteractionRoleFacade {

    private final EntityManager em;

    public InteractionRoleFacade(EntityManager em){
        this.em = em;
    }

    /**
     * Subscribe to an interaction
     * @param se
     * @param i
     */
    public final void subscribe(SocialEntity se, Interaction i) {
        if (se == null || i == null) {
            throw new IllegalArgumentException();
        }
        InteractionRole interactionRole = new InteractionRole(se, i);
        interactionRole.setRole(InteractionRole.RoleName.SUBSCRIBER);
        se.getRolesInInteractions().add(interactionRole);
        i.getRoles().add(interactionRole);
        em.persist(interactionRole);
    }

    /**
     * Unsubscribe from an interaction
     * @param se
     * @param i
     */
    public final void unsubscribe(SocialEntity se, Interaction i) {
        if (se == null || i == null) {
            throw new IllegalArgumentException();
        }
        InteractionRolePK interactionRolePK = new InteractionRolePK();
        interactionRolePK.setInteractionId(i.getId());
        interactionRolePK.setSocialEntityId(se.getId());
        InteractionRole interactionRole = em.find(InteractionRole.class, interactionRolePK);
        se.getRolesInInteractions().remove(interactionRole);
        i.getRoles().remove(interactionRole);
        em.remove(interactionRole);
    }

    /**
     * 
     * @param i
     */
    public final void unsubscribeAll(Interaction i){
        if(i == null) throw new IllegalArgumentException();
        List<SocialEntity> socialEntities = new ArrayList<SocialEntity>();
        for(InteractionRole interactionRole : i.getRoles()){
            socialEntities.add(interactionRole.getSocialEntity());
        }
        for(SocialEntity se : socialEntities){
            unsubscribe(se, i);
        }
    }

    /**
     *
     * @param se
     * @param i
     * @return true if se subscribed to i
     */
    public final boolean isSubsriber(SocialEntity se, Interaction i) {
        if (se == null || i == null) {
            throw new IllegalArgumentException();
        }
        for (InteractionRole interactionRole : se.getRolesInInteractions()) {
            if (interactionRole.getInteraction().equals(i)
                    && interactionRole.getRole().equals(InteractionRole.RoleName.SUBSCRIBER)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param se
     * @param i
     * @return true if se is the decision maker for i
     */
    public final boolean isDecisionMaker(SocialEntity se, Interaction i) {
        if (se == null || i == null) {
            throw new IllegalArgumentException();
        }
        for (InteractionRole interactionRole : se.getRolesInInteractions()) {
            if (interactionRole.getInteraction().equals(i)
                    && interactionRole.getRole().equals(InteractionRole.RoleName.DECISION_MAKER)) {
                return true;
            }
        }
        return false;
    }

}
