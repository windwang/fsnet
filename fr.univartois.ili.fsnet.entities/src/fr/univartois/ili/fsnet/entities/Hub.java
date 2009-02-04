package fr.univartois.ili.fsnet.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Hub extends Communaute {

	private String dateCreation;
	@OneToMany(mappedBy="topic")
	private List<Topic> lesTopics;
	private EntiteSociale qui;
	
	public Hub(String nomCommunaute, String dateCreation, List<Topic> lesTopics, EntiteSociale qui) {
		super(nomCommunaute);
		this.dateCreation = dateCreation;
		this.lesTopics = lesTopics;
		this.qui = qui;
	}

	public String getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	public List<Topic> getLesTopics() {
		return lesTopics;
	}

	public void setLesTopics(List<Topic> lesTopics) {
		this.lesTopics = lesTopics;
	}

	public EntiteSociale getQui() {
		return qui;
	}

	public void setQui(EntiteSociale qui) {
		this.qui = qui;
	}

}
