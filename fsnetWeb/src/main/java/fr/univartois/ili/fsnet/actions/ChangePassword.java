package fr.univartois.ili.fsnet.actions;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.security.Encryption;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.ProfileFacade;

/**
 * 
 * formular validator for change password form
 * 
 * @author geoffrey
 * 
 */
public class ChangePassword extends ActionSupport implements
		ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String OLD_PASSWORD_ATTRIBUTE_NAME = "oldPassword";

	private HttpServletRequest request;
	private String newPassword;
	private String oldPassword;
	private String confirmNewPassword;

	@Override
	public final String execute() throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);

		String old = request.getParameter(OLD_PASSWORD_ATTRIBUTE_NAME);
		if (old != null) {
			old = Encryption.getEncodedPassword(old);
		}
		if ((old == null) || (!user.getPassword().equals(old))) {
			addFieldError(OLD_PASSWORD_ATTRIBUTE_NAME,
					"error.changePassword.oldPassword");
			return INPUT;
		}
		ProfileFacade pf = new ProfileFacade(em);
		em.getTransaction().begin();
		pf.changePassword(user, oldPassword, "newPassword");
		em.getTransaction().commit();
		em.close();
		addFieldError("passwordChange", "updateProfile.passwd.change");

		oldPassword = "";
		newPassword = "";
		confirmNewPassword = "";
		return SUCCESS;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
