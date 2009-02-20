package fr.univartois.ili.fsnet.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * The class Annonce.
 * 
 */

@Entity
public class Annonce extends Information {

	/**
	 * The date of the end's ad.
	 */
	@Temporal(TemporalType.DATE)
	private Date dateFinAnnonce;

	/**
	 * Constructor of the class Annonce.
	 */
	public Annonce() {
	}

	/**
	 * Constructor of the class Annonce.
	 * 
	 * @param dateFinAnnnonce
	 *            .
	 */
	public Annonce(Date dateFinAnnonce) {
		super();
		this.dateFinAnnonce = dateFinAnnonce;

	}

	public Annonce(String nom, Date datePublication, String contenu,
			Date dateFinAnnonce) {
		super(nom, datePublication, contenu);
		this.dateFinAnnonce = dateFinAnnonce;
	}

	/**
	 * 
	 * @return The date of the end's ad .
	 */
	public Date getDateFinAnnonce() {
		return dateFinAnnonce;
	}

	/**
	 * 
	 * @return The date of the end's ad in the format jj/mm/aaaa.
	 */
	public String getDateAnnonce() {
		String date = "";
		if (dateFinAnnonce != null) {
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(dateFinAnnonce);
			int jour = calendar.get(GregorianCalendar.DAY_OF_MONTH);
			int mois = calendar.get(GregorianCalendar.MONTH)+1;
			int année = calendar.get(GregorianCalendar.YEAR);
			date = jour + "/" + mois + "/" + année;
		}
		return date;

	}

	/**
	 * Gives an end date for the ad.
	 * 
	 * @param dateFinAnnnonce
	 *            .
	 */
	public void setDateFinAnnnonce(Date dateFinAnnonce) {
		this.dateFinAnnonce = dateFinAnnonce;
	}

}
