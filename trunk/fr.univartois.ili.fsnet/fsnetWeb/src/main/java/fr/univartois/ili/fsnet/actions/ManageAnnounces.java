package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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

import fr.univartois.ili.fsnet.auth.Authenticate;
import fr.univartois.ili.fsnet.entities.Annonce;
import fr.univartois.ili.fsnet.entities.EntiteSociale;

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
		// TODO update paramater
		EntiteSociale entiteSociale = (EntiteSociale) request.getSession()
				.getAttribute(Authenticate.AUTHENTICATED_USER);

		DynaActionForm formAnnounce = (DynaActionForm) form;
		String title = (String) formAnnounce.get("announceTitle");
		String content = (String) formAnnounce.get("announceContent");
		String stringExpiryDate = (String) formAnnounce
				.get("announceExpiryDate");

		Date today = new Date();
		DateFormat simpleFormat;
		simpleFormat = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
		try {
			Date expiryDate = (Date) simpleFormat.parse(stringExpiryDate);
			// TODO check if this test is necessary
			if (0 < expiryDate.compareTo(today)) {
				Annonce announce = new Annonce(title, today, content,
						expiryDate, "Y", entiteSociale);
				entityManager.getTransaction().begin();
				entityManager.persist(announce);
				entityManager.getTransaction().commit();
			} else {
				ActionMessages errors = new ActionErrors();
				errors.add("message", new ActionMessage(
						"errors.dateLessThanCurrentDate"));
				saveErrors(request, errors);
			}
		} catch (ParseException e) {
			servlet.log(getClass().getName()
					+ " methode:create exception whene formatying date ");
			e.printStackTrace();
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
		Date today = new Date();
		DynaActionForm formAnnounce = (DynaActionForm) form;
		String title = (String) formAnnounce.get("titleAnnouncement");
		String content = (String) formAnnounce.get("contentAnnouncement");
		String stringExpiryDate = (String) formAnnounce
				.get("expiryDateAnnouncement");
		Integer idAnnounce = (Integer) formAnnounce.get("idAnnounce");
		DateFormat simpleFormat = new SimpleDateFormat("dd/MM/yy",
				Locale.FRANCE);
		EntiteSociale entiteSociale = (EntiteSociale) request.getSession()
				.getAttribute(Authenticate.AUTHENTICATED_USER);
		try {
			Date expiryDate = (Date) simpleFormat.parse(stringExpiryDate);
			// TODO check if this test is necessary
			if (0 < expiryDate.compareTo(today)) {
				Annonce announce = new Annonce(title, today, content,
						expiryDate, "Y", entiteSociale);
				announce.setId(idAnnounce);
				entityManager.getTransaction().begin();
				entityManager.merge(announce);
				entityManager.getTransaction().commit();
			} else {
				ActionMessages errors = new ActionMessages();
				errors.add("message", new ActionMessage(
						"errors.dateLessThanCurrentDate"));
				saveErrors(request, errors);
			}
		} catch (ParseException e) {
			servlet
					.log("class:ManageAnnounces methode:create exception whene formatying date ");
			e.printStackTrace();
		}
		entityManager.close();
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

		Integer idAnnounce = (Integer) request.getAttribute("idAnnounce");
		Annonce announce = new Annonce();
		announce.setId(idAnnounce);
		entityManager.getTransaction().begin();
		// TODO use try catch with remove can be null!
		entityManager.remove(entityManager.find(Annonce.class, idAnnounce));
		entityManager.getTransaction().commit();
		return mapping.findForward("success");

	}

	/**
	 * @return list of announce
	 */
	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm seaarchForm = (DynaActionForm) form;
		String textSearchAnnounce = (String) seaarchForm.get("textSearchAnnounce");
		EntityManager entityManager = factory.createEntityManager();
		List<Annonce> listAnnounces;
		//TODO warning
		servlet.log("search");
		if (textSearchAnnounce != null) {
			listAnnounces = entityManager
					.createQuery(
							"SELECT a FROM Annonce a WHERE a.nom LIKE :textSearchAnnounce OR a.contenu LIKE :textSearchAnnounce ")
					.setParameter("textSearchAnnounce", "%"+textSearchAnnounce+"%")
					.getResultList();
		} else {
			listAnnounces = entityManager.createQuery(
					"SELECT a FROM Annonce a ")
					.getResultList();
		}

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

		Integer idAnnounce = Integer.valueOf(request.getParameter("idAnnounce"));
		// TODO use try catch with find can be null!
		Annonce announce = entityManager.find(Annonce.class, idAnnounce);
		Query q = entityManager.createQuery(
				"SELECT es FROM EntiteSociale es,IN(es.lesinteractions) e WHERE e.id = :idAnnounce")
		.setParameter("idAnnounce", idAnnounce);
		System.out.println(q.getSingleResult().getClass());
		EntiteSociale entiteSociale = (EntiteSociale) entityManager
		.createQuery(
				"SELECT es FROM EntiteSociale es,IN(es.lesinteractions) e WHERE e.id = :idAnnounce")
		.setParameter("idAnnounce", idAnnounce).getSingleResult();
		servlet.log(entiteSociale.getNom()+"------------------------------------------");
		request.setAttribute("announce", announce);
		request.setAttribute("entiteSociale", entiteSociale);
		
		return mapping.findForward("success");
	}

}
