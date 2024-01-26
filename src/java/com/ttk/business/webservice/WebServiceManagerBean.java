/**
 * @ (#) WebServiceManagerBean.java Jun 14, 2006
 * Project       : TTK HealthCare Services
 * File          : WebServiceManagerBean.java
 * Author        :
 * Company       : Span Systems Corporation
 * Date Created  : Jun 14, 2006
 * @author       : Krishna K. H
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.business.webservice;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.webservice.WebServiceDAOFactory;
import com.ttk.dao.impl.webservice.WebServiceDAOImpl;


@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class WebServiceManagerBean implements WebServiceManager
{

    private WebServiceDAOFactory webserviceDAOFactory = null;
    private WebServiceDAOImpl webserviceDAO=null;

    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getWebServiceDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if (webserviceDAOFactory == null)
                webserviceDAOFactory = new WebServiceDAOFactory();
            if(strIdentifier!=null)
            {
                return webserviceDAOFactory.getDAO(strIdentifier);
            }//end of if(strIdentifier!=null)
            else
                return null;
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error."+strIdentifier+".dao");
        }//end of catch(Exception exp)
    }//End of getWebServiceDAO(String strIdentifier)

    /**
     * This method saves the Policy information.
     * @param String object which contains the policy and member details in XML format.
     * @return String object which contains Policy information.
     * @exception throws TTKException.
     */
    public String savePolicy(String document) throws TTKException {
        webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
        return webserviceDAO.savePolicy(document);
    }//end of savePolicy(String document)

    public String savePolicy(String document,String strCompAbbr)throws TTKException {
        webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
        return webserviceDAO.savePolicy(document,strCompAbbr);
    }//end of savePolicy(String document)

    /**
     * This method returns the consolidated list of Policy Number.
     * @param String object which contains the parameter in XML format.
     * @return String object which contains Policy Numbers.
     * @exception throws TTKException.
     */
    public String getConsolidatedPolicyList(String strFromDate,String strToDate,long lngInsSeqID,long lngProductSeqID,long lngOfficeSeqID,String strEnrTypeID) throws TTKException {
        webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
        return webserviceDAO.getConsolidatedPolicyList( strFromDate, strToDate, lngInsSeqID, lngProductSeqID, lngOfficeSeqID, strEnrTypeID);
    }//end of getConsolidatedPolicyList(String strValue)

    /**
     * This method method saves the Number of policy uploaded and number of policy rejected in softcopy upload.
     * @param String object which contains the softcopy upolad status in XML format.
     * @return String object which contains .
     * @exception throws TTKException.
     */
    public String  saveUploadStatus(String strBatchNumber,long lngNum_of_policies_rcvd) throws TTKException {
        webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
        return webserviceDAO.saveUploadStatus(strBatchNumber,lngNum_of_policies_rcvd);
    }//end of saveUploadStatus(String strValue)

    /**
     * This method method returns the data from TTKOffice,Product or Branch.
     * @param String object which contains the table name for which data in is required.
     * @return String object which contains .
     * @exception throws TTKException.
     */
    public String getTableData(String strIdentifier,String strID) throws TTKException{
        webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
        return webserviceDAO.getTableData(strIdentifier,strID);
    }//end of getTableData(String strIdentifier)
    
    /**
     * This method method returns the Rule Errors.
     * @param String object which contains the Batch Number for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String getRuleErrors(String strBatchNbr) throws TTKException {
    	webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
    	return webserviceDAO.getRuleErrors(strBatchNbr);
    }//end of getRuleErrors(String strBatchNbr)

    /**
	 * This method method returns the USerID And Vidal ID as A String 
	 * @param String object which contains the Policy Number for which data in is required.
	 * @param String object which contains the Vidal Id for which data in is required.
	 * @return String object which contains Rule Errors.
	 * @exception throws TTKException.
	 */

	public String individualLoginService(String strVidalID,String strIMEINumber)throws TTKException{
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.individualLoginService(strVidalID,strIMEINumber);
	}
	
	//ProjectX
	/**
	 * This method method returns the get the Xml and saves it in a folder to test and confirm the web service is working fine. 
	 * @param String object which contains the Policy NInsurance Idumber for which data in is required.
	 * @param String object which contains the Vidal Id for which data in is required.
	 * @return String object which contains Rule Errors.
	 * @exception throws TTKException.
	 */

	public String getXmlandSaveXml(String strInsID)throws TTKException{
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.getXmlandSaveXml(strInsID);
	}
	
	
	/**
	 * This method method returns theSuccess Message
	 * @param String object which contains the Vidal Id for which data in is required.
	 * @param String object which contains the Policy Number for which data in is required.
	 * @param String object which contains the Diagnostic Details for which data in is required.
	 * @param String object which contains the ClaimAmount for which data in is required.
	 * @return String object which contains Rule Errors.
	 * @exception throws TTKException.
	 */
	public String claimIntimationService(String strVidalId,String strPolicyNumber,String strDiagnosticDetails,String strClaimAmount,String strHospitalName,String strHospitalAddress,String strDtAdmission,String strDtDischarge,String strIMEINumber)throws TTKException{
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.claimIntimationService(strVidalId,strPolicyNumber,strDiagnosticDetails,strClaimAmount,strHospitalName,strHospitalAddress,strDtAdmission,strDtDischarge,strIMEINumber);
	}
	
	/**
	 * This method method returns the List of Claims and Policies as a form of String
	 * @param String object which contains the Vidal Id for which data in is required.
	 * @param String object which contains the Policy Number for which data in is required.
	 * @return String object which contains Rule Errors.
	 * @exception throws TTKException.
	 */
	public String policySearchService(String strVidalId,String strIMEINumber)throws TTKException{
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.policySearchService(strVidalId,strIMEINumber);
	}
	
	/**
	 * This method method returns the Claim Details
	 * @param String object which contains the Vidal Id for which data in is required.
	 * @param String object which contains the Policy Number for which data in is required.
	 * @return String object which contains Rule Errors.
	 * @exception throws TTKException.
	 */
	public String claimHistoryService(String strVidalId, String strPolicyNumber,String strMode,String strIMEINumber)throws TTKException {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.claimHistoryService(strVidalId,strPolicyNumber,strMode,strIMEINumber);
	}
	
	/**
	 * This method method returns the Claim status as a String
	 * @param String object which contains the Vidal Id for which data in is required.
	 * @param String object which contains the Policy Number for which data in is required.
	 * @return String object which contains Rule Errors.
	 * @exception throws TTKException.
	 */
	public String claimStatusService(String strVidalId, String strPolicyNumber,String strClaimNumber,String strIMEINumber)throws TTKException{
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.claimStatusService(strVidalId,strPolicyNumber,strClaimNumber,strIMEINumber);
	}

	/**
     * This method method returns the Text 
     * @param String object which contains the Vidal Id for which data in is required.
     *  @param String object which contains the strHospID for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	
	public String feedBackService(String strVidalId,String strIMEINumber,String strHospId,int intRating)throws TTKException
	{
	webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
	return webserviceDAO.feedBackService(strVidalId,strIMEINumber,strHospId,intRating);
	}

	/**
     * This method method returns the Text 
     * @param String object which contains the Vidal Id for which data in is required.
     *  @param String object which contains the strPolicyNumber for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String getDependentDetailsService(String strVidalId, String strPolicyNumber,String strIMEINumber)throws TTKException{
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.getDependentDetailsService(strVidalId,strPolicyNumber,strIMEINumber);
	}
	 /**
     * This method returns the list of hospitals matches with longitude and latitude 
     * @param int object which contains the intLatitude for which data in is required.
     * @param int object which contains the intLongitude for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String getHospInfoService(int startNum,int endNum,String intLatitude ,String intLongitude,String strOperator,double dbKilometers,String strIMEINumber)throws TTKException{
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.getHospInfoService(startNum,endNum,intLatitude,intLongitude,strOperator,dbKilometers,strIMEINumber);
	}

	/**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String getHospDetailsService(String searchValue,int startNum,int intNoOfRows,String strIMEINumber,String strStateID,String strCityID)throws TTKException {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.getHospDetailsService(searchValue,startNum,intNoOfRows,strIMEINumber,strStateID,strCityID);
	}
	
	
	/**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public StringBuffer getEcardTemplate(String strVidalId,String strPolicyNumber,String strIMEINumber)throws TTKException  {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.getEcardTemplate(strVidalId,strPolicyNumber,strIMEINumber);
	}
	
	/**
     * This method method returns the Text ,Color and details
     * @param String object which contains the strDate for which data in is required.
	 * @param String object which contains the strIMEINumber for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String dailyTipService(String strDate,String strIMEINumber) throws TTKException  {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.dailyTipService(strDate,strIMEINumber);
	}
	/**
     * This method method returns the byte[] code
     * @param String object which contains the strDate for which data in is required.
	 * @param String object which contains the strIMEINumber for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	
	public byte[] dailyTipImageService(String strDate,String strIMEINumber) throws TTKException  {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.dailyTipImageService(strDate,strIMEINumber);
	}
/**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String docAroundClockStatusService() throws TTKException {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.docAroundClockStatusService();
    }
    /**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String claimFeedBackService(String strVidalId,String strIMEINumber,String strClaimNumber,String strFeedback) throws TTKException {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.claimFeedBackService(strVidalId,strIMEINumber,strClaimNumber,strFeedback);
    }
    /**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String askExpertOpnionService(String strExpertType,String strUserEmail,String strUserQuery,String strVidalId,String strIMEINumber) throws TTKException {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.askExpertOpnionService(strExpertType,strUserEmail,strUserQuery,strVidalId,strIMEINumber);
    }
    
    /**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String getValidatePhoneNumber(String strValue) throws TTKException {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.getValidatePhoneNumber(strValue);
    }

    /**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String getProviderList(String genTypeId, String hospName, String StateTypeID, String cityDesc, String location) throws TTKException {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.getProviderList(genTypeId, hospName, StateTypeID, cityDesc, location);
    }
    
    /**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String getDiagTests(Long hospSeqId) throws TTKException {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.getDiagTests(hospSeqId);
    }
    
    /**
     * This method method returns the Text 
     * @param String object which contains the Vidal Id for which data in is required.
     *  @param String object which contains the strPolicyNumber for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String getPreAuthClaimsCount(String strVidalId, String strPolicyNumber,String strIMEINumber)throws TTKException{
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.getPreAuthClaimsCount(strVidalId,strPolicyNumber,strIMEINumber);
	}

	/*public String getPolicyDetailsXML(String insuranceSeqID,String userName,String password,String xmlData)throws TTKException{
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.getPolicyDetailsXML(insuranceSeqID,userName,password,xmlData);
	}*/
	
	public String getAllPolicyDetailsXML(String insuranceSeqID,String userName,String password)throws TTKException{
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.getAllPolicyDetailsXML(insuranceSeqID,userName,password);
	}
