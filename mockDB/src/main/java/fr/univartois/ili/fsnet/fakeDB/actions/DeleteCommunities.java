package fr.univartois.ili.fsnet.fakeDB.actions;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Community;

public class DeleteCommunities extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		EntityManager em = PersistenceProvider.createEntityManager();
		List<Community> communities;
		//Fetch all communities in a transaction 
		em.getTransaction().begin();
		TypedQuery<Community> q = em.createQuery("Select c From Community c", Community.class);
		communities = q.getResultList();
		
		for (Community c : communities) {
			c.getHubs();
			em.remove(c);
			em.flush();
		}
		em.getTransaction().commit();

		em.close();
		resp.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
