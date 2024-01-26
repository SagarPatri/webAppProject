/**
 * @ (#) HospitalManagerBean.java Sep 21, 2005
 * Project      :
 * File         : HospitalManagerBean.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Sep 21, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :Swaroop Kaushik D.S
 * Modified date :07th May 2010
 * Reason        :modified deleteAssocCertificatesInfo(ArrayList<Object> alCertificateDelete),getCertiDetailInfo(long iCertSeqId)
 * saveAssocCertificateInfo (TdsCertificateVO tdsCertificateVO),getAssocCertificateList(ArrayList<Object> alSearchCriteria)
 */
package com.ttk.business.empanelment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import org.dom4j.Document;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.empanelment.AccountDAOImpl;
import com.ttk.dao.impl.empanelment.EmpanelmentDAOFactory;
import com.ttk.dao.impl.empanelment.FeedbackDetailDAOImpl;
import com.ttk.dao.impl.empanelment.HospitalDAOImpl;
import com.ttk.dao.impl.empanelment.MOUDAOImpl;
import com.ttk.dao.impl.empanelment.StatusDAOImpl;
import com.ttk.dao.impl.empanelment.ValidationDAOImpl;
import com.ttk.dto.administration.MOUDocumentVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.empanelment.AccountDetailVO;
import com.ttk.dto.empanelment.FeedbackDetailVO;
import com.ttk.dto.empanelment.HospitalDetailVO;
import com.ttk.dto.empanelment.HospitalVO;
import com.ttk.dto.empanelment.NetworkTypeVO;
import com.ttk.dto.empanelment.PreRequisiteVO;
import com.ttk.dto.empanelment.StatusVO;
import com.ttk.dto.empanelment.TdsCertificateVO;
import com.ttk.dto.empanelment.ValidationDetailVO;
@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class HospitalManagerBean implements HospitalManager {

	private EmpanelmentDAOFactory empanelmentDAOFactory = null;
	private HospitalDAOImpl hospitalDAO = null;
	private FeedbackDetailDAOImpl feedbackDetailDAO = null;
	private AccountDAOImpl accountDAO = null;
	private ValidationDAOImpl validationDAO = null;
	private StatusDAOImpl statusDAO = null;
	private MOUDAOImpl mouDAO = null;

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
			}//end of else if(strIdentifier!=null)
			else
				return null;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//end of getEmpanelmentDAO(String strIdentifier)

	/**
     * This method returns the ArrayList, which contains the HospitalVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of HospitalVO object's which contains the hospital details
     * @exception throws TTKException
     */
    public ArrayList getHospitalList(ArrayList alSearchObjects) throws TTKException
	{
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getHospitalList(alSearchObjects);
	}//end of getHospitalList(ArrayList alSearchObjects)

    /**
     * This method returns the Arraylist of PreAuthHospitalVO's  which contains Hospital details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthHospitalVO object which contains Hospital details
     * @exception throws TTKException
     */
    public ArrayList getPreAuthHospitalList(ArrayList alSearchCriteria) throws TTKException {
    	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
        return hospitalDAO.getPreAuthHospitalList(alSearchCriteria);
    }//end of getPreAuthHospitalList(ArrayList alSearchCriteria)

    /**
     * This method returns the ArrayList, which contains the HospitalVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of HospitalVO object's which contains the hospital empanelled details, which is used in Administration Products/Policies
     * @exception throws TTKException
     */
    public ArrayList getEmpanelledHospitalList(ArrayList alSearchObjects) throws TTKException {
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getEmpanelledHospitalList(alSearchObjects);
	}//end of getEmpanelledHospitalList(ArrayList alSearchObjects)

    /**
     * This method returns the ArrayList, which contains the HospitalVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of HospitalVO object's which contains the hospital Associated/Excluded details,which is used in Administration Products/Policies
     * @exception throws TTKException
     */
    public ArrayList getAssociatedExcludedList(ArrayList alSearchObjects) throws TTKException {
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getAssociatedExcludedList(alSearchObjects);
	}//end of getAssociatedExcludedList(ArrayList alSearchObjects)

	/**
	 * This method returns the HospitalDetailVO, which contains all the hospital details
	 * @param lHospSeqId Long object which contains the hospital seq id
	 * @return HospitalDetailVO object which contains all the hospital details
	 * @exception throws TTKException
	 */
	public HospitalDetailVO getHospitalDetail(Long lHospSeqId) throws TTKException
	{
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getHospitalDetail(lHospSeqId);
	}//end of getHospitalDetail(Long lHospSeqId)

	/**
	 * This method returns the HashMap,which contains the City Types associating the State
	 * @return HashMap containing City Types associating the State
	 * @exception throws TTKException
	 */
    public HashMap getCityInfo() throws TTKException {
    	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
    	return hospitalDAO.getCityInfo();
    }//end of getCityInfo()
    
    /**
	 * This method returns the HashMap,which contains the City Types associating the State
	 * @return HashMap containing City Types associating the State
	 * @exception throws TTKException
	 */
    public HashMap getCityInfo(String stateCode) throws TTKException {
    	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
    	return hospitalDAO.getCityInfo(stateCode);
    }//end of getCityInfo(stateCode)

	
	/**
	 * This method returns the ArrayList,which contains the State Types associating the Country
	 * @return HashMap containing State Types associating the Country
	 * @exception throws TTKException
	 */
    public ArrayList getStateInfo(String countryId) throws TTKException {
    	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
    	return hospitalDAO.getStateInfo(countryId);
    }//end of getStateInfo()
	/**
	 * This method adds or updates the hospital details
	 * The method also calls other methods on DAO to insert/update the hospital details to the database
	 * @param hospitalDetailVO HospitalDetailVO object which contains the hospital details to be added/updated
	 * @return long value, Hospital Seq Id
	 * @exception throws TTKException
	 */
	public long addUpdateHospital(HospitalDetailVO hospitalDetailVO) throws TTKException
	{
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.addUpdateHospital(hospitalDetailVO);
	}//end of addUpdateHospital(HospitalDetailVO hospitalDetailVO)

	/**
	 * This method delete's the hospital records from the database.
	 * @param alHospitalList ArrayList object which contains the hospital sequence id's to be deleted
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteHospital(ArrayList alHospitalList) throws TTKException
	{
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.deleteHospital(alHospitalList);
	}//end of deleteHospital(ArrayList alHospitalList)

	/**
	 * This method dis-associate's the hospital records from the database.
	 * @param alHospitalList ArrayList object which contains the hospital product sequence id's, to be dis-associate hospital records
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int disassociateHospital(ArrayList alHospitalList) throws TTKException {
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.disassociateHospital(alHospitalList);
	}//end of disassociateHospital(ArrayList alHospitalList)

	/**
	 * This method associate or excluded the hospital records to the database.
	 * @param lProdPolicySeqId Long ProductPolicySeq Id which contains the Hospital productpolicy seq id
	 * @param strStatusGeneralTypeId String which contains the status general type id for associate or exclude the hospital
	 * @param alAssocExcludedList ArrayList object which contains the hospital sequence id's to be associated or excluded
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int associateExcludeHospital(Long lProdPolicySeqId,String strStatusGeneralTypeId,ArrayList alAssocExcludedList) throws TTKException {
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.associateExcludeHospital(lProdPolicySeqId,strStatusGeneralTypeId,alAssocExcludedList);
	}//end of associateExcludeHospital(Long lProdPolicySeqId,String strStatusGeneralTypeId,ArrayList alAssocExcludedList)

	/**
	 * This method returns the StatusVO, which contains all the hospital status details
	 * @param lHospitalSeqId Long object which contains the Hospital sequence Id
	 * @return StatusVO object which contains all the hospital status details
	 * @exception throws TTKException
	 */
	public StatusVO getStatus(Long lHospitalSeqId) throws TTKException
	{
		statusDAO = (StatusDAOImpl)this.getEmpanelmentDAO("status");
		return statusDAO.getStatus(lHospitalSeqId);
	}//end of getStatus(Long lHospitalSeqId)

	/**
	 * This method updates the hospital status
	 * The method also calls other methods on DAO to update the hospital status to the database
	 * @param statusVO StatusVO object which contains the status details to be updated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int updateStatus(StatusVO statusVO) throws TTKException
	{
		statusDAO = (StatusDAOImpl)this.getEmpanelmentDAO("status");
		return statusDAO.updateStatus(statusVO);
	}//end of updateStatus(StatusVO statusVO)

	/**
	 * This method returns the HashMap,which contains the ReasonId,Reason Description
	 *@return HashMap containing Reason Type Id and Reason Description
	 * @exception throws TTKException
	 */
	public HashMap getReasonInfo() throws TTKException{
		statusDAO = (StatusDAOImpl)this.getEmpanelmentDAO("status");
		return statusDAO.getReasonInfo();
	}//end of getReasonInfo()

	/**
	 * This method returns the ArrayList, which contains the Hospital Discrepancy List , which are populated from the database
	 * @param hospitalVO HospitalVO object which contains the minimum details about the hospital discrepancy
	 * @return ArrayList of HospitalDetailVO object's which contains the hospital discrepancy details
	 * @exception throws TTKException
	 */
	public ArrayList getHospitalDiscrepancyList(HospitalVO hospitalVO) throws TTKException
	{
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getHospitalDiscrepancyList(hospitalVO);
	}//end of getHospitalDiscrepancyList(HospitalVO hospitalVO)

	/**
	 * This method updates the hospital discrepancy details
	 * The method also calls other methods on DAO to update the hospital discrepancy details to the database
	 * @param strDiscrepancyType String object which specifies whether the discrepancy flag has to be updated or not
	 * @param hospitalVO HospitalVO object which contains the minimum details about the hospital
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int updateDiscrepancyInfo(String strDiscrepancyType, HospitalVO hospitalVO) throws TTKException
	{
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.updateDiscrepancyInfo(strDiscrepancyType,hospitalVO);
	}//end of updateDiscrepancyInfo(String strDiscrepancyType, HospitalVO hospitalVO)

	/**
	 * This method returns the AccountDetailVO, which contains all the hospital account details
	 * @param lHospSeqId long which contains the Hospital Seq Id
	 * @return AccountDetailVO object which contains all the hospital account details
	 * @exception throws TTKException
	 */
	public AccountDetailVO getAccount(long lHospSeqId) throws TTKException
	{
		accountDAO = (AccountDAOImpl)this.getEmpanelmentDAO("account");
		return accountDAO.getAccount(lHospSeqId);
	}//end of getAccount(long lHospSeqId)
	
	//getting Data from ArrayList for Mulptile Accounts
	public ArrayList getAccountIntX(long lHospSeqId) throws TTKException
	{
		accountDAO = (AccountDAOImpl)this.getEmpanelmentDAO("account");
		return accountDAO.getAccountIntX(lHospSeqId);
	}//end of getAccount(long lHospSeqId)

	
	public ArrayList getPartnerAccountIntX(long PartnerSeqId) throws TTKException{
		accountDAO = (AccountDAOImpl)this.getEmpanelmentDAO("account");
		return accountDAO.getPartnerAccountIntX(PartnerSeqId);
	}//end of getAccount(long lHospSeqId)

	

	/**
	 * This method returns the AccountDetailVO, which contains all the getPartnerAccountDetails
	 * @param lHospSeqId long which contains the Hospital Seq Id
	 * @return AccountDetailVO object which contains all the getPartnerAccountDetails
	 * @exception throws TTKException
	 */
	public AccountDetailVO getPartnerAccountDetails(long partnerSeqId) throws TTKException
	{
		accountDAO = (AccountDAOImpl)this.getEmpanelmentDAO("account");
		return accountDAO.getPartnerAccountDetails(partnerSeqId);
	}//end of getAccount(long lHospSeqId)
	
	
	
	
	
	/**
	 * This method adds or updates the account details
	 * The method also calls other methods on DAO to insert/update the account details to the database
	 * @param accountDetailVO AccountDetailVO object which contains the account details to be added/updated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int addUpdatePartnerAccount(AccountDetailVO accountDetailVO) throws TTKException{
		accountDAO = (AccountDAOImpl)this.getEmpanelmentDAO("account");
		return accountDAO.addUpdatePartnerAccount(accountDetailVO);
	}//end of addUpdateAccount(AccountDetailVO accountDetailVO)

	
	
	
	
	/**
	 * This method adds or updates the account details
	 * The method also calls other methods on DAO to insert/update the account details to the database
	 * @param accountDetailVO AccountDetailVO object which contains the account details to be added/updated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int addUpdateAccount(AccountDetailVO accountDetailVO) throws TTKException
	{
		accountDAO = (AccountDAOImpl)this.getEmpanelmentDAO("account");
		return accountDAO.addUpdateAccount(accountDetailVO);
	}//end of addUpdateAccount(AccountDetailVO accountDetailVO)

	/**
	 * This method returns the AccountDetailVO, which contains all the general account History Details
	 * @param lEmpanelSeqId long which contains the Empanel Seq Id
	 * @return AccountDetailVO object which contains all the general account History Details
	 * @exception throws TTKException
	 */
	public AccountDetailVO getEmpanelmentFeeDetails(long lEmpanelSeqId) throws TTKException{
		accountDAO = (AccountDAOImpl)this.getEmpanelmentDAO("account");
		return accountDAO.getEmpanelmentFeeDetails(lEmpanelSeqId);
	}//end of getEmpanelmentFeeDetails(long lEmpanelSeqId)

    /**
     * This method returns the AccountDetailVO, which contains all the general account History Details
     * @param lngGuaranteeSeqID long which contains the Bank Guarantee Seq ID
     * @return AccountDetailVO object which contains all the general account History Details
     * @exception throws TTKException
     */
    public AccountDetailVO getBankGuaranteeDetails(long lngGuaranteeSeqID) throws TTKException {
        accountDAO = (AccountDAOImpl)this.getEmpanelmentDAO("account");
        return accountDAO.getBankGuaranteeDetails(lngGuaranteeSeqID);
    }//end of getBankGuaranteeDetails(long lngGuaranteeSeqID)

	/**
	 * This method returns the ArrayList, which contains the AccountDetailVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of AccountDetailVO object's which contains the Account Details of Bank Guarantee
	 * @exception throws TTKException
	 */
	public ArrayList getBankGuaranteeHistoryList(ArrayList alSearchObjects) throws TTKException
	{
		accountDAO = (AccountDAOImpl)this.getEmpanelmentDAO("account");
		return accountDAO.getBankGuaranteeHistoryList(alSearchObjects);
	}//end of getBankGuaranteeHistoryList(ArrayList alSearchObjects)

	/**
	 * This method returns the ArrayList, which contains the AccountDetailVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of AccountDetailVO object's which contains the Account Details of Empanelment fee HistoryList
	 * @exception throws TTKException
	 */
	public ArrayList getEmpanelmentFeeHistoryList(ArrayList alSearchObjects) throws TTKException
	{
		accountDAO = (AccountDAOImpl)this.getEmpanelmentDAO("account");
		return accountDAO.getEmpanelmentFeeHistoryList(alSearchObjects);
	}// End of getEmpanelmentFeeHistoryList(ArrayList alSearchObjects)

	/**
     * This method returns the ArrayList, which contains the ValidationDetailVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of ValidationDetailVO object's which contains the hospital validation details
     * @exception throws TTKException
     */
    public ArrayList getValidationList(ArrayList alSearchObjects) throws TTKException
	{
		validationDAO = (ValidationDAOImpl)this.getEmpanelmentDAO("validation");
		return validationDAO.getValidationList(alSearchObjects);
	}//end of getValidationList(ArrayList alSearchObjects)

    /**
     * This method returns the validation details
     * @param lngValidationSeqID the key to fetch the validation details
     * @return ValidationDetailVO the object which contains the details of validation
     * @@exception throws TTKException
     */
    public ValidationDetailVO getValidationDetail(Long lngValidationSeqID) throws TTKException
    {
    	validationDAO = (ValidationDAOImpl)this.getEmpanelmentDAO("validation");
    	return validationDAO.getValidationDetail(lngValidationSeqID);
    }//End of getValidationDetail(Long lngValidationSeqID)

	/**
	 * This method adds or updates the hospital validation details
	 * The method also calls other methods on DAO to insert/update the hospital validation details to the database
	 * @param validationDetailVO ValidationDetailVO object which contains the hospital validation details to be added/updated
	 * @return long value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public long addUpdateValidation(ValidationDetailVO validationDetailVO) throws TTKException
	{
		validationDAO = (ValidationDAOImpl)this.getEmpanelmentDAO("validation");
		return validationDAO.addUpdateValidation(validationDetailVO);
	}//end of addUpdateValidation(ValidationDetailVO validationDetailVO)

	/**
     * This method delete's the hospital validation records from the database.
     * @param strValidateSeqID String object which contains the hospital validation sequence id's to be deleted
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int deleteValidation(String strValidateSeqID) throws TTKException
	{
		validationDAO = (ValidationDAOImpl)this.getEmpanelmentDAO("validation");
		return validationDAO.deleteValidation(strValidateSeqID);
	}//end of deleteValidation(String strValidateSeqID)

	/**
	 * This method returns the ArrayList, which contains the FeedbackDetailVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of Feedba* @throws TTKException
	 * @exception throws TTKException
	 */
	public ArrayList getFeedbackList(ArrayList alSearchObjects) throws TTKException
	{
		feedbackDetailDAO = (FeedbackDetailDAOImpl)this.getEmpanelmentDAO("feedback");
		return feedbackDetailDAO.getFeedbackList(alSearchObjects);
	}//end of getFeedbackList(ArrayList alSearchObjects, String sortColumnName, String sortOrder, String startRow, String endRow)

	/**
	 * This method returns the ArrayList of FeedbackDetailVO's, which contains the hospital feedback details
	 * @param lngFeedBackSeqID Long object which contains the feed back seq id
	 * @return ArrayList of FeedBackDetailVO object's which contains the hospital feedback details
	 * @exception throws TTKException
	 */
	public ArrayList getFeedback(Long lngFeedBackSeqID) throws TTKException
	{
		feedbackDetailDAO = (FeedbackDetailDAOImpl)this.getEmpanelmentDAO("feedback");
		return feedbackDetailDAO.getFeedback(lngFeedBackSeqID);
	}//end of getFeedback(int iFeedbackID)

	/**
	 * This method adds or updates the hospital feedback details
	 * The method also calls other methods on DAO to insert/update the hospital feedback details to the database
	 * @param feedbackDetailVO FeedbackDetailVO object which contains the hospital feedback details to be added/updated
	 * @return long value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public long addUpdateFeedback(FeedbackDetailVO feedbackDetailVO) throws TTKException
	{
		feedbackDetailDAO = (FeedbackDetailDAOImpl)this.getEmpanelmentDAO("feedback");
		return feedbackDetailDAO.addUpdateFeedback(feedbackDetailVO);
	}//end of addUpdateFeedback(FeedbackDetailVO feedbackDetailVO)

	/**
	 * This method delete's the hospital feedback records from the database.
	 * @param strDeleteSeqID String the hospital feedback sequence id's to be deleted
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteFeedback(String strDeleteSeqID) throws TTKException
	{
		feedbackDetailDAO = (FeedbackDetailDAOImpl)this.getEmpanelmentDAO("feedback");
		return feedbackDetailDAO.deleteFeedback(strDeleteSeqID);
	}//end of deleteFeedback(ArrayList alFeedbackList)

	/**
	 * This method returns the ArrayList, which contains all the details about the hospital feedback questions and answers list
	 * @return ArrayList which contains all the details about the hospital feedback questions and answers list
	 * @exception throws TTKException
	 */
	public ArrayList getQAList() throws TTKException{
		feedbackDetailDAO = (FeedbackDetailDAOImpl)this.getEmpanelmentDAO("feedback");
		return feedbackDetailDAO.getQAList();
	}//end of getQAList()

	/**
	 * This method returns the modified MOU document
	 * @param lHospSeqId long object which contains the Hospital seq id
	 * @return Document the XML based MOU document
	 * @exception throws TTKException
	 */
	public Document getMOUDocument(long lHospSeqId) throws TTKException{
		mouDAO = (MOUDAOImpl)this.getEmpanelmentDAO("mou");
		return mouDAO.getMOUDocument(lHospSeqId);
	}//end of getMOUDocument(long lHospSeqId)

	/**
	 * This method adds or updates the MOU details
	 * @param mouDocument Document object which contains MOU details
	 * @param lHospSeqId Long object which contains the hospital sequence id
	 * @param lUpdatedBy Long object which contains the user sequence id
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int addUpdateDocument(Document mouDocument, Long lHospSeqId, Long lUpdatedBy) throws TTKException{
		mouDAO = (MOUDAOImpl)this.getEmpanelmentDAO("mou");
		return mouDAO.addUpdateDocument(mouDocument, lHospSeqId, lUpdatedBy);
	}//end of addUpdateDocument(Document mouDocument, Long lHospSeqId)
	
	/**
	 * This method saves the Copay Information to the Hospital associated to Product/Policy or all the 
	 * Hospitals associated to Product/Policy 
	 * @param hospCopayVO HospitalCopayVO object which contains the Copay details to be saved
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int saveAssocHospCopay(ArrayList alHospCopay) throws TTKException {
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.saveAssocHospCopay(alHospCopay);
	}//end of saveAssocHospCopay(ArrayList alHospCopay)
	
	/**
	 * This method deletes the Copay Information to the selected Hospital(s) associated to Product/Policy 
	 *  
	 * @param alHospCopay ArrayList object which contains the Copay details to be deleted
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteAssocHospCopay(ArrayList alHospCopay) throws TTKException {
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.deleteAssocHospCopay(alHospCopay);
	}//end of deleteAssocHospCopay(ArrayList alHospCopay)
	
	/**
     * This method returns the ArrayList, which contains the TdsCertificateVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of TdsCertificateVO's object's which contains the details of the TDS certificate Information
     * @exception throws TTKException
     */
	public ArrayList<Object> getAssocCertificateList(ArrayList<Object> alSearchCriteria) throws TTKException
	{
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
    	return hospitalDAO.getAssocCertificateList(alSearchCriteria);
	}//end of getAssocCertificateList(ArrayList alAssociatedCertificatesList)
	
	  /**
     * This method saves the Tds Certificate details
     * @param tdsCertificateVO TdsCertificateVO object which contains the Tds Certificate details
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
	public int saveAssocCertificateInfo (TdsCertificateVO tdsCertificateVO) throws TTKException
	{
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.saveAssocCertificateInfo(tdsCertificateVO);
	}////end of saveAssocCertificateInfo (TdsCertificateVO tdsCertificateVO)
	
	 /**
     * This method fetches the particular Tds Certificate Info as TdsCertificateVO
     * @param Long CertificationId
     * @return Tds Certificate details in the form of TdsCertificateVO
     * @exception throws TTKException
     */
	public TdsCertificateVO getCertiDetailInfo(long iCertSeqId) throws TTKException
	{
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
    	return hospitalDAO.getCertiDetailInfo(iCertSeqId);
	}//end of getCertiDetailInfo(long iCertSeqId)
	
	/**
     * This method deletes the particular TdsCertificate Info from the database,based on the certificate seq id's present in the arraylist
     * @param ArrayList alCertificateDelete
     * @return int It signifies the number of rows deleted in the database. 
     * @exception throws TTKException
     */
	public int deleteAssocCertificatesInfo(ArrayList<Object> alCertificateDelete,String gateway) throws TTKException
	{
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.deleteAssocCertificatesInfo(alCertificateDelete,gateway);
	}//end of deleteAssocCertificatesInfo(ArrayList<Object> alCertificateDelete)
	/**
     * This method saves the File Uploads details
     * @param mouDocumentVO mouDocumentVO object which contains the Tds Certificate details
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
	public int saveMouUploads(MOUDocumentVO mouDocumentVO,ArrayList alFileAUploadList,Long userSeqId,String hospSeqId,String origFileName,InputStream inputStream,int formFileSize) throws TTKException
    {
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.saveMouUploads(mouDocumentVO,alFileAUploadList,userSeqId,hospSeqId,origFileName,inputStream,formFileSize);
    }//end of saveMouUploads(MOUDocumentVO mouDocumentVO,ArrayList alFileAUploadList) throws TTKException
	public int savepreAuthUploads(MOUDocumentVO mouDocumentVO,ArrayList alFileAUploadList,Long userSeqId,String hospSeqId,String origFileName,InputStream inputStream,int formFileSize) throws TTKException
    {
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.savepreAuthUploads(mouDocumentVO,alFileAUploadList,userSeqId,hospSeqId,origFileName,inputStream,formFileSize);
    }//end of savepreAuthUploads(MOUDocumentVO mouDocumentVO,ArrayList alFileAUploadList) throws TTKException
	
	
	/**
     * This method Fetches the list of files uploaded details
     * @param mouDocumentVO mouDocumentVO object which contains the Tds Certificate details
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
	public ArrayList<Object> getMouUploadsList(String hospSeqId) throws TTKException
    {
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getMouUploadsList(hospSeqId);
    }//end of saveMouUploads(MOUDocumentVO mouDocumentVO,ArrayList alFileAUploadList) throws TTKException
	public ArrayList<Object> getPreAuthUploadsList(String hospSeqId) throws TTKException
    {
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getPreAuthUploadsList(hospSeqId);
    }//end of saveMouUploads(MOUDocumentVO mouDocumentVO,ArrayList alFileAUploadList) throws TTKException
	
	/**
     * This method Fetches the License Numbers for Providers
     * @exception throws TTKException
     */
	public ArrayList<Object> getLicenceNumbers(String prviderId,String provName,String strIdentifier) throws TTKException
    {
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getLicenceNumbers(prviderId,provName,strIdentifier);
    }//end of getLicenceNumbers(String prviderId) throws TTKException
	
	/**
     * This method Fetches the License Numbers for Providers
     * @exception throws TTKException
     */
	public ArrayList<Object> getLicenceNoForPreEmp(String provName) throws TTKException
    {
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getLicenceNoForPreEmp(provName);
    }//end of getLicenceNumbers(String prviderId) throws TTKException
	
	/**
	 * 
     * To Generate Credentials for preRequisitebased on Provider name 
     * This method Fetches the License Numbers for Providers
     * @exception throws TTKException
     */
    public PreRequisiteVO generateCredentialsforPreRequisite(PreRequisiteVO preRequisiteVO,String strContactSeqId) throws TTKException {
    	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.generateCredentialsforPreRequisite(preRequisiteVO,strContactSeqId);
	}//end of generateCredentialsforPreRequisite(PreRequisiteVO preRequisiteVO,String strContactSeqId) throws TTKException
    
    /**
	 * 
     * To Generate Credentials for preRequisitebased on Provider name 
     * This method Fetches the License Numbers for Providers
     * @exception throws TTKException
     */
    public int sendCredentialsforPreRequisite(PreRequisiteVO preRequisiteVO,String strContactSeqId, String iContactSeqId) throws TTKException {
    	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.sendCredentialsforPreRequisite(preRequisiteVO,strContactSeqId,iContactSeqId);
	}//end of sendCredentialsforPreRequisite(PreRequisiteVO preRequisiteVO,String strContactSeqId) throws TTKException
    
    /**
	 * 
     * To Generate Dashboard for a Provider name 
     * This method Fetches the License Numbers for Providers
     * @exception throws TTKException
     */
    public ArrayList<Object> getProviderDashBoard() throws TTKException {
    	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getProviderDashBoard();
	}//end of sendCredentialsforPreRequisite(PreRequisiteVO preRequisiteVO,String strContactSeqId) throws TTKException

    /**
	 * 
    * To Generate Total Providers in Dashboard a Provider name 
    * This method Fetches the License Numbers for Providers
    * @exception throws TTKException
    */
   public ArrayList<Object> getTotalProviders() throws TTKException {
   	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getTotalProviders();
	}//end of sendCredentialsforPreRequisite(PreRequisiteVO preRequisiteVO,String strContactSeqId) throws TTKException

   
   /**
	 * 
   * To Get Total Payers Adding Empanelment screen
   * @exception throws TTKException
   */
  public String getAllPayersList() throws TTKException {
  	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getAllPayersList();
	}//end of sendCredentialsforPreRequisite(PreRequisiteVO preRequisiteVO,String strContactSeqId) throws TTKException

  
  public int getIsdCode(String countryCode) throws TTKException {
	  	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
			return hospitalDAO.getIsdCode(countryCode);
		}//end of sendCredentialsforPreRequisite(PreRequisiteVO preRequisiteVO,String strContactSeqId) throws TTKException



  
  /**
   * get Network types  
   */
  public ArrayList<NetworkTypeVO> getNetworkTypeList() throws TTKException {
	   	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
			return hospitalDAO.getNetworkTypeList();
		}//end of getNetworkTypeList() throws TTKException

  /**
   * Save Network types  
   */
  public int saveNetworkType(NetworkTypeVO networkTypeVO,Long userSeqId) throws TTKException {
  	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
	return hospitalDAO.saveNetworkType(networkTypeVO,userSeqId);
	}//end of saveNetworkType(NetworkTypeVO networkTypeVO,Long userSeqId) throws TTKException
  
  /**
   * Modify Network types  
   */
 public int modifyNetworkType(String[] strResult,String[] payer,Long userSeqId) throws TTKException {
	  	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.modifyNetworkType(strResult,payer,userSeqId);
		}//end of saveNetworkType(NetworkTypeVO networkTypeVO,Long userSeqId) throws TTKException
  
  /**
   * Modify Network types  
   */
  public ArrayList<String[]> getHospitalNetworkTypes(Long lngHospSeqId) throws TTKException {
  	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
	return hospitalDAO.getHospitalNetworkTypes(lngHospSeqId);
	}//end of saveNetworkType(NetworkTypeVO networkTypeVO,Long userSeqId) throws TTKException
  
  
  /**
   * Get Network types   from History Table
   */
  public ArrayList<NetworkTypeVO> getNetworkHistory() throws TTKException {
	  	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getNetworkHistory();
		}//end of saveNetworkType(NetworkTypeVO networkTypeVO,Long userSeqId) throws TTKException
  
  public String getHaadCategories(String hosp_seq_id) throws TTKException {
	  	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getHaadCategories(hosp_seq_id);
		}//end of saveNetworkType(NetworkTypeVO networkTypeVO,Long userSeqId) throws TTKException
  
  
  
  public ArrayList<Object> getHaadEditHeaders(String hosp_seq_id) throws TTKException {
	  	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getHaadEditHeaders(hosp_seq_id);
		
		}//end of saveNetworkType(hosp_seq_id) throws TTKException
  public ArrayList<Object> getHaadCategories(String hosp_seq_id,String temp,String primaryNetwork) throws TTKException {
	  	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getHaadCategories(hosp_seq_id,temp,primaryNetwork);
		}//end of saveNetworkType(NetworkTypeVO networkTypeVO,Long userSeqId) throws TTKException
  
  public ArrayList<Object> getHaadColumnHEaders() throws TTKException {
	  	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getHaadColumnHEaders();
		}//end of getHaadColumnHEaders
  
  
  public int saveHaadCategories(ArrayList<String> alColumnHeaders,String[] cacheId,String[] factor,String[] baseRate,String[] gap,String[] margin,String[] discount,
	  		String hospSeqId,Long userSeqId,String networkType,String haadTarrifStartDt,String haadTarrifEndDt)throws TTKException{
	  	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.saveHaadCategories(alColumnHeaders,cacheId,factor,baseRate,gap,margin,discount,hospSeqId,userSeqId,networkType,haadTarrifStartDt,haadTarrifEndDt);
		}//end of saveHaadCategories
  
  public int updateHaadCategories(String eligibleNetworks,String haadGroup,String haadfactor,String haadTarrifStartDt,String haadTarrifEndDt,String factorVal,String hosp_seq_id,Long userSeqId)throws TTKException{
	  	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.updateHaadCategories(eligibleNetworks,haadGroup,haadfactor,haadTarrifStartDt,haadTarrifEndDt,factorVal,hosp_seq_id,userSeqId);
		}//end of getHaadColumnHEaders

  public ArrayList<CacheObject> getEligibleNetworks(String hosp_seq_id) throws TTKException {
	  	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getEligibleNetworks(hosp_seq_id);
		}//end of getEligibleNetworks(String hosp_seq) throws TTKException
  
  public ArrayList<Object> getHaadFactorsHostory(String hosp_seq_id) throws TTKException {
	  	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getHaadFactorsHostory(hosp_seq_id);
  }

  public ArrayList getOldLicenseList(Long lngHospSeqId)
			throws TTKException {
		hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		return hospitalDAO.getOldLicenseList(lngHospSeqId);
	}


