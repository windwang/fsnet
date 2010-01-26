package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=
		@UniqueConstraint(columnNames={
								"SOCIALENTITY_ID", 
								"INTERACTION_ID"}))
public class InteractionRole implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum RoleName {
        DECISION_MAKER, SUBSCRIBER
    }
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
    @ManyToOne
    private Interaction interaction;
    
    @ManyToOne
    private SocialEntity socialEntity;
    
    @Enumerated(value=EnumType.STRING)
    private RoleName rolename;

	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the rolename
	 */
	public RoleName getRolename() {
		return rolename;
	}

	/**
	 * @param rolename the rolename to set
	 */
	public void setRolename(RoleName rolename) {
		this.rolename = rolename;
	}

	/**
     * Get the value of interaction
     *
     * @return the value of interaction
     */
    public Interaction getInteraction() {
        return interaction;
    }

    /**
     * Set the value of interaction
     *
     * @param interaction new value of interaction
     */
    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }

    /**
     * Get the value of socialEntity
     *
     * @return the value of socialEntity
     */
    public SocialEntity getSocialEntity() {
        return socialEntity;
    }

    /**
     * Set the value of socialEntity
     *
     * @param socialEntity new value of socialEntity
     */
    public void setSocialEntity(SocialEntity socialEntity) {
        this.socialEntity = socialEntity;
    }

    /**
     * Get the value of role
     *
     * @return the value of role
     */
    public RoleName getRole() {
        return rolename;
    }

    /**
     * Set the value of role
     *
     * @param role new value of role
     */
    public void setRole(RoleName role) {
        this.rolename = role;
    }
}
