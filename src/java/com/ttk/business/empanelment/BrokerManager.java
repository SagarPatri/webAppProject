package com.ttk.business.empanelment;
import java.util.ArrayList;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.empanelment.BrokerDetailVO;
import com.ttk.dto.empanelment.InsuranceFeedbackVO;
import com.ttk.dto.empanelment.InsuranceProductVO;

import javax.ejb.*;
@Local
public interface BrokerManager {
	
	/**
	 * This method returns the ArrayList, which contains the list of InsuranceCompany which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of InsuranceCompany object's which contains the InsuranceCompany details
	 * @exception throws TTKException
	 */
	public ArrayList getBrokerCompanyList(ArrayList alSearchObjects) throws TTKException;

	/**
	 * This method returns the BrokerDetailVO, which contains the all the insurancecompany details
	 * @param lInsuranceParentId is Insurance Company Parent Sequence ID 
	 * @param lInsuranceCompanyID is of long type which is unique for each insurance company
	 * @return BrokerDetailVO object which contains all the insurancecompany details
	 * @exception throws TTKException
	 */
	public BrokerDetailVO getBrokerCompanyDetail(Long lInsuranceParentId,Long lInsuranceCompanyID) throws TTKException;
	
	/**
	 * This method adds or updates the insurance company  details   
	 * The method also calls other methods on DAO to insert/update the insurance company  details to the database 
	 * @param BrokerDetailVO BrokerDetailVO object which contains the insurance comapny  details to be added/updated
	 * @return long value, Insurance Seq Id
	 * @exception throws TTKException
	 */
	public long addUpdateInsuranceCompany(BrokerDetailVO BrokerDetailVO)  throws TTKException;
	
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
	
	/**
	 * This method returns the InsuranceFeedbackVO, which contains the InsuranceCompany Feedback details which are populated from the database
	 * @param lngInsFeedbackSeqID long value which contains Feedback Seq ID
	 * @return InsuranceFeedbackVO which contains the InsuranceCompany Feedback details
	 * @exception throws TTKException
	 */
	public InsuranceFeedbackVO getCompanyFeedback(long lngInsFeedbackSeqID) throws TTKException;
	
	/**
	 * This method adds or updates the insurance Feedback   
	 * The method also calls other methods on DAO to insert/update the insurance details to the database 
	 * @param insuranceFeedbackVO InsuranceFeedbackVO object which contains the insuranceFeedback details to be added/updated
	 * @return long value, Insurance Feedback Sequence Id
	 * @exception throws TTKException
	 */
	public long addUpdateCompanyFeedback(InsuranceFeedbackVO insuranceFeedbackVO) throws TTKException ;
	
	/**
	 * This method delete's the Insurance Feedback   records from the database.  
	 * @param strFeedbackSeqID String object which contains the Feedback sequence id's to be deleted
     * @param lnguserSeqID for which user has loggedin
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int deleteCompanyFeedback(String strFeedbackSeqID,long lnguserSeqID) throws TTKException;
	
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
	
	//AK
	public ArrayList getBrokerDetails(ArrayList alSearchObjects) throws TTKException;
	public ArrayList getBrokerDetailsList(ArrayList alSearchObjects,Long policySeqID) throws TTKException;
	public ArrayList<Object> getBrokerDetailsAssocGrpList(ArrayList alSearchObjects) throws TTKException;
	public int saveBrokerDeatils(ArrayList alSearchObjects) throws TTKException;
	
	public int saveAssociatedBrokersUser(ArrayList alSaveList,Long endorsementSeqId) throws TTKException;
	
	public int disAssociateBrokersUser(ArrayList alSaveList,Long endorsementSeqId) throws TTKException;

	public int deleteFloatAssocGrp(String Brokerseqid,Long policySeqID,Long endorsementSeqId,String SwitchType) throws TTKException;

	public int getAssoDissoCount(Long policySeqID,Long brokerseqid) throws TTKException;
	public ArrayList getAssociatedBrokerDetailsList(ArrayList alSearchObjects,Long policySeqID) throws TTKException;
	
	public String getBrokerAssocCount(Long policySeqID) throws TTKException;
}
