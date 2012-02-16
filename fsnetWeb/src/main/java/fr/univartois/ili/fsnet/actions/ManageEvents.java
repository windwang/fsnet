package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import fr.univartois.ili.fsnet.commons.utils.DateUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.InteractionRoleFacade;
import fr.univartois.ili.fsnet.facade.InterestFacade;
import fr.univartois.ili.fsnet.facade.MeetingFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;
import fr.univartois.ili.fsnet.filter.FilterInteractionByUserGroup;

/**
 * Execute CRUD Actions for the entity Event
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class ManageEvents extends MappingDispatchAction implements CrudAction {

	private Date validateDate(String eventDate, HttpServletRequest request,
			String propertyKey) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), 0, 0, -1);
		Date today = calendar.getTime();

		Date typedEventDate;

		try {
			typedEventDate = DateUtils.format(eventDate);
		} catch (ParseException e) {
			ActionErrors errors = new ActionErrors();
			errors.add(propertyKey, new ActionMessage(("event.date.errors")));
			saveErrors(request, errors);
			return null;
		}

		if (typedEventDate.before(today)) {
			ActionErrors errors = new ActionErrors();
			errors.add(propertyKey, new ActionMessage(
					("date.error.dateBeforeToday")));
			saveErrors(request, errors);
			return null;
		}
		return typedEventDate;
	}
	
	private Date validateDateForEditing(String eventDate, HttpServletRequest request,
			String propertyKey) {

		Date typedEventDate;

		try {
			typedEventDate = DateUtils.format(eventDate);
		} catch (ParseException e) {
			ActionErrors errors = new ActionErrors();
			errors.add(propertyKey, new ActionMessage(("event.date.errors")));
			saveErrors(request, errors);
			return null;
		}

		return typedEventDate;
	}

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		if (!fascade.isAuthorized(member, Right.ADD_ANNOUNCE)) {
			em.close();
			return new ActionRedirect(mapping.findForward("unauthorized"));
		}

		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String eventName = (String) dynaForm.get("eventName");
		String eventDescription = (String) dynaForm.get("eventDescription");
		String eventBeginDate = (String) dynaForm.get("eventBeginDate");
		String eventEndDate = (String) dynaForm.get("eventEndDate");
		String adress = (String) dynaForm.get("eventAddress");
		String city = (String) dynaForm.get("eventCity");
		Date typedEventBeginDate = validateDate(eventBeginDate, request,
				"eventBeginDate");
		Date typedEventEndDate = validateDate(eventEndDate, request,
				"eventEndDate");
		addRightToRequest(request);
		if (typedEventBeginDate == null || typedEventEndDate == null) {
			return mapping.getInputForward();
		}
		if (typedEventBeginDate.after(typedEventEndDate)) {
			ActionErrors errors = new ActionErrors();
			errors.add("eventBeginDate", new ActionMessage(("events.21")));
			errors.add("eventEndDate", new ActionMessage(("events.21")));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
		
		em.getTransaction().begin();

		MeetingFacade meetingFacade = new MeetingFacade(em);
		Meeting event = meetingFacade.createMeeting(member, eventName,
				eventDescription, typedEventEndDate, false,
				typedEventBeginDate, adress, city);

		String InterestsIds[] = (String[]) dynaForm.get("selectedInterests");
		InterestFacade fac = new InterestFacade(em);
		List<Interest> interests = new ArrayList<Interest>();
		int currentId;
		for (currentId = 0; currentId < InterestsIds.length; currentId++) {
			interests.add(fac.getInterest(Integer
					.valueOf(InterestsIds[currentId])));
		}
		InteractionFacade ifacade = new InteractionFacade(em);
		ifacade.addInterests(event, interests);
		em.getTransaction().commit();
		em.close();
		ActionRedirect redirect = new ActionRedirect(
				mapping.findForward("success"));
		redirect.addParameter("eventId", event.getId());
		return redirect;
	}

	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		try {
			EntityManager em = PersistenceProvider.createEntityManager();
			em.getTransaction().begin();
			
			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			String eventId = (String) dynaForm.get("eventId");

			String eventName = (String) dynaForm.get("eventName");
			String eventDescription = (String) dynaForm.get("eventDescription");
			String eventBeginDate = (String) dynaForm.get("eventBeginDate");
			String eventEndDate = (String) dynaForm.get("eventEndDate");
			String adress = (String) dynaForm.get("eventAddress");
			String city = (String) dynaForm.get("eventCity");
			Date typedEventBeginDate = validateDateForEditing(eventBeginDate, request,
					"eventBeginDate");
			Date typedEventEndDate = validateDateForEditing(eventEndDate, request,
					"eventEndDate");

			if (typedEventBeginDate == null || typedEventEndDate == null) {
				return mapping.getInputForward();
			}
			if (typedEventBeginDate.after(typedEventEndDate)) {
				ActionErrors errors = new ActionErrors();
				errors.add("eventBeginDate", new ActionMessage(("events.21")));
				errors.add("eventEndDate", new ActionMessage(("events.21")));
				saveErrors(request, errors);
				return mapping.getInputForward();
			}
			
			MeetingFacade meetingFacade = new MeetingFacade(em);
			Meeting meeting = meetingFacade.getMeeting(Integer
					.parseInt(eventId));

			meeting.setContent(eventDescription);
			meeting.setTitle(eventName);
			meeting.setStartDate(typedEventBeginDate);
			meeting.setEndDate(typedEventEndDate);

			if (meeting.getAddress() != null) {
				meeting.getAddress().setAddress(adress);
				meeting.getAddress().setCity(city);
			}

			em.merge(meeting);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("success");
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String eventId = (String) dynaForm.get("eventId");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		InteractionFacade interactionFacade = new InteractionFacade(em);
		MeetingFacade meetingFacade = new MeetingFacade(em);
		addRightToRequest(request);
		em.getTransaction().begin();
		Meeting meeting = meetingFacade.getMeeting(Integer.parseInt(eventId));
		Set<SocialEntity> followingEntitys = meeting.getFollowingEntitys();
		for (SocialEntity se : followingEntitys) {
			se.getFavoriteInteractions().remove(meeting);
		}
		InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
				em);
		interactionRoleFacade.unsubscribeAll(meeting);
		interactionFacade.deleteInteraction(user, meeting);
		try {
			em.getTransaction().commit();
		} catch (RollbackException e) {
			servlet.log("no commit", e);
		}
		em.close();

		return mapping.findForward("success");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward subscribe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		addRightToRequest(request);
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		if (!fascade.isAuthorized(member, Right.REGISTER_EVENT)) {
			em.close();
			return new ActionRedirect(mapping.findForward("unauthorized"));
		}
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String eventId = (String) dynaForm.get("eventId");
		try {
			em.getTransaction().begin();
			MeetingFacade meetingFacade = new MeetingFacade(em);
			Meeting meeting = meetingFacade.getMeeting(Integer
					.parseInt(eventId));
			InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
					em);
			interactionRoleFacade.subscribe(member, meeting);
			em.getTransaction().commit();
		} catch (RollbackException e) {
			e.printStackTrace();
		}
		em.close();
		ActionRedirect redirect = new ActionRedirect(
				mapping.findForward("success"));
		redirect.addParameter("eventId", dynaForm.get("eventId"));
		return redirect;
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward unsubscribe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		addRightToRequest(request);
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		if (!fascade.isAuthorized(member, Right.REGISTER_EVENT)) {
			em.close();
			return new ActionRedirect(mapping.findForward("unauthorized"));
		}
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String eventId = (String) dynaForm.get("eventId");
		em.getTransaction().begin();
		MeetingFacade meetingFacade = new MeetingFacade(em);
		Meeting meeting = meetingFacade.getMeeting(Integer.parseInt(eventId));
		InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
				em);
		interactionRoleFacade.unsubscribe(member, meeting);
		em.getTransaction().commit();
		em.close();
		ActionRedirect redirect = new ActionRedirect(
				mapping.findForward("success"));
		redirect.addParameter("eventId", dynaForm.get("eventId"));
		return redirect;
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm searchForm = (DynaActionForm) form; // NOSONAR
		String searchString = (String) searchForm.get("searchString");

		EntityManager em = PersistenceProvider.createEntityManager();
		addRightToRequest(request);

		em.getTransaction().begin();

		MeetingFacade meetingFacade = new MeetingFacade(em);
		List<Meeting> results = meetingFacade.searchMeeting(searchString);

		if (results != null && !results.isEmpty()) {
			FilterInteractionByUserGroup filter = new FilterInteractionByUserGroup(
					em);
			SocialEntity se = UserUtils.getAuthenticatedUser(request);
			results = filter.filterInteraction(se, results);
		}

		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		InteractionFacade interactionFacade = new InteractionFacade(em);
		List<Integer> unreadInteractionsId = interactionFacade
				.getUnreadInteractionsIdForSocialEntity(member);
		refreshNumNewEvents(request, em);

		em.getTransaction().commit();
		em.close();

		request.setAttribute("eventsList", results);
		request.setAttribute("unreadInteractionsId", unreadInteractionsId);
		return mapping.findForward("success");
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String eventId = (String) dynaForm.get("eventId");
		EntityManager em = PersistenceProvider.createEntityManager();
		addRightToRequest(request);
		em.getTransaction().begin();

		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		request.setAttribute("member", member);

		MeetingFacade meetingFacade = new MeetingFacade(em);
		try {
			Meeting event = meetingFacade.getMeeting(Integer.parseInt(eventId));
			member.addInteractionRead(event);

			InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
					em);
			boolean isSubscriber = interactionRoleFacade.isSubsriber(member,
					event);
			Set<SocialEntity> subscribers = interactionRoleFacade
					.getSubscribers(event);

			refreshNumNewEvents(request, em);
			em.getTransaction().commit();

			em.close();

			// TODO find a solution to paginate a Set

			request.setAttribute("subscribers", subscribers);
			request.setAttribute("subscriber", isSubscriber);
			request.setAttribute("event", event);
		} catch (NumberFormatException e) {
		}
		return mapping.findForward("success");

	}

	private void addRightToRequest(HttpServletRequest request) {
		SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request);
		Right rightAddEvent = Right.ADD_EVENT;
		Right rightRegisterEvent = Right.REGISTER_EVENT;
		request.setAttribute("rightAddEvent", rightAddEvent);
		request.setAttribute("rightRegisterEvent", rightRegisterEvent);
		request.setAttribute("socialEntity", socialEntity);
	}

	/**
	 * Store the number of non reed events
	 * 
	 * @param request
	 * @param em
	 */
	public static final void refreshNumNewEvents(HttpServletRequest request,
			EntityManager em) {
		HttpSession session = request.getSession();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		InteractionFacade inf = new InteractionFacade(em);
		List<Interaction> list = inf.getUnreadInteractionsForSocialEntity(user);
		int numNonReedEvents = Interaction.filter(list, Meeting.class).size();
		session.setAttribute("numNonReedEvents", numNonReedEvents);
	}

	/**
	 * @author stephane gronowski Access to the jsp to create an {@link Meeting}
	 *         .
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward displayCreateEvent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		EntityManager entityManager = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request,
				entityManager);
		SocialGroupFacade fascade = new SocialGroupFacade(entityManager);
		if (!fascade.isAuthorized(user, Right.ADD_EVENT)) {
			entityManager.close();
			return new ActionRedirect(mapping.findForward("unauthorized"));
		}
		entityManager.close();

		return new ActionRedirect(mapping.findForward("success"));
	}

	/**
	 * @author lahoucine aitelhaj Access to the jsp to edit a {@link Meeting}.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward displayToModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		// TODO check for right to update an event

		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String eventId = (String) dynaForm.get("eventId");
		EntityManager em = PersistenceProvider.createEntityManager();

		MeetingFacade meetingFacade = new MeetingFacade(em);
		try {
			em.getTransaction().begin();
			Meeting event = meetingFacade.getMeeting(Integer.parseInt(eventId));

			dynaForm.set("eventName", event.getTitle());
			dynaForm.set("eventDescription", event.getContent());
			if (event.getAddress() != null) {
				dynaForm.set("eventAddress", event.getAddress().getAddress());
				dynaForm.set("eventCity", event.getAddress().getCity());
			}

			dynaForm.set("eventBeginDate",
					DateUtils.renderDateWithHours(event.getStartDate()));
			dynaForm.set("eventEndDate",
					DateUtils.renderDateWithHours(event.getEndDate()));
			em.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return new ActionRedirect(mapping.findForward("success"));
	}

}
