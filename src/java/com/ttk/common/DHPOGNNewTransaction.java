
/**
 * @ (#) DHPOGNNewTransaction.java Feb 27 2017
 * Project      : TTK HealthCare Services
 * File         : DHPOGNNewTransaction.java
 * Author       : Nagababu Kamadi
 * Company      : RCS TEchnologies
 * Date Created : Feb 27 2017
 *
 * @author       : Nagababu Kamadi
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class DHPOGNNewTransaction implements Job {
	
	private static final String strDHPOGNNewTransactionError="DHPOGNNewTransaction";
	private static final Logger log = Logger.getLogger( DHPOGNNewTransaction.class );
	public void execute(JobExecutionContext arg0) {	
		try
		{	
		if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.WS.GN.NT.RUN.MODE"))){
		//	System.out.println("*DHPO* Schedular Started........");
			if(!DhpoDownloadFiles.isTaskExcecuting()){
				System.out.println("*DHPO* Task is executing........");
				DhpoDownloadFiles.excecuteTask();
			}else{
				System.out.println("*DHPO* Previous Task Not Completed");
			}
			
		}
		}catch(Exception e){
			DhpoDownloadFiles.setTaskExcecuteStatus(false);
			e.printStackTrace();
		}//end of catch(Exception e)		
	}//end of run method
	
	
}//end of DHPOGNNewTransaction
