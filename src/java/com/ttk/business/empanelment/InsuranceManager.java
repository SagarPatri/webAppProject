/**
 * @ (#) Any.java Oct 19, 2005
 * Project      : ttkHealthCareServices
 * File         : InsuranceManager.java
 * Author       : Ravindra
 * Company      : SpanSystem Corp:
 * Date Created : Oct 19, 2005
 *
 * @author       :Ravindra
 
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.business.empanelment;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.empanelment.BrokerFeedbackVO;
import com.ttk.dto.empanelment.InsuranceDetailVO;
import com.ttk.dto.empanelment.InsuranceFeedbackVO;
import com.ttk.dto.empanelment.InsuranceProductVO;
//added for Mail-SMS Template for Cigna
import com.ttk.dto.empanelment.NotificationInfoVO;
import com.ttk.dto.insurancepricing.AgeMasterVO;
import com.ttk.dto.insurancepricing.AreaOfCoverVO;
import com.ttk.dto.insurancepricing.InsPricingVO;
import com.ttk.dto.insurancepricing.MasterFactorVO;
import com.ttk.dto.insurancepricing.PolicyConfigVO;
import com.ttk.dto.insurancepricing.SwPolicyConfigVO;

import javax.ejb.*;

@Local

public interface InsuranceManager {
	
	/**
	 * This method returns the ArrayList, which contains the list of InsuranceCompany which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of InsuranceCompany object's which contains the InsuranceCompany details
	 * @exception throws TTKException
	 */
	public ArrayList getInsuranceCompanyList(ArrayList alSearchObjects) throws TTKException;
	
	/**
	 * This method returns the InsuranceDetailVO, which contains the all the insurancecompany details
	 * @param lInsuranceParentId is Insurance Company Parent Sequence ID 
	 * @param lInsuranceCompanyID is of long type which is unique for each insurance company
	 * @return InsuranceDetailVO object which contains all the insurancecompany details
	 * @exception throws TTKException
	 */
	public InsuranceDetailVO getInsuranceCompanyDetail(Long lInsuranceParentId,Long lInsuranceCompanyID) throws TTKException;
	
	/**
	 * This method adds or updates the insurance company  details   
	 * The method also calls other methods on DAO to insert/update the insurance company  details to the database 
	 * @param insuranceDetailVO InsuranceDetailVO object which contains the insurance comapny  details to be added/updated
	 * @return long value, Insurance Seq Id
	 * @exception throws TTKException
	 */
	public long addUpdateInsuranceCompany(InsuranceDetailVO insuranceDetailVO)  throws TTKException;
	/**
	 * This method delete's the insurance Company  records from the database.  
	 * @param strInsSeqID String object which contains the insurance company  sequence id's to be deleted
	 * @return int  value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int deleteInsuranceCompany(String strInsSeqID)   throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the InsuranceCompanyProductVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of InsuranceCompanyProductVO object's which contains the InsuranceCompany details
	 * @exception throws TTKException
	 */
	public ArrayList getProductList(ArrayList alSearchObjects)  throws TTKException;
	
	/**
	 * This method adds or updates the insurance Products   
	 * The method also calls other methods on DAO to insert/update the insurance details to the database 
	 * @param insuranceProductVO InsuranceProductVO object which contains the insuranceCompanyProduct details to be added/updated
	 * @return long value, Associate Office Sequence Id
	 * @exception throws TTKException
	 */
	public long addUpdateProduct(InsuranceProductVO insuranceProductVO) throws TTKException;
	
	/**
	 * This method delete's the Products  records from the database.  
	 * @param alProductList ArrayList object which contains the Product sequence id's to be deleted
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int  deleteProduct(ArrayList alProductList)  throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the InsuranceFeedbackVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of InsuranceFeedbackVO object's which contains the InsuranceCompany Feedback details
	 * @exception throws TTKException
	 */
	public ArrayList getCompanyFeedbackList(ArrayList alSearchObjects) throws TTKException;
	
	//added for kocb broker login
	public ArrayList getBrokerFeedbackList(ArrayList alSearchObjects) throws TTKException;
	
	
	/**
	 * This method returns the InsuranceFeedbackVO, which contains the InsuranceCompany Feedback details which are populated from the database
	 * @param lngInsFeedbackSeqID long value which contains Feedback Seq ID
	 * @return InsuranceFeedbackVO which contains the InsuranceCompany Feedback details
	 * @exception throws TTKException
	 */
	public InsuranceFeedbackVO getCompanyFeedback(long lngInsFeedbackSeqID) throws TTKException;
	
	public InsuranceFeedbackVO getBrokerFeedback(long lngInsFeedbackSeqID) throws TTKException;
	
	/**
	 * This method adds or updates the insurance Feedback   
	 * The method also calls other methods on DAO to insert/update the insurance details to the database 
	 * @param insuranceFeedbackVO InsuranceFeedbackVO object which contains the insuranceFeedback details to be added/updated
	 * @return long value, Insurance Feedback Sequence Id
	 * @exception throws TTKException
	 */
	public long addUpdateCompanyFeedback(InsuranceFeedbackVO insuranceFeedbackVO) throws TTKException ;
	//added for broker login kocb
	//public long addUpdateBrokerFeedback(BrokerFeedbackVO brokerFeedbackVO) throws TTKException ;
	public long addUpdateBrokerFeedback(InsuranceFeedbackVO insuranceFeedbackVO) throws TTKException ;
	
	/**
	 * This method delete's the Insurance Feedback   records from the database.  
	 * @param strFeedbackSeqID String object which contains the Feedback sequence id's to be deleted
     * @param lnguserSeqID for which user has loggedin
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int deleteCompanyFeedback(String strFeedbackSeqID,long lnguserSeqID) throws TTKException;
/**
	 * This method delete's the Broker Feedback   records from the database.  
	 * @param strFeedbackSeqID String object which contains the Feedback sequence id's to be deleted
     * @param lnguserSeqID for which user has loggedin
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int deleteBrokerFeedback(String strFeedbackSeqID,long lnguserSeqID) throws TTKException;
	/**
	 * This method returns the ArrayList object of ProductVO's, which contains all the details about the products Associated to parent Insurance Company
	 * @param lInsSeqId long Insurance Sequence Id
	 * @return ArrayList object which contains all the details about the Insurance Company Associated products
	 * @exception throws TTKException 
	 */
	public ArrayList getProductCode(long lInsSeqId) throws TTKException;
	
	/**
	 * This method returns the AraayList of InsuranceVO which contains the details of the HeadOffice and regional office and branch offices and regionael offices
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return AraayList of InsuranceVO which contains the details of the HeadOffice and regional office and branch offices and regionael offices
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getCompanyDetails(ArrayList alSearchObjects) throws TTKException;
	
	/**
	 * This method returns the Associated Product Details
	 * @param lngAssociateOfficeSequenceID the associated Office Sequence ID for which the product details are required
	 * @return InsuranceProductVO which contains the Associated Product Details
	 * @exception throws TTKException
	 */
	public InsuranceProductVO getProductDetails(Long  lngAssociateOfficeSequenceID) throws TTKException;
	//added for Mail-SMS Template for Cigna
	/*
	 * Added for CR KOC Mail-SMS Notification.
	 * 
	 */
	public NotificationInfoVO getMailNotificationList(Long insuranceSeqID) throws TTKException;
	/*
	 * Added for CR KOC Mail-SMS Notification.
	 */
	public int saveMailNotificationInfo(NotificationInfoVO notificationInfoVO) throws TTKException;

	/*
	 * method to get the max Abbreviation code
	 */

	public String getMaxAbbrevationCode()throws TTKException;
	
	public Long savePricingList(InsPricingVO insPricingVO) throws TTKException;
	
	public InsPricingVO selectPricingList(Long lpricingSeqId) throws TTKException;
	
	public ArrayList getProfileIncomeList(Long lpricingSeqId) throws TTKException;	
	
	public int saveIncomeProfile(InsPricingVO insPricingVO) throws TTKException;
	public ArrayList getProfileIncomeListvalue(Long lpricingSeqId) throws TTKException;	
	
	public ArrayList getInsuranceProfileList(ArrayList alSearchObjects) throws TTKException;
	
	 public int savePlanDesignConfig(PolicyConfigVO policyConfigVO) throws TTKException;
	 public PolicyConfigVO getPlanDesignConfigData(long webBoardId)throws TTKException;
	 
	 public PolicyConfigVO saveGenerateQuote(PolicyConfigVO policyConfigVO)throws TTKException;
	 
	 public  Object[] selectGenerateQuote(long webBoardId)throws TTKException;
	 //bikki
	 public ArrayList swSavePricingList(InsPricingVO insPricingVO)throws TTKException;
	 public InsPricingVO swSelectPricingList(Long lpricingSeqId) throws TTKException;
	 public ArrayList getSwInsuranceProfileList(ArrayList<Object> searchData) throws TTKException;
	 public Object[] getBenefitvalueAfter(Long lpricingSeqId) throws TTKException;
	 //b
	 public Long swSaveIncomeProfile(HashMap<String, Object> screen2Data) throws TTKException;
	 //end bikki
	 
	 // govind
	 
	 public String [] callDbValidation(InsPricingVO insPricingVO)throws TTKException;
	 
	 /**
		 * This method returns the Associated Product Details
		 * @param lngAssociateOfficeSequenceID the associated Office Sequence ID for which the product details are required
		 * @return InsuranceProductVO which contains the Associated Product Details
		 * @exception throws TTKException
		 */
	 public ArrayList<MasterFactorVO> selectFactorMasterList() throws TTKException;
	 
	 public Long saveFactorMaster(ArrayList<MasterFactorVO> factorList) throws TTKException;

	 public ArrayList getVatProductList(InsuranceDetailVO insDetailVO) throws TTKException;
	
	 public int savePriceTrendMaster(MasterFactorVO masterFactorVO) throws TTKException;
	 public MasterFactorVO getPriceTrendMaster(MasterFactorVO masterFactorVO) throws TTKException;
	 
	 
	 
	 
	 public Object[] getdemographicData(InsPricingVO insPricingVO) throws TTKException;
	 
	 public Long saveDemographicData(SwPolicyConfigVO swPolicyConfigVO) throws TTKException;
	 
	 public HashMap getGrossPremiumData(long GroupProfileSeqID) throws TTKException;
	 
	 public Long saveRiskPremiumData(ArrayList updatedDataList, String flag) throws TTKException;
	 
	 public InsPricingVO swFetchScreen1(InsPricingVO insPricingVO) throws TTKException;
	 
	 public ArrayList<AreaOfCoverVO> getPriceOpAOC() throws TTKException;
	 
	 public Long savePriceOpAOC(ArrayList<AreaOfCoverVO> opAocList) throws TTKException;

	public String PricingUploadExcel(String batchNo, FileReader fileReader,int length,Long pricingseqid) throws TTKException;

	
	 /**
		 * This method returns the Associated Product Details
		 * @param lngAssociateOfficeSequenceID the associated Office Sequence ID for which the product details are required
		 * @return InsuranceProductVO which contains the Associated Product Details
		 * @exception throws TTKException
		 */	 
	 public Long saveTable2Data(ArrayList<SwPolicyConfigVO> alTable2Data) throws TTKException;
	 
	 /**
		 * This method returns the Associated Product Details
		 * @param lngAssociateOfficeSequenceID the associated Office Sequence ID for which the product details are required
		 * @return InsuranceProductVO which contains the Associated Product Details
		 * @exception throws TTKException
		 */	 
	 public Long saveTable3Data(ArrayList<HashMap<String, String>> alHmTable3Data) throws TTKException;
		
	 public String saveVatProductList(String insSeqId, String productId,String refFlag)throws TTKException;
	 
}// End of InsuranceManager




