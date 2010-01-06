package fr.univartois.ili.fsnet.admin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import fr.univartois.ili.fsnet.entities.Interet;

public class ActionInterest extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse arg3) throws Exception {
        DynaActionForm dynaform = (DynaActionForm)form;
        Interet dto = new Interet();
        BeanUtils.copyProperties(dto,dynaform);
        servlet.getServletContext().log("Je t'ai vue !!!");

        req.getSession().setAttribute("interet",dto);
        req.getSession().setAttribute("interets[]", req.getParameterValues("interets[]"));
        servlet.getServletContext().log("Je t'ai vue  "+dynaform.getString("nomInteret")+" == "+dto.getNomInteret()+"!!!");
        return mapping.findForward("continue"); 
	}
}
