package fr.univartois.ili.fsnet.auth;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;

/**
 * Servlet implementation class DropConsultationDatabase
 */
public class DropConsultationDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DropConsultationDatabase() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("DROP TABLE Consultation").executeUpdate();
		em.createNativeQuery("DROP TABLE ConsultationChoice").executeUpdate();
		em.createNativeQuery("DROP TABLE ConsultationChoiceVote").executeUpdate();
		em.createNativeQuery("DROP TABLE ConsultationVote").executeUpdate();
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
