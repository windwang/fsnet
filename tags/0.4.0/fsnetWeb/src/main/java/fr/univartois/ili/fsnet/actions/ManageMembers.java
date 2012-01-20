package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade.SearchResult;

public class ManageMembers extends MappingDispatchAction {

	/**
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
                em.getTransaction().begin();
		List<SocialEntity> resultOthers = null;
		List<SocialEntity> resultContacts = null;
		List<SocialEntity> resultRequested = null;
		List<SocialEntity> resultAsked = null;

		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
	
                DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
		String searchText = (String) dynaForm.get("searchText");
           
                SocialEntityFacade sef = new SocialEntityFacade(em);
                HashMap<SearchResult, List<SocialEntity>> results = sef.searchSocialEntity(searchText, member);	
                resultContacts = results.get(SearchResult.Contacts);
		resultRequested = results.get(SearchResult.Requested);
		resultAsked = results.get(SearchResult.Asked);
		resultOthers = results.get(SearchResult.Others);
                em.getTransaction().commit();
		em.close();

		request.setAttribute("membersResult", resultOthers);
		request.setAttribute("membersContactsResult", resultContacts);
		request.setAttribute("membersRequestedResult", resultRequested);
		request.setAttribute("membersAskedResult", resultAsked);

		return mapping.findForward("success");
	}
}
