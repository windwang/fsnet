package fr.univartois.ili.fsnet.admin.servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.admin.ParserFileConfig;
import fr.univartois.ili.fsnet.admin.SendMail;
import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Inscription;

/**
 * @author romuald druelle. Servlet implementation class AddUser
 */
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private static final String SUBJECT = "Inscription FSNet";

	private EntityManagerFactory factory;

	private EntityManager em;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddUser() {
		super();
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
	 * Method that creates an email address from the concatenation of name and
	 * surname of a social entity.
	 * 
	 * @param nom
	 * @param prenom
	 * @return the created email address .
	 */
	public String createEmail(String nom, String prenom) {
		StringBuilder builder = new StringBuilder();
		builder.append(nom);
		builder.append("@");
		builder.append(prenom);
		builder.append(".net");
		return builder.toString();
	}

	/**
	 * Method that creates an welcome message to FSNet.
	 * 
	 * @param nom
	 * @param prenom
	 * @param email
	 * @return the message .
	 */
	private String createMessageRegistration(String nom, String prenom,
			String email, String addressFsnet) {
		StringBuilder message = new StringBuilder();
		message.append("Bonjour ");
		message.append(nom);
		message.append(" ");
		message.append(prenom);
		message.append(".\n\n");
		message
				.append("Vous venez d'être enregistré à FSNet (Firm Social Network).\n\n");
		message
				.append("Désormais vous pouvez vous connecter sur le site ");
		message.append(addressFsnet);
		message.append(". Pour celà renseignez uniquement votre adresse email ");
		message.append(email);
		message.append(" .\n\n");
		message
				.append("Pour finaliser votre inscription il suffit de remplir votre profil.\n\n");
		message.append("Merci pour votre inscription.\n\n");
		message
				.append("Cet e-mail vous a été envoyé d'une adresse servant uniquement à expédier des messages. Merci de ne pas répondre à ce message.");
		return message.toString();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * String nom = request.getParameter("Nom"); String prenom =
		 * request.getParameter("Prenom"); // String email = createEmail(nom,
		 * prenom); String email = request.getParameter("Email"); EntiteSociale
		 * entite = new EntiteSociale(nom, prenom, email);
		 */
		String redirection = request.getParameter("redirection");
		EntiteSociale entite = (EntiteSociale) request.getSession()
				.getAttribute("entitesociale");
		String email = entite.getEmail();
		SendMail envMail = null;
		List<String> listeDest = new ArrayList<String>();
		String message = null;
		File file = new File(SearchFileConfig.FILE_PATH);
		ParserFileConfig parser = null;
		String[] parameters = null;

		em.getTransaction().begin();
		em.persist(entite);
		em.getTransaction().commit();

		Inscription inscription = new Inscription(entite);
		em.getTransaction().begin();
		em.persist(inscription);
		em.getTransaction().commit();

		listeDest.add(email);
		parser = new ParserFileConfig(file);
		parameters = parser.parse();
		message = createMessageRegistration(entite.getNom(),
				entite.getPrenom(), email, parameters[3]);
		envMail = new SendMail(parameters[0],parameters[1],parameters[2],parameters[4]);
		try {
			envMail.sendMessage(listeDest, SUBJECT, message);
			log("Message envoyé");
		} catch (MessagingException e) {
			log("Erreur : " + e.getMessage());
		}
		RequestDispatcher disp = getServletContext()
				.getRequestDispatcher(
						"/"
								+ redirection
								+ "?user=current&showHide=show&deploy=[-]&titleDeploy=R%E9duire la liste");
		disp.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
