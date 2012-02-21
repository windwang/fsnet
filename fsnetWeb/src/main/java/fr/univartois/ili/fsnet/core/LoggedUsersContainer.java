package fr.univartois.ili.fsnet.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * This singleton stores the logged in users. The unique instance is stored in
 * application scope in order to retrieve data in the presentation layer
 * 
 * @author Mathieu Boniface < mat.boniface {At} gmail.com >
 */
public final class LoggedUsersContainer {
	
	private static final Logger LOGGER = Logger.getLogger(LogoutListener.class.getSimpleName());

	/*
	 * Sole instance of this class
	 */
	private static final LoggedUsersContainer instance = new LoggedUsersContainer();

	/*
	 * private constructor of this class
	 */
	private LoggedUsersContainer() {
	}

	/*
	 * @return the sole instance of this class
	 */
	public static final LoggedUsersContainer getInstance() {
		return instance;
	}

	/*
	 * Each logged in user is stored with this id as the key of this map and his
	 * name as a value
	 */
	private final Map<Integer, String> loggedUsers = new ConcurrentHashMap<Integer, String>();

	/**
	 * Notify that an user is connected
	 * 
	 * @param userId
	 *            an user id.
	 * @param name
	 *            the name of this user.
	 */
	public void addUser(Integer userId, String name) {
		if (!loggedUsers.containsKey(userId)) {
			loggedUsers.put(userId, name);
		}
	}

	/**
	 * Notify that an user is disconnected
	 * 
	 * @param userId
	 */
	public void removeUser(Integer userId) {
		if (userId == null) {
			LOGGER.warning("The userId should not be null");
			return;
		}
		loggedUsers.remove(userId);
	}

	/**
	 * 
	 * @param userId
	 * @return true if ths user specified by <i>userId</i> is logged in
	 */
	public boolean isUserLogged(Integer userId) {
		return loggedUsers.containsKey(userId);
	}

	/**
	 * Return the Map of logged in users
	 * 
	 */
	public Map<Integer, String> getUsers() {
		return loggedUsers;
	}

}
