package fr.univartois.ili.fsnet.tags;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.Consultation;
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
	
	private List<Integer> nb;
	private Iterator<Integer> currentNb;
	private int total;
	private int max;
	
	@Override
	public int doStartTag() throws JspException {
		if (consultation == null || consultation.getChoices().size() == 0)
			return SKIP_BODY;
		init();
		currentNb = nb.iterator();
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
		pageContext.getRequest().removeAttribute(number);
		pageContext.getRequest().removeAttribute(percent);
		return super.doEndTag();
	}
	
	public void setAttributes(){
		int nbVotes = currentNb.next();
		if (!"".equals(number)) pageContext.getRequest().setAttribute(number, nbVotes);
		if (!"".equals(percent)) pageContext.getRequest().setAttribute(percent, Math.round(Float.valueOf(nbVotes*100)/total));
		if (!"".endsWith(maximum)) pageContext.getRequest().setAttribute(maximum, nbVotes == max);
	}
	
	public void init(){
		Iterator<ConsultationChoice> choices = consultation.getChoices().iterator();
		nb = new ArrayList<Integer>();
		while (choices.hasNext()){
			choices.next();
			nb.add(0);
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
			votes = consultation.getConsultationVotes().iterator();
			while (votes.hasNext()){
				vote = votes.next();
				voteChoices = vote.getChoices().iterator();
				while (voteChoices.hasNext()){
					if (choice.getId() == voteChoices.next().getChoice().getId()){
						nb.set(i, nb.get(i)+1);
						if (max < nb.get(i))
							max = nb.get(i);
						total++;
					}
				}
			}
			i++;
		}
		if (consultation.getType() == Consultation.TypeConsultation.YES_NO_OTHER){
			votes=consultation.getConsultationVotes().iterator();
			nb.add(0);
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
	
	
	
}
