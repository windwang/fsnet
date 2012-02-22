package fr.univartois.ili.fsnet.admin.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
import org.apache.struts.util.MessageResources;

import fr.univartois.ili.fsnet.commons.mail.FSNetConfiguration;
import fr.univartois.ili.fsnet.commons.mail.FSNetMailer;
import fr.univartois.ili.fsnet.commons.mail.Mail;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Property;
import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 * @author FSNet
 * 
 */
public class ConfigureFSNet extends MappingDispatchAction {

	private static final String DEFAULT_SMTP_PORT = "25";
	private static final int MAX_PICTURE_SIZE = 500000;

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward editMailConfiguration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		FSNetConfiguration conf = FSNetConfiguration.getInstance();
		Properties properties = conf.getFSNetConfiguration();
		DynaActionForm dynaform = (DynaActionForm) form; // NOSONAR
		try {
			dynaform.set("MailFrom",
					properties.getProperty(FSNetConfiguration.MAIL_FROM_KEY));
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
			dynaform.set("SMTPPort",
					properties.getProperty(FSNetConfiguration.SMTP_PORT_KEY));
			dynaform.set("SMTPHost",
					properties.getProperty(FSNetConfiguration.SMTP_HOST_KEY));
			dynaform.set("FSNetWebURL", properties
					.getProperty(FSNetConfiguration.FSNET_WEB_ADDRESS_KEY));
			dynaform.set("FSNetWebURL", properties
					.getProperty(FSNetConfiguration.FSNET_WEB_ADDRESS_KEY));

			dynaform.set("PicturesDirectory", properties
					.getProperty(FSNetConfiguration.PICTURES_DIRECTORY_KEY));
			dynaform.set("KeyFacebook",
					properties.getProperty(FSNetConfiguration.KEY_FACEBOOK));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return mapping.findForward("success");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward saveMailConfiguration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		DynaActionForm dynaform = (DynaActionForm) form; // NOSONAR
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		if ("".equals(dynaform.get("enableTLS"))) {
			saveProperty(em, FSNetConfiguration.ENABLE_TLS_KEY,
					Boolean.FALSE.toString());
		} else {
			saveProperty(em, FSNetConfiguration.ENABLE_TLS_KEY,
					Boolean.TRUE.toString());
		}
		if ("".equals(dynaform.get("enableSSL"))) {
			saveProperty(em, FSNetConfiguration.SSL_KEY,
					Boolean.FALSE.toString());
		} else {
			saveProperty(em, FSNetConfiguration.SSL_KEY,
					Boolean.TRUE.toString());
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
		saveProperty(em, FSNetConfiguration.MAIL_FROM_KEY,
				(String) dynaform.get("MailFrom"));
		saveProperty(em, FSNetConfiguration.SMTP_HOST_KEY,
				(String) dynaform.get("SMTPHost"));
		String SMTP_Port = (String) dynaform.get("SMTPPort");
		try {
			Integer.parseInt(SMTP_Port);
		} catch (NumberFormatException e) {
			SMTP_Port = DEFAULT_SMTP_PORT;
		}
		saveProperty(em, FSNetConfiguration.SMTP_PORT_KEY, SMTP_Port);
		saveProperty(em, FSNetConfiguration.FSNET_WEB_ADDRESS_KEY,
				(String) dynaform.get("FSNetWebURL"));
		String dirName = (String) dynaform.get("PicturesDirectory");
		if (isValidDirectory(dirName)) {
			saveProperty(em, FSNetConfiguration.PICTURES_DIRECTORY_KEY, dirName);
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add("PicturesDirectory", new ActionMessage("configure.23"));
			saveErrors(request, errors);
		}

		em.getTransaction().commit();
		em.close();
		FSNetConfiguration.getInstance().refreshConfiguration();

		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");
		request.setAttribute("success", bundle.getMessage(request.getLocale(),
				"configure.mail.update.success"));

		return mapping.findForward("success");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward saveFacebookId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaform = (DynaActionForm) form; // NOSONAR
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();

		saveProperty(em, FSNetConfiguration.KEY_FACEBOOK,
				(String) dynaform.get("KeyFacebook"));

		em.getTransaction().commit();
		em.close();
		FSNetConfiguration.getInstance().refreshConfiguration();

		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");
		request.setAttribute("success", bundle.getMessage(request.getLocale(),
				"configure.facebook.update.success"));

		return mapping.findForward("success");
	}

	/**
	 * @param em
	 * @param key
	 * @param value
	 */
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

	/**
	 * @param dirName
	 * @return
	 */
	private boolean isValidDirectory(String dirName) {
		boolean isValidDirectory;
		File directory = new File(dirName);
		if (directory.isDirectory()) {
			isValidDirectory = true;
		} else {
			isValidDirectory = false;
		}
		return isValidDirectory;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward sendTestMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		FSNetMailer mailer = FSNetMailer.getInstance();
		Mail mail = mailer.createMail();
		mail.setSubject("FSNet mail test");
		mail.setContent("Successful configuration");
		mail.addRecipient(dynaForm.getString("Recipient"));
		mailer.sendMail(mail);

		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");
		request.setAttribute("success", bundle.getMessage(request.getLocale(),
				"configure.testMail.sent"));

		return mapping.findForward("success");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward updateDB(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String fileUri = FSNetConfiguration.getInstance()
				.getFSNetConfiguration()
				.getProperty(FSNetConfiguration.PICTURES_DIRECTORY_KEY)
				+ "/DB.txt";
		String line;
		File f = new File(fileUri);
		StringBuffer tmp = new StringBuffer();
		if (f.exists()) {

			InputStream ips = new FileInputStream(fileUri);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);

			while ((line = br.readLine()) != null) {
				if (line.contains("DB modified")) {
					tmp.append(line);
				}
			}
			try{
			br.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		if (!f.exists() || !tmp.toString().contains("DB modified")) {
			EntityManager entityManager = PersistenceProvider
					.createEntityManager();
			List<SocialEntity> entities = entityManager.createQuery(
					"SELECT s FROM SocialEntity s", SocialEntity.class)
					.getResultList();

			HashMap<String, String> mails = new HashMap<String, String>();
			for (SocialEntity s : entities) {
				mails.put(s.getEmail(), s.getEmail().toLowerCase());
			}

			for (String s : mails.keySet()) {
				entityManager.getTransaction().begin();
				Query query = entityManager
						.createQuery("UPDATE SocialEntity s SET s.email = :newmail WHERE s.email= :oldmail");
				query.setParameter("newmail", mails.get(s));
				query.setParameter("oldmail", s);
				query.executeUpdate();
				entityManager.getTransaction().commit();
			}

			PrintWriter out = new PrintWriter(new FileWriter(fileUri));
			out.println("DB modified");
			out.close();
		}

		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");
		request.setAttribute("success", bundle.getMessage(request.getLocale(),
				"configure.db.update.success"));

		return mapping.findForward("success");
	}

	public ActionForward updateDateType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		try {
			EntityManager em = PersistenceProvider.createEntityManager();
			em.getTransaction().begin();

			Query query = em
					.createNativeQuery("ALTER TABLE MEETING MODIFY STARTDATE DATETIME");
			query.executeUpdate();

			query = em
					.createNativeQuery("ALTER TABLE ANNOUNCEMENT MODIFY ENDDATE DATETIME");
			query.executeUpdate();

			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");
		request.setAttribute("success", bundle.getMessage(request.getLocale(),
				"configure.db.update.success"));

		return mapping.findForward("success");
	}

}
