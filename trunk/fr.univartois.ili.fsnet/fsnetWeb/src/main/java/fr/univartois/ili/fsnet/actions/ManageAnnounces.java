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
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.DateUtils;
import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 * 
 * @author Mehdi Benzaghar <mehdi.benzaghar at gmail.com>
 */
public class ManageAnnounces extends MappingDispatchAction implements
		CrudAction {

	private static final EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");

	/**
	 * @return to announces view after persisting new announce
	 */
	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager entityManager = factory.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request,
				entityManager);

		DynaActionForm formAnnounce = (DynaActionForm) form; //NOSONAR
		String title = (String) formAnnounce.get("announceTitle");
		String content = (String) formAnnounce.get("announceContent");
		String stringExpiryDate = (String) formAnnounce
				.get("announceExpiryDate");

		try {
			Date expiryDate = DateUtils.format(stringExpiryDate);
			if (0 > DateUtils.compareToToday(expiryDate)) {
				Announcement announce = new Announcement(user, title, content, expiryDate, false);
				entityManager.getTransaction().begin();
				entityManager.persist(announce);
				entityManager.getTransaction().commit();
			} else {
				ActionMessages errors = new ActionErrors();
				errors.add("message", new ActionMessage("dateBelowDateToday"));
				saveErrors(request, errors);
				return mapping.findForward("failer");
			}
		} catch (ParseException e) {
			servlet.log(getClass().getName()
					+ " methode:create exception when formating date ");
			e.printStackTrace();
			return mapping.findForward("failer");
		}
		entityManager.close();
		return mapping.findForward("success");

	}

	/**
	 * @return to views announce after updating it
	 */
	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager entityManager = factory.createEntityManager();
		DynaActionForm formAnnounce = (DynaActionForm) form;//NOSONAR
		String title = (String) formAnnounce.get("announceTitle");
		String content = (String) formAnnounce.get("announceContent");
		String stringExpiryDate = (String) formAnnounce
				.get("announceExpiryDate");
		Integer idAnnounce = (Integer) formAnnounce.get("idAnnounce");
		Announcement announce = entityManager.find(Announcement.class, idAnnounce);

		try {
			Date expiryDate = DateUtils.format(stringExpiryDate);
			if (0 > DateUtils.compareToToday(expiryDate)) {
				announce.setContent(content);
				announce.setEndDate(expiryDate);
				announce.setTitle(title);
				entityManager.getTransaction().begin();
				entityManager.merge(announce);
				entityManager.getTransaction().commit();

			} else {
				ActionMessages errors = new ActionMessages();
				errors.add("message", new ActionMessage(
						"errors.dateBelowDateToday"));
				saveErrors(request, errors);
			}
			request.setAttribute("announce", announce);
			request.setAttribute("owner", true);
		} catch (ParseException e) {
			servlet
					.log("class:ManageAnnounces methode:create exception whene formatying date ");
			e.printStackTrace();
			return mapping.findForward("failer");
		} finally {
			entityManager.close();
		}
		return mapping.findForward("success");
	}

	/**
     *
     */
	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager entityManager = factory.createEntityManager();

		Integer idAnnounce = Integer
				.valueOf(request.getParameter("idAnnounce"));

		Announcement announce = entityManager.find(Announcement.class, idAnnounce);
		servlet.log("remove announce");
		ActionMessages message = new ActionErrors();
		entityManager.getTransaction().begin();
		if (announce != null) {
			entityManager.remove(announce);
			entityManager.flush();
			entityManager.getTransaction().commit();

			entityManager.close();
			message.add("message", new ActionMessage("success.deleteAnnounce"));
		} else {
			message.add("message", new ActionMessage("errors.deleteAnnounce"));

		}
		saveMessages(request, message);
		return mapping.findForward("success");

	}

	/**
	 * @return list of announce
	 */
	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm seaarchForm = (DynaActionForm) form;//NOSONAR
		String textSearchAnnounce = (String) seaarchForm
				.get("textSearchAnnounce");
		EntityManager entityManager = factory.createEntityManager();
		List<Announcement> listAnnounces;
		// TODO warning
		servlet.log("search");
		if (textSearchAnnounce != null) {
			listAnnounces = entityManager
					.createQuery(
							"SELECT a FROM Announcement a WHERE  TYPE(a) IN(Announcement) AND ( a.title LIKE :textSearchAnnounce OR a.content LIKE :textSearchAnnounce) ")
					.setParameter("textSearchAnnounce",
							"%" + textSearchAnnounce + "%").getResultList();
		} else {
			listAnnounces = entityManager.createQuery(
					"SELECT a FROM Announcement a ").getResultList();
		}
		entityManager.close();
		request.setAttribute("listAnnounces", listAnnounces);
		return mapping.findForward("success");
	}

	/**
	 * @return announce in request
	 */
	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager entityManager = factory.createEntityManager();

		SocialEntity SocialEntity = UserUtils.getAuthenticatedUser(request,
				entityManager);
		Integer idAnnounce = Integer
				.valueOf(request.getParameter("idAnnounce"));

		Announcement announce = entityManager.find(Announcement.class, idAnnounce);
		SocialEntity SocialEntityOwner = (SocialEntity) entityManager
				.createQuery(
						"SELECT es FROM SocialEntity es,IN(es.interactions) e WHERE e = :announce")
				.setParameter("announce", announce).getSingleResult();

		entityManager.close();
		request.setAttribute("announce", announce);
		request.setAttribute("SocialEntity", SocialEntityOwner);
		servlet
				.log(SocialEntityOwner.toString()
						+ SocialEntityOwner.getName());
		if (SocialEntity.getId() == SocialEntityOwner.getId()) {
			request.setAttribute("owner", true);
		}

		return mapping.findForward("success");
	}
}
