/**
 * @ (#) HospitalManager.java Sep 21, 2005
 * Project      : 
 * File         : HospitalManager.java
 * Author       : Nagaraj D V
 * Company      : 
 * Date Created : Sep 21, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :  Ramakrishna K M,Swaroop Kaushik D.S
 * Modified date :  Oct 01,2005
 * Reason        :  Modified Method signature of getAccount()
 */ 
package com.ttk.business.empanelment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.dom4j.Document;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
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

import javax.ejb.Local;

@Local

public interface HospitalManager {
	
	
	
    /**
	 * This method returns the ArrayList, which contains the HospitalVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of HospitalVO object's which contains the hospital details
	 * @exception throws TTKException
	 */
	public ArrayList getHospitalList(ArrayList alSearchObjects) throws TTKException;
	
	/**
     * This method returns the Arraylist of PreAuthHospitalVO's  which contains Hospital details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthHospitalVO object which contains Hospital details
     * @exception throws TTKException
     */
    public ArrayList getPreAuthHospitalList(ArrayList alSearchCriteria) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the HospitalVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of HospitalVO object's which contains the hospital empanelled details, which is used in Administration Products/Policies
	 * @exception throws TTKException
	 */
	public ArrayList getEmpanelledHospitalList(ArrayList alSearchObjects) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the HospitalVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of HospitalVO object's which contains the hospital Associated/Excluded details,which is used in Administration Products/Policies
	 * @exception throws TTKException
	 */
	public ArrayList getAssociatedExcludedList(ArrayList alSearchObjects) throws TTKException;
	
	/**
	 * This method returns the HospitalDetailVO, which contains all the hospital details
	 * @param lHospSeqId Long object which contains the hospital seq id
	 * @return HospitalDetailVO object which contains all the hospital details
	 * @exception throws TTKException 
	 */
	public HospitalDetailVO getHospitalDetail(Long lHospSeqId) throws TTKException;
	
	/**
	 * This method returns the HashMap,which contains the City Types associating the State
	 * @return HashMap containing City Types associating the State
	 * @exception throws TTKException
	 */
    public HashMap getCityInfo() throws TTKException; 
    
    /**
	 * This method returns the HashMap,which contains the City Types associating the State
	 * @return HashMap containing City Types associating the State
	 * @exception throws TTKException
	 */
    public HashMap getCityInfo(String stateCode) throws TTKException; 
	
  //getting Data from ArrayList for Mulptile Accounts
    public ArrayList getPartnerAccountIntX(long PartnerSeqId) throws TTKException;
    
    
    public AccountDetailVO getPartnerAccountDetails(long partnerSeqId) throws TTKException;
    
		public ArrayList getStateInfo(String countryId) throws TTKException; 
	
	
	public int addUpdatePartnerAccount(AccountDetailVO accountDetailVO) throws TTKException;

	/**
	 * This method adds or updates the hospital details   
	 * The method also calls other methods on DAO to insert/update the hospital details to the database 
	 * @param hospitalDetailVO HospitalDetailVO object which contains the hospital details to be added/updated
	 * @return long value, Hospital Seq Id
	 * @exception throws TTKException
	 */
	public long addUpdateHospital(HospitalDetailVO hospitalDetailVO) throws TTKException;
	
	/**
	 * This method delete's the hospital records from the database.  
	 * @param alHospitalList ArrayList object which contains the hospital sequence id's to be deleted
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteHospital(ArrayList alHospitalList) throws TTKException;
	
	/**
	 * This method dis-associate's the hospital records from the database.  
	 * @param alHospitalList ArrayList object which contains the hospital product sequence id's, to be dis-associate hospital records
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int disassociateHospital(ArrayList alHospitalList) throws TTKException;
	
	/**
	 * This method associate or excluded the hospital records to the database.
	 * @param lProdPolicySeqId Long Product Policy Seq Id which contains the Hospital product Policy seq id
	 * @param strStatusGeneralTypeId String which contains the status general type id for associate or exclude the hospital  
	 * @param alAssocExcludedList ArrayList object which contains the hospital sequence id's to be associated or excluded
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int associateExcludeHospital(Long lProdPolicySeqId,String strStatusGeneralTypeId,ArrayList alAssocExcludedList) throws TTKException;
	
	/**
	 * This method returns the StatusVO, which contains all the hospital status details
	 * @param lHospitalSeqId Long object which contains the Hospital sequence Id
	 * @return StatusVO object which contains all the hospital status details
	 * @exception throws TTKException
	 */
	public StatusVO getStatus(Long lHospitalSeqId) throws TTKException;
	
