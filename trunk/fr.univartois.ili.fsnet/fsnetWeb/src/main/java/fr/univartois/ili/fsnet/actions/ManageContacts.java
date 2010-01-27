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

import fr.univartois.ili.fsnet.entities.SocialEntity;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * 
 * @author Grioche Legrand
 */
public class ManageContacts extends MappingDispatchAction implements CrudAction {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");


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
        DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
        EntityManager em = factory.createEntityManager();
        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
        final String idString = (String) dynaForm.get("entitySelected");
        int entitySelected = Integer.parseInt(idString);

        ActionErrors errors = new ActionErrors();
        if (entitySelected != user.getId()) {
            em.getTransaction().begin();

            // TODO changer les listes en set sur les entites sociales pour eviter
            // les doublons
            SocialEntity entity = em.find(SocialEntity.class, entitySelected);

            if (notInRelation(user, entity)) {
                user.getRequested().add(entity);
                entity.getAsked().add(user);
                em.getTransaction().commit();
            } else {
                errors.add("entitySelected", new ActionMessage("contacts.error.alreadyAsked"));
            }
        } else {
            errors.add("entitySelected", new ActionMessage("contacts.error.askself"));
        }
        em.close();
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
        DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
        EntityManager em = factory.createEntityManager();
        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
        final String idString = (String) dynaForm.get("entityAccepted");
        int id = Integer.parseInt(idString);
        SocialEntity entityAccepted = em.find(SocialEntity.class, id);
        //TODO check that in a method when --facading--
        if (user.getAsked().contains(entityAccepted)
                && entityAccepted.getRequested().contains(user)
                && !user.getContacts().contains(entityAccepted)
                && !entityAccepted.getContacts().contains(user)) {
            em.getTransaction().begin();
            user.getContacts().add(entityAccepted);
            entityAccepted.getContacts().add(user);
            user.getAsked().remove(entityAccepted);
            entityAccepted.getRequested().remove(user);
            em.getTransaction().commit();
        } else {
            //TODO create a non consistant database error message SEVER !
        }
        em.close();
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

        DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
        EntityManager em = factory.createEntityManager();
        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
        final String idString = (String) dynaForm.get("entityRefused");
        int id = Integer.parseInt(idString);
        SocialEntity entityRefused = em.find(SocialEntity.class, id);
        if (user.getAsked().contains(entityRefused)
                && entityRefused.getRequested().contains(user)
                && !user.getContacts().contains(entityRefused)
                && !entityRefused.getContacts().contains(user)) {
        em.getTransaction().begin();
        user.getAsked().remove(entityRefused);
        entityRefused.getRequested().remove(user);
        user.getRefused().add(entityRefused);
        em.merge(user);
        em.merge(entityRefused);
        em.getTransaction().commit();
        }
        em.close();
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
        DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
        EntityManager em = factory.createEntityManager();
        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
        final String idString = (String) dynaForm.get("entityDeleted");
        int id = Integer.parseInt(idString);
        SocialEntity entityDeleted = em.find(SocialEntity.class, id);
        em.getTransaction().begin();
        user.getContacts().remove(entityDeleted);
        entityDeleted.getContacts().remove(user);
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
        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
        user.getContacts();
        user.getAsked();
        user.getRequested();
        request.setAttribute("theUser", user);
        em.close();
        return mapping.findForward("success");
    }

    // TODO move in facade
    private boolean notInRelation(SocialEntity user, SocialEntity entity) {
        return !(user.getAsked().contains(entity)
                || user.getRequested().contains(entity)
                || user.getContacts().contains(entity)
                || entity.getAsked().contains(user)
                || entity.getRequested().contains(user)
                || entity.getContacts().contains(user));
    }
}
