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
import java.util.logging.Level;
import java.util.logging.Logger;

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
	
	private static final String CONFIGURATION_ENABLETLS_FORM = "enableTLS";
	private static final String CONFIGURATION_ENABLESSL_FORM = "enableSSL";
	private static final String CONFIGURATION_ENABLEAUTHENTIFICATION_FORM = "enableAuthentication";
	private static final String CONFIGURATION_SMTPUSERNAME_FORM = "SMTPUsername";
	private static final String CONFIGURATION_SMTPPASSWORD_FORM = "SMTPPassword";
	private static final String CONFIGURATION_SMTPPORT_FORM = "SMTPPort";
	private static final String CONFIGURATION_SMTPHOST_FORM = "SMTPHost";
	private static final String CONFIGURATION_FSNETWEBURL_FORM = "FSNetWebURL";
	private static final String CONFIGURATION_PICTURESDIRECTORY_FORM = "PicturesDirectory";
	private static final String CONFIGURATION_KEYFACEBOOK_FORM = "KeyFacebook";

	private static final String SUCCES_ACTION_NAME = "success";
	private static final String INTERNATIONALIZATION_RESSOURCE = "FSneti18n";

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
				dynaform.set(CONFIGURATION_ENABLETLS_FORM, "on");
			} else {
				dynaform.set(CONFIGURATION_ENABLETLS_FORM, "");
			}
			if (conf.isSSLEnabled()) {
				dynaform.set(CONFIGURATION_ENABLESSL_FORM, "on");
			} else {
				dynaform.set(CONFIGURATION_ENABLESSL_FORM, "");
			}
			if (conf.isAuthenticationEnabled()) {
				dynaform.set(CONFIGURATION_ENABLEAUTHENTIFICATION_FORM, "on");
				dynaform.set(CONFIGURATION_SMTPUSERNAME_FORM, properties
						.getProperty(FSNetConfiguration.SMTP_USER_KEY));
				dynaform.set(CONFIGURATION_SMTPPASSWORD_FORM, properties
						.getProperty(FSNetConfiguration.SMTP_PASSWORD_KEY));
			} else {
				dynaform.set(CONFIGURATION_ENABLEAUTHENTIFICATION_FORM, "");
				dynaform.set(CONFIGURATION_SMTPUSERNAME_FORM, "");
				dynaform.set(CONFIGURATION_SMTPPASSWORD_FORM, "");
			}
			dynaform.set(CONFIGURATION_SMTPPORT_FORM,
					properties.getProperty(FSNetConfiguration.SMTP_PORT_KEY));
			dynaform.set(CONFIGURATION_SMTPHOST_FORM,
					properties.getProperty(FSNetConfiguration.SMTP_HOST_KEY));
			dynaform.set(CONFIGURATION_FSNETWEBURL_FORM, properties
					.getProperty(FSNetConfiguration.FSNET_WEB_ADDRESS_KEY));

			dynaform.set(CONFIGURATION_PICTURESDIRECTORY_FORM, properties
					.getProperty(FSNetConfiguration.PICTURES_DIRECTORY_KEY));
			dynaform.set(CONFIGURATION_KEYFACEBOOK_FORM,
					properties.getProperty(FSNetConfiguration.KEY_FACEBOOK));

		} catch (Exception ex) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", ex);
		}

		return mapping.findForward(SUCCES_ACTION_NAME);
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
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		
		DynaActionForm dynaform = (DynaActionForm) form; // NOSONAR
		if ("".equals(dynaform.get(CONFIGURATION_ENABLETLS_FORM))) {
			saveProperty(em, FSNetConfiguration.ENABLE_TLS_KEY,
					Boolean.FALSE.toString());
		} else {
			saveProperty(em, FSNetConfiguration.ENABLE_TLS_KEY,
					Boolean.TRUE.toString());
		}
		
		if ("".equals(dynaform.get(CONFIGURATION_ENABLESSL_FORM))) {
			saveProperty(em, FSNetConfiguration.SSL_KEY,
					Boolean.FALSE.toString());
		} else {
			saveProperty(em, FSNetConfiguration.SSL_KEY,
					Boolean.TRUE.toString());
		}
		
		if ("".equals(dynaform.get(CONFIGURATION_ENABLEAUTHENTIFICATION_FORM))) {
			saveProperty(em, FSNetConfiguration.ENABLE_AUTHENTICATION_KEY,
					Boolean.FALSE.toString());
		} else {
			saveProperty(em, FSNetConfiguration.ENABLE_AUTHENTICATION_KEY,
					Boolean.TRUE.toString());
			saveProperty(em, FSNetConfiguration.SMTP_USER_KEY,
					(String) dynaform.get(CONFIGURATION_SMTPUSERNAME_FORM));
			saveProperty(em, FSNetConfiguration.SMTP_PASSWORD_KEY,
					(String) dynaform.get(CONFIGURATION_SMTPPASSWORD_FORM));
		}
		
		saveProperty(em, FSNetConfiguration.MAIL_FROM_KEY,
				(String) dynaform.get("MailFrom"));
		saveProperty(em, FSNetConfiguration.SMTP_HOST_KEY,
				(String) dynaform.get(CONFIGURATION_SMTPHOST_FORM));
		String smtpPort = (String) dynaform.get(CONFIGURATION_SMTPPORT_FORM);
		
		try {
			Integer.parseInt(smtpPort);
		} catch (NumberFormatException e) {
			smtpPort = DEFAULT_SMTP_PORT;
		}
		
		saveProperty(em, FSNetConfiguration.SMTP_PORT_KEY, smtpPort);
		saveProperty(em, FSNetConfiguration.FSNET_WEB_ADDRESS_KEY,
				(String) dynaform.get(CONFIGURATION_FSNETWEBURL_FORM));
		String dirName = (String) dynaform.get(CONFIGURATION_PICTURESDIRECTORY_FORM);
		
		if (isValidDirectory(dirName)) {
			saveProperty(em, FSNetConfiguration.PICTURES_DIRECTORY_KEY, dirName);
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(CONFIGURATION_PICTURESDIRECTORY_FORM, new ActionMessage(
					"configure.error.imgFolder2"));
			saveErrors(request, errors);
		}

		em.getTransaction().commit();
		em.close();
		
		FSNetConfiguration.getInstance().refreshConfiguration();

		MessageResources bundle = MessageResources
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE);
		request.setAttribute(SUCCES_ACTION_NAME, bundle.getMessage(
				request.getLocale(), "configure.mail.update.success"));

		return mapping.findForward(SUCCES_ACTION_NAME);
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
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		
		DynaActionForm dynaform = (DynaActionForm) form; // NOSONAR

		saveProperty(em, FSNetConfiguration.KEY_FACEBOOK,
				(String) dynaform.get(CONFIGURATION_KEYFACEBOOK_FORM));

		em.getTransaction().commit();
		em.close();
		FSNetConfiguration.getInstance().refreshConfiguration();

		MessageResources bundle = MessageResources
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE);
		request.setAttribute(SUCCES_ACTION_NAME, bundle.getMessage(
				request.getLocale(), "configure.facebook.update.success"));

		return mapping.findForward(SUCCES_ACTION_NAME);
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
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE);
		request.setAttribute(SUCCES_ACTION_NAME, bundle.getMessage(
				request.getLocale(), "configure.testMail.sent"));

		return mapping.findForward(SUCCES_ACTION_NAME);
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
			
			try {
				br.close();
			} catch (Exception e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
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
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE);
		request.setAttribute(SUCCES_ACTION_NAME, bundle.getMessage(
				request.getLocale(), "configure.db.update.success"));

		return mapping.findForward(SUCCES_ACTION_NAME);
	}

	public ActionForward updateDateType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		
		try {
			em.getTransaction().begin();

			Query query = em
					.createNativeQuery("ALTER TABLE MEETING MODIFY STARTDATE DATETIME");
			query.executeUpdate();

			query = em
					.createNativeQuery("ALTER TABLE ANNOUNCEMENT MODIFY ENDDATE DATETIME");
			query.executeUpdate();

			em.getTransaction().commit();
			
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		} finally {
			em.close();
		}

		MessageResources bundle = MessageResources
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE);
		request.setAttribute(SUCCES_ACTION_NAME, bundle.getMessage(
				request.getLocale(), "configure.db.update.success"));

		return mapping.findForward(SUCCES_ACTION_NAME);
	}

	public ActionForward dropCVTables(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		
		try {
			em.getTransaction().begin();

			Query query = em
					.createNativeQuery("DROP TABLE IF EXISTS CURRICULUM");
			query.executeUpdate();
			Logger.getAnonymousLogger().log(Level.SEVERE, "",
					"DROP TABLE CURRICULUM");

			query = em
					.createNativeQuery("DROP TABLE IF EXISTS CURRICULUM_HOBBIESCV");
			query.executeUpdate();
			Logger.getAnonymousLogger().log(Level.SEVERE, "",
					"DROP TABLE CURRICULUM_HOBBIESCV");

			query = em.createNativeQuery("DROP TABLE IF EXISTS DEGREECV");
			query.executeUpdate();
			Logger.getAnonymousLogger().log(Level.SEVERE, "",
					"DROP TABLE DEGREECV");

			query = em
					.createNativeQuery("DROP TABLE IF EXISTS ESTABLISHMENTCV");
			query.executeUpdate();
			Logger.getAnonymousLogger().log(Level.SEVERE, "",
					"DROP TABLE ESTABLISHMENTCV");

			query = em.createNativeQuery("DROP TABLE IF EXISTS FORMATIONCV");
			query.executeUpdate();
			Logger.getAnonymousLogger().log(Level.SEVERE, "",
					"DROP TABLE FORMATIONCV");

			query = em.createNativeQuery("DROP TABLE IF EXISTS HOBBIESCV");
			query.executeUpdate();
			Logger.getAnonymousLogger().log(Level.SEVERE, "",
					"DROP TABLE HOBBIESCV");

			query = em.createNativeQuery("DROP TABLE IF EXISTS MEMBERCV");
			query.executeUpdate();
			Logger.getAnonymousLogger().log(Level.SEVERE, "",
					"DROP TABLE MEMBERCV");

			query = em.createNativeQuery("DROP TABLE IF EXISTS TRAININGCV");
			query.executeUpdate();
			Logger.getAnonymousLogger().log(Level.SEVERE, "",
					"DROP TABLE TRAININGCV");

			query = em
					.createNativeQuery("DROP TABLE IF EXISTS ASSOCIATIONDATEDEGREECV");
			query.executeUpdate();
			Logger.getAnonymousLogger().log(Level.SEVERE, "",
					"DROP TABLE ASSOCIATIONDATEDEGREECV");

			query = em
					.createNativeQuery("DROP TABLE IF EXISTS ASSOCIATIONDATEFORMATIONCV");
			query.executeUpdate();
			Logger.getAnonymousLogger().log(Level.SEVERE, "",
					"DROP TABLE ASSOCIATIONDATEFORMATIONCV");

			query = em
					.createNativeQuery("DROP TABLE IF EXISTS ASSOCIATIONDATETRAININGCV");
			query.executeUpdate();
			Logger.getAnonymousLogger().log(Level.SEVERE, "",
					"DROP TABLE ASSOCIATIONDATETRAININGCV");

			em.getTransaction().commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		} finally {
			em.close();
		}

		MessageResources bundle = MessageResources
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE);
		request.setAttribute(SUCCES_ACTION_NAME, bundle.getMessage(
				request.getLocale(), "configure.db.dropcvtables.success"));

		return mapping.findForward(SUCCES_ACTION_NAME);
	}

	public ActionForward addRecalTimeColumnInMeeting(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			em.getTransaction().begin();

			Query query = em
					.createNativeQuery("ALTER TABLE MEETING ADD COLUMN RECALLDATE DATETIME");
			query.executeUpdate();
			Logger.getAnonymousLogger().log(Level.SEVERE, "",
					"COLUMN RECALLDATE ADDED IN MEETING TABLE");

			em.getTransaction().commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		} finally {
			em.close();
		}

		MessageResources bundle = MessageResources
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE);
		request.setAttribute(SUCCES_ACTION_NAME, bundle.getMessage(
				request.getLocale(), "configure.db.addRecalTimeColumn.success"));

		return mapping.findForward(SUCCES_ACTION_NAME);
	}

	public ActionForward addColorColumnInSocialGroup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		
		try {
			em.getTransaction().begin();

			Query query = em
					.createNativeQuery("ALTER TABLE SOCIALGROUP ADD COLUMN COLOR VARCHAR(6) DEFAULT 'c9e6f8'");
			query.executeUpdate();
			
//			Query query2 = em.createQuery("UPDATE SOCIALGROUP SET COLOR = :defaultColor");
//			query.setParameter("defaultColor", "c9e6f8");
//			query2.executeUpdate();
			
			Logger.getAnonymousLogger().log(Level.SEVERE, "",
					"COLUMN COLOR ADDED IN SOCIALGROUP TABLE");

			em.getTransaction().commit();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		} finally {
			em.close();
		}

		MessageResources bundle = MessageResources
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE);
		request.setAttribute(SUCCES_ACTION_NAME, bundle.getMessage(
				request.getLocale(), "configure.db.addRecalTimeColumn.success"));

		return mapping.findForward(SUCCES_ACTION_NAME);
	}

}
