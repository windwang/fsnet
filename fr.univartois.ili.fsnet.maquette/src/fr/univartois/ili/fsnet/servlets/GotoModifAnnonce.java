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

import fr.univartois.ili.fsnet.entities.Annonce;

/**
 * Servlet implementation class ModifEven1
 */
public class GotoModifAnnonce extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GotoModifAnnonce() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("fsnetjpa");
		EntityManager em = factory.createEntityManager();
		String id = request.getParameter("idChoisi");
		System.out.println("               id annonce " + id);
		Annonce ann = em.getReference(Annonce.class, Integer.valueOf(request
				.getParameter("idChoisi")));
		getServletContext().setAttribute("annonce", ann);

		RequestDispatcher dispa = getServletContext().getRequestDispatcher(
				"/modifAnnonce.jsp");
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
