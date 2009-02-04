package fr.univartois.ili.fsnet.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class RapportActivites {

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	@OneToMany(mappedBy="activites")
	private List<Interaction> lesInteractions;

	
	
	public RapportActivites(List<Interaction> lesInteractions) {
		this.lesInteractions = lesInteractions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Interaction> getLesInteractions() {
		return lesInteractions;
	}

	public void setLesInteractions(List<Interaction> lesInteractions) {
		this.lesInteractions = lesInteractions;
	}

}
