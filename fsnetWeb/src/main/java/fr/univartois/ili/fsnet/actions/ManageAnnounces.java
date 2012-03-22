package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.DateUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.PrivateMessage;
import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.AnnouncementFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.InterestFacade;
import fr.univartois.ili.fsnet.facade.PrivateMessageFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;
import fr.univartois.ili.fsnet.filter.FilterInteractionByUserGroup;

/**
 * 
 * @author Mehdi Benzaghar <mehdi.benzaghar at gmail.com>
 */
public class ManageAnnounces extends MappingDispatchAction implements
		CrudAction {

	private static final String UNAUTHORIZED_ACTION_NAME = "unauthorized";
	private static final String SUCCES_ACTION_NAME = "success";
	private static final String FAILED_ACTION_NAME = "failed";

	private static final String ANNOUNCE_ID_ATTRIBUTE_NAME = "idAnnounce";
	private static final String ANNOUNCE_TITLE_FORM_FIELD_NAME = "announceTitle";
	private static final String ANNOUNCE_CONTENT_FORM_FIELD_NAME = "announceContent";
	private static final String ANNOUNCE_EXPIRY_DATE_FORM_FIELD_NAME = "announceExpiryDate";

	/**
	 * @return to announces view after persisting new announce
	 */
	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager entityManager = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request,
				entityManager);
		SocialGroupFacade fascade = new SocialGroupFacade(entityManager);
		if (!fascade.isAuthorized(user, Right.ADD_ANNOUNCE)) {
			entityManager.close();
			return mapping.findForward(UNAUTHORIZED_ACTION_NAME);
		}

		try {
			DynaActionForm formAnnounce = (DynaActionForm) form; // NOSONAR
			String title = (String) formAnnounce
					.get(ANNOUNCE_TITLE_FORM_FIELD_NAME);
			String content = (String) formAnnounce
					.get(ANNOUNCE_CONTENT_FORM_FIELD_NAME);
			String stringExpiryDate = (String) formAnnounce
					.get(ANNOUNCE_EXPIRY_DATE_FORM_FIELD_NAME);
			String interestsIds[] = (String[]) formAnnounce
					.get("selectedInterests");

			Announcement createdAnnounce;
			addRightToRequest(request);

			Date expiryDate = DateUtils.format(stringExpiryDate);
			if (0 > DateUtils.compareToToday(expiryDate)) {
				AnnouncementFacade announcementFacade = new AnnouncementFacade(
						entityManager);
				entityManager.getTransaction().begin();
				createdAnnounce = announcementFacade.createAnnouncement(user,
						title, content, expiryDate, false);
				InterestFacade fac = new InterestFacade(entityManager);
				List<Interest> interests = new ArrayList<Interest>();
				int currentId;
				for (currentId = 0; currentId < interestsIds.length; currentId++) {
					interests.add(fac.getInterest(Integer
							.valueOf(interestsIds[currentId])));
				}
				InteractionFacade ifacade = new InteractionFacade(entityManager);
				ifacade.addInterests(createdAnnounce, interests);
				entityManager.getTransaction().commit();
			} else {
				ActionMessages errors = new ActionErrors();
				errors.add(ANNOUNCE_EXPIRY_DATE_FORM_FIELD_NAME,
						new ActionMessage("date.error.dateBelowDateToday"));
				saveErrors(request, errors);

				return mapping.findForward(FAILED_ACTION_NAME);
			}
		} catch (ParseException e) {
			servlet.log(getClass().getName()
					+ " methode:create exception when formating date ");

			return mapping.findForward(FAILED_ACTION_NAME);
		} catch (NumberFormatException e) {
			return mapping.findForward(FAILED_ACTION_NAME);
		} finally {
			entityManager.close();
		}

		return mapping.findForward(SUCCES_ACTION_NAME);
	}

	/**
	 * @return to views announce after updating it
	 */
	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException,
			UnauthorizedOperationException {
		EntityManager entityManager = PersistenceProvider.createEntityManager();

		try {
			DynaActionForm formAnnounce = (DynaActionForm) form;// NOSONAR
			String title = (String) formAnnounce
					.get(ANNOUNCE_TITLE_FORM_FIELD_NAME);
			String content = (String) formAnnounce
					.get(ANNOUNCE_CONTENT_FORM_FIELD_NAME);
			String stringExpiryDate = (String) formAnnounce
					.get(ANNOUNCE_EXPIRY_DATE_FORM_FIELD_NAME);
			Integer idAnnounce = (Integer) formAnnounce
					.get(ANNOUNCE_ID_ATTRIBUTE_NAME);
			AnnouncementFacade announcementFacade = new AnnouncementFacade(
					entityManager);
			Announcement announce = announcementFacade
					.getAnnouncement(idAnnounce);
			addRightToRequest(request);
			SocialEntity user = UserUtils.getAuthenticatedUser(request,
					entityManager);

			if (!announce.getCreator().equals(user)) {
				throw new UnauthorizedOperationException("exception.message");
			}

			Date expiryDate = DateUtils.format(stringExpiryDate);
			if (0 > DateUtils.compareToToday(expiryDate)) {
				entityManager.getTransaction().begin();
				announcementFacade.modifyAnnouncement(idAnnounce, title,
						content, expiryDate);
				entityManager.getTransaction().commit();

			} else {
				ActionMessages errors = new ActionMessages();
				errors.add(ANNOUNCE_EXPIRY_DATE_FORM_FIELD_NAME,
						new ActionMessage("date.error.dateBelowDateToday"));
				saveErrors(request, errors);
				return mapping.findForward(FAILED_ACTION_NAME);
			}

			request.setAttribute("announce", announce);
			request.setAttribute("owner", true);
		} catch (ParseException e) {
			servlet.log("class:ManageAnnounces methode:create exception whene formatying date ");
			return mapping.findForward(FAILED_ACTION_NAME);
		} finally {
			entityManager.close();
		}

		return mapping.findForward(SUCCES_ACTION_NAME);
	}

	/**
     *
     */
	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			em.getTransaction().begin();

			AnnouncementFacade announcementFacade = new AnnouncementFacade(em);
			Integer idAnnounce = Integer.valueOf(request
					.getParameter(ANNOUNCE_ID_ATTRIBUTE_NAME));
			Announcement announce = announcementFacade
					.getAnnouncement(idAnnounce);
			InteractionFacade interactionFacade = new InteractionFacade(em);
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
			addRightToRequest(request);

			if (announce != null) {
				interactionFacade.deleteInteraction(user, announce);
			}

			em.getTransaction().commit();
			
			ActionMessages message = new ActionErrors();
			message.add("message", new ActionMessage(
					"announce.message.delete.success"));
			saveMessages(request, message);
		} catch (NumberFormatException e) {
			return mapping.findForward(FAILED_ACTION_NAME);
		} finally {
			em.close();
		}

		return mapping.findForward(SUCCES_ACTION_NAME);
	}

	/**
	 * @return list of announce
	 */
	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		DynaActionForm seaarchForm = (DynaActionForm) form;// NOSONAR
		String textSearchAnnounce = (String) seaarchForm
				.get("textSearchAnnounce");
		em.getTransaction().begin();
		AnnouncementFacade announcementFacade = new AnnouncementFacade(em);

		List<Announcement> listAnnounces = announcementFacade
				.searchAnnouncement(textSearchAnnounce);
		/* filter list announce */
		if (listAnnounces != null && !listAnnounces.isEmpty()) {
			FilterInteractionByUserGroup filter = new FilterInteractionByUserGroup(
					em);
			SocialEntity se = UserUtils.getAuthenticatedUser(request);
			listAnnounces = filter.filterInteraction(se, listAnnounces);
		}

		addRightToRequest(request);

		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		List<Announcement> myAnnouncesList = announcementFacade
				.getUserAnnouncements(member);
		InteractionFacade interactionFacade = new InteractionFacade(em);
		List<Integer> unreadInteractionsId = interactionFacade
				.getUnreadInteractionsIdForSocialEntity(member);
		refreshNumNewAnnonces(request, em);
		em.getTransaction().commit();
		em.close();

		request.setAttribute("annoucesList", listAnnounces);
		request.setAttribute("myAnnouncesList", myAnnouncesList);

		request.setAttribute("unreadInteractionsId", unreadInteractionsId);

		return mapping.findForward(SUCCES_ACTION_NAME);
	}

	/**
	 * @return announce in request
	 */
	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager entityManager = PersistenceProvider.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request,
					entityManager);
			Integer idAnnounce = Integer.valueOf(request
					.getParameter(ANNOUNCE_ID_ATTRIBUTE_NAME));
			AnnouncementFacade announcementFacade = new AnnouncementFacade(
					entityManager);
			Announcement announce = announcementFacade
					.getAnnouncement(idAnnounce);
			SocialEntity socialEntityOwner = (SocialEntity) entityManager
					.createQuery(
							"SELECT es FROM SocialEntity es,IN(es.interactions) e WHERE e = :announce")
					.setParameter("announce", announce).getSingleResult();
			addRightToRequest(request);

			SocialEntity member = UserUtils.getAuthenticatedUser(request,
					entityManager);
			member.addInteractionRead(announce);
			refreshNumNewAnnonces(request, entityManager);
			entityManager.getTransaction().commit();
			entityManager.close();

			request.setAttribute("announce", announce);
			request.setAttribute("SocialEntity", socialEntityOwner);
			servlet.log(socialEntityOwner.toString()
					+ socialEntityOwner.getName());
			if (socialEntity.getId() == socialEntityOwner.getId()) {
				request.setAttribute("owner", true);
			}
		} catch (NumberFormatException e) {
			return mapping.findForward(FAILED_ACTION_NAME);
		}

		return mapping.findForward(SUCCES_ACTION_NAME);
	}

	/**
	 * @return announce in request
	 */
	public ActionForward displayToModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
			em.getTransaction().begin();
			Integer idAnnounce = Integer.valueOf(request
					.getParameter(ANNOUNCE_ID_ATTRIBUTE_NAME));

			AnnouncementFacade announcementFacade = new AnnouncementFacade(em);
			Announcement announce = announcementFacade
					.getAnnouncement(idAnnounce);
			addRightToRequest(request);
			em.getTransaction().commit();

			dynaForm.set(ANNOUNCE_ID_ATTRIBUTE_NAME, announce.getId());
			dynaForm.set(ANNOUNCE_TITLE_FORM_FIELD_NAME, announce.getTitle());
			dynaForm.set(ANNOUNCE_CONTENT_FORM_FIELD_NAME,
					announce.getContent());
			dynaForm.set(ANNOUNCE_EXPIRY_DATE_FORM_FIELD_NAME,
					DateUtils.renderDateWithHours(announce.getEndDate()));
		} catch (NumberFormatException e) {
			return mapping.findForward(FAILED_ACTION_NAME);
		} finally {
			em.close();
		}

		return mapping.findForward(SUCCES_ACTION_NAME);
	}

	private void addRightToRequest(HttpServletRequest request) {
		SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request);
		Right rightAddAnnounce = Right.ADD_ANNOUNCE;
		request.setAttribute("rightAddAnnounce", rightAddAnnounce);
		request.setAttribute("socialEntity", socialEntity);
	}

	/**
	 * Store the number of non reed announces
	 * 
	 * @param request
	 * @param em
	 */
	public static final void refreshNumNewAnnonces(HttpServletRequest request,
			EntityManager em) {
		HttpSession session = request.getSession();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		InteractionFacade inf = new InteractionFacade(em);
		List<Interaction> list = inf.getUnreadInteractionsForSocialEntity(user);
		int numNonReedAnnounces = Interaction.filter(list, Announcement.class)
				.size();
		session.setAttribute("numNonReedAnnounces", numNonReedAnnounces);
	}

	/**
	 * @author stephane gronowski Access to the jsp to create an
	 *         {@link Announcement}.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward displayCreateAnnounce(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		em.close();
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		if (!fascade.isAuthorized(user, Right.ADD_ANNOUNCE)) {
			return mapping.findForward(UNAUTHORIZED_ACTION_NAME);
		}

		return mapping.findForward(SUCCES_ACTION_NAME);
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
	public ActionForward deleteMulti(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		addRightToRequest(request);
		SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(
				request, em);
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR

		try {
			String[] selectedAnnounces = (String[]) dynaForm
					.get("selectedAnnounces");
			
			AnnouncementFacade announcementFacade = new AnnouncementFacade(em);
			InteractionFacade interactionFacade = new InteractionFacade(em);
	
			addRightToRequest(request);
			
			for (int i = 0; i < selectedAnnounces.length; i++) {
				em.getTransaction().begin();
				Announcement announce = announcementFacade
						.getAnnouncement(Integer.valueOf(selectedAnnounces[i]));
			
				addRightToRequest(request);

				if (announce != null) {
					interactionFacade.deleteInteraction(authenticatedUser,
							announce);
				}
				em.getTransaction().commit();
			}

		} finally {
			em.close();
		}

		return mapping.findForward(SUCCES_ACTION_NAME);
	}
}
