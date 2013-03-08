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

import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

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
public class ManageAdminMembers extends ActionSupport implements
		CrudAction,ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getAnonymousLogger();

	private static final String MEMBERS_USER_EXISTS_ACTION_NAME = "members.user.exists";
	private static final String MEMBERS_ERROR_ON_CREATE_ACTION_NAME = "members.error.on.create";
	private static final String ID_MEMBER_REQUEST_PARAMETER_NAME = "idMember";
	private static final String SUCCES_ATTRIBUTE_NAME = "success";
	
	private HttpServletRequest request;
	private String name;
	private String firstname;
	private String email;
	private String parentId;
	private String message;
	private String formatBirthDay;
	private String password;
	private String passwordConfirmation;
	
	private String adress;
	private String city;
	private String phone;
	private String sexe;
	private String job;
	private Date birthDay;
	private int id;
	private String fileMultipleMember;
	private String multipleMember;
	private String searchText;
	
	public String getName() {
		return name;
	}

	

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFormatBirthDay() {
		return formatBirthDay;
	}

	public void setFormatBirthDay(String formatBirthDay) {
		this.formatBirthDay = formatBirthDay;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getFileMultipleMember() {
		return fileMultipleMember;
	}

	public void setFileMultipleMember(String fileMultipleMember) {
		this.fileMultipleMember = fileMultipleMember;
	}

	public String getMultipleMember() {
		return multipleMember;
	}

	public void setMultipleMember(String multipleMember) {
		this.multipleMember = multipleMember;
	}
	
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
	public String create() throws Exception{

		EntityManager em = PersistenceProvider.createEntityManager();

		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		SocialEntityFacade facadeSE = new SocialEntityFacade(em);
		SocialEntity socialEntity = facadeSE.createSocialEntity(name,
				firstname, email);
		SocialGroup socialGroup = socialGroupFacade.getSocialGroup(Integer
				.parseInt(parentId));
		try {
			String definedPassword = null;
			String encryptedPassword = null;
			if (password == null || "".equals(password)) {
				definedPassword = Encryption.generateRandomPassword();
				LOGGER.info("#### Generated Password : " + definedPassword);
				encryptedPassword = Encryption
						.getEncodedPassword(definedPassword);
			} else {
				definedPassword = password;
				LOGGER.info("#### Defined Password : " + password);
				encryptedPassword = Encryption
						.getEncodedPassword(password);
			}
			socialEntity.setPassword(encryptedPassword);
			em.getTransaction().begin();
			socialGroup.addSocialElement(socialEntity);
			em.persist(socialGroup);

			em.getTransaction().commit();

			Locale currentLocale = request.getLocale();
			sendConfirmationMail(socialEntity, definedPassword,
					message, currentLocale);
		} catch (RollbackException e) {
			addFieldError(email, MEMBERS_USER_EXISTS_ACTION_NAME);
		} catch (Exception e) {
			addFieldError(email, MEMBERS_ERROR_ON_CREATE_ACTION_NAME);
		}
		em.close();

		name = "";
		firstname =  "";
		email = "";
		parentId = "";
		password = "";
		passwordConfirmation = "";
		cleanSession(request);

		request.setAttribute(SUCCES_ATTRIBUTE_NAME, getText("member.success.on.create"));

		return SUCCESS;
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
	public String createMultipleFile() throws Exception {
		String formInput = readFileLinePerLine(fileMultipleMember);
		String personalizedMessage = message;
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
				addFieldError(email, MEMBERS_USER_EXISTS_ACTION_NAME);
				return INPUT;
			} catch (Exception e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
				addFieldError(email, MEMBERS_ERROR_ON_CREATE_ACTION_NAME);
				return INPUT;
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
					addFieldError(email, MEMBERS_USER_EXISTS_ACTION_NAME);
				} catch (Exception e) {
					Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
					addFieldError(email, MEMBERS_ERROR_ON_CREATE_ACTION_NAME);
				}
			}
		} else {
			addFieldError("fileMultipleMember", "members.error.file");
		}
		return SUCCESS;
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
	public String createMultiple() throws Exception{

		String formInput = multipleMember;
		multipleMember="";
		String personalizedMessage = message;

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
			addFieldError(email, MEMBERS_USER_EXISTS_ACTION_NAME);
			return INPUT;
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
			addFieldError(email, MEMBERS_ERROR_ON_CREATE_ACTION_NAME);
			return INPUT;
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
				addFieldError(email, MEMBERS_USER_EXISTS_ACTION_NAME);
			} catch (Exception e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
				addFieldError(email, MEMBERS_ERROR_ON_CREATE_ACTION_NAME);
			}

		}

		request.setAttribute(SUCCES_ATTRIBUTE_NAME, getText("members.success.on.create"));

		return SUCCESS;
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

		mail.setSubject(getText("members.welcomeMessage.subject"));
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

		String tmpPersonalizedMessage = new String(personalizedMessage);
		tmpPersonalizedMessage = tmpPersonalizedMessage.replace(
				"\"" + getText("members.name") + "\"", name);
		tmpPersonalizedMessage = tmpPersonalizedMessage.replace(
				"\"" + getText("members.firstName") + "\"",
				firstName);
		tmpPersonalizedMessage = tmpPersonalizedMessage.replace(
				"\"" + getText("members.password") + "\"",
				password);
		tmpPersonalizedMessage = tmpPersonalizedMessage.replace("\"Email\"", email);
		tmpPersonalizedMessage = tmpPersonalizedMessage.replace("\"url\"",
				addressFsnet);
		return tmpPersonalizedMessage;
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
	public String delete() throws Exception {
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
	public String switchState() throws Exception {
		String entitySelected = request.getParameter("entitySelected");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		em.getTransaction().begin();
		int socialEntityId = Integer.parseInt(entitySelected);
		socialEntityFacade.switchState(socialEntityId);
		em.getTransaction().commit();
		em.close();
		return SUCCESS;
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
	public String display() throws Exception {
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
		this.adress = adress;
		this.city = city;
		phone = member.getPhone();
		sexe = member.getSex();
		job = member.getProfession();
		birthDay = member.getBirthDate();
		name = member.getName();
		email =  member.getEmail();
		firstname =  member.getFirstName();
		id = member.getId();

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
		
		return SUCCESS;

	/**
	 * @param dynaForm
	 * @return
	 */
		/* Remove ?
		 * try {
			Date birthday = DateUtils.format(formatBirthDay);
			if (birthday.after(new Date())) {
				addFieldError(formatBirthDay, "date.error.invalid");
			}
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 100);
			if (birthday.before(cal.getTime())) {
				addFieldError(formatBirthDay, "date.error.invalid");
			}
		} catch (ParseException e1) {
			if (!formatBirthDay.isEmpty()) {
				addFieldError(formatBirthDay, "date.error.invalid");
			}
			// Date Format is empty. Do nothing.
		}*/
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
	public String modify() throws Exception {
		EntityManager entityManager = PersistenceProvider.createEntityManager();
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(
				entityManager);

		String name = this.name;
		String firstName = this.firstname;
		String email = this.email;
		email = email.toLowerCase();
		Integer idGroup = Integer.parseInt(this.parentId);
		String job = this.job;
		String address = this.adress;
		String city = this.city;
		String phone = this.phone;
		String sexe = this.sexe;
		Integer idMember = this.id;

		Date birthDay = null;
		try {
			birthDay = DateUtils.format(this.formatBirthDay);
		} catch (ParseException e) {
			// Date Format is invalid or empty. Do nothing.
		}
		this.birthDay = birthDay;

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

		addFieldError(message, "member.success.update");
		cleanSession(request);

		request.setAttribute(SUCCES_ATTRIBUTE_NAME, getText("member.success.on.modify"));

		return SUCCESS;
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
	public String search() throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();

		Set<SocialEntity> resultOthers = null;
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		SocialGroup socialGroupUser = UserUtils.getHisGroup(request);
		List<SocialEntity> resultOthersList = socialGroupFacade
				.getAllChildMembers(socialGroupUser);
		if (form != null) {
			String searchText = this.searchText;
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

		return SUCCESS;
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
	public String deleteInterestMember() throws Exception {
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

		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
}
