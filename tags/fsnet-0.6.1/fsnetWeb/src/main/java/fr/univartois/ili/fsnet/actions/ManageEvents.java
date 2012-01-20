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
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.InteractionRoleFacade;
import fr.univartois.ili.fsnet.facade.InterestFacade;
import fr.univartois.ili.fsnet.facade.MeetingFacade;

/**
 * Execute CRUD Actions for the entity Event
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class ManageEvents extends MappingDispatchAction implements CrudAction {

	private Date validateDate(String eventDate, HttpServletRequest request,
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
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), 0, 0, -1);
		Date today = calendar.getTime();
		if (typedEventDate.before(today)) {
			ActionErrors errors = new ActionErrors();
			errors.add(propertyKey, new ActionMessage(("date.error.invalid")));
			saveErrors(request, errors);
			return null;
		}
		return typedEventDate;
	}

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String eventName = (String) dynaForm.get("eventName");
		String eventDescription = (String) dynaForm.get("eventDescription");
		String eventBeginDate = (String) dynaForm.get("eventBeginDate");
		String eventEndDate = (String) dynaForm.get("eventEndDate");
		// TODO !!! recuperer l'adresse et la city !!!
		String adress = "";
		String city = "";
		Date typedEventBeginDate = validateDate(eventBeginDate, request,
				"eventBeginDate");
		Date typedEventEndDate = validateDate(eventEndDate, request,
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

		EntityManager em = PersistenceProvider.createEntityManager();

		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
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
		ActionRedirect redirect = new ActionRedirect(mapping
				.findForward("success"));
		redirect.addParameter("eventId", event.getId());
		return redirect;
	}

	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO code pour la modification

		return null;
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
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String eventId = (String) dynaForm.get("eventId");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		em.getTransaction().begin();
		MeetingFacade meetingFacade = new MeetingFacade(em);
		Meeting meeting = meetingFacade.getMeeting(Integer.parseInt(eventId));
		InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
				em);
		interactionRoleFacade.subscribe(member, meeting);
		em.getTransaction().commit();
		em.close();
		ActionRedirect redirect = new ActionRedirect(mapping
				.findForward("success"));
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
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String eventId = (String) dynaForm.get("eventId");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		em.getTransaction().begin();
		MeetingFacade meetingFacade = new MeetingFacade(em);
		Meeting meeting = meetingFacade.getMeeting(Integer.parseInt(eventId));
		InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
				em);
		interactionRoleFacade.unsubscribe(member, meeting);
		em.getTransaction().commit();
		em.close();
		ActionRedirect redirect = new ActionRedirect(mapping
				.findForward("success"));
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
		em.getTransaction().begin();
		MeetingFacade meetingFacade = new MeetingFacade(em);
		List<Meeting> results = meetingFacade.searchMeeting(searchString);
		em.getTransaction().commit();
		em.close();
		
		Paginator<Meeting> paginator = new Paginator<Meeting>(results, request, "eventsLists");
		
		request.setAttribute("eventsListPaginator", paginator);
		return mapping.findForward("success");
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String eventId = (String) dynaForm.get("eventId");

		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();

		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);

		MeetingFacade meetingFacade = new MeetingFacade(em);
		Meeting event = meetingFacade.getMeeting(Integer.parseInt(eventId));

		InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
				em);
		boolean isSubscriber = interactionRoleFacade.isSubsriber(member, event);
		Set<SocialEntity> subscribers = interactionRoleFacade
				.getSubscribers(event);

		em.getTransaction().commit();
		em.close();
		
		// TODO find a solution to paginate a Set
		
		request.setAttribute("subscribers", subscribers);
		request.setAttribute("subscriber", isSubscriber);
		request.setAttribute("event", event);
		return mapping.findForward("success");
	}
}
