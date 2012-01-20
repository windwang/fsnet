package fr.univartois.ili.fsnet.fakeDB.actions;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;

public class CleanEntity extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String entityName = req.getParameter("entity");
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("Select o From "+ entityName + " o" );
		List<Object> objects = q.getResultList();
		for (Object o : objects)  {
			em.remove(o);
		}
		em.getTransaction().commit();
		em.close();
		resp.sendRedirect("index.jsp");
	}

}
