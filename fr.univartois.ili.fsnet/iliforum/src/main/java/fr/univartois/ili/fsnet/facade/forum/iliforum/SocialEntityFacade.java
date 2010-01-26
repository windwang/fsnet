package fr.univartois.ili.fsnet.facade.forum.iliforum;

import fr.univartois.ili.fsnet.entities.SocialEntity;
import javax.persistence.EntityManager;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class SocialEntityFacade {

    private final EntityManager em;

    SocialEntityFacade(EntityManager em) {
        this.em = em;

    }

    public SocialEntity createSocialEntity(String name, String firstName, String email) {
        return null;
    }
}
