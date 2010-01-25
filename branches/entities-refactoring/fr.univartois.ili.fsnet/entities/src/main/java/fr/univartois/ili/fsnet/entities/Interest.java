
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
 * The class Interest.
 * 
 */

@Entity
public class Interest {

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
	private List<SocialEntity> lesEntites = new ArrayList<SocialEntity>();

	/**
	 * The interest name.
	 */
	@Column(nullable=false,unique=true)
	private String nomInteret;

	/**
	 * Constructor of the class Interest.
	 */
	public Interest() {
	}

	/**
	 * Constructor of the class Interest.
	 * 
	 * @param lesEntites
	 * @param nomInteret
	 */
	public Interest(List<SocialEntity> lesEntites, String nomInteret) {
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
	public List<SocialEntity> getLesEntites() {
		return lesEntites;
	}

	/**
	 * Gives the list of social entities that are affected by this interest.
	 * 
	 * @param lesEntites
	 */
	public void setLesEntites(List<SocialEntity> lesEntites) {
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