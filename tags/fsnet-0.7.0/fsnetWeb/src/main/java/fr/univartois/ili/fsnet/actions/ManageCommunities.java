package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.CommunityFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.InterestFacade;

/**
 * Execute CRUD Actions (and more) for the entity community
 * 
 * @author Audrey Ruellan and Cerelia Besnainou
 */
public class ManageCommunities extends MappingDispatchAction implements CrudAction {


	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String name = (String) dynaForm.get("name");

		EntityManager em = PersistenceProvider.createEntityManager();
		CommunityFacade communityFacade = new CommunityFacade(em);
		boolean doesNotExists = false;
		try{
			communityFacade.getCommunityByName(name);

		} catch (NoResultException e){
			doesNotExists = true;

		}
		if (doesNotExists) {
			String InterestsIds[] = (String[]) dynaForm.get("selectedInterests");
			InterestFacade fac = new InterestFacade(em);
			List<Interest> interests = new ArrayList<Interest>();
			int currentId;
			for (currentId = 0; currentId < InterestsIds.length; currentId++) {
				interests.add(fac.getInterest(Integer.valueOf(InterestsIds[currentId])));
			}
			
			SocialEntity creator = UserUtils.getAuthenticatedUser(request, em);
			em.getTransaction().begin();
			Community createdCommunity = communityFacade.createCommunity(creator, name);
			InteractionFacade ifacade = new InteractionFacade(em);
			ifacade.addInterests(createdCommunity, interests);
			em.getTransaction().commit();
			em.close();

		}
		else{
			ActionErrors actionErrors = new ActionErrors();
			ActionMessage msg = new ActionMessage("communities.alreadyExists");
			actionErrors.add("createdCommunityName", msg);
			saveErrors(request, actionErrors);
		}
		
		dynaForm.set("name", "");
		
		return mapping.findForward("success");
	}


	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {

		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String communityId = (String) dynaForm.get("communityId");

		EntityManager em = PersistenceProvider.createEntityManager();
		CommunityFacade communityFacade = new CommunityFacade(em);
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		InteractionFacade interactionFacade = new InteractionFacade(em);
		em.getTransaction().begin();
		Community community = communityFacade.getCommunity(Integer.parseInt(communityId));
		interactionFacade.deleteInteraction(user, community);
		community.getCreator().getInteractions().remove(community);
		if (community.getParentCommunity() != null) {
			community.getParentCommunity().getChildrenCommunities()
					.remove(community);
		}
		em.getTransaction().commit();
		em.close();

		return mapping.findForward("success");

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
		EntityManager em = PersistenceProvider.createEntityManager();
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

		Paginator<Community> paginator = new Paginator<Community>(result, request, "searchCommunity", "searchText");

		request.setAttribute("communitiesSearchPaginator", paginator);
		return mapping.findForward("success");
	}

	public ActionForward searchYourCommunities(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		//TODO use facade
		EntityManager em = PersistenceProvider.createEntityManager();
		List<Community> result = null;
		String pattern = "";
		SocialEntity creator = UserUtils.getAuthenticatedUser(request, em);

		if (form != null) {
			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			pattern = (String) dynaForm.get("searchCommunityText");

		}
		em.getTransaction().begin();

		TypedQuery<Community> query = em.createQuery("SELECT community FROM Community community WHERE community.title LIKE :pattern AND community.creator= :creator", Community.class);
		query.setParameter("pattern", "%" + pattern + "%");
		query.setParameter("creator", creator);
		result = query.getResultList();

		em.getTransaction().commit();
		em.close();

		Paginator<Community> paginator = new Paginator<Community>(result, request, 5, "myCommunities");

		request.setAttribute("myCommunitiesPaginator", paginator);
		return mapping.findForward("success");
	}
}
