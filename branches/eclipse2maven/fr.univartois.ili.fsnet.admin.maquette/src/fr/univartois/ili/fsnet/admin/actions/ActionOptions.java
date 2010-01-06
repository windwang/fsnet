package fr.univartois.ili.fsnet.admin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class ActionOptions extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse arg3) throws Exception {
		DynaActionForm dynaform = (DynaActionForm) form;

		req.getSession().setAttribute("serveursmtp",
				dynaform.get("serveursmtp"));
		req.getSession().setAttribute("hote", dynaform.get("hote"));
		req.getSession().setAttribute("motdepasse", dynaform.get("motdepasse"));
		req.getSession().setAttribute("adressefsnet",
				dynaform.get("adressefsnet"));
		req.getSession().setAttribute("port", dynaform.get("port"));
		System.out.println("ICICICICICI LES OPTIONSSSSSS");
		return mapping.findForward("continue");
	}
}
