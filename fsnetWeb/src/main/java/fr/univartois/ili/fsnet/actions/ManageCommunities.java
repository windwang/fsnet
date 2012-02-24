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
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;
import org.eclipse.persistence.exceptions.DatabaseException;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.CommunityFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.InterestFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;
import fr.univartois.ili.fsnet.filter.FilterInteractionByUserGroup;

/**
 * Execute CRUD Actions (and more) for the entity community
 * 
 * @author Audrey Ruellan and Cerelia Besnainou
 */
public class ManageCommunities extends MappingDispatchAction implements
		CrudAction {

	
	private static final String SUCCES_ATTRIBUTE_NAME = "success";
	private static final String MODIFIED_COMMUNITY_NAME_FORM_FIELD_NAME = "modifiedCommunityName";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#create(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String name = (String) dynaForm.get("name");

		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity creator = UserUtils.getAuthenticatedUser(request, em);

		addRightToRequest(request);
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		if (!fascade.isAuthorized(creator, Right.CREATE_COMMUNITY)) {
			em.close();
			return new ActionRedirect(mapping.findForward("unauthorized"));
		}

		CommunityFacade communityFacade = new CommunityFacade(em);
		boolean doesNotExists = false;
		try {
			communityFacade.getCommunityByName(name);

		} catch (NoResultException e) {
			doesNotExists = true;

		}
		if (doesNotExists) {
			String InterestsIds[] = (String[]) dynaForm
					.get("selectedInterests");
			InterestFacade fac = new InterestFacade(em);
			List<Interest> interests = new ArrayList<Interest>();
			int currentId;
			for (currentId = 0; currentId < InterestsIds.length; currentId++) {
				interests.add(fac.getInterest(Integer
						.valueOf(InterestsIds[currentId])));
			}

			em.getTransaction().begin();
			Community createdCommunity = communityFacade.createCommunity(
					creator, name);
			InteractionFacade ifacade = new InteractionFacade(em);
			ifacade.addInterests(createdCommunity, interests);
			em.getTransaction().commit();
			em.close();

		} else {
			ActionErrors actionErrors = new ActionErrors();
			ActionMessage msg = new ActionMessage("communities.alreadyExists");
			actionErrors.add("createdCommunityName", msg);
			saveErrors(request, actionErrors);
		}

		dynaForm.set("name", "");

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#display(org.apache.struts.
	 * action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#delete(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String communityId = (String) dynaForm.get("communityId");
		addRightToRequest(request);
		EntityManager em = PersistenceProvider.createEntityManager();
		CommunityFacade communityFacade = new CommunityFacade(em);
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		InteractionFacade interactionFacade = new InteractionFacade(em);
		em.getTransaction().begin();
		Community community = communityFacade.getCommunity(Integer
				.parseInt(communityId));
		interactionFacade.deleteInteraction(user, community);
		community.getCreator().getInteractions().remove(community);
		if (community.getParentCommunity() != null) {
			community.getParentCommunity().getChildrenCommunities()
					.remove(community);
		}
		em.getTransaction().commit();
		em.close();

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#modify(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
		String newCommunityName = (String) dynaForm
				.get(MODIFIED_COMMUNITY_NAME_FORM_FIELD_NAME);
		String communityName = (String) dynaForm.get("modifierCommunityName");
		CommunityFacade facade = new CommunityFacade(em);
		boolean doesNotExist = false;
		Community community = facade.getCommunityByName(communityName);

		if (community != null) {

			try {
				facade.getCommunityByName(newCommunityName);
			} catch (NoResultException e) {
				doesNotExist = true;
			}
			if (doesNotExist) {
				try {
					em.getTransaction().begin();
					facade.modifyCommunity(newCommunityName, community);
					em.getTransaction().commit();
				} catch (DatabaseException ex) {
					ActionErrors actionErrors = new ActionErrors();
					ActionMessage msg = new ActionMessage(
							"communities.alreadyExists");
					actionErrors.add(MODIFIED_COMMUNITY_NAME_FORM_FIELD_NAME, msg);
					saveErrors(request, actionErrors);
					em.close();
					return mapping.findForward("failed");
				}
			} else {
				ActionErrors actionErrors = new ActionErrors();
				ActionMessage msg = new ActionMessage(
						"communities.alreadyExists");
				actionErrors.add(MODIFIED_COMMUNITY_NAME_FORM_FIELD_NAME, msg);
				saveErrors(request, actionErrors);
				em.close();
				return mapping.findForward("failed");
			}
			em.close();
		}

		dynaForm.set("modifierCommunityName", "");
		dynaForm.set(MODIFIED_COMMUNITY_NAME_FORM_FIELD_NAME, "");

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#search(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		List<Community> result = null;
		String searchText = "";
		CommunityFacade communityFacade = new CommunityFacade(em);
		addRightToRequest(request);
		if (form != null) {
			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			searchText = (String) dynaForm.get("searchText");

		}
		em.getTransaction().begin();
		result = communityFacade.searchCommunity(searchText);
		/* filter list communities */
		if (result != null && !result.isEmpty()) {
			FilterInteractionByUserGroup filter = new FilterInteractionByUserGroup(
					em);
			SocialEntity se = UserUtils.getAuthenticatedUser(request);
			result = filter.filterInteraction(se, result);
		}
		em.getTransaction().commit();
		em.close();

		request.setAttribute("communitiesSearch", result);
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
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
	public ActionForward searchYourCommunities(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// TODO use facade
		EntityManager em = PersistenceProvider.createEntityManager();
		List<Community> result = null;
		String pattern = "";
		SocialEntity creator = UserUtils.getAuthenticatedUser(request, em);
		addRightToRequest(request);
		if (form != null) {
			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			pattern = (String) dynaForm.get("searchCommunityText");

		}
		em.getTransaction().begin();

		TypedQuery<Community> query = em
				.createQuery(
						"SELECT community FROM Community community WHERE community.title LIKE :pattern AND community.creator= :creator",
						Community.class);
		query.setParameter("pattern", "%" + pattern + "%");
		query.setParameter("creator", creator);
		result = query.getResultList();

		em.getTransaction().commit();
		em.close();

		request.setAttribute("myCommunities", result);
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	/**
	 * @param request
	 */
	private void addRightToRequest(HttpServletRequest request) {
		SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request);
		Right rightCreateCommunity = Right.CREATE_COMMUNITY;
		request.setAttribute("rightCreateCommunity", rightCreateCommunity);
		request.setAttribute("socialEntity", socialEntity);
	}
}
