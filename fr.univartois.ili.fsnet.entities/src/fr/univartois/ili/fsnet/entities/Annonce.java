package fr.univartois.ili.fsnet.entities;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Annonce extends Information {

	private Date dateFinAnnnonce;

	public Annonce(Date dateFinAnnnonce) {
		super();
		this.dateFinAnnnonce = dateFinAnnnonce;
	}

	public Date getDateFinAnnnonce() {
		return dateFinAnnnonce;
	}

	public void setDateFinAnnnonce(Date dateFinAnnnonce) {
		this.dateFinAnnnonce = dateFinAnnnonce;
	}
	
	
}
