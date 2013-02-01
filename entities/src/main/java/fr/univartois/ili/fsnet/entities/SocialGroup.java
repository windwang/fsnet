package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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

	private String color;
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

	/**
	 * The social group state
	 */
	private Boolean isEnabled;

	@Override
	public boolean getIsEnabled() {
		return isEnabled;
	}

	@Override
	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

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
		this.setColor("C9E6F8");
	}

	/**
	 * Determine if the group has the specific {@link Right}
	 * 
	 * @param right
	 *            the specific {@link Right}
	 * @return true if it has this {@link Right}
	 */
	public boolean isAuthorized(Right right) {
		if (rights.contains(right)){
			return true;
		}

		SocialGroup father = getGroup();
		if (father != null){
			return father.isAuthorized(right);
		}

		return false;
	}

	/**
	 * Return the {@link Right}s of the {@link SocialGroup}
	 * 
	 * @return the {@link Right}s of the {@link SocialGroup}
	 */
	public Set<Right> getRights() {
		return rights;
	}

	/**
	 * @author Mohamed SAID
	 * 
	 * @return the {@link Right}s of the {@link SocialGroup}
	 */
	public void setRights(Set<Right> rights) {
		this.rights = rights;
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
		for (SocialElement socialElement : socialElements){
			socialElement.setGroup(this);
		}
		
		this.socialElements = socialElements;
	}

	
	public void setSocialElements(List<SocialElement> socialElements, SocialGroup sg) {

		for (SocialElement socialElement : this.socialElements) {
			socialElement.setGroup(sg);
		}
		for (SocialElement socialElement : socialElements){
			socialElement.setGroup(this);
		}
		
		this.socialElements = socialElements;
	}
	public void addSocialElement(SocialElement socialElement) {
		if (!this.socialElements.contains(socialElement)) {
			socialElement.setGroup(this);
			this.socialElements.add(socialElement);
		}
	}

	public void addAllSocialElements(Collection<SocialElement> socialElements) {
		for (SocialElement socialElement : socialElements) {
			addSocialElement(socialElement);
		}
	}

	public void removeSocialElement(SocialElement socialElement) {
		if (this.socialElements.contains(socialElement)) {
			socialElement.setGroup(null);
			this.socialElements.remove(socialElement);
		}
	}

	public void removeAllSocialElements(Collection<SocialElement> socialElements) {
		for (SocialElement socialElement : socialElements) {
			removeSocialElement(socialElement);
		}
	}

	public SocialEntity getMasterGroup() {
		return masterGroup;
	}

	public void setMasterGroup(SocialEntity masterGroup) {
		SocialGroup oldSocialGroup = masterGroup.getGroup();
		if (!this.equals(oldSocialGroup) && oldSocialGroup != null){
			oldSocialGroup.removeSocialElement(masterGroup);
		}
		this.addSocialElement(masterGroup);
		this.masterGroup = masterGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((isEnabled == null) ? 0 : isEnabled.hashCode());
		result = prime * result
				+ ((masterGroup == null) ? 0 : masterGroup.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rights == null) ? 0 : rights.hashCode());
		result = prime * result
				+ ((socialElements == null) ? 0 : socialElements.hashCode());
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
		SocialGroup other = (SocialGroup) obj;
		if (description == null) {
			if (other.description != null){
				return false;
			}
		} else if (!description.equals(other.description)){
			return false;
		}
		if (isEnabled == null) {
			if (other.isEnabled != null){
				return false;
			}
		} else if (!isEnabled.equals(other.isEnabled)){
			return false;
		}
		if (masterGroup == null) {
			if (other.masterGroup != null){
				return false;
			}
		} else if (!masterGroup.equals(other.masterGroup)){
			return false;
		}
		if (name == null) {
			if (other.name != null){
				return false;
			}
		} else if (!name.equals(other.name)){
			return false;
		}
		if (rights == null) {
			if (other.rights != null){
				return false;
			}
		} else if (!rights.equals(other.rights)){
			return false;
		}
		if (socialElements == null) {
			if (other.socialElements != null){
				return false;
			}
		} else if (!socialElements.equals(other.socialElements)){
			return false;
		}
		return true;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
