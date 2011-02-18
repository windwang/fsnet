package fr.univartois.ili.fsnet.fakeDB.actions;

import java.io.IOException;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayEntities extends HttpServlet {

	private static final EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		EntityManager em = factory.createEntityManager();
		em.close();
		Metamodel metamodel = factory.getMetamodel();
		if (metamodel != null) {
			Set<EntityType<?>> entities = metamodel.getEntities();
			req.setAttribute("entities", entities);
		} else {
			log("Metamodel == null");
		}
		RequestDispatcher rd = req.getRequestDispatcher("/DisplayEntities.jsp");
		rd.forward(req, resp);
	}
	
}
 