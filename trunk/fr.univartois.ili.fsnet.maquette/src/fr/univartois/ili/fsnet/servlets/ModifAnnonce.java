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

	private EntityManagerFactory factory;

	private EntityManager em;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifAnnonce() {
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

		// String date = request.getParameter("dateDebut");
		Annonce annonce1 = (Annonce) getServletContext()
				.getAttribute("annonce");

		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("fsnetjpa");
		EntityManager em = factory.createEntityManager();
		Annonce manif = em.getReference(Annonce.class, annonce1.getId());

		String titre = request.getParameter("titreAnnonce");
		String contenu = request.getParameter("contenuAnnonce");
		String dateFin = request.getParameter("dateFinAnnonce");
		Date date = null;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			date = (Date) formatter.parse(dateFin);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		manif.setDateFinAnnnonce(date);
		manif.setContenu(contenu);
		manif.setNom(titre);

		em.getTransaction().begin();
		em.merge(manif);
		em.getTransaction().commit();
		RequestDispatcher dispa = getServletContext().getRequestDispatcher(
				"/annonces.jsp");
		dispa.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(request, response);
	}

}
