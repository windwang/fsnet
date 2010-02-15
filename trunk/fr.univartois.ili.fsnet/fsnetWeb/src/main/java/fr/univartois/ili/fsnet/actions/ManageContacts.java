package fr.univartois.ili.fsnet.actions;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.ContactFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade;

/**
 * 
 * @author Grioche Legrand
 */
public class ManageContacts extends MappingDispatchAction implements CrudAction {

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
		DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		final String idString = (String) dynaForm.get("entitySelected");
		int entitySelected = Integer.parseInt(idString);
		
		ActionErrors errors = new ActionErrors();
		// TODO changer les listes en set sur les entites sociales pour eviter
		// les doublons
		
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em); 
		SocialEntity entity = socialEntityFacade.getSocialEntity(entitySelected);
		ContactFacade contactFacade = new ContactFacade(em);
		contactFacade.askContact(user, entity);

		em.getTransaction().commit();
		em.close();
		return mapping.findForward("success");
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
		DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		final String idString = (String) dynaForm.get("entityAccepted");
		int id = Integer.parseInt(idString);
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em); 
		SocialEntity entityAccepted = socialEntityFacade.getSocialEntity(id);
		ContactFacade contactFacade = new ContactFacade(em);
		contactFacade.acceptContact(user, entityAccepted);
		em.getTransaction().commit();
		em.close();
		return mapping.findForward("success");
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

		DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		final String idString = (String) dynaForm.get("entityRefused");
		int id = Integer.parseInt(idString);
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em); 
		SocialEntity entityRefused = socialEntityFacade.getSocialEntity(id);
		ContactFacade contactFacade = new ContactFacade(em);
		contactFacade.refuseContact(user, entityRefused);
		em.getTransaction().commit();
		em.close();
		return mapping.findForward("success");
	}

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		final String idString = (String) dynaForm.get("entityDeleted");
		int id = Integer.parseInt(idString);
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em); 
		SocialEntity removedEntity = socialEntityFacade.getSocialEntity(id);
		ContactFacade contactFacade = new ContactFacade(em);
		contactFacade.removeContact(user, removedEntity);        
		em.getTransaction().commit();
		em.close();
		return mapping.findForward("success");
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {

		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
	
		user.getContacts();
		user.getAsked();
		user.getRequested();
		request.setAttribute("theUser", user);
		em.close();
		return mapping.findForward("success");
	}

}
