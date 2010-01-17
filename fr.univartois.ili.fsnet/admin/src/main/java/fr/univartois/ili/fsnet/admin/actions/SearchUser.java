package fr.univartois.ili.fsnet.admin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class SearchUser extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // TODO traitement dans l'action, la tag est tout pourrit
        DynaActionForm dynaform = (DynaActionForm) form;
        String searchtext = (String) dynaform.get("searchtext");
        String selectRecherche = (String) dynaform.get("selectRecherche");
        request.setAttribute("searchtext", searchtext);
        request.setAttribute("selectRecherche", selectRecherche);
        return mapping.findForward("success");
    }
}
