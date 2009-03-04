package fr.univartois.ili.fsnet.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * The class Manifestation.
 * 
 */

@Entity
public class Manifestation extends Information {

	/**
	 * The date of the manifestation.
	 */
	@Temporal(TemporalType.DATE)
	private Date dateManifestation;

	/**
	 * Constructor of the class Manifestation.
	 */
	public Manifestation() {
	}

	/**
	 * Constructor of the class Manifestation.
	 * 
	 * @param dateDebut
	 */
	public Manifestation(Date dateDebut) {
		super();
		this.dateManifestation = dateDebut;
	}

	public Manifestation(String titre, Date date, String contenu) {
		super(titre,date,contenu);
	}

	/**
	 * 
	 * @return the date of the manifestation.
	 */
	public Date getDateDebut() {
		return dateManifestation;
	}

	/**
	 * Gives a date to the manifestation.
	 * 
	 * @param dateDebut
	 */
	public void setDateDebut(Date dateDebut) {
		this.dateManifestation = dateDebut;
	}

}
