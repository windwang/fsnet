package fr.univartois.ili.fsnet.actions;

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
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.String;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.actions.ActionSupport;
import org.apache.struts.upload.FormFile;

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
public class ManageProfile extends ActionSupport implements CrudAction {

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
	public final String create(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * @param dynaForm
	 * @param em
	 * @param request
	 * @return
	 */
			HttpServletRequest request) {
		ActionErrors res = new ActionErrors();
		try {
			Date birthday = DateUtils.formatDate(dynaForm
					.getString(DATE_OF_BIRTH_FORM_FIELD_NAME));
			Date actualDate = new Date();
			if (birthday.after(actualDate)) {
				res.add(DATE_OF_BIRTH_FORM_FIELD_NAME, new ActionMessage(
						"date.error.invalid"));
			}
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 100);
			Date lastedDate = cal.getTime();
			if (birthday.before(lastedDate)) {
				res.add(DATE_OF_BIRTH_FORM_FIELD_NAME, new ActionMessage(
						"date.error.invalid"));
			}
		} catch (ParseException e1) {
			// DO NOTHING EMPTY DATE
		}

		if (!UserUtils.getAuthenticatedUser(request, em).getEmail()
				.equals(dynaForm.getString(MAIL_FORM_FIELD_NAME))) {
			SocialEntityFacade sef = new SocialEntityFacade(em);
			em.getTransaction().begin();
			SocialEntity se = sef.findByEmail(dynaForm
					.getString(MAIL_FORM_FIELD_NAME));
			em.getTransaction().commit();
			//em.close();

			if (se != null) {
				res.add(MAIL_FORM_FIELD_NAME, new ActionMessage(
						"error.updateProfile.email.alwaysExist"));
			}
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
	public final String modify(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade fascade = new SocialGroupFacade(em);

		if (!fascade.isAuthorized(UserUtils.getAuthenticatedUser(request, em),
				Right.MODIFY_PROFIL)) {
			em.close();
			return new ActionRedirect(mapping.findForward("unauthorized"));
		}

		Date birthday = null;
		addRightToRequest(request);

		try {
			birthday = DateUtils.formatDate(dynaForm
					.getString(DATE_OF_BIRTH_FORM_FIELD_NAME));
		} catch (ParseException e) {
			// DO NOTHING EMPTY DATE
		}

		ActionErrors actionsErrors = verified(dynaForm, em, request);

		if (!actionsErrors.isEmpty()) {
			saveErrors(request, actionsErrors);
			em.close();
			return mapping.getInputForward();
		}

		ProfileFacade pf = new ProfileFacade(em);
		em.getTransaction().begin();
		pf.editProfile(
				UserUtils.getAuthenticatedUser(request, em),
				dynaForm.getString("name"),
				dynaForm.getString("firstName"),
				new Address(dynaForm.getString("adress"), dynaForm
						.getString("city")), birthday, dynaForm
						.getString("sexe"), dynaForm.getString("job"), dynaForm
						.getString(MAIL_FORM_FIELD_NAME).toLowerCase(),
				dynaForm.getString("phone"));
		em.getTransaction().commit();
		em.close();

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
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
	public final String delete(
			HttpServletRequest request, HttpServletResponse response)
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
	public final String search(
			HttpServletRequest request, HttpServletResponse response)
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
	public final String displayToModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		addKeyFacebookInRequest(request);
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		SocialGroupFacade sgf = new SocialGroupFacade(em);
		addRightToRequest(request);
		request.setAttribute("currentUser", user);
		dyna.set("name", user.getName());
		dyna.set("firstName", user.getFirstName());

		if (user.getAddress() != null) {
			dyna.set("adress", user.getAddress().getAddress());
			dyna.set("city", user.getAddress().getCity());
		}

		if (user.getBirthDate() != null) {
			dyna.set(DATE_OF_BIRTH_FORM_FIELD_NAME,
					formatter.format(user.getBirthDate()));
		}

		dyna.set("sexe", user.getSex());
		dyna.set("job", user.getProfession());
		dyna.set(MAIL_FORM_FIELD_NAME, user.getEmail());
		dyna.set("phone", user.getPhone());

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
	public final String display(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		addKeyFacebookInRequest(request);
		EntityManager em = PersistenceProvider.createEntityManager();

		SocialEntityFacade sef = new SocialEntityFacade(em);
		SocialGroupFacade sgf = new SocialGroupFacade(em);

		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		Boolean alreadyInContact = false;

		int id = -1;
		addRightToRequest(request);

		try {
			String idS = dyna.getString("id");
			id = Integer.parseInt(idS);
		} catch (NumberFormatException e) {
			id = user.getId();
		}

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

		LoggedUsersContainer loggedCon = (LoggedUsersContainer) getServlet()
				.getServletContext().getAttribute("loggedUsers");
		Map<Integer, String> loggeddd = loggedCon.getUsers();

		if (loggeddd.containsKey(id)) {
			request.setAttribute("isLogged", true);
		} else {
			request.setAttribute("isLogged", false);
		}

		em.close();
		
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	/**
	 * @param request
	 * @param key
	 */
	private void sendPictureError(HttpServletRequest request, String key) {
		ActionErrors errors = new ActionErrors();
		errors.add("photo", new ActionMessage(key));
		saveErrors(request, errors);
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
	public final String changePhoto(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			URISyntaxException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		
		if (!fascade.isAuthorized(UserUtils.getAuthenticatedUser(request, em),
				Right.MODIFY_PICTURE)) {
			return new ActionRedirect(mapping.findForward("unauthorized"));
		}

		FormFile file = (FormFile) dynaForm.get("photo");
		InputStream inputStream = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet();
		URI uri = null;
		String stringUrl = (String) dynaForm.get("photoUrl");
		dynaForm.set("photoUrl", "");
		String urlType = null;
		
		if (stringUrl != null && !stringUrl.isEmpty()) {
			try {
				uri = new URI(stringUrl);
				httpGet.setURI(uri);
				HttpResponse httpResponse = httpClient.execute(httpGet);
				inputStream = httpResponse.getEntity().getContent();
				urlType = httpResponse.getEntity().getContentType().getValue();
			} catch (Exception e) {
				sendPictureError(request,
						"updateProfile.error.photo.invalidlink");
				return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
			}
		} else {
			uri = null;
		}

		int userId = UserUtils.getAuthenticatesUserId(request);
		addRightToRequest(request);
		
		if (file.getFileData().length != 0) {
			PictureType pictureType = null;
			for (PictureType pt : PictureType.values()) {
				if (pt.getMimeType().equals(file.getContentType())) {
					pictureType = pt;
					break;
				}
			}
			
			if (pictureType != null) {

				if (file.getFileSize() > MAX_PICTURE_SIZE) {
					sendPictureError(request,
							"updateProfile.error.photo.masize");
					return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
				}

				try {
					ImageManager.createPicturesForUser(userId,
							file.getInputStream(), pictureType);
					return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
				} catch (FileNotFoundException e) {
					sendPictureError(request, ERROR_UPDATE_ATTRIBUTE_STRING);
				} catch (IOException e) {
					sendPictureError(request, ERROR_UPDATE_ATTRIBUTE_STRING);
				} catch (IllegalStateException e) {
					sendPictureError(request, ERROR_UPDATE_ATTRIBUTE_STRING);
				}
			} else {
				sendPictureError(request, "updateProfile.error.photo.type");
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
					sendPictureError(request,
							"updateProfile.error.photo.masize");
					return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
				}

				try {
					ImageManager.createPicturesForUser(userId, inputStream,
							pictureType);
				} catch (FileNotFoundException e) {
					sendPictureError(request, ERROR_UPDATE_ATTRIBUTE_STRING);
				} catch (IOException e) {
					sendPictureError(request, ERROR_UPDATE_ATTRIBUTE_STRING);
				} catch (IllegalStateException e) {
					sendPictureError(request, ERROR_UPDATE_ATTRIBUTE_STRING);
				}
			} else {
				sendPictureError(request, "updateProfile.error.photo.type");
			}
		}

		else {
			sendPictureError(request, "updateProfile.error.photo.emptylink");
		}

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
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
	public final String deletePhoto(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Integer userId = UserUtils.getAuthenticatesUserId(request);
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		
		if (!fascade.isAuthorized(UserUtils.getAuthenticatedUser(request, em),
				Right.MODIFY_PICTURE)) {
			return new ActionRedirect(mapping.findForward("unauthorized"));
		}

		ImageManager.removeOldUserPicture(userId);
		addRightToRequest(request);
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
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
}
