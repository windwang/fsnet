package fr.univartois.ili.fsnet.core;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * This servlet is instantiated by the container at the load of the application.
 * It's used to put the LoggedUsersContainer singleton in application scope as
 * an attribute
 * 
 * @author Mathieu Boniface < mat.boniface {At} gmail.com >
 */
public class InitializerServlet extends HttpServlet {

	/**
	 * Put the LoggedUsersContainer singleton in application scope as an
	 * attribute
	 */
	@Override
	public void init() throws ServletException {
		getServletContext().setAttribute("loggedUsers",
				LoggedUsersContainer.getInstance());
	}

}
