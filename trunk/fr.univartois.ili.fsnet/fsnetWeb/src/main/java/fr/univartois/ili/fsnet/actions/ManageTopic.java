package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
import fr.univartois.ili.fsnet.facade.forum.iliforum.HubFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.TopicFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.TopicMessageFacade;

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
		HubFacade hubFacade = new HubFacade(em);
		Hub hub = hubFacade.getHub(hubId);
		SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request, em);
		TopicFacade topicFacade = new TopicFacade(em);
		Topic topic = topicFacade.createTopic(hub, socialEntity, topicSujet);
		TopicMessageFacade topicMessageFacade = new TopicMessageFacade(em);
		TopicMessage message = topicMessageFacade.createTopicMessage(messageDescription, socialEntity, topic);
		em.getTransaction().begin();
		hub.getTopics().add(topic);
		topic.getMessages().add(message);
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
		HubFacade hubFacade = new HubFacade(em);
		Hub hub = hubFacade.getHub(hubId);
		if (request.getParameterMap().containsKey("topicId")) {
			int topicId = Integer.valueOf(request.getParameter("topicId"));
			TopicFacade topicFacade = new TopicFacade(em);
			topicFacade.deleteTopic(topicId);
			Topic topic = topicFacade.getTopic(topicId);
			hub.getTopics().remove(topic);
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
		HubFacade hubFacade = new HubFacade(em);
		Hub hub = hubFacade.getHub(hubId);
		TopicFacade topicFacade = new TopicFacade(em);
		List<Topic> result = topicFacade.searchTopic(topicSujet, hub);
		request.setAttribute("resRearchTopics", result);
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
			TopicFacade topicFacade = new TopicFacade(em);
			Topic result = topicFacade.getTopic(topicId);
			request.setAttribute("topic", result);
		}
		em.close();
		return mapping.findForward("success");
	}
}
