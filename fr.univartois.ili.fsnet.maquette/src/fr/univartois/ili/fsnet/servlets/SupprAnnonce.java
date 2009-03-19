package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;

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

public class SupprAnnonce extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private EntityManagerFactory factory;

	private EntityManager em;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupprAnnonce() {
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

		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("fsnetjpa");
		EntityManager em = factory.createEntityManager();
		Annonce monAnn = em.getReference(Annonce.class, Integer.valueOf(id));

		Annonce hubMerge;
		em.getTransaction().begin();
		hubMerge = em.merge(monAnn);
		hubMerge.setVisible("N");
		em.persist(hubMerge);
		em.getTransaction().commit();

		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(
				"/annonces.jsp");
		dispatch.forward(request, response);
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
