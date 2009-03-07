package fr.univartois.ili.fsnet.admin.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.EntiteSociale;

/**
 * @author romuald druelle. Servlet implementation class AddUser
 */
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private EntityManagerFactory factory;

	private EntityManager em;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddUser() {
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
	 * Method that creates an email address from the concatenation of name and
	 * surname of a social entity.
	 * 
	 * @param nom
	 * @param prenom
	 * @return the created email address .
	 */
	public String createEmail(String nom, String prenom) {
		StringBuilder builder = new StringBuilder();
		builder.append(nom);
		builder.append("@");
		builder.append(prenom);
		builder.append(".net");
		return builder.toString();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nom = request.getParameter("Nom");
		String prenom = request.getParameter("Prenom");
		// String email = createEmail(nom, prenom);
		String email = request.getParameter("Email");
		EntiteSociale entite = new EntiteSociale(nom, prenom, email);

		em.getTransaction().begin();
		em.persist(entite);
		em.getTransaction().commit();

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
