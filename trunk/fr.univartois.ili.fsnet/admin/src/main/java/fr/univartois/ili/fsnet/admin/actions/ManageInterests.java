package fr.univartois.ili.fsnet.admin.actions;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
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
import org.eclipse.persistence.exceptions.DatabaseException;

import fr.univartois.ili.fsnet.entities.Interest;

// TODO attention : rustine sur les requetes

/**
 * Execute CRUD Actions (and more) for the entity interet
 * 
 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
 */
public class ManageInterests extends MappingDispatchAction implements
        CrudAction {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");
    private static final Logger logger = Logger.getAnonymousLogger();

    @Override
    public ActionForward create(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        DynaActionForm dynaForm = (DynaActionForm) form; //NOSONAR

        String interestName = (String) dynaForm.get("createdInterestName");
        Interest interest = new Interest(interestName);

        logger.info("new interest: " + interestName);

        try {
            em.getTransaction().begin();
            em.persist(interest);
            em.getTransaction().commit();
        } catch (RollbackException ex) {
            ActionErrors actionErrors = new ActionErrors();
            ActionMessage msg = new ActionMessage("interest.alreadyExists");
            actionErrors.add("createdInterestName", msg);
            saveErrors(request, actionErrors);
        }

        em.close();

        return mapping.findForward("success");
    }

    // TODO modify
    @Override
    public ActionForward modify(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
        int interestId = Integer.valueOf((String) dynaForm.get("modifiedInterestId"));
        String interestName = (String) dynaForm.get("modifiedInterestName");

        logger.info("interest modification: " + interestName);

        try {
            em.getTransaction().begin();
            em.createQuery(
                    "UPDATE Interest interest SET interest.name = :interestName WHERE interest.id = :interestId").setParameter("interestName", interestName).setParameter("interestId", interestId).executeUpdate();
            em.getTransaction().commit();
        } catch (DatabaseException ex) {
            ActionErrors actionErrors = new ActionErrors();
            ActionMessage msg = new ActionMessage("interest.alreadyExists");
            actionErrors.add("modifiedInterestName", msg);
            saveErrors(request, actionErrors);
        }

        em.close();

        return mapping.findForward("success");
    }

    @Override
    public ActionForward delete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();

        // TODO verify if user has the right to do delete

        int interestId = Integer.valueOf(request.getParameter("deletedInterestId"));

        logger.info("interest deleted: id=" + interestId);

        try {
            em.getTransaction().begin();
            em.createQuery(
                    "DELETE FROM Interest interest WHERE interest.id = :interestId").setParameter("interestId", interestId).executeUpdate();
            em.getTransaction().commit();
        } catch (RollbackException ex) {
            ActionErrors actionErrors = new ActionErrors();
            ActionMessage msg = new ActionMessage("interest.notExists");
            actionErrors.add("error.interest.delete", msg);
            saveErrors(request, actionErrors);
        }

        em.close();

        return mapping.findForward("success");
    }

    @Override
    public ActionForward search(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
        String interestName = "";

        if (dynaForm.get("searchInterestName") != null) {
            interestName = (String) dynaForm.get("searchInterestName");
        }

        logger.info("search interest: " + interestName);

        List<Interest> result = em.createQuery(
                "SELECT interest FROM Interest interest "
                + "WHERE interest.name LIKE :interestName ",
                Interest.class).setParameter("interestName",
                '%' + interestName + '%').getResultList();
        em.close();

        request.setAttribute("interestResult", result);

        return mapping.findForward("success");
    }

    @Override
    public ActionForward display(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();

        logger.info("Displaying interests");

        // TODO requete provisoire

        List<Interest> listAllInterests = em.createQuery(
                "SELECT interest FROM Interest interest", Interest.class).getResultList();

        request.setAttribute("allInterests", listAllInterests);

        em.close();
        return mapping.findForward("success");
    }
}