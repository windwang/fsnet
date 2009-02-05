package fr.univartois.ili.fsnet.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Manifestation extends Information{

	@Temporal(TemporalType.DATE)
	private Date dateManifestation;

	
	
	
	public Manifestation() {
	}

	public Manifestation(Date dateDebut) {
		super();
		this.dateManifestation = dateDebut;
	}

	public Date getDateDebut() {
		return dateManifestation;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateManifestation = dateDebut;
	}
	
	
}
