
package fr.univartois.ili.fsnet.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

/**
 * 
 * The class Interet.
 * 
 */

@Entity
public class Interet {

	/**
	 * The identifier.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	/**
	 * The list of social entities that are affected by this interest.
	 */
	@ManyToMany(mappedBy = "lesinterets")
	@JoinColumn(nullable=false)
	private List<EntiteSociale> lesEntites = new ArrayList<EntiteSociale>();

	/**
	 * The interest name.
	 */
	@Column(nullable=false,unique=true)
	private String nomInteret;

	/**
	 * Constructor of the class Interet.
	 */
	public Interet() {
	}

	/**
	 * Constructor of the class Interet.
	 * 
	 * @param lesEntites
	 * @param nomInteret
	 */
	public Interet(List<EntiteSociale> lesEntites, String nomInteret) {
		this.lesEntites = lesEntites;
		this.nomInteret = nomInteret;
	}

	/**
	 * 
	 * @return the identifier.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gives an identifier to the interest.
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return the list of social entities that are affected by this interest.
	 */
	public List<EntiteSociale> getLesEntites() {
		return lesEntites;
	}

	/**
	 * Gives the list of social entities that are affected by this interest.
	 * 
	 * @param lesEntites
	 */
	public void setLesEntites(List<EntiteSociale> lesEntites) {
		this.lesEntites = lesEntites;
	}

	/**
	 * 
	 * @return the interest name.
	 */
	public String getNomInteret() {
		return nomInteret;
	}

	/**
	 * Gives a name to the interest.
	 * 
	 * @param nomInteret
	 */
	public void setNomInteret(String nomInteret) {
		this.nomInteret = nomInteret;
	}

}