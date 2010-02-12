package fr.univartois.ili.fsnet.fakeDB;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.fakeDB.mocks.MathieuBonifaceMock;

public class PopulateDB extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getAnonymousLogger();

	private static final EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");

	private void loadMock(Class<? extends Mock> clazz) {
		EntityManager em = factory.createEntityManager();
		try {
			Mock mock;
			mock = clazz.newInstance();
			mock.populate(em);
		} catch (SecurityException e) {
			logger.log(Level.SEVERE, "", e);
		} catch (InstantiationException e) {
			logger.log(Level.SEVERE, "", e);
		} catch (IllegalAccessException e) {
			logger.log(Level.SEVERE, "", e);
		} finally {
			em.close();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		loadMock(MathieuBonifaceMock.class);
		resp.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
