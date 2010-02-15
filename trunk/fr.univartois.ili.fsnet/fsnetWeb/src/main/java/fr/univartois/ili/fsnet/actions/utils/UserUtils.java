package fr.univartois.ili.fsnet.actions.utils;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import fr.univartois.ili.fsnet.auth.Authenticate;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class UserUtils {

    private UserUtils() {
    }

    public static final SocialEntity getAuthenticatedUser(HttpServletRequest req) {
        SocialEntity user = (SocialEntity) req.getSession().getAttribute(
                Authenticate.AUTHENTICATED_USER);

        EntityManager em = PersistenceProvider.createEntityManager();
        SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
        user = socialEntityFacade.getSocialEntity(user.getId());
        em.close();
        return user;
    }

    public static final SocialEntity getAuthenticatedUser(HttpServletRequest req, EntityManager em) {
        SocialEntity user = (SocialEntity) req.getSession().getAttribute(
                Authenticate.AUTHENTICATED_USER);
        SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
        if (em.getTransaction().isActive()) {
            user = socialEntityFacade.getSocialEntity(user.getId());
        } else {
            em.getTransaction().begin();
            user = socialEntityFacade.getSocialEntity(user.getId());
            em.getTransaction().commit();
        }
        return user;
    }
}
