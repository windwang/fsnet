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

import fr.univartois.ili.fsnet.entities.Interet;

public class AddInterest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private EntityManagerFactory factory;

	private EntityManager em;

	@Override
	public void init() throws ServletException {
		factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
		em = factory.createEntityManager();
	}
	
	@Override
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

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Interet interet = null;
		int length = 0;
		String redirection = request.getParameter("redirection");
		String[] valuesInterests = (String[]) request.getSession()
				.getAttribute("interets[]");

		if (valuesInterests != null) {
			length = valuesInterests.length;
		}

		interet = (Interet) request.getSession().getAttribute("interet");

		persist(interet);

		if (length != 0) {
			for (int i = 0; i < length; i++) {
				if(!valuesInterests[i].isEmpty()){
					interet = new Interet();
					interet.setNomInteret(valuesInterests[i]);
					persist(interet);
				}
			}
		}

		RequestDispatcher disp = getServletContext()
				.getRequestDispatcher(
						"/"
								+ redirection
								+ "?interest=current&showHide=show&deploy=[-]&titleDeploy=R%E9duire la liste");
		disp.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
