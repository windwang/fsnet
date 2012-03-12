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

import fr.univartois.ili.fsnet.actions.ManageAnnounces;
import fr.univartois.ili.fsnet.actions.ManageConsultations;
import fr.univartois.ili.fsnet.actions.ManageContacts;
import fr.univartois.ili.fsnet.actions.ManageEvents;
import fr.univartois.ili.fsnet.actions.ManagePrivateMessages;
import fr.univartois.ili.fsnet.actions.ManageVisits;
import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.security.Encryption;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.core.LoggedUsersContainer;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;

/**
 * This class represents a servlet that is used in order to authenticate members
 * 
 * @author Mathieu Boniface < mat.boniface {At} gmail.com >
 */
public class Authenticate extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Welcome page path when the user is authenticated
	 */
	private static final String WELCOME_AUTHENTICATED_PAGE = "Home.do";
	/**
	 * Welcome page path when the user is NOT authenticated
	 */
	public static final String WELCOME_NON_AUTHENTICATED_PAGE = "/WEB-INF/jsp/login.jsp";
	/**
	 * Authenticated user key in session scope
	 */
	public static final String AUTHENTICATED_USER = "userId";
	/**
	 * Name of the cookie containing the login
	 */
	public static final String LOGIN_COOKIE = "login";
	/**
	 * Name of the cookie containing the password
	 */
	public static final String PSSWD_COOKIE = "password";

	/**
	 * Set the cookies for automatic login
	 * 
	 * @param rep
	 *            the {@link HttpServletResponse}
	 * @param login
	 *            the login email
	 * @param passwd
	 *            the password
	 */
	private void setCookies(HttpServletResponse rep, String login, String passwd) {
		Cookie logCookie = new Cookie(LOGIN_COOKIE, login);
		Cookie passwdCookie = new Cookie(PSSWD_COOKIE, passwd);

		logCookie.setMaxAge(Integer.MAX_VALUE);
		passwdCookie.setMaxAge(Integer.MAX_VALUE);

		rep.addCookie(logCookie);
		rep.addCookie(passwdCookie);
	}

	/**
	 * This method is called when an user user tries to sign in
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean authenticated = false;
		String memberMail = req.getParameter("memberMail");
		String memberPass = req.getParameter("memberPass");
		
		
		if (memberMail == null && memberPass == null) {
			if (req.getCookies() != null){

				for (Cookie c : req.getCookies()) {
					if (c.getName().equals(LOGIN_COOKIE)) {
						memberMail = c.getValue();
						if (memberPass != null){
							break;
						}
					} else if (c.getName().equals(PSSWD_COOKIE)) {
						memberPass = c.getValue();
						if (memberMail != null){
							break;
						}
					}
				}
			}
		}

		EntityManager em = PersistenceProvider.createEntityManager();
		;
		SocialEntity es = null;
		if (memberMail != null && memberPass != null) {
			memberMail=memberMail.toLowerCase();
			SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
			es = socialEntityFacade.findByEmail(memberMail);
			if (es != null
					&& Encryption.testPassword(memberPass, es.getPassword())) {
				authenticated = true;
				req.getSession(true).setAttribute(AUTHENTICATED_USER,
						es.getId());
				req.getSession(true).setAttribute("userName", es.getName());
				req.getSession(true).setAttribute("userFirstName", es.getFirstName());

				SocialEntity user = em.find(SocialEntity.class, es.getId());
				if(user.getLastConnection() != null){
					//Last connection in session before a new session
					req.getSession(true).setAttribute("LastConnection",user.getLastConnection());
				}else{
					req.getSession(true).setAttribute("LastConnection",new Date());
				}
				user.setLastConnection(new Date());
				ManagePrivateMessages.refreshNumNewMessages(req, em);
				ManageContacts.refreshNumNewContacts(req, em);
				ManageVisits.refreshNumNewVisits(req, em);
				ManageAnnounces.refreshNumNewAnnonces(req, em);
				ManageEvents.refreshNumNewEvents(req, em);
				ManageConsultations.refreshNumNewConsultations(req, em);
			} else {
				req.setAttribute("loginMessage", "login.error");
			}
		}

		if (authenticated) {
			// the user is now authenticated
			String remember = req.getParameter("remember");
			if (remember != null && remember.equals("on")){
				setCookies(resp, memberMail, memberPass);
			}

			HttpSession session = req.getSession(true);
			String lastRequestedURL = (String) session
					.getAttribute("requestedURL");
			if (lastRequestedURL != null) {
				resp.sendRedirect(lastRequestedURL);
				session.removeAttribute("requestedURL");
			} else {
				resp.sendRedirect(WELCOME_AUTHENTICATED_PAGE);
			}
			
			SocialEntity user;
			user = em.find(SocialEntity.class, es.getId());
			

			String userName = user.getFirstName() + " " + user.getName();
			LoggedUsersContainer.getInstance().addUser(user.getId(), userName);

			SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
			String groupTree = socialGroupFacade.treeParentName(user);
			req.getSession().setAttribute("groupTree", groupTree);
			req.getSession().setAttribute("hisGroup",
					UserUtils.getHisGroup(req));
			req.getSession().setAttribute("isMasterGroup",
					socialGroupFacade.isMasterGroup(user));
			req.getSession().setAttribute("isGroupResponsible",
					socialGroupFacade.isGroupResponsible(user));
			req.getSession().setAttribute("currentUserId", user.getId());

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
