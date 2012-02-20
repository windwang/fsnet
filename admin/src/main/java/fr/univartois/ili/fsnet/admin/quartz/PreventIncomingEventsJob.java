package fr.univartois.ili.fsnet.admin.quartz;

import java.util.HashMap;
import java.util.Iterator;
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
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;

/**
 * Task scheduled which send email to subscribers of an upcoming events
 * 
 * @author FSNet
 *
 */
public class PreventIncomingEventsJob implements Job {

	/**
	 * 
	 */
	public PreventIncomingEventsJob() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			preventIncomingEvents();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private void preventIncomingEvents() {
		HashMap<Meeting, Set<SocialEntity>> incomingEvents = searchIncomingEvents();
		if (incomingEvents != null) {
			Iterator<Meeting> iterator = incomingEvents.keySet().iterator();
			while (iterator.hasNext()) {
				Meeting meeting = (Meeting) iterator.next();
				for (SocialEntity socialEntity : (Set<SocialEntity>) incomingEvents
						.get(meeting)) {
					sendPreventIncomingEventMail(meeting, socialEntity,
							incomingEvents.get(meeting));
				}
			}
		}
	}

	/**
	 * @return
	 */
	private HashMap<Meeting, Set<SocialEntity>> searchIncomingEvents() {
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		return socialEntityFacade.getSocialEntityHavingEvent();
	}

	/**
	 * @param meeting
	 * @param socialEntity
	 * @param listSubscribes
	 */
	private void sendPreventIncomingEventMail(Meeting meeting,
			SocialEntity socialEntity, Set<SocialEntity> listSubscribes) {
		FSNetConfiguration conf = FSNetConfiguration.getInstance();
		String fsnetAddress = conf.getFSNetConfiguration().getProperty(
				FSNetConfiguration.FSNET_WEB_ADDRESS_KEY);
		String message;

		message = createMessage(meeting, socialEntity, fsnetAddress,
				listSubscribes);

		// send a mail
		FSNetMailer mailer = FSNetMailer.getInstance();
		Mail mail = mailer.createMail();

		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");

		mail.setSubject(bundle.getMessage("events.recallMail.subject.recall")
				+ " - " + meeting.getTitle() + " "
				+ bundle.getMessage("events.recallMail.subject.today"));

		mail.addRecipient(socialEntity.getEmail());
		mail.setContent(message);
		mailer.sendMail(mail);
	}

	/**
	 * @param meeting
	 * @param entity
	 * @param fsnetAddress
	 * @param listSubscribes
	 * @return
	 */
	private String createMessage(Meeting meeting, SocialEntity entity,
			String fsnetAddress, Set<SocialEntity> listSubscribes) {
		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");

		String message = "<h1>" + bundle.getMessage("events.recallMail.title")
				+ "</h1>";
		message += "<p>" + meeting.getTitle() + "</p>";
		message += "<ul>";
		message += "<li>" + bundle.getMessage("events.recallMail.startDate")
				+ ": " + DateUtils.renderDBDate(meeting.getStartDate())
				+ "</li>";
		message += "<li>" + bundle.getMessage("events.recallMail.endDate")
				+ ": " + DateUtils.renderDBDate(meeting.getEndDate()) + "</li>";
		message += "<li>" + bundle.getMessage("events.recallMail.subscriber")
				+ ": ";

		for (SocialEntity socialEntity : listSubscribes) {
			message += " " + socialEntity.getEmail();
		}

		message += "</li>";
		message += "<li>" + bundle.getMessage("events.recallMail.fsnet") + ": "
				+ fsnetAddress + "</li>";
		message += "</ul>";

		message += meeting.getContent();
		return message;
	}

}
