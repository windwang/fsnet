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
import fr.univartois.ili.fsnet.forum.iliforum.IliForumFacade;

/**
 * Servlet implementation class SupprMess
 */
public class SupprMess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		int idmessage;
		int identite;
		EntityManagerFactory factory;
		EntityManager entM;
		Message mess;

		idmessage = Integer.valueOf(request.getParameter("idMess"));
		identite = Integer.valueOf(request.getParameter("idEntite"));
		factory = Persistence.createEntityManagerFactory("fsnetjpa");
		entM = factory.createEntityManager();
		mess = entM.getReference(Message.class, Integer.valueOf(idmessage));

		if (mess.getPropMsg().getId() == identite) {
			IliForumFacade.getInstance().removeMessage(mess);
		}

		RequestDispatcher dispa;
		dispa = getServletContext().getRequestDispatcher("/message.jsp");
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
