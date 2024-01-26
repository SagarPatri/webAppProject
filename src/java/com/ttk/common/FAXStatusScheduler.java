/**
 * @ (#) FAXStatusScheduler.java Jan 25, 2008
 * Project      : TTK HealthCare Services
 * File         : FAXStatusScheduler.java
 * Author       : Balakrishna E
 * Company      : Span Systems Corporation
 * Date Created : Jan 25, 2008
 *
 * @author       :  Balakrishna E
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import com.ttk.common.messageservices.FaxStatusHelper;

public class FAXStatusScheduler implements Job {	
	private static Logger log = Logger.getLogger( FAXStatusScheduler.class );
	FaxStatusHelper faxStatusHelper = null;
	public void execute(JobExecutionContext arg0) {
		try
		{
			faxStatusHelper = new FaxStatusHelper();
			faxStatusHelper.faxStatus();
			log.info("Inside run method in FAXStatusScheduler");				
		}catch(Exception e){
			//Thread.sleep(10000);
			TTKCommon.logStackTrace(e);
		}//end of catch(Exception e)
	}//end of execute(JobExecutionContext arg0)
}//end of FAXStatusScheduler
