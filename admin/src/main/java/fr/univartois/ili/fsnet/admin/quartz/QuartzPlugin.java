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

/**
 * Plugin quartz : task scheduler
 * 
 * @author FSNet
 * 
 */
public class QuartzPlugin implements PlugIn {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.PlugIn#destroy()
	 */
	@Override
	public void destroy() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet
	 * , org.apache.struts.config.ModuleConfig)
	 */
	@Override
	public void init(ActionServlet arg0, ModuleConfig arg1)
			throws ServletException {
		try {
			// Grab the Scheduler instance from the Factory 
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			
			// and start it off
			scheduler.start();
			
			 // define the job and tie it to PreventIncomingEventsJob class
			JobDetail job = newJob(PreventIncomingEventsJob.class)
		           .withIdentity("PreventIncomingEventsJob","group 1")
		           .build();

			// Trigger the job to repeat every minutes
			Trigger trigger =  newTrigger()
					.withIdentity("PreventIncomingEventsTrigger", "group1")
					.startNow()
					.withSchedule(simpleSchedule()
					        .withIntervalInMinutes(1)
					        .repeatForever())
				    .build();
			
			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);

		} catch (SchedulerException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}

	}
}
