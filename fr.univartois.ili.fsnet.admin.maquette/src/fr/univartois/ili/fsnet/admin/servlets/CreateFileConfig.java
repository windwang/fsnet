package fr.univartois.ili.fsnet.admin.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateFileConfig
 */
public class CreateFileConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DIR_PATH = System.getenv("HOME") + "/FSNADMIN";

	private static final String FILE_PATH = DIR_PATH + "/admin.conf";

	private static final String HOME = "/index.jsp?accueil=current";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateFileConfig() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String smtpserver = request.getSession().getAttribute("serveursmtp")
				.toString();
		String host = request.getSession().getAttribute("hote").toString();
		String pwdhost = request.getSession().getAttribute("motdepasse")
				.toString();
		String addressfsnet = request.getSession().getAttribute("adressefsnet")
				.toString();
		String port = request.getSession().getAttribute("port").toString();

		File file = new File(FILE_PATH);
		if (!file.exists()) {
			File fb = new File(DIR_PATH);
			if (!fb.exists()) {
				fb.mkdirs();
			}
			file.createNewFile();
		}

		completeFile(smtpserver, host, pwdhost, addressfsnet, port, file);

		RequestDispatcher dp = getServletContext().getRequestDispatcher(HOME);
		dp.forward(request, response);

	}

	private void completeFile(String smtpserver, String host, String pwdhost,
			String addressfsnet, String port, File file) {
		try {
			PrintWriter writer = new PrintWriter(new BufferedWriter(
					new FileWriter(file)));
			StringBuilder sb = new StringBuilder();
			sb.append(smtpserver);
			sb.append(" ");
			sb.append(host);
			sb.append(" ");
			sb.append(pwdhost);
			sb.append(" ");
			sb.append(addressfsnet);
			sb.append(" ");
			sb.append(port);
			writer.println(sb.toString());
			writer.close();
		} catch (IOException e) {
			log(e.getMessage());
		}

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
