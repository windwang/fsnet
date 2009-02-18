package fr.univartois.ili.fsnet.entities;

import java.util.Date;

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
	private Date dateFinAnnnonce;

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
	public Annonce(Date dateFinAnnnonce) {
		super();
		this.dateFinAnnnonce = dateFinAnnnonce;
	}

	/**
	 * 
	 * @return The date of the end's ad .
	 */
	public Date getDateFinAnnnonce() {
		return dateFinAnnnonce;
	}

	/**
	 * Gives an end date for the ad.
	 * 
	 * @param dateFinAnnnonce
	 *            .
	 */
	public void setDateFinAnnnonce(Date dateFinAnnnonce) {
		this.dateFinAnnnonce = dateFinAnnnonce;
	}

}
