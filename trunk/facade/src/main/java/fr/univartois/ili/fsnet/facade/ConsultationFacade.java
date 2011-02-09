package fr.univartois.ili.fsnet.facade;


import java.util.List;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.Consultation;
import fr.univartois.ili.fsnet.entities.ConsultationChoice;
import fr.univartois.ili.fsnet.entities.ConsultationChoiceVote;
import fr.univartois.ili.fsnet.entities.ConsultationVote;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class ConsultationFacade {
	private final EntityManager em;
	
	public ConsultationFacade(EntityManager em) {
		this.em = em;
	}
	
	public final Consultation createConsultation(SocialEntity creator, String title, String description, String [] choices){
		Consultation consultation = new Consultation(creator, title, description);
		for (String s : choices){
			consultation.addChoice(new ConsultationChoice(s));
		}
		em.persist(consultation);
		return consultation;
	}
	
	public final Consultation getConsultation(int consultationId) {
        return em.find(Consultation.class, consultationId);
    }
	
	
	public final ConsultationVote voteForConsultation(SocialEntity voter, Integer idConsultation, String comment,String other,List<String> choices){
		ConsultationVote consultationVote = new ConsultationVote(voter,comment,other);
		Consultation consultation = getConsultation(idConsultation);
		if(consultation != null){
			consultationVote.setConsultation(consultation);
		}
		for(ConsultationChoice choice : consultation.getChoices()){
			if(choices.contains("idChoix"+choice.getId())){
				consultationVote.getChoices().add(new ConsultationChoiceVote(consultationVote,choice));
			}
		}
		em.persist(consultationVote);
		return consultationVote;
	}
	
	
	
}
