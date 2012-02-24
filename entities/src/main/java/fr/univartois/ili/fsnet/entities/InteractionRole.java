package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(value=InteractionRolePK.class)
public class InteractionRole implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum RoleName {
        DECISION_MAKER, SUBSCRIBER
    }
    
    @ManyToOne
    @Id
    private Interaction interaction;
    
    @ManyToOne
    @Id
    private SocialEntity socialEntity;
    
    @Enumerated(value = EnumType.STRING)
    private RoleName rolename;

    public InteractionRole(){
    }

    /**
     * Constructor
     * @param se
     * @param i
     */
    public InteractionRole(SocialEntity se, Interaction i){
        this.interaction = i;
        this.socialEntity = se;
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
