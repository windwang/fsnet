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

import com.opensymphony.xwork2.ActionSupport;

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
public class ManageContacts extends ActionSupport implements CrudAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(ManageContacts.class
			.getName());

	private static final String UNABLE_TO_PARSE_ID_ERROR = "Unable to parse the contact id as an integer";

	private int entitySelected;
	private int entityAccepted;

	private int entityRefused;

	private int entityDeleted;

	private int id;
	
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
	public String askContact(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		try {
			em.getTransaction().begin();
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
			
			
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

		return SUCCESS;
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
	public String accept(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		try {
			em.getTransaction().begin();
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
			SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
			SocialEntity entityAccept = socialEntityFacade
					.getSocialEntity(entityAccepted);
			ContactFacade contactFacade = new ContactFacade(em);
			contactFacade.acceptContact(user, entityAccept);
			refreshNumNewContacts(request, em);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			LOGGER.log(Level.WARNING,
					UNABLE_TO_PARSE_ID_ERROR, e);
			throw new UnauthorizedOperationException("exception.message");
		} finally {
			em.close();
		}

		return SUCCESS;
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
	public String refuse(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		try {
			em.getTransaction().begin();
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
			SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
			SocialEntity entityRefus = socialEntityFacade.getSocialEntity(entityRefused);
			ContactFacade contactFacade = new ContactFacade(em);
			contactFacade.refuseContact(user, entityRefus);
			refreshNumNewContacts(request, em);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			LOGGER.log(Level.WARNING,
					UNABLE_TO_PARSE_ID_ERROR, e);
			throw new UnauthorizedOperationException("exception.message");
		} finally {
			em.close();
		}

		return SUCCESS;
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
	public String create(
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
	public String modify(
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
	public String delete(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		try {
			em.getTransaction().begin();
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
			SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
			SocialEntity removedEntity = socialEntityFacade.getSocialEntity(entityDeleted);
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
	public String search(
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
	public String display(
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

		return SUCCESS;
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
	public String cancelAsk(
			HttpServletRequest request, HttpServletResponse response) {
		EntityManager em = PersistenceProvider.createEntityManager();
		try {
			SocialEntityFacade sef = new SocialEntityFacade(em);
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
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

		return SUCCESS;
	}

	public int getEntitySelected() {
		return entitySelected;
	}

	public void setEntitySelected(int entitySelected) {
		this.entitySelected = entitySelected;
	}

	public int getEntityAccepted() {
		return entityAccepted;
	}

	public void setEntityAccepted(int entityAccepted) {
		this.entityAccepted = entityAccepted;
	}

	public int getEntityRefused() {
		return entityRefused;
	}

	public void setEntityRefused(int entityRefused) {
		this.entityRefused = entityRefused;
	}

	public int getEntityDeleted() {
		return entityDeleted;
	}

	public void setEntityDeleted(int entityDeleted) {
		this.entityDeleted = entityDeleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}
