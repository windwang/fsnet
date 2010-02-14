package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
import fr.univartois.ili.fsnet.commons.utils.DateUtils;
import fr.univartois.ili.fsnet.entities.InteractionRole;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.MeetingFacade;

/**
 * Execute CRUD Actions for the entity Event
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class ManageEvents extends MappingDispatchAction implements CrudAction {

	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {

		DynaActionForm dynaForm = (DynaActionForm) form; 							//NOSONAR
		String eventName = (String) dynaForm.get("eventName");
		String eventDescription = (String) dynaForm.get("eventDescription");
		String eventDate = (String) dynaForm.get("eventDate");
		//TODO !!! recuperer l'adresse et la city !!!
		String adress = "";
		String city = "";

		Date typedEventDate;
		try {
			typedEventDate = DateUtils.format(eventDate);
		} catch (ParseException e) {
			ActionErrors errors = new ActionErrors();
			errors.add("eventDate", new ActionMessage(("event.date.errors")));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
		EntityManager em = factory.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		em.getTransaction().begin();
		// TODO !!! date de fin et date de debut !!

		MeetingFacade meetingFacade = new MeetingFacade(em);
		Meeting event = meetingFacade.createMeeting(member, eventName, eventDescription, typedEventDate, false, typedEventDate, adress, city);

		em.getTransaction().commit();
		em.close();
		request.setAttribute("event", event);
		return mapping.findForward("success");
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
		DynaActionForm dynaForm = (DynaActionForm) form;								//NOSONAR
		String eventId = (String) dynaForm.get("eventId");

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		MeetingFacade meetingFacade = new MeetingFacade(em);
		meetingFacade.deleteMeeting(Integer.parseInt(eventId));

		em.getTransaction().commit();
		em.close();

		return mapping.findForward("success");
	}


	public ActionForward subscribe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		DynaActionForm dynaForm = (DynaActionForm) form;								//NOSONAR
		String eventId = (String) dynaForm.get("eventId");
		EntityManager em = factory.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		em.getTransaction().begin();
		MeetingFacade meetingFacade = new MeetingFacade(em);
		Meeting meeting = meetingFacade.getMeeting(Integer.parseInt(eventId));

		InteractionRole interactionRole = new InteractionRole();
		interactionRole.setInteraction(meeting);
		interactionRole.setSocialEntity(member);
		interactionRole.setRole(InteractionRole.RoleName.SUBSCRIBER);
		em.persist(interactionRole);
		em.getTransaction().commit();
		return mapping.findForward("success");
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		DynaActionForm seaarchForm = (DynaActionForm) form; 							//NOSONAR
		String searchString = (String) seaarchForm.get("searchString");

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		MeetingFacade meetingFacade = new MeetingFacade(em);
		List<Meeting> results = meetingFacade.searchMeeting(searchString);
		em.getTransaction().commit();

		if (results.isEmpty()) {
			ActionErrors errors = new ActionErrors();
			errors.add("searchString", new ActionMessage("search.noResults"));
			saveErrors(request, errors);
		}

		em.close();
		request.setAttribute("events", results);
		return mapping.findForward("success");
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form;								//NOSONAR
		String eventId = (String) dynaForm.get("eventId");

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		MeetingFacade meetingFacade = new MeetingFacade(em);
		Meeting event = meetingFacade.getMeeting(Integer.parseInt(eventId));

		// TODO a finir demain
		//TypedQuery<SocialEntity> queryRole = em.createQuery("Select i from InterationRole i where ",SocialEntity.class);
		em.getTransaction().commit();
		em.close();

		request.setAttribute("event", event);
		return mapping.findForward("success");
	}
}
