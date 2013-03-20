package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.util.MessageResources;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.ChatState;
import org.jivesoftware.smackx.ChatStateManager;
import org.json.simple.JSONObject;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.talk.ITalk;
import fr.univartois.ili.fsnet.commons.talk.Talk;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.commons.utils.TalkException;
import fr.univartois.ili.fsnet.commons.utils.TalkJsonComposing;
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

	private final String xmppServer = PropsUtils.getProperty("xmpp.server"); // "localhost";
	private final int port = Integer.parseInt(PropsUtils
			.getProperty("xmpp.server.port"));// 5222;
	private final String xmppServerDomain = PropsUtils
			.getProperty("xmpp.domain"); // "master11";
	private final String password = PropsUtils.getProperty("xmpp.password");

	private static final String TALK_ATTRIBUTE_NAME = "talk"; 
	private static final String TALKMESSAGE_ATTRIBUTE_NAME = "talkMessage";
	
	/**
	 * @param request
	 * @return
	 */
	private ITalk initTalkMembers(HttpServletRequest request) {

		ITalk talk = new Talk();
		try {
			EntityManager em = PersistenceProvider.createEntityManager();
			SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
			String name = member.getName().replaceAll(" ", "").toLowerCase() 
					+ "_" + member.getId();
			String email = member.getEmail();

			Map<String, String> map = new HashMap<String, String>();
			map.put("email", email);
			map.put("name", name);
			talk.initConnexion(xmppServer, port, name, password, map);
			request.getSession().setAttribute(TALK_ATTRIBUTE_NAME, talk);

		} catch (TalkException e) {

			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
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

		ITalk talk = (ITalk) request.getSession().getAttribute(TALK_ATTRIBUTE_NAME);

		if (talk == null) {

			talk = this.initTalkMembers(request);

		}

		TalkMessage talkMessage = (TalkMessage) request.getSession()
				.getAttribute(TALKMESSAGE_ATTRIBUTE_NAME);

		if (talkMessage == null) {

			talkMessage = new TalkMessage(talk);
			request.getSession().setAttribute(TALKMESSAGE_ATTRIBUTE_NAME, talkMessage);
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

		ITalk talk = (ITalk) request.getSession().getAttribute(TALK_ATTRIBUTE_NAME);

		if (talk == null) {

			talk = this.initTalkMembers(request);

		}

		TalkMessage talkMessage = (TalkMessage) request.getSession()
				.getAttribute(TALKMESSAGE_ATTRIBUTE_NAME);
		if (talkMessage == null) {

			talkMessage = new TalkMessage(talk);
			request.getSession().setAttribute(TALKMESSAGE_ATTRIBUTE_NAME, talkMessage);
		}

		
		Map<String, Boolean> newConversation = talkMessage.getNewConversation();
		List<TalkJsonMsg> lastConversation = new ArrayList<TalkJsonMsg>();
		for (Entry<String, Boolean> entry : newConversation.entrySet()) {
			String key = entry.getKey();
			if (entry.getValue()) {
				String msg = talkMessage.getConversation().get(key).toString();
				String[] tt = key.split("@");
				TalkJsonMsg talkjson = new TalkJsonMsg(msg, tt[0], key);
				lastConversation.add(talkjson);
				newConversation.put(key, false);

			}
		}
		talkMessage.setNewConversation(newConversation);
		request.getSession().setAttribute(TALKMESSAGE_ATTRIBUTE_NAME, talkMessage);

		JSONArray jsonArray = JSONArray.fromObject(lastConversation);

		JSONObject obj = new JSONObject();
		obj.put("lastConversation", jsonArray);
		response.setHeader("cache-control", "no-cache");
		response.setContentType("application/json");
		
		obj.writeJSONString(response.getWriter());

	}
	
	/**
	 * Get session talks
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void getTalks(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		ITalk talk = (ITalk) request.getSession().getAttribute(TALK_ATTRIBUTE_NAME);

		if (talk == null) {
			talk = this.initTalkMembers(request);
		}
		
		TalkMessage talkMessage = (TalkMessage) request.getSession()
				.getAttribute(TALKMESSAGE_ATTRIBUTE_NAME);
		if (talkMessage == null) {

			talkMessage = new TalkMessage(talk);
			request.getSession().setAttribute(TALKMESSAGE_ATTRIBUTE_NAME, talkMessage);
		}
		
		Map<String,Chat> sessionTalks=talkMessage.getSessionTalks();
		Set<String> listSessions=sessionTalks.keySet();
		
		JSONArray jsonArray = JSONArray.fromObject(listSessions.toArray());

		JSONObject obj = new JSONObject();
		obj.put("sessionTalks", jsonArray);
		response.setHeader("cache-control", "no-cache");
		response.setContentType("application/json");
		
		obj.writeJSONString(response.getWriter());
	}
	
	/**
	 * Close a session talk
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void closeTalk(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		ITalk talk = (ITalk) request.getSession().getAttribute(TALK_ATTRIBUTE_NAME);

		if (talk == null) {
			talk = this.initTalkMembers(request);
		}
		
		TalkMessage talkMessage = (TalkMessage) request.getSession()
				.getAttribute(TALKMESSAGE_ATTRIBUTE_NAME);
		if (talkMessage == null) {

			talkMessage = new TalkMessage(talk);
			request.getSession().setAttribute(TALKMESSAGE_ATTRIBUTE_NAME, talkMessage);
		}
		
		String friend=request.getParameter("friend");
		
		Map<String,Chat> sessionTalks=talkMessage.getSessionTalks();
		
		sessionTalks.remove(friend+"@"+xmppServerDomain);
		
		response.setHeader("cache-control", "no-cache");
		response.setContentType("text/html");
	}
	
	/**
	 * Get session talk
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void getTalk(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		ITalk talk = (ITalk) request.getSession().getAttribute(TALK_ATTRIBUTE_NAME);
		String friend = request.getParameter("friend");
		if (talk == null) {
			talk = this.initTalkMembers(request);
		}
		
		TalkMessage talkMessage = (TalkMessage) request.getSession()
				.getAttribute(TALKMESSAGE_ATTRIBUTE_NAME);
		if (talkMessage == null) {

			talkMessage = new TalkMessage(talk);
			request.getSession().setAttribute(TALKMESSAGE_ATTRIBUTE_NAME, talkMessage);
		}
		
		StringBuilder conv = talkMessage.getConversation().get(friend+"@"+xmppServerDomain);		
		
		
		JSONObject obj = new JSONObject();
		obj.put("conversation", conv.toString());
		obj.put("friend", friend);
		response.setHeader("cache-control", "no-cache");
		response.setContentType("application/json");
		
		obj.writeJSONString(response.getWriter());
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
	 * @throws XMPPException 
	 */
	public void send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, XMPPException {
		ITalk talk = (ITalk) request.getSession().getAttribute(TALK_ATTRIBUTE_NAME);

		if (talk == null) {
			talk = this.initTalkMembers(request);
		}

		String friend = request.getParameter("toFriend");
		friend = friend + "@" + xmppServerDomain;
		String msg = request.getParameter("msg");
		/* Permet d'éviter l'injection de code html ou javascript */
		String escapedMsg = StringUtils.replaceEach(msg, new String[]{"&", "\"", "<", ">"}, new String[]{"&amp;", "&quot;", "&lt;", "&gt;"});
		TalkMessage talkMessage = (TalkMessage) request.getSession()
				.getAttribute(TALKMESSAGE_ATTRIBUTE_NAME);
		if (talkMessage == null) {

			talkMessage = new TalkMessage(talk);
			request.getSession().setAttribute(TALKMESSAGE_ATTRIBUTE_NAME, talkMessage);
		}

		Map<String, Chat> sessionTalks = talkMessage.getSessionTalks();

		Chat chat = sessionTalks.get(friend);

		if (chat == null) {

			try {
				chat = talk.createConversation(friend);
				sessionTalks.put(friend, chat);
				talkMessage.setSessionTalks(sessionTalks);

			} catch (TalkException e) {

				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
			}

		}
		StringBuilder dd = null;
		
		MessageResources bundle = MessageResources.getMessageResources("FSneti18n");
		String formattedMsg = "</br><p style=\"margin:-7px -7px -7px -7px;\">"+bundle.getMessage(request.getLocale(),"chat.me")+" :"
				+ escapedMsg + "</p></br>"; 
		try {
			talk.sendMessage(escapedMsg, friend, chat);
			dd = talkMessage.getConversation().get(friend);
			if (dd == null) {

				dd = new StringBuilder();
				dd.append(formattedMsg);
				talkMessage.getConversation().put(friend, dd);
				request.getSession().setAttribute(TALKMESSAGE_ATTRIBUTE_NAME, talkMessage);
			} else {

				dd.append(formattedMsg);
				talkMessage.getConversation().put(friend, dd);
				request.getSession().setAttribute(TALKMESSAGE_ATTRIBUTE_NAME, talkMessage);
			}

		} catch (TalkException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}

		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		String name_user_connected = member.getName().replaceAll(" ", "").toLowerCase() 
				+ "_" + member.getId();
		
		/* Changement de l'état lorsque l'on envoie un message car l'utilisateur a fini d'écrire */
		
		ChatStateManager.getInstance(talk.getConnection()).setCurrentState(ChatState.paused, chat);
		
		List<TalkJsonMsg> lastConversation = new ArrayList<TalkJsonMsg>();
		String[] tt = friend.split("@");
		TalkJsonMsg talkjson = new TalkJsonMsg(formattedMsg, tt[0], friend);
		lastConversation.add(talkjson);
		JSONArray jsonArray = JSONArray.fromObject(lastConversation);

		JSONObject obj = new JSONObject();
		obj.put("lastConversation", jsonArray);
		response.setHeader("cache-control", "no-cache");
		response.setContentType("application/json");
		
		obj.writeJSONString(response.getWriter());

	}
	
	/**
	 * composing message
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 * @throws XMPPException 
	 */
	public void composing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, XMPPException {
		String friend = request.getParameter("toFriend");
		
		ITalk talk = (ITalk) request.getSession().getAttribute(TALK_ATTRIBUTE_NAME);
		TalkMessage talkMessage = (TalkMessage) request.getSession()
				.getAttribute(TALKMESSAGE_ATTRIBUTE_NAME);
		
		if (talkMessage == null) {

			talkMessage = new TalkMessage(talk);
			request.getSession().setAttribute(TALKMESSAGE_ATTRIBUTE_NAME, talkMessage);
		}
		
		friend = friend + "@" + xmppServerDomain;
		Map<String, Chat> sessionTalks = talkMessage.getSessionTalks();
		Chat chat = sessionTalks.get(friend);
		
		if (chat == null) {

			try {
				chat = talk.createConversation(friend);
				sessionTalks.put(friend, chat);
				talkMessage.setSessionTalks(sessionTalks);

			} catch (TalkException e) {

				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
			}

		}
		
		ChatStateManager.getInstance(talk.getConnection()).setCurrentState(ChatState.composing, chat);
	}
	
	public void isComposing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
			
		String friend = request.getParameter("toFriend");
		friend = friend + "@" + xmppServerDomain;
		
		ITalk talk = (ITalk) request.getSession().getAttribute(TALK_ATTRIBUTE_NAME);	
		
		TalkMessage talkMessage = (TalkMessage) request.getSession()
				.getAttribute(TALKMESSAGE_ATTRIBUTE_NAME);
		
		if (talkMessage == null) {

			talkMessage = new TalkMessage(talk);
			request.getSession().setAttribute(TALKMESSAGE_ATTRIBUTE_NAME, talkMessage);
		}
		List<TalkJsonComposing> ljsc = new ArrayList<>();
		for(Map.Entry<String, Boolean> e:talkMessage.getIsComposing().entrySet()){
			TalkJsonComposing  tjc = new TalkJsonComposing(e.getKey(), e.getValue());
			ljsc.add(tjc);
		}

		JSONObject obj = new JSONObject();
		obj.put("isComposing",JSONArray.fromObject(ljsc));
		
		response.setHeader("cache-control", "no-cache");
		response.setContentType("application/json");
		
		obj.writeJSONString(response.getWriter());
		
	}
	
	public void notComposing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, XMPPException {
		
		String friend = request.getParameter("toFriend");
		
		ITalk talk = (ITalk) request.getSession().getAttribute(TALK_ATTRIBUTE_NAME);
		TalkMessage talkMessage = (TalkMessage) request.getSession()
				.getAttribute(TALKMESSAGE_ATTRIBUTE_NAME);
		
		if (talkMessage == null) {

			talkMessage = new TalkMessage(talk);
			request.getSession().setAttribute(TALKMESSAGE_ATTRIBUTE_NAME, talkMessage);
		}
		
		friend = friend + "@" + xmppServerDomain;
		Map<String, Chat> sessionTalks = talkMessage.getSessionTalks();
		Chat chat = sessionTalks.get(friend);
		
		if (chat == null) {

			try {
				chat = talk.createConversation(friend);
				sessionTalks.put(friend, chat);
				talkMessage.setSessionTalks(sessionTalks);

			} catch (TalkException e) {

				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
			}

		}
		
		ChatStateManager.getInstance(talk.getConnection()).setCurrentState(ChatState.paused, chat);
	}

}
