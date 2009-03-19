package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.Manifestation;

/**
 * Servlet implementation class ModifEven2
 */
public class ModifEven2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifEven2() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// String date = request.getParameter("dateDebut");
		Manifestation manif1 = (Manifestation) getServletContext()
				.getAttribute("manif");
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"
				+ manif1.getId());
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("fsnetjpa");
		EntityManager em = factory.createEntityManager();
		Manifestation manif = em.getReference(Manifestation.class, manif1
				.getId());
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&IDDDDDDDDDDDDDDDD "
				+ manif.getId());
		System.out.println("&&&&&&&&&&&&&&&&&&&&NNNNNNNNNNNNNNNNNNNNNNNN "
				+ manif.getNom());
		// manif.setContenu("contenu");
		String titre = request.getParameter("titreEvenement");
		String contenu = request.getParameter("contenuEvenement");
		String date = request.getParameter("dateDebut");
		Date dateDebut = null;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			dateDebut = (Date) formatter.parse(date);
			System.out.println("date format " + date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		manif.setdateManifestation(dateDebut);
		manif.setContenu(contenu);
		manif.setNom(titre);
		// manif.setDateInformation("date");
		em.getTransaction().begin();
		em.merge(manif);
		em.getTransaction().commit();
		RequestDispatcher dispa = getServletContext().getRequestDispatcher(
				"/toutEvenement.jsp");
		dispa.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(request, response);
	}

}
