package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.InterestFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;

/**
 * Execute CRUD Actions (and more) for the entity interet
 * 
 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
 */
public class ManageInterests extends MappingDispatchAction implements
		CrudAction {

	private static final Logger logger = Logger.getAnonymousLogger();


	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		InterestFacade facade = new InterestFacade(em);
		String interestName = (String) dynaForm.get("createdInterestName");

		logger.info("new interest: " + interestName);

		try {
			em.getTransaction().begin();
			if (dynaForm.get("parentInterestId") != null
					&& !((String) dynaForm.get("parentInterestId")).isEmpty()) {
				facade.createInterest(interestName, Integer
						.valueOf((String) dynaForm.get("parentInterestId")));
			} else {
				facade.createInterest(interestName);
			}

			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ActionErrors actionErrors = new ActionErrors();
			ActionMessage msg = new ActionMessage("interest.alreadyExists");
			actionErrors.add("createdInterestName", msg);
			saveErrors(request, actionErrors);
		}

		em.close();

		return mapping.findForward("success");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
		int interestId = Integer.valueOf((String) dynaForm
				.get("addedInterestId"));

		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);

		SocialEntityFacade facadeSE = new SocialEntityFacade(em);
		InterestFacade facadeInterest = new InterestFacade(em);

		Interest interest = facadeInterest.getInterest(interestId);
		if (interest != null) {
			logger.info("add interest: id=" + interestId + " for user: "
					+ user.getName() + " " + user.getFirstName() + " "
					+ user.getId());

			em.getTransaction().begin();
			facadeSE.addInterest(interest, user);
			em.getTransaction().commit();
		}
		em.close();
		ActionRedirect redirect = new ActionRedirect(mapping.findForward("success"));
		redirect.addParameter("infoInterestId", interestId);
		return redirect;
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		int interestId = Integer.valueOf((String) dynaForm
				.get("removedInterestId"));

		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);

		SocialEntityFacade facadeSE = new SocialEntityFacade(em);
		InterestFacade facadeInterest = new InterestFacade(em);

		Interest interest = facadeInterest.getInterest(interestId);
		if (interest != null) {
			logger.info("remove interest: id=" + interestId + " for user: "
					+ user.getName() + " " + user.getFirstName() + " "
					+ user.getId());

			em.getTransaction().begin();
			facadeSE.removeInterest(interest, user);
			em.getTransaction().commit();
		}
		em.close();
		ActionRedirect redirect = new ActionRedirect(mapping.findForward("success"));
		redirect.addParameter("infoInterestId", interestId);
		return redirect;
	}

	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return null;
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return null;
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
		InterestFacade facade = new InterestFacade(em);
		
		String interestName = "";
		if (dynaForm.get("requestInput") != null) {
			interestName = (String) dynaForm.get("requestInput");
		}
		
		
		List<Interest> results = facade.searchInterest(interestName);
		em.close();
		
		Paginator<Interest> paginator = new Paginator<Interest>(results, request, 25, "search");

		request.setAttribute("interestSearchPaginator", paginator);

		return mapping.findForward("success");
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		InterestFacade facade = new InterestFacade(em);


		List<Interest> listAllInterests = facade.getInterests();
		em.close();

		// TODO remplacer avec une vraie requete si possible

		List<Interest> finalList = new ArrayList<Interest>();
		boolean dirtyIsOK;
		for (Interest interest : listAllInterests) {
			dirtyIsOK = true;
			for (Interest interestEntity : user.getInterests()) {
				if (interestEntity.getId() == interest.getId()) {
					dirtyIsOK = false;
				}
			}
			if (dirtyIsOK) {
				finalList.add(interest);
			}
		}

		Paginator<Interest> paginatorMy = new Paginator<Interest>(user.getInterests(), request, 25, "display");
		Paginator<Interest> paginatorAdd = new Paginator<Interest>(finalList, request, 25, "addInterest");
		
		request.setAttribute("allInterests", listAllInterests);
		request.setAttribute("myInterestPaginator", paginatorMy);
		request.setAttribute("addInterestPaginator", paginatorAdd);

		return mapping.findForward("success");
	}

	public ActionForward informations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		InterestFacade facade = new InterestFacade(em);
		DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		
		int interestId = Integer.valueOf((String) dynaForm
				.get("infoInterestId"));

		Interest interest = facade.getInterest(interestId);
		HashMap<String, List<Interaction>> resultMap = facade
				.getInteractions(interestId);
		em.close();

		if (interest != null) {
			request.setAttribute("interest", interest);
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

		return mapping.findForward("success");
	}
}
