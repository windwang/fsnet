package fr.univartois.ili.fsnet.commons.talk;

import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;

import fr.univartois.ili.fsnet.commons.utils.TalkException;

/**
 * manage user talk
 * 
 * @author habib
 * 
 */
public interface ITalk {

	/**
	 * create connexion with jabber server authenticate user and create account
	 * if not exist
	 * 
	 * @param XMPPServer
	 *            Ip adress
	 * @param port
	 *            port xmpp chat
	 * @param login
	 *            login .
	 * @param pssword
	 *            password .
	 * @param map
	 * @throws TalkException
	 */
	public void initConnexion(String XMPPServer, int port, String login,
			String pssword, Map<String, String> map) throws TalkException;

	/**
	 * stop jabber connextion.
	 */

	public void stop() throws TalkException;

	/**
	 * sent message to friend
	 * 
	 * @param msg
	 *            message to sent
	 * @param friendPseudo
	 */
	public void sendMessage(String msg, String friendPseudo, Chat chat)
			throws TalkException;

	public XMPPConnection getConnection();

	/**
	 * create conversation
	 * 
	 * @param pseudoFriend
	 * @throws TalkException
	 */
	public Chat createConversation(String pseudoFriend) throws TalkException;

	/**
	 * create xmpp account for user
	 * 
	 * @param userName
	 * @param password
	 * @param map
	 *            param (mail , name ...)
	 * @return
	 */
	public boolean createAccount(String userName, String password,
			Map<String, String> map);

	/**
	 * send presence for chat
	 * 
	 * @param status
	 */
	public void sendPresence(String status);

	public void subscribe(String user);

	public void unSubscribe(String user);

}
