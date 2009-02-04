package fr.univartois.ili.fsnet.entities;

import javax.persistence.Entity;

@Entity
public class Annonce extends Information {

	private String dateFinAnnnonce;

	public Annonce(String dateFinAnnnonce) {
		super();
		this.dateFinAnnnonce = dateFinAnnnonce;
	}

	public String getDateFinAnnnonce() {
		return dateFinAnnnonce;
	}

	public void setDateFinAnnnonce(String dateFinAnnnonce) {
		this.dateFinAnnnonce = dateFinAnnnonce;
	}
	
	
}
