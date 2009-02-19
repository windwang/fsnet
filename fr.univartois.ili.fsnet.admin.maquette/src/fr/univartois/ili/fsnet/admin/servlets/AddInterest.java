package fr.univartois.ili.fsnet.admin.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.Interet;

/**
 * @author romuald druelle Servlet implementation class AddInteret
 */
public class AddInterest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private EntityManagerFactory factory;

	private EntityManager em;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddInterest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	// public void init(ServletConfig config) throws ServletException {
	// super.init(config);
	// factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
	// em = factory.createEntityManager();
	// }
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
		Interet interet = null;
		String[] valuesInterests = request.getParameterValues("interets[]");
		int length = valuesInterests.length;
		String nom = request.getParameter("nom");
		interet = new Interet();
		interet.setNomInteret(nom);

		em.getTransaction().begin();
		em.persist(interet);
		em.getTransaction().commit();

		for (int i = 0; i < length; i++) {

			interet = new Interet();
			interet.setNomInteret(valuesInterests[i]);

			em.getTransaction().begin();
			em.persist(interet);
			em.getTransaction().commit();
		}

		RequestDispatcher disp = getServletContext().getRequestDispatcher(
				"/AddInterest.jsp");
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

}
