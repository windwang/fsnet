package fr.univartois.ili.fsnet.admin.quartz;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;

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
			JobDetail job = newJob(PreventIncomingEventsJob.class)
					.withIdentity("myJob", "group 1").build();

			// activate the job every day at 0:30
			CronTrigger trigger = newTrigger()
					.withIdentity("trigger", "group1")
					.withSchedule(dailyAtHourAndMinute(0, 30)).build();

			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);

		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}
}