package fr.univartois.ili.fsnet.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
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
import org.apache.struts.upload.FormFile;

import fr.univartois.ili.fsnet.actions.utils.ImageManager;
import fr.univartois.ili.fsnet.actions.utils.PictureType;
import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.DateUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.ProfileFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.ProfileVisiteFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade;

/**
 * 
 * @author Geoffrey Boulay
 */
public class ManageProfile extends MappingDispatchAction implements CrudAction {

	/**
	 * watched profile variable session name
	 */
	public static final String WATCHED_PROFILE_VARIABLE = "watchedProfile";
	public static final String EDITABLE_PROFILE_VARIABLE = "edit";
	private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * format a name exemple : entry : le BerrE return Le Berre
	 * 
	 * @param name
	 *            string to format
	 * @return format string
	 */
	public static final String formatName(String name) {
		StringBuffer buf = new StringBuffer();
		boolean upperCase = true;
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			switch (c) {
			case '\'':
				;
			case ' ':
				;
			case '-':
				buf.append(c);
				upperCase = true;
				break;
			default:
				buf.append((upperCase) ? (Character.toUpperCase(c))
						: (Character.toLowerCase(c)));
				upperCase = false;
			}
		}
		return buf.toString();
	}

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		Date birthday = null;
		try {
			birthday = DateUtils.format(dynaForm.getString("dateOfBirth"));
			if (birthday.after(new Date())) {
				ActionErrors errors = new ActionErrors();
				errors.add("dateOfBirth", new ActionMessage("date.error.invalid"));
				saveErrors(request, errors);
				return mapping.getInputForward();
			}
		} catch (ParseException e1) {
			// DO NOTHING
		}
		ProfileFacade pf = new ProfileFacade(em);
		em.getTransaction().begin();
		pf.editProfile(UserUtils.getAuthenticatedUser(request, em),
				formatName(dynaForm.getString("name")), formatName(dynaForm
						.getString("firstName")), new Address(dynaForm
						.getString("adress"), formatName(dynaForm
						.getString("city"))), birthday, dynaForm
						.getString("sexe"), formatName(dynaForm
						.getString("job")), dynaForm.getString("mail"),
				dynaForm.getString("phone"));
		em.getTransaction().commit();
		em.close();
		return mapping.findForward("success");
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public ActionForward displayToModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dyna = (DynaActionForm) form; // NOSONAR
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
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
		em.close();
		return mapping.findForward("success");
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntityFacade sef = new SocialEntityFacade(em);
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		DynaActionForm dyna = (DynaActionForm) form; // NOSONAR
		Boolean alreadyInContact = false;

		int id = -1;
		try {
			String idS = dyna.getString("id");
			id = Integer.parseInt(idS);
		} catch (NumberFormatException e) {
			id = user.getId();
		}
		SocialEntity profile = sef.getSocialEntity(id);
		if (user.getContacts().contains(profile)
				|| user.getAsked().contains(profile)
				|| user.getRequested().contains(profile)
				|| user.getRefused().contains(profile)) {
			alreadyInContact = true;
		}
		if(!profile.equals(user)){
			ProfileVisiteFacade pvf = new ProfileVisiteFacade(em);
			em.getTransaction().begin();
			pvf.visite(user, profile);
			em.getTransaction().commit();
		}
		request.setAttribute("alreadyInContact", alreadyInContact);
		request.setAttribute(WATCHED_PROFILE_VARIABLE, profile);
		request.setAttribute(EDITABLE_PROFILE_VARIABLE, profile.equals(user));
		if (profile.getBirthDate() != null) {
			request.setAttribute("birthDay", formatter.format(profile
					.getBirthDate()));
		}
		em.close();
		request.setAttribute("currentUser", user);
		return mapping.findForward("success");
	}

	private void sendPictureError(HttpServletRequest request, String key) {
		ActionErrors errors = new ActionErrors();
		errors.add("photo", new ActionMessage(key));
		saveErrors(request, errors);
	}

	public ActionForward changePhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		FormFile file = (FormFile) dynaForm.get("photo");
		int userId = UserUtils.getAuthenticatesUserId(request);
		if (file.getFileData().length != 0) {
			PictureType pictureType = null;
			for (PictureType pt : PictureType.values()) {
				if (pt.getMimeType().equals(file.getContentType())) {
					pictureType = pt;
					break;
				}
			}
			if (pictureType != null) {
				try {
					ImageManager.createPicturesForUser(userId, file
							.getInputStream(), pictureType);
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
		} else {
			ImageManager.removeOldUserPicture(userId);
		}
		return mapping.findForward("success");
	}

	public ActionForward getPhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String userId = dynaForm.getString("memberId");
		ImageManager.sendUserPicture(Integer.parseInt(userId), response);
		return null;
	}
	
	public ActionForward getMiniature(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String userId = dynaForm.getString("memberId");
		ImageManager.sendUserMiniature(Integer.parseInt(userId), response);
		return null;
	}
}
