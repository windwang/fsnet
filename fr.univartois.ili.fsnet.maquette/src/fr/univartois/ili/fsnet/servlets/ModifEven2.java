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

import fr.univartois.ili.fsnet.entities.Manifestation;

/**
 * Servlet implementation class ModifEven2
 */
public class ModifEven2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {

		// String date = request.getParameter("dateDebut");
		Manifestation manif1;
		manif1 = (Manifestation) getServletContext().getAttribute("manif");

		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory("fsnetjpa");
		EntityManager entM;
		entM = factory.createEntityManager();
		Manifestation manif;
		manif = entM.getReference(Manifestation.class, manif1.getId());

		String titre;
		titre = request.getParameter("titreEvenement");
		String contenu;
		contenu = request.getParameter("contenuEvenement");
		String date;
		date = request.getParameter("dateDebut");
		Date dateDebut;
		dateDebut = new Date();
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
		try {
			dateDebut = (Date) formatter.parse(date);
		} catch (ParseException e) {
			Logger logger;
			logger = Logger.getLogger("FSNet");
			logger.info(e.getMessage());
		}
		manif.setdateManifestation(dateDebut);
		manif.setContenu(contenu);
		manif.setNom(titre);

		entM.getTransaction().begin();
		entM.merge(manif);
		entM.getTransaction().commit();
		RequestDispatcher dispa;
		dispa = getServletContext().getRequestDispatcher("/toutEvenement.jsp");
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
