/**
 * @ (#) FAXScheduler.java Nov 20, 2006
 * Project      : TTK HealthCare Services
 * File         : FAXScheduler.java
 * Author       : Balakrishna E
 * Company      : Span Systems Corporation
 * Date Created : Nov 20, 2006
 *
 * @author       :  Balakrishna E
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

public class FAXScheduler implements Job {
	private static Logger log = Logger.getLogger( FAXScheduler.class );
	private static final String strFAXScheduler="FAXScheduler";
	CommunicationManager communicationManager = null;
	public void execute(JobExecutionContext arg0) {
		try
		{
			communicationManager = this.getCommunicationManagerObject();
			log.info("Inside run method in FAXScheduler");
			communicationManager.sendFax("FAX");
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
			throw new TTKException(exp, strFAXScheduler);
		}//end of catch
		return communicationManager;
	}//end getCommunicationManagerObject()
}//end of FAXScheduler
