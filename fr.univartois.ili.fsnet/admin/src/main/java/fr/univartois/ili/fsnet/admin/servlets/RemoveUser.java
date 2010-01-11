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

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Inscription;

/**
 * @author romuald druelle.
 * 
 *         Servlet implementation class RemoveData
 */
public class RemoveUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private static final String FIND_ENTITY_BY_ID = "SELECT e FROM EntiteSociale e WHERE e.id = ?1";

	private static final String FIND_REGISTRATION_BY_ENTITY = "SELECT i FROM Inscription i WHERE i.entite = ?1";

	private EntityManagerFactory factory;

	private EntityManager em;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RemoveUser() {
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
	 * A method that allow to remove a social entity.
	 * 
	 * @param entite
	 */
	private void remove(Object object) {
		em.getTransaction().begin();
		em.remove(object);
		em.getTransaction().commit();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String[] users = request.getParameterValues("userSelected");
		String redirection = request.getParameter("redirection");
		int length = users.length;
		Query query;
		List<EntiteSociale> lesEntites = new ArrayList<EntiteSociale>();
		List<Inscription> lesInscriptions = new ArrayList<Inscription>();
		EntiteSociale entite = null;
		Inscription inscription = null;

		for (int i = 0; i < length; i++) {
			query = em.createQuery(FIND_ENTITY_BY_ID);
			query.setParameter(1, Integer.parseInt(users[i]));
			entite = (EntiteSociale) query.getSingleResult();
			lesEntites.add(entite);
			query = em.createQuery(FIND_REGISTRATION_BY_ENTITY);
			query.setParameter(1, entite);
			inscription = (Inscription) query.getSingleResult();
			lesInscriptions.add(inscription);
		}

		for (Inscription i : lesInscriptions) {
			remove(i);
		}

		for (EntiteSociale e : lesEntites) {
			remove(e);
		}

		lesEntites.clear();
		lesInscriptions.clear();

		RequestDispatcher disp = getServletContext()
				.getRequestDispatcher(
						"/"
								+ redirection
								+ "?user=current&showHide=show&deploy=[-]&titleDeploy=R%E9duire la liste&recherche=hide");
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
