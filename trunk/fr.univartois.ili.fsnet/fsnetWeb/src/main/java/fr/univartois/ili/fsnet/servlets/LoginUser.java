package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Inscription;

/**
 * @author lionel desruelles Servlet implementation class FindUser
 */
public class LoginUser extends HttpServlet {

	private static final Logger logger = Logger.getLogger("FSNet");

	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private transient EntityManagerFactory factory;

	private transient EntityManager entM;

	@Override
	public void init() throws ServletException {
		super.init();
		factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
		entM = factory.createEntityManager();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		if (entM != null) {
			entM.close();
		}
		if (factory != null) {
			factory.close();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {

		RequestDispatcher dispatch;
		HttpSession session;
		session = request.getSession();
		String email;
		email = request.getParameter("email");
		String password;
		password = request.getParameter("password");
		EntiteSociale entite;
		entM.clear();
		Query query;
		query = entM
				.createQuery("SELECT en FROM EntiteSociale en WHERE en.email LIKE ?1");
		query.setParameter(1, email);

		Query query2;
		query2 = entM
				.createQuery("SELECT ins FROM Inscription ins WHERE ins.entite =?1");

		try {

			entite = (EntiteSociale) query.getResultList().get(0);
			entite = entM.merge(entite);

			logger.info(entite.getEmail());
			logger.info(String.valueOf(entite.getId()));
			logger.info("Taille interet : " + entite.getLesinterets().size());

			query2.setParameter(1, entite);
			Inscription inscrit;
			inscrit = (Inscription) query2.getSingleResult();
			inscrit = entM.merge(inscrit);

			logger.info(inscrit.getEtat());

			session.setAttribute("entite", entite);
			if (inscrit.getEtat().equals(Inscription.ATTENTE)) {
				Date dateJour;
				dateJour = new Date();
				entite.setDateEntree(dateJour);

				entM.getTransaction().begin();
				entM.persist(entite);
				entM.getTransaction().commit();

				dispatch = getServletContext().getRequestDispatcher(
						"/profil.jsp");
				dispatch.forward(request, response);
			} else {
				if ((inscrit.getEtat().equals(Inscription.INSCRIT))
						&& password.equals(entite.getMdp())) {
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
			logger.info("Authentification échouée");
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
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		doGet(request, response);
	}

}
