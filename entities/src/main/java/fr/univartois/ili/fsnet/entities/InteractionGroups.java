package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class InteractionGroups implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The identifier.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/**
	 * The intituled or name of the group
	 */
    @ManyToOne
    @Id
	private SocialGroup group;
	
	/**
	 * The consultation to which the choice relates
	 */
    @ManyToOne
    @Id
	private Interaction interaction;
	
	public InteractionGroups() {
	}

	public InteractionGroups(Interaction interaction, SocialGroup group) {
		this.setInteraction(interaction);
		this.setGroup(group);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SocialGroup getName_group() {
		return group;
	}

	public void setGroup(SocialGroup group2) {
		this.group = group2;
	}

	public Interaction getInteraction() {
		return interaction;
	}

	public void setInteraction(Interaction interaction) {
		this.interaction = interaction;
	}
}
