package fr.univartois.ili.fsnet.entities;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Manifestation extends Information{

	private Date dateManifestation;

	
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
