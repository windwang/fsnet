package fr.univartois.ili.fsnet.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * The class Hub.
 * 
 */

@Entity
public class Hub extends Communaute {

	/**
	 * The date of creation of the hub.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreation;

	/**
	 * The list of topics of a hub.
	 */
	@OneToMany(mappedBy = "hub", cascade = { CascadeType.MERGE,
			CascadeType.PERSIST })
	private List<Topic> lesTopics;

	/**
	 * Constructor of the class Hub.
	 */
	public Hub() {
	}

	/**
	 * Constructor of the class Hub.
	 * 
	 * @param nomCommunaute
	 * @param dateCreation
	 * @param lesTopics
	 */
	public Hub(String nomCommunaute, Date dateCreation) {
		super(nomCommunaute);
		this.dateCreation = dateCreation;
		lesTopics = new ArrayList<Topic>();
	}

	/**
	 * 
	 * @return the date of creation of the hub.
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * Gives a date of creation to the hub.
	 * 
	 * @param dateCreation
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * 
	 * @return the list of topics of a hub.
	 */
	public List<Topic> getLesTopics() {
		return lesTopics;
	}

	/**
	 * Gives a list of topics to a hub.
	 * 
	 * @param lesTopics
	 */
	public void setLesTopics(List<Topic> lesTopics) {
		this.lesTopics = lesTopics;
	}

}
