package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.entities.PrivateMessage;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.PrivateMessageFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade;

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
        EntityManager em = factory.createEntityManager();
        try {
            if (form != null) {
                DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
                // NOSONAR
                int messageId = Integer.parseInt(dynaForm.getString("messageId"));
                SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(request, em);

                PrivateMessageFacade pmf = new PrivateMessageFacade(em);
                PrivateMessage privateMessage = pmf.getPrivateMessage(messageId);
                em.getTransaction().begin();
                pmf.deletePrivateMessage(authenticatedUser, privateMessage);
                em.getTransaction().commit();
                em.close();
                return mapping.findForward("success");
            } else {
                ActionErrors errors = new ActionErrors();
                errors.add("messageTo", new ActionMessage(("privatemessages.not.owner")));
                saveErrors(request, errors);


            }

        } catch (NumberFormatException e) {
        }
        em.close();
        // TODO errors
        return mapping.findForward("fail");
    }

    public ActionForward inbox(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(request, em);
        if (form == null) {
            List<PrivateMessage> userMessages = new ArrayList<PrivateMessage>(authenticatedUser.getReceivedPrivateMessages());
            Collections.reverse(userMessages);
            request.setAttribute("messages", userMessages);

        } else {
            // TODO
        }
        em.close();
        return mapping.findForward("success");
    }

    public ActionForward outbox(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(request, em);
        if (form == null) {
            List<PrivateMessage> userMessages = new ArrayList<PrivateMessage>(authenticatedUser.getSentPrivateMessages());
            Collections.reverse(userMessages);
            request.setAttribute("messages", userMessages);

        } else {
            // TODO
        }
        em.close();
        return mapping.findForward("success");
    }

    @Override
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActionForward display(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        EntityManager em = factory.createEntityManager();
        try {
            if (form != null) {
                DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
                int messageId = Integer.parseInt(dynaForm.getString("messageId"));
                SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(request, em);
                PrivateMessageFacade pmf = new PrivateMessageFacade(em);
                PrivateMessage privateMessage = pmf.getPrivateMessage(messageId);
                if (privateMessage != null
                        && (authenticatedUser.equals(privateMessage.getFrom())
                        || authenticatedUser.equals(privateMessage.getTo()))) {
                    if (authenticatedUser.equals(privateMessage.getTo())) {
                        em.getTransaction().begin();
                        privateMessage.setReed(true);
                        em.getTransaction().commit();
                    }
                    em.close();
                    request.setAttribute("theMessage", privateMessage);
                    return mapping.findForward("success");
                } else {
                    // TODO error try to display message but not owner 
                }
            }
        } catch (NumberFormatException e) {
        }
        em.close();
        // TODO errors
        return mapping.findForward("fail");
    }
}