	/**
	 * This method updates the hospital status   
	 * The method also calls other methods on DAO to update the hospital status to the database 
	 * @param statusVO StatusVO object which contains the status details to be updated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int updateStatus(StatusVO statusVO) throws TTKException;
	
	/**
	 * This method returns the HashMap,which contains the ReasonId,Reason Description  
	 * @return HashMap containing Reason Type Id and Reason Description
	 * @exception throws TTKException
	 */
	public HashMap getReasonInfo() throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the Hospital Discrepancy List , which are populated from the database
	 * @param hospitalVO HospitalVO object which contains the minimum details about the hospital discrepancy
	 * @return ArrayList of HospitalDetailVO object's which contains the hospital discrepancy details
	 * @exception throws TTKException
	 */
	public ArrayList getHospitalDiscrepancyList(HospitalVO hospitalVO) throws TTKException;
	
	/**
	 * This method updates the hospital discrepancy details   
	 * The method also calls other methods on DAO to update the hospital discrepancy details to the database 
	 * @param strDiscrepancyType String object which specifies whether the discrepancy flag has to be updated or not
	 * @param hospitalVO HospitalVO object which contains the minimum details about the hospital
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int updateDiscrepancyInfo(String strDiscrepancyType, HospitalVO hospitalVO) throws TTKException;
	
	/**
	 * This method returns the AccountDetailVO, which contains all the hospital account details
	 * @param lHospSeqId long which contains the Hospital Seq Id
	 * @return AccountDetailVO object which contains all the hospital account details
	 * @exception throws TTKException
	 */
	public AccountDetailVO getAccount(long lHospSeqId) throws TTKException;
	
	//getting Data from ArrayList for Mulptile Accounts
	public ArrayList getAccountIntX(long lHospSeqId) throws TTKException;
	
	/**
	 * This method adds or updates the account details   
	 * The method also calls other methods on DAO to insert/update the account details to the database 
	 * @param accountDetailVO AccountDetailVO object which contains the account details to be added/updated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int addUpdateAccount(AccountDetailVO accountDetailVO) throws TTKException;
	
	/**
	 * This method returns the AccountDetailVO, which contains all the general account History Details
	 * @param lEmpanelSeqId long which contains the Empanel Seq Id
	 * @return AccountDetailVO object which contains all the general account History Details
	 * @exception throws TTKException
	 */
	public AccountDetailVO getEmpanelmentFeeDetails(long lEmpanelSeqId) throws TTKException;
    
    /**
     * This method returns the AccountDetailVO, which contains all the general account History Details
     * @param lngGuaranteeSeqID long which contains the Bank Guarantee Seq ID
     * @return AccountDetailVO object which contains all the general account History Details
     * @exception throws TTKException
     */
    public AccountDetailVO getBankGuaranteeDetails(long lngGuaranteeSeqID) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the AccountDetailVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of AccountDetailVO object's which contains the Account Details of Bank Guarantee
	 * @exception throws TTKException
	 */
	public ArrayList getBankGuaranteeHistoryList(ArrayList alSearchObjects) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the AccountDetailVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of AccountDetailVO object's which contains the Account Details of Empanelment fee HistoryList
	 * @exception throws TTKException
	 */
	public ArrayList getEmpanelmentFeeHistoryList(ArrayList alSearchObjects) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the ValidationDetailVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ValidationDetailVO object's which contains the hospital validation details
	 * @exception throws TTKException
	 */
	public ArrayList getValidationList(ArrayList alSearchObjects) throws TTKException;
	
