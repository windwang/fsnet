package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.routines.IntegerValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.util.MessageResources;

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
public class ManageConsultations extends MappingDispatchAction {

	private static final String DEADLINE_TIME = ":23:59:59";

	private static final String NO_ANSWER = "no";
	private static final String REGEX_CONSULTATION_CHOICE = ";";

	private static final String SUCCES_ACTION_NAME = "success";
	private static final String FAILED_ACTION_NAME = "failed";
	private static final String UNAUTHORIZED_ACTION_NAME = "unauthorized";

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		DynaActionForm dynaForm = (DynaActionForm) form;
		String consultationTitle = (String) dynaForm.get("consultationTitle");
		String consultationDescription = (String) dynaForm
				.get("consultationDescription");
		String[] consultationChoices = dynaForm.getString("consultationChoice")
				.split(REGEX_CONSULTATION_CHOICE);
		String[] maxVoters = dynaForm.getString("maxVoters").split(
				REGEX_CONSULTATION_CHOICE);
		String consultationType = dynaForm.getString("consultationType");
		String consultationIfNecessaryWeight = dynaForm
				.getString("consultationIfNecessaryWeight");
		String nbVotersPerChoiceBox = dynaForm
				.getString("nbVotersPerChoiceBox");
		String limitChoicesPerVoter = dynaForm
				.getString("limitChoicesPerVoter");
		String minChoicesVoter = dynaForm.getString("minChoicesVoter");
		String maxChoicesVoter = dynaForm.getString("maxChoicesVoter");
		String showBeforeClosing = dynaForm.getString("showBeforeClosing");
		String showBeforeAnswer = dynaForm.getString("showBeforeAnswer");
		String deadline = dynaForm.getString("deadline");
		String closingAtMaxVoters = dynaForm.getString("closingAtMaxVoters");
		String[] groupsRightsAccept = (String[]) dynaForm.get("groupsListRight");

		addRightToRequest(request);

		if (!"".equals(limitChoicesPerVoter)
				&& Integer.valueOf(minChoicesVoter) > Integer
						.valueOf(maxChoicesVoter)) {
			request.setAttribute("errorChoicesVoter", true);
			return new ActionRedirect(
					mapping.findForwardConfig(FAILED_ACTION_NAME));
		}

		for (int i = 0; i < maxVoters.length; i++) {
			if ("".equals(maxVoters[i])) {
				maxVoters[i] = "-1"; // Unlimited
			} else if (!IntegerValidator.getInstance().isValid(maxVoters[i])
					|| Integer.valueOf(maxVoters[i]) < 1) {
				request.setAttribute("errorMaxVotersPerChoice", true);
				return new ActionRedirect(
						mapping.findForward(FAILED_ACTION_NAME));
			}
		}
		if (groupsRightsAccept.length == 0) {
			System.out.println("Error tableau vide !!!!!!");
			request.setAttribute("errorRights", true);
			return new ActionRedirect(mapping.findForward(FAILED_ACTION_NAME));
		}
		
		// END TODO
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		if (!fascade.isAuthorized(member, Right.ADD_CONSULTATION)) {
			return new ActionRedirect(
					mapping.findForward(UNAUTHORIZED_ACTION_NAME));
		}
		em.getTransaction().begin();
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		List<SocialGroup> listOfGroupAccepted = new ArrayList<SocialGroup>();
		for (String name : groupsRightsAccept) {
			listOfGroupAccepted.add(fascade.findByName(name));
		}
		Consultation consultation = consultationFacade.createConsultation(
				member, consultationTitle, consultationDescription,
				consultationChoices,
				Consultation.TypeConsultation.valueOf(consultationType),
				listOfGroupAccepted);

