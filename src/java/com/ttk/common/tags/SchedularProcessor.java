package com.ttk.common.tags;

import java.text.ParseException;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import com.ttk.business.webservice.UploadEnrollDataAction;
import com.ttk.common.TTKPropertiesReader;

import com.ttk.common.EmailSchedulerPreauth;
import com.ttk.common.SMSSchedulerPreauth;

public class SchedularProcessor {
	
	private static final Logger log = Logger.getLogger( SchedularProcessor.class );
	public void process() throws SchedulerException, ParseException
	{
	log.info("Schedular started");    
		try{
			System.out.println("=========================  SchedularProcessor Started  ================================");
			
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		/*		
			//CMA API  member data upload
			JobDetail jobdhpo=new JobDetail("oman",scheduler.DEFAULT_GROUP,UploadEnrollDataAction.class);
			String strOmanMemberUpload	 = TTKPropertiesReader.getPropertyValue("OMANMemberUpload");
			CronTrigger cronOmanMemberUpload=new CronTrigger("omantrns","omantrans", strOmanMemberUpload);
			scheduler.scheduleJob(jobdhpo,cronOmanMemberUpload);

		
			// PRE AUTH EMAIL  
            JobDetail jobDetailEmailPreauth = new JobDetail("EmailJOBPREAUTH", Scheduler.DEFAULT_GROUP, EmailSchedulerPreauth.class);
            String strEmailCronStringPreauth = TTKPropertiesReader.getPropertyValue("EmailCronStringPreauth");//only preauthSchedular
            CronTrigger cronTrigEmailPreauth = new CronTrigger("crontriggeremailPreauth", "crongroupemailPreauth", strEmailCronStringPreauth);//only preauthSchedular
            scheduler.scheduleJob(jobDetailEmailPreauth,cronTrigEmailPreauth); 

            // PRE AUTH SMS
            JobDetail jobDetailSMSPreauth = new JobDetail("SMSJOBPREAUTH", Scheduler.DEFAULT_GROUP, SMSSchedulerPreauth.class);
            String strSMSCronStringPreauth = TTKPropertiesReader.getPropertyValue("SMSCronStringPreauth");//only preauthSchedular
            CronTrigger cronTrigSMSPreauth = new CronTrigger("crontriggerSMSPreauth", "crongroupSMSPreauth", strSMSCronStringPreauth);//only preauthSchedular
            scheduler.scheduleJob(jobDetailSMSPreauth,cronTrigSMSPreauth);
		*/
			
			System.out.println("=========================  SchedularProcessor Ended  ================================");

			
			// Start the Scheduler running
			scheduler.start();
			log.info("done");   
			}//end of try
		catch(SchedulerException sExp)
		{
			sExp.printStackTrace();
		}//end of catch(SchedulerException sExp)
		/*catch(ParseException pExp)
		{
			pExp.printStackTrace();
		}*///end of catch(ParseException pExp)
		catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch(ParseException exp)
	}//end of process()
}//end of SchedularProcessor
