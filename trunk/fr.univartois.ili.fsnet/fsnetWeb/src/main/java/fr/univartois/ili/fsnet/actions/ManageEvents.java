package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
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

import fr.univartois.ili.fsnet.actions.utils.DateUtils;
import fr.univartois.ili.fsnet.auth.Authenticate;
import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Manifestation;

/**
 * Execute CRUD Actions for the entity Event
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class ManageEvents extends MappingDispatchAction implements CrudAction {

	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		DynaActionForm dynaForm = (DynaActionForm) form;
		String eventName = (String) dynaForm.get("eventName");
		String eventDescription = (String) dynaForm.get("eventDescription");
		String eventDate = (String) dynaForm.get("eventDate");

		EntiteSociale member = (EntiteSociale) request.getSession()
				.getAttribute(Authenticate.AUTHENTICATED_USER);
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
		em.getTransaction().begin();
		member = em.find(EntiteSociale.class, member.getId());
		Manifestation event = new Manifestation(eventName, typedEventDate,
				eventDescription, null, member);
		member.getLesinteractions().add(event);
		em.persist(event);
		request.setAttribute("event", event);
		em.getTransaction().commit();
		em.close();
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
		DynaActionForm dynaForm = (DynaActionForm) form;
		String eventId = (String) dynaForm.get("eventId");

		EntityManager em = factory.createEntityManager();

		em.getTransaction().begin();
		TypedQuery<Manifestation> query = em.createQuery(
				"Select e from Manifestation e where e.id = :eventId",
				Manifestation.class);
		query.setParameter("eventId", Integer.parseInt(eventId));
		Manifestation event = query.getSingleResult();
		em.remove(event);
		em.flush();
		em.close();
		em.getTransaction().commit();

		request.setAttribute("event", event);
		return mapping.findForward("success");
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm seaarchForm = (DynaActionForm) form;
		String searchString = (String) seaarchForm.get("searchString");

		EntityManager em = factory.createEntityManager();
		List<Manifestation> results;
		final TypedQuery<Manifestation> query;
		// on empty search return all events
		if ("".equals(searchString)) {
			query = em.createQuery("SELECT e FROM Manifestation e",
					Manifestation.class);
		} else {
			query = em.createQuery("SELECT e FROM Manifestation e "
					+ "WHERE e.nom LIKE :searchString "
					+ "OR e.contenu LIKE :searchString ", Manifestation.class);
			query.setParameter("searchString", "%" + searchString + "%");
		}
		results = query.getResultList();
		if (results.isEmpty()) {
			ActionErrors errors = new ActionErrors();
			errors.add("searchString", new ActionMessage("search.noResults"));
			saveErrors(request, errors);
		}
		request.setAttribute("events", results);
		return mapping.findForward("success");
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String eventId = (String) dynaForm.get("eventId");

		EntityManager em = factory.createEntityManager();
		TypedQuery<Manifestation> query = em.createQuery(
				"Select e from Manifestation e where e.id = :eventId",
				Manifestation.class);
		query.setParameter("eventId", Integer.parseInt(eventId));
		Manifestation event = query.getSingleResult();
		em.close();

		request.setAttribute("event", event);
		return mapping.findForward("success");
	}
}
