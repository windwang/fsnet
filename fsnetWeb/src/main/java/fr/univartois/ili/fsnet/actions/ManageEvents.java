package fr.univartois.ili.fsnet.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

import org.apache.commons.io.FileUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.upload.FormFile;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.DateUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
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

	private static final int HOUR_IN_MINUTES = 60;
	private static final int DAY_IN_MINUTES = 1440;
	private static final String DEFAULT_RECALLTIME = "0";

	private static final String UNAUTHORIZED_ACTION_NAME = "unauthorized";
	private static final String SUCCES_ACTION_NAME = "success";
	private static final String FAILED_ACTION_NAME = "failed";

	private static final String EVENT_NAME_FORM_FIELD_NAME = "eventName";
	private static final String EVENT_DESCRIPTION_FORM_FIELD_NAME = "eventDescription";
	private static final String EVENT_ADDRESS_FORM_FIELD_NAME = "eventAddress";
	private static final String EVENT_CITY_FORM_FIELD_NAME = "eventCity";
	private static final String EVENT_BEGIN_DATE_FORM_FIELD_NAME = "eventBeginDate";
	private static final String EVENT_END_DATE_FORM_FIELD_NAME = "eventEndDate";
	private static final String EVENT_RECALL_TIME_FORM_FIELD_NAME = "eventRecallTime";
	private static final String EVENT_RECALL_TYPE_TIME_FORM_FIELD_NAME = "eventRecallTypeTime";
	private static final String EVENT_ID_ATTRIBUTE_NAME = "eventId";

	public static enum EventProperty {
		UID, DTSTART, DTEND, DESCRIPTION, SUMMARY, LOCATION,UNKNOWN;
	    public static EventProperty lookup(String text) {
	        try {
	            return valueOf(text);
	        } catch(Exception e) {
	            return UNKNOWN;
	        }
	     }

    }
	
	/**
	 * @param eventDate
	 * @param request
	 * @param propertyKey
	 * @return
	 */
	private Date validateDate(String eventDate, HttpServletRequest request,
			String propertyKey) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.HOUR_OF_DAY) - 1, 0, 0);
		Date today = calendar.getTime();

		Date typedEventDate;

		try {
			typedEventDate = DateUtils.format(eventDate);
		} catch (ParseException e) {
			ActionErrors errors = new ActionErrors();
			errors.add(propertyKey, new ActionMessage(("event.date.error")));
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

	/**
	 * @param eventDate
	 * @param request
	 * @param propertyKey
	 * @return
	 */
	private Date validateDateForEditing(String eventDate,
			HttpServletRequest request, String propertyKey) {

		Date typedEventDate;

		try {
			typedEventDate = DateUtils.format(eventDate);
		} catch (ParseException e) {
			ActionErrors errors = new ActionErrors();
			errors.add(propertyKey, new ActionMessage(("event.date.error")));
			saveErrors(request, errors);
			return null;
		}

		return typedEventDate;
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
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		if (!fascade.isAuthorized(member, Right.ADD_ANNOUNCE)) {
			em.close();
			return mapping.findForward(UNAUTHORIZED_ACTION_NAME);
		}

		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String eventName = (String) dynaForm.get(EVENT_NAME_FORM_FIELD_NAME);
		String eventDescription = (String) dynaForm
				.get(EVENT_DESCRIPTION_FORM_FIELD_NAME);
		String eventBeginDate = (String) dynaForm
				.get(EVENT_BEGIN_DATE_FORM_FIELD_NAME);
		String eventEndDate = (String) dynaForm
				.get(EVENT_END_DATE_FORM_FIELD_NAME);
		String adress = (String) dynaForm.get(EVENT_ADDRESS_FORM_FIELD_NAME);
		String city = (String) dynaForm.get(EVENT_CITY_FORM_FIELD_NAME);
		String eventRecallTime = (String) dynaForm
				.get(EVENT_RECALL_TIME_FORM_FIELD_NAME);
		String eventRecallTypeTime = (String) dynaForm
				.get(EVENT_RECALL_TYPE_TIME_FORM_FIELD_NAME);

		Date typedEventBeginDate = validateDate(eventBeginDate, request,
				EVENT_BEGIN_DATE_FORM_FIELD_NAME);
		Date typedEventEndDate = validateDate(eventEndDate, request,
				EVENT_END_DATE_FORM_FIELD_NAME);
		Date typedEventRecallDate = DateUtils.substractTimeToDate(
				typedEventBeginDate, Integer.parseInt(eventRecallTime),
				eventRecallTypeTime);

		addRightToRequest(request);
		if (typedEventBeginDate == null || typedEventEndDate == null
				|| typedEventRecallDate == null) {
			return mapping.getInputForward();
		}
		if (typedEventBeginDate.after(typedEventEndDate)) {
			ActionErrors errors = new ActionErrors();
			errors.add(EVENT_BEGIN_DATE_FORM_FIELD_NAME, new ActionMessage(
					("events.date.error")));
			errors.add(EVENT_END_DATE_FORM_FIELD_NAME, new ActionMessage(
					("events.date.error")));
			saveErrors(request, errors);

			return mapping.getInputForward();
		}
		if (DateUtils.compareToToday(typedEventRecallDate) > 0) {
			ActionErrors errors = new ActionErrors();
			errors.add(EVENT_RECALL_TIME_FORM_FIELD_NAME, new ActionMessage(
					("date.error.dateBeforeToday")));
			saveErrors(request, errors);

			return mapping.getInputForward();
		}

		em.getTransaction().begin();

		MeetingFacade meetingFacade = new MeetingFacade(em);
		Meeting event = meetingFacade.createMeeting(member, eventName,
				eventDescription, typedEventEndDate, false,
				typedEventBeginDate, adress, city, typedEventRecallDate);

		String interestsIds[] = (String[]) dynaForm.get("selectedInterests");
		InterestFacade fac = new InterestFacade(em);
		List<Interest> interests = new ArrayList<Interest>();
		int currentId;
		for (currentId = 0; currentId < interestsIds.length; currentId++) {
			interests.add(fac.getInterest(Integer
					.valueOf(interestsIds[currentId])));
		}
		InteractionFacade ifacade = new InteractionFacade(em);
		ifacade.addInterests(event, interests);
		em.getTransaction().commit();
		em.close();

		ActionRedirect redirect = new ActionRedirect(
				mapping.findForward(SUCCES_ACTION_NAME));
		redirect.addParameter(EVENT_ID_ATTRIBUTE_NAME, event.getId());
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

		try {
			EntityManager em = PersistenceProvider.createEntityManager();
			em.getTransaction().begin();

			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			String eventId = (String) dynaForm.get(EVENT_ID_ATTRIBUTE_NAME);

			String eventName = (String) dynaForm
					.get(EVENT_NAME_FORM_FIELD_NAME);
			String eventDescription = (String) dynaForm
					.get(EVENT_DESCRIPTION_FORM_FIELD_NAME);
			String eventBeginDate = (String) dynaForm
					.get(EVENT_BEGIN_DATE_FORM_FIELD_NAME);
			String eventEndDate = (String) dynaForm
					.get(EVENT_END_DATE_FORM_FIELD_NAME);
			String adress = (String) dynaForm
					.get(EVENT_ADDRESS_FORM_FIELD_NAME);
			String city = (String) dynaForm.get(EVENT_CITY_FORM_FIELD_NAME);

			String eventRecallTime = (String) dynaForm
					.get(EVENT_RECALL_TIME_FORM_FIELD_NAME);
			String eventRecallTypeTime = (String) dynaForm
					.get(EVENT_RECALL_TYPE_TIME_FORM_FIELD_NAME);
			Date typedEventBeginDate = validateDate(eventBeginDate, request,
					EVENT_BEGIN_DATE_FORM_FIELD_NAME);
			Date typedEventEndDate = validateDate(eventEndDate, request,
					EVENT_END_DATE_FORM_FIELD_NAME);

			Date typedEventRecallDate = DateUtils.substractTimeToDate(
					typedEventBeginDate, Integer.parseInt(eventRecallTime),
					eventRecallTypeTime);

			if (typedEventBeginDate == null || typedEventEndDate == null
					|| typedEventRecallDate == null) {
				return mapping.getInputForward();
			}

			if (typedEventBeginDate.after(typedEventEndDate)) {
				ActionErrors errors = new ActionErrors();
				errors.add(EVENT_BEGIN_DATE_FORM_FIELD_NAME, new ActionMessage(
						("events.date.error")));
				errors.add(EVENT_END_DATE_FORM_FIELD_NAME, new ActionMessage(
						("events.date.error")));
				saveErrors(request, errors);
				return mapping.getInputForward();
			}

			if (DateUtils.compareToToday(typedEventRecallDate) > 0) {
				ActionErrors errors = new ActionErrors();
				errors.add(EVENT_RECALL_TIME_FORM_FIELD_NAME,
						new ActionMessage(("date.error.dateBeforeToday")));
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
			meeting.setRecallDate(typedEventRecallDate);

			if (meeting.getAddress() != null) {
				meeting.getAddress().setAddress(adress);
				meeting.getAddress().setCity(city);
			}

			em.merge(meeting);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}

		return mapping.findForward(SUCCES_ACTION_NAME);
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
		String eventId = (String) dynaForm.get(EVENT_ID_ATTRIBUTE_NAME);
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		InteractionFacade interactionFacade = new InteractionFacade(em);
		MeetingFacade meetingFacade = new MeetingFacade(em);
		addRightToRequest(request);

		try {
			em.getTransaction().begin();
			Meeting meeting = meetingFacade.getMeeting(Integer
					.parseInt(eventId));
			Set<SocialEntity> followingEntitys = meeting.getFollowingEntitys();
			for (SocialEntity se : followingEntitys) {
				se.getFavoriteInteractions().remove(meeting);
			}
			InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
					em);
			interactionRoleFacade.unsubscribeAll(meeting);
			interactionFacade.deleteInteraction(user, meeting);

			em.getTransaction().commit();
		} catch (Exception e) {
			return mapping.findForward(FAILED_ACTION_NAME);
		} finally {
			em.close();
		}

		return mapping.findForward(SUCCES_ACTION_NAME);
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
			return mapping.findForward(UNAUTHORIZED_ACTION_NAME);
		}
		
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String eventId = (String) dynaForm.get(EVENT_ID_ATTRIBUTE_NAME);

		try {
			em.getTransaction().begin();
			MeetingFacade meetingFacade = new MeetingFacade(em);
			Meeting meeting = meetingFacade.getMeeting(Integer
					.parseInt(eventId));
			InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
					em);
			interactionRoleFacade.subscribe(member, meeting);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			return mapping.findForward(FAILED_ACTION_NAME);
		} finally {
			em.close();
		}

		ActionRedirect redirect = new ActionRedirect(
				mapping.findForward(SUCCES_ACTION_NAME));
		redirect.addParameter(EVENT_ID_ATTRIBUTE_NAME,
				dynaForm.get(EVENT_ID_ATTRIBUTE_NAME));
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
			return mapping.findForward(UNAUTHORIZED_ACTION_NAME);
		}
		
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String eventId = (String) dynaForm.get(EVENT_ID_ATTRIBUTE_NAME);

		try {
			em.getTransaction().begin();
			MeetingFacade meetingFacade = new MeetingFacade(em);
			Meeting meeting = meetingFacade.getMeeting(Integer
					.parseInt(eventId));
			InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
					em);
			interactionRoleFacade.unsubscribe(member, meeting);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			return mapping.findForward(FAILED_ACTION_NAME);
		} finally {
			em.close();
		}

		ActionRedirect redirect = new ActionRedirect(
				mapping.findForward(SUCCES_ACTION_NAME));
		redirect.addParameter(EVENT_ID_ATTRIBUTE_NAME,
				dynaForm.get(EVENT_ID_ATTRIBUTE_NAME));
		return redirect;
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

		return mapping.findForward(SUCCES_ACTION_NAME);
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
		
		try {
			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			String eventId = (String) dynaForm.get(EVENT_ID_ATTRIBUTE_NAME);
			addRightToRequest(request);

			em.getTransaction().begin();

			SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
			request.setAttribute("member", member);

			MeetingFacade meetingFacade = new MeetingFacade(em);

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

			// TODO find a solution to paginate a Set

			request.setAttribute("subscribers", subscribers);
			request.setAttribute("subscriber", isSubscriber);
			request.setAttribute("event", event);
		} catch (NumberFormatException e) {
			return mapping.findForward(FAILED_ACTION_NAME);
		} finally {
			em.close();
		}
		return mapping.findForward(SUCCES_ACTION_NAME);

	}

	/**
	 * @param request
	 */
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
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request,
				em);
		SocialGroupFacade fascade = new SocialGroupFacade(em);

		request.setAttribute("recallDefaultValue", DEFAULT_RECALLTIME);

		if (!fascade.isAuthorized(user, Right.ADD_EVENT)) {
			em.close();
			return mapping.findForward(UNAUTHORIZED_ACTION_NAME);
		}

		em.close();

		return new ActionRedirect(mapping.findForward(SUCCES_ACTION_NAME));
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

		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			String eventId = (String) dynaForm.get(EVENT_ID_ATTRIBUTE_NAME);
			MeetingFacade meetingFacade = new MeetingFacade(em);
			
			em.getTransaction().begin();
			Meeting event = meetingFacade.getMeeting(Integer.parseInt(eventId));

			dynaForm.set(EVENT_NAME_FORM_FIELD_NAME, event.getTitle());
			dynaForm.set(EVENT_DESCRIPTION_FORM_FIELD_NAME, event.getContent());
			if (event.getAddress() != null) {
				dynaForm.set(EVENT_ADDRESS_FORM_FIELD_NAME, event.getAddress()
						.getAddress());
				dynaForm.set(EVENT_CITY_FORM_FIELD_NAME, event.getAddress()
						.getCity());
			}

			dynaForm.set(EVENT_BEGIN_DATE_FORM_FIELD_NAME,
					DateUtils.renderDateWithHours(event.getStartDate()));
			dynaForm.set(EVENT_END_DATE_FORM_FIELD_NAME,
					DateUtils.renderDateWithHours(event.getEndDate()));

			Long recallTime = DateUtils.differenceBetweenTwoDateInMinutes(
					event.getStartDate(), event.getRecallDate());

			if (recallTime % DAY_IN_MINUTES == 0) {
				recallTime /= DAY_IN_MINUTES;
				dynaForm.set(EVENT_RECALL_TYPE_TIME_FORM_FIELD_NAME, "day");
			} else {
				if (recallTime % HOUR_IN_MINUTES == 0) {
					recallTime /= HOUR_IN_MINUTES;
					dynaForm.set(EVENT_RECALL_TYPE_TIME_FORM_FIELD_NAME, "hour");
				}
			}

			dynaForm.set(EVENT_RECALL_TIME_FORM_FIELD_NAME,
					Long.toString(recallTime));
		} catch (NumberFormatException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
			return mapping.findForward(FAILED_ACTION_NAME);
		} finally {
			em.close();
		}

		return new ActionRedirect(mapping.findForward(SUCCES_ACTION_NAME));
	}
	
	public ActionForward displayImportEvents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		/** Check for right to import events **/
		
		return mapping.findForward("success");
		
	}
	
	public ActionForward importEventsFromFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
			try {
				DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
				FormFile icsFile = (FormFile) dynaForm.get("icsFile");
				
		        if(icsFile != null) {
		        	String filePath = request.getSession().getServletContext().getRealPath("/");
		            
		            if (!icsFile.getFileName().endsWith(".ics")) {
		            	return mapping.findForward("input");
		            };
		            
		            FileOutputStream outputStream = null;
		            try {
			            outputStream = new FileOutputStream(new File(filePath,icsFile.getFileName()));
			            outputStream.write(icsFile.getFileData());
		            }
		            finally {
			            if (outputStream != null) {
			            	outputStream.close();
			            }
		            }
		            
		            FileInputStream fin = new FileInputStream(filePath+icsFile.getFileName());

		            CalendarBuilder builder = new CalendarBuilder();
		            
				net.fortuna.ical4j.model.Calendar calendar = builder.build(fin);
				
				parseCalendarAndSaveToDB(calendar,request);
		        } else {
		    		return mapping.findForward("input");
		    	}
			} catch (ParserException e) {
				e.printStackTrace();
			}

		return mapping.findForward("success");
		
	}
	
	
	/**
	 * Method that parse an ical4j calendar to retrieve events and save them to the database
	 * @return String the Action status
	 */
    public void parseCalendarAndSaveToDB(net.fortuna.ical4j.model.Calendar calendar, HttpServletRequest request) {
    	try {
    		EntityManager em = PersistenceProvider.createEntityManager();
    		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
    		em.getTransaction().begin();
	        for (Iterator i = calendar.getComponents(Component.VEVENT).iterator(); i.hasNext();) {

	        	
	            Component component = (Component) i.next();
	
	    		String eventName = "";
	    		String eventDescription = "";
	    		String eventBeginDate = "";
	    		String eventEndDate = "";
	    		String adress = "";
	    		String city = "";
	    		String eventRecallTime = "0";
	    		String eventRecallTypeTime = "";
	            
	            for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
	                Property property = (Property) j.next();

	                switch(EventProperty.lookup(property.getName())) {
	                	case UID : eventName = property.getValue(); break;
	                	case DTSTART : eventBeginDate = property.getValue(); break;
	                	case DTEND :   eventEndDate = property.getValue(); break;
	                	case DESCRIPTION : eventDescription = property.getValue(); break;
	                	case SUMMARY : eventName = property.getValue(); break;
	                	case LOCATION : adress = property.getValue(); break;
	                	default : break;
	                }
	            }
	            
	    		Date typedEventBeginDate = DateUtils.convertIcsTimestampToDate(eventBeginDate);
	    		Date typedEventEndDate = DateUtils.convertIcsTimestampToDate(eventEndDate);
	    		Date typedEventRecallDate = DateUtils.substractTimeToDate(typedEventBeginDate,Integer.parseInt(eventRecallTime),
	    				eventRecallTypeTime);
	    		
	    		
                MeetingFacade meetingFacade = new MeetingFacade(em);
        		Meeting event = meetingFacade.createMeeting(member, eventName,
        				eventDescription, typedEventEndDate, false,
        				typedEventBeginDate, adress, city,typedEventRecallDate);
	        }
	        
    		em.getTransaction().commit();
    		em.close();
    		
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
