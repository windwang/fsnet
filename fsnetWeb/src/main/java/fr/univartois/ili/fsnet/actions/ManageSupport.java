package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;


import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.mail.FSNetConfiguration;
import fr.univartois.ili.fsnet.commons.mail.FSNetMailer;
import fr.univartois.ili.fsnet.commons.mail.Mail;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Property;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class ManageSupport extends MappingDispatchAction implements CrudAction {

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm formSupport = (DynaActionForm) form; // NOSONAR
		String title = (String) formSupport.get("supportTitle");
		String content = (String) formSupport.get("supportContent");

		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(
				request, em);
			postMail(authenticatedUser.getEmail(), title, content);
		
		return new ActionRedirect(mapping.findForward("success"));
	}

	private void postMail(String email, String title, String content) {
		// TODO Auto-generated method stub
			try {
				
				Property property;
				FSNetConfiguration conf = FSNetConfiguration.getInstance();
				Properties properties = conf.getFSNetConfiguration();
		
				if (!"".equals(properties.getProperty(FSNetConfiguration.MAIL_FROM_KEY))) {
						FSNetMailer mailer = FSNetMailer.getInstance();
						Mail mail = mailer.createMail();
		
						mail.setSubject(title);
						mail.addRecipient(properties
								.getProperty(FSNetConfiguration.MAIL_FROM_KEY));
						mail.setContent("From : " + email + "<br/><br/>" + content);
						mailer.sendMail(mail);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}

	}

	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}
}
