package fr.univartois.ili.fsnet.auth;

import java.io.IOException;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.EntiteSociale;

/**
 * This class represents a servlet that is used in order to authenticate members
 * 
 * @author Mathieu Boniface < mat.boniface {At} gmail.com >
 */
public class Authenticate extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Welcome page path when the user is authenticated
	 */
	private static final String WELCOME_AUTHENTICATED_PAGE = "DisplayEvents.do";

	/**
	 * Welcome page path when the user is NOT authenticated
	 */
	public static final String WELCOME_NON_AUTHENTICATED_PAGE = "/WEB-INF/jsp/login.jsp";

	/**
	 * Authenticated user key in session scope
	 */
	public static final String AUTHENTICATED_USER = "user";

	public static final EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("fsnetjpa");

	/**
	 * This method is called when an user user tries to sign in
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean authenticated = false;
		String memberMail = req.getParameter("memberMail");
		String memberPass = req.getParameter("memberPass");

		if (memberMail != null && memberPass != null) {
			EntityManager em = emf.createEntityManager();
			Query query = em
					.createQuery("Select es from EntiteSociale es where es.email = :memberMail");
			query.setParameter("memberMail", memberMail);

			try {
				EntiteSociale es = (EntiteSociale) query.getSingleResult();
				// TODO activer la vérification du mot de passe
				if (es != null) {
					// Member found, so he's authenticated
					authenticated = true;
					req.getSession(true).setAttribute(AUTHENTICATED_USER, es);
				}
			} catch (NoResultException e) {
				Logger.getAnonymousLogger()
						.fine("Member authentication failed");
				Logger.getAnonymousLogger().fine("memberMail : " + memberMail);

				// throw an error message to the request
				req.setAttribute("loginError", "login.error");
			}
			em.close();
		}

		if (authenticated) {
			// the user is now authenticated
			resp.sendRedirect(WELCOME_AUTHENTICATED_PAGE);
		} else {
			// the user is not authenticated
			RequestDispatcher dispatcher = req
					.getRequestDispatcher(WELCOME_NON_AUTHENTICATED_PAGE);
			dispatcher.forward(req, resp);
		}
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
