package fr.univartois.ili.fsnet.admin.servlets;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchMember
 */
public class SearchMember extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private EntityManagerFactory factory;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchMember() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
		factory.createEntityManager();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String filtreRecherche = request.getSession().getAttribute(
				"selectRecherche").toString();
		String textRecherche;
		textRecherche = request.getSession().getAttribute("searchText")
				.toString();
		String redirection = request.getSession().getAttribute("redirection")
				.toString();

		request.setAttribute("filtre", filtreRecherche);
		request.setAttribute("parametre", textRecherche);
		RequestDispatcher disp = getServletContext().getRequestDispatcher(
				"/" + redirection + "?user=current&recherche=show");
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
