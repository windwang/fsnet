package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONObject;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.facade.MeetingFacade;

public class Calendar extends Action {

	private List<String> events;

	/**
	 * Action that create a list of all events/meetings in json format
	 * Used for Full Calendar jquery plugin
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		EntityManager em = PersistenceProvider.createEntityManager();

		em.getTransaction().begin();

		MeetingFacade meetingFacade = new MeetingFacade(em);
		List<Meeting> results = meetingFacade.listAllMeeting();
		
		events = new ArrayList<String>();
		for (Meeting m : results) {
			SimpleDateFormat usFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			java.util.Calendar cal = java.util.GregorianCalendar.getInstance();
			cal.setTime(m.getStartDate());
			String startDate = usFormat.format(cal.getTime());
			cal.setTime(m.getEndDate());
			String endDate = usFormat.format(cal.getTime());
			
			events.add(m.getTitle()+","+startDate+","+endDate+","+"false"+","+m.getContent());
		}

		JSONArray jsonArray = JSONArray.fromObject(events);

		JSONObject obj = new JSONObject();
		obj.put("events", jsonArray);

		response.setHeader("X-JSON", obj.toJSONString());
		response.setContentType("text/html");

		return mapping.findForward("success");

	}

	public List<String> getEvents() {
		return events;
	}

	public void setEvents(List<String> events) {
		this.events = events;
	}

}