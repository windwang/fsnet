package fr.univartois.ili.fsnet.actions.utils;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import fr.univartois.ili.fsnet.auth.Authenticate;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;

/**
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class UserUtils {

	private UserUtils() {
	}

	public static final SocialEntity getAuthenticatedUser(
			HttpServletRequest request) {
		int id = getAuthenticatesUserId(request);
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		SocialEntity user = socialEntityFacade.getSocialEntity(id);
		em.close();
		return user;
	}

	public static final SocialGroup getHisGroup(HttpServletRequest request) {
		SocialEntity socialEntity = getAuthenticatedUser(request);

		return socialEntity.getGroup();
	}

	public static final Integer getAuthenticatesUserId(
			HttpServletRequest request) {
		return (Integer) request.getSession().getAttribute(
				Authenticate.AUTHENTICATED_USER);
	}

	public static final SocialEntity getAuthenticatedUser(
			HttpServletRequest request, EntityManager em) {
		int id = getAuthenticatesUserId(request);
		SocialEntity user;
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		if (em.getTransaction().isActive()) {
			user = socialEntityFacade.getSocialEntity(id);
		} else {
			em.getTransaction().begin();
			user = socialEntityFacade.getSocialEntity(id);
			em.getTransaction().commit();
		}
		return user;
	}

}
