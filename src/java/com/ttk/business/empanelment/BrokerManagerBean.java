/**
 * @ (#) Any.java Oct 19, 2005
 * Project      : ttkHealthCareServices
 * File         : InsuranceManagerBean.java
 * Author       : Ravindra
 * Company      : SpanSystem Corp:
 * Date Created : Oct 19, 2005
 *
 * @author       :  Ravindra

 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.business.empanelment;

import java.util.ArrayList;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.empanelment.EmpanelmentDAOFactory;
import com.ttk.dao.impl.empanelment.BrokerDAOImpl;
import com.ttk.dao.impl.empanelment.InsuranceFeedbackDAOImpl;
import com.ttk.dao.impl.empanelment.InsuranceProductDAOImpl;
import com.ttk.dto.empanelment.BrokerDetailVO;
import com.ttk.dto.empanelment.InsuranceFeedbackVO;
import com.ttk.dto.empanelment.InsuranceProductVO;

import javax.ejb.*;
@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)
public  class BrokerManagerBean implements BrokerManager {
	
	private EmpanelmentDAOFactory empanelmentDAOFactory = null;
	private BrokerDAOImpl brokerDAO = null;
	private InsuranceFeedbackDAOImpl insuranceFeedbackDAO = null;
	private InsuranceProductDAOImpl insuranceProductDAO = null;
	
	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	private BaseDAO getEmpanelmentDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if(empanelmentDAOFactory == null)
				empanelmentDAOFactory = new EmpanelmentDAOFactory();
			if(strIdentifier!=null)
			{ 
				return empanelmentDAOFactory.getDAO(strIdentifier);
			}//end of if(strIdentifier!=null)
			else
				return null;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//end getEmpanelmentDAO(String strIdentifier)

	/**
     * This method returns the ArrayList, which contains the list of InsuranceCompany which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of InsuranceCompany object's which contains the InsuranceCompany details
     * @exception throws TTKException
     */
    public ArrayList getBrokerCompanyList(ArrayList alSearchObjects) throws TTKException {
    	 
		brokerDAO = (BrokerDAOImpl)this.getEmpanelmentDAO("broker");
		return brokerDAO.getBrokerCompanyList(alSearchObjects);
	}// End of getInsuranceList(ArrayList alSearchObjects)
    

    
	/**
	 * This method returns the InsuranceDetailVO, which contains the all the insurancecompany details
	 * @param lInsuranceParentId is Insurance Company Parent Sequence ID
	 * @param lInsuranceCompanyID is of long type which is unique for each insurance company
	 * @return InsuranceCompanyDetailVO object which contains all the insurancecompany details
	 * @exception throws TTKException
	 **/
	public BrokerDetailVO getBrokerCompanyDetail(Long lInsuranceParentId,Long lInsuranceCompanyID) throws TTKException {
		brokerDAO = (BrokerDAOImpl)this.getEmpanelmentDAO("broker");
		return brokerDAO.getBrokerCompanyDetail(lInsuranceParentId,lInsuranceCompanyID);
	}// End of InsuranceDetailVO getInsuranceCompanyDetail(Long lInsuranceCompanyID)

	/**
	 * This method adds or updates the insurance company  details
	 * The method also calls other methods on DAO to insert/update the insurance company  details to the database
	 * @param insuranceDetailVO InsuranceDetailVO object which contains the insurance comapny  details to be added/updated
	 * @return long value, Insurance Seq Id
	 * @exception throws TTKException
	 */
	public long addUpdateInsuranceCompany(BrokerDetailVO brokerDetailVO) throws TTKException {
		brokerDAO = (BrokerDAOImpl)this.getEmpanelmentDAO("broker");
		return brokerDAO.addUpdateInsuranceCompany(brokerDetailVO);
	}//end of addUpdateInsuranceCompany(InsuranceDetailVO insuranceDetailVO)

	/**
	 * This method delete's the insurance Company  records from the database.
	 * @param strInsSeqID String object which contains the insurance company  sequence id's to be deleted
	 * @return int  value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int deleteInsuranceCompany(String strInsSeqID) throws TTKException {
		brokerDAO = (BrokerDAOImpl)this.getEmpanelmentDAO("broker");
		return brokerDAO.deleteInsuranceCompany(strInsSeqID);
	}// End of deleteInsuranceCompany(String strInsSeqID)

	/**
	 * This method returns the ArrayList, which contains the InsuranceCompanyProductVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of InsuranceCompanyProductVO object's which contains the InsuranceCompany details
	 * @exception throws TTKException
	 */
	public ArrayList getProductList(ArrayList alSearchObjects) throws TTKException{
		insuranceProductDAO = (InsuranceProductDAOImpl)this.getEmpanelmentDAO("insuranceproduct");
		return insuranceProductDAO.getProductList(alSearchObjects);
	}// End of getProductList(ArrayList alSearchObjects)

	/**
	 * This method adds or updates the insurance Products
	 * The method also calls other methods on DAO to insert/update the insurance details to the database
	 * @param insuranceProductVO InsuranceProductVO object which contains the insuranceCompanyProduct details to be added/updated
	 * @return long value, Associate Office Sequence Id
	 * @exception throws TTKException
	 */
	public long addUpdateProduct(InsuranceProductVO insuranceProductVO) throws TTKException{
		insuranceProductDAO = (InsuranceProductDAOImpl)this.getEmpanelmentDAO("insuranceproduct");
		return insuranceProductDAO.addUpdateProduct(insuranceProductVO);
	}// End of addUpdateProduct(InsuranceProductVO insuranceProductVO)
	/**
	 * This method delete's the Products  records from the database.
	 * @param alProductList ArrayList object which contains the Product sequence id's to be deleted
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int deleteProduct(ArrayList alProductList)  throws TTKException{
		insuranceProductDAO = (InsuranceProductDAOImpl)this.getEmpanelmentDAO("insuranceproduct");
		return insuranceProductDAO.deleteProduct(alProductList);
	}// End of deleteProduct(ArrayList alProductList)  throws TTKException

	/**
	 * This method returns the ArrayList object of ProductVO's, which contains all the details about the products Associated to parent Insurance Company
	 * @param lInsSeqId long Insurance Sequence Id
	 * @return ArrayList object which contains all the details about the Insurance Company Associated products
	 * @exception throws TTKException
	 */
	public ArrayList getProductCode(long lInsSeqId) throws TTKException{
		insuranceProductDAO = (InsuranceProductDAOImpl)this.getEmpanelmentDAO("insuranceproduct");
		return insuranceProductDAO.getProductCode(lInsSeqId);
	}// End of getProductCode(long lInsSeqId)

	/**
	 * This method returns the ArrayList, which contains the InsuranceFeedbackVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of InsuranceFeedbackVO object's which contains the InsuranceCompany Feedback details
	 * @exception throws TTKException
	 */
	public ArrayList  getCompanyFeedbackList(ArrayList alSearchObjects) throws TTKException{
		insuranceFeedbackDAO = (InsuranceFeedbackDAOImpl)this.getEmpanelmentDAO("insurancefeedback");
		return insuranceFeedbackDAO.getCompanyFeedbackList(alSearchObjects);
	}// End of getCompanyFeedbackList(ArrayList alSearchObjects)
	
	/**
	 * This method returns the InsuranceFeedbackVO, which contains the InsuranceCompany Feedback details which are populated from the database
	 * @param lngInsFeedbackSeqID long value which contains Feedback Seq ID
	 * @return InsuranceFeedbackVO which contains the InsuranceCompany Feedback details
	 * @exception throws TTKException
	 */
	public InsuranceFeedbackVO getCompanyFeedback(long lngInsFeedbackSeqID) throws TTKException{
		insuranceFeedbackDAO = (InsuranceFeedbackDAOImpl)this.getEmpanelmentDAO("insurancefeedback");
		return insuranceFeedbackDAO.getCompanyFeedback(lngInsFeedbackSeqID);
	}//end of getCompanyFeedback(long lngInsFeedbackSeqID)

	/**
	 * This method adds or updates the insurance Feedback
	 * The method also calls other methods on DAO to insert/update the insurance details to the database
	 * @param insuranceFeedbackVO InsuranceFeedbackVO object which contains the insuranceFeedback details to be added/updated
	 * @return long value, Insurance Feedback Sequence Id
	 * @exception throws TTKException
	 */
	public long addUpdateCompanyFeedback(InsuranceFeedbackVO insuranceFeedbackVO) throws TTKException {
		insuranceFeedbackDAO = (InsuranceFeedbackDAOImpl)this.getEmpanelmentDAO("insurancefeedback");
		return insuranceFeedbackDAO.addUpdateCompanyFeedback(insuranceFeedbackVO);
	}// End of addUpdateCompanyFeedback(InsuranceFeedbackVO insuranceFeedbackVO)

	/**
     * This method delete's the Insurance Feedback   records from the database.
     * @param strFeedbackSeqID String object which contains the Feedback sequence id's to be deleted
     * @param lnguserSeqID for which user has loggedin
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteCompanyFeedback(String strFeedbackSeqID,long lnguserSeqID) throws TTKException{
		insuranceFeedbackDAO = (InsuranceFeedbackDAOImpl)this.getEmpanelmentDAO("insurancefeedback");
		return insuranceFeedbackDAO.deleteCompanyFeedback(strFeedbackSeqID,lnguserSeqID);
	}// End of deleteCompanyFeedback(String strFeedbackSeqID,long lnguserSeqID)

	/**
	 * This method returns the AraayList of InsuranceVO which contains the details of the HeadOffice and regional office and branch offices and regionael offices
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return AraayList of InsuranceVO which contains the details of the HeadOffice and regional office and branch offices and regionael offices
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getCompanyDetails(ArrayList alSearchObjects) throws TTKException
	{
		brokerDAO = (BrokerDAOImpl)this.getEmpanelmentDAO("broker");
		return brokerDAO.getCompanyDetails(alSearchObjects);
	}//End of getCompanyDetails(ArrayList alSearchObjects)

	/**
	 * This method returns the Associated Product Details
	 * @param lngAssociateOfficeSequenceID the associated Office Sequence ID for which the product details are required
	 * @return InsuranceProductVO which contains the Associated Product Details
	 * @exception throws TTKException
	 */
	public InsuranceProductVO getProductDetails(Long  lngAssociateOfficeSequenceID) throws TTKException {
		insuranceProductDAO = (InsuranceProductDAOImpl)this.getEmpanelmentDAO("insuranceproduct");
		return insuranceProductDAO.getProductDetails(lngAssociateOfficeSequenceID);
	}//getProductDetails(Long  lngAssociateOfficeSequenceID)
	
	//AK
    public ArrayList getBrokerDetails(ArrayList alSearchObjects) throws TTKException{
    	brokerDAO = (BrokerDAOImpl) this.getEmpanelmentDAO("broker");
		return brokerDAO.getBrokerDetails(alSearchObjects);
	}
    public ArrayList getBrokerDetailsList(ArrayList alSearchObjects,Long policySeqID ) throws TTKException{
    	brokerDAO = (BrokerDAOImpl) this.getEmpanelmentDAO("broker");
		return brokerDAO.getBrokerDetailsList(alSearchObjects,policySeqID);
	}
    
    public ArrayList<Object> getBrokerDetailsAssocGrpList(ArrayList alSearchObjects) throws TTKException {
    	brokerDAO = (BrokerDAOImpl) this.getEmpanelmentDAO("broker");
    	return brokerDAO.getBrokerDetailsAssocGrpList(alSearchObjects);
	}
    
    public int saveBrokerDeatils(ArrayList alSearchObjects) throws TTKException {
    	
    	brokerDAO = (BrokerDAOImpl) this.getEmpanelmentDAO("broker");
    	return brokerDAO.saveBrokerDeatils(alSearchObjects);
    }
    
   


    public int saveAssociatedBrokersUser(ArrayList alSaveList,Long endorsementSeqId) throws TTKException {
    	brokerDAO = (BrokerDAOImpl) this.getEmpanelmentDAO("broker");
   		return brokerDAO.saveAssociatedBrokersUser(alSaveList,endorsementSeqId);
   	}//end of saveBrokerContactDetails(ArrayList alSaveList)
    
    
  
    
    public int deleteFloatAssocGrp(String Brokerseqid,Long policySeqID,Long endorsementSeqId,String SwitchType) throws TTKException {
    	brokerDAO = (BrokerDAOImpl)this.getEmpanelmentDAO("broker");
		return brokerDAO.deleteFloatAssocGrp(Brokerseqid,policySeqID,endorsementSeqId,SwitchType);
	}//end of deleteFloatAssocGrp(Long lngFloatGrpAssocSeqID)
    
    public int disAssociateBrokersUser(ArrayList alSaveList,Long endorsementSeqId) throws TTKException {
    	brokerDAO = (BrokerDAOImpl) this.getEmpanelmentDAO("broker");
   		return brokerDAO.disAssociateBrokersUser(alSaveList,endorsementSeqId);
   	}//end of saveBrokerContactDetails(ArrayList alSaveList)
    
    public int getAssoDissoCount(Long policySeqID,Long brokerseqid) throws TTKException {
    	brokerDAO = (BrokerDAOImpl) this.getEmpanelmentDAO("broker");
   		return brokerDAO.getAssoDissoCount(policySeqID,brokerseqid);
   	}//end of saveBrokerContactDetails(ArrayList alSaveList)
    
    public ArrayList getAssociatedBrokerDetailsList(ArrayList alSearchObjects,Long policySeqID) throws TTKException {
    	brokerDAO = (BrokerDAOImpl) this.getEmpanelmentDAO("broker");
   		return brokerDAO.getAssociatedBrokerDetailsList(alSearchObjects,policySeqID);
   	}
    
    public String getBrokerAssocCount(Long policySeqID) throws TTKException{
    	brokerDAO = (BrokerDAOImpl) this.getEmpanelmentDAO("broker");
   		return brokerDAO.getBrokerAssocCount(policySeqID);
    }
    
}// End of InsuranceManagerBean


