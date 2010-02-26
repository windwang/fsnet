package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.PrivateMessage;
import fr.univartois.ili.fsnet.entities.ProfileVisite;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.InteractionFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.ProfileVisiteFacade;

public class Home extends MappingDispatchAction {

	public ActionForward doDashboard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(
				request, em);
		InteractionFacade facade = new InteractionFacade(em);
		ProfileVisiteFacade pvf = new ProfileVisiteFacade(em);
		em.getTransaction().begin();
		List<ProfileVisite> visitors = pvf.getLastVisitor(authenticatedUser);
		em.getTransaction().commit();
		request.setAttribute("visitors", visitors);
		if (form == null) {
			List<PrivateMessage> userMessages = new ArrayList<PrivateMessage>(
					authenticatedUser.getReceivedPrivateMessages());
			Collections.reverse(userMessages);
			request.setAttribute("messages", userMessages);

			
			// TODO possible de faire variable.class dans JSP ? trouver un moyen  de trier par avance dans le cas contraire
			HashMap<String, List<Interaction>> resultMap = facade
					.getLastInteractions(authenticatedUser);

			for (Map.Entry<String, List<Interaction>> interactionEntry : resultMap
					.entrySet()) {
				request.setAttribute(interactionEntry.getKey(),
						interactionEntry.getValue());
			}
		} else {
			// TODO
		}
		em.close();
		return mapping.findForward("success");
	}

}
