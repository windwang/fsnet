package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * Servlet implementation class AddEvenement
 */
public class AddEvenement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DATABASE_NAME = "fsnetjpa";
	private EntityManagerFactory factory;
	private EntityManager em;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddEvenement() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
		em = factory.createEntityManager();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		getServletContext().setAttribute("idEven", id);
		System.out.println("********************************evoila le param id"
				+ getServletContext().getInitParameter("idEven"));

		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(
				"/detailEven.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String titre = request.getParameter("titreEvenement");
		String contenu = request.getParameter("contenuEvenement");
		String date = request.getParameter("dateDebut");
		System.out.println("date de debut" + date + titre + contenu);
		if (titre.isEmpty() || contenu.isEmpty() || date.isEmpty()) {

			request.setAttribute("titre", titre);
			request.setAttribute("contenu", contenu);
			request.setAttribute("date", date);

			RequestDispatcher dispatch = getServletContext()
					.getRequestDispatcher("/creerevenement.jsp");
			dispatch.forward(request, response);
		} else {

			System.out.println("Test aDD EVENEMENT " + titre + " " + contenu
					+ " " + date);
			Date date1 = null;
			DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
			try {
				date1 = (Date) formatter.parse(date);
				System.out.println("date format " + date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Manifestation nouvellevenement = new Manifestation(titre, date1,
					contenu);
			em.getTransaction().begin();
			em.persist(nouvellevenement);
			em.getTransaction().commit();

			RequestDispatcher dispatch = getServletContext()
					.getRequestDispatcher("/EvenementValide.jsp");
			dispatch.forward(request, response);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		if (em != null) {
			em.close();
		}
		if (factory != null) {
			factory.close();
		}
	}

}
