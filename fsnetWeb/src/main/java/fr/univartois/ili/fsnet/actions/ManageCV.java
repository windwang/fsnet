package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.AssociationDateDegreeCV;
import fr.univartois.ili.fsnet.entities.AssociationDateFormationCV;
import fr.univartois.ili.fsnet.entities.AssociationDateTrainingCV;
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.DegreeCV;
import fr.univartois.ili.fsnet.entities.EstablishmentCV;
import fr.univartois.ili.fsnet.entities.FormationCV;
import fr.univartois.ili.fsnet.entities.HobbiesCV;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.MemberCV;
import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.TrainingCV;
import fr.univartois.ili.fsnet.facade.CvFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;

/**
 * @author fsnet
 * 
 */
public class ManageCV extends MappingDispatchAction {

	private static final String SUCCESS_ACTION_NAME = "success";
	private static final String UNAUTHORIZED_ACTION_NAME = "unauthorized";
	
	private static final String CV_TITLE_FIELD_FORM_NAME = "cvTitle";
	private static final String CV_FIRSTNAME_FIELD_FORM_NAME = "cvFirstname";
	private static final String CV_SURNAME_FIELD_FORM_NAME = "cvSurname";
	private static final String CV_ADDRESS_FIELD_FORM_NAME = "cvAddress";
	private static final String CV_CITY_FIELD_FORM_NAME = "cvCity";
	private static final String CV_PHONE_FIELD_FORM_NAME = "cvPhone";
	private static final String CV_CP_FIELD_FORM_NAME = "cvCP";
	private static final String CV_COUNTRY_FIELD_FORM_NAME = "cvCountry";
	private static final String CV_MAIL_FIELD_FORM_NAME = "cvMail";
	private static final String CV_BIRTHDAY_FIELD_FORM_NAME = "cvBirthDay";
	private static final String CV_SITUATION_FIELD_FORM_NAME = "cvSituation";
	private static final String CV_SEX_FIELD_FORM_NAME = "cvSexe";
	
	private static final String CV_LANG_NAME_FIELD_FORM_NAME = "cvLangName";
	private static final String CV_LANG_LEVEL_FIELD_FORM_NAME = "cvLangLevel";
	
	private static final String CV_EXP_FIRM_NAME_FIELD_FORM_NAME = "cvExpFirmName";
	private static final String CV_EXP_JOB_FIELD_FORM_NAME = "cvExpJob";
	private static final String CV_EXP_BEGIN_DATE_NAME_FIELD_FORM_NAME = "cvExpBeginDate";
	private static final String CV_EXP_END_DATE_NAME_FIELD_FORM_NAME = "cvExpEndDate";
	private static final String CV_EXP_COUNTRY_FIELD_FORM_NAME = "cvExpCountry";
	private static final String CV_EXP_CITY_FIELD_FORM_NAME = "cvExpCity";
	private static final String CV_EXP_DOMAIN_FIELD_FORM_NAME = "cvExpDomain";
	
	private static final String CV_TRAINING_NAME_FIELD_FORM_NAME = "cvTrainingName";
	private static final String CV_TRAINING_INSTITUTION_NAME_FIELD_FORM_NAME = "cvTrainingInstitution";
	private static final String CV_TRAINING_COUNTRY_FIELD_FORM_NAME = "cvTrainingCountry";
	private static final String CV_TRAINING_OBTAINING_DATE_FIELD_FORM_NAME = "cvTrainingObtainingDate";
	private static final String CV_TRAINING_CITY_FIELD_FORM_NAME = "cvTrainingCity";
	
	private static final String CV_DEGREE_NAME_FIELD_FORM_NAME = "cvDegreeName";
	private static final String CV_DEGREE_DOMAIN_FIELD_FORM_NAME = "cvDegreeDomain";
	private static final String CV_DEGREE_SCHOOL_FIELD_FORM_NAME = "cvDegreeSchool";
	private static final String CV_DEGREE_COUNTRY_FIELD_FORM_NAME = "cvDegreeCountry";
	private static final String CV_DEGREE_CITY_FIELD_FORM_NAME = "cvDegreeCity";
	private static final String CV_DEGREE_BEGIN_DATE_FIELD_FORM_NAME = "cvDegreeBeginDate";
	private static final String CV_DEGREE_END_DATE_FIELD_FORM_NAME = "cvDegreeEndDate";
	
