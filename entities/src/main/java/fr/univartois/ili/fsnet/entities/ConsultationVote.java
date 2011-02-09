package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ConsultationVote implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2449018938448079607L;

	/**
	 * The identifier.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	@ManyToOne
	private Consultation consultation;

	private SocialEntity voter;
	private String comment;
	private String other;

	@OneToMany(mappedBy = "vote", cascade = CascadeType.ALL)
	private List<ConsultationChoiceVote> choices;

	public ConsultationVote() {
	}

	public ConsultationVote(SocialEntity voter, String comment, String other) {
		this.voter = voter;
		this.comment = comment;
		this.other = other;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SocialEntity getVoter() {
		return voter;
	}

	public void setVoter(SocialEntity voter) {
		this.voter = voter;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public List<ConsultationChoiceVote> getChoices() {
		return choices;
	}

	public void setChoices(List<ConsultationChoiceVote> choices) {
		this.choices = choices;
	}

	public Consultation getConsultation() {
		return consultation;
	}

	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}

}
