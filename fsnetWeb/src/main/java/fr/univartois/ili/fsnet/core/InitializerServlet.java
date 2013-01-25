package fr.univartois.ili.fsnet.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This servlet is instantiated by the container at the load of the application.
 * It's used to put the LoggedUsersContainer singleton in application scope as
 * an attribute
 * 
 * @author Mathieu Boniface < mat.boniface {At} gmail.com >
 */

@WebListener
public class InitializerServlet implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Put the LoggedUsersContainer singleton in application scope as an
	 * attribute
	 */
	@Override
	public void contextInitialized(ServletContextEvent s) {
		s.getServletContext().setAttribute("loggedUsers",
				LoggedUsersContainer.getInstance());
	}

}
