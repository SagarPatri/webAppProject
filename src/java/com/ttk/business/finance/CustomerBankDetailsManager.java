package com.ttk.business.finance;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.empanelment.AccountDetailVO;
import com.ttk.dto.empanelment.HospitalDetailVO;
import com.ttk.dto.finance.AuthorisedVO;
import com.ttk.dto.finance.BankAccountDetailVO;
import com.ttk.dto.finance.BankGuaranteeVO;
import com.ttk.dto.finance.ChequeVO;
import com.ttk.dto.finance.CustomerBankDetailsVO;
import com.ttk.dto.finance.TransactionVO;
/**
 * This class is added for cr koc 1103
 * added eft
 */
@Local
public interface CustomerBankDetailsManager {
	
	/**
	 * This method returns the HashMap,which contains the City Types associating the State
	 * @return HashMap containing City Types associating the State
	 * @exception throws TTKException
	 */
    public HashMap getbankStateInfo() throws TTKException; 
    
    /**
	 * This method returns the HashMap,which contains the Distict Types associating the State
	 * @return HashMap containing Distict Types associating the State And Bank Name
	 * @exception throws TTKException
	 */
    public HashMap getBankCityInfo(String bankState,String bankName) throws TTKException;
    
    /**
	 * This method returns the HashMap,which contains the BankBranch Types associating the State,BankNmae,BankCity
	 * @return HashMap containing BankBranch Types associating the State And Bank Name
	 * @exception throws TTKException
	 */
    public HashMap getBankBranchtInfo(String bankState,String bankName,String bankDistict) throws TTKException;
    
    /**
	 * This method returns the HashMap,which contains the IFSC Types associating the State,BankNmae,BankCity,BankBranch
	 * @return CustomerBankDetailsVO which containes IFSC code
	 * @exception throws TTKException
	 */
    public CustomerBankDetailsVO getBankIfscInfo(String bankState,String bankName,String bankDistict,String bankBranch) throws TTKException;
    
    /**
	 * This method returns the ArrayList, which contains the customerBankDetailsVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of customerBankDetailsVO'S object's which contains the details of the Bank
	 * @exception throws TTKException
	 */
    
    public ArrayList getCustomerBankAccountList(ArrayList alSearchCriteria) throws TTKException;
    
    /**
	 * This method returns the CustomerBankDetailsVO, which contains all the bank details
	 * @param lngBankSeqID  which contains seq id of Bank get the Bank Details
	 * @return CustomerBankDetailsVO object which contains the Bank details
	 * @exception throws TTKException
	 */
    public CustomerBankDetailsVO getPolicyBankAccountDetail(long lngBankAccSeqID, long lngUserSeqID) throws TTKException;
    
    /**
	 * This method returns the CustomerBankDetailsVO, which contains all the bank details
	 * @param lngBankSeqID  which contains hospital seq id of Bank get the Bank Details
	 * @return CustomerBankDetailsVO object which contains the Bank details
	 * @exception throws TTKException
	 */
    
    public CustomerBankDetailsVO getHospBankAccountDetail(long lngBankAccSeqID, long lngUserSeqID) throws TTKException;
    
    /**
	 * This method returns the CustomerBankDetailsVO, which contains all the bank details
	 * @param lngBankSeqID  which contains membergroup seq id of Bank get the Bank Details
	 * @return CustomerBankDetailsVO object which contains the Bank details
	 * @exception throws TTKException
	 */
      public CustomerBankDetailsVO getMemberBankAccountDetail(long lngBankAccSeqID, long lngUserSeqID) throws TTKException;
     /**
  	 * This method saves the Policy Bank Account information
  	 * @param customerBankDetailsVO the object which contains the Bank details which has to be saved
  	 * @return long the value which returns greater than zero for succcesssful execution of the task
  	 * @exception throws TTKException
  	 */
    public long saveBankAccountIfsc(CustomerBankDetailsVO customerBankDetails) throws TTKException;
    /**
	 * This method saves the Member Bank Account information
	 * @param customerBankDetailsVO the object which contains the Bank details which has to be saved
	 * @return long the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
    public long saveMemberBankAccountIfsc(CustomerBankDetailsVO customerBankDetails) throws TTKException;
    /**
	 * This method saves the Hospiatl Bank Account information
	 * @param customerBankDetailsVO the object which contains the Bank details which has to be saved
	 * @return long the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
    public long saveHospBankAccountIfsc(CustomerBankDetailsVO customerBankDetails) throws TTKException;
    
    /**
     * for intX
     * shows the list of Providers are in IN-PROGRESS STATUS
     */
    public ArrayList getCustomerBankAccountReviewList() throws TTKException;

    /**
	 * This method returns the HashMap,which contains the City Types associating the State
	 * @return HashMap containing City Types associating the State
	 * @exception throws TTKException
	 */
    
    public ArrayList getCustomerBankAccountReviewList(String flag) throws TTKException;
    
    public HashMap getCityInfo(String stateCode) throws TTKException; 
    /**
	 * This method returns the HospitalDetailVO, which contains all the hospital details
	 * @param lHospSeqId Long object which contains the hospital seq id
	 * @return HospitalDetailVO object which contains all the hospital details
	 * @exception throws TTKException 
	 */
	public CustomerBankDetailsVO getHospitaBanklDetail(Long lHospSeqId) throws TTKException;
	
	
	/**
	 * This method adds or updates the account details   
	 * The method also calls other methods on DAO to insert/update the account details to the database 
	 * @param accountDetailVO AccountDetailVO object which contains the account details to be added/updated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int addUpdateReviewAccount(CustomerBankDetailsVO customerBankDetailsVO) throws TTKException;

	public int addUpdateRevisedReviewAccount(CustomerBankDetailsVO customerBankDetailsVO) throws TTKException;
	
	/**
	 * This method gets the files uploaded at empanelment for a provider   
	 * @param accountDetailVO AccountDetailVO object which contains the account details to be added/updated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public ArrayList<String> getFilesUploadedAtEmpnl(Long lngHospSeqId) throws TTKException;

	
	public String getBankCode(String bankName) throws TTKException;
	
	public int saveMouUploads(CustomerBankDetailsVO customerBankDetailsVO,ArrayList<Object> alFileAUploadList, Long userSeqId,String hosp_seq_id, String origFileName, InputStream inputStream,
			int formFileSize)throws TTKException;

	public ArrayList getMouUploadsList(String hosp_seq_id) throws TTKException;
	
	 public ArrayList getPartnerBankAccountReviewList() throws TTKException;
	 
		
	 /**
		 * This method returns the PartnerDetailVO, which contains all the partner details
		 * @param lPtnrSeqId Long object which contains the partner seq id
		 * @return PartnerDetailVO object which contains all the partner details
		 * @exception throws TTKException 
		 */
		public CustomerBankDetailsVO getPartnerBankDetail(Long lPtnrSeqId) throws TTKException;
	
	
		/**
		 * This method adds or updates the account details   
		 * The method also calls other methods on DAO to insert/update the account details to the database 
		 * @param accountDetailVO AccountDetailVO object which contains the account details to be added/updated
		 * @return int value, greater than zero indicates successful execution of the task
		 * @exception throws TTKException
		 */
		public int addUpdatePartnerReviewAccount(CustomerBankDetailsVO customerBankDetailsVO) throws TTKException;
		
		

}
