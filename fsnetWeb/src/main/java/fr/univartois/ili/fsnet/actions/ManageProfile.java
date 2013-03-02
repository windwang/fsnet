package fr.univartois.ili.fsnet.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.ActionMessage;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import fr.univartois.ili.fsnet.actions.utils.FacebookKeyManager;
import fr.univartois.ili.fsnet.actions.utils.ImageManager;
import fr.univartois.ili.fsnet.actions.utils.PictureType;
import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.auth.Authenticate;
import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.DateUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.core.LoggedUsersContainer;
import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.ProfileFacade;
import fr.univartois.ili.fsnet.facade.ProfileVisiteFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;

/**
 * 
 * @author Geoffrey Boulay
 * @author SAID Mohamed
 */
public class ManageProfile extends ActionSupport implements CrudAction,ServletRequestAware {

	private static final int MAX_PICTURE_SIZE = 500000;
	/**
	 * watched profile variable session name
	 */
	public static final String WATCHED_PROFILE_VARIABLE = "watchedProfile";
	public static final String EDITABLE_PROFILE_VARIABLE = "edit";
	private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	private static final String DATE_OF_BIRTH_FORM_FIELD_NAME = "dateOfBirth";
	private static final String MAIL_FORM_FIELD_NAME = "mail";
	private static final String SUCCES_ATTRIBUTE_NAME = "success";
	private static final String IS_MASTER_GROUP_ATTRIBUTE_NAME = "isMasterGroup";
	private static final String IS_GROUP_RESPONSIBLE_ATTRIBUTE_NAME = "isGroupResponsible";
	private static final String ERROR_UPDATE_ATTRIBUTE_STRING = "updateProfile.error.photo.fatal";

