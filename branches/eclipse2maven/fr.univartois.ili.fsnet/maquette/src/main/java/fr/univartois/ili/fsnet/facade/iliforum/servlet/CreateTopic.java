package fr.univartois.ili.fsnet.facade.iliforum.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.forum.iliforum.IliForumFacade;

/**
 * Servlet implementation class CreateTopic
 */
public class CreateTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		String nom;
		String contenu;
		Date date;
		EntiteSociale ent;
		String nomUTF8;
		Topic topic;
		String contenuUTF8;
		Message message;
		IliForumFacade iff;

		nom = request.getParameter("nomTopic");
		contenu = request.getParameter("contenuMessage");
		date = new Date();
		ent = (EntiteSociale) request.getSession().getAttribute("entite");
		nomUTF8 = new String(nom.getBytes("ISO-8859-1"), "UTF-8");
		topic = new Topic(nomUTF8, date, null, (Hub) getServletContext()
				.getAttribute("monHub"), ent);
		contenuUTF8 = new String(contenu.getBytes("ISO-8859-1"), "UTF-8");
		message = new Message(contenuUTF8, date, ent, topic);

		iff = IliForumFacade.getInstance();
		iff.addTopic(topic);
		iff.addMessage(message);

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
