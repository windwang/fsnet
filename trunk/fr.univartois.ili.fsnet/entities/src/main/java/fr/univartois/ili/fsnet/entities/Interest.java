package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
public class Interest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * The identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    /**
     * The list of social entities that are affected by this interest.
     */
    @ManyToMany(mappedBy = "interests")
    @JoinColumn(nullable = false)
    private Set<SocialEntity> entities = new HashSet<SocialEntity>();
    /**
     * The interest name.
     */
    @Column(nullable = false, unique = true)
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Interest other = (Interest) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
    
}
