package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;
import java.util.Date;
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
import javax.servlet.http.HttpSession;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Inscription;
import fr.univartois.ili.fsnet.security.Md5;

/**
 * @author lionel desruelles Servlet implementation class FindUser
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

		RequestDispatcher dispatch = null;
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		EntiteSociale en = null;
		em.clear();
		Query query = em
				.createQuery("SELECT en FROM EntiteSociale en WHERE en.email LIKE ?1");
		query.setParameter(1, email);

		Query query2 = em
				.createQuery("SELECT ins FROM Inscription ins WHERE ins.entite =?1");

		try {

			en = (EntiteSociale) query.getResultList().get(0);
			en = em.merge(en);

			logger.info(en.getEmail());
			logger.info(String.valueOf(en.getId()));
			logger.info("Taille interet : " + en.getLesinterets().size());

			query2.setParameter(1, en);
			Inscription inscrit = (Inscription) query2.getSingleResult();
			inscrit = em.merge(inscrit);

			logger.info(inscrit.getEtat());

			session.setAttribute("entite", en);
			if (inscrit.getEtat().equals(Inscription.ATTENTE)) {
				Date dateJour = new Date();
				en.setDateEntree(dateJour);

				em.getTransaction().begin();
				em.persist(en);
				em.getTransaction().commit();

				dispatch = getServletContext().getRequestDispatcher(
						"/profil.jsp");
				dispatch.forward(request, response);
			} else {
				if ((inscrit.getEtat().equals(Inscription.INSCRIT))
						&& Md5.testPassword(password, en.getMdp())) {
					getServletContext().setAttribute("erreur", "");
					dispatch = getServletContext().getRequestDispatcher(
							"/index.jsp");
					dispatch.forward(request, response);
				} else {
					getServletContext().setAttribute("erreur",
							"Authentification échouée");
					dispatch = getServletContext().getRequestDispatcher(
							"/login.jsp");
					dispatch.forward(request, response);
				}

			}

		} catch (Exception e) {
			logger.info("Authentification échouée" + e);
			getServletContext().setAttribute("erreur",
					"Authentification échouée");
			dispatch = getServletContext().getRequestDispatcher("/login.jsp");
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