public Long UpdateHospitalLicense(HospitalDetailVO hospitalDetailVO)
		throws TTKException {
	
	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
	return hospitalDAO.UpdateHospitalLicense(hospitalDetailVO);
}


public HospitalDetailVO getOldLicenseUpdateDetails(Long hospSeqId)
		throws TTKException {
	
	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
	return hospitalDAO.getOldLicenseUpdateDetails(hospSeqId);
}


public ArrayList<Object> getBrokerList(String regAuthority) throws TTKException {
	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
	return hospitalDAO.getBrokerList(regAuthority);
}
  
  
	/**
	 * This method returns the StatusVO, which contains all the partner status details
	 * @param lHospitalSeqId Long object which contains the Partner sequence Id
	 * @return StatusVO object which contains all the hospital status details
	 * @exception throws TTKException
	 */
	public StatusVO getPartnerStatus(Long lPartnerSeqId) throws TTKException
	{
		statusDAO = (StatusDAOImpl)this.getEmpanelmentDAO("status");
		return statusDAO.getPartnerStatus(lPartnerSeqId);
	}//end of getStatus(Long lPartnerSeqId)
	
	/**
	 * This method returns the ArrayList, which contains the FeedbackDetailVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of Feedba* @throws TTKException
	 * @exception throws TTKException
	 */
	public ArrayList getPartnerFeedbackList(ArrayList alSearchObjects) throws TTKException
	{
		feedbackDetailDAO = (FeedbackDetailDAOImpl)this.getEmpanelmentDAO("feedback");
		return feedbackDetailDAO.getPartnerFeedbackList(alSearchObjects);
	}//end of getFeedbackList(ArrayList alSearchObjects, String sortColumnName, String sortOrder, String startRow, String endRow)

	/**
	 * This method returns the ArrayList, which contains all the details about the partner feedback questions and answers list
	 * @return ArrayList which contains all the details about the partner feedback questions and answers list
	 * @exception throws TTKException
	 */
	public ArrayList getQAPartnerList() throws TTKException{
		feedbackDetailDAO = (FeedbackDetailDAOImpl)this.getEmpanelmentDAO("feedback");
		return feedbackDetailDAO.getQAPartnerList();
	}//end of getQAList()
	
	/**
	 * This method adds or updates the partner feedback details
	 * The method also calls other methods on DAO to insert/update the partner feedback details to the database
	 * @param feedbackDetailVO FeedbackDetailVO object which contains the partner feedback details to be added/updated
	 * @return long value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public long addUpdatePartnerFeedback(FeedbackDetailVO feedbackDetailVO) throws TTKException
	{
		feedbackDetailDAO = (FeedbackDetailDAOImpl)this.getEmpanelmentDAO("feedback");
		return feedbackDetailDAO.addUpdatePartnerFeedback(feedbackDetailVO);
	}//end of addUpdateFeedback(FeedbackDetailVO feedbackDetailVO)
		
	/**
	 * This method returns the ArrayList of FeedbackDetailVO's, which contains the partner feedback details
	 * @param lngFeedBackSeqID Long object which contains the feed back seq id
	 * @return ArrayList of FeedBackDetailVO object's which contains the partner feedback details
	 * @exception throws TTKException
	 */
	public ArrayList getPartnerFeedback(Long lngFeedBackSeqID) throws TTKException
	{
		feedbackDetailDAO = (FeedbackDetailDAOImpl)this.getEmpanelmentDAO("feedback");
		return feedbackDetailDAO.getPartnerFeedback(lngFeedBackSeqID);
	}//end of getFeedback(int iFeedbackID)
	
	/**
	 * This method delete's the partner feedback records from the database.
	 * @param strDeleteSeqID String the partner feedback sequence id's to be deleted
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int deletePartnerFeedback(String strDeleteSeqID) throws TTKException
	{
		feedbackDetailDAO = (FeedbackDetailDAOImpl)this.getEmpanelmentDAO("feedback");
		return feedbackDetailDAO.deletePartnerFeedback(strDeleteSeqID);
	}//end of deleteFeedback(ArrayList alFeedbackList)
	
	/**
	 * This method updates the partner status
	 * The method also calls other methods on DAO to update the hospital status to the database
	 * @param statusVO StatusVO object which contains the status details to be updated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	
	public int updatePartnerStatus(StatusVO statusVO) throws TTKException
	{
		statusDAO = (StatusDAOImpl)this.getEmpanelmentDAO("status");
		return statusDAO.updatePartnerStatus(statusVO);
	}//end of updateStatus(StatusVO statusVO)
	
	
	
	 public ArrayList getProviderTariffReport() throws TTKException
	    {
		 	hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		 	return hospitalDAO.getProviderTariffReport();
	    }//end of getProviderTariffReport()
	 
	 public String getAllNetworkdata() throws TTKException
		{
		 hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		        return hospitalDAO.getAllNetworkdata();
		 
		}
	 
	 public long addIrdrg(HospitalDetailVO hospitalDetailVO) throws TTKException
		{
		 hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
			return hospitalDAO.addIrdrg(hospitalDetailVO);
		}
	 
	 public ArrayList<HospitalDetailVO> getIrdrgParameters(Long hospSeqId) throws TTKException
		{
		 hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		 return hospitalDAO.getIrdrgParameters(hospSeqId);
		}
	 
	 
	 public int deleteIrdrg(Long dhaNagSeqId,Long userSeqId) throws TTKException
	 {
		 hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
	      return hospitalDAO.deleteIrdrg(dhaNagSeqId,userSeqId);
	 }
	 
	 public ArrayList<Object> getIrdrgDetails(Long dhaNagSeqId) throws TTKException
	 {
		 hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
	      return hospitalDAO.getIrdrgDetails(dhaNagSeqId);
	 }
	 
	 public ArrayList<HospitalDetailVO> getIrdrgAuditLogs(Long dhaNagSeqId) throws TTKException
		{
		 hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
		 return hospitalDAO.getIrdrgAuditLogs(dhaNagSeqId);
		}
	 
	 
	 
	 public int deleteDocs(ArrayList alDeleteBatch) throws TTKException {
		 hospitalDAO = (HospitalDAOImpl)this.getEmpanelmentDAO("hospital");
			return hospitalDAO.deleteDocs(alDeleteBatch);
		}
	 
	
}//end of class HospitalManagerBean