		if (!"".equals(nbVotersPerChoiceBox)) {
			consultation.setLimitParticipantPerChoice(true);
			for (int i = 0; i < maxVoters.length; i++) {
				consultation.getChoices().get(i)
						.setMaxVoters(Integer.valueOf(maxVoters[i]));
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

		if (!"".equals(consultationIfNecessaryWeight)) {
			consultation.setIfNecessaryWeight(Double
					.valueOf(consultationIfNecessaryWeight));
		} else {
			consultation.setIfNecessaryWeight(0.5);
		}

		if (!"".equals(limitChoicesPerVoter)) {
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

		return displayAConsultation(mapping, dynaForm, request, response);
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
	public ActionForward vote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		DynaActionForm dynaForm = (DynaActionForm) form;
		String voteComment = (String) dynaForm.get("voteComment");
		String voteOther = (String) dynaForm.get("voteOther");
		Integer idConsultation = (Integer) dynaForm.get("id");
		List<String> voteChoices = Arrays.asList(dynaForm
				.getStrings("voteChoice"));
		addRightToRequest(request);
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		Consultation consultation = consultationFacade
				.getConsultation(idConsultation);

//		SocialGroupFacade fascade = new SocialGroupFacade(em);
//		if (!fascade.isSuperAdmin(member)
//				&& !fascade.getAllGroupsChildSelfInclude(member.getGroup())
//						.contains(consultation.getCreator().getGroup())) {
//			return new ActionRedirect(
//					mapping.findForward(UNAUTHORIZED_ACTION_NAME));
//		}
		
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
				return displayAConsultation(mapping, dynaForm, request,
						response);
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
						return displayAConsultation(mapping, dynaForm, request,
								response);
					}
				}
			}
			
			if (voteOk) {
				consultationFacade.voteForConsultation(consultation, vote);
				em.getTransaction().commit();
			}
		}
		
		em.close();
		
		return displayAConsultation(mapping, dynaForm, request, response);
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
	 * @return
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
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteVote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
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
		return displayAConsultation(mapping, form, request, response);
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
	public ActionForward searchYourConsultations(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
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
		ActionRedirect redirect = new ActionRedirect(
				mapping.findForward(SUCCES_ACTION_NAME));
		return redirect;
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
	public ActionForward searchConsultation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		addRightToRequest(request);
		String searchText = "";
		EntityManager em = PersistenceProvider.createEntityManager();
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		if (form != null) {
			DynaActionForm dynaForm = (DynaActionForm) form;
			searchText = (String) dynaForm.get("searchText");
		}
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

		return mapping.findForward(SUCCES_ACTION_NAME);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward displayAConsultation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
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
			if((consultation==null)){
				return new ActionRedirect(
						mapping.findForward(UNAUTHORIZED_ACTION_NAME));
			}
			Collections.sort(consultation.getChoices(),
					new ConsultationChoiceComparator());
			
			FilterInteractionByUserGroup filterGroup = new FilterInteractionByUserGroup(em);
			if((filterGroup.filterAnInteraction(member, consultation) == null)){
				return new ActionRedirect(
						mapping.findForward(UNAUTHORIZED_ACTION_NAME));
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

		ActionRedirect redirect = new ActionRedirect(
				mapping.findForward(SUCCES_ACTION_NAME));
		return redirect;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward closeConsultation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
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
		return displayAConsultation(mapping, form, request, response);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward openConsultation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
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
		return displayAConsultation(mapping, form, request, response);
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
	public ActionForward deleteConsultation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
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
		ActionRedirect redirect = new ActionRedirect(
				mapping.findForward(SUCCES_ACTION_NAME));
		return redirect;
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
	public ActionForward autocompleteOther(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String consultationId = request.getParameter("id");
		String voteOther = request.getParameter("voteOther");
		EntityManager em = PersistenceProvider.createEntityManager();
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		request.setAttribute(
				"autocompleteChoices",
				consultationFacade.getOtherChoice(
						Integer.valueOf(consultationId), voteOther));
		return mapping.findForward(SUCCES_ACTION_NAME);
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

		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");
		mail.setSubject(bundle.getMessage("consultations.mail.subject") + " : "
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
		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");
		StringBuilder sb = new StringBuilder();

		sb.append(bundle.getMessage("consultations.mail.new") + " ");
		sb.append("\"" + consultation.getTitle() + "\" ");
		sb.append(bundle.getMessage("consultations.mail.deadline") + " ");
		sb.append(consultation.getMaxDate() + ".");
		sb.append("<\br>");
		sb.append("<\br>");
		sb.append(bundle.getMessage("consultations.title.choix") + ":");
		sb.append("<ol>");

		for (ConsultationChoice choice : consultation.getChoices()) {
			sb.append("<li>" + choice.getIntituled() + "</li>");
		}

		sb.append("</ol>");
		sb.append(bundle.getMessage("consultations.mail.fsnet") + " ");
		sb.append(fsnetAddress + ".");

		return sb.toString();
	}

}
