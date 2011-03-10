package fr.univartois.ili.fsnet.facade;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Consultation;
import fr.univartois.ili.fsnet.entities.ConsultationChoice;
import fr.univartois.ili.fsnet.entities.ConsultationChoiceVote;
import fr.univartois.ili.fsnet.entities.ConsultationVote;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;

public class ConsultationFacade {
	private final EntityManager em;
	
	public ConsultationFacade(EntityManager em) {
		this.em = em;
	}
	
	public final Consultation createConsultation(SocialEntity creator, String title, String description, String [] choices, Consultation.TypeConsultation type){
		Consultation consultation = new Consultation(creator, title, description, type);
		for (String s : choices){
			consultation.addChoice(new ConsultationChoice(consultation,s));
		}
		em.persist(consultation);
		return consultation;
	}
	
	public final Consultation getConsultation(int consultationId) {
		Consultation cons = em.find(Consultation.class, consultationId);
        return cons;
    }
	
	public ConsultationVote getVote(int voteId) {
		return em.find(ConsultationVote.class, voteId);
	}
	
	public void deleteVote(Consultation consultation, SocialEntity entity,ConsultationVote vote){
		if(vote != null && entity != null) {
			if(!vote.getVoter().equals(entity)){
				throw new UnauthorizedOperationException("exception.message");
			}else{
				consultation.getConsultationVotes().remove(vote);
				entity.getVotes().remove(vote);
				deleteVote(vote);
			}
		}
	}

	public void deleteVote(ConsultationVote vote){
		if(vote== null) {
			throw new IllegalArgumentException();
		}
		em.remove(vote);
	}
	
	public List<Consultation> getUserConsultations(SocialEntity member) {
		if (member == null) {
            throw new IllegalArgumentException();
        }
        TypedQuery<Consultation> query = em.createQuery("SELECT c FROM Consultation c WHERE c.creator = :member ORDER BY c.creationDate DESC", Consultation.class);
        query.setParameter("member", member);
        return query.getResultList();
	}
	
	public final ConsultationVote voteForConsultation(SocialEntity voter, Consultation consultation, String comment,String other,List<String> choices){
		ConsultationVote consultationVote = new ConsultationVote(voter,comment,other);
		voter.getVotes().add(consultationVote);
		if(consultation != null){
			consultationVote.setConsultation(consultation);
			consultation.getConsultationVotes().add(consultationVote);
		}
		if (consultation.getType() != Consultation.TypeConsultation.YES_NO_IFNECESSARY)
			for(ConsultationChoice choice : consultation.getChoices()){
				if(choices.contains(String.valueOf(choice.getId()))){
					consultationVote.getChoices().add(new ConsultationChoiceVote(consultationVote,choice));
				}
			}
		else {
			for (String choice : choices){
				if (!choice.startsWith("no")){
					boolean ifNecessary;
					Integer id;
					if (choice.startsWith("ifNecessary")){
						id = Integer.valueOf(choice.replaceAll("ifNecessary", ""));
						ifNecessary = true;
					}
					else {
						id = Integer.valueOf(choice.replaceAll("yes", ""));
						ifNecessary = false;
					}
					for(ConsultationChoice choiceCons : consultation.getChoices()){
						if (choiceCons.getId() == id){
							ConsultationChoiceVote vote = new ConsultationChoiceVote(consultationVote, choiceCons);
							if (ifNecessary)
								vote.setIfNecessary(true);
							consultationVote.getChoices().add(vote);
						}
					}
				}
			}
		}
		em.persist(consultationVote);
		return consultationVote;
	}

	public void deleteConsultation(Consultation consultation,
			SocialEntity member) {
		if(consultation != null && member != null){
			member.getInteractions().remove(consultation);
			em.remove(consultation);
		}
		
	}

	public void closeConsultation(Consultation consultation) {
		if (consultation != null){
			consultation.setOpened(false);
			em.merge(consultation);
		}
	}

	public void openConsultation(Consultation consultation) {
		if (consultation != null){
			consultation.setOpened(true);
			em.merge(consultation);
		}
	}

	public List<Consultation> getConsultationsContaining(String searchText) {
		TypedQuery<Consultation> query = em.createQuery("SELECT consultation FROM Consultation consultation WHERE consultation.title LIKE :pattern ORDER BY consultation.creationDate DESC", Consultation.class);
        query.setParameter("pattern", "%" + searchText + "%");
        return query.getResultList();
	}

	public List<String> getOtherChoice(int idConsultation, String voteOther) {
		TypedQuery<String> query = em.createQuery("SELECT DISTINCT vote.other FROM ConsultationVote vote WHERE vote.consultation.id = :idConsultation AND vote.other LIKE :pattern ORDER BY other", String.class);
        query.setParameter("pattern", "%" + voteOther + "%");
        query.setParameter("idConsultation", idConsultation);
        return query.getResultList();
	}
	
	
}
