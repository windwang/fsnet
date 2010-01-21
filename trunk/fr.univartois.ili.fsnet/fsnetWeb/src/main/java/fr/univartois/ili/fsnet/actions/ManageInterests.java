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
import javax.persistence.NoResultException;
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

import fr.univartois.ili.fsnet.auth.Authenticate;
import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Interet;

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
		String interestName = (String) dynaForm.get("interestName");
		Interet interet = new Interet(new ArrayList<EntiteSociale>(),
				interestName);

		logger.info("new interest: " + interestName);

		try {
			em.getTransaction().begin();
			em.persist(interet);
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ActionErrors actionErrors = new ActionErrors();
			ActionMessage msg = new ActionMessage("interest.alreadyExists");
			actionErrors.add("interest", msg);
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
		int interestId = Integer.valueOf((String)dynaForm.get("interestId"));

		EntiteSociale user = (EntiteSociale) request.getSession().getAttribute(
				Authenticate.AUTHENTICATED_USER);

		logger.info("add interest: id=" + interestId + " for user: "
				+ user.getNom() + " " + user.getPrenom()+" "+user.getId());

		// TODO requete provisoire 
		
		Interet interest = (Interet) em.createQuery(
				"SELECT interest FROM Interet interest WHERE interest.id = :interestId")
				.setParameter("interestId", interestId)
				.getSingleResult();
		
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
				
			request.getSession().setAttribute(Authenticate.AUTHENTICATED_USER, user);
		} else {
			logger.info("Add interest refused");
		}
	
		em.close();

		return mapping.findForward("success");
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;
		int interestId = Integer.valueOf((String)dynaForm.get("interestId"));

		EntiteSociale user = (EntiteSociale) request.getSession().getAttribute(
				Authenticate.AUTHENTICATED_USER);

		logger.info("remove interest: id=" + interestId + " for user: "
				+ user.getNom() + " " + user.getPrenom()+" "+user.getId());

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
				
			request.getSession().setAttribute(Authenticate.AUTHENTICATED_USER, user);
		} else {
			logger.info("remove interest refused");
		}
	
		em.close();

		return mapping.findForward("success");
	}
	
	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;
		int interestId = Integer.valueOf((String) dynaForm.get("interestId"));
		String interestName = (String) dynaForm.get("interestName");

		logger.info("interest modification: " + interestName);

		try {
			em.getTransaction().begin();
			em.createQuery("UPDATE Interet SET nomInteret = :interestName WHERE idInteret = :interestId")
					.setParameter("interestName", interestName)
					.setParameter("interestId", interestId)
					.executeUpdate();
			em.getTransaction().commit();
		} catch (RollbackException ex) {
			ActionErrors actionErrors = new ActionErrors();
			ActionMessage msg = new ActionMessage("interest.alreadyExists");
			actionErrors.add("interest", msg);
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

		if (request.getParameterMap().containsKey("interestId")) {
			int interestId = Integer
					.valueOf(request.getParameter("interestId"));

			logger.info("interest deleted: id=" + interestId);

			try {
				em.getTransaction().begin();
				em.createQuery(
						"DELETE FROM Interet WHERE idInteret = :interestId")
						.setParameter("interestId", interestId).executeUpdate();
				em.getTransaction().commit();
			} catch (RollbackException ex) {
				ActionErrors actionErrors = new ActionErrors();
				ActionMessage msg = new ActionMessage("interest.notExists");
				actionErrors.add("interest", msg);
				saveErrors(request, actionErrors);
			}

			em.close();
		}

		return mapping.findForward("success");
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String interestName = (String) dynaForm.get("interestName");

		logger.info("search interest: " + interestName);

		List<Interet> result = em
				.createQuery(
						"SELECT interest FROM Interest interest WHERE interest.nomInteret LIKE '%:interestName%' ")
				.setParameter("interestName", interestName).getResultList();
		em.close();
		
		request.setAttribute("interestResult", result);
		
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
		
		if (user != null) {
			// TODO requete provisoire
			
			List<Interet> listInterests = em.createQuery(
					"SELECT interest FROM Interet interest")
					.getResultList();
						
			List<Interet> finalList = new ArrayList<Interet>();
			boolean dirtyIsOK;
			for (Interet interest : listInterests) {
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
			
			request.setAttribute("listInterests", finalList);
		} else {
			List<Interet> listInterests = em.createQuery(
					"SELECT interest FROM Interet interest").getResultList();
			request.setAttribute("listInterests", listInterests);
		}
		
		em.close();
		return mapping.findForward("success");
	}

	public ActionForward displaySocialEntityList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();

		if (request.getParameterMap().containsKey("interestId")) {
			int interestId = Integer
					.valueOf(request.getParameter("interestId"));

			logger.info("search social entity for interest: id=" + interestId);

			List<EntiteSociale> result = em
					.createQuery(
							"SELECT socialEntity FROM Interest interest, IN(interest.lesEntites) socialEntity WHERE interest.id = :interestId")
					.setParameter("interestId", interestId).getResultList();

			request.setAttribute("interestResult", result);
		}

		return mapping.findForward("success");
	}
}
