package fr.univartois.ili.fsnet.actions;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.AssociationDateDegreeCV;
import fr.univartois.ili.fsnet.entities.AssociationDateFormationCV;
import fr.univartois.ili.fsnet.entities.AssociationDateTrainingCV;
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.DegreeCV;
import fr.univartois.ili.fsnet.entities.EstablishmentCV;
import fr.univartois.ili.fsnet.entities.FormationCV;
import fr.univartois.ili.fsnet.entities.HobbiesCV;
import fr.univartois.ili.fsnet.entities.MemberCV;
import fr.univartois.ili.fsnet.entities.TrainingCV;

/**
 * 
 * formular validator for resume
 * 
 * @author BENZAOUIA
 * 
 */
public class InsertInfoCV extends MappingDispatchAction {
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
			throws ParseException {
		try{
		int nbExp = Integer.parseInt(request.getParameter("nbexp"));
		int nbfrom = Integer.parseInt(request.getParameter("nbform"));
		int nblangue = Integer.parseInt(request.getParameter("nblangue"));
		int nbloisir = Integer.parseInt(request.getParameter("nbloisir"));
		int nbdip = Integer.parseInt(request.getParameter("nbdip"));

		Curriculum curriculum = new Curriculum();

		EntityManager em = PersistenceProvider.createEntityManager();

		HttpSession mysession = request.getSession();
		
		// Insert Membe
		MemberCV member = new MemberCV();
		try{
		member.setBirthDate((Date) toDBDateFormat((String) mysession
				.getAttribute("formatBirthDay")));
		}catch(Exception e){
			
		}
		member.setTown((String) mysession.getAttribute("CvPays"));
		member.setAdress((String) mysession.getAttribute("CvAdresse"));
		member.setFirstName((String) mysession.getAttribute("CvNom"));
		member.setMail((String) mysession.getAttribute("CvContact"));
		member.setNumberPhone((String) mysession.getAttribute("CvPortable"));
		member.setSurname((String) mysession.getAttribute("CvPrenom"));
		member.setPostCode(Integer.parseInt((String) mysession
				.getAttribute("CvCp")));
		member.setSituationFamilly((String) mysession
				.getAttribute("CvSituation"));
		member.setSex((String) mysession.getAttribute("SexeMember"));
		int lang = 0;
		HashMap<String, String> languages= new HashMap<String, String>();
		while (lang < nblangue) {
			
			String CVLangue = request.getParameter("CVLangue" + lang);
			String niveaux = request.getParameter("niveaux" + lang);
			languages.put(CVLangue, niveaux);
			lang++;
		}
		member.setLanguages(languages);
		em.getTransaction().begin();
		em.persist(member);
		curriculum.setMember(member);
		curriculum.setTitleCv((String) mysession.getAttribute("CvTitle"));

		// Insert experiences
		int i = 0;

		while (i < nbExp) {
			TrainingCV training = new TrainingCV();

			AssociationDateTrainingCV dateTaining = new AssociationDateTrainingCV();
			EstablishmentCV etab = new EstablishmentCV();
			String NomEntreprise = request.getParameter("NomEntreprise" + i);
			
			String CvPoste = request.getParameter("CvPoste" + i);
			try{
			Date expBeginDate = toDBDateFormat(request
					.getParameter("expBeginDate" + i));
			Date expEndDate = toDBDateFormat(request.getParameter("expEndDate"
					+ i));

			dateTaining.setStartDate(expBeginDate);
			dateTaining.setEndDate(expEndDate);
			}catch(Exception e){
				
			}
			String CvSecteur = request.getParameter("CvSecteur" + i);
			etab.setName(NomEntreprise);
			etab.setLand("CvPaysExp" + i);
			etab.setTown("CvVilleExp" + i);

			training.setName(CvPoste);
			training.setSpeciality(CvSecteur);
		
			training.getAssociationDateTrainingCV().add(dateTaining);
			training.setAssociationDateTrainingCV(training
					.getAssociationDateTrainingCV());
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

		//Insert formation
		int f = 0;
		while (f < nbfrom) {
			
			AssociationDateFormationCV dateFormation = new AssociationDateFormationCV();
			FormationCV formation = new FormationCV();
			EstablishmentCV etab = new EstablishmentCV();
			String CvFormation = request.getParameter("CvFormation"+f);
			String CvEtablissmentform = request.getParameter("CvEtablissmentform"+f);
			String CvFormPays = request.getParameter("CvFormPays"+f);
			try{
			Date DateObtention = toDBDateFormat(request
					.getParameter("DateObtention"+f));
			dateFormation.setObtainedDate(DateObtention);
			}catch(Exception e){
				
			}
			String CvFormVille = request.getParameter("CvFormVille"+f);
		
			etab.setName(CvEtablissmentform);
			etab.setTown(CvFormVille);
			etab.setLand(CvFormPays);
			
		
			formation.getAssociationDateFormationCV().add(dateFormation);
			formation.setAssociationDateFormationCV(formation
					.getAssociationDateFormationCV());
			formation.setName(CvFormation);
			formation.setEts(etab);
			curriculum.getMyFormations().add(dateFormation);
			curriculum.setMyFormations(curriculum.getMyFormations());
			dateFormation.setIdCurriculum(curriculum);
			dateFormation.setIdFormation(formation);

			em.persist(dateFormation);
			em.persist(formation);
			em.persist(etab);

			f++;
		}
		
		
		//Insert degree
		int d=0;
		while(d<nbdip){
			DegreeCV degree=new DegreeCV();
			AssociationDateDegreeCV dateDegreeCV=new AssociationDateDegreeCV();
			EstablishmentCV etabCv=new EstablishmentCV();
			String CvEtude= request.getParameter("CvEtude"+d);
			String CvEtudeDom= request.getParameter("CvEtudeDom"+d);
			String CvEtablissment= request.getParameter("CvEtablissment"+d);
			String CvEtudePays= request.getParameter("CvEtudePays"+d);
			String CvEtudeVille= request.getParameter("CvEtudeVille"+d);
			try{
				Date etudBeginDate = toDBDateFormat(request
						.getParameter("etudBeginDate" + d));
				Date etudEndDate = toDBDateFormat(request.getParameter("etudEndDate"
						+ d));

				dateDegreeCV.setStartDate(etudBeginDate);
				dateDegreeCV.setEndDate(etudEndDate);
				}catch(Exception e){
					
				}
			degree.setStudiesLevel(CvEtude);
			degree.setDomain(CvEtudeDom);
			etabCv.setName(CvEtablissment);
			etabCv.setTown(CvEtudeVille);
			etabCv.setLand(CvEtudePays);
			
		
			degree.getAssociationDateDegreeCV().add(dateDegreeCV);
			degree.setAssociationDateDegreeCV(degree.getAssociationDateDegreeCV());
			degree.setEts(etabCv);
			curriculum.getDegs().add(dateDegreeCV);
			curriculum.setDegs(curriculum.getDegs());
			dateDegreeCV.setCurriculum(curriculum);
			dateDegreeCV.setDegree(degree);

			em.persist(dateDegreeCV);
			em.persist(degree);
			em.persist(etabCv);
			
			d++;
		}
		
		int l=0;
		while(l< nbloisir){
			HobbiesCV loisir=new HobbiesCV();
		String CvNomLoisir = request.getParameter("CvNomLoisir"+l);
		loisir.setName(CvNomLoisir);
		em.persist(loisir);
		curriculum.getHobs().add(loisir);
		curriculum.setHobs(curriculum.getHobs());
		l++;
		}

		em.persist(curriculum);

		em.getTransaction().commit();
		em.close();

		//

		return mapping.findForward("success");
		}catch(NumberFormatException e){
			return mapping.findForward("success");
			
		}
	}
	
}
