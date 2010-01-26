package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
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

import fr.univartois.ili.fsnet.actions.utils.DateUtils;
import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Meeting;

/**
 * Execute CRUD Actions for the entity Event
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class ManageEvents extends MappingDispatchAction implements CrudAction {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");

    @Override
    public ActionForward create(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        DynaActionForm dynaForm = (DynaActionForm) form; 							//NOSONAR
        String eventName = (String) dynaForm.get("eventName");
        String eventDescription = (String) dynaForm.get("eventDescription");
        String eventDate = (String) dynaForm.get("eventDate");

        Date typedEventDate;
        try {
            typedEventDate = DateUtils.format(eventDate);
        } catch (ParseException e) {
            ActionErrors errors = new ActionErrors();
            errors.add("eventDate", new ActionMessage(("event.date.errors")));
            saveErrors(request, errors);
            return mapping.getInputForward();
        }
        EntityManager em = factory.createEntityManager();
        SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
        em.getTransaction().begin();
        //member = em.find(SocialEntity.class, member.getId());
        // TODO !!! date de fin et date de debut !!
        Meeting event = new Meeting(member, eventName, eventDescription, typedEventDate, false, typedEventDate, new Address());

        member.getInteractions().add(event);
        em.persist(event);
        request.setAttribute("event", event);
        em.getTransaction().commit();
        em.close();
        return mapping.findForward("success");
    }

    @Override
    public ActionForward modify(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // TODO code pour la modification

        return null;
    }

    @Override
    public ActionForward delete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm dynaForm = (DynaActionForm) form;								//NOSONAR
        String eventId = (String) dynaForm.get("eventId");

        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        TypedQuery<Meeting> query = em.createQuery(
                "Select e from Meeting e where e.id = :eventId",
                Meeting.class);
        query.setParameter("eventId", Integer.parseInt(eventId));
        Meeting event = query.getSingleResult();
        em.remove(event);
        em.flush();
        em.getTransaction().commit();
        em.close();

        request.setAttribute("event", event);
        return mapping.findForward("success");
    }

    @Override
    public ActionForward search(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm seaarchForm = (DynaActionForm) form; 							//NOSONAR
        String searchString = (String) seaarchForm.get("searchString");

        EntityManager em = factory.createEntityManager();
        List<Meeting> results;
        final TypedQuery<Meeting> query;
        // on empty search return all events
        if ("".equals(searchString)) {
            query = em.createQuery("SELECT e FROM Meeting e",
                    Meeting.class);
        } else {
            query = em.createQuery("SELECT e FROM Meeting e "
                    + "WHERE e.title LIKE :searchString "
                    + "OR e.content LIKE :searchString ", Meeting.class);
            query.setParameter("searchString", "%" + searchString + "%");
        }
        results = query.getResultList();
        if (results.isEmpty()) {
            ActionErrors errors = new ActionErrors();
            errors.add("searchString", new ActionMessage("search.noResults"));
            saveErrors(request, errors);
        }
        em.close();
        request.setAttribute("events", results);
        return mapping.findForward("success");
    }

    @Override
    public ActionForward display(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm dynaForm = (DynaActionForm) form;								//NOSONAR
        String eventId = (String) dynaForm.get("eventId");

        EntityManager em = factory.createEntityManager();
        TypedQuery<Meeting> query = em.createQuery(
                "Select e from Meeting e where e.id = :eventId",
                Meeting.class);
        query.setParameter("eventId", Integer.parseInt(eventId));
        Meeting event = query.getSingleResult();
        em.close();

        request.setAttribute("event", event);
        return mapping.findForward("success");
    }
}
