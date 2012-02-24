package fr.univartois.ili.fsnet.admin.actions;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.AnnouncementFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;

/**
 * Execute CRUD Actions (and more) for the entity SocialGroup
 * 
 * @author Bouragba mohamed source
 *         /fsnetWeb/src/main/java/fr/univartois/ili/fsnet
 *         /actions/ManageAnnounces.java (Mehdi Benzaghar)
 */
public class ManageAnnounces extends MappingDispatchAction implements
		CrudAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#create(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#modify(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#delete(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager entityManager = PersistenceProvider.createEntityManager();
		entityManager.getTransaction().begin();
		AnnouncementFacade announcementFacade = new AnnouncementFacade(
				entityManager);
		Integer idAnnounce = Integer
				.valueOf(request.getParameter("idAnnounce"));
		Announcement announce = announcementFacade.getAnnouncement(idAnnounce);
		ActionMessages message = new ActionErrors();
		InteractionFacade interactionFacade = new InteractionFacade(
				entityManager);
		if (announce != null) {
			SocialEntity user = announce.getCreator();
			interactionFacade.deleteInteraction(user, announce);
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		message.add("message", new ActionMessage("success.deleteAnnounce"));
		saveMessages(request, message);
		return mapping.findForward("success");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#search(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm seaarchForm = (DynaActionForm) form;// NOSONAR
		String textSearchAnnounce = (String) seaarchForm
				.get("textSearchAnnounce");
		EntityManager entityManager = PersistenceProvider.createEntityManager();
		entityManager.getTransaction().begin();
		AnnouncementFacade announcementFacade = new AnnouncementFacade(
				entityManager);

		List<Announcement> listAnnounces = announcementFacade
				.searchAnnouncement(textSearchAnnounce);
		entityManager.getTransaction().commit();
		entityManager.close();

		request.setAttribute("annoucesList", listAnnounces);
		return mapping.findForward("success");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#display(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager entityManager = PersistenceProvider.createEntityManager();
		entityManager.getTransaction().begin();
		Integer idAnnounce = Integer
				.valueOf(request.getParameter("idAnnounce"));
		AnnouncementFacade announcementFacade = new AnnouncementFacade(
				entityManager);
		Announcement announce = announcementFacade.getAnnouncement(idAnnounce);
		SocialEntity socialEntity = announce.getCreator();
		SocialEntity socialEntityOwner = (SocialEntity) entityManager
				.createQuery(
						"SELECT es FROM SocialEntity es,IN(es.interactions) e WHERE e = :announce")
				.setParameter("announce", announce).getSingleResult();
		entityManager.getTransaction().commit();
		entityManager.close();

		request.setAttribute("announce", announce);
		request.setAttribute("SocialEntity", socialEntityOwner);
		servlet.log(socialEntityOwner.toString() + socialEntityOwner.getName());
		if (socialEntity.getId() == socialEntityOwner.getId()) {
			request.setAttribute("owner", true);
		}

		return mapping.findForward("success");
	}

}
