package fr.univartois.ili.fsnet.facade;


import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.Consultation;
import fr.univartois.ili.fsnet.entities.ConsultationChoice;
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
	
}
