/**
 * @ (#) FaxMDB.java 2nd May 2007
 * Project      : TTK HealthCare Services
 * File         : FaxMDB.java
 * Author       : Balakrishna.E 
 * Company      : Span Systems Corporation
 * Date Created : 2nd May 2007  
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
		@ActivationConfigProperty(propertyName="destination", propertyValue="queue/C")
})*/

//added for jboss upgradation
@MessageDriven(
		activationConfig={
				@javax.ejb.ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"), 
				@javax.ejb.ActivationConfigProperty(propertyName="destination", propertyValue="queue/C")
				}
		)
//added for jboss upgradation

public class FaxMDB implements MessageListener 
{
	private static Logger log = Logger.getLogger( FaxMDB.class );
	
	// Method from MessageListener interface
	// Take in a MapMessage and use the FaxHelper to send out an Fax message
	/**
	 * This method sends the Fax message
	 * @param message the Message object which contains the Fax information
	 */
	public void onMessage(Message message) 
	{
		MapMessage mapMessage = (MapMessage) message;
		
		try {
			log.debug("Sending Fax: FaxHelper.sendFax(mapMessage)");
			FAXHelper objFaxHelper = new FAXHelper();
			objFaxHelper.sendFax(mapMessage);
		}//end of try 
		catch(Exception e) {
			TTKCommon.logStackTrace(e);
		}//End of catch(Exception e)
	}//End of onMessage(Message message)
}//End of class FaxMDB