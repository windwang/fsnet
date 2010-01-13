package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Inscription;

/**
 * Servlet implementation class InitFSNet
 */
public class InitFSNet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DATABASE_NAME = "fsnetjpa";

	/**
	 * @throws ParseException
	 * @see HttpServlet#HttpServlet()
	 */
	public InitFSNet() throws ParseException {
		super();
		EntityManagerFactory factory;
		EntityManager entM;
		EntiteSociale entite;
		Inscription inscrit;
		DateFormat formatter;
		Date date1;
		Date date2;

		formatter = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
		date1 = (Date) formatter.parse("29/01/09");
		date2 = (Date) formatter.parse("15/01/1986");

		entite = new EntiteSociale("UserTestNom", "UserTestPrenom",
				"18 rue JeanSouvraz", date1, date2, "test", null,
				"developpeur", "test@test.fr", "03/03/04/05/06", null, null,
				null);
		factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
		entM = factory.createEntityManager();

		Query query;
		query = entM
				.createQuery("SELECT e FROM EntiteSociale e WHERE e.email LIKE ?1");
		query.setParameter(1, entite.getEmail());
		if (query.getResultList().size() == 0) {
			inscrit = new Inscription(entite);
			inscrit.setEtat();
			entM.getTransaction().begin();
			entM.persist(entite);
			entM.persist(inscrit);
			entM.getTransaction().commit();
		}
		entM.close();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(final ServletConfig config) throws ServletException {}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {}

}
