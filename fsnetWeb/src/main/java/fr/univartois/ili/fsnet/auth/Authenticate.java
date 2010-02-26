package fr.univartois.ili.fsnet.auth;

import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.commons.security.Encryption;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.core.LoggedUsersContainer;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade;

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
	private static final String WELCOME_AUTHENTICATED_PAGE = "OnAuthenticationSuccess.do";
	/**
	 * Welcome page path when the user is NOT authenticated
	 */
	public static final String WELCOME_NON_AUTHENTICATED_PAGE = "/WEB-INF/jsp/login.jsp";
	/**
	 * Authenticated user key in session scope
	 */
	public static final String AUTHENTICATED_USER = "userId";

	/**
	 * This method is called when an user user tries to sign in
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean authenticated = false;
		String memberMail = req.getParameter("memberMail");
		String memberPass = req.getParameter("memberPass");
		EntityManager em = PersistenceProvider.createEntityManager();;
		SocialEntity es = null;
		if (memberMail != null && memberPass != null) {
			SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
			es = socialEntityFacade.findByEmail(memberMail);
			if (es != null
					&& Encryption.testPassword(memberPass, es.getPassword())) {
				authenticated = true;
				req.getSession(true).setAttribute(AUTHENTICATED_USER,
						es.getId());
			} else {
				req.setAttribute("loginMessage", "login.error");
			}
		}

		if (authenticated) {
			// the user is now authenticated
			resp.sendRedirect(WELCOME_AUTHENTICATED_PAGE);
			em.getTransaction().begin();
			SocialEntity user;
			user = em.find(SocialEntity.class, es.getId());
			user.setLastConnection(new Date());
			em.merge(user);
			em.getTransaction().commit();
			
			String userName = user.getFirstName() + " " + user.getName();
			LoggedUsersContainer.getInstance().addUser(user.getId(), userName);
		} else {
			// the user is not authenticated
			RequestDispatcher dispatcher = req
					.getRequestDispatcher(WELCOME_NON_AUTHENTICATED_PAGE);
			dispatcher.forward(req, resp);
		}
		em.close();
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
