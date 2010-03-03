package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 
 * The class Interaction.
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Interaction implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * The identifier.
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	/**
	 * The community name.
	 */
	private String title;
	
	/**
	 * The creator of the interaction.
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH })
	private SocialEntity creator;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH })
	@OrderBy(value="name")
	private List<Interest> interests;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date lastModified;

	@OneToMany(mappedBy = "interaction", cascade=CascadeType.ALL)
	private Set<InteractionRole> roles;

	@ManyToMany(mappedBy = "favoriteInteractions")
	@JoinColumn(nullable = false)
	private Set<SocialEntity> followingEntitys;
	
	@Transient
	private int numSubscriber = 0;

	/**
	 * Constructor of the class Interaction.
	 */
	public Interaction() {
	}

	// TODO voir rapport d'activite
	// TODO !!! private
	public Interaction(SocialEntity creator, String title) {
		if (creator == null || title == null) {
			throw new IllegalArgumentException();
		}
		Date date = new Date();
		this.title = title;
		this.creationDate = date;
		this.lastModified = date;
		this.creator = creator;
		this.interests = new ArrayList<Interest>();
		this.roles = new HashSet<InteractionRole>();
		this.followingEntitys = new HashSet<SocialEntity>();
		// this.report = rapport;
	}

	@PreRemove
	public void onPreRemove() {
		interests.clear();
	}

	@PostUpdate
	@PostLoad
	@PostPersist
	public void onLoad() {
		numSubscriber = 0;
		for (InteractionRole interactionRole : getRoles()) {
			if (InteractionRole.RoleName.SUBSCRIBER.equals(interactionRole.getRole())) {
				numSubscriber++;
			}
		}
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
	 * @return the creator of the interaction.
	 */
	public SocialEntity getCreator() {
		return creator;
	}

	/**
	 * Set the creator of the interaction.
	 * 
	 * @param creator
	 */
	public void setCreator(SocialEntity createur) {
		this.creator = createur;
	}

	/**
	 * 
	 * @return the list of interests
	 */
	public List<Interest> getInterests() {
		return interests;
	}

	/**
	 * 
	 * @param interests
	 *            the list of interests
	 */
	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}

	/**
	 * Get the value of createDate
	 * 
	 * @return the value of createDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Set the value of createDate
	 * 
	 * @param createDate
	 *            new value of createDate
	 */
	public void setCreationDate(Date createDate) {
		this.creationDate = createDate;
	}

	/**
	 * Get the value of lastModified
	 * 
	 * @return the value of lastModified
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * Set the value of lastModified
	 * 
	 * @param lastModified
	 *            new value of lastModified
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * 
	 * @return the community name.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gives a name to the community.
	 * 
	 * @param title
	 *            .
	 */
	public void setTitle(String name) {
		this.title = name;
	}

	/**
	 * @param roles
	 *            the social entities's roles to set with this interaction
	 */
	public void setRoles(Set<InteractionRole> roles) {
		this.roles = roles;
	}

	/**
	 * @return the social entities's roles to associate with this interaction
	 */
	public Set<InteractionRole> getRoles() {
		return roles;
	}

	/**
	 * @param followingEntitys
	 *            the followingEntitys to set
	 */
	public void setFollowingEntitys(Set<SocialEntity> followingEntitys) {
		this.followingEntitys = followingEntitys;
	}

	/**
	 * @return the followingEntitys
	 */
	public Set<SocialEntity> getFollowingEntitys() {
		return followingEntitys;
	}

	public int getNumSubscriber() {
		return numSubscriber;
	}

	@PostRemove
	public void onInteractionRemove() {
		Logger.getAnonymousLogger().log(Level.INFO, "Interaction.onInteractionRemove("+getId()+")");
		for (SocialEntity socialEntity : getFollowingEntitys()) {
    		socialEntity.getFavoriteInteractions().remove(this);
    	}
		if (getCreator() != null) {
			getCreator().getInteractions().remove(this);
			setCreator(null);
		}
		interests.clear();
	}
}
