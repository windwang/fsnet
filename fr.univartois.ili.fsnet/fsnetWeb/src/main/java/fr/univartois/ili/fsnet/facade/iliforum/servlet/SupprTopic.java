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

import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.facade.forum.iliforum.IliForumFacade;

/**
 * Servlet implementation class SupprTopic
 */
public class SupprTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		int idtopic;
		int identite;
		EntityManagerFactory factory;
		EntityManager entM;
		Topic monTopic;

		idtopic = Integer.valueOf(request.getParameter("idTopic"));
		identite = Integer.valueOf(request.getParameter("idEntite"));
		factory = Persistence.createEntityManagerFactory("fsnetjpa");
		entM = factory.createEntityManager();
		monTopic = entM.getReference(Topic.class, Integer.valueOf(idtopic));

		if (monTopic.getPropTopic().getId() == identite) {
			IliForumFacade.getInstance().removeTopic(monTopic);
		}

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
