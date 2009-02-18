package fr.univartois.ili.fsnet.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * The class Information.
 * 
 */

@Entity
public class Information extends Interaction {

	/**
	 * The information name.
	 */
	private String nom;

	/**
	 * The date of the information.
	 */
	@Temporal(TemporalType.DATE)
	private Date dateInformation;

	/**
	 * The content of the information.
	 */
	private String contenu;

	/**
	 * Constructor of the class Information.
	 */
	public Information() {

		super();
	}

	/**
	 * Constructor of the class Information.
	 * 
	 * @param nom
	 * @param dateInformation
	 * @param contenu
	 */
	public Information(String nom, Date dateInformation, String contenu) {
		this.nom = nom;
		this.dateInformation = dateInformation;
		this.contenu = contenu;
	}

	/**
	 * 
	 * @return the information name.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Gives a name to the information.
	 * 
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * 
	 * @return the content of the information.
	 */
	public String getContenu() {
		return contenu;
	}

	/**
	 * Gives a content to the information.
	 * 
	 * @param contenu
	 */
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	/**
	 * 
	 * @return the date of the information.
	 */
	public Date getDateInformation() {
		return dateInformation;
	}

	/**
	 * Gives a date to the information.
	 * 
	 * @param dateInformation
	 */
	public void setDateInformation(Date dateInformation) {
		this.dateInformation = dateInformation;
	}

}
