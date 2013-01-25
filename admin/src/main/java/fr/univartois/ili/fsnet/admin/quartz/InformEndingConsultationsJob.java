package fr.univartois.ili.fsnet.admin.quartz;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.apache.struts.util.MessageResources;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import fr.univartois.ili.fsnet.commons.mail.FSNetConfiguration;
import fr.univartois.ili.fsnet.commons.mail.FSNetMailer;
import fr.univartois.ili.fsnet.commons.mail.Mail;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Consultation;
import fr.univartois.ili.fsnet.entities.ConsultationChoice;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.ConsultationFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;

/**
 * Task scheduled which send emails to subscribers of ending consultations
 * 
 * @author FSNet
 *
 */
public class InformEndingConsultationsJob implements Job{

	
	/**
	 * Fire when the jobs starts
	 * 
	 * 
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			sendMailForEndingConsultations();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}
	}
	
	/**
	 * Send email for each social entity who didn't answer to ending consultations
	 * 
	 * @return
	 */
	private void sendMailForEndingConsultations() {
		Map<Consultation, Set<SocialEntity>> endingConsultations =
				searchEndingConsultations();
		if (endingConsultations != null) {
			for(Map.Entry<Consultation, Set<SocialEntity>> entry : endingConsultations.entrySet()) {
				for (SocialEntity socialEntity : entry.getValue()) {
					sendInformationMail(entry.getKey(), socialEntity);
				}
			}
		}
	}

	/**
	 * Search ending consultations
	 * 
	 * @return
	 */
	private Map<Consultation, Set<SocialEntity>> searchEndingConsultations() {
		HashMap<Consultation, Set<SocialEntity>> consultationsWithTheirSubscribersHavingNotVoted =
				new HashMap<Consultation, Set<SocialEntity>>();
		
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		SocialGroupFacade  socialGroup = new SocialGroupFacade(em);
		
		for(Consultation c : consultationFacade.getConsultationsWhichOccurToday()){
			Set<SocialEntity> listSocialEntity = new HashSet<SocialEntity>();
			for(SocialEntity se : socialGroup.getMembersFromGroup(c.getCreator().getGroup())){
				if(!c.isVoted(se)){
					listSocialEntity.add(se);
				}
			}
			if(!listSocialEntity.isEmpty()){
				consultationsWithTheirSubscribersHavingNotVoted.put(c,listSocialEntity);
			}
		}
		if(consultationsWithTheirSubscribersHavingNotVoted.isEmpty()){
			return null;
		}
		
		return consultationsWithTheirSubscribersHavingNotVoted;
	}

	/**
	 * Send information email
	 * 
	 * @param consultation
	 * @param socialEntity
	 */
	private void sendInformationMail(Consultation consultation,
			SocialEntity socialEntity) {
		FSNetConfiguration conf = FSNetConfiguration.getInstance();
		String fsnetAddress = conf.getFSNetConfiguration().getProperty(
				FSNetConfiguration.FSNET_WEB_ADDRESS_KEY);
		
		String message = createPersonalizedMessage(consultation, socialEntity, fsnetAddress);

		FSNetMailer mailer = FSNetMailer.getInstance();
		Mail mail = mailer.createMail();

		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");
		mail.setSubject(bundle.getMessage("consultations.mail.subject") + " : " +
				consultation.getTitle());
		
		mail.addRecipient(socialEntity.getEmail());
		mail.setContent(message);
		mailer.sendMail(mail);
	}

	/**
	 * Creates an personalized message to inform of an ending consultation
	 * 
	 * @param consultation
	 * @param socialEntity
	 * @param String
	 */
	private String createPersonalizedMessage(Consultation consultation, SocialEntity entity,
			String fsnetAddress) {
		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");
		StringBuilder sb = new StringBuilder();
		String br = "<br\\>\n<br\\>";

		sb.append(bundle.getMessage("consultations.mail.deadline"));
		sb.append(br);
		sb.append(bundle.getMessage("consultations.mail.choices") + ":");
		sb.append("<ol>");
		
		for(ConsultationChoice choice : consultation.getChoices()){
			sb.append("<li>" + choice.getIntituled() + "</li>");
		}
		
		sb.append("</ol>");
		sb.append(br);
		
		sb.append(bundle.getMessage("consultations.mail.fsnet") + " ");
		sb.append(fsnetAddress + ".");
		
		return sb.toString();
	}
}
