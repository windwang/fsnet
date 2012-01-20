package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
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
import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.TopicMessage;
import fr.univartois.ili.fsnet.facade.HubFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.InterestFacade;
import fr.univartois.ili.fsnet.facade.TopicFacade;
import fr.univartois.ili.fsnet.facade.TopicMessageFacade;

/**
 * 
 * @author Zhu Rui <zrhurey at gmail.com>
 */
public class ManageTopic extends MappingDispatchAction implements CrudAction {

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String topicSujet = (String) dynaForm.get("topicSubject"); 
		String messageDescription = (String) dynaForm.get("messageDescription");
		int hubId = Integer.valueOf(Integer.parseInt(dynaForm
				.getString("hubId")));
		HubFacade hubFacade = new HubFacade(em);
		Hub hub = hubFacade.getHub(hubId);
		SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request, em);
		TopicFacade topicFacade = new TopicFacade(em);
		Topic topic = topicFacade.createTopic(hub, socialEntity, topicSujet);

		String InterestsIds[] = (String[]) dynaForm.get("selectedInterests");
		InterestFacade fac = new InterestFacade(em);
		List<Interest> interests = new ArrayList<Interest>();
		int currentId;
		for (currentId = 0; currentId < InterestsIds.length; currentId++) {
			interests.add(fac.getInterest(Integer
					.valueOf(InterestsIds[currentId])));
		}
		InteractionFacade ifacade = new InteractionFacade(em);
		ifacade.addInterests(topic, interests);

		TopicMessageFacade topicMessageFacade = new TopicMessageFacade(em);
		topicMessageFacade.createTopicMessage(messageDescription, socialEntity,
				topic);
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
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		int hubId = Integer.parseInt((String) dynaForm.get("hubId"));
		int topicId = Integer.valueOf((String) dynaForm.get("topicId"));
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		InteractionFacade interactionFacade = new InteractionFacade(em);
		em.getTransaction().begin();

		HubFacade hubFacade = new HubFacade(em);
		Hub hub = hubFacade.getHub(hubId);
		TopicFacade topicFacade = new TopicFacade(em);
		Topic topic = topicFacade.getTopic(topicId);
		hub.getTopics().remove(topic);
		for(SocialEntity se : topic.getFollowingEntitys()){
			se.getFavoriteInteractions().remove(topic);
		}
		interactionFacade.deleteInteraction(user, topic);
		try{
			em.getTransaction().commit();
		}catch(RollbackException e){
			servlet.log("commit error", e);
		}
		em.close();
		return mapping.findForward("success");
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String topicSujet = (String) dynaForm.get("topicSujetSearch");
		int hubId = Integer.parseInt((String) dynaForm.get("hubId"));
		Map<Topic, Message> topicsLastMessage = new HashMap<Topic, Message>();
		HubFacade hubFacade = new HubFacade(em);
		Hub hub = hubFacade.getHub(hubId);
		TopicFacade topicFacade = new TopicFacade(em);
		List<Topic> result = topicFacade.searchTopic(topicSujet, hub);


		for (Topic t : result) {
			List<TopicMessage> messages = t.getMessages();
			Message lastMessage = null;
			if (messages.size() > 0) {
				lastMessage = messages.get(messages.size() - 1);
			}
			topicsLastMessage.put(t, lastMessage);
		}

		em.close();
		request.setAttribute("hubResult", hub);
		request.setAttribute("topicsLastMessage", topicsLastMessage);
		return mapping.findForward("success");
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		//TODO Use DynaForm to get topicId
		int topicId = Integer.valueOf(request.getParameter("topicId"));
		EntityManager em = PersistenceProvider.createEntityManager();

		TopicFacade topicFacade = new TopicFacade(em);
		Topic result = topicFacade.getTopic(topicId);

		Paginator<TopicMessage> paginator = new Paginator<TopicMessage>(result.getMessages(), request, "displayTopic", "topicId");
		request.setAttribute("topicMessageDisplayPaginator", paginator);
		request.setAttribute("topic", result);

		em.close();
		return mapping.findForward("success");
	}

	public ActionForward searchYourTopics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {

		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String pattern = (String) dynaForm.get("searchText");
		int hubId = Integer.parseInt((String) dynaForm.get("hubId"));
		Map<Topic, Message> topicsLastMessage = new HashMap<Topic, Message>();
		SocialEntity creator = UserUtils.getAuthenticatedUser(request, em);
		Hub hub = em.find(Hub.class, hubId);

		if (pattern==null) {
			pattern = "";
		}
		em.getTransaction().begin();
		TypedQuery<Topic> query = em.createQuery("SELECT topic FROM Topic topic WHERE topic.title LIKE :pattern AND topic.hub = :hub AND topic.creator = :creator", Topic.class);
		query.setParameter("pattern", "%" + pattern + "%");
		query.setParameter("hub", hub);
		query.setParameter("creator", creator);
		List<Topic> topics = query.getResultList();

		for (Topic t : topics) {
			List<TopicMessage> messages = t.getMessages();
			Message lastMessage = null;
			if (messages.size() > 0) {
				lastMessage = messages.get(messages.size() - 1);
			}
			topicsLastMessage.put(t, lastMessage);
		}

		em.getTransaction().commit();

		em.close();
		request.setAttribute("hubResult", hub);
		request.setAttribute("topicsLastMessage", topicsLastMessage);

		return mapping.findForward("success");
	}
}
