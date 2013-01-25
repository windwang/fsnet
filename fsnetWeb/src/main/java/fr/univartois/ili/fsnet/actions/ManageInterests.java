package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
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

import fr.univartois.ili.fsnet.actions.utils.FacebookKeyManager;
import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.InterestFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;

/**
 * Execute CRUD Actions (and more) for the entity interet
 * 
 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
 */
public class ManageInterests extends MappingDispatchAction implements
		CrudAction {

	private static final Logger LOGGER = Logger.getAnonymousLogger();

	private static final String SUCCES_ATTRIBUTE_NAME = "success";

	/**
	 * @param dynaForm
	 * @param facade
	 * @param interestName
	 * @param interest
	 * @param em
	 * @param request
	 * @return
	 */
	public Interest creation(DynaActionForm dynaForm, InterestFacade facade,
			String interestName, Interest interest, EntityManager em,
			HttpServletRequest request) {
		Interest res = null;
		if (dynaForm.get("parentInterestId") != null
				&& !((String) dynaForm.get("parentInterestId")).isEmpty()) {
			res = facade.createInterest(interestName,
					Integer.valueOf((String) dynaForm.get("parentInterestId")));
		} else {
			res = facade.createInterest(interestName);
		}

		return res;

	}

	/**
	 * @param request
	 */
	private void addKeyFacebookInRequest(HttpServletRequest request) {
		request.setAttribute("KEY_FACEBOOK",
				FacebookKeyManager.getKeyFacebook());
	}

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
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		InterestFacade facade = new InterestFacade(em);
		String interestName = (String) dynaForm.get("createdInterestName");
		String interestNameTmp[];
		List<Interest> mesInterets = new LinkedList<Interest>();

		LOGGER.info("new interest: " + interestName);

		try {
			Interest interest = null;
			em.getTransaction().begin();
			if (interestName.contains(";")) {
				interestNameTmp = interestName.split(";");
				for (String myInterestName : interestNameTmp) {
					interest = null;
					myInterestName = myInterestName.trim();
					if (!myInterestName.isEmpty()) {
						interest = creation(dynaForm, facade, myInterestName,
								interest, em, request);
						mesInterets.add(interest);
					}
				}
			} else {
				interest = creation(dynaForm, facade, interestName, interest,
						em, request);
			}
			em.getTransaction().commit();
			em.getTransaction().begin();
			if (interestName.contains(";")) {
				for (Interest unInteret : mesInterets) {
					addInterestToCurrentUser(request, em, unInteret.getId());
				}
			} else {
				if (interest != null) {
					addInterestToCurrentUser(request, em, interest.getId());
				}
			}
			em.getTransaction().commit();

		} catch (RollbackException ex) {
			ActionErrors actionErrors = new ActionErrors();
			ActionMessage msg = new ActionMessage("interests.alreadyExists");
			actionErrors.add("createdInterestName", msg);
			saveErrors(request, actionErrors);
		}

		em.close();

		dynaForm.set("createdInterestName", "");

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
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
		int interestId = Integer.valueOf((String) dynaForm
				.get("addedInterestId"));
		em.getTransaction().begin();
		addInterestToCurrentUser(request, em, interestId);
		em.getTransaction().commit();
		em.close();

		ActionRedirect redirect = new ActionRedirect(
				mapping.findForward(SUCCES_ATTRIBUTE_NAME));
		redirect.addParameter("infoInterestId", interestId);
		return redirect;
	}

	/**
	 * Add the interest corresponding to the id specified to the current user.
	 * 
	 * @param request
	 *            the HttpServletRequest
	 * @param em
	 *            the entity manager (You need close it after the function)
	 * @param interestId
	 *            of the interest to add to the current user.
	 */
	private void addInterestToCurrentUser(HttpServletRequest request,
			EntityManager em, int interestId) {
		SocialEntityFacade facadeSE = new SocialEntityFacade(em);
		InterestFacade facadeInterest = new InterestFacade(em);
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);

		Interest interest = facadeInterest.getInterest(interestId);
		if (interest != null) {
			LOGGER.info("add interest: id=" + interestId + " for user: "
					+ user.getName() + " " + user.getFirstName() + " "
					+ user.getId());

			facadeSE.addInterest(interest, user);
		}
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
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		int interestId = Integer.valueOf((String) dynaForm
				.get("removedInterestId"));

		try {
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);

			SocialEntityFacade facadeSE = new SocialEntityFacade(em);
			InterestFacade facadeInterest = new InterestFacade(em);

			Interest interest = facadeInterest.getInterest(interestId);
			if (interest != null) {
				LOGGER.info("remove interest: id=" + interestId + " for user: "
						+ user.getName() + " " + user.getFirstName() + " "
						+ user.getId());

				em.getTransaction().begin();
				facadeSE.removeInterest(interest, user);
				em.getTransaction().commit();
			}
		} catch (NumberFormatException e) {

		} finally {
			em.close();
		}
		
		ActionRedirect redirect = new ActionRedirect(
				mapping.findForward(SUCCES_ATTRIBUTE_NAME));
		redirect.addParameter("infoInterestId", interestId);
		return redirect;
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
		return null;
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
		return null;
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
		DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
		InterestFacade facade = new InterestFacade(em);

		String interestName = "";
		if (dynaForm.get("searchInterests") != null) {
			interestName = (String) dynaForm.get("searchInterests");
		}

		List<Interest> results = facade.searchInterest(interestName);
		em.close();

		Paginator<Interest> paginator = new Paginator<Interest>(results,
				request, 25, "search");

		request.setAttribute("interestSearchPaginator", paginator);

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
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		InterestFacade facade = new InterestFacade(em);

		addKeyFacebookInRequest(request);

		List<Interest> listAllInterests = facade.getInterests();
		List<Interest> listNonAssociatedInterests = facade
				.getNonAssociatedInterests(user);
		em.close();

		Paginator<Interest> paginatorMy = new Paginator<Interest>(
				user.getInterests(), request, 25, "display");
		Paginator<Interest> paginatorAdd = new Paginator<Interest>(
				listNonAssociatedInterests, request, 25, "addInterest");
		request.setAttribute("allInterests", listAllInterests);
		request.setAttribute("myInterestPaginator", paginatorMy);
		request.setAttribute("addInterestPaginator", paginatorAdd);

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
	public ActionForward informations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		InterestFacade facade = new InterestFacade(em);
		DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		int interestId = Integer.valueOf((String) dynaForm
				.get("infoInterestId"));

		Interest interest = facade.getInterest(interestId);
		Map<String, List<Interaction>> resultMap = facade
				.getInteractions(interestId);
		em.close();

		if (interest != null) {
			request.setAttribute("interest", interest);
			Set<SocialEntity> members = interest.getEntities();
			members.retainAll(socialGroupFacade.getAllChildMembers(UserUtils
					.getHisGroup(request)));
			request.setAttribute("socialEntities", members);
			if (user.getInterests().contains(interest)) {
				request.setAttribute("own", true);
			} else {
				request.setAttribute("own", false);
			}

			for (Map.Entry<String, List<Interaction>> interactionEntry : resultMap
					.entrySet()) {
				request.setAttribute(interactionEntry.getKey(),
						interactionEntry.getValue());
			}
		}

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}
}
