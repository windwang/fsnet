package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
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
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.InterestFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade;

/**
 * Execute CRUD Actions (and more) for the entity interet
 * 
 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
 */
public class ManageInterests extends MappingDispatchAction implements
		CrudAction {

	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");
	private static final Logger logger = Logger.getAnonymousLogger();

	private static final int NB_RESULTS_ON_DEMAND = 6;
	private static final int NB_RESULTS_RETURNED = NB_RESULTS_ON_DEMAND - 1;

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		InterestFacade facade = new InterestFacade(em);
		String interestName = (String) dynaForm.get("createdInterestName");

		logger.info("new interest: " + interestName);

		try {
			em.getTransaction().begin();
			facade.createInterest(interestName);
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
		EntityManager em = factory.createEntityManager();
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

		return mapping.findForward("success");
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();

		int interestId;

		if (request.getParameterMap().containsKey("removedInterestId")) {
			interestId = Integer.valueOf(request
					.getParameter("removedInterestId"));
		} else {
			DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
			interestId = Integer.valueOf((String) dynaForm
					.get("removedInterestId"));
		}

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

		return mapping.findForward("success");
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
		EntityManager em = factory.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
		String interestName = "";
		InterestFacade facade = new InterestFacade(em);

		if (dynaForm.get("searchInterestName") != null) {
			interestName = (String) dynaForm.get("searchInterestName");
		}

		logger.info("search interest: " + interestName);

		List<Interest> result = facade.advancedSearchInterest(interestName, 0,
				NB_RESULTS_ON_DEMAND);
		em.close();

		if (result.size() == NB_RESULTS_ON_DEMAND) {
			result.remove(result.size() - 1);
			request.setAttribute("hasnext", true);
		}
		request.setAttribute("interestResult", result);
		request.setAttribute("currentPage", 0);
		request.setAttribute("currentSearch", interestName);

		return mapping.findForward("success");
	}

	public ActionForward advancedSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		String interestName = "";

		if (request.getParameterMap().containsKey("search")) {
			interestName = request.getParameter("search");
		}

		InterestFacade facade = new InterestFacade(em);
		logger.info("advanced search interest: " + interestName);

		int page = 0;

		if (request.getParameterMap().containsKey("nextPage")) {
			try {
				page = Integer.valueOf(request.getParameter("nextPage"));
			} catch (NumberFormatException e) {
				page = 0;
			}
			if (page < 0) {
				page = 0;
			}
		}

		List<Interest> result = facade.advancedSearchInterest(interestName,
				page * NB_RESULTS_RETURNED, NB_RESULTS_ON_DEMAND);
		em.close();

		if (result.size() == NB_RESULTS_ON_DEMAND) {
			result.remove(result.size() - 1);
			request.setAttribute("hasnext", true);
		}
		request.setAttribute("interestResult", result);
		request.setAttribute("currentPage", page);
		request.setAttribute("currentSearch", interestName);

		return mapping.findForward("success");
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		InterestFacade facade = new InterestFacade(em);
		logger.info("Displaying interests");

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

		request.setAttribute("user", user);
		request.setAttribute("allInterests", listAllInterests);
		request.setAttribute("listInterests", finalList);

		return mapping.findForward("success");
	}

	public ActionForward informations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		InterestFacade facade = new InterestFacade(em);
		logger.info("Displaying interest's informations");

		int interestId;
		if (request.getParameterMap().containsKey("interestId")) {
			try {
				interestId = Integer.valueOf(request.getParameter("interestId"));
			} catch (NumberFormatException e) {
				interestId = 0;
			}

			Interest interest = facade.getInterest(interestId);
			HashMap<String, List<Interaction>> resultMap = facade.getInteractions(interestId);
			em.close();
			
			if (interest != null) {
				request.setAttribute("interest", interest);
				for (String interactionClass : resultMap.keySet()) {
					request.setAttribute(interactionClass, resultMap.get(interactionClass));
				}
			}
		}
		return mapping.findForward("success");
	}
}
