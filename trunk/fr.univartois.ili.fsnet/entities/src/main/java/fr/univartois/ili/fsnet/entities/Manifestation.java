package fr.univartois.ili.fsnet.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

	public Manifestation(String titre, Date date, String contenu,
			String visible, EntiteSociale createur) {
		super(titre, date, contenu, visible, createur);
		this.dateManifestation = date;
	}

	/**
	 * 
	 * @return the date of the manifestation.
	 */
	public Date getdateManifestation() {
		return dateManifestation;
	}

	/**
	 * Gives a date to the manifestation.
	 * 
	 * @param dateDebut
	 */
	public void setdateManifestation(Date dateDebut) {
		this.dateManifestation = dateDebut;
	}

	/**
	 * 
	 * @return The date in the format jj/mm/aaaa.
	 */
	public String getDateManif() {
		String date = "";
		if (dateManifestation != null) {
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(dateManifestation);
			int jour = calendar.get(GregorianCalendar.DAY_OF_MONTH);
			int mois = calendar.get(GregorianCalendar.MONTH) + 1;
			int année = calendar.get(GregorianCalendar.YEAR);
			date = jour + "/" + mois + "/" + année;
		}
		return date;

	}
}