/**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
   /* public String getCityStateList() throws TTKException {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.getCityStateList();
    }
   */

	@Override
	public String getPolicyDetailsXML(String insuranceSeqID, String batchSeqID,String userName, String password, byte[] xmlData)
			throws TTKException {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.getPolicyDetailsXML(insuranceSeqID,batchSeqID,userName,password,xmlData);
	}

	@Override
	public String craetePolicyDetails(String insuranceSeqID,String batchSeqID,String userName, String password,String policyType, byte[] policyData)
			throws TTKException {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
	return webserviceDAO.createPolicyDetails(insuranceSeqID,batchSeqID, userName, password, policyType, policyData);
	}

	@Override
	public String memberUpload(String insuranceLicenceID, String userName,String password, byte[] policyData) throws TTKException {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.memberUpload(insuranceLicenceID, userName, password, policyData);
		
	}
 
  @Override
public String publishMembers(String insuranceLicenceID, String userName,String password, byte[] policyData) throws TTKException {
	
	  webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.publishMemebers(insuranceLicenceID, userName, password, policyData);
}

@Override
public String publishProductDetails(String insuranceLicenceID, String userName,String password) throws TTKException {
	 webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.publishProductDetails(insuranceLicenceID, userName, password);
}

@Override
public String publishCorporateDetails(String insuranceLicenceID,String userName, String password) throws TTKException {
	webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
	return webserviceDAO.publishCorporateDetails(insuranceLicenceID, userName, password);
}

@Override
public String memberPhotoUpload(String insuranceLicenceID, String userName,
		String password,String enrollmentNo,String format, byte[] memberPhoto) throws TTKException {
	webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
	return webserviceDAO.memberPhotoUpload(insuranceLicenceID, userName, password, enrollmentNo, format, enrollmentNo, memberPhoto);
}

@Override
public String policyDocumentUpload(String insuranceLicenceID, String userName,
		String password,String policyNumber,String format, byte[] policyDocument) throws TTKException {
	webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
	return webserviceDAO.policyDocumentUpload(insuranceLicenceID, userName, password, policyNumber, format, policyDocument);
	
}
	
	/*	public Document getCityStateList1()throws TTKException{
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.getCityStateList1();
		}
    */

/**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
  /*  public Document getStateList() throws TTKException {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.getStateList();
    }
	
	*/
   
   
    /**
     * This method method returns the Text 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
   /*( public Document getCityList(String strStateTypeId) throws TTKException {
		webserviceDAO = (WebServiceDAOImpl)this.getWebServiceDAO("policy");
		return webserviceDAO.getCityList(strStateTypeId);
    }*/

}// End of WebServiceManagerBean
