package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

/**
 * Execute CRUD Actions for the entitie Event
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class ManageEvents extends MappingDispatchAction implements CrudAction {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");

    @Override
    public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // TODO code pour la creation
        EntityManager em = factory.createEntityManager();
        // ICI la recuperation

        // Si tout c'est bien passé, on retourne success
        return mapping.findForward("success");
    }

    @Override
    public ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // TODO code pour la modification
        return null;
    }

    @Override
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // TODO code pour la suppression
        return null;
    }

    @Override
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // TODO code pour la recherche
        return null;
    }

    @Override
    public ActionForward display(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // TODO code pour l'affichage detaillé
        return null;
    }
}
