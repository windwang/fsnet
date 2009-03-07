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

/**
 * @author romuald druelle.
 * 
 *         Servlet implementation class RemoveData
 */
public class RemoveUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private static final String FIND_BY_ID = "SELECT e FROM EntiteSociale e WHERE e.id = ?1";

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
	private void remove(EntiteSociale entite) {
		em.getTransaction().begin();
		em.remove(entite);
		em.getTransaction().commit();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String[] users = request.getParameterValues("userSelected");
		int length = users.length;
		Query query;
		List<EntiteSociale> lesEntites = new ArrayList<EntiteSociale>();
		EntiteSociale entite = null;

		for (int i = 0; i < length; i++) {
			query = em.createQuery(FIND_BY_ID);
			query.setParameter(1, Integer.parseInt(users[i]));
			entite = (EntiteSociale) query.getSingleResult();
			lesEntites.add(entite);
		}

		for (EntiteSociale e : lesEntites) {
			remove(e);
		}

		RequestDispatcher disp = getServletContext().getRequestDispatcher(
				"/AddUser.jsp?user=current&showHide=show&deploy=[-]&titleDeploy=R%E9duire la liste");
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
