package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.auth.Authenticate;
import fr.univartois.ili.fsnet.entities.EntiteSociale;

public class ManageMembers extends MappingDispatchAction{

	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");

	private static final Logger logger = Logger.getAnonymousLogger();

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		Query query = null;
		Query queryContacts = null;
		Query queryRequested = null;
		Query queryAsked = null;
		List<EntiteSociale> result = null;
		List<EntiteSociale> resultContacts = null;
		List<EntiteSociale> resultRequested = null;
		List<EntiteSociale> resultAsked = null;
		EntiteSociale member = (EntiteSociale) request.getSession().getAttribute(Authenticate.AUTHENTICATED_USER);

		if(form != null){
			DynaActionForm dynaForm = (DynaActionForm) form;
			String searchText = (String) dynaForm.get("searchText");
			if(searchText.equals("")){
				query = em.createQuery("SELECT es FROM EntiteSociale es WHERE es.id <> :id");
				query.setParameter("id", member.getId());
				result = query.getResultList();
				logger.info("searchText vide");
			}else{
				logger.info("searchText non vide" + searchText);
				query = em.createQuery(
						"SELECT es FROM EntiteSociale es WHERE (es.nom LIKE :searchText" +
				" OR es.prenom LIKE :searchText OR es.email LIKE :searchText) AND es.id <> :id");
				query.setParameter("searchText", "%" + searchText + "%");
				query.setParameter("id", member.getId());

				queryContacts = em.createQuery(
						"SELECT contact FROM EntiteSociale contact, IN (contact.contacts) membre WHERE membre.id = :id AND (contact.nom LIKE :searchText" +
				" OR contact.prenom LIKE :searchText OR contact.email LIKE :searchText)");
				queryContacts.setParameter("searchText", "%" + searchText + "%");
				queryContacts.setParameter("id", member.getId());

				queryRequested = em.createQuery(
						"SELECT contact FROM EntiteSociale contact, IN (contact.requested) membre WHERE membre.id = :id AND (contact.nom LIKE :searchText" +
				" OR contact.prenom LIKE :searchText OR contact.email LIKE :searchText)");
				queryRequested.setParameter("searchText", "%" + searchText + "%");
				queryRequested.setParameter("id", member.getId());

				queryAsked = em.createQuery(
						"SELECT contact FROM EntiteSociale contact, IN (contact.asked) membre WHERE membre.id = :id AND (contact.nom LIKE :searchText" +
				" OR contact.prenom LIKE :searchText OR contact.email LIKE :searchText)");
				queryAsked.setParameter("searchText", "%" + searchText + "%");
				queryAsked.setParameter("id", member.getId());

				result = query.getResultList();
				resultContacts = queryContacts.getResultList();
				resultRequested = queryRequested.getResultList();
				resultAsked = queryAsked.getResultList();
			}

		}else{
			logger.info("Form = null");
			query = em.createQuery("SELECT es FROM EntiteSociale es WHERE es.id <> :id");
			query.setParameter("id", member.getId());
			result = query.getResultList();
			resultContacts = member.getContacts();
			resultRequested = member.getRequested();
			resultAsked = member.getAsked();
		}


		em.close();

		request.setAttribute("membersResult", result);
		request.setAttribute("membersContactsResult", resultContacts);
		request.setAttribute("membersRequestedResult", resultRequested);
		request.setAttribute("membersAskedResult", resultAsked);

		return mapping.findForward("success");
	}

}
