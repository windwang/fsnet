package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 
 * The class SocialGroup is used to group users or group
 * 
 * @author JFlamen
 * @author SAID Mohamed <simo.said09 at gmail.com>
 */
@Entity
@DiscriminatorValue("G")
public class SocialGroup extends SocialElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	// public SocialGroup(SocialEntity masterGroup, SocialEntity creator,
	public SocialGroup(SocialEntity masterGroup, String name, String description) {
		if (masterGroup == null || name == null) {
			throw new IllegalArgumentException();
		}
		this.isEnabled = true;
		// this.creator = creator;
		this.masterGroup = masterGroup;
		this.name = name;
		this.description = description;
		this.socialElements = new ArrayList<SocialElement>();

	}

	public List<SocialElement> getSocialElements() {
		return socialElements;
	}

	public void setSocialElements(List<SocialElement> socialElements) {
		this.socialElements = socialElements;
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
		if (!this.socialElements.equals(other.socialElements)) {
			return false;
		}
		return true;
	}
}
