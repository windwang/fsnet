package fr.univartois.ili.fsnet.fakeDB.actions;

import java.io.IOException;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;

public class DisplayEntities extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9143764704421439778L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		EntityManager em = PersistenceProvider.createEntityManager();
		em.close();
		Metamodel metamodel = PersistenceProvider.getMetamodel();
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
 