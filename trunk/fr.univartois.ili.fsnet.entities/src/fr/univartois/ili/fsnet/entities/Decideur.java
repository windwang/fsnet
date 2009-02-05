package fr.univartois.ili.fsnet.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Decideur {
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	@ManyToOne
	private EntiteSociale entSociale;
	@OneToOne(mappedBy="decideur")
	private Interaction interaction;

	public Decideur(EntiteSociale entSociale, Interaction interaction) {
		this.entSociale = entSociale;
		this.interaction = interaction;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EntiteSociale getEntSociale() {
		return entSociale;
	}

	public void setEntSociale(EntiteSociale entSociale) {
		this.entSociale = entSociale;
	}

	public Interaction getInteraction() {
		return interaction;
	}

	public void setInteraction(Interaction interaction) {
		this.interaction = interaction;
	}

}
