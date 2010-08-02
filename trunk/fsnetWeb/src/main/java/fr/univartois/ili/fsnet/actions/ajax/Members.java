package fr.univartois.ili.fsnet.actions.ajax;


import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;


/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class Members extends Action {
	
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ActionForward res = null;
    	if (form != null) {
            DynaActionForm theform = (DynaActionForm) form; // NOSONAR
            String actualText = (String) theform.get("searchText");
            int index = actualText.lastIndexOf(",");
            String completeUser = (index==-1) ? ("") : (actualText.substring(0, index+1));
            String searchText = (index==-1) ? (actualText) : (actualText.substring(index+1));
            EntityManager em = PersistenceProvider.createEntityManager();
            SocialEntityFacade sef = new SocialEntityFacade(em);
            Set<SocialEntity> listSE = sef.searchSocialEntity(searchText);
            em.close();
            request.setAttribute("matchesSocialEntity", listSE);
            request.setAttribute("completeUsers", completeUser);
            res = mapping.findForward("success");
        }
        return res;
    }
}
