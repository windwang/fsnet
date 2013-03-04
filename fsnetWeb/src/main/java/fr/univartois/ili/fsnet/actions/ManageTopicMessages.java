package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionSupport;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.TopicMessage;
import fr.univartois.ili.fsnet.facade.TopicFacade;
import fr.univartois.ili.fsnet.facade.TopicMessageFacade;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;

/**
 * 
 * @author Cerelia Besnainou and Audrey Ruellan
 */
public class ManageTopicMessages extends ActionSupport implements CrudAction {

	private static final Logger LOGGER = Logger.getAnonymousLogger();
	private static final String SUCCES_ACTION_NAME = "success";
	private static final String TOPIC_ID_FORM_FIELD_NAME = "topicId";
	private static final String MESSAGE_ID_FORM_FIELD_NAME = "messageId";

	private String messageDescription;
	private int topicId;
	private int messageId;

	public String getMessageDescription() {
		return messageDescription;
	}

	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}

	private HttpServletRequest request;

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
	public String create() throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			em.getTransaction().begin();

			TopicFacade topicFacade = new TopicFacade(em);
			Topic topic = topicFacade.getTopic(topicId);

			SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request,
					em);
			TopicMessageFacade topicMessageFacade = new TopicMessageFacade(em);
			topicMessageFacade.createTopicMessage(messageDescription,
					socialEntity, topic);

			em.getTransaction().commit();

			int pageId = topicMessageFacade.getLastPageId(topicId,
					Paginator.DEFAULT_NUM_RESULT_PER_PAGE);

			em.close();

//			ACTIONREDIRECT REDIRECT = NEW ACTIONREDIRECT(
//					MAPPING.FINDFORWARD(SUCCES_ACTION_NAME));
//			REDIRECT.ADDPARAMETER(TOPIC_ID_FORM_FIELD_NAME,
//					DYNAFORM.GET(TOPIC_ID_FORM_FIELD_NAME));
//			REDIRECT.ADDPARAMETER("PAGEID", PAGEID);
//
//			RETURN REDIRECT;
		} catch (NumberFormatException e) {

		} finally {
			em.close();
		}

		return SUCCES_ACTION_NAME;
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
	public String modify() throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);

			TopicMessageFacade topicMessageFacade = new TopicMessageFacade(em);
			TopicMessage message = topicMessageFacade
					.getTopicMessage(messageId);

			if (!message.getFrom().equals(user)) {
				throw new UnauthorizedOperationException("exception.message");
			}

			message.setBody(messageDescription);

			TopicFacade topicFacade = new TopicFacade(em);
			Topic topic = topicFacade.getTopic(topicId);

			em.getTransaction().begin();
			em.merge(message);
			topic.getMessages();
			em.getTransaction().commit();
		} catch (NumberFormatException e) {

		} finally {
			em.close();
		}

		return SUCCES_ACTION_NAME;
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
	public String delete() throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			em.getTransaction().begin();
			int topicId = Integer.valueOf(request
					.getParameter(TOPIC_ID_FORM_FIELD_NAME));
			TopicFacade topicFacade = new TopicFacade(em);
			Topic topic = topicFacade.getTopic(topicId);
			int messageId = Integer.valueOf(Integer.parseInt(request
					.getParameter(MESSAGE_ID_FORM_FIELD_NAME)));
			TopicMessageFacade topicMessageFacade = new TopicMessageFacade(em);
			TopicMessage message = topicMessageFacade
					.getTopicMessage(messageId);
			topicMessageFacade.deleteTopicMessage(messageId);
			topic.getMessages().remove(message);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {

		} finally {
			em.close();
		}

		return SUCCES_ACTION_NAME;
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
	public String search() throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
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
	public String display() throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {


			request.setAttribute(TOPIC_ID_FORM_FIELD_NAME, topicId);
			Topic currentTopic = em.find(Topic.class, Integer.valueOf(topicId));
			List<TopicMessage> messages = currentTopic.getMessages();
			List<TopicMessage> lastMessages;
			if (messages.size() > 3) {
				lastMessages = messages.subList(messages.size() - 3,
						messages.size());
			} else {
				lastMessages = messages;
			}
			Collections.reverse(lastMessages);
			request.setAttribute("lastMessages", lastMessages);
			if (request.getParameter(MESSAGE_ID_FORM_FIELD_NAME) != null) {

				TopicMessageFacade topicMessageFacade = new TopicMessageFacade(
						em);

				TopicMessage message = topicMessageFacade.getTopicMessage(messageId);
						
				request.setAttribute("message", message);
			}
		} catch (NumberFormatException e) {

		} finally {
			em.close();
		}

		return SUCCES_ACTION_NAME;
	}
}
