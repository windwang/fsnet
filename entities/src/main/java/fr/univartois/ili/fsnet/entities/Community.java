package fr.univartois.ili.fsnet.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 
 * The class Community.
 * 
 */
@Entity
public class Community extends Interaction {

    private static final long serialVersionUID = 1L;
    
    @OneToMany(cascade=CascadeType.ALL,mappedBy="community")
    private Set<Hub> hubs;
    
    @OneToMany(cascade=CascadeType.ALL,mappedBy="parentCommunity")
    private Set<Community> childrenCommunities;
    
    @ManyToOne
    private Community parentCommunity;

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
        hubs = new HashSet<Hub>();
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

	/**
	 * @return the childrenCommunities
	 */
	public Set<Community> getChildrenCommunities() {
		return childrenCommunities;
	}

	/**
	 * @param childrenCommunities the childrenCommunities to set
	 */
	public void setChildrenCommunities(Set<Community> childrenCommunities) {
		this.childrenCommunities = childrenCommunities;
	}
 
	/**
	 * @return the parentCommunity
	 */
	public Community getParentCommunity() {
		return parentCommunity;
	}

	/**
	 * @param parentCommunity the parent community of this community to set
	 */
	public void setParentCommunity(Community parentCommunity) {
		this.parentCommunity = parentCommunity;
	}
}
