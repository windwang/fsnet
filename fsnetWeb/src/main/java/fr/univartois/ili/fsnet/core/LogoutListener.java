package fr.univartois.ili.fsnet.core;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import fr.univartois.ili.fsnet.auth.Authenticate;
import fr.univartois.ili.fsnet.auth.Logout;

/**
 * This class listen the HttpSession in order to notify to LoggedUsersContainers
 * singleton which user is logged out
 * 
 * @author Mathieu Boniface < mat.boniface {At} gmail.com >
 */
@WebListener
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
		Logout.updateUser(se.getSession());
		LoggedUsersContainer.getInstance().removeUser(userId);
		
	}

}
