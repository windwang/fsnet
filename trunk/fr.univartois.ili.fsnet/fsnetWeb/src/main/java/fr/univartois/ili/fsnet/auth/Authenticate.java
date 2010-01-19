package fr.univartois.ili.fsnet.auth;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

	private static final String WELCOME_AUTHENTICATED_PAGE = "/Events.do";
	
	public static final String WELCOME_NON_AUTHENTICATED_PAGE = "/WEB-INF/jsp/login.jsp";

	public static final String AUTHENTICATED_USER = "authenticated_user";
	
	public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("fsnetjpa");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String memberMail = req.getParameter("memberMail");
		String memberPass = req.getParameter("memberPass");
		
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("Select es from EntiteSociale es where es.email = :memberMail");
		query.setParameter("memberMail", memberMail);
		EntiteSociale es = (EntiteSociale) query.getSingleResult();
		
		String redirectTo = WELCOME_NON_AUTHENTICATED_PAGE;
		// TODO activer la vérification du mot de passe
		if (es != null) {
			redirectTo = WELCOME_AUTHENTICATED_PAGE;
			req.getSession(true).setAttribute(AUTHENTICATED_USER, es);
		}
		RequestDispatcher dispatcher = req.getRequestDispatcher(redirectTo);
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