	private HttpServletRequest request;
	private String name;
	private String firstName;
	private String adress;
	private String city;
	private String sexe;
	private String mail;
	private String phone;
	private String job;
	private Date dateOfBirth;
	private int id;
	private File photo;
	private String photoContentType;
	private String photoUrl;
	
	
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
	public final String create()
			throws Exception {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * @param em
	 * @param request
	 * @return
	 */
	private int verified(EntityManager em) throws ParseException {
		int nbErreurs=0;
		Date actualDate = new Date();
		if (dateOfBirth.after(actualDate)) {
			addFieldError(DATE_OF_BIRTH_FORM_FIELD_NAME, "date.error.invalid");
			nbErreurs++;
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 100);
		Date lastedDate = cal.getTime();
		if (dateOfBirth.before(lastedDate)) {
			addFieldError(DATE_OF_BIRTH_FORM_FIELD_NAME, "date.error.invalid");
			nbErreurs++;
		}

		if (!UserUtils.getAuthenticatedUser(request, em).getEmail()
				.equals(mail)) {
			SocialEntityFacade sef = new SocialEntityFacade(em);
			em.getTransaction().begin();
			SocialEntity se = sef.findByEmail(mail);
			em.getTransaction().commit();
			//em.close();

			if (se != null) {
				addFieldError(MAIL_FORM_FIELD_NAME, "error.updateProfile.email.alwaysExist");
				nbErreurs++;
			}
		}
		return nbErreurs;
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
	public final String modify()
			throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade fascade = new SocialGroupFacade(em);

		if (!fascade.isAuthorized(UserUtils.getAuthenticatedUser(request, em),
				Right.MODIFY_PROFIL)) {
			em.close();
			return "unauthorized";
		}

		addRightToRequest(request);

		

		int actionsErrors = verified(em);

		if (actionsErrors>0) {
			em.close();
			return INPUT;
		}

		ProfileFacade pf = new ProfileFacade(em);
		em.getTransaction().begin();
		pf.editProfile(
				UserUtils.getAuthenticatedUser(request, em),
				name,
				firstName,
				new Address(adress, city), dateOfBirth, sexe, job, mail.toLowerCase(),
				phone);
		em.getTransaction().commit();
		em.close();

		return SUCCESS;
	}

	/**
	 * @param request
	 */
	private void addKeyFacebookInRequest(HttpServletRequest request) {
		request.setAttribute("KEY_FACEBOOK",
				FacebookKeyManager.getKeyFacebook());
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
	public final String delete()
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
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
	public final String search()
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
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
	public final String displayToModify() throws Exception {
		addKeyFacebookInRequest(request);
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		SocialGroupFacade sgf = new SocialGroupFacade(em);
		addRightToRequest(request);
		request.setAttribute("currentUser", user);
		name=user.getName();
		firstName=user.getFirstName();

		if (user.getAddress() != null) {
			adress=user.getAddress().getAddress();
			city=user.getAddress().getCity();
		}

		if (user.getBirthDate() != null) {
			dateOfBirth=user.getBirthDate();
		}

		sexe=user.getSex();
		job=user.getProfession();
		mail=user.getEmail();
		phone=user.getPhone();

		if (sgf.isMasterGroup(user)) {
			request.getSession(true).setAttribute(
					IS_MASTER_GROUP_ATTRIBUTE_NAME, true);
		} else {
			request.getSession(true).setAttribute(
					IS_MASTER_GROUP_ATTRIBUTE_NAME, false);
		}

		if (sgf.isGroupResponsible(user)) {
			request.getSession(true).setAttribute(
					IS_GROUP_RESPONSIBLE_ATTRIBUTE_NAME, true);
		} else {
			request.getSession(true).setAttribute(
					IS_GROUP_RESPONSIBLE_ATTRIBUTE_NAME, false);
		}

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
	public final String display()
			throws Exception {
		addKeyFacebookInRequest(request);
		EntityManager em = PersistenceProvider.createEntityManager();

		SocialEntityFacade sef = new SocialEntityFacade(em);
		SocialGroupFacade sgf = new SocialGroupFacade(em);

		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		Boolean alreadyInContact = false;

		addRightToRequest(request);

		if(id<0)
			id = user.getId();
		

		SocialEntity profile = sef.getSocialEntity(id);

		if (profile == null) {
			em.close();
			throw new UnauthorizedOperationException(
					"The profile id is not defined");
		}

		if (user.getContacts().contains(profile)
				|| user.getAsked().contains(profile)
				|| user.getRequested().contains(profile)
				|| user.getRefused().contains(profile)) {
			alreadyInContact = true;
		}

		if (!profile.equals(user)) {
			ProfileVisiteFacade pvf = new ProfileVisiteFacade(em);
			em.getTransaction().begin();
			pvf.visite(user, profile);
			em.getTransaction().commit();
		}

		if (user.getGroup() != null) {
			request.setAttribute("groupId", user.getGroup().getId());
		}

		request.setAttribute("alreadyInContact", alreadyInContact);
		request.setAttribute(WATCHED_PROFILE_VARIABLE, profile);
		Paginator<Interest> paginatorInterest = new Paginator<Interest>(
				profile.getInterests(), request, 25, "profileInterests", "id");
		request.setAttribute("interestPaginator", paginatorInterest);
		Paginator<SocialEntity> paginatorContact = new Paginator<SocialEntity>(
				profile.getContacts(), request, 66, "profileContacts", "id");
		request.setAttribute("contactsPaginator", paginatorContact);
		request.setAttribute(EDITABLE_PROFILE_VARIABLE, profile.equals(user));

		if (profile.getBirthDate() != null) {
			request.setAttribute("birthDay",
					formatter.format(profile.getBirthDate()));
		}

		em.getTransaction().begin();
		InteractionFacade intFac = new InteractionFacade(em);

		request.setAttribute("interactions",
				intFac.getIntetactionsByUser(profile,(int)request.getSession().getAttribute(Authenticate.AUTHENTICATED_USER)));
		em.getTransaction().commit();

		request.setAttribute("currentUser", user);
		request.setAttribute("treeGroupProfile", sgf.treeParentName(profile));

		if (sgf.isMasterGroup(user)) {
			request.getSession(true).setAttribute(
					IS_MASTER_GROUP_ATTRIBUTE_NAME, true);
		} else {
			request.getSession(true).setAttribute(
					IS_MASTER_GROUP_ATTRIBUTE_NAME, false);
		}

		if (sgf.isGroupResponsible(user)) {
			request.getSession(true).setAttribute(
					IS_GROUP_RESPONSIBLE_ATTRIBUTE_NAME, true);
		} else {
			request.getSession(true).setAttribute(
					IS_GROUP_RESPONSIBLE_ATTRIBUTE_NAME, false);
		}

		SocialGroup socialGroup = profile.getGroup();
		request.setAttribute("socialGroup", socialGroup);

		LoggedUsersContainer loggedCon = (LoggedUsersContainer) ServletActionContext.getServletContext().getAttribute("loggedUsers");
		Map<Integer, String> loggeddd = loggedCon.getUsers();

		if (loggeddd.containsKey(id)) {
			request.setAttribute("isLogged", true);
		} else {
			request.setAttribute("isLogged", false);
		}

		em.close();
		
		return SUCCESS;
	}

	/**
	 * @param request
	 * @param key
	 */
	private void sendPictureError(String key) {
		addFieldError("photo", key);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 * @throws URISyntaxException
	 */
	public final String changePhoto() throws IOException, ServletException,
			URISyntaxException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		
		if (!fascade.isAuthorized(UserUtils.getAuthenticatedUser(request, em),
				Right.MODIFY_PICTURE)) {
			return "unauthorized";
		}

		InputStream inputStream = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet();
		URI uri = null;
		
		
		String urlType = null;
		
		if (photoUrl != null && !photoUrl.isEmpty()) {
			try {
				uri = new URI(photoUrl);
				httpGet.setURI(uri);
				HttpResponse httpResponse = httpClient.execute(httpGet);
				inputStream = httpResponse.getEntity().getContent();
				urlType = httpResponse.getEntity().getContentType().getValue();
			} catch (Exception e) {
				sendPictureError("updateProfile.error.photo.invalidlink");
				return SUCCESS;
			}
		} else {
			uri = null;
		}

		photoUrl="";
		
		int userId = UserUtils.getAuthenticatesUserId(request);
		addRightToRequest(request);
		
		if (photo.length() != 0) {
			PictureType pictureType = null;
			for (PictureType pt : PictureType.values()) {
				if (pt.getMimeType().equals(photoContentType)) {
					pictureType = pt;
					break;
				}
			}
			
			if (pictureType != null) {

				if (photo.length() > MAX_PICTURE_SIZE) {
					sendPictureError("updateProfile.error.photo.masize");
					return SUCCESS;
				}

				try {
					ImageManager.createPicturesForUser(userId,
							new FileInputStream(photo), pictureType);
					return SUCCESS;
				} catch (FileNotFoundException e) {
					sendPictureError(ERROR_UPDATE_ATTRIBUTE_STRING);
				} catch (IOException e) {
					sendPictureError(ERROR_UPDATE_ATTRIBUTE_STRING);
				} catch (IllegalStateException e) {
					sendPictureError(ERROR_UPDATE_ATTRIBUTE_STRING);
				}
			} else {
				sendPictureError("updateProfile.error.photo.type");
			}
		} else if (uri != null) {
			PictureType pictureType = null;
			for (PictureType pt : PictureType.values()) {
				if (pt.getMimeType().equals(urlType)) {
					pictureType = pt;
					break;
				}
			}
			
			if (pictureType != null) {

				if (inputStream.available() > MAX_PICTURE_SIZE) {
					sendPictureError("updateProfile.error.photo.masize");
					return SUCCESS;
				}

				try {
					ImageManager.createPicturesForUser(userId, inputStream,
							pictureType);
				} catch (FileNotFoundException e) {
					sendPictureError(ERROR_UPDATE_ATTRIBUTE_STRING);
				} catch (IOException e) {
					sendPictureError(ERROR_UPDATE_ATTRIBUTE_STRING);
				} catch (IllegalStateException e) {
					sendPictureError(ERROR_UPDATE_ATTRIBUTE_STRING);
				}
			} else {
				sendPictureError("updateProfile.error.photo.type");
			}
		}

		else {
			sendPictureError("updateProfile.error.photo.emptylink");
		}

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
	public final String deletePhoto() throws Exception {
		Integer userId = UserUtils.getAuthenticatesUserId(request);
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		
		if (!fascade.isAuthorized(UserUtils.getAuthenticatedUser(request, em),
				Right.MODIFY_PICTURE)) {
			return "unauthorized";
		}

		ImageManager.removeOldUserPicture(userId);
		addRightToRequest(request);
		return SUCCESS;
	}

	/**
	 * @param request
	 */
	private void addRightToRequest(HttpServletRequest request) {
		SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request);
		Right rightModifyProfil = Right.MODIFY_PROFIL;
		Right rightModifyPicture = Right.MODIFY_PICTURE;
		request.setAttribute("rightModifyProfil", rightModifyProfil);
		request.setAttribute("rightModifyPicture", rightModifyPicture);
		request.setAttribute("socialEntity", socialEntity);
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
}
