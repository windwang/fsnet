package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 
 * The class SocialGroup is used to group users or group
 * 
 * @author JFlamen
 * @author SAID Mohamed <simo.said09 at gmail.com>
 * @author stephane gronowski
 */
@Entity
@DiscriminatorValue("G")
public class SocialGroup extends SocialElement implements Serializable {

	private static final long serialVersionUID = 1L;

	@ElementCollection(targetClass = Right.class)
	@Enumerated(EnumType.STRING)
	private Set<Right> rights = new HashSet<Right>();

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private List<SocialElement> socialElements;

	/**
	 * the master of the group
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH })
	private SocialEntity masterGroup;

	/**
	 * The name of the group
	 */
	@Column(unique = true, nullable = false)
	private String name;

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * The social group state
	 */
	private Boolean isEnabled;

	/**
	 * Constructor of the class SocialElement
	 */

	public SocialGroup() {

	}

	public SocialGroup(SocialEntity masterGroup, String name, String description) {
		if (masterGroup == null || name == null) {
			throw new IllegalArgumentException();
		}
		this.isEnabled = true;

		this.masterGroup = masterGroup;
		this.name = name;
		this.description = description;
		this.socialElements = new ArrayList<SocialElement>();

	}

	/**
	 * Determine if the group has the specific {@link Right}
	 * 
	 * @param right
	 *            the specific {@link Right}
	 * @return true if it has this {@link Right}
	 */
	public boolean isAuthorized(Right right) {
		if (rights.contains(right))
			return true;

		SocialGroup father = getGroup();
		if (father != null)
			return father.isAuthorized(right);

		return false;
	}

	/**
	 * Return the {@link Right}s of the {@link SocialGroup}
	 * 
	 * @return the {@link Right}s of the {@link SocialGroup}
	 */
	public Set<Right> getrights() {
		return rights;
	}

	/**
	 * Add a {@link Right} to the {@link SocialGroup}
	 * 
	 * @param right
	 *            the specific {@link Right}
	 */
	public void addRight(Right right) {
		rights.add(right);
	}

	/**
	 * Add a set of {@link Right} to the {@link SocialGroup}
	 * 
	 * @param rights
	 *            the specific {@link Right}s
	 */
	public void addRights(Set<Right> rights) {
		this.rights.addAll(rights);
	}

	/**
	 * Remove a {@link Right} to the {@link SocialGroup}
	 * 
	 * @param right
	 *            the specific {@link Right}
	 */
	public void removeRight(Right right) {
		rights.remove(right);
	}

	public List<SocialElement> getSocialElements() {
		return socialElements;
	}

	public void setSocialElements(List<SocialElement> socialElements) {
		for (SocialElement socialElement : this.socialElements) {
			socialElement.setGroup(null);
		}
		for (SocialElement socialElement : socialElements)
			socialElement.setGroup(this);
		this.socialElements = socialElements;
	}

	public void addSocialElements(SocialElement socialElement) {
		if (!this.socialElements.contains(socialElement)) {
			socialElement.setGroup(this);
			this.socialElements.add(socialElement);
		}
	}

	public void removeSocialElements(SocialElement socialElement) {
		if (this.socialElements.contains(socialElement)) {
			socialElement.setGroup(null);
			this.socialElements.remove(socialElement);
		}
	}

	public SocialEntity getMasterGroup() {
		return masterGroup;
	}

	public void setMasterGroup(SocialEntity masterGroup) {
		this.masterGroup = masterGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * public SocialEntity getCreator() { return creator; }
	 * 
	 * public void setCreator(SocialEntity creator) { this.creator = creator; }
	 */

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {

			return false;
		}

		final SocialGroup other = (SocialGroup) obj;

		if ((this.name == null) ? (other.name != null) : !this.name
				.equals(other.name)) {

			return false;
		}
		if ((this.description == null) ? (other.description != null)
				: !this.description.equals(other.description)) {

			return false;
		}
		if (!this.masterGroup.equals(other.masterGroup)) {

			return false;
		}
		if (this.isEnabled != other.isEnabled) {

			return false;
		}
		if (this.socialElements.size() != other.socialElements.size()) {
			return false;
		}

		return true;
	}
}
