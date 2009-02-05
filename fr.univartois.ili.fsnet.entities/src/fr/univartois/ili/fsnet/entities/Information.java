package fr.univartois.ili.fsnet.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Information extends Interaction {

	private String nom;
	@Temporal(TemporalType.DATE)
	private Date dateInformation;
	private String contenu;
	@OneToOne
	private GroupeDInteret cegroupe;


	
	public Information() {

		super();
	}

	public Information(String nom, Date dateInformation, String contenu,
			GroupeDInteret cegroupe) {
		this.nom = nom;
		this.dateInformation = dateInformation;
		this.contenu = contenu;
		this.cegroupe = cegroupe;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public Date getDateInformation() {
		return dateInformation;
	}

	public void setDateInformation(Date dateInformation) {
		this.dateInformation = dateInformation;
	}

	public GroupeDInteret getCegroupe() {
		return cegroupe;
	}

	public void setCegroupe(GroupeDInteret cegroupe) {
		this.cegroupe = cegroupe;
	}

}
