package fr.univartois.ili.fsnet.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class RapportActivites {

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	@OneToMany(mappedBy="rapport")
	private List<Interaction> lesInteractions;

	@Temporal(TemporalType.DATE)
	private Date dateRapport;
	
	
	
	public RapportActivites() {
	}

	public RapportActivites(List<Interaction> lesInteractions, Date dateRapport) {
		this.lesInteractions = lesInteractions;
		this.dateRapport = dateRapport;
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

	public Date getDateRapport() {
		return dateRapport;
	}

	public void setDateRapport(Date dateRapport) {
		this.dateRapport = dateRapport;
	}

}
