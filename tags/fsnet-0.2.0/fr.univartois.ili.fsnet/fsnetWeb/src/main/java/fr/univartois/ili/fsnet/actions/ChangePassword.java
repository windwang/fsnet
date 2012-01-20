package fr.univartois.ili.fsnet.actions;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.ProfileFacade;
import fr.univartois.ili.fsnet.form.ChangePasswordForm;

/**
 * 
 * formular validator for change password form
 * @author geoffrey
 *
 */
public class ChangePassword extends Action{
	 
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();
		ProfileFacade pf = new ProfileFacade(em);
        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
        ChangePasswordForm cpf = (ChangePasswordForm) form;   			//NOSONAR
        em.getTransaction().begin();
        pf.changePassword(user,cpf.getOldPassword() , cpf.getNewPassword());
        em.getTransaction().commit();
        em.close();
        request.setAttribute("PasswordChange", "ok");
		return mapping.findForward("success");
	}
	
}
