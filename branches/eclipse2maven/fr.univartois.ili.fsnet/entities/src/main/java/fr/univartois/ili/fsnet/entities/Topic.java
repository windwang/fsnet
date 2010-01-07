package fr.univartois.ili.fsnet.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * The class Topic.
 * 
 */

@Entity
public class Topic {

	/**
	 * The identifier.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	/**
	 * The subject of the topic.
	 */
	private String sujet;

	/**
	 * The date of the topic.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSujet;

	/**
	 * The hub in which the topic appears.
	 */
	@ManyToOne
	private Hub hub;

	/**
	 * The creator of the topic.
	 */
	@ManyToOne
	private EntiteSociale propTopic;

	/**
	 * he list of messages that the topic contains.
	 */
	@OneToMany(mappedBy = "topic", cascade = { CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH })
	private List<Message> lesMessages;

	/**
	 * Constructor of the class Topic.
	 */
	public Topic() {
	}

	/**
	 * Constructor of the class Topic.
	 * 
	 * @param sujet
	 * @param dateSujet
	 * @param lesMessages
	 * @param hub
	 * @param propTopic
	 */
	public Topic(String sujet, Date dateSujet, List<Message> lesMessages,
			Hub hub, EntiteSociale propTopic) {
		this.sujet = sujet;
		this.dateSujet = dateSujet;
		this.lesMessages = lesMessages;
		this.hub = hub;
		this.propTopic = propTopic;
	}

	/**
	 * 
	 * @return the identifier.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gives an identifier to the topic.
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return thesubject of the topic.
	 */
	public String getSujet() {
		return sujet;
	}

	/**
	 * Gives a subject to the topic.
	 * 
	 * @param sujet
	 */
	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	/**
	 * 
	 * @return the date of the topic.
	 */
	public Date getDateSujet() {
		return dateSujet;
	}

	/**
	 * Gives a date to the topic.
	 * 
	 * @param dateSujet
	 */
	public void setDateSujet(Date dateSujet) {
		this.dateSujet = dateSujet;
	}

	/**
	 * 
	 * @return the list of messages that the topic contains.
	 */
	public List<Message> getLesMessages() {
		return lesMessages;
	}

	/**
	 * Gives a list of messages to the topic.
	 * 
	 * @param lesMessages
	 */
	public void setLesMessages(List<Message> lesMessages) {
		this.lesMessages = lesMessages;
	}

	/**
	 * 
	 * @return the hub in which the topic appears.
	 */
	public Hub getHub() {
		return hub;
	}

	/**
	 * Gives a hub to the topic.
	 * 
	 * @param hub
	 */
	public void setHub(Hub hub) {
		this.hub = hub;
	}

	/**
	 * 
	 * @return the creator of the topic.
	 */
	public EntiteSociale getPropTopic() {
		return propTopic;
	}

	/**
	 * Gives the creator of the topic.
	 * 
	 * @param propTopic
	 */
	public void setPropTopic(EntiteSociale propTopic) {
		this.propTopic = propTopic;
	}

}
