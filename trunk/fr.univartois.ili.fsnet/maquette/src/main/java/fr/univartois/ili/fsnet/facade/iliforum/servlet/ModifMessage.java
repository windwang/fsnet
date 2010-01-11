package fr.univartois.ili.fsnet.facade.iliforum.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.forum.iliforum.IliForumFacade;

/**
 * Servlet implementation class ModifMessage
 */
public class ModifMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		String contenu;
		String contenuUTF8;
		Message message;

		contenu = request.getParameter("contenuMessage");
		contenuUTF8 = new String(contenu.getBytes("ISO-8859-1"), "UTF-8");
		message = (Message) getServletContext().getAttribute("monMessage");

		IliForumFacade.getInstance().updateMessage(message, contenuUTF8);

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
