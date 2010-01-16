package fr.univartois.ili.fsnet.admin.actions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class DeleteUser extends Action {

    private static final long serialVersionUID = 1L;
    private static final String DATABASE_NAME = "fsnetjpa";
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
    private static final String DELETE_ENTITY_BY_ID = "DELETE FROM EntiteSociale e WHERE e.id = ?1";
    private static final String DELETE_REGISTRATION_BY_ENTITY_ID = "DELETE FROM Inscription i WHERE i.entite.id = ?1";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        EntityManager em = factory.createEntityManager();

        // using a multibox we retrieve an array of EntiteSociale ids
        DynaActionForm dynaform = (DynaActionForm) form;
        Integer userIds[] = (Integer[]) dynaform.get("selectedUsers");

        em.getTransaction().begin();

        Query query;
        for (Integer userId : userIds) {

            query = em.createQuery(DELETE_ENTITY_BY_ID);
            query.setParameter(1, userId);
            query.executeUpdate();

            // TODO Add a CascadeType.DELETE constraint to avoid following lines
            query = em.createQuery(DELETE_REGISTRATION_BY_ENTITY_ID);
            query.setParameter(1, userId);
            query.executeUpdate();

        }

        em.getTransaction().commit();
        em.close();

        return mapping.getInputForward();
    }
}
