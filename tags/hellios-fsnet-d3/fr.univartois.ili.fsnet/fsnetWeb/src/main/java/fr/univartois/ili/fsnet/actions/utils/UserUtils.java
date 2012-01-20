package fr.univartois.ili.fsnet.actions.utils;

import fr.univartois.ili.fsnet.auth.Authenticate;
import fr.univartois.ili.fsnet.entities.EntiteSociale;
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

    public static final EntiteSociale getAuthenticatedUser(HttpServletRequest req) {
        EntiteSociale user = (EntiteSociale) req.getSession().getAttribute(
                Authenticate.AUTHENTICATED_USER);

        EntityManager em = factory.createEntityManager();
        user = em.find(EntiteSociale.class, user.getId());
        em.close();
        return user;
    }

    public static final EntiteSociale getAuthenticatedUser(HttpServletRequest req, EntityManager em) {
        EntiteSociale user = (EntiteSociale) req.getSession().getAttribute(
                Authenticate.AUTHENTICATED_USER);
        if (em.getTransaction().isActive()) {
            user = em.find(EntiteSociale.class, user.getId());
        } else {
            em.getTransaction().begin();
            user = em.find(EntiteSociale.class, user.getId());
            em.getTransaction().commit();
        }
        return user;
    }
}
