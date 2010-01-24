package fr.univartois.ili.fsnet.admin.actions;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.DynaActionForm;

import fr.univartois.ili.fsnet.admin.utils.FSNetConfiguration;
import fr.univartois.ili.fsnet.admin.utils.FSNetMailer;
import fr.univartois.ili.fsnet.admin.utils.Mail;
import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Inscription;

/**
 * This action create and persist a new EntiteSociale
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class AddMember extends Action {

	private static final EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse arg3) throws Exception {
		EntityManager em = factory.createEntityManager();
		// create and persists new EntiteSociale
		DynaActionForm dynaform = (DynaActionForm) form;
		EntiteSociale entite = new EntiteSociale();
		BeanUtils.copyProperties(entite, dynaform);
		Inscription inscription = new Inscription(entite);
		try {
			em.getTransaction().begin();
			em.persist(entite);
			em.persist(inscription);
			em.getTransaction().commit();
			sendConfirmationMail(entite);
		} catch (RollbackException ex) {
			ActionErrors actionErrors = new ActionErrors();
			ActionMessage msg = new ActionMessage("entitie.alreadyExists");
			actionErrors.add("entitie.alreadyExists", msg);
			saveErrors(req, actionErrors);
		}
		em.close();

		// TODO eviter que les informations se reafichent dans le form html si
		// ca a fonctionné
		// TODO gerer autrement les attributs parceque la c'est abusé
		ActionForward forward = new ActionForward(
				mapping.findForward("success").getPath()
						+ "?user=current&showHide=show&deploy=[-]&titleDeploy=R%E9duire la liste");
		return forward;
	}

	/**
	 * Send mails to a list of recipient.
	 * 
	 * @param entite
	 *            the new EntiteSociale
	 * @return true if success false if fail
	 * 
	 * @author Mathieu Boniface < mat.boniface {At} gmail.com >
	 */
	private void sendConfirmationMail(EntiteSociale entite) {
		FSNetConfiguration conf = FSNetConfiguration.getInstance();
		Properties properties = FSNetConfiguration.getInstance()
				.getFSNetConfiguration();
		String fsnetAddress = conf.getFSNetWebAddress();
		String message = createMessageRegistration(entite.getNom(), entite
				.getPrenom(), fsnetAddress);
		// send a mail
		FSNetMailer mailer = FSNetMailer.getInstance();
		Mail mail = mailer.createMail();
		mail.setSubject("Inscription FSNet");
		mail.addRecipient(entite.getEmail());
		mail.setContent(message);
		mailer.sendMail(mail);
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
			String addressFsnet) {
		StringBuilder message = new StringBuilder();
		message.append("Bonjour ").append(nom).append(" ").append(prenom);
		message.append(".\n\n");
		message
				.append("<h2>Vous venez d'être enregistré à FSNet (Firm Social Network).</h2>\n\n");
		message.append("Désormais vous pouvez vous connecter sur le site ");
		message.append(addressFsnet);
		message.append(" .\n\n");
		message
				.append("Pour finaliser votre inscription il suffit de remplir votre profil.\n\n");
		message.append("Merci pour votre inscription.\n\n");
		message
				.append("Cet e-mail vous a été envoyé d'une adresse servant uniquement à expédier des messages. Merci de ne pas répondre à ce message.");
		return message.toString();
	}
}
