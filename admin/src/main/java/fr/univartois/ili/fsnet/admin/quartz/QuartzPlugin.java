package fr.univartois.ili.fsnet.admin.quartz;


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
 */

public class QuartzPlugin implements PlugIn{
	

	@Override
	public void destroy() {
	}

	@Override
	public void init(ActionServlet arg0, ModuleConfig arg1)
			throws ServletException {
		try{
			JobDetail job = newJob(PreventIncomingEventsJob.class)
		           .withIdentity("PreventIncomingEventsJob","group 1")
		           .build();
			
			

			Trigger trigger =  newTrigger()
					.withIdentity("PreventIncomingEventsTrigger", "group1")
					.withSchedule(simpleSchedule()
					        .withIntervalInMinutes(1)
					        .repeatForever())
				    .build();
				   
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		 
		}catch(SchedulerException e){
			e.printStackTrace();
		}
		
	}
}