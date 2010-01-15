package fr.univartois.ili.fsnet.admin.actions;

import fr.univartois.ili.fsnet.admin.ParserFileConfig;
import fr.univartois.ili.fsnet.admin.SendMail;
import fr.univartois.ili.fsnet.admin.servlets.SearchFileConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Inscription;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * This action create and persist a new EntiteSociale
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class ActionUser extends Action {

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");
    private EntityManager em;
    private static final Logger log = Logger.getLogger(ActionUser.class.getName());

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse arg3) throws Exception {
        em = factory.createEntityManager();
        // create and persists new EntiteSociale
        DynaActionForm dynaform = (DynaActionForm) form;
        EntiteSociale entite = new EntiteSociale();
        BeanUtils.copyProperties(entite, dynaform);

        // TODO verifier les exceptions levés par rapport aux erreurs possibles (meme email.. ?)
        try {
            em.getTransaction().begin();
            em.persist(entite);
            em.getTransaction().commit();
        } catch (RollbackException ex) {
            ActionErrors actionErrors = new ActionErrors();
            ActionMessage msg = new ActionMessage(
                    "Impossible de persister l'entité, cet email est déjà dans la base.");
            actionErrors.add("entitie.alreadyExists", msg);
            saveErrors(req, actionErrors);
        }
        Inscription inscription = new Inscription(entite);
        em.getTransaction().begin();
        em.persist(inscription);
        em.getTransaction().commit();

        em.close();

        // TODO gerer le sendmail
        //sendMails(entite);
        // TODO ajouter un forward dans la conf
        req.setAttribute("user", "current");
        req.setAttribute("showHide", "show");
        req.setAttribute("deploy", "[-]");
        req.setAttribute("titleDeploy", "R%E9duire la liste");
        // TODO eviter que les informations se reafichent dans
        return mapping.findForward("success");
    }

    /**
     * Send mails to a list of recipient.
     * @param entite
     * @return
     * 
     */
    private boolean sendMails(EntiteSociale entite) {
        // Send mail
        String email = entite.getEmail();
        List<String> listeDest = new ArrayList<String>();
        listeDest.add(email);

        // TODO on va mettre un properties ici
        File file = new File(SearchFileConfig.FILE_PATH);
        ParserFileConfig parser = new ParserFileConfig(file);
        String[] parameters = parser.parse();

        String message = createMessageRegistration(entite.getNom(),
                entite.getPrenom(), email, parameters[3]);
        SendMail envMail = new SendMail(parameters[0], parameters[1], parameters[2], parameters[4]);
        try {
            envMail.sendMessage(listeDest, "Inscription FSNet", message);
            log.log(Level.INFO, "Message sent to " + listeDest);
            return true;
        } catch (MessagingException e) {
            log.log(Level.WARNING, "Error when sending a mail: ", e);
            return false;
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
        message.append("Bonjour ").append(nom).append(" ").append(prenom);
        message.append(".\n\n");
        message.append("Vous venez d'être enregistré à FSNet (Firm Social Network).\n\n");
        message.append("Désormais vous pouvez vous connecter sur le site ");
        message.append(addressFsnet);
        message.append(". Pour celà renseignez uniquement votre adresse email ");
        message.append(email);
        message.append(" .\n\n");
        message.append("Pour finaliser votre inscription il suffit de remplir votre profil.\n\n");
        message.append("Merci pour votre inscription.\n\n");
        message.append("Cet e-mail vous a été envoyé d'une adresse servant uniquement à expédier des messages. Merci de ne pas répondre à ce message.");
        return message.toString();
    }
}
