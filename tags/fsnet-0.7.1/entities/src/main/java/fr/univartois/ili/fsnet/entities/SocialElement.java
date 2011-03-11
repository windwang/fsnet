package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

/**
 * 
 * The class SocialElement this class is used by SocialGroup and SocialEntity
 * 
 * @author JFlamen
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SocialElement implements Serializable {

	/**
	 * The identifier.
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	/* Group SocialElement */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH })
	private SocialGroup group;

	/**
	 * Constructor of the class SocialElement
	 */
	public SocialElement() {

	}

	public SocialElement(SocialGroup group) {
		if (group == null) {
			throw new IllegalArgumentException();
		}
		this.group = group;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SocialGroup getGroup() {
		return group;
	}

	public void setGroup(SocialGroup group) {
		this.group = group;
	}

}
