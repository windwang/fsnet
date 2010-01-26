package fr.univartois.ili.fsnet.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * 
 * The class Community.
 * 
 */
@Entity
public class Community extends Interaction {

	private static final long serialVersionUID = 1L;
	
	@OneToMany
    private Set<Hub> hubs;

    /**
     * Constructor of the class Community.
     */
    public Community() {
    }

    /**
     * Constructor of the class Community.
     *
     * @param name
     *            .
     */
    public Community(SocialEntity creator, String name) {
        super(creator, name);
    }


    /**
     * Get the value of hubs
     *
     * @return the value of hubs
     */
    public Set<Hub> getHubs() {
        return hubs;
    }

    /**
     * Set the value of hubs
     *
     * @param hubs new value of hubs
     */
    public void setHubs(Set<Hub> hubs) {
        this.hubs = hubs;
    }
}
