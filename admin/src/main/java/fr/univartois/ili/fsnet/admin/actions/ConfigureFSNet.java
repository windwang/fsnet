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

	private static final String SUCCES_ATTRIBUTE_NAME = "success";
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
			Logger.getAnonymousLogger().log(Level.SEVERE, "", ex);
		}

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
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
		String smtpPort = (String) dynaform.get("SMTPPort");
		try {
			Integer.parseInt(smtpPort);
		} catch (NumberFormatException e) {
			smtpPort = DEFAULT_SMTP_PORT;
		}
		saveProperty(em, FSNetConfiguration.SMTP_PORT_KEY, smtpPort);
		saveProperty(em, FSNetConfiguration.FSNET_WEB_ADDRESS_KEY,
				(String) dynaform.get("FSNetWebURL"));
		String dirName = (String) dynaform.get("PicturesDirectory");
		if (isValidDirectory(dirName)) {
			saveProperty(em, FSNetConfiguration.PICTURES_DIRECTORY_KEY, dirName);
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add("PicturesDirectory", new ActionMessage(
					"configure.error.imgFolder2"));
			saveErrors(request, errors);
		}

		em.getTransaction().commit();
		em.close();
		FSNetConfiguration.getInstance().refreshConfiguration();

		MessageResources bundle = MessageResources
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE);
		request.setAttribute(SUCCES_ATTRIBUTE_NAME, bundle.getMessage(
				request.getLocale(), "configure.mail.update.success"));

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
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
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE);
		request.setAttribute(SUCCES_ATTRIBUTE_NAME, bundle.getMessage(
				request.getLocale(), "configure.facebook.update.success"));

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
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
		request.setAttribute(SUCCES_ATTRIBUTE_NAME, bundle.getMessage(
				request.getLocale(), "configure.testMail.sent"));

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
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
		request.setAttribute(SUCCES_ATTRIBUTE_NAME, bundle.getMessage(
				request.getLocale(), "configure.db.update.success"));

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
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
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}

		MessageResources bundle = MessageResources
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE);
		request.setAttribute(SUCCES_ATTRIBUTE_NAME, bundle.getMessage(
				request.getLocale(), "configure.db.update.success"));

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	public ActionForward dropCVTables(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		try {
			EntityManager em = PersistenceProvider.createEntityManager();
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
			em.close();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}

		MessageResources bundle = MessageResources
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE);
		request.setAttribute(SUCCES_ATTRIBUTE_NAME, bundle.getMessage(
				request.getLocale(), "configure.db.dropcvtables.success"));

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	public ActionForward addRecalTimeColumnInMeeting(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		try {
			EntityManager em = PersistenceProvider.createEntityManager();
			em.getTransaction().begin();

			Query query = em
					.createNativeQuery("ALTER TABLE MEETING ADD COLUMN RECALLDATE DATETIME");
			query.executeUpdate();
			Logger.getAnonymousLogger().log(Level.SEVERE, "",
					"COLUMN RECALLDATE ADDED IN MEETING TABLE");

			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}

		MessageResources bundle = MessageResources
				.getMessageResources(INTERNATIONALIZATION_RESSOURCE);
		request.setAttribute(SUCCES_ATTRIBUTE_NAME, bundle.getMessage(
				request.getLocale(), "configure.db.addRecalTimeColumn.success"));

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	public ActionForward createInteractionGroupTable(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		try {
			EntityManager em = PersistenceProvider.createEntityManager();
			em.getTransaction().begin();

			Query query = em
					.createNativeQuery("CREATE TABLE IF NOT EXISTS INTERACTIONGROUPS ("
							+ "ID int(11) NOT NULL AUTO_INCREMENT,"
							+ "GROUP_ID int(11) NOT NULL,"
							+ "INTERACTION_ID int(11) NOT NULL,"
							+ "PRIMARY KEY (ID,GROUP_ID,INTERACTION_ID),"
							+ "KEY FK_INTERACTIONGROUPS_GROUP_ID (GROUP_ID),"
							+ "KEY FK_INTERACTIONGROUPS_INTERACTION_ID (INTERACTION_ID)"
							+ ") ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18;");
			query. executeUpdate();

			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	public ActionForward createInteractionGroupDataWithOldRecord(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		try {
			EntityManager em = PersistenceProvider.createEntityManager();
			em.getTransaction().begin();

			Query query = em
					.createNativeQuery("SELECT sg.ID AS GROUP_ID, i.ID AS INTERACTION_ID"
							+ "FROM ((SOCIALGROUP AS sg JOIN SOCIALENTITY AS se ON sg.MASTERGROUP_ID = se.ID) "
							+ "JOIN INTERACTION AS i ON se.ID = i.CREATOR_ID)"
							+ "WHERE i.DTYPE = \"Consultation\";");
			query.executeUpdate();			
			
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}

		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}
}
