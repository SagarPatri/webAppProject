package com.ttk.common;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.common.exception.TTKException;

public class PaymentMailToProvider implements Job{

	
	private static Logger log = Logger.getLogger( PaymentMailToProvider.class );
	private static final String strEmailScheduler="PaymentMailToProvider";
	CommunicationManager communicationManager = null;
	
	
	public void execute(JobExecutionContext arg0)  {
		try
		{
			communicationManager = this.getCommunicationManagerObject();
			log.info("Inside1 run method in EmailScheduler");
			communicationManager.sendPaymentMail("EMAIL");
		}catch(Exception e){
			//Thread.sleep(10000);
			TTKCommon.logStackTrace(e);
		
	}

}

	private CommunicationManager getCommunicationManagerObject() throws TTKException
	{
		CommunicationManager communicationManager = null;
		try
		{
			if(communicationManager == null)
			{
				InitialContext ctx = new InitialContext();
				communicationManager = (CommunicationManager) ctx.lookup("java:global/TTKServices/business.ejb3/CommunicationManagerBean!com.ttk.business.common.messageservices.CommunicationManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strEmailScheduler);
		}//end of catch
		return communicationManager;
	}//end getCommunicationManagerObject()

	
	
}