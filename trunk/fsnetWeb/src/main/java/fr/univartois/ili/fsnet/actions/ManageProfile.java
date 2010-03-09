package fr.univartois.ili.fsnet.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.DateUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.ProfileFacade;
import fr.univartois.ili.fsnet.facade.ProfileVisiteFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;

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



	@Override
	public final ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	private ActionErrors verified(DynaActionForm dynaForm, EntityManager em, HttpServletRequest request, Date birthday){
		ActionErrors res = new ActionErrors();
		try {
			birthday = DateUtils.format(dynaForm.getString("dateOfBirth"));
			if (birthday.after(new Date())) {
				res.add("dateOfBirth", new ActionMessage("date.error.invalid"));
			}
		} catch (ParseException e1) {
			// DO NOTHING
		}
		if(! UserUtils.getAuthenticatedUser(request, em).getEmail().equals(dynaForm.getString("mail"))){
			SocialEntityFacade sef = new SocialEntityFacade(em);
			em.getTransaction().begin();
			SocialEntity se = sef.findByEmail(dynaForm.getString("mail"));
			em.getTransaction().commit();
			if(se != null){
				res.add("mail", new ActionMessage("error.updateProfile.email.alwaysExist"));
			}
		}
		return res;
	}
	
	@Override
	public final ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		Date birthday = null;
		try {
			birthday = DateUtils.format(dynaForm.getString("dateOfBirth"));
		} catch (ParseException e1) {
			// DO NOTHING
		}
		ActionErrors actionsErrors = verified(dynaForm, em, request, birthday);
		if(! actionsErrors.isEmpty()){
			saveErrors(request, actionsErrors);
			em.close();
			return mapping.getInputForward();
		}
		ProfileFacade pf = new ProfileFacade(em);
		em.getTransaction().begin();
		pf.editProfile(UserUtils.getAuthenticatedUser(request, em),
				dynaForm.getString("name"), 
				dynaForm.getString("firstName"),
				new Address(dynaForm.getString("adress"),
				dynaForm.getString("city")),
				birthday, dynaForm.getString("sexe"),
				dynaForm.getString("job"),
				dynaForm.getString("mail"),
				dynaForm.getString("phone"));
		em.getTransaction().commit();
		em.close();
		return mapping.findForward("success");
	}

	@Override
	public final ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public final ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public final ActionForward displayToModify(ActionMapping mapping,
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
	public final ActionForward display(ActionMapping mapping, ActionForm form,
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
		if(profile==null){
			em.close();
			return mapping.findForward("fail");
		}
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
		Paginator<Interest> paginatorInterest = new Paginator<Interest>(profile.getInterests(), request, "profileInterests", "id");
		request.setAttribute("interestPaginator", paginatorInterest);
		Paginator<SocialEntity> paginatorContact = new Paginator<SocialEntity>(profile.getContacts(), request, "profileContacts", "id");
		request.setAttribute("contactsPaginator", paginatorContact);
		request.setAttribute(EDITABLE_PROFILE_VARIABLE, profile.equals(user));
		if (profile.getBirthDate() != null) {
			request.setAttribute("birthDay", formatter.format(profile
					.getBirthDate()));
		}
		em.getTransaction().begin();
		InteractionFacade intFac = new InteractionFacade(em);
		List<Interaction> interactions = intFac.getIntetactionsByUser(profile);
		request.setAttribute("interactions", interactions);
		em.getTransaction().commit();
		em.close();
		request.setAttribute("currentUser", user);
		return mapping.findForward("success");
	}

	private void sendPictureError(HttpServletRequest request, String key) {
		ActionErrors errors = new ActionErrors();
		errors.add("photo", new ActionMessage(key));
		saveErrors(request, errors);
	}

	public final ActionForward changePhoto(ActionMapping mapping, ActionForm form,
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

	public final ActionForward deletePhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		Integer userId = UserUtils.getAuthenticatesUserId(request);
		ImageManager.removeOldUserPicture(userId);
		return mapping.findForward("success");
	}
}
