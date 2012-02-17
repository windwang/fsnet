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
	 * The group to which the interaction relates
	 */
    @ManyToOne
    @Id
	private SocialGroup group;
	
	/**
	 * The interaction to which the groups relates
	 */
    @ManyToOne
    @Id
	private Interaction interaction;
	
	public InteractionGroups() {
	}

	public InteractionGroups(Interaction interaction, SocialGroup group) {
		this.interaction = interaction;
		this.group = group;
	}

	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return
	 */
	public SocialGroup getGroup() {
		return group;
	}

	/**
	 * 
	 * @param group2
	 */
	public void setGroup(SocialGroup group2) {
		this.group = group2;
	}

	/**
	 * 
	 * @return
	 */
	public Interaction getInteraction() {
		return interaction;
	}

	/**
	 * 
	 * @param interaction
	 */
	public void setInteraction(Interaction interaction) {
		this.interaction = interaction;
	}
}
