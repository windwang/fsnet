package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import fr.univartois.ili.fsnet.entities.PrivateMessage;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class Home extends MappingDispatchAction {

	public ActionForward doDashboard(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(
				request, em);
		if (form == null) {
			List<PrivateMessage> userMessages = new ArrayList<PrivateMessage>(
					authenticatedUser.getReceivedPrivateMessages());
			Collections.reverse(userMessages);
			request.setAttribute("messages", userMessages);
		} else {
			// TODO
		}
		em.close();
		return mapping.findForward("success");
	}
	
}
