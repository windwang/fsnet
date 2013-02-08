package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.PostRemove;

/**
 * 
 * The class Interest.
 * 
 */
@Entity
public class Interest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The identifier.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * The list of social entities that are affected by this interest.
	 */
	@ManyToMany(mappedBy = "interests")
	@JoinColumn(nullable = false)
	private Set<SocialEntity> entities = new HashSet<SocialEntity>();

	@OneToMany(mappedBy = "parentInterest")
	private Set<Interest> childrenInterests;

	@ManyToOne
	private Interest parentInterest;

	/**
	 * The interest name.
	 */
	@Column(nullable = false, unique = true)
	@OrderColumn
	private String name;

	/**
	 * Constructor of the class Interest.
	 */
	public Interest() {
	}

	/**
	 * Constructor of the class Interest.
	 * 
	 * @param entities
	 * @param name
	 */
	public Interest(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.entities = new HashSet<SocialEntity>();
	}

	/**
	 * Constructor of the class Interest.
	 * 
	 * @param entities
	 * @param name
	 * @param parentInterest
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public Interest(String name, Interest parentInterest) {
		if (name == null || parentInterest == null) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.entities = new HashSet<SocialEntity>();
		this.parentInterest = parentInterest;
		this.parentInterest.getChildrenInterests().add(this);
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
	public Set<SocialEntity> getEntities() {
		return entities;
	}

	/**
	 * Gives the list of social entities that are affected by this interest.
	 * 
	 * @param entities
	 */
	public void setEntities(Set<SocialEntity> entities) {
		this.entities = entities;
	}

	/**
	 * 
	 * @return the interest name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gives a name to the interest.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the children's interests of this interests
	 */
	public Set<Interest> getChildrenInterests() {
		return childrenInterests;
	}

	/**
	 * @param childrenInterests
	 *            the children's interests to set
	 */
	public void setChildrenInterests(Set<Interest> childrenInterests) {
		this.childrenInterests = childrenInterests;
	}

	/**
	 * @return the parent's interest of this interest
	 */
	public Interest getParentInterest() {
		return parentInterest;
	}

	/**
	 * @param parentInterest
	 *            the parent's interest to set
	 */
	public void setParentInterest(Interest parentInterest) {
		this.parentInterest = parentInterest;
	}

	public void addChildrenInterest(Interest java) {
		// TODO Auto-generated method stub
	}

	public void removeChildrenInterest(Interest java) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Interest other = (Interest) obj;
		if (name == null) {
			if (other.name != null){
				return false;
			}
		} else if (!name.equals(other.name)){
			return false;
		}
		return true;
	}

	@PostRemove
	public void onRemove() {
		Logger.getAnonymousLogger().log(Level.INFO,
				"Interest.onInterestRemove(" + getId() + ")");
		for (SocialEntity entity : entities) {
			entity.getInterests().remove(this);
		}
	}

	@Override
	public String toString() {
		return name;
	}

}
