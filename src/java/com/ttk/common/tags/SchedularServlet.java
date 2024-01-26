/**
 * @ (#) SchedularServlet.java Oct 30, 2007
 * Project       : TTK HealthCare Services
 * File          : SchedularServlet.java
 * Author        : Balakrishna Erram
 * Company       : Span Systems Corporation
 * Date Created  : Oct 30, 2007
 *
 * @author       : Balakrishna Erram
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ttk.common.exception.TTKException;

public class SchedularServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2002240732929337472L;
	private static Logger log = Logger.getLogger( SchedularServlet.class );
//	Exception Message Identifier
    private static final String strScheduleError="Schedular";
    
	SchedularServlet schedularServlet = null;
	SchedularProcessor schedularprocessor=null;
	public void init(ServletConfig config) 
	{
		try{
			schedularprocessor=new SchedularProcessor();         
			schedularprocessor.process();
		}catch(Exception exp)
		{
			new TTKException(exp,strScheduleError);
		}
	}//end of init()
	
	public void service(HttpServletRequest req, HttpServletResponse res)
	{
	}//end of service(HttpServletRequest req, HttpServletResponse res)
	
	public void destroy() {		
		try {
			if(schedularServlet != null)
			{
				schedularServlet = null;
			}//end of if(schedularServlet != null)
		}//end of try
		catch (Exception exp) {
			new TTKException(exp,strScheduleError);
		}//end of catch (Exception e)		
		log.info("Scheduler successful shutdown.");
	}//end of destroy()
}//end of SchedularServlet