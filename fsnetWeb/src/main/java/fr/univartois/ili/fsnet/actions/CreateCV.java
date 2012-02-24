package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.upload.FormFile;

/**
 * 
 * formular validator for resume
 * @author BENZAOUIA
 *
 */
public class CreateCV extends MappingDispatchAction{
	public static SimpleDateFormat formatter = new SimpleDateFormat(
			"dd/MM/yyyy");

	public static java.util.Date stringToDate(String sDate)
			throws ParseException {
		return formatter.parse(sDate);
	}

	public static Date toDBDateFormat(String sDate) throws ParseException {
		return new Date(stringToDate(sDate).getTime());
	}
	 public ActionForward display(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws IOException, ServletException {
		 HttpSession mysession=request.getSession();
		 DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		 String cvTitle = (String) dynaForm.get("CvTitle");
		 String cvNom = (String) dynaForm.get("CvNom");
		 String cvPrenom = (String) dynaForm.get("CvPrenom");
		 String cvAdresse = (String) dynaForm.get("CvAdresse");
		 String cvVille = (String) dynaForm.get("CvVille");
		 String cvPortable = (String) dynaForm.get("CvPortable");
		 String cvCp = (String) dynaForm.get("CvCp");
		 String cvPays = (String) dynaForm.get("CvPays");
		 String cvContact = (String) dynaForm.get("CvContact");
		 String birthDay = (String) dynaForm.get("formatBirthDay");
		 
		 mysession.setAttribute("CvTitle", cvTitle);
		 mysession.setAttribute("CvNom", cvNom);
		 mysession.setAttribute("CvPrenom", cvPrenom);
		 mysession.setAttribute("CvAdresse", cvAdresse);
		 mysession.setAttribute("CvVille", cvVille);
		 mysession.setAttribute("CvPortable", cvPortable);
		 mysession.setAttribute("CvCp", cvCp);
		 mysession.setAttribute("CvPays", cvPays);
		 mysession.setAttribute("CvContact", cvContact);
		 mysession.setAttribute("formatBirthDay", birthDay);
		 mysession.setAttribute("CvSituation", request.getParameter("situation"));
		 mysession.setAttribute("SexeMember", request.getParameter("sexe"));
		 
		 ActionErrors errors = new ActionErrors();
		 int erreur=0;
		 
		 if("".equals(cvTitle)){
			
				errors.add("CvTitle", new ActionMessage("error.titre"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 
		 if("".equals(cvPortable)){
			
				errors.add("CvPortable", new ActionMessage("error.portable"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 if("".equals(cvNom)){
			
				errors.add("CvNom",  new ActionMessage("error.nom"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 if("".equals(cvPrenom)){
			
				errors.add("CvPrenom", new ActionMessage("error.prenom"));
				saveErrors(request, errors);
				erreur = 1;
				
		 }
		 if("".equals(cvAdresse)){
			 
				errors.add("CvAdresse", new ActionMessage("error.adresse"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 if("".equals(cvVille)){
			
				errors.add("CvVille", new ActionMessage("error.ville"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 if("".equals(birthDay)){
				
			 errors.add("formatBirthDay", new ActionMessage("error.birthDay"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 
		

		 if("".equals(cvPays)){
			 
				errors.add("CvPays", new ActionMessage("error.pays"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 if("".equals(cvContact)){
			
				errors.add("CvContact", new ActionMessage("error.contact"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 if("".equals(cvCp)){
				
				errors.add("CvCp", new ActionMessage("error.cp"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 if(!"".equals(cvCp)){
		try{
			Integer.parseInt(cvCp);
		}catch(Exception e){
		
			 errors.add("CvCp", new ActionMessage("error.cpInt"));
				saveErrors(request, errors);
				erreur=1;
		 }
		 }
		try{
			 toDBDateFormat(birthDay);
		}catch(Exception e){
			 errors.add("formatBirthDay", new ActionMessage("error.birthDayValue"));
				saveErrors(request, errors);
				erreur=1;
		}
		
		 if(erreur==1){
				return mapping.findForward("unauthorized");
		 }else{
	        return mapping.findForward("success");
		 }
	    }
	 public ActionForward Cree(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws IOException, ServletException {
		 return mapping.findForward("success");
	 }
}
