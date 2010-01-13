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

import fr.univartois.ili.fsnet.entities.Message;

/**
 * Servlet implementation class GotoModifMessage
 */
public class GotoModifMessage extends HttpServlet {
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
		EntityManager entM;
		Message monMess;

		factory = Persistence.createEntityManagerFactory("fsnetjpa");
		entM = factory.createEntityManager();
		monMess = entM.getReference(Message.class, Integer.valueOf(request
				.getParameter("idMess")));

		getServletContext().setAttribute("monMessage", monMess);

		RequestDispatcher dispa;
		dispa = getServletContext().getRequestDispatcher("/modifMessage.jsp");
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
		// TODO Auto-generated method stub
	}

}
