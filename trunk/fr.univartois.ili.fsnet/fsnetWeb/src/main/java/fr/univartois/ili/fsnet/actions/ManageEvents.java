package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

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

	private static final Logger logger = Logger.getAnonymousLogger();

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		DynaActionForm dynaForm = (DynaActionForm) form;
		String eventName = (String) dynaForm.get("eventName");
		String eventDescription = (String) dynaForm.get("eventDescription");
		String eventDate = (String) dynaForm.get("eventDate");
		
		logger.info("new event: " + eventName + "\n" + eventDescription + "\n"
				+ eventDate);
		
		// TODO : -> récupérer une date valide
		// 		  -> voir à quoi corespond le paramètre visible
		// 		  -> récuperer l'utilisateur authentifié
		Manifestation event = new Manifestation(eventName, new Date(), eventDescription, null, null);
		
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(event);
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
		// TODO code pour la suppression
		return null;
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO code pour la recherche
		// Mock objects
		EntiteSociale es = new EntiteSociale("Proucelle", "Matthieu",
				"prouprou@fsnet.com");
		Manifestation manifestation = new Manifestation("Apero chez prouprou",
				new Date(), "Mock content", "true", es);
		request.setAttribute("listEvents", Collections.singletonList(es));
		return mapping.findForward("success");
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO code pour l'affichage detaillé
		return null;
	}
}
