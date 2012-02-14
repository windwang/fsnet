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
	private String name_group;
	
	/**
	 * The consultation to which the choice relates
	 */
	@ManyToOne
	private Interaction interaction;
	
	public InteractionGroups() {
	}

	public InteractionGroups(Interaction interaction, String group) {
		this.setInteraction(interaction);
		setName_group(group);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName_group() {
		return name_group;
	}

	public void setName_group(String name_group) {
		this.name_group = name_group;
	}

	public Interaction getInteraction() {
		return interaction;
	}

	public void setInteraction(Interaction interaction) {
		this.interaction = interaction;
	}
}
