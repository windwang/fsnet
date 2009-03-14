package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	private EntityManagerFactory factory;
	private EntityManager em;

	/**
	 * @throws ParseException
	 * @see HttpServlet#HttpServlet()
	 */
	public InitFSNet() throws ParseException {
		super();
		EntiteSociale en;
		Inscription inscrit;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date1 = (Date) formatter.parse("29/01/09");
		Date date2 = (Date) formatter.parse("15/01/1986");
		en = new EntiteSociale("UserTestNom", "UserTestPrenom",
				"18 rue JeanSouvraz", date1, date2, "test", null,
				"developpeur", "test@test.fr", "03/03/04/05/06", null, null,
				null);
		factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
		em = factory.createEntityManager();
		Query query = em
				.createQuery("SELECT e FROM EntiteSociale e WHERE e.email LIKE ?1");
		query.setParameter(1, en.getEmail());
		if (query.getResultList().size() == 0) {
			inscrit = new Inscription(en);
			inscrit.setEtat();
			em.getTransaction().begin();
			em.persist(en);
			em.persist(inscrit);
			em.getTransaction().commit();
		}
		em.close();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
