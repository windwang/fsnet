package fr.univartois.ili.fsnet.admin.quartz;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.struts.util.MessageResources;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import fr.univartois.ili.fsnet.commons.mail.FSNetConfiguration;
import fr.univartois.ili.fsnet.commons.mail.FSNetMailer;
import fr.univartois.ili.fsnet.commons.mail.Mail;
import fr.univartois.ili.fsnet.commons.utils.DateUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.InteractionRoleFacade;
import fr.univartois.ili.fsnet.facade.MeetingFacade;

/**
 * Task scheduled which send emails to subscribers of coming events
 * 
 * @author FSNet
 *
 */
public class InformComingEventsJob implements Job {

	/**
	 * 
	 */
	public InformComingEventsJob() {

	}

	/**
	 * Fire when the jobs starts
	 * 
	 * 
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			sendMailForComingEvents();
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}
	}

	/**
	 * Send mail for each social entity having a coming event
	 * 
	 * @return
	 */
	private void sendMailForComingEvents() {
		HashMap<Meeting, Set<SocialEntity>> comingEvents = searchComingEvents();
		if (comingEvents != null) {
			for(Meeting meeting : comingEvents.keySet()) {
				for (SocialEntity socialEntity : (Set<SocialEntity>) comingEvents
						.get(meeting)) {
					sendInformationMail(meeting, socialEntity,
						comingEvents.get(meeting));
				}
			}
		}
	}

	/**
	 * Search coming events
	 * 
	 * @return all coming meetings with their subscribers
	 */
	private HashMap<Meeting, Set<SocialEntity>> searchComingEvents() {
		HashMap<Meeting, Set<SocialEntity>> meetingsWithTheirSubscribers = new
				HashMap<Meeting, Set<SocialEntity>>();
		
		EntityManager em = PersistenceProvider.createEntityManager();
		InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(em);
		em.getTransaction().begin();
		MeetingFacade meetingFacade = new MeetingFacade(em);
		
		for(Meeting m : meetingFacade.getMeetingsHavingRecallWhichOccurNow())
			meetingsWithTheirSubscribers.put(m,interactionRoleFacade.getSubscribers(m));
				
		if(meetingsWithTheirSubscribers.isEmpty())
			return null;
			
		return meetingsWithTheirSubscribers;
	}

	/**
	 * Send information mails
	 * 
	 * @param meeting
	 * @param socialEntity
	 * @param listSubscribes
	 */
	private void sendInformationMail(Meeting meeting,
			SocialEntity socialEntity, Set<SocialEntity> listSubscribes) {
		FSNetConfiguration conf = FSNetConfiguration.getInstance();
		String fsnetAddress = conf.getFSNetConfiguration().getProperty(
				FSNetConfiguration.FSNET_WEB_ADDRESS_KEY);
		String message = createPersonalizedMessage(meeting, socialEntity, fsnetAddress,
				listSubscribes);
		
		FSNetMailer mailer = FSNetMailer.getInstance();
		Mail mail = mailer.createMail();

		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");
		
		mail.setSubject(bundle.getMessage("events.mail.subject")
				+ ": " + meeting.getTitle()
				+ " - "
				+ DateUtils.renderDateForFullCalendar(meeting.getStartDate()));
		
		mail.addRecipient(socialEntity.getEmail());
		mail.setContent(message);
		mailer.sendMail(mail);
	}

	/**
	 * Creates an personalized message to inform of an coming event 
	 * 
	 * @param meeting
	 * @param entity
	 * @param fsnetAddress
	 * @param listSubscribes
	 * @return 
	 */
	private String createPersonalizedMessage(Meeting meeting, SocialEntity entity,
			String fsnetAddress, Set<SocialEntity> listSubscribes) {
		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");

		StringBuilder sb = new StringBuilder();

		sb.append(bundle.getMessage("events.mail.event") + " ");
		sb.append(meeting.getTitle() + " ");
		sb.append(bundle.getMessage("events.mail.subscribers") + " ");
		
		for (SocialEntity socialEntity : listSubscribes) {
			sb.append(socialEntity.getEmail() + ",");
		}
		
		sb.append(bundle.getMessage("events.to") + " ");
		sb.append(DateUtils.renderDateForFullCalendar(meeting.getStartDate()));
		sb.append(bundle.getMessage("events.at") + " ");
		sb.append(DateUtils.renderDateForFullCalendar(meeting.getEndDate()) + ".");
		sb.append("<\br>");
		sb.append(bundle.getMessage("events.mail.content") + " ");
		sb.append("\"" + meeting.getContent() + "\"");
		sb.append("<\br>");
		sb.append("<\br>");
		sb.append(bundle.getMessage("events.mail.fsnet") + " ");
		sb.append(fsnetAddress + ".");
		
		return sb.toString();
	}

}
