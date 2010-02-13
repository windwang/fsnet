package fr.univartois.ili.fsnet.fakeDB;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PopulateDB extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		EntityManager em = factory.createEntityManager();
		Instancier instancier = new Instancier(em);
		instancier.start();
		resp.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
