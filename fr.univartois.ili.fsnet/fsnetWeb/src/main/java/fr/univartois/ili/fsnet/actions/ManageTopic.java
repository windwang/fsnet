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
import java.util.Date;
import java.util.List;

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
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.Topic;

/**
 * 
 * @author Zhu Rui <zrhurey at gmail.com>
 */
public class ManageTopic extends MappingDispatchAction implements CrudAction {

	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String topicSujet = (String) dynaForm.get("topicSujet");
		int hubId = Integer.valueOf(Integer.parseInt(dynaForm.getString("hubId")));
		Hub hub = em.find(Hub.class, hubId);
		
		Date date = new Date();
		
		EntiteSociale entiteSociale = (EntiteSociale) request.getSession()
				.getAttribute(Authenticate.AUTHENTICATED_USER);
		Topic topic = new Topic(topicSujet, date, null, hub, entiteSociale);
		
		em.getTransaction().begin();
		hub.getLesTopics().add(topic);
		em.getTransaction().commit();
		
		em.close();
		return mapping.findForward("success");
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
		EntityManager em = factory.createEntityManager();
		if (request.getParameterMap().containsKey("topicId")) {
			int topicId = Integer.valueOf(request.getParameter("topicId"));
			em.getTransaction().begin();
			Query query = em
					.createQuery("DELETE FROM Topic topic WHERE topic.id = :topicId");
			query.setParameter("topicId", topicId);
			query.executeUpdate();
			em.getTransaction().commit();
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
		String topicSujet = (String) dynaForm.get("topicSujetSearch");
		int hubId = Integer.parseInt( (String) dynaForm.get("hubId")) ;
		Hub hub = em.find(Hub.class, hubId);
		if (topicSujet != null) {
			Query query = em
					.createQuery("SELECT topic FROM Topic topic WHERE topic.sujet LIKE :sujetRea AND topic.hub = :hub ");
			query.setParameter("sujetRea", "%" + topicSujet + "%");
			query.setParameter("hub",  hub );
			List<Topic> result = query.getResultList();
			request.setAttribute("resRearchTopics", result);
		}
		return mapping.findForward("success");
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		if (request.getParameterMap().containsKey("topicId")) {
			int topicId = Integer.valueOf(request.getParameter("topicId"));
			Query query = em
					.createQuery("SELECT topic FROM Topic topic WHERE topic.id = :topicId ");
			query.setParameter("topicId", topicId);
			Topic result = (Topic) query.getSingleResult();
			request.getSession().setAttribute("topic", result);
			
		}  
		if (request.getParameterMap().containsKey("msgId")) {
            int msgId = Integer.valueOf(request.getParameter("msgId"));
            Message msg = em.find(Message.class, msgId);
            request.getSession().setAttribute("contenu", msg.getContenu());
        }
		
		return mapping.findForward("success");
	}

}
