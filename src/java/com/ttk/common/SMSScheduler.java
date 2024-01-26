/**
 * @ (#) SMSScheduler.java Aug 9, 2007
 * Project 	     : TTK HealthCare Services
 * File          : SMSScheduler.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Aug 9, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.common;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.common.exception.TTKException;

public class SMSScheduler implements Job {
	private static Logger log = Logger.getLogger( SMSScheduler.class );
	private static final String strSMSScheduler="SMSScheduler";
	CommunicationManager communicationManager = null;
	public void execute(JobExecutionContext arg0) {
		try
		{
			communicationManager = this.getCommunicationManagerObject();
			log.info("Inside run method in SMSScheduler");
			communicationManager.sendSMS("SMS");
		}catch(Exception e){
			//Thread.sleep(10000);
			TTKCommon.logStackTrace(e);
		}//end of catch(Exception e)
	}
	
	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
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
			throw new TTKException(exp, strSMSScheduler);
		}//end of catch
		return communicationManager;
	}//end getCommunicationManagerObject()

}//end of SMSScheduler
