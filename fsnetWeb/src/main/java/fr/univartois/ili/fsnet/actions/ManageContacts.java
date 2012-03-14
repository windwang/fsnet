package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.ContactFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;

/**
 * 
 * @author Grioche Legrand
 */
public class ManageContacts extends MappingDispatchAction implements CrudAction {

	private static final Logger LOGGER = Logger.getLogger(ManageContacts.class
			.getName());

	private static final String SUCCES_ATTRIBUTE_NAME = "success";
	private static final String UNABLE_TO_PARSE_ID_ERROR = "Unable to parse the contact id as an integer";

	/**
	 * Submit a request contact to another social entity
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward askContact(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		try {
			DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
			em.getTransaction().begin();
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
			final String idString = (String) dynaForm.get("entitySelected");
			int entitySelected = 0;

			entitySelected = Integer.parseInt(idString);
			// TODO changer les listes en set sur les entites sociales pour
			// eviter
			// les doublons

			SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
			SocialEntity entity = socialEntityFacade
					.getSocialEntity(entitySelected);
			ContactFacade contactFacade = new ContactFacade(em);
			contactFacade.askContact(user, entity);

			em.getTransaction().commit();
		} catch (NumberFormatException nfe) {
			LOGGER.log(Level.WARNING,
					UNABLE_TO_PARSE_ID_ERROR, nfe);
			throw new UnauthorizedOperationException("exception.message");
		} finally {
			em.close();
		}

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	/**
	 * To accept a contact request
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward accept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		try {
			DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
			em.getTransaction().begin();
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
			final String idString = (String) dynaForm.get("entityAccepted");
			int id = Integer.parseInt(idString);
			SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
			SocialEntity entityAccepted = socialEntityFacade
					.getSocialEntity(id);
			ContactFacade contactFacade = new ContactFacade(em);
			contactFacade.acceptContact(user, entityAccepted);
			refreshNumNewContacts(request, em);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			LOGGER.log(Level.WARNING,
					UNABLE_TO_PARSE_ID_ERROR, e);
			throw new UnauthorizedOperationException("exception.message");
		} finally {
			em.close();
		}

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	/**
	 * To refuse a contact request
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward refuse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		try {
			DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
			em.getTransaction().begin();
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
			final String idString = (String) dynaForm.get("entityRefused");
			int id = Integer.parseInt(idString);
			SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
			SocialEntity entityRefused = socialEntityFacade.getSocialEntity(id);
			ContactFacade contactFacade = new ContactFacade(em);
			contactFacade.refuseContact(user, entityRefused);
			refreshNumNewContacts(request, em);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			LOGGER.log(Level.WARNING,
					UNABLE_TO_PARSE_ID_ERROR, e);
			throw new UnauthorizedOperationException("exception.message");
		} finally {
			em.close();
		}

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
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
		throw new UnsupportedOperationException("Not supported yet.");
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
		throw new UnsupportedOperationException("Not supported yet.");
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
		EntityManager em = PersistenceProvider.createEntityManager();
		try {
			DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
			em.getTransaction().begin();
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
			final String idString = (String) dynaForm.get("entityDeleted");
			int id = Integer.parseInt(idString);
			SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
			SocialEntity removedEntity = socialEntityFacade.getSocialEntity(id);
			ContactFacade contactFacade = new ContactFacade(em);
			contactFacade.removeContact(user, removedEntity);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			LOGGER.log(Level.WARNING,
					UNABLE_TO_PARSE_ID_ERROR, e);
			throw new UnauthorizedOperationException("exception.message");
		} finally {
			em.close();
		}

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
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
		throw new UnsupportedOperationException("Not supported yet.");
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
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);

		Collections.sort(user.getContacts());
		Collections.sort(user.getAsked());
		Collections.sort(user.getRequested());

		em.close();

		request.setAttribute("paginatorContacts", user.getContacts());
		request.setAttribute("paginatorAsked", user.getAsked());
		request.setAttribute("paginatorRequested", user.getRequested());

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	/**
	 * @param request
	 * @param em
	 */
	public static void refreshNumNewContacts(HttpServletRequest request,
			EntityManager em) {
		HttpSession session = request.getSession();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		session.setAttribute("numNewContactsRequests", user.getAsked().size());
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cancelAsk(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EntityManager em = PersistenceProvider.createEntityManager();
		try {
			SocialEntityFacade sef = new SocialEntityFacade(em);
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
			DynaActionForm dyna = (DynaActionForm) form; // NOSONAR
			int id = Integer.parseInt(dyna.getString("id"));
			em.getTransaction().begin();
			SocialEntity requested = sef.getSocialEntity(id);
			em.getTransaction().commit();
			ContactFacade cf = new ContactFacade(em);
			em.getTransaction().begin();
			cf.cancelRequested(user, requested, em);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			LOGGER.log(Level.WARNING,
					UNABLE_TO_PARSE_ID_ERROR, e);
			throw new UnauthorizedOperationException("exception.message");
		} finally {
			em.close();
		}

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

}