	/**
	 * This method returns the validation details
	 * @param lngValidationSeqID the key to fetch the validation details
	 * @return ValidationDetailVO the object which contains the details of validation
	 * @@exception throws TTKException
	 */
    public ValidationDetailVO getValidationDetail(Long lngValidationSeqID) throws TTKException;
	
	/**
	 * This method adds or updates the hospital validation details   
	 * The method also calls other methods on DAO to insert/update the hospital validation details to the database 
	 * @param validationDetailVO ValidationDetailVO object which contains the hospital validation details to be added/updated
	 * @return long value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public long addUpdateValidation(ValidationDetailVO validationDetailVO) throws TTKException;
	
	/**
	 * This method delete's the hospital validation records from the database.  
	 * @param strValidateSeqID String object which contains the hospital validation sequence id's to be deleted
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteValidation(String strValidateSeqID) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the FeedbackDetailVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of FeedbackDetailVO object's which contains the hospital feedback details
	 * @exception throws TTKException
	 */
	public ArrayList getFeedbackList(ArrayList alSearchObjects) throws TTKException;
	
	/**
	 * This method returns the ArrayList of FeedbackDetailVO's, which contains the hospital feedback details
	 * @param lngFeedBackSeqID Long object which contains the feed back seq id
	 * @return ArrayList FeedBackDetailVO object's which contains the hospital feedback details
	 * @exception throws TTKException
	 */
	public ArrayList getFeedback(Long lngFeedBackSeqID) throws TTKException;
	
	/**
	 * This method adds or updates the hospital feedback details   
	 * The method also calls other methods on DAO to insert/update the hospital feedback details to the database 
	 * @param feedbackDetailVO FeedbackDetailVO object which contains the hospital feedback details to be added/updated
	 * @return long value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public long addUpdateFeedback(FeedbackDetailVO feedbackDetailVO) throws TTKException;
	
	/**
	 * This method delete's the hospital feedback records from the database.  
	 * @param strDeleteSeqID String the hospital feedback sequence id's to be deleted
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteFeedback(String strDeleteSeqID) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains all the details about the hospital feedback questions and answers list
	 * @return ArrayList which contains all the details about the hospital feedback questions and answers list
	 * @exception throws TTKException
	 */
	public ArrayList getQAList() throws TTKException;
	
	/**
	 * This method returns the modified MOU document
	 * @param lHospSeqId long object which contains the Hospital seq id
	 * @return Document the XML based MOU document
	 * @exception throws TTKException
	 */
	public Document getMOUDocument(long lHospSeqId) throws TTKException; 
	
	/**
	 * This method adds or updates the MOU details   
	 * @param mouDocument Document object which contains MOU details
	 * @param lHospSeqId Long object which contains the hospital sequence id
	 * @param lUpdatedBy Long object which contains the user sequence id
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int addUpdateDocument(Document mouDocument, Long lHospSeqId, Long lUpdatedBy) throws TTKException;
	
	/**
	 * This method saves the Copay Information to the Hospital associated to Product/Policy or all the 
	 * Hospitals associated to Product/Policy 
	 * @param hospCopayVO HospitalCopayVO object which contains the Copay details to be saved
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int saveAssocHospCopay(ArrayList alHospCopay) throws TTKException;
	
	/**
	 * This method deletes the Copay Information to the selected Hospital(s) associated to Product/Policy 
	 *  
	 * @param alHospCopay ArrayList object which contains the Copay details to be deleted
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteAssocHospCopay(ArrayList alHospCopay) throws TTKException;
	
	/**
     * This method returns the ArrayList, which contains the TdsCertificateVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of TdsCertificateVO's object's which contains the details of the TDS certificate Information
     * @exception throws TTKException
     */
	public ArrayList<Object> getAssocCertificateList(ArrayList<Object> alSearchCriteria)throws TTKException;
	
