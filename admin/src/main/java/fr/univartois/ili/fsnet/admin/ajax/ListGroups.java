package fr.univartois.ili.fsnet.admin.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;

/**
 * Servlet implementation class ListGroups
 */
public class ListGroups extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListGroups() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = PersistenceProvider.createEntityManager();
		String idGroup = request.getParameter("idGroup");

		String idParent = request.getParameter("idParent");

		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		SocialGroup group = null;
		SocialGroup parentGroup = null;
		List<SocialGroup> socialGroups = null;

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		if (!idGroup.equals("")) {
			group = socialGroupFacade.getSocialGroup(Integer.parseInt(idGroup));
		}
		if (!idParent.equals("")) {
			parentGroup = socialGroupFacade.getSocialGroup(Integer
					.parseInt(idParent));
		}

		socialGroups = getRefusedSocialGroup(socialGroupFacade, group,
				parentGroup);

		for (SocialGroup socialGroup : socialGroups) {
			out.print("<option value='" + socialGroup.getId() + "'>"
					+ socialGroup.getName() + "</option>");
			socialGroupFacade.getAllChildGroups(socialGroup);
		}

		out.close();
	}

	private List<SocialGroup> getRefusedSocialGroup(SocialGroupFacade sgf,
			SocialGroup socialGroup, SocialGroup parentGroup) {
		if (sgf == null) {
			throw new IllegalArgumentException();
		}
		List<SocialGroup> resulGroups = new ArrayList<SocialGroup>();
		List<SocialGroup> allGroups = sgf.getAllSocialGroups();

		if (socialGroup != null) {
			List<SocialGroup> groups = sgf.getAcceptedSocialElementsByFilter(socialGroup, SocialGroup.class);
			allGroups.removeAll(groups);
			allGroups.removeAll(sgf.getAllAntecedentSocialGroups(socialGroup));
		}
		if (parentGroup != null) {
			allGroups.remove(parentGroup);
			allGroups.removeAll(sgf.getAllAntecedentSocialGroups(parentGroup));
		}

		for (SocialGroup sg : allGroups) {
			if (sg.getGroup() == null && !sg.equals(socialGroup))
				resulGroups.add(sg);
		}

		return resulGroups;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * 
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
