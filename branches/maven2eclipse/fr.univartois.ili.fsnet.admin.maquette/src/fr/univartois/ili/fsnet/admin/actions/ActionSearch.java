package fr.univartois.ili.fsnet.admin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;


public class ActionSearch extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse arg3) throws Exception {
		DynaActionForm dynaform = (DynaActionForm)form;
		
		
		if(req.getParameter("selectRecherche") != null){
			req.getSession().setAttribute("selectRecherche", req.getParameter("selectRecherche"));
		}
		
		req.getSession().setAttribute("searchText", dynaform.get("searchtext"));
		req.getSession().setAttribute("redirection", req.getParameter("redirection"));

		servlet.getServletContext().log("Je t'ai vue dans l'action search contenant : "+dynaform.get("searchtext")+" !!!");
        return mapping.findForward("continue"); 
	}
}
