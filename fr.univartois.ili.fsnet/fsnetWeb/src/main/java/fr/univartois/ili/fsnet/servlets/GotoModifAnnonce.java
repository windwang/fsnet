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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory("fsnetjpa");
		EntityManager entM;
		entM = factory.createEntityManager();
		String ident;
		ident = request.getParameter("idChoisi");
		Annonce ann;
		ann = entM.getReference(Annonce.class, Integer.valueOf(ident));
		getServletContext().setAttribute("annonce", ann);

		RequestDispatcher dispa;
		dispa = getServletContext().getRequestDispatcher("/modifAnnonce.jsp");
		dispa.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		super.doPost(request, response);
	}

}
