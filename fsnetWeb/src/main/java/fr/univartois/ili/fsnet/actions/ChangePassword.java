package fr.univartois.ili.fsnet.actions;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.DynaActionForm;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.security.Encryption;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.ProfileFacade;


/**
 * 
 * formular validator for change password form
 * @author geoffrey
 *
 */
public class ChangePassword extends Action{
	 
	private static final String OLD_PASSWORD_ATTRIBUTE_NAME = "oldPassword";

	
	@Override
	public final ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        EntityManager em = PersistenceProvider.createEntityManager();
        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
        ActionErrors errors = new ActionErrors();
        String old = request.getParameter(OLD_PASSWORD_ATTRIBUTE_NAME);
        if(old != null){
        	old = Encryption.getEncodedPassword(old);
        }
        if ((old == null)||(!user.getPassword().equals(old))) {
            errors.add(OLD_PASSWORD_ATTRIBUTE_NAME, new ActionMessage("error.changePassword.oldPassword"));
            this.saveErrors(request, errors);
            return mapping.getInputForward();
        }
		ProfileFacade pf = new ProfileFacade(em);
        DynaActionForm cpf = (DynaActionForm) form;   			//NOSONAR
        em.getTransaction().begin();
        pf.changePassword(user,cpf.getString(OLD_PASSWORD_ATTRIBUTE_NAME) , cpf.getString("newPassword"));
        em.getTransaction().commit();
        em.close();
        errors.add("passwordChange",new ActionMessage("updateProfile.passwd.change"));
        this.saveErrors(request, errors);
        
        cpf.set(OLD_PASSWORD_ATTRIBUTE_NAME, "");
        cpf.set("newPassword", "");
        cpf.set("confirmNewPassword", "");
		return mapping.findForward("success");
	}
	
}
