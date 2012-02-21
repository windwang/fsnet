package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author geoffrey boulay
 * 
 * This class represent a profile visit
 *
 */

@IdClass(value=ProfileVisitePK.class)
@Entity
public class ProfileVisite implements Serializable{
	
	@ManyToOne
	@Id
	private SocialEntity visited;
	
	@ManyToOne
	@Id
	private SocialEntity visitor;
	
	 @Temporal(TemporalType.TIMESTAMP)
	private Date lastVisite;
	 
	 
	/**
	 * Default constructor
	 */
	 public ProfileVisite() {		 
		 
	 }
	 
	/**
	 * Create a Profile Visite
	 * @param visited social entity's profile visited
	 * @param visiter social entity's profile visiter
	 */
	public ProfileVisite(SocialEntity visited, SocialEntity visitor) {
		super();
		this.visited = visited;
		this.visitor = visitor;
		lastVisite = new Date();
	}
	
	/**
	 * 
	 * @return socialEntity whom profile was visited
	 */
	public SocialEntity getVisited() {
		return visited;
	}

	/**
	 * set the socialEntity whom profile was visited
	 * @param visited socialEntity whom profile was visited
	 */
	public void setVisited(SocialEntity visited) {
		this.visited = visited;
	}

	/**
	 * 
	 * @return the profile visitor
	 */
	public SocialEntity getVisitor() {
		return visitor;
	}
	/**
	 * set the visitor of profile
	 * @param visitor the visitor
	 */
	public void setVisitor(SocialEntity visitor) {
		this.visitor = visitor;
	}

	/**
	 * 
	 * @return the last visit of the visited profile by the visiter
	 */
	public Date getLastVisite() {
		return lastVisite;
	}

	/**
	 * set the last visit of the visited profile by the visiter
	 * @param lastVisite
	 */
	public void setLastVisite(Date lastVisite) {
		this.lastVisite = lastVisite;
	}
	
	
	/**
	 * the visiter watch against the profile
	 */
	public void visiteAgainst(){
		this.lastVisite = new Date();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((lastVisite == null) ? 0 : lastVisite.hashCode());
		result = prime * result + ((visited == null) ? 0 : visited.hashCode());
		result = prime * result + ((visitor == null) ? 0 : visitor.hashCode());
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
		ProfileVisite other = (ProfileVisite) obj;
		if (lastVisite == null) {
			if (other.lastVisite != null){
				return false;
			}
		} else if (!lastVisite.equals(other.lastVisite)){
			return false;
		}
		if (visited == null) {
			if (other.visited != null){
				return false;
			}
		} else if (!visited.equals(other.visited)){
			return false;
		}
		if (visitor == null) {
			if (other.visitor != null){
				return false;
			}
		} else if (!visitor.equals(other.visitor)){
			return false;
		}
		return true;
	}
	
	
}
