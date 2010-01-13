package fr.univartois.ili.fsnet.facade.iliforum.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.facade.forum.iliforum.IliForumFacade;

/**
 * Servlet implementation class SupprHub
 */
public class SupprHub extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		int idhub;
		int identite;
		EntityManagerFactory factory;
		EntityManager entM;
		Hub monHub;

		idhub = Integer.valueOf(request.getParameter("idHub"));
		identite = Integer.valueOf(request.getParameter("idEntite"));
		factory = Persistence.createEntityManagerFactory("fsnetjpa");
		entM = factory.createEntityManager();
		monHub = entM.getReference(Hub.class, Integer.valueOf(idhub));

		if (monHub.getCreateur().getId() == identite) {
			IliForumFacade.getInstance().removeHub(monHub);
		}

		RequestDispatcher dispa;
		dispa = getServletContext().getRequestDispatcher("/hub.jsp");
		dispa.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}

}
