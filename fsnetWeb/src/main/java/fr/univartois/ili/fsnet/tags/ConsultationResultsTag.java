package fr.univartois.ili.fsnet.tags;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;

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
	private String histogram;
	
	private List<Double> nb;
	private Iterator<Double> currentNb;
	private List<String> choices;
	private Iterator<String> currentChoice;
	private double total;
	private double max;
	
	@Override
	public int doStartTag() throws JspException {
		if (consultation == null || consultation.getChoices().size() == 0){
			return SKIP_BODY;
		}
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
		if (!currentNb.hasNext()){
			return SKIP_BODY;
		}
		setAttributes();
		return EVAL_BODY_AGAIN;
	}
	
	@Override
	public int doEndTag() throws JspException {
		if (number != null){ pageContext.getRequest().removeAttribute(number); }
		if (percent != null){ pageContext.getRequest().removeAttribute(percent);}
		if (maximum != null){ pageContext.getRequest().removeAttribute(maximum);}
		if (choice != null){ pageContext.getRequest().removeAttribute(choice);}
		return super.doEndTag();
	}
	
	public void setAttributes(){
		double nbVotes = currentNb.next();
		if (number != null && !"".equals(number)) {
			if (consultation.getType() == TypeConsultation.YES_NO_IFNECESSARY){
				pageContext.getRequest().setAttribute(number, Double.valueOf(Math.round(nbVotes*10))/10);
			}else{ 
				pageContext.getRequest().setAttribute(number, Math.round(nbVotes));
			}
		}
		if (percent != null && !"".equals(percent)){ pageContext.getRequest().setAttribute(percent, Math.round(nbVotes*100/total));}
		if (maximum != null && !"".equals(maximum)){ pageContext.getRequest().setAttribute(maximum, nbVotes == max);}
		if (choice != null && !"".equals(choice)){ pageContext.getRequest().setAttribute(choice, currentChoice.next());}
	}
	
	public void init(){
		ConsultationChoice choice;
		Iterator<ConsultationVote> votes;
		ConsultationVote vote;
		Iterator<ConsultationChoiceVote> voteChoices;
		ConsultationChoiceVote voteChoice;
		Iterator<ConsultationChoice> choices = consultation.getChoices().iterator();
		nb = new ArrayList<Double>();
		this.choices = new ArrayList<String>();
		while (choices.hasNext()){
			choices.next();
			nb.add(0.0);
		}
		choices = consultation.getChoices().iterator();
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
					voteChoice = voteChoices.next();
					if (choice.getId() == voteChoice.getChoice().getId()){
						if (voteChoice.isIfNecessary()){
							nb.set(i, nb.get(i)+consultation.getIfNecessaryWeight());
							total+=consultation.getIfNecessaryWeight();
						}
						else {
							if (consultation.getType() == TypeConsultation.PREFERENCE_ORDER){
								nb.set(i, nb.get(i)+voteChoice.getPreferenceOrder());
							}
							else {
								nb.set(i, nb.get(i)+1);
								total+=1;
							}
						}
						if (max < nb.get(i)){
							max = nb.get(i);
						}
					}
				}
			}
			i++;
		}
		if (consultation.getType() == Consultation.TypeConsultation.YES_NO_OTHER){
			initOther();
		}
		if (consultation.getType() == TypeConsultation.PREFERENCE_ORDER){
			initPreferenceOrder();
		}
	}
	
	public void initOther(){
		Iterator<ConsultationVote> votes=consultation.getConsultationVotes().iterator();
		ConsultationVote vote;
		int i = nb.size();
		if (histogram != null && "yes".equals(histogram)){
			int iChoice;
			while (votes.hasNext()){
				vote = votes.next();
				if (!"".equals(vote.getOther())){
					if (this.choices.contains(vote.getOther())){
						iChoice=this.choices.indexOf(vote.getOther());
						nb.set(iChoice, nb.get(iChoice)+1);
					}
					else {
						iChoice=nb.size();
						nb.add(1.0);
						this.choices.add(vote.getOther());
					}
					total++;
				}
			}
		}
		else {
			nb.add(0.0);
			this.choices.add(ResourceBundle.getBundle("FSneti18n", pageContext.getRequest().getLocale()).getString("consultation.other"));
			while (votes.hasNext()){
				vote = votes.next();
				if (!"".equals(vote.getOther())){
					nb.set(i, nb.get(i)+1);
					if (max < nb.get(i)){
						max = nb.get(i);
					}
					total++;
				}
			}
		}
	}
	
	public void initPreferenceOrder(){
		SortedSet <Double>marks = new TreeSet<Double>();
		marks.addAll(nb);
		
		for (int i=0,j=0 ; i<nb.size() ; j=0,i++){
			for (Double mark : marks){
				j++;
				if (mark.equals(nb.get(i))){
					nb.set(i, Double.valueOf(j));
				}
			}
		}
	}
	
//	public void initPreferenceOrder(){
//		int i;
//		SortedSet<Integer> marks = new TreeSet<Integer>();
//		for (Double mark : nb){
//			marks.add(mark.intValue());
//		}
//		
//		for (int j=0 ; j<nb.size() ; j++){
//			i=0;
//			for (Integer mark : marks){
//				System.out.println("nb: "+nb.get(i)+"  mark: "+mark);
//				if (mark.equals(nb.get(j).intValue())){
//					nb.set(j, Double.valueOf(j+1));
//				}
//			}
//		}
////		for (Double number : nb){
////			i=0;
////			for (Integer mark : marks){
////				System.out.println("mark:"+mark+"  number:"+number);
////				if (mark.equals(number.intValue())){
////					number = Double.valueOf(i+1);
////					System.out.println("plop: "+number);
////				}
////				i++;
////			}
////		}
////		
//		for (Double number : nb){
//			System.out.println("hey:"+number);
//		}
//		
//	}
	
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

	public String getHistogram() {
		return histogram;
	}

	public void setHistogram(String histogram) {
		this.histogram = histogram;
	}
		
}
