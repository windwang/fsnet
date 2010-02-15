package fr.univartois.ili.fsnet.auth;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.commons.mail.FSNetMailer;
import fr.univartois.ili.fsnet.commons.mail.Mail;
import fr.univartois.ili.fsnet.commons.security.Encryption;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade;

public class ResetPassword extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
		sb.append("Suite &agrave; votre demande, un nouveau mot de passe vous a &eacute;t&eacute; attribu&eacute; : ");
		sb.append(password);
		sb.append("<br/><br/>");
		sb.append("Cet e-mail vous a &eacute;t&eacute; envoy&eacute; d'une adresse servant uniquement &agrave; exp&eacute;dier des messages.");
		sb.append("Merci de ne pas r&eacute;pondre &agrave; ce message.");
		return sb.toString();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String memberMail = req.getParameter("memberMail");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntityFacade facade = new SocialEntityFacade(em);
		if ((memberMail != null) && (!memberMail.isEmpty())) {
			em.getTransaction().begin();
			SocialEntity se = facade.findByEmail(memberMail);
			if (se != null) {
				resetPassword(se);
				em.merge(se);
				em.getTransaction().commit();
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
