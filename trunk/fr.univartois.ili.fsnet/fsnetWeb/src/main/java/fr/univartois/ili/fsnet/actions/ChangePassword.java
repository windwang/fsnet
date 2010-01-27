package fr.univartois.ili.fsnet.actions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.form.ChangePasswordForm;
import fr.univartois.ili.fsnet.security.Md5;

/**
 * 
 * formular validator for change password form
 * @author geoffrey
 *
 */
public class ChangePassword extends Action{

	 private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");

	 
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EntityManager em = factory.createEntityManager();
        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
        ChangePasswordForm cpf = (ChangePasswordForm) form;
        user.setPassword(Md5.getEncodedPassword(cpf.getNewPassword()));
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        em.close();
		return mapping.findForward("success");
	}
	
}
