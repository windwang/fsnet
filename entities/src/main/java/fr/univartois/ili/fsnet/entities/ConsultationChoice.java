package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ConsultationChoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1916908793891086605L;

	/**
	 * The identifier.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	/**
	 * The maximum of voters for this particular choice
	 */
	private int maxVoters;
	/**
	 * The intituled or name of the choice
	 */
	private String intituled;

	/**
	 * The consultation to which the choice relates
	 */
	@ManyToOne
	private Consultation consultation;

	public ConsultationChoice() {
	}

	public ConsultationChoice(String choice) {
		intituled = choice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMaxVoters() {
		return maxVoters;
	}

	public void setMaxVoters(int maxVoters) {
		this.maxVoters = maxVoters;
	}

	public String getIntituled() {
		return intituled;
	}

	public void setIntituled(String intituled) {
		this.intituled = intituled;
	}

	public Consultation getConsultation() {
		return consultation;
	}

	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}

}
