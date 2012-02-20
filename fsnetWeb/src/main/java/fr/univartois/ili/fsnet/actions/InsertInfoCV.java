package fr.univartois.ili.fsnet.actions;

import java.io.IOException;


import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.MemberCV;

/**
 * 
 * formular validator for resume
 * @author BENZAOUIA
 *
 */
public class InsertInfoCV extends MappingDispatchAction{
	 public ActionForward display(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws IOException, ServletException {
		 
		 HttpSession mysession=request.getSession();
		 int nbExp=Integer.parseInt(request.getParameter("nbexp"));
		 int nbfrom=Integer.parseInt(request.getParameter("nbform"));
		 int nblangue=Integer.parseInt(request.getParameter("nblangue"));
		 int nbloisir=Integer.parseInt(request.getParameter("nbloisir"));
		 System.out.print("***"+nbExp+"****"+nbfrom+"****"+nblangue+"****"+nbloisir+"****");
		 //Les experiances
		 String NomEntreprise=request.getParameter("NomEntreprise0");
		 String CvLieu=request.getParameter("CvLieu0");
		 String CvPoste=request.getParameter("CvPoste");
		 String expBeginDate=request.getParameter("expBeginDate0");
		 String CvSecteur=request.getParameter("CvSecteur0");
		 String expEndDate=request.getParameter("expEndDate0");
		 
		 //Diplôme/formation
		 
		 String CvEtude=request.getParameter("CvEtude");
		 String CvEtudeDom=request.getParameter("CvEtudeDom");
		 String CvEtablissment=request.getParameter("CvEtablissment");
		 String CvEtudePays=request.getParameter("CvEtudePays");
		 String etudBeginDate=request.getParameter("etudBeginDate");
		 String etudEndDate=request.getParameter("etudEndDate");
		 String CvEtudeVille=request.getParameter("CvEtudeVille");
		 //certification				 
		 
		 String CvNomCertif=request.getParameter("CvNomCertif");
	
		 
		 //Langues
		 String CVLangue=request.getParameter("CVLangue");
		 String niveaux=request.getParameter("niveaux");
		 
		 EntityManager em = PersistenceProvider.createEntityManager();
		 MemberCV member=new MemberCV();
		 member.setBirthDate((String) mysession.getAttribute("formatBirthDay"));
		
		 member.setTown((String) mysession.getAttribute("CvPays"));		 
		 member.setAdress((String) mysession.getAttribute("CvAdresse"));
		 member.setFirstName((String)mysession.getAttribute("CvNom"));
		 member.setMail((String)mysession.getAttribute("CvContact"));
		 member.setNumberPhone((String)mysession.getAttribute("CvPortable"));
		 member.setSurname((String)mysession.getAttribute("CvPrenom"));
		 member.setPostCode(Integer.parseInt((String) mysession.getAttribute("CvCp")));
		 member.setSituationFamilly((String)mysession.getAttribute("CvSituation"));
		 member.setSex((String)mysession.getAttribute("SexeMember"));
		 
		 
		 em.getTransaction().begin();
			
			em.persist(member);

			em.getTransaction().commit();
			em.close();
		
	        

	        return mapping.findForward("success");
	    }
}
