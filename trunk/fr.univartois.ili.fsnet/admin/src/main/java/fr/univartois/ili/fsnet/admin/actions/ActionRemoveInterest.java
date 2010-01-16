package fr.univartois.ili.fsnet.admin.actions;

import fr.univartois.ili.fsnet.entities.Interet;
import freemarker.template.utility.Collections12;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * Remove an interest from database
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class ActionRemoveInterest extends Action {

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");
    private EntityManager em;
    private static final Logger log = Logger.getLogger(ActionUser.class.getName());
    private static final String FIND_BY_ID = "SELECT i FROM Interet i WHERE i.id =?1";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] interests = request.getParameterValues("interestSelected");
        if (interests != null) {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            try {
                for (String s : interests) {
                    Query query = em.createQuery(FIND_BY_ID);
                    query.setParameter(1, Integer.parseInt(s));
                    em.remove(query.getSingleResult());
                }
                em.getTransaction().commit();
            } catch (Exception e) {
                ActionErrors actionErrors = new ActionErrors();
                ActionMessage msg = new ActionMessage("errors.removeInterest");
                actionErrors.add("errors.removeInterest", msg);
                saveErrors(request, actionErrors);
            }
            em.close();
        }
             
        ActionForward forward = new ActionForward(
                mapping.findForward("success").getPath()
                + "?interest=current&showHide=show&deploy=[-]&titleDeploy=R%E9duire la liste&recherche=hide");
        return forward;
    }
}
