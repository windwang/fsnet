package fr.univartois.ili.fsnet.actions;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import java.io.IOException;
import java.util.List;

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

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * 
 * @author Grioche Legrand
 */
public class ManageContacts extends MappingDispatchAction implements CrudAction {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");

    public ActionForward ask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        List<EntiteSociale> listEntities = em.createQuery(
                "select entite from EntiteSociale entite").getResultList();
        request.setAttribute("listEntities", listEntities);
        em.close();
        return mapping.findForward("success");
    }

    /**
     * Submit a request contact to another social entity
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward askContact(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm dynaForm = (DynaActionForm) form;
        EntityManager em = factory.createEntityManager();
        EntiteSociale user = UserUtils.getAuthenticatedUser(request, em);
        final String idString = (String) dynaForm.get("entitySelected");
        int entitySelected = Integer.parseInt(idString);

        ActionErrors errors = new ActionErrors();
        if (entitySelected != user.getId()) {
            em.getTransaction().begin();
            
            // TODO changer les listes en set sur les entites sociales pour eviter
            // les doublons
            EntiteSociale entity = em.find(EntiteSociale.class, entitySelected);
            user.getRequested().add(entity);
            entity.getAsked().add(user);
            em.getTransaction().commit();
            em.close();
        } else {
            errors.add("entitySelected", new ActionMessage("contacts.error.askself"));
        }
        return mapping.findForward("success");
    }

    /**
     * To accept a contact request
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward accept(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm dynaForm = (DynaActionForm) form;
        EntityManager em = factory.createEntityManager();
        EntiteSociale user = UserUtils.getAuthenticatedUser(request, em);
        final String idString = (String) dynaForm.get("entityAccepted");
        int id = Integer.parseInt(idString);
        EntiteSociale entityAccepted = em.find(EntiteSociale.class, id);
        em.getTransaction().begin();
        user.getContacts().add(entityAccepted);
        entityAccepted.getContacts().add(user);
        user.getAsked().remove(entityAccepted);
        entityAccepted.getRequested().remove(user);
        em.merge(user);
        em.merge(entityAccepted);
        em.getTransaction().commit();
        return mapping.findForward("success");
    }

    /**
     * To refuse a contact request
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward refuse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        DynaActionForm dynaForm = (DynaActionForm) form;
        EntityManager em = factory.createEntityManager();
        EntiteSociale user = UserUtils.getAuthenticatedUser(request, em);
        final String idString = (String) dynaForm.get("entityRefused");
        int id = Integer.parseInt(idString);
        EntiteSociale entityRefused = em.find(EntiteSociale.class, id);
        em.getTransaction().begin();
        user.getAsked().remove(entityRefused);
        entityRefused.getRequested().remove(user);
        em.merge(user);
        em.merge(entityRefused);
        em.getTransaction().commit();
        return mapping.findForward("success");
    }

    /**
     * Display the list of asked contacts
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward displayRequests(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        EntityManager em = factory.createEntityManager();
        EntiteSociale user = UserUtils.getAuthenticatedUser(request, em);
        List<EntiteSociale> listRequests = user.getRequested();
        em.close();
        request.setAttribute("listRequests", listRequests);
        return mapping.findForward("success");
    }

    /**
     * Display the list of received demands
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward displayDemands(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        EntityManager em = factory.createEntityManager();
        EntiteSociale user = UserUtils.getAuthenticatedUser(request, em);
        List<EntiteSociale> listDemands = user.getAsked();
        em.close();
        request.setAttribute("listDemands", listDemands);
        return mapping.findForward("success");
    }

    @Override
    public ActionForward create(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
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
        DynaActionForm dynaForm = (DynaActionForm) form;
        EntityManager em = factory.createEntityManager();
        EntiteSociale user = UserUtils.getAuthenticatedUser(request, em);
        final String idString = (String) dynaForm.get("entityDeleted");
        int id = Integer.parseInt(idString);
        EntiteSociale entityDeleted = em.find(EntiteSociale.class, id);
        em.getTransaction().begin();
        user.getContacts().remove(entityDeleted);
        entityDeleted.getContacts().remove(user);
        em.merge(user);
        em.merge(entityDeleted);
        em.getTransaction().commit();
        em.close();
        return mapping.findForward("success");
    }

    @Override
    public ActionForward search(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActionForward display(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        EntityManager em = factory.createEntityManager();
        EntiteSociale user = UserUtils.getAuthenticatedUser(request, em);
        List<EntiteSociale> listContacts = user.getContacts();
        request.setAttribute("listContacts", listContacts);
        List<EntiteSociale> listDemands = user.getAsked();
        request.setAttribute("listDemands", listDemands);
        List<EntiteSociale> listRequests = user.getRequested();
        request.setAttribute("listRequests", listRequests);
        em.close();
        return mapping.findForward("success");
    }
}
