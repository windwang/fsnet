package fr.univartois.ili.fsnet.actions;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.String;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.ActionSupport;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 * @author FSNet
 * 
 */
public class ManageFavorites extends ActionSupport {

	private static final String INTERACTION_ID_FORM_FIELD_NAME = "interactionId";
	private static final String SUCCESS_ACTION_NAME = "success";

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String add(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			int interactionId = Integer.parseInt((String) dynaForm
					.get(INTERACTION_ID_FORM_FIELD_NAME));
			em.getTransaction().begin();
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
			Interaction interaction = em.find(Interaction.class, interactionId);
			if (interaction != null) {
				user.getFavoriteInteractions().add(interaction);
				interaction.getFollowingEntitys().add(user);
			}
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			return null;
		} finally {
			em.close();
		}

		return null;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String remove(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			int interactionId = Integer.parseInt((String) dynaForm
					.get(INTERACTION_ID_FORM_FIELD_NAME));
			em.getTransaction().begin();

			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
			Interaction interaction = em.find(Interaction.class, interactionId);

			if (interaction != null) {
				interaction.getFollowingEntitys().remove(user);
				user.getFavoriteInteractions().remove(interaction);
				em.flush();
			}
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			return null;
		} finally {
			em.close();
		}

		return null;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String display(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			int interactionId = Integer.parseInt((String) dynaForm
					.get(INTERACTION_ID_FORM_FIELD_NAME));
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
			Interaction interaction = em.find(Interaction.class, interactionId);
			if (user.getFavoriteInteractions().contains(interaction)) {
				request.setAttribute("isFavorite", true);
			} else {
				request.setAttribute("isFavorite", false);
			}
			request.setAttribute(INTERACTION_ID_FORM_FIELD_NAME,
					interaction.getId());
		} catch (NumberFormatException e) {
			return mapping.findForward(SUCCESS_ACTION_NAME);
		} finally {
			em.close();
		}
		
		return mapping.findForward(SUCCESS_ACTION_NAME);
	}
}
