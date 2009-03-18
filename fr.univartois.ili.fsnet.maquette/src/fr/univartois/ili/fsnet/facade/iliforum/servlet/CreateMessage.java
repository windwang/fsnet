package fr.univartois.ili.fsnet.facade.iliforum.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.facade.forum.iliforum.IliForumFacade;

/**
 * Servlet implementation class CreateMessage
 */
public class CreateMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nom = request.getParameter("contenuMessage");
		Date date = new Date();
		EntiteSociale ent = (EntiteSociale) request.getSession().getAttribute(
				"entite");

		String nomUTF8 = new String(nom.getBytes("ISO-8859-1"), "UTF-8");
		Message message = new Message(nomUTF8, date, ent,
				(Topic) getServletContext().getAttribute("monTopic"));

		IliForumFacade.getInstance().addMessage(message);
		RequestDispatcher dispa = getServletContext().getRequestDispatcher(
				"/message.jsp");
		dispa.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
