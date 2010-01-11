/**
 * 
 */
package fr.univartois.ili.fsnet.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author romuald druelle
 * 
 */
@Entity
public class Inscription {

	/**
	 * The state "awaiting registration".
	 */
	public static final String ATTENTE = "En attente d'inscription";

	/**
	 * The state "registered".
	 */
	public static final String INSCRIT = "Inscrit";

	/**
	 * The identifier.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	/*
	 * * The social entity.
	 */
	private EntiteSociale entite;

	/**
	 * The state.
	 */
	private String etat;

	/**
	 * Constructor of the class Inscription.
	 */
	public Inscription() {
		etat = ATTENTE;
	}

	/**
	 * Constructor of the class Inscription.
	 * 
	 * @param entite
	 *            .
	 */
	public Inscription(EntiteSociale entite) {
		this.entite = entite;
		etat = ATTENTE;
	}

	/**
	 * 
	 * @return the identifier.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gives an identifier to the social entity.
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return the entity social state
	 */
	public String getEtat() {
		return etat;
	}

	/**
	 * 
	 * @return the social entity concerned by the registration.
	 */
	public EntiteSociale getEntite() {
		return entite;
	}

	/**
	 * Change the entity sociale state.
	 */
	public void setEtat() {
		etat = INSCRIT;
	}

	/**
	 * Provides a social entity for registration.
	 * 
	 * @param entite
	 */
	public void setEntite(EntiteSociale entite) {
		this.entite = entite;
	}

}
