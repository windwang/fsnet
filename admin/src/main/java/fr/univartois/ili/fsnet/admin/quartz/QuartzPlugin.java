package fr.univartois.ili.fsnet.admin.quartz;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;


/**
 * Plugin quartz : task scheduler
 * 
 * @author FSNet
 * 
 */
public class QuartzPlugin implements PlugIn {
	
	private static final int INFORM_ENDING_CONSULTATION_HOUR = 0 ;
	private static final int INFORM_ENDING_CONSULTATION_MINUTE = 30 ;

	/*
	 * Destroy quartz plugin
	 * 
	 * 
	 */
	@Override
	public void destroy() {
	}

	/*
	 * Initialize quartz plugin
	 * 
	 */
	@Override
	public void init(ActionServlet arg0, ModuleConfig arg1)
			throws ServletException {
		try {
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();

			// define the job and tie it to InformComingEventsJob class
			JobDetail informComingEventsJob = newJob(InformComingEventsJob.class)
					.withIdentity("InformComingEventsJob", "group 1")
					.build();

			// Trigger the job to repeat every minutes
			Trigger informComingEventsTrigger = newTrigger()
					.withIdentity("InformComingEventsJobTrigger", "group1")
					.withSchedule(
							simpleSchedule().withIntervalInMinutes(1)
									.repeatForever()).build();

			// define the job and tie it to InformEndingConsultationJob class
			JobDetail informEndingConsultationsJob = newJob(InformEndingConsultationsJob.class)
					.withIdentity("InformEndingConsultationJob", "group 1")
					.build();

			// Trigger the job daily at 00h30
			Trigger informEndingConsultationsTrigger = newTrigger()
					.withIdentity("InformEndingConsultationsTrigger", "group1")
					.withSchedule(dailyAtHourAndMinute(INFORM_ENDING_CONSULTATION_HOUR,
							INFORM_ENDING_CONSULTATION_MINUTE))
					.withPriority(10)
					.build();
			
			
			// Tell quartz to schedule the job inform coming events using our trigger
			scheduler.scheduleJob(informComingEventsJob, informComingEventsTrigger);
			
			// Tell quartz to schedule the job inform ending consultations using our trigger
			scheduler.scheduleJob(informEndingConsultationsJob, informEndingConsultationsTrigger);

			// Starts the Scheduler's threads that fire Triggers
			scheduler.start();

		} catch (SchedulerException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}

	}
}
