package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * @author geoffrey
 *
 */
public class CreateCV extends MappingDispatchAction{
	 public ActionForward display(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws IOException, ServletException {
	        
		 DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		 String CvTitle = (String) dynaForm.get("CvTitle");
		 String CvNom = (String) dynaForm.get("CvNom");
		 String CvPrenom = (String) dynaForm.get("CvPrenom");
		 String CvAdresse = (String) dynaForm.get("CvAdresse");
		 String CvVille = (String) dynaForm.get("CvVille");
		 String CvPortable = (String) dynaForm.get("CvPortable");
		 String CvCp = (String) dynaForm.get("CvCp");
		 String CvPays = (String) dynaForm.get("CvPays");
		 String CvContact = (String) dynaForm.get("CvContact");
		 
		 ActionErrors errors = new ActionErrors();
		 int erreur=0;
		 
		 if(CvTitle==""){
			
				errors.add("CvTitle", new ActionMessage("error.titre"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 
		 if(CvPortable==""){
			
				errors.add("CvPortable", new ActionMessage("error.portable"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 if(CvNom==""){
			
				errors.add("CvNom", new ActionMessage("error.nom"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 if(CvPrenom==""){
			
				errors.add("CvPrenom", new ActionMessage("error.prenom"));
				saveErrors(request, errors);
				erreur = 1;
				
		 }
		 if(CvAdresse==""){
			 
				errors.add("CvAdresse", new ActionMessage("error.adresse"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 if(CvVille==""){
			
				errors.add("CvVille", new ActionMessage("error.ville"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 if(CvCp==""){
			 
				errors.add("CvCp", new ActionMessage("error.cp"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 if(CvPays==""){
			 
				errors.add("CvPays", new ActionMessage("error.pays"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 if(CvContact==""){
			
				errors.add("CvContact", new ActionMessage("error.contact"));
				saveErrors(request, errors);
				erreur = 1;
		 }
		 if(erreur==1){
				return mapping.findForward("unauthorized");
		 }else
	        return mapping.findForward("success");
	    }
}
