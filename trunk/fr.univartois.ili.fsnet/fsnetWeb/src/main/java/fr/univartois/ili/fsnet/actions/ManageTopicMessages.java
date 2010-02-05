package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.TopicMessage;
import fr.univartois.ili.fsnet.facade.forum.iliforum.TopicFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.TopicMessageFacade;

/**
 *
 * @author Cerelia Besnainou and Audrey Ruellan
 */
public class ManageTopicMessages extends MappingDispatchAction implements CrudAction {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");
    private static final Logger logger = Logger.getAnonymousLogger();

    @Override
    public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("create Message: ");
        EntityManager em = factory.createEntityManager();
        DynaActionForm dynaForm = (DynaActionForm) form;						//NOSONAR
        String messageDescription = (String) dynaForm.get("messageDescription");
        int topicId = Integer.valueOf(Integer.parseInt(dynaForm.getString("topicId")));
        
        TopicFacade topicFacade = new TopicFacade(em);
        Topic topic = topicFacade.getTopic(topicId);

        SocialEntity SocialEntity = UserUtils.getAuthenticatedUser(request, em);
        TopicMessageFacade topicMessageFacade = new TopicMessageFacade(em);
        TopicMessage message = topicMessageFacade.createTopicMessage(messageDescription, SocialEntity, topic);

        em.getTransaction().begin();
        topic.getMessages().add(message);
        em.getTransaction().commit();

        em.close();
        return mapping.findForward("success");
    }

    @Override
    public ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        logger.info("modify Message ");
        EntityManager em = factory.createEntityManager();
        DynaActionForm dynaForm = (DynaActionForm) form;						//NOSONAR
        String messageDescription = (String) dynaForm.get("messageDescription");
        int messageId = Integer.valueOf(Integer.parseInt(dynaForm.getString("messageId")));
        TopicMessageFacade topicMessageFacade = new TopicMessageFacade(em);
        TopicMessage message = topicMessageFacade.getTopicMessage(messageId);
        message.setBody(messageDescription);
        int topicId = Integer.valueOf(Integer.parseInt(dynaForm.getString("topicId")));
        TopicFacade topicFacade = new TopicFacade(em);
        Topic topic = topicFacade.getTopic(topicId);

        em.getTransaction().begin();
        em.merge(message);
        topic.getMessages();
        em.getTransaction().commit();
        em.close();
        return mapping.findForward("success");
    }

    @Override
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        int topicId = Integer.valueOf(request.getParameter("topicId"));
        TopicFacade topicFacade = new TopicFacade(em);
        Topic topic = topicFacade.getTopic(topicId);
        int messageId = Integer.valueOf(Integer.parseInt(request.getParameter("messageId")));
        TopicMessageFacade topicMessageFacade = new TopicMessageFacade(em);
        TopicMessage message = topicMessageFacade.getTopicMessage(messageId);
        em.getTransaction().begin();
        Query query = em.createQuery("DELETE FROM TopicMessage message WHERE message.id = :messageId");
        query.setParameter("messageId", messageId);
        query.executeUpdate();

        topic.getMessages().remove(message);
        em.getTransaction().commit();
        em.close();
        return mapping.findForward("success");
    }

    @Override
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActionForward display(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("display Message: ");
        String topicId = request.getParameter("topicId");
        request.setAttribute("topicId", topicId);
        String messageId = request.getParameter("messageId");

        if (messageId != null) {
            EntityManager em = factory.createEntityManager();
            TopicMessageFacade topicMessageFacade = new TopicMessageFacade(em);
            TopicMessage message = topicMessageFacade.getTopicMessage(Integer.parseInt(messageId));
            request.setAttribute("message", message);
        }

        return mapping.findForward("success");
    }
}
