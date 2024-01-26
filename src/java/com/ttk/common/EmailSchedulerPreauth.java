/**
 * @ (#) EmailSchedulerPreauth.java April 21, 2014
 * Project      : Vidal HealthCare Services
 * File         : EmailSchedulerPreauth.java
 * Author       : Manjunath Reddy
 * Company      : RCS Technologies
 * Date Created : April 21, 2014
 *
 * @author       :  Manjunath Reddy
 * Modified by   :
 * Modified date :
 * Reason        : To run separate schedular only for preauth cases
 */

package com.ttk.common;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.common.exception.TTKException;


 public class EmailSchedulerPreauth implements Job {
	private static Logger log = Logger.getLogger( EmailScheduler.class );
	private static final String strEmailScheduler="EmailScheduler";
	CommunicationManager communicationManager = null;
	public void execute(JobExecutionContext arg0) {
		try
		{
			communicationManager = this.getCommunicationManagerObject();
			log.info("E-MAIL (1) : Inside execute() of EmailSchedulerPreauth");
			communicationManager.sendMessagePreauth("EMAIL");
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
			throw new TTKException(exp, strEmailScheduler);
		}//end of catch
		return communicationManager;
	}//end getCommunicationManagerObject()
}//end of EmailScheduler
