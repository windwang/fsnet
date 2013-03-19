package fr.univartois.ili.fsnet.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionSupport;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.ProfileVisite;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.ProfileVisiteFacade;

/**
 * @author FSNet
 * 
 */
public class ManageVisits extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2048363284550003091L;

	/**
	 * @param mapping
	 * @param request
	 * @param response
	 * @param em
	 * @param authenticatedUser
	 */
	public void lastVisitsSinceLastConnection(HttpServletRequest request, HttpServletResponse response,
			EntityManager em, SocialEntity authenticatedUser) {
		// recovery of visitors
		ProfileVisiteFacade pvf = new ProfileVisiteFacade(em);
		Date lastConnectionSession = (Date) request.getSession().getAttribute(
				"LastConnection");
		List<ProfileVisite> lastVisitors = pvf
				.getLastVisitorSinceLastConnection(authenticatedUser);

		// recovery of visitors since the last connection
		List<ProfileVisite> newLastvisitors = new ArrayList<ProfileVisite>();

		// recovery of visitors before the last connection
		List<ProfileVisite> lastVisitorsBeforeLastConnection = new ArrayList<ProfileVisite>();
		for(int i=0;i<lastVisitors.size();i++){
			if(lastVisitors.get(i).getLastVisite().after(lastConnectionSession)){
				newLastvisitors.add(lastVisitors.get(i));
			} else {
				lastVisitorsBeforeLastConnection.add(lastVisitors.get(i));
			}
		}
		// paging list of recent visitors since my last connection
		request.setAttribute("lastVisitors", newLastvisitors);

		// paging list of old visitors before my last connection
		request.setAttribute("beforeLastVisitors",
				lastVisitorsBeforeLastConnection);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String display(
			HttpServletRequest request, HttpServletResponse response) {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(
				request, em);
		lastVisitsSinceLastConnection(request, response, em,
				authenticatedUser);
		
		em.close();
		
		return SUCCESS;
	}

	/**
	 * Store the number of non reed private messages in the session
	 * 
	 * @param request
	 * @param em
	 */
	public static final void refreshNumNewVisits(HttpServletRequest request,
			EntityManager em) {
		// recovery of visitors
		HttpSession session = request.getSession();
		SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(
				request, em);
		ProfileVisiteFacade pvf = new ProfileVisiteFacade(em);
		Date lastConnectionSession = (Date) session
				.getAttribute("LastConnection");
		List<ProfileVisite> lastVisitors = pvf
				.getLastVisitorSinceLastConnection(authenticatedUser);

		int numNewVisits = 0;
		for (int i = 0; i < lastVisitors.size(); i++) {
			if (lastVisitors.get(i).getLastVisite()
					.after(lastConnectionSession)) {
				numNewVisits++;
			}
		}

		// paging list of recent visitors since my last connection
		session.setAttribute("numNewVisits", numNewVisits);
	}
	
	
}
