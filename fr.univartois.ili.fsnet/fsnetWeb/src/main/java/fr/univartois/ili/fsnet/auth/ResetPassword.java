package fr.univartois.ili.fsnet.auth;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.commons.mail.FSNetMailer;
import fr.univartois.ili.fsnet.commons.mail.Mail;
import fr.univartois.ili.fsnet.commons.security.Encryption;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade;

public class ResetPassword extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("fsnetjpa");

	public void resetPassword(SocialEntity se) {
		String generatedPassword = Encryption.generateRandomPassword();
		String message = createMessage(generatedPassword);
		FSNetMailer mailer = FSNetMailer.getInstance();
		Mail mail = mailer.createMail();
		mail.addRecipient(se.getEmail());
		mail.setContent(message);
		se.setPassword(Encryption.getEncodedPassword(generatedPassword));
		mail.setSubject("Génération d'un nouveau mot de passe pour FSNet");
		mailer.sendMail(mail);
	}

	private String createMessage(String password) {
		StringBuffer sb = new StringBuffer();
		sb.append("Bonjour, <br/><br/>");
		sb
				.append("Suite à votre demande, un nouveau mot de passe vous a été attribué : ");
		sb.append(password);
		sb.append("<br/><br/>");
		sb
				.append("Cet e-mail vous a été envoyé d'une adresse servant uniquement à expédier des messages.");
		sb.append("Merci de ne pas répondre à ce message.");
		return sb.toString();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String memberMail = req.getParameter("memberMail");
		EntityManager em = emf.createEntityManager();
		SocialEntityFacade facade = new SocialEntityFacade(em);
		if ((memberMail != null) && (!memberMail.isEmpty())) {
			em.getTransaction().begin();
			SocialEntity se = facade.findByEmail(memberMail);
			if (se != null) {
				resetPassword(se);
				em.merge(se);
				em.getTransaction().commit();
				Logger.getAnonymousLogger().severe("Exception ok");
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
