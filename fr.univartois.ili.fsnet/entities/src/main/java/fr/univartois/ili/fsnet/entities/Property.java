package fr.univartois.ili.fsnet.entities;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Property {
	
	public Property(){
		
	}
	
	public static enum FsnetProperty {
		SMTP_PORT, SMTP_HOST, SMTP_USER, SMTP_PASSWORD, ENABLE_TLS, ENABLE_SSL, ENABLE_AUTHENTICATION, MAIL_FROM, URL;
	}
	
	@Id
	@Enumerated
	private FsnetProperty key;
	
	private String value;

	public FsnetProperty getKey() {
		return key;
	}

	public void setKey(FsnetProperty key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
