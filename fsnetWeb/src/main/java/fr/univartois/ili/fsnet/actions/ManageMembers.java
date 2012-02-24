package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade.SearchResult;

/**
 * @author FSNet
 *
 */
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
		Set<SocialEntity> resultOthers = null;
		Set<SocialEntity> resultContacts = null;
		Set<SocialEntity> resultRequested = null;
		Set<SocialEntity> resultAsked = null;

		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);

		DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
		String searchText = (String) dynaForm.getString("searchText");

		SocialEntityFacade sef = new SocialEntityFacade(em);
		SocialGroupFacade sgf = new SocialGroupFacade(em);
		List<SocialEntity> membersVisibleByCurrentMember = sgf.getPersonsWithWhoMemberCanInteract(member);
		HashMap<SearchResult, Set<SocialEntity>> results = sef
				.searchSocialEntity(searchText, member);
		resultContacts = results.get(SearchResult.Contacts);
		resultContacts.retainAll(membersVisibleByCurrentMember);
		resultRequested = results.get(SearchResult.Requested);
		resultRequested.retainAll(membersVisibleByCurrentMember);
		resultAsked = results.get(SearchResult.Asked);
		resultAsked.retainAll(membersVisibleByCurrentMember);
		resultOthers = results.get(SearchResult.Others);
		resultOthers.retainAll(membersVisibleByCurrentMember);
		em.getTransaction().commit();
		if(sgf.isMasterGroup(member)){
			request.getSession(true).setAttribute("isMasterGroup", true);
		}else{ 
			request.getSession(true).setAttribute("isMasterGroup", false);
		}
		if(sgf.isGroupResponsible(member)){
			request.getSession(true).setAttribute("isGroupResponsible", true);
		}else{ 
			request.getSession(true).setAttribute("isGroupResponsible", false);
		}
		em.close();
	

		request.setAttribute("membersResult", resultOthers);
		request.setAttribute("membersContactsResult", resultContacts);
		request.setAttribute("membersRequestedResult", resultRequested);
		request.setAttribute("membersAskedResult", resultAsked);
		
		return mapping.findForward("success");
	}
}
