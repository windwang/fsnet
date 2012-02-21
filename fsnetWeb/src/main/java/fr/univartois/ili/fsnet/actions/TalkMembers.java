package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;
import org.jivesoftware.smack.Chat;
import org.json.simple.JSONObject;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.talk.ITalk;
import fr.univartois.ili.fsnet.commons.talk.Talk;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.commons.utils.TalkException;
import fr.univartois.ili.fsnet.commons.utils.TalkJsonMsg;
import fr.univartois.ili.fsnet.commons.utils.TalkMessage;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.tools.PropsUtils;

/**
 * manage chat action.
 * 
 * @author habib
 * 
 */
public class TalkMembers extends MappingDispatchAction {

	private static List<TalkMessage> talkMessages;
	private final String xmppServer = PropsUtils.getProperty("xmpp.server"); // "localhost";
	private final int port = Integer.parseInt(PropsUtils
			.getProperty("xmpp.server.port"));// 5222;
	private final String xmppServerDomain = PropsUtils
			.getProperty("xmpp.domain"); // "master11";
	private final String password = PropsUtils.getProperty("xmpp.password");

	/**
	 * @param request
	 * @return
	 */
	private ITalk initTalkMembers(HttpServletRequest request) {

		ITalk talk = new Talk();
		try {
			EntityManager em = PersistenceProvider.createEntityManager();
			SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
			String name = member.getName().replaceAll(" ", "").toLowerCase();
			String email = member.getEmail();
			// String pass = member.getPassword();

			Map<String, String> map = new HashMap<String, String>();
			map.put("email", email);
			map.put("name", name);
			talk.initConnexion(xmppServer, port, name, password, map);
			request.getSession().setAttribute("talk", talk);
			talkMessages = new ArrayList<TalkMessage>();

		} catch (TalkException e) {

			e.printStackTrace();
		}
		return talk;
	}

	/**
	 * activate listener chat
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void activate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ITalk talk = (ITalk) request.getSession().getAttribute("talk");

		if (talk == null) {

			talk = this.initTalkMembers(request);

		}

		TalkMessage talkMessage = (TalkMessage) request.getSession()
				.getAttribute("talkMessage");

		if (talkMessage == null) {

			talkMessage = new TalkMessage(talk);
			request.getSession().setAttribute("talkMessage", talkMessage);
		}

		List<String> valListner = new ArrayList<String>();
		valListner.add("succed");
		JSONArray jsonArray = JSONArray.fromObject(valListner);

		JSONObject obj = new JSONObject();
		obj.put("valListner", jsonArray);

		response.setHeader("X-JSON", obj.toJSONString());
		response.setHeader("cache-control", "no-cache");
		response.setContentType("text/html");

	}

	/**
	 * detect recieved message
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void receive(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ITalk talk = (ITalk) request.getSession().getAttribute("talk");

		if (talk == null) {

			talk = this.initTalkMembers(request);

		}

		TalkMessage talkMessage = (TalkMessage) request.getSession()
				.getAttribute("talkMessage");
		if (talkMessage == null) {

			talkMessage = new TalkMessage(talk);
			request.getSession().setAttribute("talkMessage", talkMessage);
		}

		Map<String, Boolean> newConversation = talkMessage.getNewConversation();
		List<TalkJsonMsg> lastConversation = new ArrayList<TalkJsonMsg>();
		for (Entry<String, Boolean> entry : newConversation.entrySet()) {
			String key = entry.getKey();
			if (entry.getValue() == true) {

				String msg = talkMessage.getConversation().get(key).toString();
				String[] tt = key.split("@");
				TalkJsonMsg talkjson = new TalkJsonMsg(msg, tt[0], key);
				lastConversation.add(talkjson);
				newConversation.put(key, false);

			}
		}
		talkMessage.setNewConversation(newConversation);
		request.getSession().setAttribute("talkMessage", talkMessage);

		JSONArray jsonArray = JSONArray.fromObject(lastConversation);

		JSONObject obj = new JSONObject();
		obj.put("lastConversation", jsonArray);

		response.setHeader("X-JSON", obj.toJSONString());
		response.setHeader("cache-control", "no-cache");
		response.setContentType("text/html");

		// return mapping.findForward("success");

	}

	/**
	 * send message
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ITalk talk = (ITalk) request.getSession().getAttribute("talk");

		if (talk == null) {

			talk = this.initTalkMembers(request);

		}

		String friend = request.getParameter("toFriend");
		friend = friend + "@" + xmppServerDomain;
		String msg = request.getParameter("msg");

		TalkMessage talkMessage = (TalkMessage) request.getSession()
				.getAttribute("talkMessage");
		if (talkMessage == null) {

			talkMessage = new TalkMessage(talk);
			request.getSession().setAttribute("talkMessage", talkMessage);
		}
		talkMessage = (TalkMessage) request.getSession().getAttribute(
				"talkMessage");
		Map<String, Chat> sessionTalks = talkMessage.getSessionTalks();

		Chat chat = sessionTalks.get(friend);

		if (chat == null) {

			try {
				chat = talk.createConversation(friend);
				sessionTalks.put(friend, chat);
				talkMessage.setSessionTalks(sessionTalks);

			} catch (TalkException e) {

				e.printStackTrace();
			}

		}
		StringBuilder dd = null;
		try {
			talk.sendMessage(msg, friend, chat);
			dd = talkMessage.getConversation().get(friend);
			if (dd == null) {

				dd = new StringBuilder();
				dd.append("</br><p style=\"margin:-7px -7px -7px -7px;\">me :"
						+ msg + "</p></br>");
				talkMessage.getConversation().put(friend, dd);
				request.getSession().setAttribute("talkMessage", talkMessage);
			} else {

				dd.append("</br><p style=\"margin:-7px -7px -7px -7px;\">me :"
						+ msg + "</p></br>");
				talkMessage.getConversation().put(friend, dd);
				request.getSession().setAttribute("talkMessage", talkMessage);
			}

		} catch (TalkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<TalkJsonMsg> lastConversation = new ArrayList<TalkJsonMsg>();
		String[] tt = friend.split("@");
		TalkJsonMsg talkjson = new TalkJsonMsg(dd.toString(), tt[0], friend);
		lastConversation.add(talkjson);
		JSONArray jsonArray = JSONArray.fromObject(lastConversation);

		JSONObject obj = new JSONObject();
		obj.put("lastConversation", jsonArray);
		response.setHeader("cache-control", "no-cache");
		response.setHeader("X-JSON", obj.toJSONString());
		response.setContentType("text/html");

	}

}
