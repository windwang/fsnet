package fr.univartois.ili.fsnet.tags;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.Consultation;
import fr.univartois.ili.fsnet.entities.Consultation.TypeConsultation;
import fr.univartois.ili.fsnet.entities.ConsultationChoice;
import fr.univartois.ili.fsnet.entities.ConsultationChoiceVote;
import fr.univartois.ili.fsnet.entities.ConsultationVote;

public class ConsultationResultsTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Consultation consultation;
	private String number;
	private String percent;
	private String maximum;
	private String choice;
	
	private List<Double> nb;
	private Iterator<Double> currentNb;
	private List<String> choices;
	private Iterator<String> currentChoice;
	private double total;
	private double max;
	
	@Override
	public int doStartTag() throws JspException {
		if (consultation == null || consultation.getChoices().size() == 0)
			return SKIP_BODY;
		init();
		currentNb = nb.iterator();
		currentChoice = choices.iterator();
		if (!currentNb.hasNext()){
			return SKIP_BODY;			
		}
		setAttributes();
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doAfterBody() throws JspException {
		if (!currentNb.hasNext())
			return SKIP_BODY;
		setAttributes();
		return EVAL_BODY_AGAIN;
	}
	
	@Override
	public int doEndTag() throws JspException {
		if (number != null) pageContext.getRequest().removeAttribute(number);
		if (percent != null) pageContext.getRequest().removeAttribute(percent);
		if (maximum != null) pageContext.getRequest().removeAttribute(maximum);
		if (choice != null) pageContext.getRequest().removeAttribute(choice);
		return super.doEndTag();
	}
	
	public void setAttributes(){
		double nbVotes = currentNb.next();
		if (number != null && !"".equals(number)) {
			if (consultation.getType() == TypeConsultation.YES_NO_IFNECESSARY)
				pageContext.getRequest().setAttribute(number, Double.valueOf(Math.round(nbVotes*10))/10);
			else 
				pageContext.getRequest().setAttribute(number, Math.round(nbVotes));
		}
		if (percent != null && !"".equals(percent)) pageContext.getRequest().setAttribute(percent, Math.round(nbVotes*100/total));
		if (maximum != null && !"".equals(maximum)) pageContext.getRequest().setAttribute(maximum, nbVotes == max);
		if (choice != null && !"".equals(choice)) pageContext.getRequest().setAttribute(choice, currentChoice.next());
	}
	
	public void init(){
		Iterator<ConsultationChoice> choices = consultation.getChoices().iterator();
		nb = new ArrayList<Double>();
		this.choices = new ArrayList<String>();
		while (choices.hasNext()){
			choices.next();
			nb.add(0.0);
		}
		choices = consultation.getChoices().iterator();
		ConsultationChoice choice;
		Iterator<ConsultationVote> votes;
		ConsultationVote vote;
		Iterator<ConsultationChoiceVote> voteChoices;
		total = 0;
		max = 0;
		int i=0;
		while (choices.hasNext()){
			choice = choices.next();
			this.choices.add(choice.getIntituled());
			votes = consultation.getConsultationVotes().iterator();
			while (votes.hasNext()){
				vote = votes.next();
				voteChoices = vote.getChoices().iterator();
				while (voteChoices.hasNext()){
					ConsultationChoiceVote voteChoice = voteChoices.next();
					if (choice.getId() == voteChoice.getChoice().getId()){
						if (voteChoice.isIfNecessary()){
							nb.set(i, nb.get(i)+consultation.getIfNecessaryWeight());
							total+=consultation.getIfNecessaryWeight();
						}
						else {
							nb.set(i, nb.get(i)+1);
							total+=1;
						}
						if (max < nb.get(i))
							max = nb.get(i);
					}
				}
			}
			i++;
		}
		if (consultation.getType() == Consultation.TypeConsultation.YES_NO_OTHER){
			votes=consultation.getConsultationVotes().iterator();
			nb.add(0.0);
			while (votes.hasNext()){
				vote = votes.next();
				if (!"".equals(vote.getOther())){
					nb.set(i, nb.get(i)+1);
					if (max < nb.get(i))
						max = nb.get(i);
					total++;
				}
			}
		}
	}
	
	public Consultation getConsultation() {
		return consultation;
	}
	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String nb) {
		this.number = nb;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getMaximum() {
		return maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}
	
	
	
}
