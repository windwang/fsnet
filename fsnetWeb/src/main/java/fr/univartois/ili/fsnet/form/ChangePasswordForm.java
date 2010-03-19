package fr.univartois.ili.fsnet.form;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.security.Encryption;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class ChangePasswordForm extends ValidatorForm {
	/**
	 * Minimum size for a password
	 */
	public static final int PASSWORD_MIN_LENGTH=6;
	/**
	 * Maximum size for a password
	 */
	public static final int PASSWORD_MAX_LENGTH=30;
	
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        EntityManager em = PersistenceProvider.createEntityManager();
        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
        ActionErrors res = super.validate(mapping, request);
        String old = Encryption.getEncodedPassword(oldPassword);
        if (!user.getPassword().equals(old)) {
            res.add("oldPassword", new ActionMessage("error.changePassword.oldPassword"));
        }
        if (!newPassword.equals(confirmNewPassword)) {
            res.add("confirmNewPassword", new ActionMessage("error.updateProfile.confirmPwd.diff"));
        }
        if(newPassword.length()<PASSWORD_MIN_LENGTH || newPassword.length()>PASSWORD_MAX_LENGTH){
        	res.add("newPassword",new ActionMessage("error.updateProfile.passwd.size"));
        }
        return res;
    }
}
