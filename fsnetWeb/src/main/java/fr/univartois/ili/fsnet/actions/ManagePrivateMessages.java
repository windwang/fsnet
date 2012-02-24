package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.PrivateMessage;
import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.PrivateMessageFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;

/**
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class ManagePrivateMessages extends MappingDispatchAction implements
		CrudAction {

	private static final String SUCCES_ATTRIBUTE_NAME = "success";

	
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

		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(
				request, em);
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		if (!fascade.isAuthorized(authenticatedUser, Right.SEND_MESSAGE)) {
			em.close();
			return new ActionRedirect(mapping.findForward("unauthorized"));
		}

		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String to = dynaForm.getString("messageTo");
		String subject = dynaForm.getString("messageSubject");
		String body = dynaForm.getString("messageBody");
		em.getTransaction().begin();
		SocialEntityFacade sef = new SocialEntityFacade(em);
		StringTokenizer stk = new StringTokenizer(to, ",");
		List<SocialEntity> receivers = new ArrayList<SocialEntity>();
		addRightToRequest(request);
		while (stk.hasMoreTokens()) {
			String email = stk.nextToken();
			SocialEntity findByEmail = sef.findByEmail(email);
			if (findByEmail == null) {
				ActionErrors errors = new ActionErrors();
				errors.add("messageTo", new ActionMessage(
						"privatemessages.to.error", email));
				saveErrors(request, errors);
				em.getTransaction().commit();
				em.close();
				return mapping.getInputForward();
			}
			receivers.add(findByEmail);
		}
		for (SocialEntity se : receivers) {
			PrivateMessageFacade pmf = new PrivateMessageFacade(em);
			pmf.sendPrivateMessage(body, authenticatedUser, subject, se);
		}
		em.getTransaction().commit();
		em.close();
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
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
		throw new UnsupportedOperationException("Not supported yet.");
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
		addRightToRequest(request);
		try {
			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			int messageId = Integer.parseInt(dynaForm.getString("messageId"));
			SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(
					request, em);

			PrivateMessageFacade pmf = new PrivateMessageFacade(em);
			PrivateMessage privateMessage = pmf.getPrivateMessage(messageId);
			if (privateMessage == null) {
				em.close();
				throw new UnauthorizedOperationException("");
			}
			em.getTransaction().begin();
			pmf.deletePrivateMessage(authenticatedUser, privateMessage);
			em.getTransaction().commit();
			em.close();
			return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
		} catch (NumberFormatException e) {
			servlet.log("GRAVE ERROR : MUST BE VALIDATE BY STRUTS", e);
		}
		em.close();
		return mapping.findForward("fail");
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
	public ActionForward deleteMulti(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		addRightToRequest(request);
		try {
			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR

			String[] selectedMessages = (String[]) dynaForm
					.get("selectedMessages");
			SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(
					request, em);
			PrivateMessageFacade pmf = new PrivateMessageFacade(em);
			em.getTransaction().begin();
			for (int i = 0; i < selectedMessages.length; i++) {
				PrivateMessage m = pmf.getPrivateMessage(Integer
						.valueOf(selectedMessages[i]));
				pmf.deletePrivateMessage(authenticatedUser, m);
			}
			em.flush();
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
		}
		em.close();
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
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
	public ActionForward deleteMulti2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		EntityManager em = PersistenceProvider.createEntityManager();
		addRightToRequest(request);
		try {
			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR

			String[] selectedMessages = (String[]) dynaForm
					.get("selectedMessages");
			SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(
					request, em);
			PrivateMessageFacade pmf = new PrivateMessageFacade(em);
			em.getTransaction().begin();
			for (int i = 0; i < selectedMessages.length; i++) {
				PrivateMessage m = pmf.getPrivateMessage(Integer
						.valueOf(selectedMessages[i]));
				pmf.deletePrivateMessage(authenticatedUser, m);
			}
			em.flush();
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
		}

		em.close();
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
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
	public ActionForward inbox(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(
				request, em);
		addRightToRequest(request);
		List<PrivateMessage> userMessages = new ArrayList<PrivateMessage>(
				authenticatedUser.getReceivedPrivateMessages());
		Collections.reverse(userMessages);

		/*
		 * Paginator<PrivateMessage> paginator = new
		 * Paginator<PrivateMessage>(userMessages, request, "inboxMessages");
		 * 
		 * request.setAttribute("inBoxMessagesPaginator", paginator);
		 */
		request.setAttribute("inBoxMessages", userMessages);

		refreshNumNewMessages(request, userMessages);
		refreshNumNewMessages(request, em);
		em.close();
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
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
	public ActionForward outbox(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(
				request, em);
		addRightToRequest(request);
		if (form == null) {
			List<PrivateMessage> userMessages = new ArrayList<PrivateMessage>(
					authenticatedUser.getSentPrivateMessages());
			Collections.reverse(userMessages);

			request.setAttribute("outBoxMessages", userMessages);

		} else {
			servlet.log("ManagePrivateMessage.display must be not ask");
		}
		em.close();
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
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

		EntityManager em = PersistenceProvider.createEntityManager();
		addRightToRequest(request);
		try {
			if (form != null) {
				DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
				int messageId = Integer.parseInt(dynaForm
						.getString("messageId"));
				SocialEntity authenticatedUser = UserUtils
						.getAuthenticatedUser(request, em);
				PrivateMessageFacade pmf = new PrivateMessageFacade(em);
				PrivateMessage privateMessage = pmf
						.getPrivateMessage(messageId);

				List<PrivateMessage> userMessages = new ArrayList<PrivateMessage>(
						pmf.getConversation(privateMessage.getFrom(),
								privateMessage.getSubject(),
								privateMessage.getTo()));
				Collections.reverse(userMessages);

				if (privateMessage != null
						&& (authenticatedUser.equals(privateMessage.getFrom()) || authenticatedUser
								.equals(privateMessage.getTo()))) {
					if (authenticatedUser.equals(privateMessage.getTo())) {
						em.getTransaction().begin();
						privateMessage.setReed(true);
						refreshNumNewMessages(request, em);
						em.getTransaction().commit();
					}

					Paginator<PrivateMessage> paginator = new Paginator<PrivateMessage>(
							userMessages, request, "conversationMessages");

					request.setAttribute("conversationMessages", paginator);
					request.setAttribute("theMessage", privateMessage);
					em.close();
					return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
				} else {
					em.close();
					throw new UnauthorizedOperationException(
							"Must be the owner of message");
				}
			}
		} catch (NumberFormatException e) {
		}
		// NEVER HAPPEND
		return null;
	}

	/**
	 * @param messages
	 * @return
	 */
	private static final int calculateNumNewMessage(
			Collection<PrivateMessage> messages) {
		int numNonReedPrivateMessages = 0;
		for (PrivateMessage pm : messages) {
			if (!pm.isReed()) {
				numNonReedPrivateMessages++;
			}
		}
		return numNonReedPrivateMessages;
	}

	/**
	 * @param request
	 * @param messages
	 */
	private static final void refreshNumNewMessages(HttpServletRequest request,
			Collection<PrivateMessage> messages) {
		HttpSession session = request.getSession();
		int numNonReedPrivateMessages = calculateNumNewMessage(messages);
		session.setAttribute("numNonReedPrivateMessages",
				numNonReedPrivateMessages);
	}

	/**
	 * Store the number of non reed private messages in the session
	 * 
	 * @param request
	 * @param em
	 */
	public static final void refreshNumNewMessages(HttpServletRequest request,
			EntityManager em) {
		HttpSession session = request.getSession();
		SocialEntity se = UserUtils.getAuthenticatedUser(request, em);
		int numNonReedPrivateMessages = calculateNumNewMessage(se
				.getReceivedPrivateMessages());
		session.setAttribute("numNonReedPrivateMessages",
				numNonReedPrivateMessages);
	}

	/**
	 * @param request
	 */
	private void addRightToRequest(HttpServletRequest request) {
		SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request);
		Right rightAnswerMessage = Right.ANSWER_MESSAGE;
		Right rightSendMessage = Right.SEND_MESSAGE;
		request.setAttribute("rightAnswerMessage", rightAnswerMessage);
		request.setAttribute("rightSendMessage", rightSendMessage);
		request.setAttribute("socialEntity", socialEntity);
	}

}
