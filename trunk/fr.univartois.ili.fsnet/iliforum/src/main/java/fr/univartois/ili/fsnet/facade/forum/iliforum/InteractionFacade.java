package fr.univartois.ili.fsnet.facade.forum.iliforum;

import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Interest;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;

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
}
