package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

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
public class ManageCV extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 3871072232416409836L;

	private Map<String,Object> mysession ;
	private String cvTitle;
	private String cvFirstname;
	private String cvSurname;
	private String cvAddress;
	private String cvCity;
	private String cvPhone;
	private String cvCP;
	private String cvCountry;
	private String cvMail;
	private String cvBirthDay;
	private String cvSituation;
	private String cvSexe;

	private static final String UNAUTHORIZED_ACTION_NAME = "unauthorized";

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

	/**
	 * @param sDate
	 * @return
	 * @throws ParseException
	 */
	public static java.util.Date stringToDate(String sDate)
			throws ParseException {
		return new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
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
	public String cree(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return SUCCESS;
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
	public String displayProfile(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
//		HttpSession mysession = request.getSession();

		mysession.put("action", true);
		mysession.put("cvTitle", cvTitle);
		mysession.put("cvFirstname", cvFirstname);
		mysession.put("cvSurname", cvSurname);
		mysession.put("cvAddress", cvAddress);
		mysession.put("cvCity", cvCity);
		mysession.put("cvPhone", cvPhone);
		mysession.put("cvCP", cvCP);
		mysession.put("cvCountry", cvCountry);
		mysession.put("cvMail", cvMail);
		mysession.put("cvBirthDay", cvBirthDay);
		mysession.put("cvSituation",request.getParameter("situation"));
		mysession.put("cvSexe",request.getParameter("sexe"));

		int erreur = 0;

		if ("".equals(cvTitle)) {
			addFieldError("cvTitle","error.titre");
			erreur = 1;
		}

		if ("".equals(cvPhone)) {
			addFieldError("cvPhone","error.portable");
			erreur = 1;
		}

		if ("".equals(cvSurname)) {
			addFieldError("cvSurname","error.nom");
			erreur = 1;
		}

		if ("".equals(cvFirstname)) {
			addFieldError("cvFirstname","error.prenom");
			erreur = 1;
		}

		if ("".equals(cvAddress)) {
			addFieldError("cvAddress","error.adresse");
			erreur = 1;
		}

		if ("".equals(cvCountry)) {
			addFieldError("cvCountry","error.ville");
			erreur = 1;
		}
		if ("".equals(cvBirthDay)) {
			addFieldError("cvBirthDay","error.birthDay");
			erreur = 1;
		}

		if ("".equals(cvCountry)) {
			addFieldError("cvCountry","error.pays");
			erreur = 1;
		}

		if ("".equals(cvMail)) {
			addFieldError("cvMail","error.contact");
			erreur = 1;
		}

		if ("".equals(cvCP)) {
			addFieldError("cvCP","error.cp");
			erreur = 1;
		} else {
			try {
				Integer.parseInt(cvCP);
			} catch (Exception e) {
				addFieldError("cvCP","error.cpInt");
				erreur = 1;
			}
		}

		try {
			toDBDateFormat(cvBirthDay);
		} catch (Exception e) {
			addFieldError("cvBirthDay","error.birthDayValue");
			erreur = 1;
		}

		if (erreur == 1) {
			return UNAUTHORIZED_ACTION_NAME;
		} else {
			return SUCCESS;
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
	public String displayExp(HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		request.getSession().removeAttribute("action");
		try {
			int nbExp = Integer.parseInt(request.getParameter("nbExp"));
			int nbForm = Integer.parseInt(request.getParameter("nbForm"));
			int nbLang = Integer.parseInt(request.getParameter("nbLang"));
			int nbHobby = Integer.parseInt(request.getParameter("nbHobbie"));
			int nbDegree = Integer.parseInt(request.getParameter("nbDegree"));

			Curriculum curriculum = new Curriculum();

			EntityManager em = PersistenceProvider.createEntityManager();

			HttpSession mysession = request.getSession();

			// Insert member
			MemberCV member = new MemberCV();
			try {
				member.setBirthDate((Date) toDBDateFormat((String) mysession
						.getAttribute(cvBirthDay)));
			} catch (Exception e) {

			}

			member.setFirstName((String) mysession
					.getAttribute(cvFirstname));
			member.setSurname((String) mysession
					.getAttribute(cvSurname));
			member.setNumberPhone((String) mysession
					.getAttribute(cvPhone));
			member.setMail((String) mysession
					.getAttribute(cvMail));
			member.setTown((String) mysession
					.getAttribute(cvCountry));
			member.setAdress((String) mysession
					.getAttribute(cvAddress));
			member.setPostCode(Integer.parseInt((String) mysession
					.getAttribute(cvCP)));
			member.setSituationFamilly((String) mysession
					.getAttribute(cvSituation));
			member.setSex((String) mysession
					.getAttribute(cvSexe));

			// Insert langs
			int lang = 0;
			HashMap<String, String> languages = new HashMap<String, String>();
			while (lang < nbLang) {
				String cvNameLang = request
						.getParameter(CV_LANG_NAME_FIELD_FORM_NAME + lang);
				String cvLevelLang = request
						.getParameter(CV_LANG_LEVEL_FIELD_FORM_NAME + lang);
				languages.put(cvNameLang, cvLevelLang);
				lang++;
			}
			SocialEntity mem = UserUtils.getAuthenticatedUser(request, em);
			member.setLanguages(languages);
			em.getTransaction().begin();
			em.persist(member);
			curriculum.setMember(member);
			curriculum.setTitleCv((String) mysession
					.getAttribute(cvTitle));

			// Insert professional experiences
			int i = 0;

			while (i < nbExp) {
				TrainingCV training = new TrainingCV();

				AssociationDateTrainingCV dateTaining = new AssociationDateTrainingCV();
				EstablishmentCV etab = new EstablishmentCV();
				String nomEntreprise = request
						.getParameter(CV_EXP_FIRM_NAME_FIELD_FORM_NAME + i);

				String cvPoste = request
						.getParameter(CV_EXP_JOB_FIELD_FORM_NAME + i);
				try {
					Date expBeginDate = toDBDateFormat(request
							.getParameter(CV_EXP_BEGIN_DATE_NAME_FIELD_FORM_NAME
									+ i));
					Date expEndDate = toDBDateFormat(request
							.getParameter(CV_EXP_END_DATE_NAME_FIELD_FORM_NAME
									+ i));

					dateTaining.setStartDate(expBeginDate);
					dateTaining.setEndDate(expEndDate);
				} catch (Exception e) {

				}
				String cvSecteur = request
						.getParameter(CV_EXP_DOMAIN_FIELD_FORM_NAME + i);
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
			while (f < nbForm) {

				AssociationDateFormationCV dateFormation = new AssociationDateFormationCV();
				FormationCV formation = new FormationCV();
				EstablishmentCV etab = new EstablishmentCV();
				String cvFormation = request
						.getParameter(CV_TRAINING_NAME_FIELD_FORM_NAME + f);
				String cvEtablissmentform = request
						.getParameter(CV_TRAINING_INSTITUTION_NAME_FIELD_FORM_NAME
								+ f);
				String cvFormPays = request
						.getParameter(CV_TRAINING_COUNTRY_FIELD_FORM_NAME + f);
				try {
					Date dateObtention = toDBDateFormat(request
							.getParameter(CV_TRAINING_OBTAINING_DATE_FIELD_FORM_NAME
									+ f));
					dateFormation.setObtainedDate(dateObtention);
				} catch (Exception e) {

				}
				String cvFormVille = request
						.getParameter(CV_TRAINING_CITY_FIELD_FORM_NAME + f);

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
			while (d < nbDegree) {
				DegreeCV degree = new DegreeCV();
				AssociationDateDegreeCV dateDegreeCV = new AssociationDateDegreeCV();
				EstablishmentCV etabCv = new EstablishmentCV();
				String cvEtude = request
						.getParameter(CV_DEGREE_NAME_FIELD_FORM_NAME + d);
				String cvEtudeDom = request
						.getParameter(CV_DEGREE_DOMAIN_FIELD_FORM_NAME + d);
				String cvEtablissment = request
						.getParameter(CV_DEGREE_SCHOOL_FIELD_FORM_NAME + d);
				String cvEtudePays = request
						.getParameter(CV_DEGREE_COUNTRY_FIELD_FORM_NAME + d);
				String cvEtudeVille = request
						.getParameter(CV_DEGREE_CITY_FIELD_FORM_NAME + d);
				try {
					Date etudBeginDate = toDBDateFormat(request
							.getParameter(CV_DEGREE_BEGIN_DATE_FIELD_FORM_NAME
									+ d));
					Date etudEndDate = toDBDateFormat(request
							.getParameter(CV_DEGREE_END_DATE_FIELD_FORM_NAME
									+ d));

					dateDegreeCV.setStartDate(etudBeginDate);
					dateDegreeCV.setEndDate(etudEndDate);
				} catch (Exception e) {
					Logger.getAnonymousLogger().log(Level.WARNING, "", e);
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
			while (l < nbHobby) {
				HobbiesCV loisir = new HobbiesCV();
				String cvNomLoisir = request
						.getParameter(CV_HOBBY_NAME_FIELD_FORM_NAME + l);
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

			return SUCCESS;
		} catch (NumberFormatException e) {
			return SUCCESS;
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
	public String displayCv(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		EntityManager em = PersistenceProvider.createEntityManager();
		addRightToRequest(request);
		SocialEntity mem = UserUtils.getAuthenticatedUser(request, em);
		em.getTransaction().begin();

		CvFacade cvfacade = new CvFacade(em);
		List<Curriculum> results = cvfacade.listAllCv(mem.getId());

		em.close();

		request.setAttribute("CVsList", results);

		return SUCCESS;
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
	public String delete(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			long id = Integer.parseInt(request.getParameter("idCv"));
			CvFacade cvFacade = new CvFacade(em);
			Curriculum curriculum = cvFacade.getCurriculum(id);
			em.getTransaction().begin();
			em.remove(curriculum);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			return INPUT;
		} finally {
			em.close();
		}

		return INPUT;
	}

	@Override
	public void setSession(Map<String, Object> mysession) {
		this.mysession = mysession; 
		
	}
}
