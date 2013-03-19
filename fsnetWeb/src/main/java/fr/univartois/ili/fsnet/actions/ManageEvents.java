package fr.univartois.ili.fsnet.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

import com.opensymphony.xwork2.ActionSupport;

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
public class ManageEvents extends ActionSupport implements CrudAction,
		ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int HOUR_IN_MINUTES = 60;
	private static final int DAY_IN_MINUTES = 1440;
	private static final String DEFAULT_RECALLTIME = "0";

	private static final String UNAUTHORIZED_ACTION_NAME = "unauthorized";
	private static final String FAILED_ACTION_NAME = "failed";

	private static final String EVENT_BEGIN_DATE_FORM_FIELD_NAME = "eventBeginDate";
	private static final String EVENT_END_DATE_FORM_FIELD_NAME = "eventEndDate";
	private static final String EVENT_RECALL_TIME_FORM_FIELD_NAME = "eventRecallTime";
	private static final String ERROR_ON_DATE_MESSAGE = "events.date.error";

	private String eventName;
	private String eventDescription;
	private String eventAddress;
	private String eventCity;
	private Date eventBeginDate;
	private Date eventEndDate;
	private int eventId;
	private List<String> selectedInterests;
	private String[] selectedEvents;
	private String searchString;
	private int eventRecallTime;
	private String eventRecallTypeTime;
	private File icsFile;
	private String icsFileContentType;
	private String icsFileFileName;

	private HttpServletRequest request;

	public static enum EventProperty {
		UID, DTSTART, DTEND, DESCRIPTION, SUMMARY, LOCATION, UNKNOWN;
		public static EventProperty lookup(String text) {
			try {
				return valueOf(text);
			} catch (Exception e) {
				return UNKNOWN;
			}
		}

	}

	public ManageEvents() {
		searchString = "";
		selectedInterests = new ArrayList<>();
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
			addFieldError(propertyKey, "event.date.error");
			return null;
		}

		if (typedEventDate.before(today)) {
			addFieldError(propertyKey, "date.error.dateBeforeToday");
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
	public String create() throws IOException, ServletException {

		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		if (!fascade.isAuthorized(member, Right.ADD_ANNOUNCE)) {
			em.close();
			return UNAUTHORIZED_ACTION_NAME;
		}

		Date typedEventRecallDate = DateUtils.substractTimeToDate(
				eventBeginDate, eventRecallTime, eventRecallTypeTime);

		addRightToRequest(request);
		if (eventBeginDate == null || eventEndDate == null
				|| typedEventRecallDate == null) {
			return INPUT;
		}
		if (eventBeginDate.after(eventEndDate)) {
			addFieldError(EVENT_BEGIN_DATE_FORM_FIELD_NAME,
					ERROR_ON_DATE_MESSAGE);
			addFieldError(EVENT_END_DATE_FORM_FIELD_NAME, ERROR_ON_DATE_MESSAGE);

			return INPUT;
		}
		if (DateUtils.compareToToday(typedEventRecallDate) > 0) {
			addFieldError(EVENT_RECALL_TIME_FORM_FIELD_NAME,
					"date.error.dateBeforeToday");

			return INPUT;
		}

		em.getTransaction().begin();

		MeetingFacade meetingFacade = new MeetingFacade(em);

		Meeting event = meetingFacade.createMeeting(member, eventName,
				eventDescription, eventEndDate, false, eventBeginDate,
				eventAddress, eventCity, typedEventRecallDate);

		InterestFacade fac = new InterestFacade(em);
		List<Interest> interests = new ArrayList<Interest>();
		int currentId;
		for (currentId = 0; currentId < selectedInterests.size(); currentId++) {
			interests.add(fac.getInterest(Integer.valueOf(selectedInterests
					.get(currentId))));
		}
		InteractionFacade ifacade = new InteractionFacade(em);
		ifacade.addInterests(event, interests);
		em.getTransaction().commit();
		em.close();

		eventId = event.getId();

		return SUCCESS;
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
	public String modify() throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			em.getTransaction().begin();

			Date typedEventRecallDate = DateUtils.substractTimeToDate(
					eventBeginDate, eventRecallTime, eventRecallTypeTime);

			if (eventBeginDate == null || eventEndDate == null
					|| typedEventRecallDate == null) {
				return INPUT;
			}

			if (eventBeginDate.after(eventEndDate)) {
				addFieldError(EVENT_BEGIN_DATE_FORM_FIELD_NAME,
						ERROR_ON_DATE_MESSAGE);
				addFieldError(EVENT_END_DATE_FORM_FIELD_NAME,
						ERROR_ON_DATE_MESSAGE);

				return INPUT;
			}

			if (DateUtils.compareToToday(typedEventRecallDate) > 0) {
				addFieldError(EVENT_RECALL_TIME_FORM_FIELD_NAME,
						"date.error.dateBeforeToday");

				return INPUT;
			}

			MeetingFacade meetingFacade = new MeetingFacade(em);
			Meeting meeting = meetingFacade.getMeeting(eventId);

			meeting.setContent(eventDescription);
			meeting.setTitle(eventName);
			meeting.setStartDate(eventBeginDate);
			meeting.setEndDate(eventEndDate);
			meeting.setRecallDate(typedEventRecallDate);

			if (meeting.getAddress() != null) {
				meeting.getAddress().setAddress(eventAddress);
				meeting.getAddress().setCity(eventCity);
			}

			em.merge(meeting);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
			return FAILED_ACTION_NAME;
		} finally {
			em.close();
		}

		return SUCCESS;
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
	public String delete() throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		InteractionFacade interactionFacade = new InteractionFacade(em);
		MeetingFacade meetingFacade = new MeetingFacade(em);
		addRightToRequest(request);

		try {
			em.getTransaction().begin();
			Meeting meeting = meetingFacade.getMeeting(eventId);
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
			return FAILED_ACTION_NAME;
		} finally {
			em.close();
		}

		return SUCCESS;
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String subscribe(HttpServletRequest request,
			HttpServletResponse response) {
		EntityManager em = PersistenceProvider.createEntityManager();

		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		addRightToRequest(request);
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		if (!fascade.isAuthorized(member, Right.REGISTER_EVENT)) {
			em.close();
			return UNAUTHORIZED_ACTION_NAME;
		}

		try {
			em.getTransaction().begin();
			MeetingFacade meetingFacade = new MeetingFacade(em);
			Meeting meeting = meetingFacade.getMeeting(eventId);
			InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
					em);
			interactionRoleFacade.subscribe(member, meeting);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			return FAILED_ACTION_NAME;
		} finally {
			em.close();
		}

		return SUCCESS;
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String unsubscribe(HttpServletRequest request,
			HttpServletResponse response) {
		EntityManager em = PersistenceProvider.createEntityManager();

		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		addRightToRequest(request);
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		if (!fascade.isAuthorized(member, Right.REGISTER_EVENT)) {
			em.close();
			return UNAUTHORIZED_ACTION_NAME;
		}

		try {
			em.getTransaction().begin();
			MeetingFacade meetingFacade = new MeetingFacade(em);
			Meeting meeting = meetingFacade.getMeeting(eventId);
			InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
					em);
			interactionRoleFacade.unsubscribe(member, meeting);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			return FAILED_ACTION_NAME;
		} finally {
			em.close();
		}

		return SUCCESS;
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
	public String search() throws IOException, ServletException {

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

		List<Meeting> resultsMyEvents = meetingFacade.getUserMeeting(member);
		InteractionFacade interactionFacade = new InteractionFacade(em);
		List<Integer> unreadInteractionsId = interactionFacade
				.getUnreadInteractionsIdForSocialEntity(member);
		refreshNumNewEvents(request, em);

		em.getTransaction().commit();
		em.close();

		request.setAttribute("eventsList", results);
		request.setAttribute("myEventsList", resultsMyEvents);
		request.setAttribute("unreadInteractionsId", unreadInteractionsId);

		return SUCCESS;
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
	public String display() throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			addRightToRequest(request);

			em.getTransaction().begin();

			SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
			request.setAttribute("member", member);

			MeetingFacade meetingFacade = new MeetingFacade(em);

			Meeting event = meetingFacade.getMeeting(eventId);
			member.addInteractionRead(event);

			InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
					em);
			boolean isSubscriber = interactionRoleFacade.isSubsriber(member,
					event);
			Set<SocialEntity> subscribers = interactionRoleFacade
					.getSubscribers(event);

			refreshNumNewEvents(request, em);
			em.getTransaction().commit();

			request.setAttribute("subscribers", subscribers);
			request.setAttribute("subscriber", isSubscriber);
			request.setAttribute("event", event);
		} catch (NumberFormatException e) {
			return FAILED_ACTION_NAME;
		} finally {
			em.close();
		}
		return SUCCESS;

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
	public String displayCreateEvent() throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		SocialGroupFacade fascade = new SocialGroupFacade(em);

		request.setAttribute("recallDefaultValue", DEFAULT_RECALLTIME);

		if (!fascade.isAuthorized(user, Right.ADD_EVENT)) {
			em.close();
			return SUCCESS;
		}

		em.close();

		return SUCCESS;
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
	public String displayToModify() throws Exception {

		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			MeetingFacade meetingFacade = new MeetingFacade(em);

			em.getTransaction().begin();
			Meeting event = meetingFacade.getMeeting(eventId);

			eventName = event.getTitle();
			eventDescription = event.getContent();
			if (event.getAddress() != null) {
				eventAddress = event.getAddress().getAddress();
				eventCity = event.getAddress().getCity();
			}

			eventBeginDate = event.getStartDate();
			eventEndDate = event.getEndDate();

			Long recallTime = DateUtils.differenceBetweenTwoDateInMinutes(
					event.getStartDate(), event.getRecallDate());

			if (recallTime % DAY_IN_MINUTES == 0) {
				recallTime /= DAY_IN_MINUTES;
				eventRecallTypeTime = "day";
			} else {
				if (recallTime % HOUR_IN_MINUTES == 0) {
					recallTime /= HOUR_IN_MINUTES;
					eventRecallTypeTime = "hour";
				}
			}

			eventRecallTime = Integer.parseInt(recallTime.toString());
			return FAILED_ACTION_NAME;
		} finally {
			em.close();
		}

		// return SUCCESS;
	}

	public String displayImportEvents() throws Exception {

		/** Check for right to import events **/

		return SUCCESS;

	}

	/**
	 * Method that import events from an ics file
	 * 
	 * @param mapping
	 *            Struts ActionMapping
	 * @param form
	 *            Struts ActionForm
	 * @param request
	 *            Http Servlet Request
	 * @param response
	 *            Http Servlet Response
	 * @return String the Action status
	 * @throws IOException
	 * @throws ServletException
	 */
	public String importEventsFromFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		try {

			if (icsFile != null && !icsFileFileName.equals("")) {
				String filePath = request.getSession().getServletContext()
						.getRealPath("/");

				if (!icsFileFileName.endsWith(".ics")) {
					addFieldError("icsFile",
							"events.import.onlyIcsFileExtension");
					return FAILED_ACTION_NAME;
				}

				FileOutputStream outputStream = null;
				FileInputStream inputStream = new FileInputStream(icsFile);
				try {
					outputStream = new FileOutputStream(new File(filePath,
							icsFileFileName));
					byte buffer[] = new byte[500];
					int nbBytes;
					int offset = 0;
					while ((nbBytes = inputStream.read(buffer)) != -1) {
						outputStream.write(buffer, offset, nbBytes);
						offset += nbBytes;
					}
				} finally {
					if (outputStream != null) {
						outputStream.close();
					}
					if (inputStream != null) {
						inputStream.close();
					}
				}

				FileInputStream fin = new FileInputStream(filePath
						+ icsFileFileName);

				CalendarBuilder builder = new CalendarBuilder();

				net.fortuna.ical4j.model.Calendar calendar = builder.build(fin);

				parseCalendarAndSaveToDB(calendar, request);
			} else {
				addFieldError("icsFile", "events.import.icsFileRequired");
				return FAILED_ACTION_NAME;
			}
		} catch (ParserException e) {
			addFieldError("icsFile", "events.import.parseError");
			return FAILED_ACTION_NAME;
		}

		return SUCCESS;

	}

	/**
	 * Method that export an event/meeting into an ics file The event is mapped
	 * with the RFC for ical event (ical4j is used to create the ics file)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String exportEventById(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String filePath = request.getSession().getServletContext()
				.getRealPath("/");
		File icsToCreate = new File(filePath, "calendar_event.ics");

		try (OutputStream icsOutputStream = new FileOutputStream(icsToCreate);
				InputStream icsInputStream = new FileInputStream(icsToCreate)) {
			net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
			calendar.getProperties().add(
					new ProdId("-//Calendar//Event 1.0//EN"));
			calendar.getProperties().add(Version.VERSION_2_0);
			calendar.getProperties().add(CalScale.GREGORIAN);
			EntityManager em = PersistenceProvider.createEntityManager();
			MeetingFacade meetingFacade = new MeetingFacade(em);
			em.getTransaction().begin();
			Meeting event = meetingFacade.getMeeting(eventId);

			VEvent myevent = convertEventToIcalEvent(event);

			calendar.getComponents().add(myevent);

			CalendarOutputter outputter = new CalendarOutputter();
			outputter.setValidating(false);
			outputter.output(calendar, icsOutputStream);

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;filename=calendar_event_id_" + eventId + ".ics");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-control", "no-cache");

			ServletOutputStream out = response.getOutputStream();

			int i;
			while ((i = icsInputStream.read()) != -1) {
				out.write(i);
			}
			out.close();
			em.close();
		} catch (NumberFormatException nfe) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", nfe);
		} catch (FileNotFoundException fnfe) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", fnfe);
		} catch (IOException io) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", io);
		} catch (net.fortuna.ical4j.model.ValidationException ve) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", ve);
		}

		return SUCCESS;
	}

	/**
	 * Method that export an event/meeting into an ics file The event is mapped
	 * with the RFC for ical event (ical4j is used to create the ics file)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String exportAllEvent(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String filePath = request.getSession().getServletContext()
				.getRealPath("/");
		File icsToCreate = new File(filePath, "calendar_all_event.ics");
		try (OutputStream icsOutputStream = new FileOutputStream(icsToCreate);
				InputStream icsInputStream = new FileInputStream(icsToCreate)) {

			net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
			calendar.getProperties().add(
					new ProdId("-//Calendar//Event 1.0//EN"));
			calendar.getProperties().add(Version.VERSION_2_0);
			calendar.getProperties().add(CalScale.GREGORIAN);

			EntityManager em = PersistenceProvider.createEntityManager();

			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);

			em.getTransaction().begin();

			MeetingFacade meetingFacade = new MeetingFacade(em);
			List<Meeting> results = meetingFacade.getAllUserMeeting(user);

			for (Meeting m : results) {
				VEvent myevent = convertEventToIcalEvent(m);
				calendar.getComponents().add(myevent);
			}

			CalendarOutputter outputter = new CalendarOutputter();
			outputter.setValidating(false);
			outputter.output(calendar, icsOutputStream);

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;filename=calendar_all_event.ics");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-control", "no-cache");

			ServletOutputStream out = response.getOutputStream();

			int i;
			while ((i = icsInputStream.read()) != -1) {
				out.write(i);
			}
			out.close();
			em.close();
		} catch (IOException io) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", io);
		} catch (net.fortuna.ical4j.model.ValidationException ve) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", ve);
		}

		return SUCCESS;
	}

	/**
	 * Method that map an event into an ical4j event
	 * 
	 * @param event
	 *            the event/meeting to be mapped
	 * @return VEvent the ical4j event mapped
	 */
	public VEvent convertEventToIcalEvent(Meeting event) {
		VEvent icalEvent = new VEvent();
		Summary summary = new Summary(event.getTitle());
		Location location = new Location(event.getAddress().getAddress() + ""
				+ event.getAddress().getCity());

		Description description = new Description(event.getContent());

		DateTime startDate = new DateTime(new Date());
		DateTime endDate = new DateTime(new Date());
		if (event.getStartDate() != null) {
			startDate = new DateTime(DateUtils.toIcal4jFormat(event
					.getStartDate()));
		}

		if (event.getEndDate() != null) {
			endDate = new DateTime(DateUtils.toIcal4jFormat(event.getEndDate()));
		}

		UidGenerator ug;
		try {
			ug = new UidGenerator("uidGen");
			Uid uid = ug.generateUid();
			icalEvent.getProperties().add(uid);
		} catch (SocketException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}

		icalEvent.getProperties().add(summary);
		icalEvent.getProperties().add(location);
		icalEvent.getProperties().add(description);
		icalEvent.getProperties().add(new DtStart(startDate));
		icalEvent.getProperties().add(new DtEnd(endDate));

		return icalEvent;
	}

	/**
	 * Method that parse an ical4j calendar to retrieve events and save them to
	 * the database
	 * 
	 * @return String the Action status
	 */
	public void parseCalendarAndSaveToDB(
			net.fortuna.ical4j.model.Calendar calendar,
			HttpServletRequest request) {
		try {
			EntityManager em = PersistenceProvider.createEntityManager();
			SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
			em.getTransaction().begin();
			for (Iterator i = calendar.getComponents(Component.VEVENT)
					.iterator(); i.hasNext();) {

				Component component = (Component) i.next();

				String eventName = "";
				String eventDescription = "";
				String eventBeginDate = "";
				String eventEndDate = "";
				String adress = "";
				String city = "";
				String eventRecallTime = "0";
				String eventRecallTypeTime = "minute";

				for (Iterator j = component.getProperties().iterator(); j
						.hasNext();) {
					Property property = (Property) j.next();

					switch (EventProperty.lookup(property.getName())) {
					case UID:
						eventName = property.getValue();
						break;
					case DTSTART:
						eventBeginDate = property.getValue();
						break;
					case DTEND:
						eventEndDate = property.getValue();
						break;
					case DESCRIPTION:
						eventDescription = property.getValue();
						break;
					case SUMMARY:
						eventName = property.getValue();
						break;
					case LOCATION:
						adress = property.getValue();
						break;
					default:
						break;
					}
				}

				Date typedEventBeginDate = DateUtils
						.convertIcsTimestampToDate(eventBeginDate);
				Date typedEventEndDate = DateUtils
						.convertIcsTimestampToDate(eventEndDate);
				Date typedEventRecallDate = DateUtils.substractTimeToDate(
						typedEventBeginDate, Integer.parseInt(eventRecallTime),
						eventRecallTypeTime);

				MeetingFacade meetingFacade = new MeetingFacade(em);
				Meeting event = meetingFacade
						.createMeeting(member, eventName, eventDescription,
								typedEventEndDate, false, typedEventBeginDate,
								adress, city, typedEventRecallDate);
			}

			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
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
	public String deleteMulti() throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();
		addRightToRequest(request);
		SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(
				request, em);

		try {

			MeetingFacade eventFacade = new MeetingFacade(em);
			InteractionFacade interactionFacade = new InteractionFacade(em);

			addRightToRequest(request);

			for (int i = 0; i < selectedEvents.length; i++) {
				em.getTransaction().begin();
				Meeting meeting = eventFacade.getMeeting(Integer
						.parseInt(selectedEvents[i]));
				Set<SocialEntity> followingEntitys = meeting
						.getFollowingEntitys();
				for (SocialEntity se : followingEntitys) {
					se.getFavoriteInteractions().remove(meeting);
				}
				InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
						em);
				interactionRoleFacade.unsubscribeAll(meeting);
				interactionFacade.deleteInteraction(authenticatedUser, meeting);

				em.getTransaction().commit();
			}

		} finally {
			em.close();
		}

		return SUCCESS;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getEventAddress() {
		return eventAddress;
	}

	public void setEventAddress(String eventAddress) {
		this.eventAddress = eventAddress;
	}

	public String getEventCity() {
		return eventCity;
	}

	public void setEventCity(String eventCity) {
		this.eventCity = eventCity;
	}

	public Date getEventBeginDate() {
		return eventBeginDate;
	}

	public void setEventBeginDate(Date eventBeginDate) {
		this.eventBeginDate = eventBeginDate;
	}

	public Date getEventEndDate() {
		return eventEndDate;
	}

	public void setEventEndDate(Date eventEndDate) {
		this.eventEndDate = eventEndDate;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public List<String> getSelectedInterests() {
		return selectedInterests;
	}

	public void setSelectedInterests(List<String> selectedInterests) {
		this.selectedInterests = selectedInterests;
	}

	public String[] getSelectedEvents() {
		return selectedEvents;
	}

	public void setSelectedEvents(String[] selectedEvents) {
		this.selectedEvents = selectedEvents;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public int getEventRecallTime() {
		return eventRecallTime;
	}

	public void setEventRecallTime(int eventRecallTime) {
		this.eventRecallTime = eventRecallTime;
	}

	public String getEventRecallTypeTime() {
		return eventRecallTypeTime;
	}

	public void setEventRecallTypeTime(String eventRecallTypeTime) {
		this.eventRecallTypeTime = eventRecallTypeTime;
	}

	public String getIcsFileContentType() {
		return icsFileContentType;
	}

	public void setIcsFileContentType(String icsFileContentType) {
		this.icsFileContentType = icsFileContentType;
	}

	public File getIcsFile() {
		return icsFile;
	}

	public void setIcsFile(File icsFile) {
		this.icsFile = icsFile;
	}

	public String getIcsFileFileName() {
		return icsFileFileName;
	}

	public void setIcsFileFileName(String icsFileFileName) {
		this.icsFileFileName = icsFileFileName;
	}

}
