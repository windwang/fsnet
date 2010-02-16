package fr.univartois.ili.fsnet.actions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.mail.FSNetConfiguration;
import fr.univartois.ili.fsnet.commons.utils.DateUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.ProfileFacade;
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

	/*
	 * Return the path of the directory that receive pictures
	 */
	private String getStorageDirectory() {
		String directory = FSNetConfiguration.getInstance()
				.getFSNetConfiguration().getProperty(
						FSNetConfiguration.PICTURES_DIRECTORY_KEY);
		if (directory != null) {
			if (!directory.endsWith("/")) {
				directory = directory + "/";
			}
		}
		return directory;
	}

	/*
	 * install the picture in the proper directory
	 * @param fileName The file name
	 * @param suffix the file's sufix (.png, .bmp...)
	 * @param datas the picture's datas 
	 */
	private boolean installPicture(String fileName, String suffix, byte[] datas) {
		boolean installed = true;
		String directory = getStorageDirectory();
		if (directory != null) {
			removeOldPicture(directory + fileName);
			String fileToCreate = directory + fileName + suffix;
			File image = new File(fileToCreate);
			try {
				OutputStream out = new FileOutputStream(image);
				out.write(datas);
				out.flush();
				out.close();
			} catch (IOException e) {
				installed = false;
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
			}
		} else {
			installed = false;
			Logger.getAnonymousLogger().severe(
					"Le répertoire de stockage des images n'est pas configuré");
		}
		return installed;
	}

	/*
	 * Delete the picture currently installed on the file system
	 */
	private void removeOldPicture(String fileName) {
		File oldPicture = searchPicture(fileName);
		if (oldPicture != null) {
			oldPicture.delete();
			Logger.getAnonymousLogger().severe("oldPicture == null");
		}
	}

	/*
	 * return the java.io.File represented by filename on the file system
	 * @param fileName the filename without suffix
	 */
	private File searchPicture(String fileName) {
		for (AllowedPictureType pictureType : AllowedPictureType.values()) {
			File f = new File(fileName + pictureType.getSuffix());
			if (f.exists()) {
				return f;
			}
		}
		return null;
	}

	public ActionForward changePhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		FormFile file = (FormFile) dynaForm.get("photo");
		
		int userId = UserUtils.getAuthenticatesUserId(request);
		String fileName = Integer.toString(userId);
		
		if (file.getFileData().length != 0) {
			String suffix = null;
			for (AllowedPictureType pictureType : AllowedPictureType.values()) {
				if (pictureType.getMimeType().equals(file.getContentType())) {
					suffix = pictureType.getSuffix();
				}
			}
			
			if (suffix != null) {
				if (!installPicture(fileName, suffix, file.getFileData())) {
					ActionErrors errors = new ActionErrors();
					errors.add("photo", new ActionMessage(
							"updateProfile.error.photo.fatal"));
					saveErrors(request, errors);
				}
				
			} else {
				ActionErrors errors = new ActionErrors();
				errors.add("photo", new ActionMessage(
						"updateProfile.error.photo.type"));
				saveErrors(request, errors);
			}	
		} else {
			String directory = getStorageDirectory();
			if (directory != null) {
				removeOldPicture(directory + fileName);
			}
		}
		return mapping.findForward("success");
	}

	private void sendDefaultPhoto(HttpServletResponse response) {
		try {
			response.setContentType("image/png");
			InputStream inputStream = getServlet().getServletContext()
					.getResourceAsStream("/images/DefaultPhoto.png");
			OutputStream out = response.getOutputStream();
			byte[] datas = new byte[4096];
			int numReaded = inputStream.read(datas);
			while (numReaded != -1) {
				out.write(datas, 0, numReaded);
				numReaded = inputStream.read(datas);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE,
					"Default photo not found", e);
		} catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE,
					"Default photo cannot be readed", e);
		}
	}

	public ActionForward getPhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		byte[] datas = null;
		String memberId = dynaForm.getString("memberId");
		String directory = getStorageDirectory();
		if (directory != null) {
			File picture = searchPicture(directory + memberId);
			if (picture != null) {
				String contentType = null;
				for (AllowedPictureType pictureType : AllowedPictureType
						.values()) {
					if (picture.getName().endsWith(pictureType.getSuffix())) {
						contentType = pictureType.getMimeType();
					}
				}
				response.setContentType(contentType);
				response.setContentLength((int) picture.length());
				datas = new byte [(int) picture.length()];
				BufferedInputStream stream = new BufferedInputStream(new FileInputStream(picture));
				stream.read(datas);
				OutputStream out = response.getOutputStream();
				out.write(datas);
			} else {
				sendDefaultPhoto(response);
			}
		} else {
			sendDefaultPhoto(response);
		}
		return null;
	}
}
