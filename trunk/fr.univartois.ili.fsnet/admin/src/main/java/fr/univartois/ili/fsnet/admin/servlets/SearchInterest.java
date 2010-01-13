package fr.univartois.ili.fsnet.admin.servlets;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchInterest extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private EntityManagerFactory factory;

	@Override
	public void init() throws ServletException {
		factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
		factory.createEntityManager();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String textRecherche = request.getSession().getAttribute("searchText")
				.toString();
		String redirection = request.getSession().getAttribute("redirection")
				.toString();
		request.setAttribute("parametre", textRecherche);
		RequestDispatcher disp = getServletContext().getRequestDispatcher(
				"/" + redirection + "?interet=current&recherche=show");
		disp.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
