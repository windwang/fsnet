package fr.univartois.ili.fsnet.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.routines.IntegerValidator;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

import fr.univartois.ili.fsnet.actions.utils.ConsultationChoiceComparator;
import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.mail.FSNetConfiguration;
import fr.univartois.ili.fsnet.commons.mail.FSNetMailer;
import fr.univartois.ili.fsnet.commons.mail.Mail;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Consultation;
import fr.univartois.ili.fsnet.entities.Consultation.TypeConsultation;
import fr.univartois.ili.fsnet.entities.ConsultationChoice;
import fr.univartois.ili.fsnet.entities.ConsultationChoiceVote;
import fr.univartois.ili.fsnet.entities.ConsultationVote;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.facade.ConsultationFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;
import fr.univartois.ili.fsnet.filter.FilterInteractionByUserGroup;

/**
 * @author FSNet
 * 
 */
public class ManageConsultations extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String DEADLINE_TIME = ":23:59:59";

	private static final String NO_ANSWER = "no";
	private static final String REGEX_CONSULTATION_CHOICE = ";";

	private static final String FAILED_ACTION_NAME = "failed";
	private static final String UNAUTHORIZED_ACTION_NAME = "unauthorized";

	private List<String> listTypeKey;
	private List<String> listTypeValue;
	
	private int minChoicesVoter;
	private int maxChoicesVoter;
	private String consultationTitle;
	private String consultationDescription;

	private int idConsultation;

	private String voteOther;

	private String voteComment;

	private String searchText;

	private String[] selectedConsultations;
	private String[] voteChoice;
	private String[] groupsListRight;
	private String consultationChoice;
	private String maxVoters;

	private String limitChoicesPerVoter;

	private String nbVotersPerChoiceBox;

	private String showBeforeAnswer;

	private String showBeforeClosing;

	private String consultationType;

	private String deadline;

	private String closingAtMaxVoters;

	private String consultationIfNecessaryWeight;

	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public ManageConsultations() {
		listTypeKey=new ArrayList<>();
		listTypeValue=new ArrayList<>();
		
		listTypeValue.add("YES_NO");
		listTypeValue.add("YES_NO_OTHER");
		listTypeValue.add("YES_NO_IFNECESSARY");
		listTypeValue.add("PREFERENCE_ORDER");
		
		listTypeKey.add("consultations.form.typeYesNo");
		listTypeKey.add("consultations.form.typeYesNoOther");
		listTypeKey.add("consultations.form.typeYesNoIfNecessary");
		listTypeKey.add("consultations.form.typePreferenceOrder");
		
	}

	public String displayCreate(){
		
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String create() throws Exception {
		
		if(consultationChoice == null){
			return FAILED_ACTION_NAME;
		}
		
		String[] consultationChoices = consultationChoice
				.split(REGEX_CONSULTATION_CHOICE);
		String[] maxVoterz = maxVoters.split(REGEX_CONSULTATION_CHOICE);

		addRightToRequest(request);

		if (!"".equals(limitChoicesPerVoter)
				&& minChoicesVoter > maxChoicesVoter) {
			request.setAttribute("errorChoicesVoter", true);
			return FAILED_ACTION_NAME;
		}

		for (int i = 0; i < maxVoterz.length; i++) {
			if ("".equals(maxVoterz[i])) {
				maxVoterz[i] = "-1"; // Unlimited
			} else if (!IntegerValidator.getInstance().isValid(maxVoterz[i])
					|| Integer.valueOf(maxVoterz[i]) < 1) {
				request.setAttribute("errorMaxVotersPerChoice", true);
				return FAILED_ACTION_NAME;
			}
		}
		if (groupsListRight.length == 0) {
			request.setAttribute("errorRights", true);
			return FAILED_ACTION_NAME;
		}

		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		if (!fascade.isAuthorized(member, Right.ADD_CONSULTATION)) {
			return UNAUTHORIZED_ACTION_NAME;
		}
		em.getTransaction().begin();
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		List<SocialGroup> listOfGroupAccepted = new ArrayList<SocialGroup>();
		for (String name : groupsListRight) {
			listOfGroupAccepted.add(fascade.findByName(name));
		}
		Consultation consultation = consultationFacade.createConsultation(
				member, consultationTitle, consultationDescription,
				consultationChoices,
				Consultation.TypeConsultation.valueOf(consultationType),
				listOfGroupAccepted);

		if (!"".equals(nbVotersPerChoiceBox)) {
			consultation.setLimitParticipantPerChoice(true);
			for (int i = 0; i < maxVoterz.length; i++) {
				consultation.getChoices().get(i)
						.setMaxVoters(Integer.valueOf(maxVoterz[i]));
			}
		}
		if (!"".equals(showBeforeAnswer)) {
			consultation.setShowBeforeAnswer(false);
		}

		if (!"".equals(showBeforeClosing)) {
			consultation.setShowBeforeClosing(false);
		}

		if (!"".equals(deadline)) {
			consultation.setClosingAtDate(true);
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy:hh:mm:ss");
				consultation.setMaxDate(dateFormat.parse(deadline
						+ DEADLINE_TIME));
			} catch (Exception e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
			}
		}

		if (!"".equals(closingAtMaxVoters)) {
			consultation.setClosingAtMaxVoters(true);
			consultation.setMaxVoters(Integer.valueOf(closingAtMaxVoters));
		}

		if (consultationIfNecessaryWeight != null && !"".equals(consultationIfNecessaryWeight)) {
			consultation.setIfNecessaryWeight(Double
					.valueOf(consultationIfNecessaryWeight));
		} else {
			consultation.setIfNecessaryWeight(0.5);
		}

		if (!"".equals(limitChoicesPerVoter) && limitChoicesPerVoter.equals("true")) {
			consultation.setLimitChoicesPerParticipant(true);
			if (!"".equals(maxChoicesVoter)) {
				consultation.setLimitChoicesPerParticipantMax(Integer
						.valueOf(maxChoicesVoter));
			}
			if (!"".equals(minChoicesVoter)) {
				consultation.setLimitChoicesPerParticipantMin(Integer
						.valueOf(minChoicesVoter));
			}
		}

		em.getTransaction().commit();
		em.close();

		request.setAttribute("id", consultation.getId());

		sendMailForNewConsultation(consultation, member);

		return displayAConsultation(request, response);
	}

	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String vote() throws Exception {

		addRightToRequest(request);
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		Consultation consultation = consultationFacade
				.getConsultation(idConsultation);

		// SocialGroupFacade fascade = new SocialGroupFacade(em);
		// if (!fascade.isSuperAdmin(member)
		// && !fascade.getAllGroupsChildSelfInclude(member.getGroup())
		// .contains(consultation.getCreator().getGroup())) {
		// return new ActionRedirect(
		// mapping.findForward(UNAUTHORIZED_ACTION_NAME));
		// }

		List<String> voteChoices = new ArrayList<>(Arrays.asList(voteChoice));

		if (consultation.isLimitChoicesPerParticipant()) {
			int answersNumber = 0;
			if (TypeConsultation.YES_NO_IFNECESSARY.equals(consultation
					.getType())) {
				for (String s : voteChoices) {
					if (!s.startsWith(NO_ANSWER)) {
						answersNumber++;
					}
				}
			} else if (TypeConsultation.YES_NO_OTHER.equals(consultation
					.getType())) {
				answersNumber = voteChoices.size()
						+ ("".equals(voteOther) ? 0 : 1);
			} else {
				answersNumber = voteChoices.size();
			}
			if (answersNumber < consultation.getLimitChoicesPerParticipantMin()
					|| answersNumber > consultation
							.getLimitChoicesPerParticipantMax()) {

				request.setAttribute("errorChoicesPerParticipant", true);
				return displayAConsultation(request, response);
			}
		}

		em.getTransaction().begin();
		if (isAllowedToVote(consultation, member)) {
			ConsultationVote vote = new ConsultationVote(member, voteComment,
					voteOther);
			boolean voteOk = true;
			switch (consultation.getType()) {
			case PREFERENCE_ORDER:
				voteOk = votePreferenceOrder(request, consultation, vote,
						voteChoices);
				break;
			case YES_NO_IFNECESSARY:
				voteOk = voteIfNecessary(consultation, vote, voteChoices);
				break;
			default:
				for (ConsultationChoice choice : consultation.getChoices()) {
					if (voteChoices.contains(String.valueOf(choice.getId()))) {
						vote.getChoices().add(
								new ConsultationChoiceVote(vote, choice));
					}
				}
			}

			if (consultation.getType() != Consultation.TypeConsultation.PREFERENCE_ORDER
					&& consultation.isLimitParticipantsPerChoice()) {
				for (ConsultationChoice choice : consultation.getChoices()) {
					int nbVotes = 0;
					for (ConsultationVote cv : consultation
							.getConsultationVotes()) {
						for (ConsultationChoiceVote ccv : cv.getChoices()) {
							if (ccv.getChoice().equals(choice)) {
								nbVotes++;
							}
						}
					}

					if (nbVotes > choice.getMaxVoters()) {
						return displayAConsultation(request, response);
					}
				}
			}

			if (voteOk) {
				consultationFacade.voteForConsultation(consultation, vote);
				em.getTransaction().commit();
			}
		}

		em.close();

		return displayAConsultation(request, response);
	}

	/**
	 * @param consultation
	 * @param vote
	 * @param choices
	 * @return
	 */
	private boolean voteIfNecessary(Consultation consultation,
			ConsultationVote vote, List<String> choices) {
		for (String choice : choices) {
			if (!choice.startsWith("no")) {
				boolean ifNecessary;
				Integer id;
				if (choice.startsWith("ifNecessary")) {
					id = Integer.valueOf(choice.replaceAll("ifNecessary", ""));
					ifNecessary = true;
				} else {
					id = Integer.valueOf(choice.replaceAll("yes", ""));
					ifNecessary = false;
				}
				for (ConsultationChoice choiceCons : consultation.getChoices()) {
					if (choiceCons.getId() == id) {
						ConsultationChoiceVote choiceVote = new ConsultationChoiceVote(
								vote, choiceCons);
						if (ifNecessary) {
							choiceVote.setIfNecessary(true);
						}
						vote.getChoices().add(choiceVote);
					}
				}
			}
		}
		return true;
	}

	/**
	 * @param request
	 * @param consultation
	 * @param vote
	 * @param choices
	 * @return boolean
	 */
	private boolean votePreferenceOrder(HttpServletRequest request,
			Consultation consultation, ConsultationVote vote,
			List<String> choices) {
		List<Integer> marks = new ArrayList<Integer>();
		for (String choice : choices) {
			for (ConsultationChoice consChoice : consultation.getChoices()) {
				String[] choiceValues = choice.split("_");
				if (consChoice.getId() == Integer.valueOf(choiceValues[0])) {
					ConsultationChoiceVote choiceVote = new ConsultationChoiceVote(
							vote, consChoice);
					choiceVote.setPreferenceOrder(Integer
							.valueOf(choiceValues[1]));
					vote.getChoices().add(choiceVote);
					if (marks.contains(Integer.valueOf(choiceValues[1]))) {
						request.setAttribute("errorPreferenceOrderDistinct",
								true);
						return false;
					}
					marks.add(Integer.valueOf(choiceValues[1]));
				}
			}
		}
		return true;
	}

	
	/**
	 * 
	 * @return String
	 */
	public String deleteVote() {
		String idConsultation = request.getParameter("consultation");
		String idVote = request.getParameter("vote");
		if (idConsultation != null && !"".equals(idConsultation)) {
			request.setAttribute("id", idConsultation);
			if (idVote != null && !"".equals(idVote)) {
				EntityManager em = PersistenceProvider.createEntityManager();
				SocialEntity member = UserUtils.getAuthenticatedUser(request,
						em);
				ConsultationFacade consultationFacade = new ConsultationFacade(
						em);
				Consultation consultation = consultationFacade
						.getConsultation(Integer.valueOf(idConsultation));
				ConsultationVote vote = consultationFacade.getVote(Integer
						.valueOf(idVote));
				if (consultation.isOpened()) {
					em.getTransaction().begin();
					consultationFacade.deleteVote(consultation, member, vote);
					em.getTransaction().commit();
				}
				em.close();
			}
		}
		return displayAConsultation(request, response);
	}

	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String searchYourConsultations() throws Exception {
		addRightToRequest(request);
		EntityManager em = PersistenceProvider.createEntityManager();
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		List<Consultation> listConsultations = consultationFacade
				.getUserConsultations(member);
		request.setAttribute("consultationsList", listConsultations);
		InteractionFacade interactionFacade = new InteractionFacade(em);
		List<Integer> unreadInteractionsId = interactionFacade
				.getUnreadInteractionsIdForSocialEntity(member);
		request.setAttribute("unreadInteractionsId", unreadInteractionsId);
		return SUCCESS;
	}

	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String searchConsultation() throws Exception {
		addRightToRequest(request);
		EntityManager em = PersistenceProvider.createEntityManager();
		ConsultationFacade consultationFacade = new ConsultationFacade(em);

		List<Consultation> searchConsultations = consultationFacade
				.getConsultationsContaining(searchText);
		if (searchConsultations != null && !searchConsultations.isEmpty()) {
			FilterInteractionByUserGroup filter = new FilterInteractionByUserGroup(
					em);
			SocialEntity se = UserUtils.getAuthenticatedUser(request);
			searchConsultations = filter.filterInteraction(se,
					searchConsultations);
		}
		request.setAttribute("consultationsSearchList", searchConsultations);

		em.getTransaction().begin();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		InteractionFacade interactionFacade = new InteractionFacade(em);
		List<Integer> unreadInteractionsId = interactionFacade
				.getUnreadInteractionsIdForSocialEntity(member);
		refreshNumNewConsultations(request, em);
		em.getTransaction().commit();
		em.close();
		request.setAttribute("unreadInteractionsId", unreadInteractionsId);

		return SUCCESS;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String displayAConsultation(HttpServletRequest request,
			HttpServletResponse response) {
		addRightToRequest(request);
		String idConsultation = request.getParameter("id");
		if (idConsultation == null || "".equals(idConsultation)) {
			idConsultation = String.valueOf(request.getAttribute("id"));
		}
		if (idConsultation != null) {
			EntityManager em = PersistenceProvider.createEntityManager();
			SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
			request.setAttribute("member", member);
			ConsultationFacade consultationFacade = new ConsultationFacade(em);
			Consultation consultation = consultationFacade
					.getConsultation(Integer.valueOf(idConsultation));
			if ((consultation == null)) {
				return UNAUTHORIZED_ACTION_NAME;
			}
			Collections.sort(consultation.getChoices(),
					new ConsultationChoiceComparator());

			FilterInteractionByUserGroup filterGroup = new FilterInteractionByUserGroup(
					em);
			if ((filterGroup.filterAnInteraction(member, consultation) == null)) {
				return UNAUTHORIZED_ACTION_NAME;
			}

			em.getTransaction().begin();
			member.addInteractionRead(consultation);
			refreshNumNewConsultations(request, em);
			em.getTransaction().commit();

			request.setAttribute("consultation", consultation);
			if (isAllowedToVote(consultation, member)) {
				request.setAttribute("allowedToVote", true);
			} else {
				request.setAttribute("allowedToVote", false);
			}
			request.setAttribute("allowedToShowResults",
					isAllowedToShowResults(consultation, member));
			if (consultation.isLimitParticipantsPerChoice()) {
				List<String> disabledList = new ArrayList<String>();
				for (ConsultationChoice choice : consultation.getChoices()) {
					int nbVotes = consultationFacade.getChoicesVote(
							choice.getId()).size();
					if (nbVotes >= choice.getMaxVoters()) {
						disabledList.add("true");
					} else {
						disabledList.add("false");
					}
				}
				request.setAttribute("disabledList", disabledList);
			}
			em.close();
		}

		return SUCCESS;
	}

	/**
	 * 
	 * @return String
	 */
	public String closeConsultation() {
		String idConsultation = request.getParameter("id");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		Consultation consultation = consultationFacade.getConsultation(Integer
				.valueOf(idConsultation));
		if (member.equals(consultation.getCreator())) {
			em.getTransaction().begin();
			consultationFacade.closeConsultation(consultation);
			em.getTransaction().commit();
			em.close();
		}
		return displayAConsultation(request, response);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String openConsultation() {
		String idConsultation = request.getParameter("id");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		Consultation consultation = consultationFacade.getConsultation(Integer
				.valueOf(idConsultation));
		if (member.equals(consultation.getCreator())) {
			em.getTransaction().begin();
			consultationFacade.openConsultation(consultation);
			em.getTransaction().commit();
			em.close();
		}
		return displayAConsultation(request, response);
	}

	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String deleteConsultation() throws Exception {
		addRightToRequest(request);
		String idConsultation = request.getParameter("id");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		Consultation consultation = consultationFacade.getConsultation(Integer
				.valueOf(idConsultation));
		if (member.equals(consultation.getCreator())) {
			em.getTransaction().begin();
			consultationFacade.deleteConsultation(consultation, member);
			em.getTransaction().commit();
			em.close();
		}
		return SUCCESS;

	}

	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String autocompleteOther() throws Exception {
		String consultationId = request.getParameter("id");
		String voteOther = request.getParameter("voteOther");
		EntityManager em = PersistenceProvider.createEntityManager();
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		request.setAttribute(
				"autocompleteChoices",
				consultationFacade.getOtherChoice(
						Integer.valueOf(consultationId), voteOther));
		return SUCCESS;
	}

	/**
	 * @param consultation
	 * @param member
	 * @return
	 */
	public boolean isAllowedToVote(Consultation consultation,
			SocialEntity member) {
		return consultation.isOpened() && !consultation.isVoted(member)
				&& !consultation.isMaximumVoterReached()
				&& !consultation.isDeadlineReached();
	}

	/**
	 * @param consultation
	 * @param member
	 * @return
	 */
	public boolean isAllowedToShowResults(Consultation consultation,
			SocialEntity member) {
		return (consultation.isShowBeforeAnswer() || consultation
				.isVoted(member))
				&& (consultation.isShowBeforeClosing()
						|| !consultation.isOpened()
						|| consultation.isMaximumVoterReached() || consultation
							.isDeadlineReached());
	}

	/**
	 * Store the number of non reed Consultations
	 * 
	 * @param request
	 * @param em
	 */
	public static final void refreshNumNewConsultations(
			HttpServletRequest request, EntityManager em) {
		HttpSession session = request.getSession();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		InteractionFacade inf = new InteractionFacade(em);
		List<Interaction> list = inf.getUnreadInteractionsForSocialEntity(user);

		int numNonReedConsultations = Interaction.filter(list,
				Consultation.class).size();
		session.setAttribute("numNonReedConsultations", numNonReedConsultations);
	}

	/**
	 * @param request
	 */
	private void addRightToRequest(HttpServletRequest request) {
		SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request);
		Right rightAddConsultation = Right.ADD_CONSULTATION;
		request.setAttribute("rightAddConsultation", rightAddConsultation);
		request.setAttribute("socialEntity", socialEntity);
	}

	/**
	 * Send email for inform of a new consultation to every member of the same
	 * group that the owner of the consultation
	 * 
	 * @param consultation
	 * @param socialEntity
	 */
	private void sendMailForNewConsultation(Consultation consultation,
			SocialEntity creator) {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade socialGroup = new SocialGroupFacade(em);
		for (SocialEntity se : socialGroup.getMembersFromGroup(consultation
				.getCreator().getGroup())) {
			sendInformationMail(consultation, se);
		}
	}

	/**
	 * Send information email
	 * 
	 * @param consultation
	 * @param socialEntity
	 */
	private void sendInformationMail(Consultation consultation,
			SocialEntity socialEntity) {
		FSNetConfiguration conf = FSNetConfiguration.getInstance();
		String fsnetAddress = conf.getFSNetConfiguration().getProperty(
				FSNetConfiguration.FSNET_WEB_ADDRESS_KEY);
		String message;

		message = createPersonalizedMessage(consultation, socialEntity,
				fsnetAddress);

		FSNetMailer mailer = FSNetMailer.getInstance();
		Mail mail = mailer.createMail();

		mail.setSubject(getText("consultations.mail.subject") + " : "
				+ consultation.getTitle());

		mail.addRecipient(socialEntity.getEmail());
		mail.setContent(message);
		mailer.sendMail(mail);
	}

	/**
	 * Creates an personalized message to inform of an ending consultation
	 * 
	 * @param consultation
	 * @param socialEntity
	 * @param String
	 */
	private String createPersonalizedMessage(Consultation consultation,
			SocialEntity entity, String fsnetAddress) {
		StringBuilder sb = new StringBuilder();

		sb.append(getText("consultations.mail.new") + " ");
		sb.append("\"" + consultation.getTitle() + "\" ");

		if (consultation.getMaxDate() != null) {
			sb.append(getText("consultations.mail.deadline") + " ");
			sb.append(consultation.getMaxDate() + ".");
		}

		sb.append("<br/>");
		sb.append("<br/>");
		sb.append(getText("consultations.title.choix") + ":");
		sb.append("<ol>");

		for (ConsultationChoice choice : consultation.getChoices()) {
			sb.append("<li>" + choice.getIntituled() + "</li>");
		}

		sb.append("</ol>");
		sb.append(getText("consultations.mail.fsnet") + " ");
		sb.append(fsnetAddress + ".");

		return sb.toString();
	}

	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String deleteMulti() throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();
		addRightToRequest(request);
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);

		try {
			ConsultationFacade consultationFacade = new ConsultationFacade(em);
			addRightToRequest(request);

			for (int i = 0; i < selectedConsultations.length; i++) {
				em.getTransaction().begin();
				Consultation consultation = consultationFacade
						.getConsultation(Integer.valueOf(Integer
								.valueOf(selectedConsultations[i])));
				if (member.equals(consultation.getCreator())) {
					consultationFacade.deleteConsultation(consultation, member);
				}
				em.getTransaction().commit();
			}

		} finally {
			em.close();
		}

		return SUCCESS;
	}

	public int getMinChoicesVoter() {
		return minChoicesVoter;
	}

	public void setMinChoicesVoter(int minChoicesVoter) {
		this.minChoicesVoter = minChoicesVoter;
	}

	public int getMaxChoicesVoter() {
		return maxChoicesVoter;
	}

	public void setMaxChoicesVoter(int maxChoicesVoter) {
		this.maxChoicesVoter = maxChoicesVoter;
	}

	public String getConsultationTitle() {
		return consultationTitle;
	}

	public void setConsultationTitle(String consultationTitle) {
		this.consultationTitle = consultationTitle;
	}

	public String getConsultationDescription() {
		return consultationDescription;
	}

	public void setConsultationDescription(String consultationDescription) {
		this.consultationDescription = consultationDescription;
	}

	public int getIdConsultation() {
		return idConsultation;
	}

	public void setIdConsultation(int idConsultation) {
		this.idConsultation = idConsultation;
	}

	public String getVoteOther() {
		return voteOther;
	}

	public void setVoteOther(String voteOther) {
		this.voteOther = voteOther;
	}

	public String getVoteComment() {
		return voteComment;
	}

	public void setVoteComment(String voteComment) {
		this.voteComment = voteComment;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String[] getSelectedConsultations() {
		return selectedConsultations;
	}

	public void setSelectedConsultations(String[] selectedConsultations) {
		this.selectedConsultations = selectedConsultations;
	}

	public String[] getVoteChoice() {
		return voteChoice;
	}

	public void setVoteChoice(String[] voteChoice) {
		this.voteChoice = voteChoice;
	}

	public String[] getGroupsListRight() {
		return groupsListRight;
	}

	public void setGroupsListRight(String[] groupsListRight) {
		this.groupsListRight = groupsListRight;
	}

	public String getConsultationChoice() {
		return consultationChoice;
	}

	public void setConsultationChoice(String consultationChoice) {
		this.consultationChoice = consultationChoice;
	}

	public String getMaxVoters() {
		return maxVoters;
	}

	public void setMaxVoters(String maxVoters) {
		this.maxVoters = maxVoters;
	}

	public String getLimitChoicesPerVoter() {
		return limitChoicesPerVoter;
	}

	public void setLimitChoicesPerVoter(String limitChoicesPerVoter) {
		this.limitChoicesPerVoter = limitChoicesPerVoter;
	}

	public String getNbVotersPerChoiceBox() {
		return nbVotersPerChoiceBox;
	}

	public void setNbVotersPerChoiceBox(String nbVotersPerChoiceBox) {
		this.nbVotersPerChoiceBox = nbVotersPerChoiceBox;
	}

	public String getShowBeforeAnswer() {
		return showBeforeAnswer;
	}

	public void setShowBeforeAnswer(String showBeforeAnswer) {
		this.showBeforeAnswer = showBeforeAnswer;
	}

	public String getShowBeforeClosing() {
		return showBeforeClosing;
	}

	public void setShowBeforeClosing(String showBeforeClosing) {
		this.showBeforeClosing = showBeforeClosing;
	}

	public String getConsultationType() {
		return consultationType;
	}

	public void setConsultationType(String consultationType) {
		this.consultationType = consultationType;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getClosingAtMaxVoters() {
		return closingAtMaxVoters;
	}

	public void setClosingAtMaxVoters(String closingAtMaxVoters) {
		this.closingAtMaxVoters = closingAtMaxVoters;
	}

	public String getConsultationIfNecessaryWeight() {
		return consultationIfNecessaryWeight;
	}

	public void setConsultationIfNecessaryWeight(
			String consultationIfNecessaryWeight) {
		this.consultationIfNecessaryWeight = consultationIfNecessaryWeight;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public List<String> getListTypeKey() {
		return listTypeKey;
	}

	public List<String> getListTypeValue() {
		return listTypeValue;
	}

	
	
}
