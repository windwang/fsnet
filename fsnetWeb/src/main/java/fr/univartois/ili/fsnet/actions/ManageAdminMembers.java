package fr.univartois.ili.fsnet.actions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
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
import org.apache.struts.util.MessageResources;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.mail.FSNetConfiguration;
import fr.univartois.ili.fsnet.commons.mail.FSNetMailer;
import fr.univartois.ili.fsnet.commons.mail.Mail;
import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.security.Encryption;
import fr.univartois.ili.fsnet.commons.utils.DateUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.facade.InterestFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;

/**
 * Execute CRUD Actions (and more) for the entity member
 * 
 * @author SAID Mohamed
 */
public class ManageAdminMembers extends MappingDispatchAction implements
		CrudAction {

	private static final Logger LOGGER = Logger.getAnonymousLogger();

	private static final String NAME_FORM_FIELD_NAME = "name";
	private static final String FIRSTNAME_FORM_FIELD_NAME = "firstName";
	private static final String EMAIL_FORM_FIELD_NAME = "email";
	private static final String PARENT_ID_FORM_FIELD_NAME = "parentId";
	private static final String MESSAGE_FORM_FIELD_NAME = "message";
	private static final String FORMAT_BIRTHDAY_FORM_FIELD_NAME = "formatBirthDay";
	private static final String MEMBERS_USER_EXISTS_ACTION_NAME = "members.user.exists";
	private static final String MEMBERS_ERROR_ON_CREATE_ACTION_NAME = "members.error.on.create";
	private static final String ID_MEMBER_REQUEST_PARAMETER_NAME = "idMember";
	private static final String SUCCES_ATTRIBUTE_NAME = "success";
	private static final String INTERNATIONALIZATION_RESSOURCE_NAME = "FSneti18n";
	

	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#create(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String name = (String) dynaForm.get(NAME_FORM_FIELD_NAME);
		dynaForm.set(NAME_FORM_FIELD_NAME, "");
		String firstName = (String) dynaForm.get(FIRSTNAME_FORM_FIELD_NAME);
		dynaForm.set(FIRSTNAME_FORM_FIELD_NAME, "");

		String mail = (String) dynaForm.get(EMAIL_FORM_FIELD_NAME);
		mail = mail.toLowerCase();
		dynaForm.set(EMAIL_FORM_FIELD_NAME, "");

		String parentId = (String) dynaForm.get(PARENT_ID_FORM_FIELD_NAME);
		dynaForm.set(PARENT_ID_FORM_FIELD_NAME, "");

		String personalizedMessage = (String) dynaForm.get(MESSAGE_FORM_FIELD_NAME);
		String inputPassword = (String) dynaForm.get("password");
		dynaForm.set("password", "");
		dynaForm.set("passwordConfirmation", "");

		EntityManager em = PersistenceProvider.createEntityManager();

		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		SocialEntityFacade facadeSE = new SocialEntityFacade(em);
		SocialEntity socialEntity = facadeSE.createSocialEntity(name,
				firstName, mail);
		SocialGroup socialGroup = socialGroupFacade.getSocialGroup(Integer
				.parseInt(parentId));
		try {
			String definedPassword = null;
			String encryptedPassword = null;
			if (inputPassword == null || "".equals(inputPassword)) {
				definedPassword = Encryption.generateRandomPassword();
				LOGGER.info("#### Generated Password : " + definedPassword);
				encryptedPassword = Encryption
						.getEncodedPassword(definedPassword);
			} else {
				definedPassword = inputPassword;
				LOGGER.info("#### Defined Password : " + inputPassword);
				encryptedPassword = Encryption
						.getEncodedPassword(inputPassword);
			}
			socialEntity.setPassword(encryptedPassword);
			em.getTransaction().begin();
			socialGroup.addSocialElement(socialEntity);
			em.persist(socialGroup);

			em.getTransaction().commit();

			Locale currentLocale = request.getLocale();
			sendConfirmationMail(socialEntity, definedPassword,
					personalizedMessage, currentLocale);
		} catch (RollbackException e) {
			ActionErrors errors = new ActionErrors();
			errors.add(EMAIL_FORM_FIELD_NAME, new ActionMessage(MEMBERS_USER_EXISTS_ACTION_NAME));
			saveErrors(request, errors);
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add(EMAIL_FORM_FIELD_NAME, new ActionMessage(MEMBERS_ERROR_ON_CREATE_ACTION_NAME));
			saveErrors(request, errors);
		}
		em.close();

		dynaForm.set(NAME_FORM_FIELD_NAME, "");
		dynaForm.set(FIRSTNAME_FORM_FIELD_NAME, "");
		dynaForm.set(EMAIL_FORM_FIELD_NAME, "");
		dynaForm.set(PARENT_ID_FORM_FIELD_NAME, "");
		cleanSession(request);

		MessageResources bundle = MessageResources
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE_NAME);
		request.setAttribute(SUCCES_ATTRIBUTE_NAME, bundle.getMessage(request.getLocale(),
				"member.success.on.create"));

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	/**
	 * @param filePath
	 * @return
	 */
	public String readFileLinePerLine(String filePath) {
		String allString = "";
		try {
			BufferedReader buff = new BufferedReader(new FileReader(filePath));
			try {
				String line;
				while ((line = buff.readLine()) != null) {
					if (line.matches("^[A-Za-z0-9 -.]{1,30}/[A-Za-z0-9 -.]{1,30}/[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$")){
						allString = allString.concat(line + "\n");
					}
				}
			} finally {
				buff.close();
			}
		} catch (IOException ioe) {
			return null;
		}
		return allString;
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
	public ActionForward createMultipleFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR;
		String formInput = readFileLinePerLine((String) dynaForm
				.get("fileMultipleMember"));
		String personalizedMessage = (String) dynaForm.get(MESSAGE_FORM_FIELD_NAME);
		if (formInput != null) {
			EntityManager em = PersistenceProvider.createEntityManager();
			SocialEntityFacade facadeSE = new SocialEntityFacade(em);

			String[] formSocialEntities = formInput.split("\n");
			Map<SocialEntity, String> socialEntities = new HashMap<SocialEntity, String>();

			em.getTransaction().begin();

			for (String formSocialEntitie : formSocialEntities) {
				formSocialEntitie = formSocialEntitie.replaceAll("\r", "");
				String[] socialEntitieInput = formSocialEntitie.split("/");

				SocialEntity socialEntity = facadeSE.createSocialEntity(
						socialEntitieInput[0], socialEntitieInput[1],
						socialEntitieInput[2].toLowerCase());

				String definedPassword = Encryption.generateRandomPassword();
				LOGGER.info("#### Defined Password : " + definedPassword);
				String encryptedPassword = Encryption
						.getEncodedPassword(definedPassword);
				socialEntity.setPassword(encryptedPassword);

				socialEntities.put(socialEntity, definedPassword);
				em.persist(socialEntity);
			}

			try {
				em.getTransaction().commit();
			}
			// I'm not really sure about the exception, so I took the same as in
			// the
			// create methode
			catch (RollbackException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
				ActionErrors errors = new ActionErrors();
				errors.add(EMAIL_FORM_FIELD_NAME, new ActionMessage(MEMBERS_USER_EXISTS_ACTION_NAME));
				saveErrors(request, errors);
				return mapping.getInputForward();
			} catch (Exception e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
				ActionErrors errors = new ActionErrors();
				errors.add(EMAIL_FORM_FIELD_NAME,
						new ActionMessage(MEMBERS_ERROR_ON_CREATE_ACTION_NAME));
				saveErrors(request, errors);
				return mapping.getInputForward();
			}

			em.close();

			Locale currentLocale = request.getLocale();
			for (Entry<SocialEntity, String> entry : socialEntities.entrySet()) {
				try {
					sendConfirmationMail(entry.getKey(), entry.getValue(),
							personalizedMessage, currentLocale);
				}
				// I'm not really sure about the exception, so I took the same
				// as in
				// the create methode
				catch (RollbackException e) {
					Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
					ActionErrors errors = new ActionErrors();
					errors.add(EMAIL_FORM_FIELD_NAME,
							new ActionMessage(MEMBERS_USER_EXISTS_ACTION_NAME));
					saveErrors(request, errors);
				} catch (Exception e) {
					Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
					ActionErrors errors = new ActionErrors();
					errors.add(EMAIL_FORM_FIELD_NAME, new ActionMessage(
							MEMBERS_ERROR_ON_CREATE_ACTION_NAME));
					saveErrors(request, errors);
				}
			}
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add("fileMultipleMember", new ActionMessage(
					"members.error.file"));
			saveErrors(request, errors);
		}
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	/**
	 * 
	 * Same as create but for multiple entity. Create multiple
	 * {@link SocialEntity} and persist them in database. Format : one entity
	 * per line using the following pattern : name/firstname/email
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 * @author stephane Gronowski
	 */
	public ActionForward createMultiple(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR

		String formInput = (String) dynaForm.get("multipleMember");
		dynaForm.set("multipleMember", "");
		String personalizedMessage = (String) dynaForm.get(MESSAGE_FORM_FIELD_NAME);

		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntityFacade facadeSE = new SocialEntityFacade(em);

		String[] formSocialEntities = formInput.split("\n");
		Map<SocialEntity, String> socialEntities = new HashMap<SocialEntity, String>();

		em.getTransaction().begin();

		for (String formSocialEntitie : formSocialEntities) {
			formSocialEntitie = formSocialEntitie.replaceAll("\r", "");
			String[] socialEntitieInput = formSocialEntitie.split("/");

			SocialEntity socialEntity = facadeSE.createSocialEntity(
					socialEntitieInput[0], socialEntitieInput[1],
					socialEntitieInput[2].toLowerCase());

			String definedPassword = Encryption.generateRandomPassword();
			LOGGER.info("#### Defined Password : " + definedPassword);
			String encryptedPassword = Encryption
					.getEncodedPassword(definedPassword);
			socialEntity.setPassword(encryptedPassword);

			socialEntities.put(socialEntity, definedPassword);
			em.persist(socialEntity);
		}

		try {
			em.getTransaction().commit();
		}
		// I'm not really sure about the exception, so I took the same as in the
		// create methode
		catch (RollbackException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
			ActionErrors errors = new ActionErrors();
			errors.add(EMAIL_FORM_FIELD_NAME, new ActionMessage(MEMBERS_USER_EXISTS_ACTION_NAME));
			saveErrors(request, errors);
			return mapping.getInputForward();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
			ActionErrors errors = new ActionErrors();
			errors.add(EMAIL_FORM_FIELD_NAME, new ActionMessage(MEMBERS_ERROR_ON_CREATE_ACTION_NAME));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}

		em.close();

		Locale currentLocale = request.getLocale();
		for (Entry<SocialEntity, String> entry : socialEntities.entrySet()) {
			try {
				sendConfirmationMail(entry.getKey(), entry.getValue(),
						personalizedMessage, currentLocale);
			}
			// I'm not really sure about the exception, so I took the same as in
			// the create methode
			catch (RollbackException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
				ActionErrors errors = new ActionErrors();
				errors.add(EMAIL_FORM_FIELD_NAME, new ActionMessage(MEMBERS_USER_EXISTS_ACTION_NAME));
				saveErrors(request, errors);
			} catch (Exception e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
				ActionErrors errors = new ActionErrors();
				errors.add(EMAIL_FORM_FIELD_NAME,
						new ActionMessage(MEMBERS_ERROR_ON_CREATE_ACTION_NAME));
				saveErrors(request, errors);
			}

		}

		MessageResources bundle = MessageResources
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE_NAME);
		request.setAttribute(SUCCES_ATTRIBUTE_NAME, bundle.getMessage(request.getLocale(),
				"members.success.on.create"));

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	/**
	 * Send mails to a list of recipient.
	 * 
	 * @param socialEntity
	 *            the new EntiteSociale
	 * @param password
	 *            the password of the {@link SocialEntity}
	 * @param personalizedMessage
	 *            message that will be send to the person to inform it that it
	 *            has been registered
	 * @param locale
	 *            the current {@link Locale}
	 * @return true if success false if fail
	 * 
	 * @author Mathieu Boniface < mat.boniface {At} gmail.com >
	 * @author stephane Gronowski
	 */
	private void sendConfirmationMail(SocialEntity socialEntity,
			String password, String personalizedMessage, Locale locale) {
		FSNetConfiguration conf = FSNetConfiguration.getInstance();
		String fsnetAddress = conf.getFSNetConfiguration().getProperty(
				FSNetConfiguration.FSNET_WEB_ADDRESS_KEY);
		String message;

		message = createPersonalizedMessage(socialEntity.getName(),
				socialEntity.getFirstName(), fsnetAddress, password,
				socialEntity.getEmail(), personalizedMessage, locale);
		// send a mail
		FSNetMailer mailer = FSNetMailer.getInstance();
		Mail mail = mailer.createMail();

		MessageResources bundle = MessageResources
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE_NAME);

		mail.setSubject(bundle.getMessage(locale,
				"members.welcomeMessage.subject"));
		mail.addRecipient(socialEntity.getEmail());
		mail.setContent(message);

		mailer.sendMail(mail);
	}

	/**
	 * Method that creates an personalized "welcome to FSNet" message.
	 * 
	 * @param nom
	 *            the name of the {@link SocialEntity}
	 * @param prenom
	 *            the first name of the {@link SocialEntity}
	 * @param addressFsnet
	 *            url of the FSnet application
	 * @param password
	 *            the password of the {@link SocialEntity}
	 * @param personalizedMessage
	 *            message that will be send to the person to inform it that it
	 *            has been registered
	 * @param locale
	 *            the current {@link Locale}
	 * @return the message .
	 * @author stephane Gronowski
	 */
	private String createPersonalizedMessage(String name, String firstName,
			String addressFsnet, String password, String email,
			String personalizedMessage, Locale locale) {

		MessageResources bundle = MessageResources
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE_NAME);
		StringBuilder message = new StringBuilder();

		personalizedMessage = personalizedMessage.replace(
				"\"" + bundle.getMessage(locale, "members.name") + "\"", name);
		personalizedMessage = personalizedMessage.replace(
				"\"" + bundle.getMessage(locale, "members.firstName") + "\"",
				firstName);
		personalizedMessage = personalizedMessage.replace(
				"\"" + bundle.getMessage(locale, "members.password") + "\"",
				password);
		personalizedMessage = personalizedMessage.replace("\"Email\"", email);
		personalizedMessage = personalizedMessage.replace("\"url\"",
				addressFsnet);
		message.append(personalizedMessage);
		return message.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#delete(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException();
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
	public ActionForward switchState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String entitySelected = request.getParameter("entitySelected");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		em.getTransaction().begin();
		int socialEntityId = Integer.parseInt(entitySelected);
		socialEntityFacade.switchState(socialEntityId);
		em.getTransaction().commit();
		em.close();
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#display(org.apache.struts.
	 * action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		EntityManager entityManager = PersistenceProvider.createEntityManager();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(
				entityManager);

		Integer idMember = Integer.valueOf(request.getParameter(ID_MEMBER_REQUEST_PARAMETER_NAME));

		SocialEntity member = socialEntityFacade.getSocialEntity(idMember);

		String adress = "";
		String city = "";
		if (member.getAddress() != null) {
			if (member.getAddress().getAddress() != null){
				adress = member.getAddress().getAddress();
			}
			if (member.getAddress().getCity() != null){
				city = member.getAddress().getCity();
			}
		}
		dynaForm.set("address", adress);
		dynaForm.set("city", city);
		dynaForm.set("phone", member.getPhone());
		dynaForm.set("sexe", member.getSex());
		dynaForm.set("job", member.getProfession());
		dynaForm.set("birthDay", member.getBirthDate());
		dynaForm.set(NAME_FORM_FIELD_NAME, member.getName());
		dynaForm.set(EMAIL_FORM_FIELD_NAME, member.getEmail());
		dynaForm.set(FIRSTNAME_FORM_FIELD_NAME, member.getFirstName());
		dynaForm.set("id", member.getId());
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(
				entityManager);
		List<SocialGroup> socialGroups = socialGroupFacade
				.getAllChildGroups(UserUtils.getHisGroup(request));

		Paginator<Interest> paginator = new Paginator<Interest>(
				member.getInterests(), request, "interestsMember", ID_MEMBER_REQUEST_PARAMETER_NAME);

		request.setAttribute("interestsMemberPaginator", paginator);
		request.setAttribute("id", member.getId());
		List<SocialEntity> allMastersGroup = socialGroupFacade
				.getAllMasterGroupes();

		cleanSession(request);

		if (allMastersGroup.contains(member)){
			request.getSession(true).setAttribute("master", true);
		}else{
			request.getSession(true).setAttribute("master", false);
		}

		request.getSession(true).setAttribute("group", member.getGroup());
		request.getSession(true).setAttribute("allGroups", socialGroups);
		entityManager.close();
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	/**
	 * @param dynaForm
	 * @return
	 */
	private ActionErrors verified(DynaActionForm dynaForm) {
		ActionErrors res = new ActionErrors();
		try {
			Date birthday = DateUtils.format(dynaForm
					.getString(FORMAT_BIRTHDAY_FORM_FIELD_NAME));
			if (birthday.after(new Date())) {
				res.add(FORMAT_BIRTHDAY_FORM_FIELD_NAME, new ActionMessage(
						"date.error.invalid"));
			}
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 100);
			if (birthday.before(cal.getTime())) {
				res.add(FORMAT_BIRTHDAY_FORM_FIELD_NAME, new ActionMessage(
						"date.error.invalid"));
			}
		} catch (ParseException e1) {
			if (!dynaForm.getString(FORMAT_BIRTHDAY_FORM_FIELD_NAME).isEmpty()) {
				res.add(FORMAT_BIRTHDAY_FORM_FIELD_NAME, new ActionMessage(
						"date.error.invalid"));
			}
			// Date Format is empty. Do nothing.
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#modify(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager entityManager = PersistenceProvider.createEntityManager();
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(
				entityManager);
		DynaActionForm formSocialENtity = (DynaActionForm) form;// NOSONAR

		String name = (String) formSocialENtity.get(NAME_FORM_FIELD_NAME);
		String firstName = (String) formSocialENtity.get(FIRSTNAME_FORM_FIELD_NAME);
		String email = (String) formSocialENtity.get(EMAIL_FORM_FIELD_NAME);
		email = email.toLowerCase();
		Integer idGroup = Integer.parseInt((String) formSocialENtity
				.get(PARENT_ID_FORM_FIELD_NAME));
		String job = (String) formSocialENtity.get("job");
		String address = (String) formSocialENtity.get("address");
		String city = (String) formSocialENtity.get("city");
		String phone = (String) formSocialENtity.get("phone");
		String sexe = (String) formSocialENtity.get("sexe");
		Integer idMember = (Integer) formSocialENtity.get("id");
		ActionErrors errors = verified(formSocialENtity);

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.getInputForward();
		}

		Date birthDay = null;
		try {
			birthDay = DateUtils.format((String) formSocialENtity
					.get(FORMAT_BIRTHDAY_FORM_FIELD_NAME));
		} catch (ParseException e) {
			// Date Format is invalid or empty. Do nothing.
		}
		formSocialENtity.set("birthDay", birthDay);

		SocialEntityFacade facadeSE = new SocialEntityFacade(entityManager);

		SocialEntity member = facadeSE.getSocialEntity(idMember);
		SocialGroup oldGroup = member.getGroup();
		SocialGroup newGroup = socialGroupFacade.getSocialGroup(idGroup);
		oldGroup.removeSocialElement(member);
		newGroup.addSocialElement(member);

		member.setFirstname(firstName);
		member.setName(name);
		member.setEmail(email);
		member.setAddress(new Address(address, city));
		member.setBirthDate(birthDay);
		member.setPhone(phone);
		member.setSex(sexe);
		member.setProfession(job);
		entityManager.getTransaction().begin();
		entityManager.merge(oldGroup);
		entityManager.merge(newGroup);
		entityManager.getTransaction().commit();

		request.setAttribute("member", member);
		request.setAttribute("id", member.getId());
		Paginator<Interest> paginator = new Paginator<Interest>(
				member.getInterests(), request, "interestsMember", ID_MEMBER_REQUEST_PARAMETER_NAME);
		request.setAttribute("interestsMemberPaginator", paginator);

		errors.add(MESSAGE_FORM_FIELD_NAME, new ActionMessage("member.success.update"));
		saveErrors(request, errors);
		cleanSession(request);

		MessageResources bundle = MessageResources
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE_NAME);
		request.setAttribute(SUCCES_ATTRIBUTE_NAME, bundle.getMessage(request.getLocale(),
				"member.success.on.modify"));

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	/**
	 * @param request
	 */
	private void cleanSession(HttpServletRequest request) {
		request.getSession(true).setAttribute("master", false);
		request.getSession(true).removeAttribute("group");
		request.getSession(true).removeAttribute("allGroups");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#search(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();

		Set<SocialEntity> resultOthers = null;
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		SocialGroup socialGroupUser = UserUtils.getHisGroup(request);
		List<SocialEntity> resultOthersList = socialGroupFacade
				.getAllChildMembers(socialGroupUser);
		if (form != null) {
			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			String searchText = (String) dynaForm.get("searchText");
			SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
			resultOthers = socialEntityFacade.searchSocialEntity(searchText);
			em.getTransaction().commit();
			em.close();

			if (resultOthers != null) {
				resultOthers.retainAll(resultOthersList);
				resultOthersList = new ArrayList<SocialEntity>(resultOthers);
				Collections.sort(resultOthersList);
				request.setAttribute("membersList", resultOthersList);

			} else {
				request.setAttribute("membersList", null);
			}
		} else {
			em.getTransaction().commit();
			em.close();
			request.setAttribute("membersList", resultOthersList);
			List<SocialGroup> socialGroups = socialGroupFacade
					.getAllChildGroups(UserUtils.getHisGroup(request));
			cleanSession(request);
			request.getSession(true).setAttribute("allGroups", socialGroups);
		}

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	/**
	 * delete interest member by admin
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward deleteInterestMember(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Integer interestSelected = Integer.valueOf(request
				.getParameter("interestSelected"));
		Integer idSocialEntity = Integer.valueOf(request
				.getParameter(ID_MEMBER_REQUEST_PARAMETER_NAME));

		EntityManager em = PersistenceProvider.createEntityManager();

		LOGGER.info("delete interest social entity");
		SocialEntityFacade ise = new SocialEntityFacade(em);
		InterestFacade interestFacade = new InterestFacade(em);
		em.getTransaction().begin();
		ise.removeInterest(interestFacade.getInterest(interestSelected),
				ise.getSocialEntity(idSocialEntity));
		em.getTransaction().commit();
		em.close();

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

}
