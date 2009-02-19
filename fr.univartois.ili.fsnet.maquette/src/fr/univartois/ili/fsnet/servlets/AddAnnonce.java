package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.*;

/**
 * author jerome bouwy
 * Servlet implementation class AddAnnonce
 */
public class AddAnnonce extends HttpServlet {
	private static Logger logger = Logger.getLogger("FSNet");

	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private EntityManagerFactory factory;

	private EntityManager em;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddAnnonce() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
	public void init() throws ServletException {
		super.init();
		factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
		em = factory.createEntityManager();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		if (em != null) {
			em.close();
		}
		if (factory != null) {
			factory.close();
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String titre = request.getParameter("titreAnnonce");
		String contenu = request.getParameter("contenuAnnonce");
		String dateFin = request.getParameter("dateFinAnnonce");
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date = null;
		try {
			date = (Date)formatter.parse(dateFin);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Test aDD ANNONCE "+titre+" "+contenu +" "+(new Date().toString()));
		
		
		Annonce nouvelleInfo = new Annonce(titre, new Date(), contenu, date);
		em.getTransaction().begin();
		em.persist(nouvelleInfo);
		em.getTransaction().commit();
		
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/annonces.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
