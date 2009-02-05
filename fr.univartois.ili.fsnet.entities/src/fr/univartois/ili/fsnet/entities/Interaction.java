package fr.univartois.ili.fsnet.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Interaction {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private boolean valide;
	@OneToOne
	private Decideur decideur;
	@ManyToOne
	private EntiteSociale createur;
	@ManyToOne
	private RapportActivites rapport;
	
	
	
	public Interaction() {
		
	}
	public Interaction(boolean valide, Decideur decideur,
			EntiteSociale createur, RapportActivites rapport) {
		this.valide = valide;
		this.decideur = decideur;
		this.createur = createur;
		this.rapport = rapport;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isValide() {
		return valide;
	}
	public void setValide(boolean valide) {
		this.valide = valide;
	}
	public Decideur getDecideur() {
		return decideur;
	}
	public void setDecideur(Decideur decideur) {
		this.decideur = decideur;
	}
	public EntiteSociale getCreateur() {
		return createur;
	}
	public void setCreateur(EntiteSociale createur) {
		this.createur = createur;
	}
	public RapportActivites getRapport() {
		return rapport;
	}
	public void setRapport(RapportActivites rapport) {
		this.rapport = rapport;
	}
	
	

}
