package fr.univartois.ili.fsnet.actions.utils;

import fr.univartois.ili.fsnet.auth.Authenticate;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class UserUtils {

    private UserUtils() {
    }
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");

    public static final SocialEntity getAuthenticatedUser(HttpServletRequest req) {
        SocialEntity user = (SocialEntity) req.getSession().getAttribute(
                Authenticate.AUTHENTICATED_USER);

        EntityManager em = factory.createEntityManager();
        user = em.find(SocialEntity.class, user.getId());
        em.close();
        return user;
    }

    public static final SocialEntity getAuthenticatedUser(HttpServletRequest req, EntityManager em) {
        SocialEntity user = (SocialEntity) req.getSession().getAttribute(
                Authenticate.AUTHENTICATED_USER);
        if (em.getTransaction().isActive()) {
            user = em.find(SocialEntity.class, user.getId());
        } else {
            em.getTransaction().begin();
            user = em.find(SocialEntity.class, user.getId());
            em.getTransaction().commit();
        }
        return user;
    }
}
