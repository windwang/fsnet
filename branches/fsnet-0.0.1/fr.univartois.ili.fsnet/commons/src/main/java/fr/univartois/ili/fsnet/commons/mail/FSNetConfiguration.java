package fr.univartois.ili.fsnet.commons.mail;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.univartois.ili.fsnet.entities.Property;

public class FSNetConfiguration {

	public static final String MAIL_FROM_KEY = "fsnet.mail.from";
	public static final String ENABLE_TLS_KEY = "mail.smtp.starttls.enable";
	public static final String SMTP_HOST_KEY = "mail.smtp.host";
	public static final String SMTP_USER_KEY = "mail.smtp.user";
	public static final String SMTP_PASSWORD_KEY = "mail.smtp.password";
	public static final String FSNET_WEB_ADDRESS_KEY = "fsnet.web.address";
	public static final String ENABLE_AUTHENTICATION_KEY = "mail.smtp.auth";
	public static final String SMTP_PORT_KEY = "mail.smtp.port";
	public static final String SSL_KEY = "mail.smtp.socketFactory.class";
	public static final String SSL_ENABLED_VALUE = "javax.net.ssl.SSLSocketFactory";

	private static final FSNetConfiguration instance;

	public static final FSNetConfiguration getInstance() {
		return instance;
	}

	private Properties properties = new Properties();

	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");

	static {
		instance = new FSNetConfiguration();
	}

	private FSNetConfiguration() {
		refreshConfiguration();
	}

	public Properties getFSNetConfiguration() {
		return properties;
	}

	public final void refreshConfiguration() {
		properties.clear();
		EntityManager em = factory.createEntityManager();
		Property property;
		property = em.find(Property.class,
				ENABLE_AUTHENTICATION_KEY);
		if (property != null) {
			properties.put(ENABLE_AUTHENTICATION_KEY, property.getValue());
			property = em
					.find(Property.class, SMTP_USER_KEY);
			if (property == null) {
				throw new IllegalStateException();
			}
			properties.put(SMTP_USER_KEY, property.getValue());
			property = em.find(Property.class,
					SMTP_PASSWORD_KEY);
			if (property == null) {
				throw new IllegalStateException();
			}
			properties.put(SMTP_PASSWORD_KEY, property.getValue());
		}
		property = em.find(Property.class, MAIL_FROM_KEY);
		if (property != null) {
			properties.put(MAIL_FROM_KEY, property.getValue());
		}
		property = em.find(Property.class, SMTP_HOST_KEY);
		if (property != null) {
			properties.put(SMTP_HOST_KEY, property.getValue());
		}
		property = em.find(Property.class, SSL_KEY);
		if (property != null) {
			if (Boolean.valueOf(property.getValue())) {
				properties.put(SSL_KEY, SSL_ENABLED_VALUE);
			}
		}
		property = em.find(Property.class, ENABLE_TLS_KEY);
		if (property != null) {
			properties.put(ENABLE_TLS_KEY, property.getValue());
		}
		property = em.find(Property.class, FSNET_WEB_ADDRESS_KEY);
		if (property != null) {
			properties.put(FSNET_WEB_ADDRESS_KEY, property.getValue());
		}
		property = em.find(Property.class, SMTP_PORT_KEY);
		if (property != null) {
			properties.put(SMTP_PORT_KEY, property.getValue());
		}
		em.close();
	}

	public boolean isSSLEnabled() {
		return SSL_ENABLED_VALUE.equals(properties.getProperty(SSL_KEY));
	}

	public boolean isTLSEnabled() {
		return "true".equalsIgnoreCase(properties.getProperty(ENABLE_TLS_KEY));
	}

	public boolean isAuthenticationEnabled() {
		return "true".equalsIgnoreCase(properties
				.getProperty(ENABLE_AUTHENTICATION_KEY));
	}

}
