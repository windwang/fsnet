package fr.univartois.ili.fsnet.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Topic {

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private String sujet;
	private String dateSujet;
	
	@OneToMany(mappedBy="message")
	private List<Message> lesMessages;

	public Topic(String sujet, String dateSujet, List<Message> lesMessages) {
		this.sujet = sujet;
		this.dateSujet = dateSujet;
		this.lesMessages = lesMessages;
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

	public String getDateSujet() {
		return dateSujet;
	}

	public void setDateSujet(String dateSujet) {
		this.dateSujet = dateSujet;
	}

	public List<Message> getLesMessages() {
		return lesMessages;
	}

	public void setLesMessages(List<Message> lesMessages) {
		this.lesMessages = lesMessages;
	}

}
