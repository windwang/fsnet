/*
 *  Copyright (C) 2010 Matthieu Proucelle <matthieu.proucelle at gmail.com>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
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
import org.eclipse.persistence.exceptions.DatabaseException;

import fr.univartois.ili.fsnet.auth.Authenticate;
import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Interet;

// TODO attention : rustine sur les requetes
// TODO attention : rustine sur les merge user de session

/**
 * Execute CRUD Actions (and more) for the entity interet
 * 
 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
 */
public class ManageInterests extends MappingDispatchAction implements
		CrudAction {

	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");

	private static final Logger logger = Logger.getAnonymousLogger();

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;

		String interestName = (String) dynaForm.get("createdInterestName");
		Interet interest = new Interet(new ArrayList<EntiteSociale>(),
				interestName);

		logger.info("new interest: " + interestName);

		try {
			em.getTransaction().begin();
			em.persist(interest);
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ActionErrors actionErrors = new ActionErrors();
			ActionMessage msg = new ActionMessage("interest.alreadyExists");
			actionErrors.add("createdInterestName", msg);
			saveErrors(request, actionErrors);
		}

		em.close();

		return mapping.findForward("success");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;
		int interestId = Integer.valueOf((String) dynaForm
				.get("addedInterestId"));

		EntiteSociale user = (EntiteSociale) request.getSession().getAttribute(
				Authenticate.AUTHENTICATED_USER);

		logger.info("add interest: id=" + interestId + " for user: "
				+ user.getNom() + " " + user.getPrenom() + " " + user.getId());

		// TODO requete provisoire

		try {
			Interet interest = (Interet) em
					.createQuery(
							"SELECT interest FROM Interet interest WHERE interest.id = :interestId")
					.setParameter("interestId", interestId).getSingleResult();
	
			boolean dirtyIsOk = true;
	
			for (Interet interestTmp : user.getLesinterets()) {
				if (interestTmp.getId() == interestId) {
					dirtyIsOk = false;
				}
			}
	
			if (dirtyIsOk) {
				user.getLesinterets().add(interest);
				em.getTransaction().begin();
				em.merge(user);
				em.getTransaction().commit();
	
				request.getSession().setAttribute(Authenticate.AUTHENTICATED_USER,
						user);
			} else {
				logger.info("Add interest refused");
			}
		} catch (PersistenceException e) {
		}
		em.close();

		return mapping.findForward("success");
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;
		int interestId = Integer.valueOf((String) dynaForm
				.get("removedInterestId"));

		EntiteSociale user = (EntiteSociale) request.getSession().getAttribute(
				Authenticate.AUTHENTICATED_USER);

		logger.info("remove interest: id=" + interestId + " for user: "
				+ user.getNom() + " " + user.getPrenom() + " " + user.getId());

		// TODO requete provisoire

		Interet interest = null;

		for (Interet interestTmp : user.getLesinterets()) {
			if (interestTmp.getId() == interestId) {
				interest = interestTmp;
				break;
			}
		}

		if (interest != null) {
			user.getLesinterets().remove(interest);
			em.getTransaction().begin();
			em.merge(user);
			em.getTransaction().commit();

			request.getSession().setAttribute(Authenticate.AUTHENTICATED_USER,
					user);
		} else {
			logger.info("remove interest refused");
		}

		em.close();

		return mapping.findForward("success");
	}

	// TODO modify
	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;
		int interestId = Integer.valueOf((String) dynaForm.get("modifiedInterestId"));
		String interestName = (String) dynaForm.get("modifiedInterestName");

		logger.info("interest modification: " + interestName);

		try {
			em.getTransaction().begin();
			em
					.createQuery(
							"UPDATE Interet interest SET interest.nomInteret = :interestName WHERE interest.id = :interestId")
					.setParameter("interestName", interestName)
					.setParameter("interestId", interestId)
					.executeUpdate();
			em.getTransaction().commit();
			EntiteSociale user = (EntiteSociale) request.getSession()
			.getAttribute(Authenticate.AUTHENTICATED_USER);
			user = em.find(EntiteSociale.class, user.getId());
			em.refresh(user);
			request.getSession().setAttribute(Authenticate.AUTHENTICATED_USER,
					user);
		} catch (DatabaseException ex) {
			ActionErrors actionErrors = new ActionErrors();
			ActionMessage msg = new ActionMessage("interest.alreadyExists");
			actionErrors.add("modifiedInterestName", msg);
			saveErrors(request, actionErrors);
		}

		em.close();

		return mapping.findForward("success");
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();

		// TODO verify if user has the right to do delete

		int interestId = Integer.valueOf(request
				.getParameter("deletedInterestId"));

		logger.info("interest deleted: id=" + interestId);

		try {
			em.getTransaction().begin();
			em
					.createQuery(
							"DELETE FROM Interet interest WHERE interest.id = :interestId")
					.setParameter("interestId", interestId).executeUpdate();
			em.getTransaction().commit();

			EntiteSociale user = (EntiteSociale) request.getSession()
					.getAttribute(Authenticate.AUTHENTICATED_USER);
			user = em.find(EntiteSociale.class, user.getId());
			em.refresh(user);
			request.getSession().setAttribute(Authenticate.AUTHENTICATED_USER,
					user);
		} catch (RollbackException ex) {
			ActionErrors actionErrors = new ActionErrors();
			ActionMessage msg = new ActionMessage("interest.notExists");
			actionErrors.add("error.interest.delete", msg);
			saveErrors(request, actionErrors);
		}

		em.close();

		return mapping.findForward("success");
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String interestName = "";

		if (dynaForm.get("searchInterestName") != null) {
			interestName = (String) dynaForm.get("searchInterestName");
		}

		logger.info("search interest: " + interestName);

		List<Interet> result = em.createQuery(
				"SELECT interest FROM Interet interest "
						+ "WHERE interest.nomInteret LIKE :interestName ",
				Interet.class).setParameter("interestName",
				'%' + interestName + '%').getResultList();
		em.close();

		if (result.size() > 0) {
			request.setAttribute("interestResult", result);
		}

		return mapping.findForward("success");
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		EntiteSociale user = (EntiteSociale) request.getSession().getAttribute(
				Authenticate.AUTHENTICATED_USER);

		logger.info("Displaying interests");

		// TODO requete provisoire

		List<Interet> listAllInterests = em.createQuery(
				"SELECT interest FROM Interet interest", Interet.class)
				.getResultList();

		List<Interet> finalList = new ArrayList<Interet>();
		boolean dirtyIsOK;
		for (Interet interest : listAllInterests) {
			dirtyIsOK = true;
			for (Interet interestEntity : user.getLesinterets()) {
				if (interestEntity.getId() == interest.getId()) {
					dirtyIsOK = false;
				}
			}
			if (dirtyIsOK) {
				finalList.add(interest);
			}
		}
		request.setAttribute("allInterests", listAllInterests);
		request.setAttribute("listInterests", finalList);

		em.close();
		return mapping.findForward("success");
	}
}
