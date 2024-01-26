/**
 * @ (#) RuleEngineServlet.java Dec 24, 2007
 * Project 	     : TTK HealthCare Services
 * File          : RuleEngineServlet.java
 * Author        : Arun K N
 * Company       : Span Systems Corporation
 * Date Created  : Dec 24, 2007
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ttk.common.security.RuleXMLHelper;

public class RuleEngineServlet extends HttpServlet {
	
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger( RuleEngineServlet.class );
	RuleXMLHelper ruleXMLHelper = null;
	
	public void init(ServletConfig config) 
	{
		try
		{
			log.info("Inside RuleEngineServlet....");
			ruleXMLHelper=new RuleXMLHelper();
			HashMap hmDisplayNodes=null;
			HashMap hmCopayResultNodes=null;
			//load the display nodes.
			hmDisplayNodes=ruleXMLHelper.loadDisplayNodes(TTKCommon.getDocument("MasterBaseRules.xml"));
			hmCopayResultNodes=ruleXMLHelper.loadCopayResultNodes(TTKCommon.getDocument("MasterBaseRules.xml"));
			config.getServletContext().setAttribute("RULE_DISPLAY_NODES",hmDisplayNodes);
			config.getServletContext().setAttribute("RULE_COPAY_RESULT_NODES",hmCopayResultNodes);
		}//end of try
		catch (Exception e) {
			log.info("Scheduler failed to start cleanly: " + e.toString());
			e.printStackTrace();
		}//end of catch (Exception e)
	}//end of init()
	
	public void service(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException
	{
		log.info("Inside scheduler service method");
	}//end of service(HttpServletRequest req, HttpServletResponse res)
	
	public void destroy() {		
		try {
			if(ruleXMLHelper != null)
			{
				ruleXMLHelper = null;
			}//end of if(schedularServlet != null)
		}//end of try
		catch (Exception e) {
			log.info("Scheduler failed to shutdown cleanly: " + e.toString());
			e.printStackTrace();
		}//end of catch (Exception e)
		finally
		{
			ruleXMLHelper = null;
		}//end of finally
		log.info("Scheduler successful shutdown.");
	}//end of destroy()
}//end of RuleEngineServlet
