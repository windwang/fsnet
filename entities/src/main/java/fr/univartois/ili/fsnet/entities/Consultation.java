package fr.univartois.ili.fsnet.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
	@OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL)
	@OrderBy(value = "intituled")
	private List<ConsultationChoice> choices;

	@OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL)
	@OrderBy(value = "id")
	private List<ConsultationVote> consultationVotes;

	private String description;

	@Column(length = 1)
	private String limitParticipantsPerChoice;

	@Column(length = 1)
	private String limitChoicesPerParticipant;

	private int limitChoicesPerParticipantMin;

	private int limitChoicesPerParticipantMax;

	@Enumerated(value = EnumType.STRING)
	private TypeConsultation type;

	private double ifNecessaryWeight;

	@Column(length = 1)
	private String showBeforeAnswer;

	@Column(length = 1)
	private String showBeforeClosing;

	@Column(length = 1)
	private String allowAllToModify;

	@Column(length = 1)
	private String closingAtDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date maxDate;

	@Column(length = 1)
	private String closingAtMaxVoters;

	private int maxVoters;

	@Column(length = 1)
	private String opened;

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
	public Consultation(SocialEntity creator, String title, String description,
			TypeConsultation type) {
		super(creator, title);
		this.description = description;
		this.type = type;
		limitChoicesPerParticipant = "F";
		limitChoicesPerParticipantMin = 1;
		limitChoicesPerParticipantMax = 1;
		closingAtDate = "F";
		closingAtMaxVoters = "F";
		limitParticipantsPerChoice = "F";
		showBeforeAnswer = "T";
		showBeforeClosing = "T";
		allowAllToModify = "F";
		opened = "T";
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

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public int getMaxVoters() {
		return maxVoters;
	}

	public void setMaxVoters(int maxVoters) {
		this.maxVoters = maxVoters;
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

	public String getLimitParticipantsPerChoice() {
		return limitParticipantsPerChoice;
	}

	public void setLimitParticipantsPerChoice(String limitParticipantsPerChoice) {
		this.limitParticipantsPerChoice = limitParticipantsPerChoice;
	}

	@Transient
	public boolean isLimitParticipantsPerChoice() {
		return "T".equals(limitParticipantsPerChoice);
	}

	public void setLimitParticipantPerChoice(boolean limitParticipantsPerChoice) {
		if (limitParticipantsPerChoice)
			this.limitParticipantsPerChoice = "T";
		else
			this.limitParticipantsPerChoice = "F";
	}

	public String getLimitChoicesPerParticipant() {
		return limitChoicesPerParticipant;
	}

	public void setLimitChoicesPerParticipant(String limitChoicesPerParticipant) {
		this.limitChoicesPerParticipant = limitChoicesPerParticipant;
	}

	@Transient
	public boolean isLimitChoicesPerParticipant() {
		return "T".equals(limitChoicesPerParticipant);
	}

	public void setLimitChoicesPerParticipant(boolean limitChoicesPerParticipant) {
		if (limitChoicesPerParticipant) {
			this.limitChoicesPerParticipant = "T";
		} else
			this.limitChoicesPerParticipant = "F";
	}

	public String getShowBeforeAnswer() {
		return showBeforeAnswer;
	}

	public void setShowBeforeAnswer(String showBeforeAnswer) {
		this.showBeforeAnswer = showBeforeAnswer;
	}

	@Transient
	public boolean isShowBeforeAnswer() {
		return "T".equals(showBeforeAnswer);
	}

	public void setShowBeforeAnswer(boolean showBeforeAnswer) {
		if (showBeforeAnswer)
			this.showBeforeAnswer = "T";
		else
			this.showBeforeAnswer = "F";
	}

	public String getShowBeforeClosing() {
		return showBeforeClosing;
	}

	public void setShowBeforeClosing(String showBeforeClosing) {
		this.showBeforeClosing = showBeforeClosing;
	}

	@Transient
	public boolean isShowBeforeClosing() {
		return "T".equals(showBeforeClosing);
	}

	public void setShowBeforeClosing(boolean showBeforeClosing) {
		if (showBeforeClosing)
			this.showBeforeClosing = "T";
		else
			this.showBeforeClosing = "F";
	}

	public String getAllowAllToModify() {
		return allowAllToModify;
	}

	public void setAllowAllToModify(String allowAllToModify) {
		this.allowAllToModify = allowAllToModify;
	}

	@Transient
	public boolean isAllowAllToModify() {
		return "T".equals(allowAllToModify);
	}

	public void setAllowAllToModify(boolean allowAllToModify) {
		if (allowAllToModify)
			this.allowAllToModify = "T";
		else
			this.allowAllToModify = "F";
	}

	public String getClosingAtDate() {
		return closingAtDate;
	}

	public void setClosingAtDate(String closingAtDate) {
		this.closingAtDate = closingAtDate;
	}

	@Transient
	public boolean isClosingAtDate() {
		return "T".equals(closingAtDate);
	}

	public void setClosingAtDate(boolean closingAtDate) {
		if (closingAtDate)
			this.closingAtDate = "T";
		else
			this.closingAtDate = "F";
	}

	public String getClosingAtMaxVoters() {
		return closingAtMaxVoters;
	}

	public void setClosingAtMaxVoters(String closingAtMaxVoters) {
		this.closingAtMaxVoters = closingAtMaxVoters;
	}

	@Transient
	public boolean isClosingAtMaxVoters() {
		return "T".equals(closingAtMaxVoters);
	}

	public void setClosingAtMaxVoters(boolean closingAtMaxVoters) {
		if (closingAtMaxVoters)
			this.closingAtMaxVoters = "T";
		else
			this.closingAtMaxVoters = "F";
	}

	public String getOpened() {
		return opened;
	}

	public void setOpened(String opened) {
		this.opened = opened;
	}

	@Transient
	public boolean isOpened() {
		return "T".equals(opened);
	}

	public void setOpened(boolean opened) {
		if (opened)
			this.opened = "T";
		else
			this.opened = "F";
	}

	public boolean isVoted(SocialEntity member) {
		Iterator<ConsultationVote> it = consultationVotes.iterator();
		while (it.hasNext()) {
			if (it.next().getVoter().equals(member)){
				return true;
			}
		}
		return false;
	}

	public boolean isMaximumVoterReached() {
		return "T".equals(closingAtMaxVoters)
				&& consultationVotes.size() >= maxVoters;
	}

	public boolean isDeadlineReached() {
		return "T".equals(closingAtDate) && maxDate.before(new Date());
	}
}
