/**
 * @ (#) WebServiceManager.java Jun 14, 2006
 * Project       : TTK HealthCare Services
 * File          : WebServiceManager.java
 * Author        :
 * Company       : Span Systems Corporation
 * Date Created  : Jun 14, 2006
 * @author       : Krishna K. H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.webservice;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
@Local

public interface WebServiceManager {

    /**
     * This method saves the Policy information.
     * @param String object which contains the policy and member details in XML format.
     * @return String object which contains Policy information.
     * @exception throws TTKException.
     */
    public String savePolicy(String document) throws TTKException;
    
    /**
     * This method saves the Policy information.
     * @param String object which contains the policy and member details in XML format.
     * @return String object which contains Policy information.
     * @exception throws TTKException.
     */
    public String savePolicy(String document,String strCompAbbr) throws TTKException;
    
    /**
     * This method returns the consolidated list of Policy Number.
     * @param String object which contains the parameter in XML format.
     * @return String object which contains Policy Numbers.
     * @exception throws TTKException.
     */
    public String getConsolidatedPolicyList(String strFromDate,String strToDate,long lngInsSeqID,long lngProductSeqID,long lngOfficeSeqID,String strEnrTypeID) throws TTKException;
    
    /**
     * This method method saves the Number of policy uploaded and number of policy rejected in softcopy upload.
     * @param String object which contains the softcopy upolad status in XML format.
     * @return String object which contains .
     * @exception throws TTKException.
     */
    public String saveUploadStatus(String strBatchNumber,long lngNum_of_policies_rcvd) throws TTKException;
    
    /**
     * This method method returns the data from TTKOffice,Product or Branch.
     * @param String object which contains the table name for which data in is required.
     * @return String object which contains .
     * @exception throws TTKException.
     */
    public String getTableData(String strIdentifier,String strID) throws TTKException;
    
    /**
     * This method method returns the Rule Errors.
     * @param String object which contains the Batch Number for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String getRuleErrors(String strBatchNbr) throws TTKException;

   /**
     * This method method returns the USerID And Vidal ID as A String 
     * @param String object which contains the Policy Number for which data in is required.
     * @param String object which contains the Vidal Id for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String individualLoginService(String strVidalID,String strIMEINumber)throws TTKException;
	
	//ProjectX
	 /**
     * This method method returns the USerID And Vidal ID as A String 
     * @param String object which contains the Policy Number for which data in is required.
     * @param String object which contains the Vidal Id for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String getXmlandSaveXml(String strInsID)throws TTKException;
	
	/**
     * This method method returns theSuccess Message
     * @param String object which contains the Vidal Id for which data in is required.
     * @param String object which contains the Policy Number for which data in is required.
     * @param String object which contains the Diagnostic Details for which data in is required.
     * @param String object which contains the ClaimAmount for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String claimIntimationService(String strVidalId,String strPolicyNumber,String strDiagnosticDetails,String strClaimAmount,String strHospitalName,String strHospitalAddress,String strDtAdmission,String strDtDischarge,String strIMEINumber)throws TTKException;
	 /**
     * This method method returns the List of Claims and Policies as a form of String
	 * @param String object which contains the Vidal Id for which data in is required.
	 * @param String object which contains the Policy Number for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String policySearchService(String strVidalId,String strIMEINumber)throws TTKException;
	  /**
     * This method method returns the Claim Details
  	 * @param String object which contains the Vidal Id for which data in is required.
	 * @param String object which contains the Policy Number for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String claimHistoryService(String strVidalId, String strPolicyNumber,String strMode,String strIMEINumber)throws TTKException ;
	 /**
     * This method method returns the Claim status as a String
 	 * @param String object which contains the Vidal Id for which data in is required.
	 * @param String object which contains the Policy Number for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String claimStatusService(String strVidalId, String strPolicyNumber,String strClaimNumber,String strIMEINumber)throws TTKException;

	/**This method method returns the Text 
     * @param String object which contains the Vidal Id for which data in is required.
     *  @param String object which contains the strHospID for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String feedBackService(String strVidalId,String strIMEINumber,String strHospId,int intRating)throws TTKException;

	/**
     * This method method returns the Text 
     * @param String object which contains the Vidal Id for which data in is required.
     *  @param String object which contains the strPolicyNumber for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String getDependentDetailsService(String strVidalId, String strPolicyNumber,String strIMEINumber)throws TTKException;
	
	 /**
     * This method returns the list of hospitals matches with longitude and latitude 
     * @param int object which contains the intLatitude for which data in is required.
     * @param int object which contains the intLongitude for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String getHospInfoService(int startNum,int endNum,String intLatitude ,String intLongitude,String strOperator,double dbKilometers,String strIMEINumber)throws TTKException;
	
	

	 /**
	 * This method method returns the Text 
	 * @param String object which contains the searchValue for which data in is required search Criteria.
	 * @return String object which contains Rule Errors.
	 * @exception throws TTKException.
	 */
	public String getHospDetailsService(String searchValue,int startNum,int intNoOfRows,String strIMEINumber,String strStateID,String strCityID)throws TTKException ;
		
	
	/**
     * This method method returns the JPEG IMAGE
     * @param String object which contains the Vidal Id for which data in is required.
     * @return String object which contains Rule Errors.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public StringBuffer getEcardTemplate(String strVidalId,String strPolicyNumber,String strIMEINumber)throws TTKException ;
	
	/**
     * This method method returns the byte[] code
     * @param String object which contains the strDate for which data in is required.
	 * @param String object which contains the strIMEINumber for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public byte[] dailyTipImageService(String strDate,String strIMEINumber) throws TTKException;

	/**
     * This method method returns the Text ,Color and details
     * @param String object which contains the strDate for which data in is required.
	 * @param String object which contains the strIMEINumber for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	
	public String dailyTipService(String strDate,String strIMEINumber) throws TTKException;

	/**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String docAroundClockStatusService() throws TTKException;


   /**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String claimFeedBackService(String strVidalId,String strIMEINumber,String strClaimNumber,String strFeedback) throws TTKException;
   
   /**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String askExpertOpnionService(String strExpertType,String strUserEmail,String strUserQuery,String strVidalId,String strIMEINumber) throws TTKException;
	
    
    /**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String getValidatePhoneNumber(String strValue) throws TTKException;
    
    
    /**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String getProviderList(String genTypeId, String hospName, String StateTypeID, String cityDesc, String location) throws TTKException;

    /**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String getDiagTests(Long hospSeqId) throws TTKException;
   
    /**
     * This method method returns the Text 
     * @param String object which contains the Vidal Id for which data in is required.
     *  @param String object which contains the strPolicyNumber for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String getPreAuthClaimsCount(String strVidalId, String strPolicyNumber,String strIMEINumber)throws TTKException;
	
	/**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    //public String getCityStateList() throws TTKException;
    /**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
   // public Document getCityStateList1()throws TTKException;


/**
     * This method method returns the DocumentXml 
     *   * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    //public Document getStateList() throws TTKException;
    /**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
  //  public Document getCityList(String strStateTypeId) throws TTKException;
	
	public String getPolicyDetailsXML(String insuranceSeqID,String batchSeqID,String userName,String password,byte[] xmlData)throws TTKException;
	public String getAllPolicyDetailsXML(String insuranceSeqID,String userName,String password)throws TTKException;

	public String craetePolicyDetails(String insuranceSeqID,String batchSeqID, String userName,String password, String policyType, byte[] xmlData)throws TTKException;

	public String memberUpload(String insuranceLicenceID, String userName,String password,byte[] policyData) throws TTKException;

	public String publishMembers(String insuranceLicenceID, String userName,String password,byte[] policyData) throws TTKException;
	public String publishProductDetails(String insuranceLicenceID, String userName,String password) throws TTKException;
	public String publishCorporateDetails(String insuranceLicenceID, String userName,String password) throws TTKException;
	public String memberPhotoUpload(String insuranceLicenceID, String userName,String password,String enrollmentNo,String format,byte[] memberPhoto) throws TTKException;
	public String policyDocumentUpload(String insuranceLicenceID, String userName,String password,String enrollmentNo,String format,byte[] policyDocument) throws TTKException;
    }//End of WebServiceManager
