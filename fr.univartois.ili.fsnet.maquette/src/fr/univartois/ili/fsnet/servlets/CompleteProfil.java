package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
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
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Inscription;
import fr.univartois.ili.fsnet.entities.Interet;

/**
 * @author lionel desruelles Servlet implementation class CompleteProfil
 */
public class CompleteProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private EntityManagerFactory factory;

	private EntityManager em;

	private static final String FIND_INTERET_BY_ID = "SELECT i FROM Interet i WHERE i.id = ?1";

	private static final String FIND_INSCRIPTION_BY_ENTITE = "SELECT ins FROM Inscription ins WHERE ins.entite =?1";

	private static Logger logger = Logger.getLogger("FSNet");

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CompleteProfil() {
		super();
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
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

		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		String dateNaissance = request.getParameter("dateNaissance");
		String adresse = request.getParameter("adresse");
		String telephone = request.getParameter("telephone");
		String profession = request.getParameter("profession");

		// Query query = em
		// .createQuery("SELECT en FROM EntiteSociale en WHERE en.email LIKE ?1");
		// query.setParameter(1, email);
		// EntiteSociale entite = (EntiteSociale) query.getSingleResult();

		EntiteSociale entite = (EntiteSociale) session.getAttribute("entite");
		entite = em.find(EntiteSociale.class, entite.getId());

		List<Interet> lesInterets = new ArrayList<Interet>();
		// Interet
		String[] interests = null;
		interests = request.getParameterValues("interestSelected");

		if (interests != null) {
			Query interest;
			int length = interests.length;
			Interet interet = null;

			for (int i = 0; i < length; i++) {
				logger.info("TEST:id =" + interests[i]);
				interest = em.createQuery(FIND_INTERET_BY_ID);
				interest.setParameter(1, Integer.parseInt(interests[i]));
				interet = (Interet) interest.getSingleResult();
				lesInterets.add(interet);
			}
		}
		//

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = (Date) formatter.parse(dateNaissance);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Query inscrit = em.createQuery(FIND_INSCRIPTION_BY_ENTITE);
		inscrit.setParameter(1, entite);
		Inscription ins = (Inscription) inscrit.getSingleResult();
		ins.setEtat();

		entite.setAdresse(adresse);
		entite.setDateNaissance(date);

		entite.setNumTel(telephone);
		entite.setProfession(profession);
		entite.setLesinterets(lesInterets);

		em.getTransaction().begin();
		em.persist(entite);
		em.getTransaction().commit();

		logger.info("Taille interets : " + entite.getLesinterets().size());

		if (interests != null)
			logger.info("Nom interets : "
					+ entite.getLesinterets().get(0).getNomInteret());
		session.setAttribute("entite", entite);

		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(
				"/index.jsp");
		dispatch.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
