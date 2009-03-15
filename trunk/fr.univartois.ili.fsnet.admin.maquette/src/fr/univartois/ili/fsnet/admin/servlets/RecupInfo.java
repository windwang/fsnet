package fr.univartois.ili.fsnet.admin.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Hub;

import fr.univartois.ili.fsnet.entities.Topic;

/**
 * Servlet implementation class RecupInfo
 */
public class RecupInfo extends HttpServlet {
	private static Logger logger = Logger.getLogger("FSNet");

	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private EntityManagerFactory factory;

	private EntityManager em;
	
	Date date = new Date();
	DateFormat formatter = new SimpleDateFormat("dd/MM/yy");

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecupInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		super.init();
		factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
		em = factory.createEntityManager();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		if (em != null) {
			em.close();
		}
		if (factory != null) {
			factory.close();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("nbInscrit", nombreInscrit());
		request.setAttribute("nbAnnoncesTot", nombreAnnonces());
		request.setAttribute("nbHubTot", nombreTotalHub());
		request.setAttribute("nbAnnoncesPub", nombreAnnoncesValides());
		request.setAttribute("nbEvenementTot", nombreEvenements());
		request.setAttribute("nbEvenementPub", nombreEvenementValides());
		
		RequestDispatcher disp = getServletContext().getRequestDispatcher(
				"/rapportactivite.jsp?rapport=current");
		disp.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private int nombreInscrit() {
		Query query = em.createQuery("SELECT count(en) FROM EntiteSociale en");
		return Integer.parseInt(query.getSingleResult().toString());
	}

	private int nombreEnCoursInscription() {
		// En cours
		return 0;
	}

	private int nombreAnnonces() {
		Query query = em.createQuery("SELECT count(a) FROM Annonce a");
		return Integer.parseInt(query.getSingleResult().toString());
	}

	private int nombreAnnoncesValides() {
		Query query = em
				.createQuery("SELECT count(a) FROM Annonce a WHERE a.dateFinAnnonce>?1");
		query.setParameter(1, date);
		return Integer.parseInt(query.getSingleResult().toString());
	}

	private int nombreEvenements() {
		Query query = em.createQuery("SELECT count(e) FROM Manifestation e");
		return Integer.parseInt(query.getSingleResult().toString());
	}
	private int nombreEvenementValides() {
		Query query = em
				.createQuery("SELECT count(e) FROM Manifestation e WHERE e.dateManifestation>?1");
		query.setParameter(1, date);
		return Integer.parseInt(query.getSingleResult().toString());
	}

	private int nombreTotalHub() {
		Query query = em.createQuery("SELECT count(h) FROM Hub h");
		return Integer.parseInt(query.getSingleResult().toString());
	}

}
