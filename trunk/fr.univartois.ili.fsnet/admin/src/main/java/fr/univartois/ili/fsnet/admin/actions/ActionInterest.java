package fr.univartois.ili.fsnet.admin.actions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.DynaActionForm;

import fr.univartois.ili.fsnet.entities.Interet;

public class ActionInterest extends Action{
	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");
    private EntityManager em;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse arg3) throws Exception {
		em = factory.createEntityManager();
		DynaActionForm dynaform = (DynaActionForm)form;
        Interet dto = new Interet();
        BeanUtils.copyProperties(dto,dynaform);
        
        try {
            em.getTransaction().begin();
            em.persist(dto);
            em.getTransaction().commit();
        } catch (RollbackException ex) {
            ActionErrors actionErrors = new ActionErrors();
            ActionMessage msg = new ActionMessage(
                    "Impossible de persister l'entité, cet intérêt est déjà dans la base.");
            actionErrors.add("nomInteret", msg);
            saveErrors(req, actionErrors);
        }
        
        em.close();
        
        ActionForward forward = new ActionForward(
        		mapping.findForward("continue").getPath()+
        		"?interest=current&showHide=show&deploy=[-]&titleDeploy=R%E9duire la liste"
        );
        
        return forward; 
	}
}
