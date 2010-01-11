package fr.univartois.ili.fsnet.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * 
 * The class Interaction.
 * 
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Interaction {

	/**
	 * The identifier.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	/**
	 * 
	 */
	private boolean valide;

	/**
	 * The decision maker that governs the interaction.
	 */
	@OneToOne
	private Decideur decideur;

	/**
	 * The creator of the interaction.
	 */
	@ManyToOne
	private EntiteSociale createur;

	/**
	 * Report of activities, which included all interactions.
	 */
	@ManyToOne
	private RapportActivites rapport;

	/**
	 * Constructor of the class Interaction.
	 */
	public Interaction() {

	}

	/**
	 * Constructor of the class Interaction.
	 * 
	 * @param valide
	 * @param decideur
	 * @param createur
	 * @param rapport
	 */
	public Interaction(boolean valide, Decideur decideur,
			EntiteSociale createur, RapportActivites rapport) {
		this.valide = valide;
		this.decideur = decideur;
		this.createur = createur;
		this.rapport = rapport;
	}

	/**
	 * 
	 * @return the identifier.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gives an identifier to the interaction.
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return a boolean stating whether the interaction is valid or not.
	 */
	public boolean isValide() {
		return valide;
	}

	/**
	 * 
	 * @param valide
	 */
	public void setValide(boolean valide) {
		this.valide = valide;
	}

	/**
	 * 
	 * @return the decision maker that governs the interaction.
	 */
	public Decideur getDecideur() {
		return decideur;
	}

	/**
	 * Gives a decison maker to the interaction.
	 * 
	 * @param decideur
	 */
	public void setDecideur(Decideur decideur) {
		this.decideur = decideur;
	}

	/**
	 * 
	 * @return the creator of the interaction.
	 */
	public EntiteSociale getCreateur() {
		return createur;
	}

	/**
	 * Gives the creator of the interaction.
	 * 
	 * @param createur
	 */
	public void setCreateur(EntiteSociale createur) {
		this.createur = createur;
	}

	/**
	 * 
	 * @return the report of activities.
	 */
	public RapportActivites getRapport() {
		return rapport;
	}

	/**
	 * Gives a report of activities to the interaction.
	 * 
	 * @param rapport
	 */
	public void setRapport(RapportActivites rapport) {
		this.rapport = rapport;
	}

}
