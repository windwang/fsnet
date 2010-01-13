package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Inscription;
import fr.univartois.ili.fsnet.entities.Interet;
import fr.univartois.ili.fsnet.security.Md5;

/**
 * @author lionel desruelles Servlet implementation class CompleteProfil
 */
public class CompleteProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private transient EntityManagerFactory factory;

	private transient EntityManager entM;

	private static final String FIND_INTERET_ID = "SELECT i FROM Interet i WHERE i.id = ?1";

	private static final String FIND_INS_BY_ENT = "SELECT ins FROM Inscription ins WHERE ins.entite =?1";

	private static final Logger logger = Logger.getLogger("FSNet");

	@Override
	public void init() throws ServletException {
		super.init();
		factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
		entM = factory.createEntityManager();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		if (entM != null) {
			entM.close();
		}
		if (factory != null) {
			factory.close();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {

		HttpSession session;
		String nom;
		String prenom;
		String email;
		String dateNaissance;
		String adresse;
		String telephone;
		String profession;
		String pwd1;
		String pwd2;
		String sexe;

		session = request.getSession();
		nom = request.getParameter("nom");
		prenom = request.getParameter("prenom");
		email = request.getParameter("email");
		dateNaissance = request.getParameter("dateNaissance");
		adresse = request.getParameter("adresse");
		telephone = request.getParameter("telephone");
		profession = request.getParameter("profession");
		pwd1 = request.getParameter("pwd1");
		pwd2 = request.getParameter("pwd2");
		sexe = request.getParameter("sexe");

		if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty()
				|| dateNaissance.isEmpty() || adresse.isEmpty()
				|| telephone.isEmpty() || profession.isEmpty()
				|| pwd1.isEmpty() || sexe.isEmpty()) {

			request.setAttribute("nom", nom);
			request.setAttribute("prenom", prenom);
			request.setAttribute("dateNaissance", dateNaissance);
			request.setAttribute("email", email);
			request.setAttribute("adresse", adresse);
			request.setAttribute("telephone", telephone);
			request.setAttribute("profession", profession);
			request.setAttribute("sexe", sexe);
			RequestDispatcher dispatch;
			dispatch = getServletContext().getRequestDispatcher("/profil.jsp");
			dispatch.forward(request, response);

		} else {

			EntiteSociale entite;
			entite = (EntiteSociale) session.getAttribute("entite");
			// entite = em.find(EntiteSociale.class, entite.getId());
			entite = entM.merge(entite);

			List<Interet> lesInterets;
			lesInterets = new ArrayList<Interet>();
			String[] interests = null;
			interests = request.getParameterValues("interestSelected");

			if (interests != null) {
				Query interest;
				int length;
				length = interests.length;
				Interet interet = null;

				for (int i = 0; i < length; i++) {
					logger.info("TEST:id =" + interests[i]);
					interest = entM.createQuery(FIND_INTERET_ID);
					interest.setParameter(1, Integer.parseInt(interests[i]));
					interet = (Interet) interest.getSingleResult();
					lesInterets.add(interet);
				}
			}

			DateFormat formatter;
			formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
			Date date = null;
			try {
				date = (Date) formatter.parse(dateNaissance);
			} catch (ParseException e) {
				logger.info(e.getMessage());
			}

			Query inscrit;
			inscrit = entM.createQuery(FIND_INS_BY_ENT);
			inscrit.setParameter(1, entite);
			Inscription ins;
			ins = (Inscription) inscrit.getSingleResult();
			ins.setEtat();

			entite.setNom(nom);
			entite.setPrenom(prenom);
			entite.setEmail(email);
			entite.setAdresse(adresse);
			entite.setDateNaissance(date);
			entite.setSexe(sexe);

			entite.setNumTel(telephone);
			entite.setProfession(profession);
			entite.setLesinterets(lesInterets);
			entite.setMdp(pwd1);

			entM.getTransaction().begin();
			entM.persist(entite);
			entM.persist(ins);
			entM.getTransaction().commit();

			logger.info("Taille interets : " + entite.getLesinterets().size());

			if (interests != null) {
				logger.info("Nom interets : "
						+ entite.getLesinterets().get(0).getNomInteret());
			}
			session.setAttribute("entite", entite);

			RequestDispatcher dispatch;
			dispatch = getServletContext().getRequestDispatcher("/index.jsp");
			dispatch.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		doGet(request, response);
	}

}
