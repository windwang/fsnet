package fr.univartois.ili.fsnet.admin.servlets;

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

import fr.univartois.ili.fsnet.entities.Interet;

/**
 * @author romuald druelle Servlet. implementation class AddInteret
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
	 * A method that allow to persist an interest.
	 * 
	 * @param interet
	 */
	private void persist(Interet interet) {
		em.getTransaction().begin();
		em.persist(interet);
		em.getTransaction().commit();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Interet interet = null;
		int length = 0;
		String nom = request.getParameter("Intitule");
		String[] valuesInterests = request.getParameterValues("interets[]");
		if (valuesInterests != null) {
			length = valuesInterests.length;
		}

		interet = new Interet();
		interet.setNomInteret(nom);

		persist(interet);

		if (length != 0) {
			for (int i = 0; i < length; i++) {

				interet = new Interet();
				interet.setNomInteret(valuesInterests[i]);
				persist(interet);
			}
		}

		RequestDispatcher disp = getServletContext().getRequestDispatcher(
				"/AddInterest.jsp?interest=current&showHide=show&deploy=[-]&titleDeploy=R%E9duire la liste");
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
