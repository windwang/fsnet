package fr.univartois.ili.fsnet.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Information extends Interaction {

	private String nom;
	private String dateInformation;
	private String contenu;
	@OneToOne(mappedBy="groupeDinteret")
	private GroupeDInteret cegroupe;

	public Information() {

		super();
	}

	public Information(String nom, String dateInformation, String contenu,
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

	public String getDateInformation() {
		return dateInformation;
	}

	public void setDateInformation(String dateInformation) {
		this.dateInformation = dateInformation;
	}

	public GroupeDInteret getCegroupe() {
		return cegroupe;
	}

	public void setCegroupe(GroupeDInteret cegroupe) {
		this.cegroupe = cegroupe;
	}

}
