/*package com.ttk.common;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ttk.business.maintenance.MaintenanceManager;
import com.ttk.common.exception.TTKException;

public class DHPORemittanceAdvice  implements Job {

	
	private static final String strDHPOVHNewTransactionError="DHPORemittanceAdvice";
	private static final Logger log = Logger.getLogger( DHPORemittanceAdvice.class );
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		MaintenanceManager maintenanceManager = null;
     
		try
		{
			
			System.out.println(" HI I am a DHPORemittanceAdvice class ");
		   maintenanceManager = this.getMaintenanceManagerObject();
		  boolean flag =	maintenanceManager.remittanceAdviceFileDetail();
		   System.out.println("test2  flag ==>"+flag);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	private MaintenanceManager getMaintenanceManagerObject() throws TTKException
	{
		MaintenanceManager maintenanceManager = null;
		try
		{
			if(maintenanceManager == null)
			{
				InitialContext ctx = new InitialContext();
				maintenanceManager = (MaintenanceManager) ctx.lookup("java:global/TTKServices/business.ejb3/MaintenanceManagerBean!com.ttk.business.maintenance.MaintenanceManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strDHPOVHNewTransactionError);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()	
	

}
*/