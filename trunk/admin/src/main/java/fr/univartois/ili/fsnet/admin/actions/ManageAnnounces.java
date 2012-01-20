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
 * @author Bouragba mohamed
 * source /fsnetWeb/src/main/java/fr/univartois/ili/fsnet/actions/ManageAnnounces.java (Mehdi Benzaghar)
 */
public class ManageAnnounces extends MappingDispatchAction implements CrudAction{

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return null;
	}

	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return null;
	}

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
		if (announce != null)
		{
			SocialEntity user = announce.getCreator();
			interactionFacade.deleteInteraction(user, announce);
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		message.add("message", new ActionMessage("success.deleteAnnounce"));
		saveMessages(request, message);
		return mapping.findForward("success");
	}

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

		Paginator<Announcement> paginator = new Paginator<Announcement>(
				listAnnounces, request, "listAnnounces");

		request.setAttribute("annoucesListPaginator", paginator);
		return mapping.findForward("success");
	}

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
		SocialEntity SocialEntity = announce.getCreator();
		SocialEntity SocialEntityOwner = (SocialEntity) entityManager
				.createQuery(
						"SELECT es FROM SocialEntity es,IN(es.interactions) e WHERE e = :announce")
				.setParameter("announce", announce).getSingleResult();
		entityManager.getTransaction().commit();
		entityManager.close();

		request.setAttribute("announce", announce);
		request.setAttribute("SocialEntity", SocialEntityOwner);
		servlet.log(SocialEntityOwner.toString() + SocialEntityOwner.getName());
		if (SocialEntity.getId() == SocialEntityOwner.getId()) {
			request.setAttribute("owner", true);
		}

		return mapping.findForward("success");
	}

}
