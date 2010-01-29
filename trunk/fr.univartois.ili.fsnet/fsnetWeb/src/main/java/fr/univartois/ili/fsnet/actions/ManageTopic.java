package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.TopicMessage;

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
		DynaActionForm dynaForm = (DynaActionForm) form; 					// NOSONAR
		String topicSujet = (String) dynaForm.get("topicSubject");				// NOSONAR
		String messageDescription = (String) dynaForm.get("messageDescription");
		int hubId = Integer.valueOf(Integer.parseInt(dynaForm
				.getString("hubId")));
		Hub hub = em.find(Hub.class, hubId);
		SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request, em);
		Topic topic = new Topic(hub, socialEntity, topicSujet);
                TopicMessage message = new TopicMessage(messageDescription, socialEntity, topic);
		em.getTransaction().begin();
		hub.getTopics().add(topic);
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
		int hubId = Integer.valueOf(Integer.parseInt(request
				.getParameter("hubId")));
		Hub hub = em.find(Hub.class, hubId);
		if (request.getParameterMap().containsKey("topicId")) {
			int topicId = Integer.valueOf(request.getParameter("topicId"));
			Topic topic = em.find(Topic.class, topicId);
			em.getTransaction().begin();
			Query query = em
					.createQuery("DELETE FROM Topic topic WHERE topic.id = :topicId");
			query.setParameter("topicId", topicId);
			query.executeUpdate();
			hub.getTopics().remove(topic);
			em.getTransaction().commit();

		}
		em.close();
		return mapping.findForward("success");
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String topicSujet = (String) dynaForm.get("topicSujetSearch");
		int hubId = Integer.parseInt((String) dynaForm.get("hubId"));
		Hub hub = em.find(Hub.class, hubId);
		if (topicSujet != null) {
			TypedQuery<Topic> query = em
					.createQuery(
							"SELECT topic FROM Topic topic WHERE topic.title LIKE :sujetRea AND topic.hub = :hub ",
							Topic.class);
			query.setParameter("sujetRea", "%" + topicSujet + "%");
			query.setParameter("hub", hub);
			List<Topic> result = query.getResultList();
			request.setAttribute("resRearchTopics", result);
		}
		em.close();
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
			request.setAttribute("topic", result);
		}
		em.close();
		return mapping.findForward("success");
	}
}
