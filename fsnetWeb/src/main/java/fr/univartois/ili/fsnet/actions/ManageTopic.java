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

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

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
public class ManageTopic extends ActionSupport implements CrudAction, ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;
	
	private int hubId;
	private int topicId;
	private String topicSubject;
	private String topicSujetSearch;	
	private String messageDescription;
	private String selectedInterests;
	private String searchText;
	private String pattern;
	private int[] interestsIds;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#create(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String create() throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			em.getTransaction().begin();
	
			HubFacade hubFacade = new HubFacade(em);
			Hub hub = hubFacade.getHub(hubId);
			SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request,
					em);
			TopicFacade topicFacade = new TopicFacade(em);
			Topic topic = topicFacade
					.createTopic(hub, socialEntity, topicSubject);

			InterestFacade fac = new InterestFacade(em);
			List<Interest> interests = new ArrayList<Interest>();
			int currentId;
			for (currentId = 0; currentId < interestsIds.length; currentId++) {
				interests.add(fac.getInterest(Integer
						.valueOf(interestsIds[currentId])));
			}
			InteractionFacade ifacade = new InteractionFacade(em);
			ifacade.addInterests(topic, interests);

			TopicMessageFacade topicMessageFacade = new TopicMessageFacade(em);
			topicMessageFacade.createTopicMessage(messageDescription,
					socialEntity, topic);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {

		} finally {
			em.close();
		}
		return SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#modify(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String modify() throws Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#delete(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String delete() throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
			InteractionFacade interactionFacade = new InteractionFacade(em);
			em.getTransaction().begin();

			HubFacade hubFacade = new HubFacade(em);
			Hub hub = hubFacade.getHub(hubId);
			TopicFacade topicFacade = new TopicFacade(em);
			Topic topic = topicFacade.getTopic(topicId);
			hub.getTopics().remove(topic);
			for (SocialEntity se : topic.getFollowingEntitys()) {
				se.getFavoriteInteractions().remove(topic);
			}
			interactionFacade.deleteInteraction(user, topic);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {

		} catch (RollbackException e) {
			ServletActionContext.getServletContext().log("commit error", e);
		} finally {
			em.close();
		}

		return SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#search(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String search() throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			Map<Topic, Message> topicsLastMessage = new HashMap<Topic, Message>();
			HubFacade hubFacade = new HubFacade(em);
			Hub hub = hubFacade.getHub(hubId);
			TopicFacade topicFacade = new TopicFacade(em);
			List<Topic> result = topicFacade.searchTopic(topicSubject, hub);

			for (Topic t : result) {
				List<TopicMessage> messages = t.getMessages();
				Message lastMessage = null;
				if (messages.size() > 0) {
					lastMessage = messages.get(messages.size() - 1);
				}
				topicsLastMessage.put(t, lastMessage);
			}

			request.setAttribute("hubResult", hub);
			request.setAttribute("topicsLastMessage", topicsLastMessage);
		} catch (NumberFormatException e) {

		} finally {
			em.close();
		}

		// TODO modify paginator for accepting HasMap

		return SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#display(org.apache.struts.
	 * action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String display() throws Exception {
		// TODO Use DynaForm to get topicId
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			int topicId = Integer.valueOf(request.getParameter("topicId"));

			TopicFacade topicFacade = new TopicFacade(em);
			Topic result = topicFacade.getTopic(topicId);

			Paginator<TopicMessage> paginator = new Paginator<TopicMessage>(
					result.getMessages(), request, "displayTopic", "topicId");
			request.setAttribute("topicMessageDisplayPaginator", paginator);
			request.setAttribute("topic", result);
		} catch (NumberFormatException e) {

		} finally {
			em.close();
		}

		return SUCCESS;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String searchYourTopics() throws Exception {

		EntityManager em = PersistenceProvider.createEntityManager();
		Map<Topic, Message> topicsLastMessage = new HashMap<Topic, Message>();
		SocialEntity creator = UserUtils.getAuthenticatedUser(request, em);
		Hub hub = em.find(Hub.class, hubId);

		if (pattern == null) {
			pattern = "";
		}
		em.getTransaction().begin();
		TypedQuery<Topic> query = em
				.createQuery(
						"SELECT topic FROM Topic topic WHERE topic.title LIKE :pattern AND topic.hub = :hub AND topic.creator = :creator",
						Topic.class);
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

		return SUCCESS;
	}

	public String getTopicSubject() {
		return topicSubject;
	}

	public void setTopicSubject(String topicSubject) {
		this.topicSubject = topicSubject;
	}

	public int getHubId() {
		return hubId;
	}

	public void setHubId(int hubId) {
		this.hubId = hubId;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getSelectedInterests() {
		return selectedInterests;
	}

	public void setSelectedInterests(String selectedInterests) {
		this.selectedInterests = selectedInterests;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getTopicSujetSearch() {
		return topicSujetSearch;
	}

	public void setTopicSujetSearch(String topicSujetSearch) {
		this.topicSujetSearch = topicSujetSearch;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
}
