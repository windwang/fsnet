package fr.univartois.ili.fsnet.admin.actions;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.commons.security.Encryption;
import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.CommunityFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade;

/**
 * Execute CRUD Actions (and more) for the entity community
 * 
 * @author Audrey Ruellan and Cerelia Besnainou
 */
public class ManageCommunities extends MappingDispatchAction implements CrudAction {

	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");
	private static final Logger logger = Logger.getAnonymousLogger();

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet");
	}
	
	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String communityId = request.getParameter("communityId");

		logger.info("delete community: " + communityId);
		
		EntityManager em = factory.createEntityManager();
		CommunityFacade communityFacade = new CommunityFacade(em);
		em.getTransaction().begin();
		communityFacade.deleteCommunity(Integer.parseInt(communityId));
		em.getTransaction().commit();
		em.close();
		
		return mapping.findForward("success");
	
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		List<Community> result = null;
		String searchText = "";
		CommunityFacade communityFacade = new CommunityFacade(em);
		if (form != null) {
			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			searchText = (String) dynaForm.get("searchText");
			
		}
		em.getTransaction().begin();
		result = communityFacade.searchCommunity(searchText);
		em.getTransaction().commit();
		em.close();
		

		request.setAttribute("communitiesResult", result);
		return mapping.findForward("success");
	}
}
