package fr.univartois.ili.fsnet.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * The class Consultation.
 * 
 */

@Entity
public class Consultation extends Interaction {

	public static enum TypeConsultation {
		YES_NO, YES_NO_OTHER, YES_NO_IFNECESSARY, PREFERENCE_ORDER;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3610580722835190140L;

	/**
	 * The list of choices available for this consultation
	 */
	@OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ConsultationChoice> choices;

	@OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ConsultationVote> consultationVotes;

	private String description;
	private boolean limitParticipantsPerChoice;
	private boolean limitChoicesPerParticipant;
	private int limitChoicesPerParticipantMin;
	private int limitChoicesPerParticipantMax;
	@Enumerated(value = EnumType.STRING)
	private TypeConsultation type;
	private double ifNecessaryWeight;
	private boolean showBeforeAnswer;
	private boolean showBeforeClosing;
	private boolean allowAllToModify;
	private boolean closingAtDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date maxDate;
	private boolean closingAtMaxVoters;
	private int maxVoters;
	private boolean opened;
	private int currentVoters;

	public Consultation() {
	}

	/**
	 * The Consultation constructor
	 * 
	 * @param creator
	 * @param title
	 * @param description
	 */
	public Consultation(SocialEntity creator, String title, String description) {
		super(creator, title);
		this.description = description;
		this.choices = new ArrayList<ConsultationChoice>();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ConsultationChoice> getChoices() {
		return choices;
	}

	public void setChoices(List<ConsultationChoice> choices) {
		this.choices = choices;
	}

	public boolean isLimitParticipantsPerChoice() {
		return limitParticipantsPerChoice;
	}

	public void setLimitParticipantsPerChoice(boolean limitParticipantsPerChoice) {
		this.limitParticipantsPerChoice = limitParticipantsPerChoice;
	}

	public boolean isLimitChoicesPerParticipant() {
		return limitChoicesPerParticipant;
	}

	public void setLimitChoicesPerParticipant(boolean limitChoicesPerParticipant) {
		this.limitChoicesPerParticipant = limitChoicesPerParticipant;
	}

	public int getLimitChoicesPerParticipantMin() {
		return limitChoicesPerParticipantMin;
	}

	public void setLimitChoicesPerParticipantMin(
			int limitChoicesPerParticipantMin) {
		this.limitChoicesPerParticipantMin = limitChoicesPerParticipantMin;
	}

	public int getLimitChoicesPerParticipantMax() {
		return limitChoicesPerParticipantMax;
	}

	public void setLimitChoicesPerParticipantMax(
			int limitChoicesPerParticipantMax) {
		this.limitChoicesPerParticipantMax = limitChoicesPerParticipantMax;
	}

	public TypeConsultation getType() {
		return type;
	}

	public void setType(TypeConsultation type) {
		this.type = type;
	}

	public double getIfNecessaryWeight() {
		return ifNecessaryWeight;
	}

	public void setIfNecessaryWeight(double ifNecessaryWeight) {
		this.ifNecessaryWeight = ifNecessaryWeight;
	}

	public boolean isShowBeforeAnswer() {
		return showBeforeAnswer;
	}

	public void setShowBeforeAnswer(boolean showBeforeAnswer) {
		this.showBeforeAnswer = showBeforeAnswer;
	}

	public boolean isShowBeforeClosing() {
		return showBeforeClosing;
	}

	public void setShowBeforeClosing(boolean showBeforeClosing) {
		this.showBeforeClosing = showBeforeClosing;
	}

	public boolean isAllowAllToModify() {
		return allowAllToModify;
	}

	public void setAllowAllToModify(boolean allowAllToModify) {
		this.allowAllToModify = allowAllToModify;
	}

	public boolean isClosingAtDate() {
		return closingAtDate;
	}

	public void setClosingAtDate(boolean closingAtDate) {
		this.closingAtDate = closingAtDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public boolean isClosingAtMaxVoters() {
		return closingAtMaxVoters;
	}

	public void setClosingAtMaxVoters(boolean closingAtMaxVoters) {
		this.closingAtMaxVoters = closingAtMaxVoters;
	}

	public int getMaxVoters() {
		return maxVoters;
	}

	public void setMaxVoters(int maxVoters) {
		this.maxVoters = maxVoters;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean current) {
		this.opened = current;
	}

	public int getCurrentVoters() {
		return currentVoters;
	}

	public void setCurrentVoters(int currentVoters) {
		this.currentVoters = currentVoters;
	}

	public void addChoice(ConsultationChoice consultationChoice) {
		this.choices.add(consultationChoice);
	}

	public List<ConsultationVote> getConsultationVotes() {
		return consultationVotes;
	}

	public void setConsultationVotes(List<ConsultationVote> consultationVotes) {
		this.consultationVotes = consultationVotes;
	}

}
