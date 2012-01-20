package fr.univartois.ili.fsnet.actions;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;

public class OnAuthenticationSuccess extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();
		ManagePrivateMessages.storeNumNewMessages(request, em);
		em.close();
		return mapping.findForward("success");
	}
	
}