	  /**
     * This method saves the Tds Certificate details
     * @param tdsCertificateVO TdsCertificateVO object which contains the Tds Certificate details
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
	public int saveAssocCertificateInfo(TdsCertificateVO tdsCertificateVO)throws TTKException;
	
	 /**
     * This method fetches the particular Tds Certificate Info as TdsCertificateVO
     * @param Long CertificationId
     * @return Tds Certificate details in the form of TdsCertificateVO
     * @exception throws TTKException
     */
	public TdsCertificateVO getCertiDetailInfo(long iCertSeqId)throws TTKException;
	
	/**
     * This method deletes the particulr TdsCertificate Info from the database,based on the certificate seq id's present in the arraylist
     * @param ArrayList alCertificateDelete
     * @return int It signifies the number of rows deleted in the database. 
     * @exception throws TTKException
     */
	public int deleteAssocCertificatesInfo(ArrayList<Object> alCertificateDelete,String gateway)throws TTKException;
	
	/**
     * To Save Links - File Uploads for Hospitals
     */
    public int saveMouUploads(MOUDocumentVO mouDocumentVO,ArrayList alFileAUploadList,Long userSeqId,String hospSeqId,String origFileName,InputStream inputStream,int formFileSize) throws TTKException;
    public int savepreAuthUploads(MOUDocumentVO mouDocumentVO,ArrayList alFileAUploadList,Long userSeqId,String hospSeqId,String origFileName,InputStream inputStream,int formFileSize) throws TTKException;
    
   
    /**
     * To Fetch All Uploaded files by Default - File Uploads for Hospitals
     */
    public ArrayList<Object> getMouUploadsList(String hospSeqId) throws TTKException;
    public ArrayList<Object> getPreAuthUploadsList(String hospSeqId) throws TTKException;
    
    /**
     * To Fetch Licence Number based on Provider name - used Ajax to do
     */
    public ArrayList<Object> getLicenceNumbers(String prviderId,String provName,String strIdentifier) throws TTKException;
    
    /**
     * To Fetch Licence Number based on Provider name - used Ajax to do
     */
    public ArrayList<Object> getLicenceNoForPreEmp(String provName) throws TTKException;

    
    
    /**
     * To Generate Credentials for preRequisitebased on Provider name 
     */
    public PreRequisiteVO generateCredentialsforPreRequisite(PreRequisiteVO preRequisiteVO,String strContactSeqId) throws TTKException;

    /**
     * To Send Credentials for preRequisitebased on Provider name 
     */
    public int sendCredentialsforPreRequisite(PreRequisiteVO preRequisiteVO,String strContactSeqId,String iContactSeqId) throws TTKException;

    /**
     * Generate Dash Board for a provider 
     */
    public ArrayList<Object> getProviderDashBoard() throws TTKException;
  
    
    /**
     * Generate Dash Board for a provider 
     */
    public ArrayList<Object> getTotalProviders() throws TTKException;
    
    
    /**
     * function to get the list of payers at provider empnelment screen 
     */
    public String getAllPayersList() throws TTKException;
    
    
    public int getIsdCode(String countryCode) throws TTKException;

    /**
     * get Network types  
     */
    public ArrayList<NetworkTypeVO> getNetworkTypeList() throws TTKException;

    /**
     * Save new  Network types  
     */
    public int saveNetworkType(NetworkTypeVO networkTypeVO,Long userSeqId) throws TTKException;    
    
    /**
     * Modify Network types  
     */
   public int modifyNetworkType(String[] strResult,String[] payer,Long userSeqId) throws TTKException; 

    /**
     * Get Network types  
     */
    public ArrayList<String[]> getHospitalNetworkTypes(Long lngHospSeqId) throws TTKException;
    
    /**
     * Get Network types   from History Table
     */
    public ArrayList<NetworkTypeVO> getNetworkHistory() throws TTKException;
    
