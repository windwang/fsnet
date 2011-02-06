package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ConsultationChoiceVote implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6852623674623963308L;

	/**
	 * The identifier.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	@ManyToOne
	private ConsultationVote vote;

	private boolean ifNecessary;

	public ConsultationChoiceVote() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ConsultationVote getVote() {
		return vote;
	}

	public void setVote(ConsultationVote vote) {
		this.vote = vote;
	}

	public boolean isIfNecessary() {
		return ifNecessary;
	}

	public void setIfNecessary(boolean ifNecessary) {
		this.ifNecessary = ifNecessary;
	}

}
