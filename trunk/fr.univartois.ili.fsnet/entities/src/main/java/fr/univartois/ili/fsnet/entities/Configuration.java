package fr.univartois.ili.fsnet.entities;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Configuration {
	
	public static enum FsnetProperty {
		SMTP_PORT, SMPT_HOST;
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
