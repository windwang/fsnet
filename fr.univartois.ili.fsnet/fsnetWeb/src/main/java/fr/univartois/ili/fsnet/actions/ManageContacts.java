/*
 *  Copyright (C) 2010 Matthieu Proucelle <matthieu.proucelle at gmail.com>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.List;
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
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.auth.Authenticate;
import fr.univartois.ili.fsnet.entities.EntiteSociale;

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
        Logger.getAnonymousLogger().severe("@@@@@@@@@@@@@" + listEntities);
        em.close();
        return mapping.findForward("success");
    }

    /**
     * to submit a request contact to another social entity
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


        EntityManager em = factory.createEntityManager();
        EntiteSociale user = (EntiteSociale) request.getSession().getAttribute(
                Authenticate.AUTHENTICATED_USER);
        EntiteSociale entitySelected = (EntiteSociale) request.getAttribute("entitySelected");
        em.getTransaction().begin();

        // TODO changer les listes en set sur les entites sociales pour eviter
        // les doublons
        user.getRequested().add(entitySelected);
        entitySelected.getAsked().add(user);
        em.merge(entitySelected);
        em.merge(user);
        em.getTransaction().commit();
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


        EntityManager em = factory.createEntityManager();
        EntiteSociale user = (EntiteSociale) request.getSession().getAttribute(
                Authenticate.AUTHENTICATED_USER);
        EntiteSociale entityAccepted = (EntiteSociale) request.getAttribute("entityAccepted");
        em.getTransaction().begin();
        user.getContacts().add(entityAccepted);
        entityAccepted.getContacts().add(user);
        user.getAsked().remove((EntiteSociale) entityAccepted);
        entityAccepted.getRequested().remove(user);
        em.merge(user);
        em.merge(entityAccepted);
        em.getTransaction().commit();
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


        EntityManager em = factory.createEntityManager();
        EntiteSociale user = (EntiteSociale) request.getSession().getAttribute(
                Authenticate.AUTHENTICATED_USER);
        EntiteSociale entityRefused = (EntiteSociale) request.getAttribute("entityRefused");

        em.getTransaction().begin();
        user.getRefused().add(entityRefused);
        user.getAsked().remove(entityRefused);
        entityRefused.getRequested().remove(user);
        entityRefused.getRefused().add(user);
        em.merge(user);
        em.merge(entityRefused);
        em.getTransaction().commit();

        em.close();
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
        EntiteSociale user = (EntiteSociale) request.getSession().getAttribute(
                Authenticate.AUTHENTICATED_USER);
        // Non surtout pas
        //em.refresh(user);
        List<EntiteSociale> listRequests = user.getRequested();
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
        EntiteSociale user = (EntiteSociale) request.getSession().getAttribute(
                Authenticate.AUTHENTICATED_USER);
        List<EntiteSociale> listDemands = user.getAsked();
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
        throw new UnsupportedOperationException("Not supported yet.");
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
        EntiteSociale user = (EntiteSociale) request.getSession().getAttribute(
                Authenticate.AUTHENTICATED_USER);
        user = em.find(EntiteSociale.class, user.getId());
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
