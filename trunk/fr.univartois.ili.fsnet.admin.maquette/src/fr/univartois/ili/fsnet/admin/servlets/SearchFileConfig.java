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

	public static final String FILE_PATH = System.getProperty("user.home")
			+ ".fsadminrc";

	public static final String HOME = "/index.jsp?accueil=current";

	public static final String OPTIONS = "/options.jsp?options=current";

	private String redirection = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchFileConfig() {
		super();
	}

	@Override
	public void init() throws ServletException {
		searchFile();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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

	private void searchFile() {

		File file = new File(FILE_PATH);
		if (!file.exists()) {
			redirection = OPTIONS;
		} else {
			redirection = HOME;
		}
	}

}
