package fr.univartois.ili.fsnet.facade.iliforum.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.facade.forum.iliforum.IliForumFacade;

/**
 * Servlet implementation class ModifHub
 */
public class ModifHub extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		String titre;
		Hub hub;
		String titreUTF8;

		titre = request.getParameter("titreHub");
		hub = (Hub) getServletContext().getAttribute("monHub");
		titreUTF8 = new String(titre.getBytes("ISO-8859-1"), "UTF-8");

		IliForumFacade.getInstance().updateHub(hub, titreUTF8);

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
