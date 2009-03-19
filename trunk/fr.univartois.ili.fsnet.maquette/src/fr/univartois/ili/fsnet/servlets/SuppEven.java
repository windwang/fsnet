package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.Manifestation;

/**
 * Servlet implementation class SuppEven
 */
public class SuppEven extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SuppEven() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int idEven = Integer.valueOf(request.getParameter("idEven"));
		int identite = Integer.valueOf(request.getParameter("idEntite"));

		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("fsnetjpa");
		EntityManager em = factory.createEntityManager();
		Manifestation manif = em.getReference(Manifestation.class, Integer
				.valueOf(idEven));

		if (manif.getCreateur().getId() == identite) {
			manif.setVisible("N");
			em.getTransaction().begin();
			em.merge(manif);
			em.getTransaction().commit();
			// IliForumFacade.getInstance().removeHub( manif);
		} else {
		}

		RequestDispatcher dispa = getServletContext().getRequestDispatcher(
				"/toutEvenement.jsp");
		dispa.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(request, response);
	}

}
