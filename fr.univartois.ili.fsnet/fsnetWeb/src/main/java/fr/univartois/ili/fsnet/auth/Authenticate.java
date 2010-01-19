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

public class Authenticate extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String WELCOME_AUTHENTICATED_PAGE = "Events.do";

	public static final String WELCOME_NON_AUTHENTICATED_PAGE = "/WEB-INF/jsp/login.jsp";

	public static final String AUTHENTICATED_USER = "authenticated_user";

	public static final EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("fsnetjpa");

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
				req.setAttribute("loginError", "login.error");
			}
			em.close();
		}

		if (authenticated) {
			resp.sendRedirect(WELCOME_AUTHENTICATED_PAGE);
		} else {
			RequestDispatcher dispatcher = req
					.getRequestDispatcher(WELCOME_NON_AUTHENTICATED_PAGE);
			dispatcher.forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
