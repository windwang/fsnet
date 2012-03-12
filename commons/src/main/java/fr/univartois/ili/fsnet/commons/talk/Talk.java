package fr.univartois.ili.fsnet.commons.talk;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smack.packet.Presence.Type;

import fr.univartois.ili.fsnet.commons.utils.TalkException;

/**
 * @see {@link ITalk}
 * 
 * @author habib
 * 
 */
public class Talk implements ITalk,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private XMPPConnection connection;

	private ConnectionConfiguration config = null;
	private AccountManager accountManager = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.univartois.ili.fsnet.commons.talk.ITalk#getConnection()
	 */
	@Override
	public XMPPConnection getConnection() {
		return connection;
	}

	/**
	 * @param connection
	 */
	public void setConnection(XMPPConnection connection) {
		this.connection = connection;

	}

	/**
	 * 
	 */
	public Talk() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.commons.talk.ITalk#createAccount(java.lang.String
	 * , java.lang.String, java.util.Map)
	 */
	@Override
	public boolean createAccount(String userName, String password,
			Map<String, String> map) {
		{
			if (!connection.isConnected()){
				try {
					connection.connect();
				} catch (XMPPException e3) {

					Logger.getAnonymousLogger().log(Level.SEVERE, "", e3);
				}
			}
			
			try {
				if (map == null){
					map = new HashMap<String, String>();
				}
				accountManager.createAccount(userName, password, map);

				connection.disconnect();
				// Thread.sleep(6000);
				connection.connect();
				connection.login(userName, password);

				return true;
			} catch (XMPPException e2) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e2);
			}
			return false;

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.commons.talk.ITalk#sendPresence(java.lang.String)
	 */
	@Override
	public void sendPresence(String status) {

		Presence presence = new Presence(Type.available, status, 10,
				Mode.available);
		if (status.equals("offline")) {
			presence.setType(Type.unavailable);
			presence.setMode(Mode.away);

		}
		connection.sendPacket(presence);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.commons.talk.ITalk#subscribe(java.lang.String)
	 */
	@Override
	public void subscribe(String user) {

		Presence presence = new Presence(Presence.Type.subscribe, "available",
				10, Mode.available);
		presence.setTo(user);
		connection.sendPacket(presence);

		presence = new Presence(Presence.Type.subscribed);
		presence.setTo(user);
		connection.sendPacket(presence);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.commons.talk.ITalk#unSubscribe(java.lang.String)
	 */
	@Override
	public void unSubscribe(String user) {

		Presence presence = new Presence(Presence.Type.unsubscribe,
				"available", 10, Mode.available);
		presence.setTo(user);
		connection.sendPacket(presence);

		presence = new Presence(Presence.Type.unsubscribed);
		presence.setTo(user);
		connection.sendPacket(presence);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.commons.talk.ITalk#initConnexion(java.lang.String
	 * , int, java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public void initConnexion(String xmppServer, int port, String login,
			String pssword, Map<String, String> map) throws TalkException {
		// connexion(XMPPServer, port);
		// login(login, pssword);
		config = new ConnectionConfiguration(xmppServer, port);

		connection = new XMPPConnection(config);
		try {
			if (!connection.isConnected()) {
				connection.connect();
			}

			accountManager = connection.getAccountManager();
			connection.login(login, pssword);

		} catch (XMPPException e) {
			if ((e.getLocalizedMessage().contains("authentication failed") || e
					.getLocalizedMessage().contains("SASL authentication"))
			&& accountManager.supportsAccountCreation()) {
				createAccount(login, pssword, map);

			} else {
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);

			}
		}

	}

	/**
	 * @param XMPPServer
	 * @param port
	 * @throws TalkException
	 *
	 *
	 * This method is never used
	 *
	private void connexion(String XMPPServer, int port) throws TalkException {
		config = new ConnectionConfiguration(XMPPServer, port);

		connection = new XMPPConnection(config);
		try {
			connection.connect();
			accountManager = connection.getAccountManager();
		} catch (XMPPException e) {
			throw new TalkException("Problem Jabber connexion ", e);
		}
	}
	*/
	/**
	 * @param login
	 * @param pssword
	 * @throws TalkException
	 *
	 * This method is never used
	 *
	private void login(String login, String pssword) throws TalkException {

		try {
			connection.login(login, pssword);
		} catch (XMPPException e) {
			throw new TalkException("Problem login  with  username", e);
		}

	}
	*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.univartois.ili.fsnet.commons.talk.ITalk#stop()
	 */
	@Override
	public void stop() throws TalkException {
		connection.disconnect();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.commons.talk.ITalk#sendMessage(java.lang.String,
	 * java.lang.String, org.jivesoftware.smack.Chat)
	 */
	@Override
	public void sendMessage(String msg, String friendPseudo, Chat chat)
			throws TalkException {
		try {
			chat.sendMessage(msg);

		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.commons.talk.ITalk#createConversation(java.lang
	 * .String)
	 */
	@Override
	public Chat createConversation(String pseudoFriend) throws TalkException {
		try {

			Chat chat = connection.getChatManager().createChat(pseudoFriend,
					new MessageListener() {
						@Override
						public void processMessage(Chat chat, Message message) {

						}
					});

			return chat;

		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}
		return null;
	}

	public ConnectionConfiguration getConfig() {
		return config;
	}

	public void setConfig(ConnectionConfiguration config) {
		this.config = config;
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

}
