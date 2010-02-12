package fr.univartois.ili.fsnet.admin.actions;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.commons.mail.FSNetConfiguration;
import fr.univartois.ili.fsnet.commons.mail.FSNetMailer;
import fr.univartois.ili.fsnet.commons.mail.Mail;
import fr.univartois.ili.fsnet.entities.Property;

public class ConfigureFSNet extends MappingDispatchAction {

	private static final String DEFAULT_SMTP_PORT = "25";

	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");

	public ActionForward editMailConfiguration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		FSNetConfiguration conf = FSNetConfiguration.getInstance();
		Properties properties = conf.getFSNetConfiguration();
		DynaActionForm dynaform = (DynaActionForm) form; // NOSONAR
		try {
			dynaform.set("MailFrom", properties
					.getProperty(FSNetConfiguration.MAIL_FROM_KEY));
			if (conf.isTLSEnabled()) {
				dynaform.set("enableTLS", "on");
			} else {
				dynaform.set("enableTLS", "");
			}
			if (conf.isSSLEnabled()) {
				dynaform.set("enableSSL", "on");
			} else {
				dynaform.set("enableSSL", "");
			}
			if (conf.isAuthenticationEnabled()) {
				dynaform.set("enableAuthentication", "on");
				dynaform.set("SMTPUsername", properties
						.getProperty(FSNetConfiguration.SMTP_USER_KEY));
				dynaform.set("SMTPPassword", properties
						.getProperty(FSNetConfiguration.SMTP_PASSWORD_KEY));
			} else {
				dynaform.set("enableAuthentication", "");
				dynaform.set("SMTPUsername", "");
				dynaform.set("SMTPPassword", "");
			}
			dynaform.set("SMTPPort", properties
					.getProperty(FSNetConfiguration.SMTP_PORT_KEY));
			dynaform.set("SMTPHost", properties
					.getProperty(FSNetConfiguration.SMTP_HOST_KEY));
			dynaform.set("FSNetWebURL", properties
					.getProperty(FSNetConfiguration.FSNET_WEB_ADDRESS_KEY));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mapping.findForward("success");
	}

	public ActionForward saveMailConfiguration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		DynaActionForm dynaform = (DynaActionForm) form; // NOSONAR
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		if ("".equals(dynaform.get("enableTLS"))) {
			saveProperty(em, FSNetConfiguration.ENABLE_TLS_KEY, Boolean.FALSE
					.toString());
		} else {
			saveProperty(em, FSNetConfiguration.ENABLE_TLS_KEY, Boolean.TRUE
					.toString());
		}
		if ("".equals(dynaform.get("enableSSL"))) {
			saveProperty(em, FSNetConfiguration.SSL_KEY, Boolean.FALSE
					.toString());
		} else {
			saveProperty(em, FSNetConfiguration.SSL_KEY, Boolean.TRUE
					.toString());
		}
		if ("".equals(dynaform.get("enableAuthentication"))) {
			saveProperty(em, FSNetConfiguration.ENABLE_AUTHENTICATION_KEY,
					Boolean.FALSE.toString());
		} else {
			saveProperty(em, FSNetConfiguration.ENABLE_AUTHENTICATION_KEY,
					Boolean.TRUE.toString());
			saveProperty(em, FSNetConfiguration.SMTP_USER_KEY,
					(String) dynaform.get("SMTPUsername"));
			saveProperty(em, FSNetConfiguration.SMTP_PASSWORD_KEY,
					(String) dynaform.get("SMTPPassword"));
		}
		saveProperty(em, FSNetConfiguration.MAIL_FROM_KEY, (String) dynaform
				.get("MailFrom"));
		saveProperty(em, FSNetConfiguration.SMTP_HOST_KEY, (String) dynaform
				.get("SMTPHost"));
		String SMTP_Port = (String) dynaform.get("SMTPPort");
		try {
			Integer.parseInt(SMTP_Port);
		} catch (NumberFormatException e) {
			SMTP_Port = DEFAULT_SMTP_PORT;
		}
		saveProperty(em, FSNetConfiguration.SMTP_PORT_KEY, SMTP_Port);
		saveProperty(em, FSNetConfiguration.FSNET_WEB_ADDRESS_KEY,
				(String) dynaform.get("FSNetWebURL"));
		em.getTransaction().commit();
		em.close();
		FSNetConfiguration.getInstance().refreshConfiguration();
		return mapping.findForward("success");
	}

	private void saveProperty(EntityManager em, String key, String value) {
		Property property = em.find(Property.class, key);
		if (property == null) {
			property = new Property();
			property.setKey(key);
			property.setValue(value);
			em.persist(property);
		} else {
			property.setValue(value);
			em.merge(property);
		}
	}

	public ActionForward sendTestMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		FSNetMailer mailer = FSNetMailer.getInstance();
		Mail mail = mailer.createMail();
		mail.setSubject("FSNet mail test");
		mail.setContent("Configuration réussie");
		mail.addRecipient(dynaForm.getString("Recipient"));
		mailer.sendMail(mail);
		return mapping.findForward("success");
	}
}
