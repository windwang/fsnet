package fr.univartois.ili.fsnet.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Annonce extends Information {
	
	@Temporal(TemporalType.DATE)
	private Date dateFinAnnnonce;


	public Annonce() {
	}

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
