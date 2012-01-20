package fr.univartois.ili.fsnet.form;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.security.Md5;

public class ChangePasswordForm extends ValidatorForm {

    /**
     *
     */
    private static final long serialVersionUID = -4883204887473399364L;
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
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");

    @Override
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        EntityManager em = factory.createEntityManager();
        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
        ActionErrors res = super.validate(mapping, request);
        String old = Md5.getEncodedPassword(oldPassword);
        if (!user.getPassword().equals(old)) {
            res.add("oldPassword", new ActionMessage("error.changePassword.oldPassword"));
        }
        if (!newPassword.equals(confirmNewPassword)) {
            res.add("confirmNewPassword", new ActionMessage("error.updateProfile.confirmPwd.diff"));
        }
        return res;
    }
}
