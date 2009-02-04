package fr.univartois.ili.fsnet.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class EntiteSociale {
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private String nom;
	private String prenom;
	private String adresse;
	private String dateEntree;
	private String dateNaissance;
	private String mdp;
	private String photo;
	private String profession;
	private String mail;
	private String numTel;
	@OneToMany(mappedBy="interations")
	private List<Interaction> lesinteractions;
	@ManyToMany(mappedBy="interet")
	private List<Interet> lesinterets;
	@OneToMany(mappedBy="roles")
	private List<Decideur> lesDecideurs;

	public EntiteSociale(String nom, String prenom, String mail) {
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
	}

	public EntiteSociale(String nom, String prenom, String adresse,
			String dateEntree, String dateNaissance, String mdp, String photo,
			String profession, String mail, String numTel,
			List<Interaction> lesinteractions, List<Interet> lesinterets,
			List<Decideur> lesDecideurs) {
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.dateEntree = dateEntree;
		this.dateNaissance = dateNaissance;
		this.mdp = mdp;
		this.photo = photo;
		this.profession = profession;
		this.mail = mail;
		this.numTel = numTel;
		this.lesinteractions = lesinteractions;
		this.lesinterets = lesinterets;
		this.lesDecideurs = lesDecideurs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getDateEntree() {
		return dateEntree;
	}

	public void setDateEntree(String dateEntree) {
		this.dateEntree = dateEntree;
	}

	public String getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNumTel() {
		return numTel;
	}

	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	public List<Interaction> getLesinteractions() {
		return lesinteractions;
	}

	public void setLesinteractions(List<Interaction> lesinteractions) {
		this.lesinteractions = lesinteractions;
	}

	public List<Interet> getLesinterets() {
		return lesinterets;
	}

	public void setLesinterets(List<Interet> lesinterets) {
		this.lesinterets = lesinterets;
	}

	public List<Decideur> getLesDecideurs() {
		return lesDecideurs;
	}

	public void setLesDecideurs(List<Decideur> lesDecideurs) {
		this.lesDecideurs = lesDecideurs;
	}

}
