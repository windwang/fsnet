package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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

	private void lastVisits(ActionMapping mapping, HttpServletRequest request,
			HttpServletResponse response, EntityManager em,
			SocialEntity authenticatedUser) throws IOException,
			ServletException {
		ProfileVisiteFacade pvf = new ProfileVisiteFacade(em);
		List<ProfileVisite> visitors = pvf.getLastVisitor(authenticatedUser);
		request.setAttribute("visitors", visitors);
	}

	private void lastInteractions(ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response,
			EntityManager em)
			throws IOException, ServletException {
		InteractionFacade facade = new InteractionFacade(em);
		HashMap<Interaction, ArrayList<String>> result = facade
				.getLastInteractions();		
		
		request.setAttribute("lastInteractions", result);
	}

	private void lastMessages(ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response,
			EntityManager em, SocialEntity authenticatedUser)
			throws IOException, ServletException {
		List<PrivateMessage> userMessages = new ArrayList<PrivateMessage>(
				authenticatedUser.getReceivedPrivateMessages());
		Collections.reverse(userMessages);
		request.setAttribute("messages", userMessages);
	}

	private void getPropsals(ActionMapping mapping, HttpServletRequest request,
			HttpServletResponse response, EntityManager em,
			SocialEntity authenticatedUser) throws IOException,
			ServletException {
		// TODO store in request scope some proposals
	}

	public ActionForward doDashboard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(
				request, em);

		lastVisits(mapping, request, response, em, authenticatedUser);
		lastInteractions(mapping, request, response, em);
		lastMessages(mapping, request, response, em, authenticatedUser);
		getPropsals(mapping, request, response, em, authenticatedUser);

		em.close();

		return mapping.findForward("success");
	}

}
