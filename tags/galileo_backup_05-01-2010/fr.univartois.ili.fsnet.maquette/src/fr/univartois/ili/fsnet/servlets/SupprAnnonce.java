package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.Annonce;

public class SupprAnnonce extends HttpServlet {

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
		// TODO Auto-generated method stub
		String ident;
		ident = request.getParameter("idChoisi");

		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory("fsnetjpa");
		EntityManager entM2;
		entM2 = factory.createEntityManager();
		Annonce monAnn;
		monAnn = entM2.getReference(Annonce.class, Integer.valueOf(ident));

		Annonce hubMerge;
		entM2.getTransaction().begin();
		hubMerge = entM2.merge(monAnn);
		hubMerge.setVisible("N");
		entM2.persist(hubMerge);
		entM2.getTransaction().commit();

		request
				.setAttribute("info",
						"<p id=\"info\">La suppression d'une annonce est effectuée.</p>");

		RequestDispatcher dispatch;
		dispatch = getServletContext().getRequestDispatcher("/annonces.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		// TODO Auto-generated method stub

	}
}
