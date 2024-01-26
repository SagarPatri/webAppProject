/**
 * @ (#) EmailMDB.java 17th Feb 2006
 * Project      : TTK HealthCare Services
 * File         : EmailMDB.java
 * Author       : Balakrishna.E 
 * Company      : Span Systems Corporation
 * Date Created : 17th Feb 2006  
 *
 * @author       : 
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.common.messageservices;

import javax.ejb.*;
import javax.jms.*;
import org.apache.log4j.Logger;
import com.ttk.common.TTKCommon;

/*@MessageDriven(activateConfig ={@ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
		@ActivationConfigProperty(propertyName="destination", propertyValue="queue/A")
})*/

//added for jboss upgradation
@MessageDriven(
		activationConfig={
				@javax.ejb.ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"), 
				@javax.ejb.ActivationConfigProperty(propertyName="destination", propertyValue="queue/A")
				}
		)
//added for jboss upgradation

public class EmailMDB implements MessageListener 
{
	private static Logger log = Logger.getLogger( EmailMDB.class );
	
	// Method from MessageListener interface
	// Take in a MapMessage and use the EmailHelper to send out an email message
	/**
	 * This method sends the email message
	 * @param message the Message object which contains the email information
	 */
	public void onMessage(Message message) 
	{
		MapMessage mapMessage = (MapMessage) message;
		
		try {
			log.debug("Sending email: EmailHelper.sendMessage(mapMessage)");
			EmailHelper objEmailHelper = new EmailHelper();
			objEmailHelper.sendMessage(mapMessage);
		}//end of try 
		catch(Exception e) {
			TTKCommon.logStackTrace(e);
		}//End of catch(Exception e)
	}//End of onMessage(Message message)
}//End of class EmailMDB
