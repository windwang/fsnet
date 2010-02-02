package fr.univartois.ili.fsnet.auth;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.SocialEntity;

public class ResetPassword extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("fsnetjpa");

	public void resetPassword(SocialEntity se) {
		// TODO change the password
		// TODO send a mail
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String memberMail = req.getParameter("memberMail");
		EntityManager em = emf.createEntityManager();
		if ((memberMail != null) && (!memberMail.isEmpty())) {
			try {
				em.getTransaction().begin();
				Query query = em
						.createQuery("Select es from SocialEntity es where es.email = :memberMail");
				query.setParameter("memberMail", memberMail);
				SocialEntity se = (SocialEntity) query.getSingleResult();
				resetPassword(se);
				em.merge(se);
				em.getTransaction().commit();
			} catch (Exception e) {
			}

		}
		em.close();
		req.setAttribute("loginMessage", "login.3");
		RequestDispatcher dispatcher = req
				.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
