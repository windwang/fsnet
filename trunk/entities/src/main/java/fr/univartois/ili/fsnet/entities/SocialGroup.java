package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 
 * The class SocialGroup is used to group users or group
 * 
 * @author JFlamen
 */
@Entity
@DiscriminatorValue("G")
public class SocialGroup extends SocialElement implements Serializable {

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private List<SocialElement> socialElements;

	/**
	 * the master of the group
	 */

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH })
	private SocialEntity masterGroup;

	/**
	 * The creator of the group
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH })
	private SocialEntity creator;

	private String description;

	/**
	 * The social group state
	 */
	private boolean isEnabled;

	/**
	 * Constructor of the class SocialElement
	 */

	public SocialGroup() {

	}

	public SocialGroup(SocialEntity masterGroup, SocialEntity creator,
			String description) {
		if (creator == null || masterGroup == null || description == null) {
			throw new IllegalArgumentException();
		}
		this.isEnabled = true;
		this.creator = creator;
		this.masterGroup = masterGroup;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SocialEntity getCreator() {
		return creator;
	}

	public void setCreator(SocialEntity creator) {
		this.creator = creator;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}
