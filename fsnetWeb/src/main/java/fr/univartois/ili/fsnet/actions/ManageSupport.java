package fr.univartois.ili.fsnet.actions;


import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;


import com.opensymphony.xwork2.ActionSupport;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.mail.FSNetConfiguration;
import fr.univartois.ili.fsnet.commons.mail.FSNetMailer;
import fr.univartois.ili.fsnet.commons.mail.Mail;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 * @author FSNet
 * 
 */
public class ManageSupport extends ActionSupport implements CrudAction {
	private HttpServletRequest request;
	private String supportTitle;
	private String supportContent;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#create(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String create()
			throws Exception {
//		String title = (String) formSupport.get("supportTitle");
//		String content = (String) formSupport.get("supportContent");

		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(
				request, em);
//		postMail(authenticatedUser.getEmail(), title, content);
		postMail(authenticatedUser.getEmail(), supportTitle, supportContent);
		//return new ActionRedirect(mapping.findForward("success"));
		return SUCCESS;
	}

	public String getSupportContent() {
		return supportContent;
	}

	public void setSupportContent(String supportContent) {
		this.supportContent = supportContent;
	}

	/**
	 * @param email
	 * @param title
	 * @param content
	 */
	private void postMail(String email, String title, String content) {
		try {
			FSNetConfiguration conf = FSNetConfiguration.getInstance();
			Properties properties = conf.getFSNetConfiguration();

			if (!"".equals(properties
					.getProperty(FSNetConfiguration.MAIL_FROM_KEY))) {
				FSNetMailer mailer = FSNetMailer.getInstance();
				Mail mail = mailer.createMail();

				mail.setSubject(title);
				mail.addRecipient(properties
						.getProperty(FSNetConfiguration.MAIL_FROM_KEY));
				mail.setContent("From : " + email + "<br/><br/>" + content);
				mailer.sendMail(mail);
			}
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#modify(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String modify()
			throws Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#delete(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String delete()
			throws Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#search(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String search()
			throws Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#display(org.apache.struts.
	 * action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String display()
			throws Exception {
		return null;
	}
}
