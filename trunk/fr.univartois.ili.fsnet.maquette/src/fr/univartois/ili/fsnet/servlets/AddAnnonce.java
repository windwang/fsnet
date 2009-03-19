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
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.univartois.ili.fsnet.entities.Annonce;
import fr.univartois.ili.fsnet.entities.EntiteSociale;

/**
 * author jerome bouwy Servlet implementation class AddAnnonce
 */
public class AddAnnonce extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private transient EntityManagerFactory factory;

	private transient EntityManager entM;

	@Override
	public void init() throws ServletException {
		super.init();
		factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
		entM = factory.createEntityManager();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		if (entM != null) {
			entM.close();
		}
		if (factory != null) {
			factory.close();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		String ident;
		ident = request.getParameter("idChoisi");
		getServletContext().setAttribute("idChoisi", ident);
		RequestDispatcher dispatch;

		if (ident.equalsIgnoreCase("0")) {

			dispatch = getServletContext()
					.getRequestDispatcher("/annonces.jsp");
			dispatch.forward(request, response);
		}

		else {
			dispatch = getServletContext().getRequestDispatcher(
					"/detailAnnonce.jsp");
			dispatch.forward(request, response);

		}
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
		titre = request.getParameter("titreAnnonce");
		String contenu;
		contenu = request.getParameter("contenuAnnonce");
		String dateFin;
		dateFin = request.getParameter("dateFinAnnonce");
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);

		if (titre.isEmpty() || contenu.isEmpty() || dateFin.isEmpty()) {

			request.setAttribute("titre", titre);
			request.setAttribute("contenu", contenu);
			request.setAttribute("datefin", dateFin);
			RequestDispatcher dispatch;
			dispatch = getServletContext().getRequestDispatcher(
					"/publierannonce.jsp");
			dispatch.forward(request, response);

		} else {

			Date date = null;
			Date aujourdhui;
			aujourdhui = new Date();
			try {
				date = (Date) formatter.parse(dateFin);
			} catch (ParseException e) {
				Logger logger;
				logger = Logger.getLogger("FSNet");
				logger.info(e.getMessage());
			}

			Annonce nouvelleInfo;
			nouvelleInfo = new Annonce(titre, aujourdhui, contenu, date, "Y",
					entite);
			entM.getTransaction().begin();
			entM.persist(nouvelleInfo);
			entM.getTransaction().commit();

			RequestDispatcher dispatch;
			dispatch = getServletContext()
					.getRequestDispatcher("/annonces.jsp");
			dispatch.forward(request, response);

		}
	}
}
