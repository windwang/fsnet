package fr.univartois.ili.fsnet.entities;

import javax.persistence.Entity;

@Entity
public class Manifestation extends Information{

	private String dateManifestation;

	
	public Manifestation(String dateDebut) {
		super();
		this.dateManifestation = dateDebut;
	}

	public String getDateDebut() {
		return dateManifestation;
	}

	public void setDateDebut(String dateDebut) {
		this.dateManifestation = dateDebut;
	}
	
	
}
