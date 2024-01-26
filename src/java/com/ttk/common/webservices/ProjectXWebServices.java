
/**
 * @ (#) WebServices.java Jun 14, 2006
 * Project       : TTK HealthCare Services
 * File          : WebServices.java
 * Author        : Kishor kumar S H
 * Company       : Span Systems Corporation
 * Date Created  : 12th Dec 2014
 *
 * @author       :  Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.webservices;

import javax.naming.InitialContext;

import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;

import com.ttk.business.webservice.WebServiceManager;
import com.ttk.common.exception.TTKException;
import com.ttk.common.webservices.helper.ValidatorResourceHelper;
//import org.dom4j.Document;

public class ProjectXWebServices
{
	private static Logger log = Logger.getLogger(ProjectXWebServices.class);
	
	
	/**
     * This method method returns the String 
     * @param String object which contains the strVidalID for which data in is required.
     * @param String object which contains thestrIMEINumber for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    
    public String getXmlandSaveXml(String strInsID) throws org.apache.axis.AxisFault{
    	String strResult="";
    	try
        {
    		log.info("INSIDE LOGIN PAGE ACTION START");
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.getXmlandSaveXml(strInsID);
            log.info("INSIDE LOGIN PAGE ACTION START");
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of individualLoginService(String strVidalID,StrIMEINumber)
    
    
    /**
     * Returns the WebServiceManager session object for invoking methods on it.
     * @return webServiceManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private WebServiceManager getWebServiceManagerObject() throws TTKException
    {
        WebServiceManager webServiceManager = null;
        try
        {
            if(webServiceManager == null)
            {
                InitialContext ctx = new InitialContext();
                webServiceManager = (WebServiceManager) ctx.lookup("java:global/TTKServices/business.ejb3/WebServiceManagerBean!com.ttk.business.webservice.WebServiceManager");
            }//end if(webServiceManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "webservice");
        }//end of catch
        return webServiceManager;
    }//end getWebServiceManagerObject()

  
   
}//end of WebServices