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

import fr.univartois.ili.fsnet.entities.Annonce;

/**
 * author jerome bouwy Servlet implementation class AddAnnonce
 */
public class ModifAnnonce extends HttpServlet {

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

		// String date = request.getParameter("dateDebut");
		Annonce annonce1;
		annonce1 = (Annonce) getServletContext().getAttribute("annonce");

		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory("fsnetjpa");
		EntityManager entM;
		entM = factory.createEntityManager();
		Annonce manif;
		manif = entM.getReference(Annonce.class, annonce1.getId());

		String titre;
		titre = request.getParameter("titreAnnonce");
		String contenu;
		contenu = request.getParameter("contenuAnnonce");
		String dateFin;
		dateFin = request.getParameter("dateFinAnnonce");
		Date date = new Date();
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
		try {
			date = (Date) formatter.parse(dateFin);

		} catch (ParseException e) {
			logger.info(e.getMessage());
		}
		manif.setDateFinAnnnonce(date);
		manif.setContenu(contenu);
		manif.setNom(titre);

		entM.getTransaction().begin();
		entM.merge(manif);
		entM.getTransaction().commit();
		request
				.setAttribute("info",
						"<p id=\"info\">La modification d'une annonce est effectuée.</p>");

		RequestDispatcher dispa;
		dispa = getServletContext().getRequestDispatcher("/annonces.jsp");
		dispa.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		super.doPost(request, response);
	}

}
