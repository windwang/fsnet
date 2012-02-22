package fr.univartois.ili.fsnet.actions;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.sql.Date;

import javax.persistence.EntityManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.tiles.taglib.GetAttributeTag;

import java.text.ParseException;
import java.util.HashMap;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.AssociationDateFormationCV;
import fr.univartois.ili.fsnet.entities.AssociationDateTrainingCV;
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.DegreeCV;
import fr.univartois.ili.fsnet.entities.EstablishmentCV;
import fr.univartois.ili.fsnet.entities.FormationCV;
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
	
	  public static java.util.Date stringToDate(String sDate) throws ParseException {
	        return  formatter.parse(sDate);
	    }
	  public static Date toDBDateFormat(String sDate) throws ParseException {
	        return new Date(stringToDate(sDate).getTime());
	    }
	 public ActionForward display(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws ParseException
	            {
		 int nbExp=Integer.parseInt(request.getParameter("nbexp"));
		 int nbfrom=Integer.parseInt(request.getParameter("nbform"));
		 int nblangue=Integer.parseInt(request.getParameter("nblangue"));
		 int nbloisir=Integer.parseInt(request.getParameter("nbloisir"));
		
		 Curriculum curriculum=new Curriculum();
	
		 EntityManager em = PersistenceProvider.createEntityManager();
		
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
		 int lang=0;
		HashMap<String,String> languages = null;
		 while(lang<nblangue){
		 String CVLangue=request.getParameter("CVLangue"+lang);
		 String niveaux=request.getParameter("niveaux"+lang);
		 languages.put(CVLangue, niveaux);
		 
		 
		 }
		 member.setLanguages(languages);
		 em.getTransaction().begin();
			em.persist(member);
	     curriculum.setMember(member);
	     curriculum.setTitleCv((String)mysession.getAttribute("CvTitle"));
		 
		
		
		 
		 //Les experiances
		 int i=0;
		 
		 while(i< nbExp){
			 TrainingCV training=new TrainingCV();
			 
			 AssociationDateTrainingCV dateTaining=new AssociationDateTrainingCV();
			 EstablishmentCV etab=new EstablishmentCV();
			 String NomEntreprise=request.getParameter("NomEntreprise"+i);
			 String CvLieu=request.getParameter("CvLieu"+i);
			 String CvPoste=request.getParameter("CvPoste"+i);
			 Date expBeginDate = toDBDateFormat(request.getParameter("expBeginDate"+i));
			 Date expEndDate = toDBDateFormat(request.getParameter("expEndDate"+i));
			 
			
			
			
		
		 String CvSecteur=request.getParameter("CvSecteur"+i);
		
		 
		
		
		
		 etab.setName(NomEntreprise);
		
		 dateTaining.setStartDate(expBeginDate);		 
		 dateTaining.setEndDate(expEndDate);
		 training.setName(CvPoste);
		 training.setSpeciality(CvSecteur);
		 training.getAssociationDateTrainingCV().add(dateTaining);
		 training.setAssociationDateTrainingCV(training.getAssociationDateTrainingCV());
		 training.setMyEst(etab);
		 
		 curriculum.getTrains().add(dateTaining);
		 curriculum.setTrains(curriculum.getTrains());
		 
		 dateTaining.setCurriculum(curriculum);
		 dateTaining.setTraining(training);
		  em.persist(dateTaining);
		  
		 em.persist(training);
		 em.persist(etab);
		
		
			
		 i++;
		 }
		 
		 //Diplôme/formation
		 int j=0;
		 while(j<nbfrom){
			DegreeCV degreecv=new DegreeCV();
			AssociationDateFormationCV dateFormation=new AssociationDateFormationCV();
			FormationCV  formation= new FormationCV();
			 EstablishmentCV etab=new EstablishmentCV();
		 String CvEtude=request.getParameter("CvEtude"+j);
		 String CvEtudeDom=request.getParameter("CvEtudeDom"+j);
		 String CvEtablissment=request.getParameter("CvEtablissment"+j);
		 String CvEtudePays=request.getParameter("CvEtudePays"+j);
		 Date etudBeginDate=toDBDateFormat(request.getParameter("etudBeginDate"+j));
		 Date etudEndDate=toDBDateFormat(request.getParameter("etudEndDate"+j));
		 String CvEtudeVille=request.getParameter("CvEtudeVille"+j);
		 			 formation.setName(CvEtude);
		 			 etab.setName(CvEtablissment);
		 			 etab.setTown(CvEtudeVille);
		 			 etab.setLand(CvEtudePays);
		 			 dateFormation.setEndDate(etudEndDate);
		 			 dateFormation.setStartDate(etudBeginDate);
		 			 formation.getAssociationDateFormationCV().add(dateFormation);
		 			 formation.setAssociationDateFormationCV(formation.getAssociationDateFormationCV());
		 			 formation.setEts(etab);
		 			 curriculum.getMyFormations().add(dateFormation);
		 			 curriculum.setMyFormations( curriculum.getMyFormations());
		 			dateFormation.setIdCurriculum(curriculum);
		 			dateFormation.setIdFormation(formation);
		 			
		 			em.persist(dateFormation);
		 			em.persist(formation);
		 			em.persist(etab);
		 			 
		 			 
		 
		 
		 j++;
		 }
		 String CvNomLoisir=request.getParameter("CvNomLoisir");
		 //Langues
		
		
		
			
		 
		
		
		 
		 
		
		
			em.persist(curriculum);
			
			em.getTransaction().commit();
			em.close();
	
//	        

	        return mapping.findForward("success");
	    }
}
