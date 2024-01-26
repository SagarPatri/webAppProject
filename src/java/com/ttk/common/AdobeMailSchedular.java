package com.ttk.common;

import java.util.ArrayList;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ttk.business.claims.ClaimIntimationSmsManager;
import com.ttk.common.exception.TTKException;
//1274 A 
public class AdobeMailSchedular implements Job {
	private static Logger log = Logger.getLogger( AdobeMailSchedular.class );
	private static final String AdobeFormMailAttachement="AdobeAttachment";
	AdobeMailResponseReader adobeMailResponseReader=null;
	ClaimIntimationSmsManager claimIntimationSmsManager = null;
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			log.info("Inside run method in AdobeMailSchedular");
			adobeMailResponseReader=new AdobeMailResponseReader();
			claimIntimationSmsManager = this.getClaimIntimationSmsManagerObject();
			adobeMailResponseReader.readMailAdobeAttachment();
				
		}catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (TTKException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ClaimIntimationSmsManager getClaimIntimationSmsManagerObject() throws TTKException
	{
		ClaimIntimationSmsManager claimIntimationSmsManager = null;
		try
		{
			if(claimIntimationSmsManager == null)
			{
				InitialContext ctx = new InitialContext();
				//claimIntimationSmsManager = (ClaimIntimationSmsManager) ctx.lookup(ClaimIntimationSmsManager.class.getName());
				claimIntimationSmsManager = (ClaimIntimationSmsManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimIntimationSmsManagerBean!com.ttk.business.claims.ClaimIntimationSmsManager");

			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp,AdobeFormMailAttachement );
		}//end of catch
		return claimIntimationSmsManager;
	}//end getCommunicationManagerObject()
	

}
