package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class InteractionRole implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum RoleName {

        DECISION_MAKER, SUBSCRIBER
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Interaction interaction;
    @ManyToOne
    private SocialEntity socialEntity;
    private RoleName rolename;

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(Long id) {
        this.id = id;
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
