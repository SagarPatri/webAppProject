
/**
 * @ (#) Health4SureServices.java Jun 14, 2006
 * Project       : TTK HealthCare Services
 * File          : Health4SureServices.java
 * Author        : Krishna K H
 * Company       : Span Systems Corporation
 * Date Created  : Jun 14, 2006
 *
 * @author       :  Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.webservices;

import org.apache.axis.AxisFault;
import javax.naming.InitialContext;
import com.ttk.business.webservice.WebServiceManager;
import com.ttk.common.exception.TTKException;
import com.ttk.common.webservices.helper.ValidatorResourceHelper;


public class Health4SureServices
{
    
    
    
    /**
     * This method returns the Name once mobile number is validated.
     * @param String object which contains the Name.
     * @return String object which contains mobile number.
     * @exception throws TTKException.
     */
    public String getValidatePhoneNumber(String strValue) throws org.apache.axis.AxisFault
    {
        String strResult="";
        try
        {
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.getValidatePhoneNumber(strValue);
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of getValidataPhoneNumber(String strValue)

    
    /**
     * This method returns the Name once mobile number is validated.
     * @param String object which contains the Name.
     * @return String object which contains mobile number.
     * @exception throws TTKException.
     */
    public String getProviderList(String genTypeId, String hospName, String StateTypeID, String cityDesc, String location) throws org.apache.axis.AxisFault
    {
        String strResult="";
        try
        {
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.getProviderList(genTypeId,hospName,StateTypeID,cityDesc,location);
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of getProviderList(String strValue)
    
    
    /**
     * This method returns the Name once mobile number is validated.
     * @param String object which contains the Name.
     * @return String object which contains mobile number.
     * @exception throws TTKException.
     */
    public String getDiagTests(Long hospSeqId) throws org.apache.axis.AxisFault
    {
        String strResult="";
        try
        {
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.getDiagTests(hospSeqId);
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of getProviderList(String strValue)

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
}//end of Health4SureServices