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
import org.apache.log4j.Logger;
import org.apache.commons.codec.binary.Base64;
import com.ttk.business.common.MobileEcard;
import com.ttk.business.webservice.WebServiceManager;
import com.ttk.common.exception.TTKException;
import com.ttk.common.webservices.helper.ValidatorResourceHelper;
//import org.dom4j.Document;

public class MobileAppServices
{
	private static Logger log = Logger.getLogger(MobileAppServices.class);
    /**
     * This method method returns the String 
     * @param String object which contains the strVidalID for which data in is required.
     * @param String object which contains thestrIMEINumber for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    
    public String individualLoginService(String strVidalID,String strIMEINumber) throws org.apache.axis.AxisFault{
    	String strResult="";
    	try
        {
    		log.info("INSIDE LOGIN PAGE ACTION START");
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.individualLoginService(strVidalID,strIMEINumber);
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
     * This method method returns the JPEG IMAGE
     * @param String object which contains the Vidal Id for which data in is required.
      * @param String object which contains the strIMEINumber for which data in is required.
	 * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String ecardService(String strVidalId,String strPolicyNumber,String strIMEINumber) throws org.apache.axis.AxisFault{
    	String strResult="";

    	try
        {
    		log.info("INSIDE ECARD SERVICES ACTION START");
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            StringBuffer strEcardData= (StringBuffer)webServiceManager.getEcardTemplate(strVidalId, strPolicyNumber,strIMEINumber);
	        

	   if(!(strEcardData.toString()).equalsIgnoreCase("NA"))
		{
           String pdfFileName= MobileEcard.getPdfFileName(strVidalId, strEcardData);
           String strJpegFileName= MobileEcard.getJpegFileName(strVidalId, pdfFileName);
           strResult=MobileEcard.getImageByteData(strJpegFileName);
		}
	    else{strResult="No Data Found";	}
          //  strResult = webServiceManager.ecardService(strVidalID);
  		log.info("INSIDE ECARD SERVICES ACTION END");
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of  ecardService(String strVidalId, String strPolicyNumber,String strIMEINumber)


        /**
     * This method method returns theSuccess Message
     * @param String object which contains the Vidal Id for which data in is required.
     * @param String object which contains the Policy Number for which data in is required.
     * @param String object which contains the Diagnostic Details for which data in is required.
     * @param String object which contains the ClaimAmount for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String claimIntimationService(String strVidalId,String strPolicyNumber,String strDiagnosticDetails,String strClaimAmount,String strHospitalName,String strHospitalAddress,String strDtAdmission,String strDtDischarge,String strIMEINumber) throws org.apache.axis.AxisFault{
    	String strResult="";
    	try
        {
    		log.info("INSIDE CLAIM INTIMATION  ACTION START");
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.claimIntimationService(strVidalId,strPolicyNumber,strDiagnosticDetails,strClaimAmount,strHospitalName,strHospitalAddress,strDtAdmission,strDtDischarge,strIMEINumber);
            log.info("INSIDE CLAIM INTIMATION  ACTION END");
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of   claimIntimationService(String strVidalId,String strPolicyNumber,String strDiagnosticDetails,String strClaimAmount,String strHospitalName,String strHospitalAddress,String strDtAdmission,String strDtDischarge,String strIMEINumber)
	
    
	 /**
     * This method method returns the List of Claims and Policies as a form of String
     * @param String object which contains the Policy Number for which data in is required.
     * @param String object which contains the Vidal Id for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String policySearchService(String strVidalId,String strIMEINumber) throws org.apache.axis.AxisFault{
    	String strResult="";
    	try
        {
    		log.info("INSIDE POLICY SEARCH ACTION START");
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.policySearchService(strVidalId,strIMEINumber);
            log.info("INSIDE POLICY SEARCH ACTION END");
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of policySearchService(String strVidalId,String strIMEINumber)
	
    
    /**
     * This method method returns the Claim Details
     * @param String object which contains the Policy Number for which data in is required.
     * @param String object which contains the Vidal Id for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
  	public String claimHistoryService(String strVidalId, String strPolicyNumber,String strMode,String strIMEINumber)throws org.apache.axis.AxisFault
	{
    	String strResult="";
    	try
        {
    		log.info("INSIDE CLAIM HISTORY ACTION START");
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.claimHistoryService(strVidalId,strPolicyNumber,strMode,strIMEINumber);
            log.info("INSIDE CLAIM HISTORY ACTION END");
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of claimStatusService(String strVidalId, String strPolicyNumber,String strClaimNumber,String strIMEINumber)
	
    
    
    /**
     * This method method returns the Claim status as a String
      * @param String object which contains the Policy Number for which data in is required.
     * @param String object which contains the Vidal Id for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String claimStatusService(String strVidalId,String strPolicyNumber,String strClaimNumber,String strIMEINumber) throws org.apache.axis.AxisFault{
    	String strResult="";
    	try
        {
    		log.info("INSIDE CLAIM STATUS ACTION START");
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.claimStatusService(strVidalId,strPolicyNumber,strClaimNumber,strIMEINumber);
            log.info("INSIDE CLAIM STATUS ACTION END");
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of claimStatusService(String strVidalId,String strPolicyNumber)

	
    /**
     * This method method returns the Text 
     * @param String object which contains the Vidal Id for which data in is required.
     *  @param String object which contains the strHospID for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
  public String feedBackService(String strVidalId,String strIMEINumber,String strHospId,int intRating) throws org.apache.axis.AxisFault{
    	String strResult="";
    	try
        {
    		log.info("INSIDE FEEDBACK SERVICES ACTION START");
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.feedBackService(strVidalId,strIMEINumber,strHospId,intRating);
            log.info("INSIDE FEEDBACK SERVICES ACTION END");
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of feedBackService(String strVidalId,String strIMEINumber,String strHospId,int intRating)
    
    
    /**
     * This method method returns the Text 
     * @param String object which contains the Vidal Id for which data in is required.
     *  @param String object which contains the strPolicyNumber for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String getDependentDetailsService(String strVidalId,String strPolicyNumber,String strIMEINumber) throws org.apache.axis.AxisFault{
    	String strResult="";
    	try
        {
    		log.info("INSIDE DEPENDENT DETAILS ACTION START");
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.getDependentDetailsService(strVidalId,strPolicyNumber,strIMEINumber);
            log.info("INSIDE DEPENDENT DETAILS ACTION END");
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of getDependentDetailsService(String strVidalId, String strPolicyNumber,String strIMEINumber)
	
    
  
	 /**
     * This method returns the list of hospitals matches with longitude and latitude 
     * @param int object which contains the Start Number for which data in is required.
     * @param int object which contains the End Number for which data in is required.
     * @param String object which contains the intLatitude for which data in is required.
     * @param String object which contains the intLongitude for which data in is required.
     * @param String  object which contains the Operator for which data in is required.
     * @param double object which contains the kilometers for which data in is required.
     * @param String  object which contains the IMEi Number for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String getHospInfoService(int startNum,int intNoOfRows,String intLatitude ,String intLongitude,String strOperator,double dbKilometers,String strIMEINumber) throws org.apache.axis.AxisFault{
    	String strResult="";
    	try
        {
    		log.info("INSIDE HOSPITAL INFORMATION ACTION START");
    		if((!(intLatitude.trim()).equalsIgnoreCase("0")) || (!(intLongitude.trim()).equalsIgnoreCase("0")) )
    		{
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.getHospInfoService(startNum,intNoOfRows,intLatitude,intLongitude,strOperator,dbKilometers,strIMEINumber);
    		}
    		else{
    			strResult="no data found";
    		}
    		log.info("INSIDE HOSPITAL INFORMATION ACTION END");
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of (int startNum,int endNum,String intLatitude ,String intLongitude,String strOperator,double dbKilometers,String strIMEINumber)
	
  
   /**
	 * This method method returns the Text 
	 * @param String object which contains the searchValue for which data in is required.
	 * @param int object which contains the startNum Id for which data in is required.
	 * @param int object which contains the intNoOfRows for which data in is required.
	 * @param String object which contains the strIMEINumber Id for which data in is required.
	 *  @param String object which contains the strStateID for which data in is required.
	 * @param String object which contains the strCityID Id for which data in is required.
	* @return String object which contains Rule Errors.
	 * @exception throws TTKException.
	 */
    public String getHospDetailsService(String searchValue,int startNum,int intNoOfRows,String strIMEINumber,String strStateID,String strCityID) throws org.apache.axis.AxisFault{
    	String strResult="";
    	try
        {
    		log.info("INSIDE HOSPITAL DETAILS ACTION START");
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            strResult = webServiceManager.getHospDetailsService(searchValue,startNum,intNoOfRows,strIMEINumber,strStateID,strCityID);
            log.info("INSIDE HOSPITAL DETAILS ACTION END");
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return strResult;
    }//end of getHospDetailsService(String searchValue,int startNum,int intNoOfRows,String strIMEINumber,String strStateID,String strCityID)
    
    
     
    /**
     * This method method returns the Text (tip_title,tip_text,tip_color)
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @param String object which contains the strIMEINumber for which data in is required search Criteria.
	 * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String dailyTipService(String strDate,String strIMEINumber) throws org.apache.axis.AxisFault{
    	  	 String dailyTipData="";
    	try
        {
    		log.info("INSIDE DAILY TIPS ACTION START");
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            dailyTipData = webServiceManager.dailyTipService(strDate,strIMEINumber);
            log.info("INSIDE DAILY TIPS ACTION END");
       
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
        return dailyTipData;
    }//end of dailyTipService(String strDate,String strIMEINumber)
   
    
    /**
     * This method method returns the Base64.encodeBase64String 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @param String object which contains the strIMEINumber for which data in is required search Criteria.
	 * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String dailyTipImageService(String strDate,String strIMEINumber) throws org.apache.axis.AxisFault{
    	 byte[] data=null;
    	 String dailyTipData="";
    	try
        {
    		log.info("INSIDE DAILY TIPS IMAGE ACTION START");
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
 	     data = webServiceManager.dailyTipImageService(strDate,strIMEINumber);
  	    log.info("INSIDE DAILY TIPS IMAGE ACTION END");
		
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
	
        return (data != null) ? Base64.encodeBase64String(data) : "no data found";
    }//end of dailyTipImageService(String strDate,String strIMEINumber) 
   
 /**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String docAroundClockStatusService() throws org.apache.axis.AxisFault{
    	   	 String dailyTipData="";
    	try
        {
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
           // dailyTipData = webServiceManager.docAroundClockStatusService();
            		
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
	
        return "testing data";
    }//end of docAroundClockStatusService(S)
   

	/**
     * This method method returns the String (Claim FeedBack Updated Successfully or not)
     * @param String object which contains the strVidalId for which data in is required search Criteria.
     * @param String object which contains the strIMEINumber for which data in is required search Criteria.
  	 * @param String object which contains the strClaimNumber for which data in is required search Criteria.
     * @param String object which contains the strFeedback for which data in is required search Criteria.
 	 * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String claimFeedBackService(String strVidalId,String strIMEINumber,String strClaimNumber,String strFeedback) throws org.apache.axis.AxisFault{
    	   	 String strFeedBack="";
    	try
        {
    		log.info("INSIDE CLAIM FEEDBACK SERVICES ACTION START");
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
 	     strFeedBack = webServiceManager.claimFeedBackService(strVidalId,strIMEINumber,strClaimNumber,strFeedback);
  	    log.info("INSIDE CLAIM FEEDBACK SERVICES ACTION END");		
		
            		
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
	
        return strFeedBack;
    }//end of claimFeedBackService(strVidalId,strIMEINumber,strClaimNumber,strFeedback);
   
     /**
     * This method method returns the Text 
     * @param String object which contains the strExpertType for which data in is required search Criteria.
     * @param String object which contains the strUserEmail for which data in is required search Criteria.
	 * @param String object which contains the strUserQuery for which data in is required search Criteria.
     * @param String object which contains the strVidalId for which data in is required search Criteria.
	  * @param String object which contains the strIMEINumber for which data in is required search Criteria.
	 * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String askExpertOpnionService(String strExpertType,String strUserEmail,String strUserQuery,String strVidalId,String strIMEINumber) throws org.apache.axis.AxisFault{
    	   	 String strOpnion="";
    	try
        {
    		log.info("INSIDE EXPERT OPNION ACTION START");
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
 	     strOpnion = webServiceManager.askExpertOpnionService(strExpertType,strUserEmail,strUserQuery,strVidalId,strIMEINumber);
  	    log.info("INSIDE EXPERT OPNION ACTION END");
            		
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
	
        return strOpnion;
    }//end of askExpertOpnionService(String strExpertType,String strUserEmail,String strUserQuery,String strVidalId,String strIMEINumber);
   
    

/**
     * This method method returns the String StateList 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String getPreAuthClaimsCount(String strVidalId,String strPolicyNumber,String strIMEINumber) throws org.apache.axis.AxisFault{
	   	 String strOpnion="";
	try
   {
		log.info("INSIDE PREAUTN/CLAIMS COUNT ACTION START");
       WebServiceManager webServiceManager=this.getWebServiceManagerObject();
     strOpnion = webServiceManager.getPreAuthClaimsCount(strVidalId,strPolicyNumber,strIMEINumber);
	    log.info("INSIDE PREAUTN/CLAIMS COUNT ACTION END");
       		
   }//end of try
   catch(Exception exp)
   {
       exp.printStackTrace();
       AxisFault fault=new AxisFault();
       fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
       throw fault;
   }//end of catch

   return strOpnion;
}//end of getPreAuthClaimsCount(String strExpertType,String strUserEmail,String strUserQuery,String strVidalId,String strIMEINumber);



/**
     * This method method returns the String StateList 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
   /* public String getStateList() throws org.apache.axis.AxisFault{
    	   	 Document document=null;
    	try
        {
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            document = webServiceManager.getStateList();
            		
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
	
        return document.asXML();
    }//end of getCityStateList((String strVidalId,String strIMEINumber)
   */

    /**
     * This method method returns CityList in the form of XML 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
   /* public String getCityList(String strStateTypeId) throws org.apache.axis.AxisFault{
    	Document document=null;
    	try
        {
            WebServiceManager webServiceManager=this.getWebServiceManagerObject();
            document = webServiceManager.getCityList(strStateTypeId);
            		
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            AxisFault fault=new AxisFault();
            fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
            throw fault;
        }//end of catch
	
        return document.asXML();
    }//end of getCityList((String strVidalId,String strIMEINumber)
   
    
*/

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