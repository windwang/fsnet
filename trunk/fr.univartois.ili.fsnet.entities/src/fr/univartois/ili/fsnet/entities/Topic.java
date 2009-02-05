package fr.univartois.ili.fsnet.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Topic {

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private String sujet;
	@Temporal(TemporalType.DATE)
	private Date dateSujet;
	@ManyToOne
	private Hub hub;
	
	@ManyToOne
	private EntiteSociale propTopic;
	
	@OneToMany(mappedBy="topic")
	private List<Message> lesMessages;

	
	public Topic() {
	}

	public Topic(String sujet, Date dateSujet, List<Message> lesMessages, Hub hub, EntiteSociale propTopic) {
		this.sujet = sujet;
		this.dateSujet = dateSujet;
		this.lesMessages = lesMessages;
		this.hub = hub;
		this.propTopic = propTopic;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public Date getDateSujet() {
		return dateSujet;
	}

	public void setDateSujet(Date dateSujet) {
		this.dateSujet = dateSujet;
	}

	public List<Message> getLesMessages() {
		return lesMessages;
	}

	public void setLesMessages(List<Message> lesMessages) {
		this.lesMessages = lesMessages;
	}

	public Hub getHub() {
		return hub;
	}

	public void setHub(Hub hub) {
		this.hub = hub;
	}

	public EntiteSociale getPropTopic() {
		return propTopic;
	}

	public void setPropTopic(EntiteSociale propTopic) {
		this.propTopic = propTopic;
	}

}
