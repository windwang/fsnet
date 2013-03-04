package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.InterestFacade;

/**
 * @author FSNet
 * 
 */
public class ManageInteractions extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */

	private int interactionId;
	private int interestId;

	public int getInteractionId() {
		return interactionId;
	}

	public void setInteractionId(int interactionId) {
		this.interactionId = interactionId;
	}

	public int getInterestId() {
		return interestId;
	}

	public void setInterestId(int interestId) {
		this.interestId = interestId;
	}

	public String removeInterest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {

			em.getTransaction().begin();

			SocialEntity member = UserUtils.getAuthenticatedUser(request, em);

			InteractionFacade interactionFacade = new InteractionFacade(em);
			Interaction interaction = interactionFacade
					.getInteraction(interactionId);

			InterestFacade interestFacade = new InterestFacade(em);
			Interest interest = interestFacade.getInterest(interestId);

			interactionFacade.removeInterest(member, interaction, interest);
			em.getTransaction().commit();
			LOGGER.info("removeInterestOfInteraction : interactionId:"
					+ interactionId + ";interestId:" + interestId);
		} catch (NumberFormatException e) {

		} finally {
			em.close();
		}

		return request.getHeader("referer");
	}
}
