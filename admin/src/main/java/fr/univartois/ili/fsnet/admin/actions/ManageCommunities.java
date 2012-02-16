package fr.univartois.ili.fsnet.admin.actions;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
import org.apache.struts.util.MessageResources;
import org.eclipse.persistence.exceptions.DatabaseException;

import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.CommunityFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;

/**
 * Execute CRUD Actions (and more) for the entity community
 * 
 * @author Audrey Ruellan and Cerelia Besnainou
 */
public class ManageCommunities extends MappingDispatchAction implements
		CrudAction {

	private static final Logger logger = Logger.getAnonymousLogger();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#create(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String name = (String) dynaForm.get("name");
		String socialEntityId = (String) dynaForm.get("socialEntityId");

		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			em.createQuery(
					"SELECT community FROM Community community WHERE community.title LIKE :communityName",
					Community.class).setParameter("communityName", name)
					.getSingleResult();

			ActionErrors actionErrors = new ActionErrors();
			ActionMessage msg = new ActionMessage("communities.alreadyExists");
			actionErrors.add("createdCommunityName", msg);
			saveErrors(request, actionErrors);

		} catch (NoResultException e) {

			SocialEntity creator = em.find(SocialEntity.class,
					Integer.parseInt(socialEntityId));
			CommunityFacade communityFacade = new CommunityFacade(em);

			em.getTransaction().begin();
			communityFacade.createCommunity(creator, name);

			em.getTransaction().commit();
			em.close();

		}

		dynaForm.set("name", "");

		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");
		request.setAttribute("success", bundle.getMessage(request.getLocale(),
				"community.success.on.create"));

		return mapping.findForward("success");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#delete(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String communityId = request.getParameter("communityId");

		logger.info("delete community: " + communityId);

		EntityManager em = PersistenceProvider.createEntityManager();
		CommunityFacade communityFacade = new CommunityFacade(em);
		InteractionFacade interactionFacade = new InteractionFacade(em);
		em.getTransaction().begin();
		Community community = communityFacade.getCommunity(Integer
				.parseInt(communityId));
		interactionFacade.deleteInteraction(community);
		em.getTransaction().commit();
		em.close();

		return mapping.findForward("success");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#display(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
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
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#modify(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
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
				.get("modifiedCommunityName");
		String communityName = (String) dynaForm.get("modifierCommunityName");
		CommunityFacade facade = new CommunityFacade(em);
		boolean doesNotExist = false;
		Community community = facade.getCommunityByName(communityName);

		if (community != null) {
			logger.info("community modification: " + communityName);

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
					actionErrors.add("modifiedCommunityName", msg);
					saveErrors(request, actionErrors);
				}
			} else {
				ActionErrors actionErrors = new ActionErrors();
				ActionMessage msg = new ActionMessage(
						"communities.alreadyExists");
				actionErrors.add("modifiedCommunityName", msg);
				saveErrors(request, actionErrors);
			}
			em.close();
		}

		dynaForm.set("modifierCommunityName", "");
		dynaForm.set("modifiedCommunityName", "");

		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");
		request.setAttribute("success", bundle.getMessage(request.getLocale(),
				"community.success.on.modify"));

		return mapping.findForward("success");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#search(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		List<Community> result = null;
		Set<SocialEntity> allMembers = null;
		String searchText = "";
		CommunityFacade communityFacade = new CommunityFacade(em);
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		if (form != null) {
			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			searchText = (String) dynaForm.get("searchText");

		}
		em.getTransaction().begin();
		allMembers = socialEntityFacade.searchSocialEntity("");
		result = communityFacade.searchCommunity(searchText);
		em.getTransaction().commit();
		em.close();

		Paginator<Community> paginator = new Paginator<Community>(result,
				request, "communities");

		request.setAttribute("allMembers", allMembers);
		request.setAttribute("communitiesListPaginator", paginator);
		return mapping.findForward("success");
	}
}
