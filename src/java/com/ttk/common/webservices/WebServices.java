/**
 * @ (#) WebServices.java Jun 14, 2006
 * Project       : TTK HealthCare Services
 * File          : WebServices.java
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


public class WebServices
{
    /**
     * This method saves the Policy information.
     * @param String object which contains the policy and member details in XML format.
     * @return String object which contains Policy information.
     * @exception throws TTKException.
     */
    /*public String savePolicy(String strValue) throws org.apache.axis.AxisFault
    {
        String strResult="";
        try
        {
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.savePolicy(strValue);
        }//end of try
        catch(Exception exp)
        {
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of savePolicy(String strValue)
*/
    /**
     * This method saves the Policy information.
     * @param String object which contains the policy and member details in XML format.
     * @return String object which contains Policy information.
     * @exception throws TTKException.
     */
    public String savePolicy(String strValue,String strCompAbbr) throws org.apache.axis.AxisFault
    {
        String strResult="";
        try
        {
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.savePolicy(strValue,strCompAbbr);
        }//end of try
        catch(Exception exp)
        {
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of savePolicy(String strValue)

    /**
     * This method returns the consolidated list of Policy Number.
     * @param String object which contains the parameter in XML format.
     * @return String object which contains Policy Numbers.
     * @exception throws TTKException.
     */
    public String getConsolidatedPolicyList(String strFromDate,String strToDate,long lngInsSeqID,long lngProductSeqID,long lngOfficeSeqID,String strEnrTypeID) throws org.apache.axis.AxisFault
    {
        String strResult="";
        try
        {
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.getConsolidatedPolicyList(strFromDate,strToDate,lngInsSeqID,lngProductSeqID,lngOfficeSeqID,strEnrTypeID);
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of savePolicy(String strValue)

    /**
     * This method method saves the Number of policy uploaded and number of policy rejected in softcopy upload.
     * @param strBatchNumber String which contains Batch Number.
     * @param lngNum_of_policies_rcvd long which contains Number of policies recived.
     * @param strEndorsement_number String which contains Endorsement number.
     * @param strPolicyMode String which contains Policy Mode.
     * @return String object which contains .
     * @exception throws TTKException.
     */
    public String saveUploadStatus(String strBatchNumber,long lngNum_of_policies_rcvd,String strEndorsement_number,String strPolicyMode ) throws org.apache.axis.AxisFault
    {
        String strResult="";
        try
        {
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.saveUploadStatus(strBatchNumber,lngNum_of_policies_rcvd);
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of saveUploadStatus(String strValue)

    /**
     * This method method returns the data from TTKOffice,Product or Branch.
     * @param String object which contains the table name for which data in is required.
     * @return String object which contains .
     * @exception throws TTKException.
     */
    public String getTableData(String strIdentifier,String strID) throws org.apache.axis.AxisFault
    {
        String strResult="";
        try
        {
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.getTableData(strIdentifier,strID);
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of getTableData(String strIdentifier)
    
    /**
     * This method method returns the Rule Errors.
     * @param String object which contains the Batch Number for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String getRuleErrors(String strBatchNbr) throws org.apache.axis.AxisFault{
    	String strResult="";
    	try
        {
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.getRuleErrors(strBatchNbr);
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of getRuleErrors(String strBatchNbr)

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