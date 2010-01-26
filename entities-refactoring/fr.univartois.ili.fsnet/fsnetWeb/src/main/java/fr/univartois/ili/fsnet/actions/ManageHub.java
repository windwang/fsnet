package fr.univartois.ili.fsnet.actions;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.TopicMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Cerelia Besnainou et Audrey Ruellan
 */
public class ManageHub extends MappingDispatchAction implements CrudAction {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");
    private static final Logger logger = Logger.getAnonymousLogger();

    @Override
    public ActionForward create(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm dynaForm = (DynaActionForm) form;						//NOSONAR
        String hubName = (String) dynaForm.get("hubName");

        logger.info("new hub: " + hubName);
        EntityManager em = factory.createEntityManager();
        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);


        Hub hub = new Hub(null, user, hubName);
        SocialEntity es = UserUtils.getAuthenticatedUser(request, em);
        em.getTransaction().begin();
        em.persist(hub);
        em.getTransaction().commit();
        em.close();

        return mapping.findForward("success");
    }

    @Override
    public ActionForward modify(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActionForward delete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String hubId = request.getParameter("hubId");

        logger.info("delete hub: " + hubId);

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Hub hub WHERE hub.id = :hubId ").setParameter("hubId", Integer.parseInt(hubId)).executeUpdate();
        em.getTransaction().commit();
        em.close();
        return mapping.findForward("success");
    }

    @Override
    public ActionForward search(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        DynaActionForm dynaForm = (DynaActionForm) form;						//NOSONAR
        String hubName;
        if (form == null) {
            hubName = "";
        } else {
            hubName = (String) dynaForm.get("hubName");
        }

        logger.info("search hub: " + hubName);

        EntityManager em = factory.createEntityManager();

        List<Hub> result = em.createQuery(
                "SELECT hub FROM Hub hub WHERE hub.title LIKE :hubName ").setParameter("hubName", "%" + hubName + "%").getResultList();
        em.close();
        request.setAttribute("hubResults", result);

        return mapping.findForward("success");
    }

    @Override
    public ActionForward display(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String hubId = (String) request.getParameter("hubId");

        logger.info("display hub: " + hubId);

        Map<Topic, Message> topicsLastMessage = new HashMap<Topic, Message>();
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Hub result = (Hub) em.createQuery(
                "SELECT hub FROM Hub hub WHERE hub.id = :hubId").setParameter(
                "hubId", Integer.parseInt(hubId)).getSingleResult();

        for (Topic t : result.getTopics()) {
            List<TopicMessage> messages = t.getMessages();
            Message lastMessage = null;
            if (messages.size() > 0) {
                lastMessage = messages.get(messages.size() - 1);
            }
            topicsLastMessage.put(t, lastMessage);
        }
        em.getTransaction().commit();
        em.close();
        request.setAttribute("hubResult", result);
        request.setAttribute("topicsLastMessage", topicsLastMessage);
        return mapping.findForward("success");
    }
}
