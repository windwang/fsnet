package fr.univartois.ili.fsnet.admin.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.Interet;

/**
 * @author romuald druelle.
 * 
 *         Servlet implementation class RemoveInterest
 */
public class RemoveInterest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private static final String FIND_BY_ID = "SELECT i FROM Interet i WHERE i.id = ?1";

	private EntityManagerFactory factory;

	private EntityManager em;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RemoveInterest() {
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
	 * A method that allow to remove an interest.
	 * 
	 * @param interet
	 */
	private void remove(Interet interet) {
		em.getTransaction().begin();
		em.remove(interet);
		em.getTransaction().commit();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String[] interests = request.getParameterValues("interestSelected");
		String redirection = request.getParameter("redirection");
		int length = interests.length;
		Query query;
		List<Interet> lesInterets = new ArrayList<Interet>();
		Interet interet = null;

		for (int i = 0; i < length; i++) {
			query = em.createQuery(FIND_BY_ID);
			query.setParameter(1, Integer.parseInt(interests[i]));
			interet = (Interet) query.getSingleResult();
			lesInterets.add(interet);
		}

		for (Interet i : lesInterets) {
			remove(i);
		}

		RequestDispatcher disp = getServletContext()
				.getRequestDispatcher(
						"/"
								+ redirection
								+ "?interest=current&showHide=show&deploy=[-]&titleDeploy=R%E9duire la liste&recherche=hide");
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
