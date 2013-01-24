package fr.univartois.ili.fsnet.admin.utils;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;

/**
 * Servlet implementation class InsertSocialElement
 */

@WebServlet(displayName="InsertSocialElement", urlPatterns = {"/InsertSocialElement"})
public class InsertSocialElement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InsertSocialElement() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery(
				"INSERT INTO SOCIALELEMENT(ID,DTYPE) SELECT ID, 'E' from SOCIALENTITY")
				.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}