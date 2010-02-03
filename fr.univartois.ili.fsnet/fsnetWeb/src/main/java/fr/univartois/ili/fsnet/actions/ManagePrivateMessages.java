package fr.univartois.ili.fsnet.actions;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.entities.PrivateMessage;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.PrivateMessageFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class ManagePrivateMessages extends MappingDispatchAction implements CrudAction {

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");

    @Override
    public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
        String to = dynaForm.getString("messageTo");
        String subject = dynaForm.getString("messageSubject");
        String body = dynaForm.getString("messageBody");

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(request, em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity findByEmail = sef.findByEmail(to);
        if (findByEmail != null) {
            PrivateMessageFacade pmf = new PrivateMessageFacade(em);
            pmf.sendPrivateMessage(body, authenticatedUser, subject, findByEmail);
            em.getTransaction().commit();

            em.close();

            return mapping.findForward("success");
        } else {
            // TODO !!! GERER ERREUR
            ActionErrors errors = new ActionErrors();
            errors.add("messageTo", new ActionMessage(("privatemessages.to.error")));
            saveErrors(request, errors);
            em.close();
            return mapping.getInputForward();
        }
    }

    @Override
    public ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(request, em);
        if (form == null) {
            request.setAttribute("messages", authenticatedUser.getReceivedPrivateMessages());
        } else {
            // TODO
        }
        return mapping.findForward("success");
    }

    @Override
    public ActionForward display(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
