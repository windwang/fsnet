package fr.univartois.ili.fsnet.core;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import fr.univartois.ili.fsnet.auth.Authenticate;

/**
 * This class listen the HttpSession in order to notify to LoggedUsersContainers
 * singleton which user is logged out
 * 
 * @author Mathieu Boniface < mat.boniface {At} gmail.com >
 */
public class LogoutListener implements HttpSessionListener {

	/**
	 * non used method
	 */
	@Override
	public void sessionCreated(HttpSessionEvent se) {}

	/**
	 * notify the LoggedUsersContainers singleton which user is logged out 
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		Integer userId = (Integer) se.getSession().getAttribute(
				Authenticate.AUTHENTICATED_USER);
		LoggedUsersContainer.getInstance().removeUser(userId);
	}

}