	private static final String CV_HOBBY_NAME_FIELD_FORM_NAME = "cvHobbyName";
	
	private static SimpleDateFormat formatter = new SimpleDateFormat(
			"dd/MM/yyyy");

	/**
	 * @param sDate
	 * @return
	 * @throws ParseException
	 */
	public static java.util.Date stringToDate(String sDate)
			throws ParseException {
		return formatter.parse(sDate);
	}

	/**
	 * @param sDate
	 * @return
	 * @throws ParseException
	 */
	public static Date toDBDateFormat(String sDate) throws ParseException {
		return new Date(stringToDate(sDate).getTime());
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward cree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return mapping.findForward(SUCCESS_ACTION_NAME);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward displayProfile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession mysession = request.getSession();
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String cvTitle = (String) dynaForm.get(CV_TITLE_FIELD_FORM_NAME);
		String cvNom = (String) dynaForm.get(CV_FIRSTNAME_FIELD_FORM_NAME);
		String cvPrenom = (String) dynaForm.get(CV_SURNAME_FIELD_FORM_NAME);
		String cvAdresse = (String) dynaForm.get(CV_ADDRESS_FIELD_FORM_NAME);
		String cvVille = (String) dynaForm.get(CV_CITY_FIELD_FORM_NAME);
		String cvPortable = (String) dynaForm.get(CV_PHONE_FIELD_FORM_NAME);
		String cvCp = (String) dynaForm.get(CV_CP_FIELD_FORM_NAME);
		String cvPays = (String) dynaForm.get(CV_COUNTRY_FIELD_FORM_NAME);
		String cvContact = (String) dynaForm.get(CV_MAIL_FIELD_FORM_NAME);
		String birthDay = (String) dynaForm.get(CV_BIRTHDAY_FIELD_FORM_NAME);
		mysession.setAttribute("action", true);
		mysession.setAttribute(CV_TITLE_FIELD_FORM_NAME, cvTitle);
		mysession.setAttribute(CV_FIRSTNAME_FIELD_FORM_NAME, cvNom);
		mysession.setAttribute(CV_SURNAME_FIELD_FORM_NAME, cvPrenom);
		mysession.setAttribute(CV_ADDRESS_FIELD_FORM_NAME, cvAdresse);
		mysession.setAttribute(CV_CITY_FIELD_FORM_NAME, cvVille);
		mysession.setAttribute(CV_PHONE_FIELD_FORM_NAME, cvPortable);
		mysession.setAttribute(CV_CP_FIELD_FORM_NAME, cvCp);
		mysession.setAttribute(CV_COUNTRY_FIELD_FORM_NAME, cvPays);
		mysession.setAttribute(CV_MAIL_FIELD_FORM_NAME, cvContact);
		mysession.setAttribute(CV_BIRTHDAY_FIELD_FORM_NAME, birthDay);
		mysession
				.setAttribute(CV_SITUATION_FIELD_FORM_NAME, request.getParameter("situation"));
		mysession.setAttribute(CV_SEX_FIELD_FORM_NAME, request.getParameter("sexe"));

		ActionErrors errors = new ActionErrors();
		int erreur = 0;

		if ("".equals(cvTitle)) {
			errors.add(CV_TITLE_FIELD_FORM_NAME, new ActionMessage("error.titre"));
			saveErrors(request, errors);
			erreur = 1;
		}

		if ("".equals(cvPortable)) {
			errors.add(CV_PHONE_FIELD_FORM_NAME, new ActionMessage("error.portable"));
			saveErrors(request, errors);
			erreur = 1;
		}
		
		if ("".equals(cvNom)) {
			errors.add(CV_FIRSTNAME_FIELD_FORM_NAME, new ActionMessage("error.nom"));
			saveErrors(request, errors);
			erreur = 1;
		}
		
		if ("".equals(cvPrenom)) {
			errors.add(CV_SURNAME_FIELD_FORM_NAME, new ActionMessage("error.prenom"));
			saveErrors(request, errors);
			erreur = 1;
		}
		
		if ("".equals(cvAdresse)) {
			errors.add(CV_ADDRESS_FIELD_FORM_NAME, new ActionMessage("error.adresse"));
			saveErrors(request, errors);
			erreur = 1;
		}
		
		if ("".equals(cvVille)) {
			errors.add(CV_CITY_FIELD_FORM_NAME, new ActionMessage("error.ville"));
			saveErrors(request, errors);
			erreur = 1;
		}
		if ("".equals(birthDay)) {
			errors.add(CV_BIRTHDAY_FIELD_FORM_NAME, new ActionMessage("error.birthDay"));
			saveErrors(request, errors);
			erreur = 1;
		}

		if ("".equals(cvPays)) {
			errors.add(CV_COUNTRY_FIELD_FORM_NAME, new ActionMessage("error.pays"));
			saveErrors(request, errors);
			erreur = 1;
		}
		
		if ("".equals(cvContact)) {
			errors.add(CV_MAIL_FIELD_FORM_NAME, new ActionMessage("error.contact"));
			saveErrors(request, errors);
			erreur = 1;
		}
		
		if ("".equals(cvCp)) {
			errors.add(CV_CP_FIELD_FORM_NAME, new ActionMessage("error.cp"));
			saveErrors(request, errors);
			erreur = 1;
		}
		
		if (!"".equals(cvCp)) {
			try {
				Integer.parseInt(cvCp);
			} catch (Exception e) {
				errors.add(CV_CP_FIELD_FORM_NAME, new ActionMessage("error.cpInt"));
				saveErrors(request, errors);
				erreur = 1;
			}
		}
		
		try {
			toDBDateFormat(birthDay);
		} catch (Exception e) {
			errors.add(CV_BIRTHDAY_FIELD_FORM_NAME, new ActionMessage(
					"error.birthDayValue"));
			saveErrors(request, errors);
			erreur = 1;
		}

		if (erreur == 1) {
			return mapping.findForward(UNAUTHORIZED_ACTION_NAME);
		} else {
			return mapping.findForward(SUCCESS_ACTION_NAME);
		}
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	public ActionForward displayExp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
		request.getSession().removeAttribute("action");
		try {
			int nbExp = Integer.parseInt(request.getParameter("nbexp"));
			int nbfrom = Integer.parseInt(request.getParameter("nbform"));
			int nblangue = Integer.parseInt(request.getParameter("nblangue"));
			int nbloisir = Integer.parseInt(request.getParameter("nbloisir"));
			int nbdip = Integer.parseInt(request.getParameter("nbdip"));

			Curriculum curriculum = new Curriculum();

			EntityManager em = PersistenceProvider.createEntityManager();

			HttpSession mysession = request.getSession();

			// Insert member
			MemberCV member = new MemberCV();
			try {
				member.setBirthDate((Date) toDBDateFormat((String) mysession
						.getAttribute(CV_BIRTHDAY_FIELD_FORM_NAME)));
			} catch (Exception e) {

			}
			
			member.setFirstName((String) mysession.getAttribute(CV_FIRSTNAME_FIELD_FORM_NAME));
			member.setSurname((String) mysession.getAttribute(CV_SURNAME_FIELD_FORM_NAME));
			member.setNumberPhone((String) mysession.getAttribute(CV_PHONE_FIELD_FORM_NAME));
			member.setMail((String) mysession.getAttribute(CV_MAIL_FIELD_FORM_NAME));
			member.setTown((String) mysession.getAttribute(CV_COUNTRY_FIELD_FORM_NAME));
			member.setAdress((String) mysession.getAttribute(CV_ADDRESS_FIELD_FORM_NAME));
			member.setPostCode(Integer.parseInt((String) mysession
					.getAttribute(CV_CP_FIELD_FORM_NAME)));
			member.setSituationFamilly((String) mysession
					.getAttribute(CV_SITUATION_FIELD_FORM_NAME));
			member.setSex((String) mysession.getAttribute(CV_SEX_FIELD_FORM_NAME));
			
			// Insert langs
			int lang = 0;
			HashMap<String, String> languages = new HashMap<String, String>();
			while (lang < nblangue) {
				String cvNameLang = request.getParameter(CV_LANG_NAME_FIELD_FORM_NAME + lang);
				String cvLevelLang = request.getParameter(CV_LANG_LEVEL_FIELD_FORM_NAME + lang);
				languages.put(cvNameLang, cvLevelLang);
				lang++;
			}
			SocialEntity mem = UserUtils.getAuthenticatedUser(request, em); 
			member.setLanguages(languages);
			em.getTransaction().begin();
			em.persist(member);
			curriculum.setMember(member);
			curriculum.setTitleCv((String) mysession.getAttribute(CV_TITLE_FIELD_FORM_NAME));

			// Insert professional experiences
			int i = 0;

			while (i < nbExp) {
				TrainingCV training = new TrainingCV();

				AssociationDateTrainingCV dateTaining = new AssociationDateTrainingCV();
				EstablishmentCV etab = new EstablishmentCV();
				String nomEntreprise = request
						.getParameter(CV_EXP_FIRM_NAME_FIELD_FORM_NAME + i);

				String cvPoste = request.getParameter(CV_EXP_JOB_FIELD_FORM_NAME + i);
				try {
					Date expBeginDate = toDBDateFormat(request
							.getParameter(CV_EXP_BEGIN_DATE_NAME_FIELD_FORM_NAME + i));
					Date expEndDate = toDBDateFormat(request
							.getParameter(CV_EXP_END_DATE_NAME_FIELD_FORM_NAME + i));

					dateTaining.setStartDate(expBeginDate);
					dateTaining.setEndDate(expEndDate);
				} catch (Exception e) {

				}
				String cvSecteur = request.getParameter(CV_EXP_DOMAIN_FIELD_FORM_NAME + i);
				etab.setName(nomEntreprise);
				etab.setLand(CV_EXP_COUNTRY_FIELD_FORM_NAME + i);
				etab.setTown(CV_EXP_CITY_FIELD_FORM_NAME + i);

				training.setName(cvPoste);
				training.setSpeciality(cvSecteur);

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

			// Insert trainings
			int f = 0;
			while (f < nbfrom) {

				AssociationDateFormationCV dateFormation = new AssociationDateFormationCV();
				FormationCV formation = new FormationCV();
				EstablishmentCV etab = new EstablishmentCV();
				String cvFormation = request.getParameter(CV_TRAINING_NAME_FIELD_FORM_NAME + f);
				String cvEtablissmentform = request
						.getParameter(CV_TRAINING_INSTITUTION_NAME_FIELD_FORM_NAME + f);
				String cvFormPays = request.getParameter(CV_TRAINING_COUNTRY_FIELD_FORM_NAME + f);
				try {
					Date dateObtention = toDBDateFormat(request
							.getParameter(CV_TRAINING_OBTAINING_DATE_FIELD_FORM_NAME + f));
					dateFormation.setObtainedDate(dateObtention);
				} catch (Exception e) {

				}
				String cvFormVille = request.getParameter(CV_TRAINING_CITY_FIELD_FORM_NAME + f);

				etab.setName(cvEtablissmentform);
				etab.setTown(cvFormVille);
				etab.setLand(cvFormPays);

				formation.getAssociationDateFormationCV().add(dateFormation);
				formation.setAssociationDateFormationCV(formation
						.getAssociationDateFormationCV());
				formation.setName(cvFormation);
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

			// Insert degrees
			int d = 0;
			while (d < nbdip) {
				DegreeCV degree = new DegreeCV();
				AssociationDateDegreeCV dateDegreeCV = new AssociationDateDegreeCV();
				EstablishmentCV etabCv = new EstablishmentCV();
				String cvEtude = request.getParameter(CV_DEGREE_NAME_FIELD_FORM_NAME + d);
				String cvEtudeDom = request.getParameter(CV_DEGREE_DOMAIN_FIELD_FORM_NAME + d);
				String cvEtablissment = request.getParameter(CV_DEGREE_SCHOOL_FIELD_FORM_NAME
						+ d);
				String cvEtudePays = request.getParameter(CV_DEGREE_COUNTRY_FIELD_FORM_NAME + d);
				String cvEtudeVille = request.getParameter(CV_DEGREE_CITY_FIELD_FORM_NAME + d);
				try {
					Date etudBeginDate = toDBDateFormat(request
							.getParameter(CV_DEGREE_BEGIN_DATE_FIELD_FORM_NAME + d));
					Date etudEndDate = toDBDateFormat(request
							.getParameter(CV_DEGREE_END_DATE_FIELD_FORM_NAME + d));

					dateDegreeCV.setStartDate(etudBeginDate);
					dateDegreeCV.setEndDate(etudEndDate);
				} catch (Exception e) {

				}
				degree.setStudiesLevel(cvEtude);
				degree.setDomain(cvEtudeDom);
				etabCv.setName(cvEtablissment);
				etabCv.setTown(cvEtudeVille);
				etabCv.setLand(cvEtudePays);

				degree.getAssociationDateDegreeCV().add(dateDegreeCV);
				degree.setAssociationDateDegreeCV(degree
						.getAssociationDateDegreeCV());
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

			// Insert hobbies
			int l = 0;
			while (l < nbloisir) {
				HobbiesCV loisir = new HobbiesCV();
				String cvNomLoisir = request.getParameter(CV_HOBBY_NAME_FIELD_FORM_NAME + l);
				loisir.setName(cvNomLoisir);
				em.persist(loisir);
				curriculum.getHobs().add(loisir);
				curriculum.setHobs(curriculum.getHobs());
				l++;
			}
			curriculum.setUserId(mem.getId());
			em.persist(curriculum);

			em.getTransaction().commit();
			em.close();

			//

			return mapping.findForward(SUCCESS_ACTION_NAME);
		} catch (NumberFormatException e) {
			return mapping.findForward(SUCCESS_ACTION_NAME);
		}
	}

	/**
	 * @param request
	 */
	private void addRightToRequest(HttpServletRequest request) {
		SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request);
		Right rightAddEvent = Right.ADD_EVENT;
		Right rightRegisterEvent = Right.REGISTER_EVENT;
		request.setAttribute("rightAddEvent", rightAddEvent);
		request.setAttribute("rightRegisterEvent", rightRegisterEvent);
		request.setAttribute("socialEntity", socialEntity);
	}

	/**
	 * @param request
	 * @param em
	 */
	public static final void refreshNumNewEvents(HttpServletRequest request,
			EntityManager em) {
		HttpSession session = request.getSession();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		InteractionFacade inf = new InteractionFacade(em);
		List<Interaction> list = inf.getUnreadInteractionsForSocialEntity(user);
		int numNonReedEvents = Interaction.filter(list, Meeting.class).size();
		session.setAttribute("numNonReedEvents", numNonReedEvents);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward displayCv(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		EntityManager em = PersistenceProvider.createEntityManager();
		addRightToRequest(request);
		SocialEntity mem = UserUtils.getAuthenticatedUser(request, em);
		em.getTransaction().begin();

		CvFacade cvfacade = new CvFacade(em);
		List<Curriculum> results = cvfacade.listAllCv(mem.getId());
		
		em.close();

		request.setAttribute("CVsList", results);

		return mapping.findForward(SUCCESS_ACTION_NAME);
	}
}
