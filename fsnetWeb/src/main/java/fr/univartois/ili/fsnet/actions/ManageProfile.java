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
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.upload.FormFile;

import fr.univartois.ili.fsnet.actions.utils.FacebookKeyManager;
import fr.univartois.ili.fsnet.actions.utils.ImageManager;
import fr.univartois.ili.fsnet.actions.utils.PictureType;
import fr.univartois.ili.fsnet.actions.utils.UserUtils;
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
public class ManageProfile extends MappingDispatchAction implements CrudAction {

	private static final int MAX_PICTURE_SIZE = 500000;
	/**
	 * watched profile variable session name
	 */
	public static final String WATCHED_PROFILE_VARIABLE = "watchedProfile";
	public static final String EDITABLE_PROFILE_VARIABLE = "edit";
	private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

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
	public final ActionForward create(ActionMapping mapping, ActionForm form,
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
	private ActionErrors verified(DynaActionForm dynaForm, EntityManager em,
			HttpServletRequest request) {
		ActionErrors res = new ActionErrors();
		try {
			Date birthday = DateUtils.formatDate(dynaForm
					.getString("dateOfBirth"));
			Date actualDate = new Date();
			if (birthday.after(actualDate)) {
				res.add("dateOfBirth", new ActionMessage("date.error.invalid"));
			}
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 100);
			Date lastedDate = cal.getTime();
			if (birthday.before(lastedDate)) {
				res.add("dateOfBirth", new ActionMessage("date.error.invalid"));
			}
		} catch (ParseException e1) {
			// DO NOTHING EMPTY DATE
		}
		if (!UserUtils.getAuthenticatedUser(request, em).getEmail()
				.equals(dynaForm.getString("mail"))) {
			SocialEntityFacade sef = new SocialEntityFacade(em);
			em.getTransaction().begin();
			SocialEntity se = sef.findByEmail(dynaForm.getString("mail"));
			em.getTransaction().commit();
			if (se != null) {
				res.add("mail", new ActionMessage(
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
	public final ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		if (!fascade.isAuthorized(UserUtils.getAuthenticatedUser(request, em),
				Right.MODIFY_PROFIL)) {
			em.close();
			return new ActionRedirect(mapping.findForward("unauthorized"));
		}

		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		Date birthday = null;
		addRightToRequest(request);
		try {
			birthday = DateUtils.formatDate(dynaForm.getString("dateOfBirth"));
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
						.getString("mail").toLowerCase(), dynaForm
						.getString("phone"));
		em.getTransaction().commit();
		em.close();
		return mapping.findForward("success");
	}

	/**
	 * @param request
	 * @param response
	 */
	private void addKeyFacebookInRequest(HttpServletRequest request,
			HttpServletResponse response) {
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
	public final ActionForward delete(ActionMapping mapping, ActionForm form,
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
	public final ActionForward search(ActionMapping mapping, ActionForm form,
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
	public final ActionForward displayToModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		addKeyFacebookInRequest(request, response);
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dyna = (DynaActionForm) form; // NOSONAR
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
			dyna.set("dateOfBirth", formatter.format(user.getBirthDate()));
		}
		dyna.set("sexe", user.getSex());
		dyna.set("job", user.getProfession());
		dyna.set("mail", user.getEmail());
		dyna.set("phone", user.getPhone());
		if (sgf.isMasterGroup(user))
			request.getSession(true).setAttribute("isMasterGroup", true);
		else
			request.getSession(true).setAttribute("isMasterGroup", false);
		if (sgf.isGroupResponsible(user))
			request.getSession(true).setAttribute("isGroupResponsible", true);
		else
			request.getSession(true).setAttribute("isGroupResponsible", false);
		em.close();
		return mapping.findForward("success");
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
	public final ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		addKeyFacebookInRequest(request, response);
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntityFacade sef = new SocialEntityFacade(em);
		SocialGroupFacade sgf = new SocialGroupFacade(em);

		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		DynaActionForm dyna = (DynaActionForm) form; // NOSONAR
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

		if (user.getGroup() != null)
			request.setAttribute("groupId", user.getGroup().getId());
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
				intFac.getIntetactionsByUser(profile));
		em.getTransaction().commit();

		request.setAttribute("currentUser", user);
		request.setAttribute("treeGroupProfile", sgf.TreeParentName(profile));

		if (sgf.isMasterGroup(user))
			request.getSession(true).setAttribute("isMasterGroup", true);
		else
			request.getSession(true).setAttribute("isMasterGroup", false);
		if (sgf.isGroupResponsible(user))
			request.getSession(true).setAttribute("isGroupResponsible", true);
		else
			request.getSession(true).setAttribute("isGroupResponsible", false);

		SocialGroup socialGroup = profile.getGroup();
		request.setAttribute("socialGroup", socialGroup);
		em.close();

		LoggedUsersContainer loggedCon = (LoggedUsersContainer) getServlet()
				.getServletContext().getAttribute("loggedUsers");
		Map<Integer, String> loggeddd = loggedCon.getUsers();
		if (loggeddd.containsKey(id)) {
			System.out.println("id " + id + "  is logged");
			request.setAttribute("isLogged", true);
		} else {
			System.out.println("id " + id + "  is not logged");
			request.setAttribute("isLogged", false);
		}

		return mapping.findForward("success");
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
	public final ActionForward changePhoto(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			URISyntaxException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		if (!fascade.isAuthorized(UserUtils.getAuthenticatedUser(request, em),
				Right.MODIFY_PICTURE))
			return new ActionRedirect(mapping.findForward("unauthorized"));

		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		FormFile file = (FormFile) dynaForm.get("photo");
		InputStream inputStream = null;
		HttpClient httpClient = new DefaultHttpClient();
		;
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
				return mapping.findForward("success");
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
					return mapping.findForward("success");
				}

				try {
					ImageManager.createPicturesForUser(userId,
							file.getInputStream(), pictureType);
					return mapping.findForward("success");
				} catch (FileNotFoundException e) {
					sendPictureError(request, "updateProfile.error.photo.fatal");
				} catch (IOException e) {
					sendPictureError(request, "updateProfile.error.photo.fatal");
				} catch (IllegalStateException e) {
					sendPictureError(request, "updateProfile.error.photo.fatal");
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
					return mapping.findForward("success");
				}

				try {
					ImageManager.createPicturesForUser(userId, inputStream,
							pictureType);
				} catch (FileNotFoundException e) {
					sendPictureError(request, "updateProfile.error.photo.fatal");
				} catch (IOException e) {
					sendPictureError(request, "updateProfile.error.photo.fatal");
				} catch (IllegalStateException e) {
					sendPictureError(request, "updateProfile.error.photo.fatal");
				}
			} else {
				sendPictureError(request, "updateProfile.error.photo.type");
			}
		}

		else {
			sendPictureError(request, "updateProfile.error.photo.emptylink");
		}

		return mapping.findForward("success");
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
	public final ActionForward deletePhoto(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Integer userId = UserUtils.getAuthenticatesUserId(request);
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		if (!fascade.isAuthorized(UserUtils.getAuthenticatedUser(request, em),
				Right.MODIFY_PICTURE))
			return new ActionRedirect(mapping.findForward("unauthorized"));

		ImageManager.removeOldUserPicture(userId);
		addRightToRequest(request);
		return mapping.findForward("success");
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
