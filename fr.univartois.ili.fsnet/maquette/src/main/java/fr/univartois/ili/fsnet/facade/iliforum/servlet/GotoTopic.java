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

/**
 * Servlet implementation class GotoTopic
 */
public class GotoTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory("fsnetjpa");
		EntityManager entM;
		entM = factory.createEntityManager();

		Hub monHub;
		monHub = entM.getReference(Hub.class, Integer.valueOf(request
				.getParameter("idHub")));
		getServletContext().setAttribute("monHub", monHub);

		RequestDispatcher dispa;
		dispa = getServletContext().getRequestDispatcher("/topic.jsp");
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