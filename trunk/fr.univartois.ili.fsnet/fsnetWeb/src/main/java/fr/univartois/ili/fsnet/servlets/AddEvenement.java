package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Manifestation;

/**
 * Servlet implementation class AddEvenement
 */
public class AddEvenement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DATABASE_NAME = "fsnetjpa";
	private transient EntityManagerFactory factory;
	private transient EntityManager entM;
	private static final Logger logger = Logger.getLogger("FSNet");

	@Override
	public void init() throws ServletException {
		super.init();
		factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
		entM = factory.createEntityManager();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		String ident;
		ident = request.getParameter("id");
		getServletContext().setAttribute("idEven", ident);

		RequestDispatcher dispatch;
		dispatch = getServletContext().getRequestDispatcher("/detailEven.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {

		EntiteSociale entite;

		HttpSession session;
		session = request.getSession();
		entite = (EntiteSociale) session.getAttribute("entite");

		String titre;
		titre = request.getParameter("titreEvenement");
		String contenu;
		contenu = request.getParameter("contenuEvenement");
		String date;
		date = request.getParameter("dateDebut");
		if (titre.isEmpty() || contenu.isEmpty() || date.isEmpty()) {

			request.setAttribute("titre", titre);
			request.setAttribute("contenu", contenu);
			request.setAttribute("date", date);

			RequestDispatcher dispatch;
			dispatch = getServletContext().getRequestDispatcher(
					"/creerevenement.jsp");
			dispatch.forward(request, response);
		} else {

			Date date1;
			date1 = new Date();
			DateFormat formatter;
			formatter = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
			try {
				date1 = (Date) formatter.parse(date);

			} catch (ParseException e) {
				logger.info(e.getMessage());
			}

			Manifestation nouvellevenement;
			nouvellevenement = new Manifestation(titre, date1, contenu, "Y",
					entite);
			entM.getTransaction().begin();
			entM.persist(nouvellevenement);
			entM.getTransaction().commit();
			request.setAttribute("info",
					"<p id=\"info\">Votre événement est bien validé.</p>");
			RequestDispatcher dispatch;
			dispatch = getServletContext().getRequestDispatcher(
					"/toutEvenement.jsp");
			dispatch.forward(request, response);
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		if (entM != null) {
			entM.close();
		}
		if (factory != null) {
			factory.close();
		}
	}

}
