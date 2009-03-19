package fr.univartois.ili.fsnet.admin.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateFileConfig
 */
public class SearchFileConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String FILE_PATH = System.getenv("HOME")
			+ "/FSNADMIN/admin.conf";

	private static final String HOME = "/index.jsp?accueil=current";

	private static final String OPTIONS = "/options.jsp?options=current";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchFileConfig() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String redirection = null;
		File file = new File(FILE_PATH);
		if (!file.exists()) {
			redirection = OPTIONS;
		} else {
			redirection = HOME;
		}
		RequestDispatcher disp = getServletContext().getRequestDispatcher(
				redirection);
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
