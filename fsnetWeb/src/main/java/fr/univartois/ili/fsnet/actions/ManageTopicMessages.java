package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

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
public class ManageTopicMessages extends MappingDispatchAction implements
		CrudAction {

	private static final Logger logger = Logger.getAnonymousLogger();

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
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		logger.info("create Message: ");
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String messageDescription = (String) dynaForm.get("messageDescription");
		int topicId = Integer.valueOf(Integer.parseInt(dynaForm
				.getString("topicId")));

		TopicFacade topicFacade = new TopicFacade(em);
		Topic topic = topicFacade.getTopic(topicId);

		SocialEntity SocialEntity = UserUtils.getAuthenticatedUser(request, em);
		TopicMessageFacade topicMessageFacade = new TopicMessageFacade(em);
		topicMessageFacade.createTopicMessage(messageDescription, SocialEntity,
				topic);

		em.getTransaction().commit();

		int pageId = topicMessageFacade.getLastPageId(topicId,
				Paginator.DEFAULT_NUM_RESULT_PER_PAGE);

		em.close();
		ActionRedirect redirect = new ActionRedirect(
				mapping.findForward("success"));
		redirect.addParameter("topicId", dynaForm.get("topicId"));
		redirect.addParameter("pageId", pageId);
		return redirect;
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
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		logger.info("modify Message ");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String messageDescription = (String) dynaForm.get("messageDescription");
		int messageId = Integer.valueOf(Integer.parseInt(dynaForm
				.getString("messageId")));
		TopicMessageFacade topicMessageFacade = new TopicMessageFacade(em);
		TopicMessage message = topicMessageFacade.getTopicMessage(messageId);

		if (!message.getFrom().equals(user)) {
			throw new UnauthorizedOperationException("exception.message");
		}

		message.setBody(messageDescription);

		int topicId = Integer.valueOf(Integer.parseInt(dynaForm
				.getString("topicId")));
		TopicFacade topicFacade = new TopicFacade(em);
		Topic topic = topicFacade.getTopic(topicId);

		em.getTransaction().begin();
		em.merge(message);
		topic.getMessages();
		em.getTransaction().commit();
		em.close();
		return mapping.findForward("success");
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
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		int topicId = Integer.valueOf(request.getParameter("topicId"));
		TopicFacade topicFacade = new TopicFacade(em);
		Topic topic = topicFacade.getTopic(topicId);
		int messageId = Integer.valueOf(Integer.parseInt(request
				.getParameter("messageId")));
		TopicMessageFacade topicMessageFacade = new TopicMessageFacade(em);
		TopicMessage message = topicMessageFacade.getTopicMessage(messageId);
		topicMessageFacade.deleteTopicMessage(messageId);
		topic.getMessages().remove(message);
		em.getTransaction().commit();
		em.close();
		return mapping.findForward("success");
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
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
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
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR

		String topicId = (String) dynaForm.get("topicId");
		logger.info("display Message: ");

		request.setAttribute("topicId", topicId);
		EntityManager em = PersistenceProvider.createEntityManager();
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
		if (request.getParameter("messageId") != null) {
			String messageId = (String) dynaForm.get("messageId");

			TopicMessageFacade topicMessageFacade = new TopicMessageFacade(em);
			TopicMessage message = topicMessageFacade.getTopicMessage(Integer
					.parseInt(messageId));
			request.setAttribute("message", message);
		}
		em.close();
		return mapping.findForward("success");
	}
}
