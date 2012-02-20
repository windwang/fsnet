package fr.univartois.ili.fsnet.actions;

import java.io.IOException;

import java.sql.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.sun.tools.example.debug.expr.ParseException;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.AssociationDateTrainingCV;
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.EstablishmentCV;
import fr.univartois.ili.fsnet.entities.MemberCV;
import fr.univartois.ili.fsnet.entities.TrainingCV;

/**
 * 
 * formular validator for resume
 * @author BENZAOUIA
 *
 */
public class InsertInfoCV extends MappingDispatchAction{
	  public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	  
	    public static Date stringToDate(String sDate) throws ParseException, java.text.ParseException {
	        return (Date) formatter.parse(sDate);
	    }
	 public ActionForward display(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws IOException, ServletException, ParseException, java.text.ParseException {
		 AssociationDateTrainingCV dateTaining=new AssociationDateTrainingCV();
		 EstablishmentCV etab=new EstablishmentCV();
		 Curriculum curriculum=new Curriculum();
		 
		 EntityManager em = PersistenceProvider.createEntityManager();
		
		 
		 
		 em.getTransaction().begin();
			
		 
		 TrainingCV training=new TrainingCV();
		 HttpSession mysession=request.getSession();
		 //MemberCv
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
		 curriculum.setMember(member);
		 int nbExp=Integer.parseInt(request.getParameter("nbexp"));
		 int nbfrom=Integer.parseInt(request.getParameter("nbform"));
		 int nblangue=Integer.parseInt(request.getParameter("nblangue"));
		 int nbloisir=Integer.parseInt(request.getParameter("nbloisir"));
		 System.out.print("***"+nbExp+"****"+nbfrom+"****"+nblangue+"****"+nbloisir+"****");
		 
		 //Les experiances
		 for(int i=0;i<nbExp;i++){
		 String NomEntreprise=request.getParameter("NomEntreprise"+i);
		 String CvLieu=request.getParameter("CvLieu0"+i);
		 String CvPoste=request.getParameter("CvPoste"+i);
		 Date expBeginDate=stringToDate(request.getParameter("expBeginDate"+i));
		 String CvSecteur=request.getParameter("CvSecteur"+i);
		 Date expEndDate=stringToDate(request.getParameter("expEndDate"+i));
		 training.setSpeciality(CvSecteur);
		 training.setName(CvPoste);
		 etab.setName(NomEntreprise);
		 etab.setAdress(CvLieu);		 
		 dateTaining.setStartDate(expBeginDate);
		 dateTaining.setEndDate(expEndDate);		
		 List<AssociationDateTrainingCV> myCVs=training.getAssociationDateTrainingCV();
		 myCVs.add(dateTaining);
		 training.setAssociationDateTrainingCV(myCVs);
		 training.setMyEst(etab);
		 
		 
		 }
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
		
			em.persist(member);

			em.getTransaction().commit();
			em.close();
		
	        

	        return mapping.findForward("success");
	    }
}