    /**
     *
     */
    public String getHaadCategories(String hosp_seq_id) throws TTKException;
    public ArrayList<Object> getHaadEditHeaders(String hosp_seq_id) throws TTKException;
    public ArrayList<Object> getHaadCategories(String hosp_seq_id, String temp,String primaryNetwork) throws TTKException;
    
   public ArrayList<Object> getHaadColumnHEaders() throws TTKException;
   public int saveHaadCategories(ArrayList<String> alColumnHeaders,String[] cacheId,String[] factor,String[] baseRate,String[] gap,String[] margin,String[] discount,
	  		String hospSeqId,Long userSeqId,String networkType,String haadTarrifStartDt,String haadTarrifEndDt)throws TTKException;
   public int updateHaadCategories(String eligibleNetworks,String haadGroup,String haadfactor,String haadTarrifStartDt,String haadTarrifEndDt,String factorVal,String hosp_seq_id,Long userSeqId)throws TTKException;
   public ArrayList<CacheObject> getEligibleNetworks(String hosp_seq_id) throws TTKException;
   public ArrayList<Object> getHaadFactorsHostory(String hosp_seq_id) throws TTKException;

public ArrayList getOldLicenseList(Long lngHospSeqId)throws TTKException;

public Long UpdateHospitalLicense(HospitalDetailVO hospitalDetailVO) throws TTKException;

public HospitalDetailVO getOldLicenseUpdateDetails(Long hospSeqId) throws TTKException;

public ArrayList<Object> getBrokerList(String regAuthority) throws TTKException;



      
	public StatusVO getPartnerStatus(Long lPartnerSeqId) throws TTKException;
	
	public ArrayList getPartnerFeedbackList(ArrayList alSearchObjects) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains all the details about the partner feedback questions and answers list
	 * @return ArrayList which contains all the details about the partner feedback questions and answers list
	 * @exception throws TTKException
	 */
	public ArrayList getQAPartnerList() throws TTKException;
	
	/**
	 * This method adds or updates the partner feedback details   
	 * The method also calls other methods on DAO to insert/update the partner feedback details to the database 
	 * @param feedbackDetailVO FeedbackDetailVO object which contains the partner feedback details to be added/updated
	 * @return long value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public long addUpdatePartnerFeedback(FeedbackDetailVO feedbackDetailVO) throws TTKException;
      
	
	/**
	 * This method returns the ArrayList of FeedbackDetailVO's, which contains the partner feedback details
	 * @param lngFeedBackSeqID Long object which contains the feed back seq id
	 * @return ArrayList FeedBackDetailVO object's which contains the partner feedback details
	 * @exception throws TTKException
	 */
	public ArrayList getPartnerFeedback(Long lngFeedBackSeqID) throws TTKException;
	
	
	/**
	 * This method delete's the partner feedback records from the database.  
	 * @param strDeleteSeqID String the partner feedback sequence id's to be deleted
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int deletePartnerFeedback(String strDeleteSeqID) throws TTKException;
	
	
	/**
	 * This method updates the partner status   
	 * The method also calls other methods on DAO to update the hospital status to the database 
	 * @param statusVO StatusVO object which contains the status details to be updated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int updatePartnerStatus(StatusVO statusVO) throws TTKException;
	
	public ArrayList getProviderTariffReport() throws TTKException;
	
	public String getAllNetworkdata() throws TTKException;
	
	public long addIrdrg(HospitalDetailVO hospitalDetailVO) throws TTKException;
	
	public ArrayList<HospitalDetailVO> getIrdrgParameters(Long hospSeqId) throws TTKException;
	
	public int deleteIrdrg(Long dhaNagSeqId,Long userSeqId) throws TTKException;
	
	public ArrayList<Object> getIrdrgDetails(Long hospSeqId)throws TTKException;
	
	public ArrayList<HospitalDetailVO> getIrdrgAuditLogs(Long hospSeqId) throws TTKException;
	
	public int deleteDocs(ArrayList alDeleteBatch) throws TTKException ;
	
	
}//end of HospitalManager
