package fr.univartois.ili.fsnet.actions.ajax;

import java.io.Writer;
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
            Writer out = response.getWriter();
            response.setContentType("text/xml");

            out.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.append("<root>");
            for (SocialEntity se : listSE) {
                out.append("<item id='");
                out.append(""+se.getId());
                out.append("' label='"); 
                out.append(completeUser);
                out.append(se.getEmail());
                out.append( "'/>");
            }
            out.append("</root>");
            out.flush();
            out.close();
        }
        return null;
    }
}
