package fr.univartois.ili.fsnet.actions;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import java.io.IOException;
import java.util.List;

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

import fr.univartois.ili.fsnet.entities.EntiteSociale;

public class ManageMembers extends MappingDispatchAction {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");

    /**
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward search(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        Query query = null;
        Query queryContacts = null;
        Query queryRequested = null;
        Query queryAsked = null;
        List<EntiteSociale> resultOthers = null;
        List<EntiteSociale> resultContacts = null;
        List<EntiteSociale> resultRequested = null;
        List<EntiteSociale> resultAsked = null;
        EntiteSociale member = UserUtils.getAuthenticatedUser(request, em);

        member = em.find(EntiteSociale.class, member.getId());

        if (form != null) {
            DynaActionForm dynaForm = (DynaActionForm) form;
            String searchText = (String) dynaForm.get("searchText");

            query = em.createQuery(
                    "SELECT es FROM EntiteSociale es WHERE (es.nom LIKE :searchText"
                    + " OR es.prenom LIKE :searchText OR es.email LIKE :searchText) AND es.id <> :id");
            query.setParameter("searchText", "%" + searchText + "%");
            query.setParameter("id", member.getId());

            queryContacts = em.createQuery(
                    "SELECT contact FROM EntiteSociale contact, IN (contact.contacts) membre WHERE membre.id = :id AND (contact.nom LIKE :searchText"
                    + " OR contact.prenom LIKE :searchText OR contact.email LIKE :searchText)");
            queryContacts.setParameter("searchText", "%" + searchText + "%");
            queryContacts.setParameter("id", member.getId());

            queryRequested = em.createQuery(
                    "SELECT contact FROM EntiteSociale contact, IN (contact.requested) membre WHERE membre.id = :id AND (contact.nom LIKE :searchText"
                    + " OR contact.prenom LIKE :searchText OR contact.email LIKE :searchText)");
            queryRequested.setParameter("searchText", "%" + searchText + "%");
            queryRequested.setParameter("id", member.getId());

            queryAsked = em.createQuery(
                    "SELECT contact FROM EntiteSociale contact, IN (contact.asked) membre WHERE membre.id = :id AND (contact.nom LIKE :searchText"
                    + " OR contact.prenom LIKE :searchText OR contact.email LIKE :searchText)");
            queryAsked.setParameter("searchText", "%" + searchText + "%");
            queryAsked.setParameter("id", member.getId());

            resultContacts = queryContacts.getResultList();
            resultRequested = queryRequested.getResultList();
            resultAsked = queryAsked.getResultList();

        } else {
            query = em.createQuery("SELECT es FROM EntiteSociale es WHERE es.id <> :id");
            query.setParameter("id", member.getId());

            resultContacts = member.getContacts();
            resultRequested = member.getRequested();
            resultAsked = member.getAsked();
        }

        resultOthers = query.getResultList();

        resultOthers.removeAll(resultContacts);
        resultOthers.removeAll(resultAsked);
        resultOthers.removeAll(resultRequested);

        em.close();

        if (resultOthers != null) {
            request.setAttribute("membersResult", resultOthers);
        }
        if (resultContacts != null) {
            request.setAttribute("membersContactsResult", resultContacts);
        }
        if (resultRequested != null) {
            request.setAttribute("membersRequestedResult", resultRequested);
        }
        if (resultAsked != null) {
            request.setAttribute("membersAskedResult", resultAsked);
        }

        return mapping.findForward("success");
    }
}
