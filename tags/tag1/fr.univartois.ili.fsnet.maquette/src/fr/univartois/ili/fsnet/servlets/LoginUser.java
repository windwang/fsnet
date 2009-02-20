package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;
import java.util.logging.Logger;

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
 * @author lionel desruelles 
 * Servlet implementation class FindUser
 */
public class LoginUser extends HttpServlet {

	private static Logger logger = Logger.getLogger("FSNet");

	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private EntityManagerFactory factory;

	private EntityManager em;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginUser() {
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		Query query = em
				.createQuery("SELECT en FROM EntiteSociale en WHERE en.email LIKE ?1");
		query.setParameter(1, email);

		try {
			EntiteSociale en = (EntiteSociale) query.getSingleResult();
			logger.info(en.getEmail());
			logger.info(String.valueOf(en.getId()));
			getServletContext().setAttribute("idLogin", en.getId());
			
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/profil.jsp");
			dispatch.forward(request, response);
		} catch (Exception e) {
			logger.info("Authentification échouée");
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/login.html");
			dispatch.forward(request, response);
		}
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
