package fr.univartois.ili.fsnet.auth;

import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 * 
 * This Servlet is called when a authenticated user wants to log out
 * 
 * @author Mathieu Boniface < mat.boniface {At} gmail.com >
 */
public class Logout extends HttpServlet {

	/**
	 * Name of the cookie containing the login
	 */
	public static final String LOGIN_COOKIE = "login";
	/**
	 * Name of the cookie containing the password
	 */
	public static final String PSSWD_COOKIE = "password";

	/**
	 * remove the cookies from the user's browser in order to disable the
	 * automatic login
	 * 
	 * @param resp
	 *            the {@link HttpServletResponse}
	 */
	public void removeCookies(HttpServletResponse resp) {
		Cookie logCookie = new Cookie(LOGIN_COOKIE, "");
		Cookie passwdCookie = new Cookie(PSSWD_COOKIE, "");

		logCookie.setMaxAge(0);
		passwdCookie.setMaxAge(0);

		resp.addCookie(logCookie);
		resp.addCookie(passwdCookie);
	}

	/**
	 * Invalidate the current session of the authenticated user and redirect him
	 * to the login page
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		updateUser(req.getSession());
		if (session != null) {
			session.invalidate();
		}
		removeCookies(resp);
		
		RequestDispatcher dispatcher = req
				.getRequestDispatcher(Authenticate.WELCOME_NON_AUTHENTICATED_PAGE);
		dispatcher.forward(req, resp);
	}

	private void updateUser(HttpSession session) {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user;
		user = em.find(SocialEntity.class, session.getAttribute(Authenticate.AUTHENTICATED_USER));
		
		user.setLastConnection(new Date());
		em.merge(user);
		em.getTransaction().commit();
		
	}

	/**
	 * Delegated to doGet
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
