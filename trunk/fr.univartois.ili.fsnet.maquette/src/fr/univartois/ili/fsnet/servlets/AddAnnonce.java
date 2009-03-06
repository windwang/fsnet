package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.*;

/**
 * author jerome bouwy Servlet implementation class AddAnnonce
 */
public class AddAnnonce extends HttpServlet {
	private static Logger logger = Logger.getLogger("FSNet");

	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private EntityManagerFactory factory;

	private EntityManager em;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddAnnonce() {
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
		// TODO Auto-generated method stub
		String id = request.getParameter("idChoisi");
		getServletContext().setAttribute("idChoisi", id);

		if (id.equalsIgnoreCase("0")) {
			System.out.println("liste totale");

			Query requete = em.createQuery("SELECT a FROM Annonce a");
			Date aujourdhui = new Date();
			List<Annonce> listAnn = (List<Annonce>) requete.getResultList();
			Iterator it = listAnn.iterator();
			while (it.hasNext()) {
				Annonce a = (Annonce) it.next();
				System.out.println("dateFin" + a.getDateFinAnnonce());
				if (a.getDateFinAnnonce().before(aujourdhui)) {
					em.getTransaction().begin();
					em.remove(a);
					em.getTransaction().commit();
				}

			}
			RequestDispatcher dispatch = getServletContext()
					.getRequestDispatcher("/annonces.jsp");
			dispatch.forward(request, response);
		}

		else {
			RequestDispatcher dispatch = getServletContext()
					.getRequestDispatcher("/detailAnnonce.jsp");
			dispatch.forward(request, response);

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String titre = request.getParameter("titreAnnonce");
		String contenu = request.getParameter("contenuAnnonce");
		String dateFin = request.getParameter("dateFinAnnonce");
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");

		if (titre.isEmpty() || contenu.isEmpty() || dateFin.isEmpty()) {
			
			request.setAttribute("titre", titre);
			request.setAttribute("contenu", contenu);
			request.setAttribute("datefin", dateFin);
			RequestDispatcher dispatch = getServletContext()
					.getRequestDispatcher("/publierannonce.jsp");
			dispatch.forward(request, response);
			return;
		}

		System.out.println("dateFin" + dateFin);
		Date date = null;
		Date aujourdhui = new Date();
		try {
			date = (Date) formatter.parse(dateFin);
			System.out.println("date format " + date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (date.after(aujourdhui)) {

			Annonce nouvelleInfo = new Annonce(titre, aujourdhui, contenu, date);
			em.getTransaction().begin();
			em.persist(nouvelleInfo);
			em.getTransaction().commit();
		}
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(
				"/annonces.jsp");
		dispatch.forward(request, response);

	}

}
