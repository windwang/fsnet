package fr.univartois.ili.fsnet.commons.utils;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import fr.univartois.ili.fsnet.commons.talk.ITalk;

/**
 * represent a frame talk message
 * 
 * @author habib
 * 
 */
public class TalkMessage implements ChatManagerListener, MessageListener {

	private Map<String, StringBuilder> conversation;
	private Map<String, Chat> sessionTalks;
	private String ownerSession;
	private ITalk talk;
	private Map<String, Boolean> newConversation;

	/**
	 * @param talk
	 */
	public TalkMessage(ITalk talk) {
		this.talk = talk;
		talk.getConnection().getChatManager().addChatListener(this);
	}

	/**
	 * @return
	 */
	public Map<String, StringBuilder> getConversation() {
		if (conversation == null) {
			conversation = new HashMap<String, StringBuilder>();
		}
		return conversation;
	}

	/**
	 * @param conversation
	 */
	public void setConversation(Map<String, StringBuilder> conversation) {
		this.conversation = conversation;
	}

	/**
	 * @return
	 */
	public Map<String, Chat> getSessionTalks() {
		if (sessionTalks == null) {
			sessionTalks = new HashMap<String, Chat>();
		}
		// return conversation;
		return sessionTalks;
	}

	/**
	 * @param sessionTalks
	 */
	public void setSessionTalks(Map<String, Chat> sessionTalks) {
		this.sessionTalks = sessionTalks;
	}

	/**
	 * @return
	 */
	public String getOwnerSession() {
		return ownerSession;
	}

	/**
	 * @param ownerSession
	 */
	public void setOwnerSession(String ownerSession) {
		this.ownerSession = ownerSession;
	}

	/**
	 * @return
	 */
	public String getHtmlConversation() {
		return conversation.toString();

	}

	/**
	 * @return
	 */
	public Map<String, Boolean> getNewConversation() {
		if (newConversation == null) {
			newConversation = new HashMap<String, Boolean>();
		}
		return newConversation;
	}

	/**
	 * @param newConversation
	 */
	public void setNewConversation(Map<String, Boolean> newConversation) {
		this.newConversation = newConversation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jivesoftware.smack.MessageListener#processMessage(org.jivesoftware
	 * .smack.Chat, org.jivesoftware.smack.packet.Message)
	 */
	@Override
	public void processMessage(Chat chat, Message message) {

		// for (Entry<String, StringBuilder> entry :
		// getConversation().entrySet()) {
		// String key = entry.getKey();
		// System.out.println("friend :" + key);
		// System.out.println("msg :" + entry.getValue().toString());
		//
		// }
		//
		// System.out.println("afficher session");
		// for (Entry<String, Chat> entry : getSessionTalks().entrySet()) {
		// String key = entry.getKey();
		// System.out.println("friend :" + key);
		// System.out.println("chat :" + entry.getValue());
		//
		// }
		//
		// System.out.println("sender: " + chat.getParticipant()
		// + " -Received message: "
		// + (message != null ? message.getBody() : "NULL"));

		String[] particiant = chat.getParticipant().split("/");

		StringBuilder dd = getConversation().get(particiant[0]);
		if (dd == null) {

			dd = new StringBuilder();

		}
		String[] name = chat.getParticipant().split("@");
		dd.append("</br><p style=\"color: blue;margin:-7px -7px -7px -7px;\">"
				+ name[0] + " :" + message.getBody() + "</p>");
		getConversation().put(particiant[0], dd);
		getNewConversation().put(particiant[0], true);

		// this.setConversation(conversation);

		// verifier si la session existe
		Chat currentChat = getSessionTalks().get(particiant[0]);
		if (currentChat == null) {
			try {
				Chat chatt = talk.createConversation(particiant[0]);
				getSessionTalks().put(particiant[0], chatt);
			} catch (TalkException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jivesoftware.smack.ChatManagerListener#chatCreated(org.jivesoftware
	 * .smack.Chat, boolean)
	 */
	@Override
	public void chatCreated(Chat chat, boolean arg1) {
		chat.addMessageListener(this);

	}

}
